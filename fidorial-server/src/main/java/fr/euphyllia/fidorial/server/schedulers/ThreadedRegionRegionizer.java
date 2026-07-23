package fr.euphyllia.fidorial.server.schedulers;

import fr.fidorial.scheduler.RegionTickHandler;
import fr.fidorial.scheduler.RegionTps;
import fr.fidorial.scheduler.RegionizedScheduler;
import fr.fidorial.world.ChunkPos;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadedRegionRegionizer implements RegionizedScheduler {

    public static final int SECTION_SHIFT = 5;
    private static final ComponentLogger LOGGER = ComponentLogger.logger(ThreadedRegionRegionizer.class);
    private static final long TICK_PERIOD_MS = 50L;
    /**
     * Number of consecutive empty ticks before an idle region is destroyed.
     * At 20 TPS this corresponds to ~1 minute of inactivity.
     */
    private static final int MAX_EMPTY_TICKS = 20 * 60;

    private static final int TPS_SAMPLE_SIZE = 100; // ~5 s a 20 TPS

    private static final ThreadMXBean THREADS = ManagementFactory.getThreadMXBean();
    private static final boolean CPU_TIME_SUPPORTED = cpuTimeSupported();

    private final ScheduledExecutorService workers;
    private final ConcurrentMap<RegionKey, Region> regions = new ConcurrentHashMap<>();
    private final List<RegionTickHandler> tickHandlers = new CopyOnWriteArrayList<>();

    public ThreadedRegionRegionizer(final int workerThreads) {
        final AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newScheduledThreadPool(
                workerThreads, r -> new Thread(r, "fidorial-region-worker-" + id.incrementAndGet()));
        LOGGER.info("Region pool started with {} workers", workerThreads);
    }

    @Override
    public void execute(final Key worldName, final ChunkPos pos, final Runnable task) {
        enqueue(worldName, pos, task, 0);
    }

    @Override
    public void executeDelayed(final Key worldName, final ChunkPos pos, final Runnable task, final long delayTicks) {
        enqueue(worldName, pos, task, Math.max(0, delayTicks));
    }

    @Override
    public boolean isOwnedByCurrentThread(final Key worldName, final ChunkPos pos) {
        final Region region = regions.get(RegionKey.of(worldName, pos));
        return region != null && region.tickingThread == Thread.currentThread();
    }

    public void registerTickHandler(final RegionTickHandler handler) {
        tickHandlers.add(handler);
    }

    public void addTicket(final Key worldName, final ChunkPos pos) {
        final RegionKey key = RegionKey.of(worldName, pos);
        regions.compute(key, (k, region) -> {
            if (region == null) {
                region = createRegion(key);
            }
            region.tickets.incrementAndGet();
            region.emptyTicks.set(0);
            return region;
        });
    }

    public void removeTicket(final Key worldName, final ChunkPos pos) {
        final Region region = regions.get(RegionKey.of(worldName, pos));
        if (region != null) {
            region.tickets.updateAndGet(t -> Math.max(0, t - 1));
        }
    }

    public void moveTicket(final Key worldName, final ChunkPos from, final ChunkPos to) {
        if (RegionKey.of(worldName, from).equals(RegionKey.of(worldName, to))) return;
        addTicket(worldName, to);
        removeTicket(worldName, from);
    }

    private static boolean cpuTimeSupported() {
        try {
            if (!THREADS.isThreadCpuTimeSupported()) return false;
            if (!THREADS.isThreadCpuTimeEnabled()) THREADS.setThreadCpuTimeEnabled(true);
            return THREADS.isThreadCpuTimeEnabled();
        } catch (final UnsupportedOperationException | SecurityException e) {
            LOGGER.warn("Per-thread CPU time is unavailable, falling back to tick occupancy", e);
            return false;
        }
    }

    private static long currentThreadCpuNanos() {
        if (!CPU_TIME_SUPPORTED) return -1L;
        final long cpu = THREADS.getCurrentThreadCpuTime();
        return cpu < 0 ? -1L : cpu;
    }

    /**
     * Snapshot of the region that owns the given chunk, or {@code null} when
     * no region is active there (or when it has not ticked enough yet).
     */
    public @Nullable RegionTpsSnapshot snapshotAt(final Key worldName, final ChunkPos pos) {
        final Region region = regions.get(RegionKey.of(worldName, pos));
        return region == null ? null : region.snapshot();
    }

    public List<RegionTpsSnapshot> tpsSnapshots() {
        final List<RegionTpsSnapshot> out = new ArrayList<>(regions.size());
        for (final Region region : regions.values()) {
            final RegionTpsSnapshot snapshot = region.snapshot();
            if (snapshot != null) out.add(snapshot);
        }
        out.sort(Comparator.comparingDouble(RegionTpsSnapshot::tps));
        return out;
    }

    public void shutdown() {
        for (final Region region : regions.values()) {
            final ScheduledFuture<?> future = region.future;
            if (future != null) future.cancel(false);
        }
        workers.shutdown();
        try {
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) workers.shutdownNow();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            workers.shutdownNow();
        }
    }

    private Region createRegion(final RegionKey key) {
        final Region region = new Region(key);
        region.future = workers.scheduleAtFixedRate(region::tick, 0, TICK_PERIOD_MS, TimeUnit.MILLISECONDS);
        LOGGER.debug("Region created: {}", key);
        return region;
    }

    private void enqueue(final Key worldName, final ChunkPos pos, final Runnable task, final long delayTicks) {
        final RegionKey key = RegionKey.of(worldName, pos);
        regions.compute(key, (k, region) -> {
            if (region == null) {
                region = createRegion(key);
            }
            region.emptyTicks.set(0);
            region.tasks.add(new ScheduledTask(task, region.currentTick + delayTicks));
            return region;
        });
    }

    private void tryRemoveIdle(final Region region) {
        regions.compute(region.key, (k, existing) -> {
            if (existing != region) return existing;
            if (region.tasks.isEmpty()
                    && region.tickets.get() == 0
                    && region.emptyTicks.get() >= MAX_EMPTY_TICKS
                    && region.future != null) {
                region.future.cancel(false);
                LOGGER.debug("Region removed: {}", region.key);
                return null;
            }
            return region;
        });
    }

    public record RegionTpsSnapshot(
            Key world,
            int sectionX,
            int sectionZ,
            double tps,
            double msptAvg,
            double cpuPercent,
            int queuedTasks,
            int tickets)
            implements RegionTps {

        public int originChunkX() {
            return sectionX << SECTION_SHIFT;
        }

        public int originChunkZ() {
            return sectionZ << SECTION_SHIFT;
        }
    }

    private record RegionKey(Key world, int sectionX, int sectionZ) {
        static RegionKey of(final Key world, final ChunkPos pos) {
            return new RegionKey(world, pos.x() >> SECTION_SHIFT, pos.z() >> SECTION_SHIFT);
        }
    }

    private record ScheduledTask(Runnable task, long executeAtTick) {
    }

    private final class Region {
        final RegionKey key;
        final Queue<ScheduledTask> tasks = new ConcurrentLinkedQueue<>();
        final AtomicInteger emptyTicks = new AtomicInteger();
        final AtomicInteger tickets = new AtomicInteger();
        private final Object tpsLock = new Object();
        private final long[] tickEndNanos = new long[TPS_SAMPLE_SIZE];
        private final long[] tickDurationNanos = new long[TPS_SAMPLE_SIZE];
        private final long[] tickCpuNanos = new long[TPS_SAMPLE_SIZE];
        volatile @Nullable Thread tickingThread;

        @Nullable ScheduledFuture<?> future;

        long currentTick;
        private int sampleIndex;
        private int sampleCount;

        Region(final RegionKey key) {
            this.key = key;
        }

        @Nullable RegionTpsSnapshot snapshot() {
            synchronized (tpsLock) {
                if (sampleCount < 2) return null;
                final int newest = Math.floorMod(sampleIndex - 1, TPS_SAMPLE_SIZE);
                final int oldest = sampleCount < TPS_SAMPLE_SIZE ? 0 : sampleIndex;
                final long elapsed = tickEndNanos[newest] - tickEndNanos[oldest];
                if (elapsed <= 0) return null;
                final double tps = (sampleCount - 1) * 1_000_000_000.0 / elapsed;
                long totalDuration = 0;
                long totalCpu = 0;
                boolean cpuMeasured = true;

                for (int i = 0; i < sampleCount; i++) {
                    totalDuration += tickDurationNanos[i];
                    if (tickCpuNanos[i] < 0) {
                        cpuMeasured = false;
                    } else {
                        totalCpu += tickCpuNanos[i];
                    }
                }
                final double msptAvg = totalDuration / 1_000_000.0 / sampleCount;
                final double avgWallPerTick = (double) elapsed / (sampleCount - 1);
                final double busyPerTick =
                        cpuMeasured ? (double) totalCpu / sampleCount : (double) totalDuration / sampleCount;
                final double cpuPercent = avgWallPerTick <= 0 ? 0.0 : busyPerTick / avgWallPerTick * 100.0;

                return new RegionTpsSnapshot(
                        key.world(),
                        key.sectionX(),
                        key.sectionZ(),
                        Math.min(tps, 20.0),
                        msptAvg,
                        cpuPercent,
                        tasks.size(),
                        tickets.get());
            }
        }

        private void recordTick(final long startNanos, final long startCpuNanos) {
            final long end = System.nanoTime();
            final long endCpu = currentThreadCpuNanos();
            synchronized (tpsLock) {
                tickEndNanos[sampleIndex] = end;
                tickDurationNanos[sampleIndex] = end - startNanos;
                tickCpuNanos[sampleIndex] =
                        (startCpuNanos < 0 || endCpu < 0) ? -1L : Math.max(0L, endCpu - startCpuNanos);
                sampleIndex = (sampleIndex + 1) % TPS_SAMPLE_SIZE;
                if (sampleCount < TPS_SAMPLE_SIZE) sampleCount++;
            }
        }

        void tick() {
            final long startNanos = System.nanoTime();
            final long startCpuNanos = currentThreadCpuNanos();
            tickingThread = Thread.currentThread();
            try {
                currentTick++;

                final int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    final ScheduledTask t = tasks.poll();
                    if (t == null) break;
                    if (t.executeAtTick <= currentTick) {
                        try {
                            t.task.run();
                        } catch (final Throwable ex) {
                            LOGGER.error("Error in a task of {}", key, ex);
                        }
                    } else {
                        tasks.add(t);
                    }
                }

                for (final RegionTickHandler handler : tickHandlers) {
                    try {
                        handler.tick(key.world(), key.sectionX(), key.sectionZ(), currentTick);
                    } catch (final Throwable ex) {
                        LOGGER.error("Error in a tick handler of {}", key, ex);
                    }
                }

                if (tasks.isEmpty() && tickets.get() == 0) {
                    if (emptyTicks.incrementAndGet() >= MAX_EMPTY_TICKS) {
                        tryRemoveIdle(this);
                    }
                } else {
                    emptyTicks.set(0);
                }
            } finally {
                tickingThread = null;
                recordTick(startNanos, startCpuNanos);
            }
        }
    }
}
