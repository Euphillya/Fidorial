package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class World {

    private final Dimension dimension;
    private final ChunkStorage storage;
    private final ChunkGenerator generator;

    private final Map<Long, ChunkColumn> loaded = new ConcurrentHashMap<>();
    private final Set<Long> dirty = ConcurrentHashMap.newKeySet();

    public World(Dimension dimension, ChunkStorage storage, ChunkGenerator generator) {
        this.dimension = dimension;
        this.storage = storage;
        this.generator = generator;
    }

    private static long key(int chunkX, int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

    public Dimension dimension() {
        return dimension;
    }

    public ChunkColumn getChunk(int chunkX, int chunkZ) throws IOException {
        long k = key(chunkX, chunkZ);
        ChunkColumn cached = loaded.get(k);
        if (cached != null) return cached;

        ChunkColumn fromDisk = storage.load(dimension, chunkX, chunkZ);
        if (fromDisk != null) {
            loaded.put(k, fromDisk);
            return fromDisk;
        }

        ChunkColumn generated = generator.generate(chunkX, chunkZ);
        loaded.put(k, generated);
        dirty.add(k); // un chunk fraîchement généré doit être écrit au moins une fois
        return generated;
    }

    public void markDirty(int chunkX, int chunkZ) {
        dirty.add(key(chunkX, chunkZ));
    }

    public void saveDirty() throws IOException {
        for (Long k : Set.copyOf(dirty)) {
            ChunkColumn chunk = loaded.get(k);
            if (chunk != null) {
                storage.save(dimension, chunk);
            }
            dirty.remove(k);
        }
    }

    public void saveAll() throws IOException {
        for (ChunkColumn chunk : loaded.values()) {
            storage.save(dimension, chunk);
        }
        dirty.clear();
    }

    public void unloadChunk(int chunkX, int chunkZ) throws IOException {
        long k = key(chunkX, chunkZ);
        if (dirty.remove(k)) {
            ChunkColumn chunk = loaded.get(k);
            if (chunk != null) storage.save(dimension, chunk);
        }
        loaded.remove(k);
    }

    public int loadedCount() {
        return loaded.size();
    }
}
