package fr.euphyllia.fidorial.server.world.chunk;

import org.jspecify.annotations.Nullable;

public final class ChunkColumn {

    private final int chunkX;
    private final int chunkZ;
    private final int minY;
    private final int height;
    private final int minSectionY;
    private final int sectionCount;
    private final ChunkSection[] sections;

    private long inhabitedTime;
    private long lastUpdate;
    private String status = "minecraft:full";

    public ChunkColumn(int chunkX, int chunkZ, int minY, int height,
                       BlockState fillBlock, String fillBiome) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.minY = minY;
        this.height = height;
        this.minSectionY = minY >> 4;
        this.sectionCount = height >> 4;
        this.sections = new ChunkSection[sectionCount];
        for (int i = 0; i < sectionCount; i++) {
            sections[i] = new ChunkSection(minSectionY + i, fillBlock, fillBiome);
        }
    }

    public int chunkX() {
        return chunkX;
    }

    public int chunkZ() {
        return chunkZ;
    }

    public int minY() {
        return minY;
    }

    public int height() {
        return height;
    }

    public int minSectionY() {
        return minSectionY;
    }

    public int sectionCount() {
        return sectionCount;
    }

    public ChunkSection[] sections() {
        return sections;
    }

    public void putSection(ChunkSection s) {
        int idx = s.sectionY() - minSectionY;
        if (idx >= 0 && idx < sectionCount) {
            sections[idx] = s;
        }
    }

    public long inhabitedTime() {
        return inhabitedTime;
    }

    public void setInhabitedTime(long t) {
        this.inhabitedTime = t;
    }

    public long lastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long t) {
        this.lastUpdate = t;
    }

    public String status() {
        return status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    private @Nullable ChunkSection sectionForY(int worldY) {
        int idx = (worldY >> 4) - minSectionY;
        if (idx < 0 || idx >= sectionCount) return null;
        return sections[idx];
    }


    public void setBlock(int localX, int worldY, int localZ, BlockState state) {
        ChunkSection s = sectionForY(worldY);
        if (s != null) {
            s.setBlock(localX, worldY & 15, localZ, state);
        }
    }

    public BlockState getBlock(int localX, int worldY, int localZ) {
        ChunkSection s = sectionForY(worldY);
        return s == null ? BlockState.AIR : s.getBlock(localX, worldY & 15, localZ);
    }

    public long[] computeHeightmap(java.util.function.Predicate<BlockState> solid) {
        int bits = BitPacking.bitsFor(height + 1, 1);
        int[] values = new int[256];
        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                int top = minY;
                for (int worldY = minY + height - 1; worldY >= minY; worldY--) {
                    if (solid.test(getBlock(x, worldY, z))) {
                        top = worldY + 1;
                        break;
                    }
                }
                values[z * 16 + x] = top - minY;
            }
        }
        return BitPacking.pack(values, bits);
    }
}
