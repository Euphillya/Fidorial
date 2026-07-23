package fr.fidorial.scheduler;

import fr.fidorial.world.ChunkPos;
import net.kyori.adventure.key.Key;

import java.util.List;

public interface RegionizedScheduler {

    void execute(Key worldName, ChunkPos pos, Runnable task);

    void executeDelayed(Key worldName, ChunkPos pos, Runnable task, long delayTicks);

    boolean isOwnedByCurrentThread(Key worldName, ChunkPos pos);

    List<? extends RegionTps> tpsSnapshots();
}
