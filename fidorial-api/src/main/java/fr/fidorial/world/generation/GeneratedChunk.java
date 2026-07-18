package fr.fidorial.world.generation;

import net.kyori.adventure.key.Key;

public interface GeneratedChunk {

    int chunkX();

    int chunkZ();

    int minY();

    int height();

    void setBlock(int x, int y, int z, Key block);

    void setBiome(int x, int y, int z, Key biome);
}
