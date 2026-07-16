package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.api.scheduler.RegionTickHandler;
import fr.euphyllia.fidorial.api.scheduler.RegionTps;
import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.api.world.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadedRegionRegionizer implements RegionizedScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadedRegionRegionizer.class);
    private static final long TICK_PERIOD_MS = 50L;
    /**
     * Number of consecutive empty ticks before an idle region is destroyed.
     * At 20 TPS this corresponds to ~1 minute of inactivity.
     */
    private static final int MAX_EMPTY_TICKS = 20 * 60;

    private static final int SECTION_SHIFT = 5;
    private static final int TPS_SAMPLE_SIZE = 100; // ~5 s a 20 TPS
    private final ScheduledExecutorService workers;
    private final ConcurrentMap<RegionKey, Region> regions = new ConcurrentHashMap<>();
    private final List<RegionTickHandler> tickHandlers = new CopyOnWriteArrayList<>();

    public ThreadedRegionRegionizer(int workerThreads) {
        AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newScheduledThreadPool(workerThreads,
                r -> new Thread(r, "fidorial-region-worker-" + id.incrementAndGet()));
        LOGGER.info("Pool regional demarre avec {} workers", workerThreads);
    }

    @Override
    public void execute(String worldName, ChunkPos pos, Runnable task) {
        enqueue(worldName, pos, task, 0);
    }

    @Override
    public void executeDelayed(String worldName, ChunkPos pos, Runnable task, long delayTicks) {
        enqueue(worldName, pos, task, Math.max(0, delayTicks));
    }

    @Override
    public boolean isOwnedByCurrentThread(String worldName, ChunkPos pos) {
        Region region = regions.get(RegionKey.of(worldName, pos));
        return region != null && region.tickingThread == Thread.currentThread();
    }

    public void registerTickHandler(RegionTickHandler handler) {
        tickHandlers.add(handler);
    }

    public void addTicket(String worldName, ChunkPos pos) {
        RegionKey key = RegionKey.of(worldName, pos);
        regions.compute(key, (k, region) -> {
            if (region == null) {
                region = createRegion(key);
            }
            region.tickets.incrementAndGet();
            region.emptyTicks.set(0);
            return region;
        });
    }

    public void removeTicket(String worldName, ChunkPos pos) {
        Region region = regions.get(RegionKey.of(worldName, pos));
        if (region != null) {
            region.tickets.updateAndGet(t -> Math.max(0, t - 1));
        }
    }

    public void moveTicket(String worldName, ChunkPos from, ChunkPos to) {
        if (RegionKey.of(worldName, from).equals(RegionKey.of(worldName, to))) return;
        addTicket(worldName, to);
        removeTicket(worldName, from);
    }

    public List<RegionTpsSnapshot> tpsSnapshots() {
        List<RegionTpsSnapshot> out = new ArrayList<>(regions.size());
        for (Region region : regions.values()) {
            RegionTpsSnapshot snapshot = region.snapshot();
            if (snapshot != null) out.add(snapshot);
        }
        out.sort(Comparator.comparingDouble(RegionTpsSnapshot::tps));
        return out;
    }

    public void shutdown() {
        for (Region region : regions.values()) {
            ScheduledFuture<?> future = region.future;
            if (future != null) future.cancel(false);
        }
        workers.shutdown();
        try {
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) workers.shutdownNow();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            workers.shutdownNow();
        }
    }

    private Region createRegion(RegionKey key) {
        Region region = new Region(key);
        region.future = workers.scheduleAtFixedRate(
                region::tick, 0, TICK_PERIOD_MS, TimeUnit.MILLISECONDS);
        LOGGER.debug("Region creee : {}", key);
        return region;
    }

    private void enqueue(String worldName, ChunkPos pos, Runnable task, long delayTicks) {
        RegionKey key = RegionKey.of(worldName, pos);
        regions.compute(key, (k, region) -> {
            if (region == null) {
                region = createRegion(key);
            }
            region.emptyTicks.set(0);
            region.tasks.add(new ScheduledTask(task, region.currentTick + delayTicks));
            return region;
        });
    }

    private void tryRemoveIdle(Region region) {
        regions.compute(region.key, (k, existing) -> {
            if (existing != region) return existing;
            if (region.tasks.isEmpty() && region.tickets.get() == 0
                    && region.emptyTicks.get() >= MAX_EMPTY_TICKS) {
                region.future.cancel(false);
                LOGGER.debug("Region supprimee : {}", region.key);
                return null;
            }
            return region;
        });
    }

    public record RegionTpsSnapshot(String world, int sectionX, int sectionZ,
                                    double tps, double msptAvg, int queuedTasks, int tickets)
            implements RegionTps {

        public int originChunkX() {
            return sectionX << SECTION_SHIFT;
        }

        public int originChunkZ() {
            return sectionZ << SECTION_SHIFT;
        }
    }

    private record RegionKey(String world, int sectionX, int sectionZ) {
        static RegionKey of(String world, ChunkPos pos) {
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
        volatile Thread tickingThread;
        ScheduledFuture<?> future;
        long currentTick;
        private int sampleIndex;
        private int sampleCount;

        Region(RegionKey key) {
            this.key = key;
        }

        RegionTpsSnapshot snapshot() {
            synchronized (tpsLock) {
                if (sampleCount < 2) return null;
                int newest = Math.floorMod(sampleIndex - 1, TPS_SAMPLE_SIZE);
                int oldest = sampleCount < TPS_SAMPLE_SIZE
                        ? 0
                        : sampleIndex;
                long elapsed = tickEndNanos[newest] - tickEndNanos[oldest];
                if (elapsed <= 0) return null;
                double tps = (sampleCount - 1) * 1_000_000_000.0 / elapsed;
                long totalDuration = 0;
                for (int i = 0; i < sampleCount; i++) totalDuration += tickDurationNanos[i];
                double msptAvg = totalDuration / 1_000_000.0 / sampleCount;
                return new RegionTpsSnapshot(key.world(), key.sectionX(), key.sectionZ(),
                        Math.min(tps, 20.0), msptAvg, tasks.size(), tickets.get());
            }
        }

        private void recordTick(long startNanos) {
            long end = System.nanoTime();
            synchronized (tpsLock) {
                tickEndNanos[sampleIndex] = end;
                tickDurationNanos[sampleIndex] = end - startNanos;
                sampleIndex = (sampleIndex + 1) % TPS_SAMPLE_SIZE;
                if (sampleCount < TPS_SAMPLE_SIZE) sampleCount++;
            }
        }

        void tick() {
            long startNanos = System.nanoTime();
            tickingThread = Thread.currentThread();
            try {
                currentTick++;

                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    ScheduledTask t = tasks.poll();
                    if (t == null) break;
                    if (t.executeAtTick <= currentTick) {
                        try {
                            t.task.run();
                        } catch (Throwable ex) {
                            LOGGER.error("Erreur dans une tache de {}", key, ex);
                        }
                    } else {
                        tasks.add(t);
                    }
                }

                for (RegionTickHandler handler : tickHandlers) {
                    try {
                        handler.tick(key.world(), key.sectionX(), key.sectionZ(), currentTick);
                    } catch (Throwable ex) {
                        LOGGER.error("Erreur dans un tick handler de {}", key, ex);
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
                recordTick(startNanos);
            }
        }
    }
}
