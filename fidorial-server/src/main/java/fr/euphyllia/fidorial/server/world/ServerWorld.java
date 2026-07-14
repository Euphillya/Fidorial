package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.api.entity.Entity;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.world.BlockPos;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ServerWorld implements World {

    private final Dimension dimension;
    private final ChunkStorage storage;
    private final ChunkGenerator generator;
    private final BlockStateRegistry blockStates;
    private final EntityManager entities = new EntityManager();
    private final int minY;
    private final int height;

    private final Map<Long, ChunkColumn> loaded = new ConcurrentHashMap<>();
    private final Set<Long> dirty = ConcurrentHashMap.newKeySet();

    public ServerWorld(Dimension dimension, ChunkStorage storage, ChunkGenerator generator,
                       BlockStateRegistry blockStates, int minY, int height) {
        this.dimension = dimension;
        this.storage = storage;
        this.generator = generator;
        this.blockStates = blockStates;
        this.minY = minY;
        this.height = height;
    }

    private static long key(int chunkX, int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

    public Dimension dimension() {
        return dimension;
    }

    public EntityManager entityManager() {
        return entities;
    }

    @Override
    public Key key() {
        return Key.parse(dimension.id());
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int getBlockStateId(BlockPos pos) {
        try {
            return blockStates.networkId(getBlock(pos.x(), pos.y(), pos.z()));
        } catch (IOException e) {
            throw new UncheckedIOException("Lecture du bloc " + pos + " impossible", e);
        }
    }

    @Override
    public boolean setBlockStateId(BlockPos pos, int stateId) {
        try {
            return setBlock(pos.x(), pos.y(), pos.z(), blockStates.byId(stateId));
        } catch (IOException e) {
            throw new UncheckedIOException("Ecriture du bloc " + pos + " impossible", e);
        }
    }

    @Override
    public Collection<? extends Entity> entities() {
        return entities.all();
    }

    @Override
    public Entity entity(UUID uuid) {
        return entities.byUuid(uuid);
    }

    @Override
    public Entity entity(int entityId) {
        return entities.byId(entityId);
    }

    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    public ChunkColumn getChunk(int chunkX, int chunkZ) throws IOException {
        long k = key(chunkX, chunkZ);
        ChunkColumn cached = loaded.get(k);
        if (cached != null) {
            return cached;
        }
        try {
            return loaded.computeIfAbsent(k, ignored -> {
                try {
                    ChunkColumn fromDisk = storage.load(dimension, chunkX, chunkZ);
                    if (fromDisk != null) {
                        return fromDisk;
                    }
                    // Un chunk fraichement genere doit etre ecrit au moins une fois.
                    dirty.add(k);
                    return generator.generate(chunkX, chunkZ);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    public void markDirty(int chunkX, int chunkZ) {
        dirty.add(key(chunkX, chunkZ));
    }

    public boolean setBlock(int x, int y, int z, BlockState state) throws IOException {
        ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return false;
        }
        column.setBlock(x & 15, y, z & 15, state);
        markDirty(x >> 4, z >> 4);
        return true;
    }

    public BlockState getBlock(int x, int y, int z) throws IOException {
        ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return BlockState.AIR;
        }
        return column.getBlock(x & 15, y, z & 15);
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
            if (chunk != null) {
                storage.save(dimension, chunk);
            }
        }
        loaded.remove(k);
    }

    public int loadedCount() {
        return loaded.size();
    }
}
