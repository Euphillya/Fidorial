package fr.euphyllia.fidorial.server.world;

import fr.fidorial.entity.Entity;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Chunk;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    private final Set<ChunkViewSource> viewers = ConcurrentHashMap.newKeySet();
    private volatile AsyncChunkLoader chunkLoader;
    private Iterable<? extends net.kyori.adventure.audience.Audience> adventure$audiences;

    public ServerWorld(Dimension dimension, ChunkStorage storage, ChunkGenerator generator,
                       BlockStateRegistry blockStates, int minY, int height) {
        this.dimension = dimension;
        this.storage = storage;
        this.generator = generator;
        this.blockStates = blockStates;
        this.minY = minY;
        this.height = height;
    }

    public static long chunkKey(int chunkX, int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

    private static long key(int chunkX, int chunkZ) {
        return chunkKey(chunkX, chunkZ);
    }

    public Dimension dimension() {
        return dimension;
    }

    public EntityManager entityManager() {
        return entities;
    }

    @Override
    public Key key() {
        return Key.key(dimension.id());
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int height() {
        return height;
    }

    public void setChunkLoader(AsyncChunkLoader loader) {
        this.chunkLoader = loader;
    }

    @Override
    public CompletableFuture<Chunk> getChunkAsync(int chunkX, int chunkZ) {
        ChunkColumn cached = loaded.get(key(chunkX, chunkZ));
        if (cached != null) {
            return CompletableFuture.completedFuture(wrap(cached));
        }
        AsyncChunkLoader loader = this.chunkLoader;
        if (loader == null) {
            try {
                return CompletableFuture.completedFuture(wrap(getChunk(chunkX, chunkZ)));
            } catch (IOException e) {
                return CompletableFuture.failedFuture(e);
            }
        }
        return loader.loadAsync(this, chunkX, chunkZ).thenApply(this::wrap);
    }

    @Override
    public Chunk getChunkIfLoaded(int chunkX, int chunkZ) {
        ChunkColumn cached = loaded.get(key(chunkX, chunkZ));
        return cached == null ? null : wrap(cached);
    }

    private ServerChunk wrap(ChunkColumn column) {
        return new ServerChunk(this, column, blockStates);
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
        invalidateAudiences();
    }

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
        invalidateAudiences();
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

    public void addViewer(ChunkViewSource viewer) {
        viewers.add(viewer);
    }

    public void removeViewer(ChunkViewSource viewer) {
        viewers.remove(viewer);
    }

    public int unloadUnusedChunks() {
        if (loaded.isEmpty()) {
            return 0;
        }
        Set<Long> wanted = new HashSet<>();
        for (ChunkViewSource viewer : viewers) {
            viewer.collectViewedChunks(wanted::add);
        }

        int unloaded = 0;
        for (Long k : loaded.keySet()) {
            if (wanted.contains(k) || dirty.contains(k)) {
                continue;
            }
            if (loaded.remove(k) != null) {
                unloaded++;
            }
        }
        return unloaded;
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

    private void invalidateAudiences() {
        adventure$audiences = null;
    }

    @Override
    public Iterable<? extends Audience> audiences() {
        if (adventure$audiences == null) {
            adventure$audiences = this.entities().stream()
                    .filter(ServerPlayer.class::isInstance)
                    .map(ServerPlayer.class::cast)
                    .toList();
        }
        return adventure$audiences;
    }

    @Override
    public CompletableFuture<Boolean> unloadChunkAsync(int chunkX, int chunkZ) {
        long k = key(chunkX, chunkZ);
        if (!loaded.containsKey(k)) {
            return CompletableFuture.completedFuture(false);
        }

        Set<Long> wanted = new HashSet<>();
        for (ChunkViewSource viewer : viewers) {
            viewer.collectViewedChunks(wanted::add);
        }
        if (wanted.contains(k)) {
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                unloadChunk(chunkX, chunkZ);
                return true;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
