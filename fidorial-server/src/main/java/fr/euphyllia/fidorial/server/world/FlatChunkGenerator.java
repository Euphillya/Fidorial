package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;

public final class FlatChunkGenerator implements ChunkGenerator {

    private final int minY;
    private final int height;
    private final BlockState floor;
    private final String biome;
    private final int floorThickness;

    public FlatChunkGenerator(int minY, int height, BlockState floor, String biome, int floorThickness) {
        this.minY = minY;
        this.height = height;
        this.floor = floor;
        this.biome = biome;
        this.floorThickness = floorThickness;
    }

    public static FlatChunkGenerator cobblestone(int minY, int height) {
        return new FlatChunkGenerator(minY, height,
                BlockState.of("minecraft:cobblestone"), "minecraft:plains", 16);
    }

    @Override
    public ChunkColumn generate(int chunkX, int chunkZ) {
        ChunkColumn chunk = new ChunkColumn(chunkX, chunkZ, minY, height, BlockState.AIR, biome);
        for (int y = minY; y < minY + floorThickness; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    chunk.setBlock(x, y, z, floor);
                }
            }
        }
        return chunk;
    }
}
