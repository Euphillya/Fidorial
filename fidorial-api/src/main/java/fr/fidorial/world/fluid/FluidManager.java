package fr.fidorial.world.fluid;

import net.kyori.adventure.key.Key;

public interface FluidManager {

    FluidState fluidAt(Key world, int x, int y, int z);

    boolean placeSource(Key world, int x, int y, int z, FluidType type);

    boolean removeFluid(Key world, int x, int y, int z);

    void scheduleUpdate(Key world, int x, int y, int z);

    void notifyBlockChanged(Key world, int x, int y, int z);
}
