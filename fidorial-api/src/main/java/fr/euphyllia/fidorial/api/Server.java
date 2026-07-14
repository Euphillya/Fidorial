package fr.euphyllia.fidorial.api;

import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.api.world.fluid.FluidManager;

public interface Server {

    String minecraftVersion();

    int protocolVersion();

    RegionizedScheduler scheduler();

    void shutdown();

    boolean isRunning();

    FluidManager fluids();

}
