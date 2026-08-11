package fr.euphyllia.fidorial.server.world.light;

import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.Set;

public interface LightEngine {

    Set<Long> checkBlock(int x, int y, int z, LightAccess access);

    LongSet checkChunkEdge(int chunkX, int chunkZ, int neighborChunkX, int neighborChunkZ, LightAccess access);

    void relight(LongSet chunks, LightAccess access);
}
