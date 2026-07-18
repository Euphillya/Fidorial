package fr.fidorial.world;

public record ChunkPos(int x, int z) {

    public static ChunkPos fromBlock(int blockX, int blockZ) {
        return new ChunkPos(blockX >> 4, blockZ >> 4);
    }
}
