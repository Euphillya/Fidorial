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
        if (column.lightPopulated()) {
            return false;
        }
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
