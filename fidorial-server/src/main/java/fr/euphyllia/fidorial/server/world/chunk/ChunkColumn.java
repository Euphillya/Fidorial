package fr.euphyllia.fidorial.server.world.chunk;

import fr.euphyllia.fidorial.server.world.light.ChunkLightData;
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

    private volatile boolean lightPopulated;
    private @Nullable ChunkLightData lightData;

    public ChunkColumn(final int chunkX, final int chunkZ, final int minY, final int height, final BlockState fillBlock, final String fillBiome) {
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

    public void putSection(final ChunkSection s) {
        final int idx = s.sectionY() - minSectionY;
        if (idx >= 0 && idx < sectionCount) {
            sections[idx] = s;
        }
    }

    public long inhabitedTime() {
        return inhabitedTime;
    }

    public void setInhabitedTime(final long t) {
        this.inhabitedTime = t;
    }

    public long lastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final long t) {
        this.lastUpdate = t;
    }

    public String status() {
        return status;
    }

    public void setStatus(final String s) {
        this.status = s;
    }

    public synchronized ChunkLightData lightData() {
        ChunkLightData data = lightData;
        if (data == null) {
            data = new ChunkLightData(minY, height);
            lightData = data;
        }
        return data;
    }

    public boolean lightPopulated() {
        return lightPopulated;
    }

    public void setLightPopulated(final boolean populated) {
        this.lightPopulated = populated;
    }

    private @Nullable ChunkSection sectionForY(final int worldY) {
        final int idx = (worldY >> 4) - minSectionY;
        if (idx < 0 || idx >= sectionCount) return null;
        return sections[idx];
    }

    public void setBlock(final int localX, final int worldY, final int localZ, final BlockState state) {
        final ChunkSection s = sectionForY(worldY);
        if (s != null) {
            s.setBlock(localX, worldY & 15, localZ, state);
        }
    }

    public BlockState getBlock(final int localX, final int worldY, final int localZ) {
        final ChunkSection s = sectionForY(worldY);
        return s == null ? BlockState.AIR : s.getBlock(localX, worldY & 15, localZ);
    }

    public long[] computeHeightmap(final java.util.function.Predicate<BlockState> solid) {
        final int bits = BitPacking.bitsFor(height + 1, 1);
        final int[] values = new int[256];
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
