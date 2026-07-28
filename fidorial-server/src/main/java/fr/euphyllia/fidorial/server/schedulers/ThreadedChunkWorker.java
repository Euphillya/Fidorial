package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.server.world.AsyncChunkLoader;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedChunkWorker implements AsyncChunkLoader {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ThreadedChunkWorker.class);

    private final ScheduledExecutorService workers;

    private final Map<String, CompletableFuture<ChunkColumn>> inFlight = new ConcurrentHashMap<>();

    public ThreadedChunkWorker(final int workerThreads) {
        final AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newScheduledThreadPool(
                workerThreads, r -> new Thread(r, "fidorial-chunk-worker-" + id.incrementAndGet()));
        LOGGER.info("Chunk pool started with {} workers", workerThreads);
    }

    private static String key(final ServerWorld world, final int chunkX, final int chunkZ) {
        return world.dimension().id() + ":" + chunkX + "," + chunkZ;
    }

    @Override
    public CompletableFuture<ChunkColumn> loadAsync(final ServerWorld world, final int chunkX, final int chunkZ) {
        final String key = key(world, chunkX, chunkZ);

        final CompletableFuture<ChunkColumn> promise = new CompletableFuture<>();
        final CompletableFuture<ChunkColumn> existing = inFlight.putIfAbsent(key, promise);
        if (existing != null) {
            return existing;
        }

        CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                return world.getChunk(chunkX, chunkZ);
                            } catch (final IOException e) {
                                throw new RuntimeException(
                                        "Unable to load chunk " + chunkX + "," + chunkZ, e);
                            }
                        },
                        workers)
                .whenComplete((chunk, error) -> {
                    inFlight.remove(key, promise);
                    if (error != null) {
                        promise.completeExceptionally(error);
                    } else {
                        promise.complete(chunk);
                    }
                });

        return promise;
    }

    public void shutdown() {
        workers.shutdown();
        try {
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
                workers.shutdownNow();
            }
        } catch (final InterruptedException e) {
            workers.shutdownNow();
            Thread.currentThread().interrupt();
        }
        LOGGER.info("Chunk workers stopped");
    }
}
