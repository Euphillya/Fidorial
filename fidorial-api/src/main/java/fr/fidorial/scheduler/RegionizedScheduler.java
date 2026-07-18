package fr.fidorial.scheduler;

import fr.fidorial.world.ChunkPos;

import java.util.List;

public interface RegionizedScheduler {

    void execute(String worldName, ChunkPos pos, Runnable task);

    void executeDelayed(String worldName, ChunkPos pos, Runnable task, long delayTicks);

    boolean isOwnedByCurrentThread(String worldName, ChunkPos pos);

    List<? extends RegionTps> tpsSnapshots();
}
