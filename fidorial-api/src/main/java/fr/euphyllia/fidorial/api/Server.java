package fr.euphyllia.fidorial.api;

import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;

public interface Server {

    String minecraftVersion();

    int protocolVersion();

    RegionizedScheduler scheduler();

    void shutdown();

    boolean isRunning();
}
