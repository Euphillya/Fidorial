package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.entity.AnvilEntitySerializer;
import fr.euphyllia.fidorial.server.world.entity.EntitySpawnBridge;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.storage.EntityRegionStorage;
import fr.fidorial.entity.Entity;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Chunk;
import fr.fidorial.world.ChunkPos;
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
import java.util.function.IntSupplier;

public final class ServerWorld implements World {

    private final Dimension dimension;
    private final ChunkStorage storage;
    private final EntityRegionStorage entityStorage;
    private final AnvilEntitySerializer entitySerializer;
    private final ChunkGenerator generator;
    private final BlockStateRegistry blockStates;
    private final EntityManager entities = new EntityManager();
    private final int minY;
    private final int height;

    private final Map<Long, ChunkColumn> loaded = new ConcurrentHashMap<>();
    private final Set<Long> dirty = ConcurrentHashMap.newKeySet();
    private final Set<Long> entitiesLoaded = ConcurrentHashMap.newKeySet();
    private final Set<ChunkViewSource> viewers = ConcurrentHashMap.newKeySet();
    private volatile AsyncChunkLoader chunkLoader;
    private volatile IntSupplier entityIdSupplier;
    private volatile EntitySpawnBridge entityBridge;
    private Iterable<? extends Audience> adventure$audiences;

    public ServerWorld(Dimension dimension, ChunkStorage storage,
                       EntityRegionStorage entityStorage, AnvilEntitySerializer entitySerializer,
                       ChunkGenerator generator,
                       BlockStateRegistry blockStates, int minY, int height) {
        this.dimension = dimension;
        this.storage = storage;
        this.entityStorage = entityStorage;
        this.entitySerializer = entitySerializer;
        this.generator = generator;
        this.blockStates = blockStates;
        this.minY = minY;
        this.height = height;
    }

    public void setEntityBridge(IntSupplier entityIdSupplier, EntitySpawnBridge entityBridge) {
        this.entityIdSupplier = entityIdSupplier;
        this.entityBridge = entityBridge;
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
        ChunkColumn column;
        try {
            column =  loaded.computeIfAbsent(k, ignored -> {
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

        ensureEntitiesLoaded(chunkX, chunkZ);
        return column;
    }

    private void ensureEntitiesLoaded(int chunkX, int chunkZ) {
        long k = key(chunkX, chunkZ);
        if (!entitiesLoaded.add(k)) {
            return;
        }
        IntSupplier idSupplier = this.entityIdSupplier;
        if (idSupplier == null) {
            entitiesLoaded.remove(k);
            return;
        }
        try {
            NbtCompound root = entityStorage.load(dimension, chunkX, chunkZ);
            if (root == null) {
                return;
            }
            List<AbstractEntity> restored = entitySerializer.fromChunkNbt(root, this, idSupplier);
            EntitySpawnBridge bridge = this.entityBridge;
            for (AbstractEntity entity : restored) {
                entities.add(entity);
                if (bridge != null) {
                    bridge.onEntityAppear(entity);
                }
            }
            invalidateAudiences();
        } catch (IOException e) {
            entitiesLoaded.remove(k);
            throw new UncheckedIOException(
                    "Unable to load entities for chunk " + chunkX + "," + chunkZ, e);
        }
    }

    private void saveChunkEntities(int chunkX, int chunkZ) throws IOException {
        List<AbstractEntity> inChunk = persistableEntities(chunkX, chunkZ);
        if (inChunk.isEmpty() && !entityStorage.hasChunk(dimension, chunkX, chunkZ)) {
            return;
        }
        NbtCompound root = entitySerializer.toChunkNbt(chunkX, chunkZ, inChunk);
        entityStorage.save(dimension, chunkX, chunkZ, root);
    }

    private List<AbstractEntity> persistableEntities(int chunkX, int chunkZ) {
        List<AbstractEntity> result = new ArrayList<>();
        for (AbstractEntity entity : entities.inChunk(new ChunkPos(chunkX, chunkZ))) {
            if (AnvilEntitySerializer.isPersistable(entity)) {
                result.add(entity);
            }
        }
        return result;
    }

    private void unloadChunkEntities(int chunkX, int chunkZ) throws IOException {
        long k = key(chunkX, chunkZ);
        if (!entitiesLoaded.remove(k)) {
            return;
        }
        saveChunkEntities(chunkX, chunkZ);
        EntitySpawnBridge bridge = this.entityBridge;
        for (AbstractEntity entity : persistableEntities(chunkX, chunkZ)) {
            entities.remove(entity);
            if (bridge != null) {
                bridge.onEntityDisappear(entity);
            }
        }
        invalidateAudiences();
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
        saveLoadedEntities();
    }

    public void saveAll() throws IOException {
        for (ChunkColumn chunk : loaded.values()) {
            storage.save(dimension, chunk);
        }
        dirty.clear();
        saveLoadedEntities();
    }

    // Todo : It will be very resource-intensive if there are many entities; this method will need to be modified.
    private void saveLoadedEntities() throws IOException {
        for (Long k : Set.copyOf(entitiesLoaded)) {
            int cx = (int) (k >> 32);
            int cz = (int) (long) k;
            saveChunkEntities(cx, cz);
        }
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
                int cx = (int) (k >> 32);
                int cz = (int) (long) k;
                try {
                    unloadChunkEntities(cx, cz);
                } catch (IOException exception) {
                    throw new UncheckedIOException(
                            "Unloading entities from chunk " + cx + "," + cz + "failed.", exception
                    );
                }
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
        unloadChunkEntities(chunkX, chunkZ);
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
