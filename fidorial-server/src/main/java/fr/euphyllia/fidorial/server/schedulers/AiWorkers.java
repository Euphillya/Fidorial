package fr.euphyllia.fidorial.server.schedulers;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;


public class AiWorkers {

    private static final ComponentLogger LOGGER = getLogger(AiWorkers.class);
    private static volatile ExecutorService executor;

    private AiWorkers() {
    }

    private static ExecutorService executor() {
        ExecutorService current = executor;
        if (current == null) {
            synchronized (AiWorkers.class) {
                current = executor;
                if (current == null) {
                    int threads = Math.max(1, Runtime.getRuntime().availableProcessors() / 4);
                    AtomicInteger id = new AtomicInteger();
                    current = Executors.newFixedThreadPool(threads, task -> {
                        Thread thread = new Thread(task, "fidorial-ai-worker-" + id.incrementAndGet());
                        thread.setDaemon(true);
                        return thread;
                    });
                    executor = current;
                    LOGGER.info("Pool IA demarre avec {} workers", threads);
                }
            }
        }
        return current;
    }

    public static boolean submit(Runnable task) {
        try {
            executor().execute(() -> {
                try {
                    task.run();
                } catch (Throwable t) {
                    LOGGER.error("Erreur dans une tache IA", t);
                }
            });
            return true;
        } catch (RejectedExecutionException e) {
            return false;
        }
    }

    public static void shutdown() {
        ExecutorService current = executor;
        if (current == null) {
            return;
        }
        current.shutdown();
        try {
            if (!current.awaitTermination(2, TimeUnit.SECONDS)) {
                current.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            current.shutdownNow();
        }
    }
}
