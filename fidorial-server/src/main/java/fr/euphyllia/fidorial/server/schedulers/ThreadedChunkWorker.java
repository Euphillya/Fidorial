package fr.euphyllia.fidorial.server.schedulers;

import ca.spottedleaf.common.util.IntPairUtil;
import ca.spottedleaf.concurrentutil.completable.Completable;
import ca.spottedleaf.concurrentutil.executor.PrioritisedExecutor;
import ca.spottedleaf.concurrentutil.executor.thread.BalancedPrioritisedThreadPool;
import ca.spottedleaf.concurrentutil.util.Priority;
import fr.euphyllia.fidorial.server.world.AsyncChunkLoader;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedChunkWorker implements AsyncChunkLoader {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ThreadedChunkWorker.class);

    private static final long GROUP_TIME_SLICE_NS = BalancedPrioritisedThreadPool.DEFAULT_GROUP_TIME_SLICE;
    private final BalancedPrioritisedThreadPool pool;
    private final BalancedPrioritisedThreadPool.OrderedStreamGroup group;

    private final PrioritisedExecutor loadExecutor;

    private final PrioritisedExecutor genericExecutor;

    private final Map<Key, Map<Long, PendingLoad>> inFlight = new ConcurrentHashMap<>();

    private record PendingLoad(Completable<ChunkColumn> promise, PrioritisedExecutor.PrioritisedTask task) {
    }

    public ThreadedChunkWorker(final int workerThreads) {
        final AtomicInteger id = new AtomicInteger();
        this.pool = new BalancedPrioritisedThreadPool(
                GROUP_TIME_SLICE_NS,
                r -> new Thread(r, "fidorial-chunk-worker-" + id.incrementAndGet()));
        this.group = pool.createOrderedStreamGroup();
        this.loadExecutor = group.createExecutor();
        this.genericExecutor = group.createExecutor();
        this.pool.adjustThreadCount(Math.max(1, workerThreads));

        LOGGER.info("Chunk pool started with {} workers", workerThreads);
    }

    @Override
    public CompletableFuture<ChunkColumn> loadAsync(final ServerWorld world, final int chunkX, final int chunkZ) {
        return loadAsync(world, chunkX, chunkZ, Priority.NORMAL).toFuture();
    }

    public Completable<ChunkColumn> loadAsync(
            final ServerWorld world, final int chunkX, final int chunkZ, final Priority priority) {
        final Map<Long, PendingLoad> worldMap =
                inFlight.computeIfAbsent(world.dimension().id(), _ -> new ConcurrentHashMap<>());
        final long key = IntPairUtil.key(chunkX, chunkZ);

        final PendingLoad current = worldMap.get(key);
        if (current != null) {
            current.task().raisePriority(priority);
            return current.promise();
        }

        final Completable<ChunkColumn> promise = new Completable<>();
        final PrioritisedExecutor.PrioritisedTask task = loadExecutor.createTask(
                () -> {
                    try {
                        promise.complete(world.getChunk(chunkX, chunkZ));
                    } catch (final IOException e) {
                        promise.completeExceptionally(
                                new UncheckedIOException("Unable to load chunk " + chunkX + "," + chunkZ, e));
                    } catch (final Throwable t) {
                        promise.completeExceptionally(t);
                    }
                },
                priority);

        final PendingLoad pending = new PendingLoad(promise, task);
        final PendingLoad raced = worldMap.putIfAbsent(key, pending);
        if (raced != null) {
            task.cancel();
            raced.task().raisePriority(priority);
            return raced.promise();
        }

        promise.whenComplete((_, _) -> worldMap.remove(key, pending));
        task.queue();
        return promise;
    }

    public void raisePriority(final ServerWorld world, final int chunkX, final int chunkZ, final Priority priority) {
        final Map<Long, PendingLoad> worldMap = inFlight.get(world.dimension().id());
        if (worldMap == null) return;
        final PendingLoad pending = worldMap.get(IntPairUtil.key(chunkX, chunkZ));
        if (pending != null) {
            pending.task().raisePriority(priority);
        }
    }

    public void execute(final Runnable task) {
        genericExecutor.queueTask(task, Priority.NORMAL);
    }

    public void shutdown() {
        pool.shutdown(false);
        if (!pool.join(TimeUnit.SECONDS.toMillis(5L))) {
            pool.halt(true);
        }
        inFlight.clear();
        LOGGER.info("Chunk workers stopped");
    }
}
