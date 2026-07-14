package fr.euphyllia.fidorial.api.world.fluid;

public interface FluidManager {

    FluidState fluidAt(String world, int x, int y, int z);

    boolean placeSource(String world, int x, int y, int z, FluidType type);

    boolean removeFluid(String world, int x, int y, int z);

    void scheduleUpdate(String world, int x, int y, int z);

    void notifyBlockChanged(String world, int x, int y, int z);
}
