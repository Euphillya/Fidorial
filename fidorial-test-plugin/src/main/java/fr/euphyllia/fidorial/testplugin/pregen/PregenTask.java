package fr.euphyllia.fidorial.testplugin.pregen;

import fr.euphyllia.fidorial.api.world.Chunk;
import fr.euphyllia.fidorial.api.world.World;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class PregenTask {

    private static final int MAX_IN_FLIGHT = 64;

    private static final long REPORT_PERIOD_MS = 5_000;

    private final World world;
    private final Logger logger;
    private final int centerX;
    private final int centerZ;
    private final int radius;
    private final int total;
    private final Consumer<String> progressListener;

    private final Semaphore inFlight = new Semaphore(MAX_IN_FLIGHT);
    private final AtomicInteger done = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();
    private final long startedAt = System.currentTimeMillis();
    private volatile boolean cancelled;
    private volatile boolean finished;
    private Thread thread;

    public PregenTask(World world, Logger logger,
                      int centerX, int centerZ, int radius, Consumer<String> progressListener) {
        this.world = world;
        this.logger = logger;
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.radius = radius;
        this.total = (2 * radius + 1) * (2 * radius + 1);
        this.progressListener = progressListener;
    }

    public void start() {
        thread = Thread.ofPlatform().name("fidorial-pregen").start(() -> {
            try {
                run();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void cancel() {
        cancelled = true;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return !finished && !cancelled;
    }

    public String status() {
        int completed = done.get();
        long elapsedMs = Math.max(1, System.currentTimeMillis() - startedAt);
        double perSecond = completed * 1000.0 / elapsedMs;
        long remaining = total - completed;
        long etaSeconds = perSecond > 0 ? (long) (remaining / perSecond) : -1;
        return String.format("%d/%d chunks (%.1f%%), %.0f chunks/s, ETA %s",
                completed, total, completed * 100.0 / total, perSecond,
                etaSeconds < 0 ? "?" : etaSeconds + "s");
    }

    private void run() throws ExecutionException, InterruptedException {
        logger.info("Pre-generation lancee : rayon {} autour de {},{} ({} chunks)",
                radius, centerX, centerZ, total);
        long nextReport = System.currentTimeMillis() + REPORT_PERIOD_MS;

        outer:
        for (int r = 0; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) {
                        continue;
                    }
                    if (cancelled) {
                        break outer;
                    }
                    submit(this.world, centerX + dx, centerZ + dz).get();

                    if (System.currentTimeMillis() >= nextReport) {
                        nextReport = System.currentTimeMillis() + REPORT_PERIOD_MS;
                        progressListener.accept(status());
                    }
                }
            }
        }

        // attendre la fin des chargements en vol
        try {
            inFlight.acquire(MAX_IN_FLIGHT);
        } catch (InterruptedException e) {
            thread.interrupt();
        }
        finished = true;

        if (cancelled) {
            progressListener.accept("Pre-generation annulee apres " + done.get() + " chunks.");
        } else {
            long seconds = (System.currentTimeMillis() - startedAt) / 1000;
            progressListener.accept("Pre-generation terminee : " + done.get() + " chunks en "
                    + seconds + "s" + (failed.get() > 0 ? " (" + failed.get() + " echecs, voir logs)" : "") + ".");
        }
    }

    private CompletableFuture<Chunk> submit(World world, int chunkX, int chunkZ) {
        try {
            inFlight.acquire();
        } catch (InterruptedException e) {
            thread.interrupt();
            cancelled = true;
            return new CompletableFuture<>();
        }

        return world.getChunkAsync(chunkX, chunkZ)
                .thenCompose(c -> world.unloadChunkAsync(chunkX, chunkZ).thenApply(x -> c))
                .whenComplete((ignored, error) -> {
                    inFlight.release();
                    if (error != null) {
                        failed.incrementAndGet();
                        logger.warn("Pre-generation du chunk {},{} impossible", chunkX, chunkZ, error);
                    }
                    done.incrementAndGet();
                });
    }
}
