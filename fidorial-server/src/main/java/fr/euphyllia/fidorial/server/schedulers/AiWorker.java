package fr.euphyllia.fidorial.server.schedulers;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class AiWorker {

    private static final ComponentLogger LOGGER = getLogger(AiWorker.class);

    private final ExecutorService workers;

    public AiWorker(int workerThreads) {
        AtomicInteger id = new AtomicInteger();
        this.workers = Executors.newFixedThreadPool(workerThreads, r -> {
            Thread thread = new Thread(r, "fidorial-ai-worker-" + id.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
        LOGGER.info("AI pool started with {} workers", workerThreads);
    }

    public boolean submit(Runnable task) {
        try {
            workers.execute(() -> {
                try {
                    task.run();
                } catch (Throwable t) {
                    LOGGER.error("Error in an AI task", t);
                }
            });
            return true;
        } catch (RejectedExecutionException e) {
            return false;
        }
    }

    public void shutdown() {
        workers.shutdown();
        try {
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
                workers.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            workers.shutdownNow();
        }
        LOGGER.info("AI workers stopped");
    }
}