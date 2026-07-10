package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;

public interface ChunkGenerator {

    ChunkColumn generate(int chunkX, int chunkZ);
}
