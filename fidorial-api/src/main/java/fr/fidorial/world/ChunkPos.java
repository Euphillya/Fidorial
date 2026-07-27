package fr.fidorial.world;

public record ChunkPos(int x, int z) {

    public static ChunkPos fromBlock(final int blockX, final int blockZ) {
        return new ChunkPos(blockX >> 4, blockZ >> 4);
    }

    public static long chunkKey(final ChunkPos pos) {
        return chunkKey(pos.x(), pos.z());
    }
    public static long chunkKey(final int chunkX, final int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }
}
