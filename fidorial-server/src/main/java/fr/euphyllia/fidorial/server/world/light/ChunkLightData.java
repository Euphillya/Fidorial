package fr.euphyllia.fidorial.server.world.light;

import fr.fidorial.world.light.LightType;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

public class ChunkLightData {
    public static final int SECTION_BYTES = 2048;

    private final int minY;
    private final int minSectionY;
    private final int sectionCount;

    private final byte[] @Nullable [] blockLight;
    private final byte[] @Nullable [] skyLight;

    private final int[] heightmap;
    private int skyFullFromY = Integer.MAX_VALUE;

    public ChunkLightData(final int minY, final int height) {
        this.minY = minY;
        this.minSectionY = minY >> 4;
        this.sectionCount = height >> 4;
        this.blockLight = new byte[sectionCount][];
        this.skyLight = new byte[sectionCount][];
        this.heightmap = new int[256];
        Arrays.fill(this.heightmap, minY - 1);
    }

    public int minY() {
        return minY;
    }

    private static int nibbleIndex(final int localX, final int localY, final int localZ) {
        return (localY << 8) | (localZ << 4) | localX;
    }

    private byte[] @Nullable [] layer(final LightType type) {
        return type == LightType.BLOCK ? blockLight : skyLight;
    }

    private int sectionIndexForY(final int worldY) {
        return (worldY >> 4) - minSectionY;
    }

    public int topOpaqueY(final int localX, final int localZ) {
        return heightmap[(localZ & 15) << 4 | (localX & 15)];
    }

    public void setTopOpaqueY(final int localX, final int localZ, final int y) {
        heightmap[(localZ & 15) << 4 | (localX & 15)] = y;
    }

    public int get(final LightType type, final int localX, final int worldY, final int localZ) {
        if (type == LightType.SKY) {
            final int top = topOpaqueY(localX & 15, localZ & 15);
            if (worldY > top) {
                return 15;
            }
        }
        final int section = sectionIndexForY(worldY);
        if (section < 0 || section >= sectionCount) {
            return 0;
        }
        final byte[] @Nullable [] layerType = layer(type);
        final byte[] data = layerType[section];
        if (data == null) {
            return 0;
        }
        final int index = nibbleIndex(localX & 15, worldY & 15, localZ & 15);
        final int b = data[index >> 1] & 0xFF;
        return (index & 1) == 0 ? (b & 0x0F) : (b >> 4);
    }

    public void set(final LightType type, final int localX, final int worldY, final int localZ, final int level) {
        final int section = sectionIndexForY(worldY);
        if (section < 0 || section >= sectionCount) {
            return;
        }
        final byte[] @Nullable [] layerType = layer(type);
        byte @Nullable [] data = layerType[section];
        if (data == null) {
            if (level == 0) {
                return;
            }
            data = new byte[SECTION_BYTES];
            layerType[section] = data;
        }
        final int index = nibbleIndex(localX & 15, worldY & 15, localZ & 15);
        final int byteIndex = index >> 1;
        final int current = data[byteIndex] & 0xFF;
        final int clamped = level & 0x0F;
        if ((index & 1) == 0) {
            data[byteIndex] = (byte) ((current & 0xF0) | clamped);
        } else {
            data[byteIndex] = (byte) ((current & 0x0F) | (clamped << 4));
        }
    }

    public byte @Nullable [] sectionArray(final LightType type, final int sectionIndex) {
        if (sectionIndex < 0 || sectionIndex >= sectionCount) {
            return null;
        }
        final byte[] @Nullable [] layerType = layer(type);
        return layerType[sectionIndex];
    }

    public void setSectionArray(final LightType type, final int sectionIndex, final byte @Nullable [] data) {
        if (sectionIndex < 0 || sectionIndex >= sectionCount) {
            return;
        }
        final byte[] @Nullable [] layerType = layer(type);
        if (data == null) {
            layerType[sectionIndex] = null;
        } else if (data.length == SECTION_BYTES) {
            layerType[sectionIndex] = data;
        }
    }

    public int skyFullFromY() {
        return skyFullFromY;
    }

    public void setSkyFullFromY(final int worldY) {
        this.skyFullFromY = worldY;
    }

    public void clear() {
        for (int i = 0; i < sectionCount; i++) {
            blockLight[i] = null;
            skyLight[i] = null;
        }
        Arrays.fill(heightmap, minY - 1);
        skyFullFromY = Integer.MAX_VALUE;
    }
}
