package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.chunk.ChunkSection;
import fr.fidorial.world.generation.GeneratedChunk;
import net.kyori.adventure.key.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginGeneratedChunk implements GeneratedChunk {

    private static final Map<Key, BlockState> BLOCK_CACHE = new ConcurrentHashMap<>();

    private final ChunkColumn column;
    private final int minY;
    private final int height;

    public PluginGeneratedChunk(int chunkX, int chunkZ, int minY, int height, String defaultBiome) {
        this.column = new ChunkColumn(chunkX, chunkZ, minY, height, BlockState.AIR, defaultBiome);
        this.minY = minY;
        this.height = height;
    }

    ChunkColumn column() {
        return column;
    }

    @Override
    public int chunkX() {
        return column.chunkX();
    }

    @Override
    public int chunkZ() {
        return column.chunkZ();
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void setBlock(int x, int y, int z, Key block) {
        checkLocal(x, z);
        checkY(y);
        BlockState state = BLOCK_CACHE.computeIfAbsent(block, k -> BlockState.of(k.toString()));
        column.setBlock(x, y, z, state);
    }

    @Override
    public void setBiome(int x, int y, int z, Key biome) {
        checkLocal(x, z);
        checkY(y);
        ChunkSection section = column.sections()[(y >> 4) - column.minSectionY()];
        section.setBiome(x >> 2, (y & 15) >> 2, z >> 2, biome.toString());
    }

    private void checkLocal(int x, int z) {
        if (x < 0 || x > 15 || z < 0 || z > 15) {
            throw new IllegalArgumentException("coordonnees locales hors bornes : x=" + x + ", z=" + z);
        }
    }

    private void checkY(int y) {
        if (y < minY || y >= minY + height) {
            throw new IllegalArgumentException(
                    "y hors bornes : " + y + " (attendu [" + minY + ", " + (minY + height) + "[)");
        }
    }
}
