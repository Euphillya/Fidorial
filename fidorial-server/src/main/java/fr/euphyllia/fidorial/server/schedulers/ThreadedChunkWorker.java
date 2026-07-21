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

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class ThreadedChunkWorker implements AsyncChunkLoader {

    private static final ComponentLogger LOGGER = getLogger(ThreadedChunkWorker.class);

    private final ScheduledExecutorService workers;

    private final Map<String, CompletableFuture<ChunkColumn>> inFlight = new ConcurrentHashMap<>();

    public ThreadedChunkWorker(int workerThreads) {
        AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newScheduledThreadPool(workerThreads,
                r -> new Thread(r, "fidorial-chunk-worker-" + id.incrementAndGet()));
        LOGGER.info("Chunk pool started with {} workers", workerThreads);
    }

    private static String key(ServerWorld world, int chunkX, int chunkZ) {
        return world.dimension().id() + ":" + chunkX + "," + chunkZ;
    }

    @Override
    public CompletableFuture<ChunkColumn> loadAsync(ServerWorld world, int chunkX, int chunkZ) {
        String key = key(world, chunkX, chunkZ);

        CompletableFuture<ChunkColumn> promise = new CompletableFuture<>();
        CompletableFuture<ChunkColumn> existing = inFlight.putIfAbsent(key, promise);
        if (existing != null) {
            return existing;
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                return world.getChunk(chunkX, chunkZ);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Chargement du chunk " + chunkX + "," + chunkZ + " impossible", e);
            }
        }, workers).whenComplete((chunk, error) -> {
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
        } catch (InterruptedException e) {
            workers.shutdownNow();
            Thread.currentThread().interrupt();
        }
        LOGGER.info("Chunk workers stopped");
    }
}