package fr.euphyllia.fidorial.api.world;

public record BlockPos(int x, int y, int z) {

    public BlockPos relative(BlockFace face) {
        return new BlockPos(x + face.dx(), y + face.dy(), z + face.dz());
    }

    public BlockPos offset(int dx, int dy, int dz) {
        return new BlockPos(x + dx, y + dy, z + dz);
    }

    public int chunkX() {
        return x >> 4;
    }

    public int chunkZ() {
        return z >> 4;
    }

    public int localX() {
        return x & 15;
    }

    public int localZ() {
        return z & 15;
    }

    public ChunkPos chunk() {
        return new ChunkPos(x >> 4, z >> 4);
    }
}
