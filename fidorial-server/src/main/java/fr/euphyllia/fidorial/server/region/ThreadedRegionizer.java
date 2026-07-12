package fr.euphyllia.fidorial.server.region;

import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.api.world.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadedRegionizer implements RegionizedScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadedRegionizer.class);
    private static final long TICK_PERIOD_MS = 50L;
    /**
     * Number of consecutive empty ticks before an idle region is destroyed.
     * At 20 TPS this corresponds to ~1 minute of inactivity.
     */
    private static final int MAX_EMPTY_TICKS = 20 * 60;

    private static final int SECTION_SHIFT = 5;

    private final ScheduledExecutorService workers;
    private final ConcurrentMap<RegionKey, Region> regions = new ConcurrentHashMap<>();

    public ThreadedRegionizer(int workerThreads) {
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

    private void enqueue(String worldName, ChunkPos pos, Runnable task, long delayTicks) {
        RegionKey key = RegionKey.of(worldName, pos);
        regions.compute(key, (k, region) -> {
            if (region == null) {
                region = new Region(key);
                region.future = workers.scheduleAtFixedRate(
                        region::tick, 0, TICK_PERIOD_MS, TimeUnit.MILLISECONDS);
                LOGGER.debug("Region creee : {}", key);
            }
            region.emptyTicks.set(0);
            region.tasks.add(new ScheduledTask(task, region.currentTick + delayTicks));
            return region;
        });
    }

    private void tryRemoveIdle(Region region) {
        regions.compute(region.key, (k, existing) -> {
            if (existing != region) return existing;
            if (region.tasks.isEmpty() && region.emptyTicks.get() >= MAX_EMPTY_TICKS) {
                region.future.cancel(false);
                LOGGER.debug("Region supprimee : {}", region.key);
                return null;
            }
            return region;
        });
    }

    private record RegionKey(String world, int sectionX, int sectionZ) {
        static RegionKey of(String world, ChunkPos pos) {
            return new RegionKey(world, pos.x() >> SECTION_SHIFT, pos.z() >> SECTION_SHIFT);
        }
    }

    private final class Region {
        final RegionKey key;
        final Queue<ScheduledTask> tasks = new ConcurrentLinkedQueue<>();
        final AtomicInteger emptyTicks = new AtomicInteger();
        volatile Thread tickingThread;
        ScheduledFuture<?> future;
        long currentTick;

        Region(RegionKey key) {
            this.key = key;
        }

        void tick() {
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

                if (tasks.isEmpty()) {
                    if (emptyTicks.incrementAndGet() >= MAX_EMPTY_TICKS) {
                        tryRemoveIdle(this);
                    }
                } else {
                    emptyTicks.set(0);
                }
            } finally {
                tickingThread = null;
            }
        }
    }

    private record ScheduledTask(Runnable task, long executeAtTick) {
    }
}
