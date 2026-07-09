package fr.euphyllia.fidorial.api.scheduler;

import fr.euphyllia.fidorial.api.world.ChunkPos;

public interface RegionizedScheduler {

    void execute(String worldName, ChunkPos pos, Runnable task);

    void executeDelayed(String worldName, ChunkPos pos, Runnable task, long delayTicks);

    boolean isOwnedByCurrentThread(String worldName, ChunkPos pos);
}
