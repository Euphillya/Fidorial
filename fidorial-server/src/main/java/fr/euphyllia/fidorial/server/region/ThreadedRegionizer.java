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
        regionFor(worldName, pos).enqueue(task, 0);
    }

    @Override
    public void executeDelayed(String worldName, ChunkPos pos, Runnable task, long delayTicks) {
        regionFor(worldName, pos).enqueue(task, Math.max(0, delayTicks));
    }

    @Override
    public boolean isOwnedByCurrentThread(String worldName, ChunkPos pos) {
        Region region = regions.get(RegionKey.of(worldName, pos));
        return region != null && region.tickingThread == Thread.currentThread();
    }

    public void shutdown() {
        workers.shutdown();
        try {
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) workers.shutdownNow();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            workers.shutdownNow();
        }
    }

    private Region regionFor(String worldName, ChunkPos pos) {
        return regions.computeIfAbsent(RegionKey.of(worldName, pos), key -> {
            Region region = new Region(key);
            region.future = workers.scheduleAtFixedRate(
                    region::tick, 0, TICK_PERIOD_MS, TimeUnit.MILLISECONDS);
            LOGGER.debug("Region creee : {}", key);
            return region;
        });
    }

    private record RegionKey(String world, int sectionX, int sectionZ) {
        static RegionKey of(String world, ChunkPos pos) {
            return new RegionKey(world, pos.x() >> SECTION_SHIFT, pos.z() >> SECTION_SHIFT);
        }
    }

    private static final class Region {
        final RegionKey key;
        final Queue<ScheduledTask> tasks = new ConcurrentLinkedQueue<>();
        volatile Thread tickingThread;
        ScheduledFuture<?> future;
        long currentTick;

        Region(RegionKey key) {
            this.key = key;
        }

        void enqueue(Runnable task, long delayTicks) {
            tasks.add(new ScheduledTask(task, currentTick + delayTicks));
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

            } finally {
                tickingThread = null;
            }
        }
    }

    private record ScheduledTask(Runnable task, long executeAtTick) {
    }
}
