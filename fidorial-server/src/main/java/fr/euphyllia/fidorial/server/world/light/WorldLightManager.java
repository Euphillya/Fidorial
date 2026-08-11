package fr.euphyllia.fidorial.server.world.light;

import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.light.LightType;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.Set;

public class WorldLightManager {

    private final LightAccess access;
    private final LightEngine engine;

    public WorldLightManager(final int minY, final int height, final LightAccess access) {
        this(access, new FloodFillLightEngine(minY, height));
    }

    public WorldLightManager(final LightAccess access, final LightEngine engine) {
        this.access = access;
        this.engine = engine;
    }

    public synchronized boolean lightChunkIfNeeded(final ChunkColumn column, final int chunkX, final int chunkZ) {
        if (access.lightAt(chunkX, chunkZ) == null) {
            return false;
        }
        engine.relight(LongSet.of(ChunkPos.chunkKey(chunkX, chunkZ)), access);
        column.setLightPopulated(true);
        return true;
    }

    public synchronized Set<Long> checkBlock(final int x, final int y, final int z) {
        return engine.checkBlock(x, y, z, access);
    }

    public synchronized Set<Long> checkChunkEdges(final Set<Long> chunkKeys) {
        final LongSet dirty = new LongOpenHashSet();
        final LongSet processedPairs = new LongOpenHashSet();

        for (final long key : chunkKeys) {
            final int chunkX = (int) (key >> 32);
            final int chunkZ = (int) key;
            if (!access.isLightPopulated(chunkX, chunkZ)) {
                continue;
            }

            for (final int[] d : NEIGHBOR_OFFSETS) {
                final int nx = chunkX + d[0];
                final int nz = chunkZ + d[1];
                final long pairKey = pairKey(chunkX, chunkZ, nx, nz);
                if (!processedPairs.add(pairKey)) {
                    continue;
                }
                if (!access.isLightPopulated(nx, nz)) {
                    continue;
                }
                dirty.addAll(engine.checkChunkEdge(chunkX, chunkZ, nx, nz, access));
            }
        }
        return dirty;
    }

    private static final int[][] NEIGHBOR_OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static long pairKey(final int ax, final int az, final int bx, final int bz) {
        final long a = ChunkPos.chunkKey(ax, az);
        final long b = ChunkPos.chunkKey(bx, bz);
        final long lo = Math.min(a, b), hi = Math.max(a, b);
        return lo * 0x9E3779B97F4A7C15L ^ hi;
    }

    public synchronized Set<Long> relightChunks(final Set<Long> chunkKeys) {
        final LongSet loaded = new LongOpenHashSet();
        for (final long key : chunkKeys) {
            if (access.lightAt((int) (key >> 32), (int) key) != null) {
                loaded.add(key);
            }
        }
        engine.relight(loaded, access);
        return loaded;
    }

    public int blockLight(final int x, final int y, final int z) {
        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        return data == null ? 0 : data.get(LightType.BLOCK, x, y, z);
    }

    public int skyLight(final int x, final int y, final int z) {
        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        return data == null ? 0 : data.get(LightType.SKY, x, y, z);
    }

    public int lightLevel(final int x, final int y, final int z) {
        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        if (data == null) {
            return 0;
        }
        return Math.max(data.get(LightType.BLOCK, x, y, z), data.get(LightType.SKY, x, y, z));
    }
}
