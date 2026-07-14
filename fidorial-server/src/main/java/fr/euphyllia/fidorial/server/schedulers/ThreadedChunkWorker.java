package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedChunkWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadedChunkWorker.class);

    private final ScheduledExecutorService workers;

    private final Map<String, CompletableFuture<ChunkColumn>> inFlight = new ConcurrentHashMap<>();

    public ThreadedChunkWorker(int workerThreads) {
        AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newScheduledThreadPool(workerThreads,
                r -> new Thread(r, "fidorial-chunk-worker-" + id.incrementAndGet()));
        LOGGER.info("Pool chunk demarre avec {} workers", workerThreads);
    }

    private static String key(ServerWorld world, int chunkX, int chunkZ) {
        return world.dimension().id() + ":" + chunkX + "," + chunkZ;
    }

    public CompletableFuture<ChunkColumn> loadAsync(ServerWorld world, int chunkX, int chunkZ) {
        String key = key(world, chunkX, chunkZ);
        return inFlight.computeIfAbsent(key, k ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return world.getChunk(chunkX, chunkZ);
                    } catch (IOException e) {
                        throw new RuntimeException(
                                "Chargement du chunk " + chunkX + "," + chunkZ + " impossible", e);
                    }
                }, workers).whenComplete((_, _) -> inFlight.remove(key))
        );
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
        LOGGER.info("Chunk workers arrêtés");
    }
}