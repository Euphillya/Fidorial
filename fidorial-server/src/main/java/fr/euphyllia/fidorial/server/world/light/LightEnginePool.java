package fr.euphyllia.fidorial.server.world.light;

import java.util.concurrent.ArrayBlockingQueue;

public final class LightEnginePool {
    private final ArrayBlockingQueue<FloodFillLightEngine> pool;
    private final int minY;
    private final int height;

    public LightEnginePool(final int parallelism, final int minY, final int height) {
        this.minY = minY;
        this.height = height;
        this.pool = new ArrayBlockingQueue<>(parallelism);
        for (int i = 0; i < parallelism; i++) {
            pool.add(new FloodFillLightEngine(minY, height));
        }
    }

    public FloodFillLightEngine acquire() throws InterruptedException {
        return pool.take();
    }

    public void release(final FloodFillLightEngine engine) {
        pool.offer(engine);
    }
}
