package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.entity.AnvilEntitySerializer;
import fr.euphyllia.fidorial.server.world.entity.EntitySpawnBridge;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;
import fr.euphyllia.fidorial.server.world.storage.EntityRegionStorage;
import fr.euphyllia.fidorial.server.world.time.WorldClocks;
import fr.euphyllia.fidorial.server.world.time.WorldTimeEngine;
import fr.fidorial.entity.Entity;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Chunk;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.World;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
    private final WorldTimeEngine dayNightCycle;
    private final int minY;
    private final int height;

    private final Map<Long, ChunkColumn> loaded = new ConcurrentHashMap<>();
    private final Set<Long> dirty = ConcurrentHashMap.newKeySet();
    private final Set<Long> entitiesLoaded = ConcurrentHashMap.newKeySet();
    private final Set<ChunkViewSource> viewers = ConcurrentHashMap.newKeySet();
    private volatile @Nullable AsyncChunkLoader chunkLoader;
    private volatile @Nullable IntSupplier entityIdSupplier;
    private volatile @Nullable EntitySpawnBridge entityBridge;
    private @Nullable Iterable<? extends Audience> adventure$audiences;

    public ServerWorld(
            final Dimension dimension,
            final ChunkStorage storage,
            final EntityRegionStorage entityStorage,
            final AnvilEntitySerializer entitySerializer,
            final ChunkGenerator generator,
            final BlockStateRegistry blockStates,
            final int minY,
            final int height
    ) {
        this.dimension = dimension;
        this.storage = storage;
        this.entityStorage = entityStorage;
        this.entitySerializer = entitySerializer;
        this.generator = generator;
        this.blockStates = blockStates;
        this.dayNightCycle = new WorldTimeEngine(WorldClocks.forDimension(dimension));
        this.minY = minY;
        this.height = height;
    }

    public void setEntityBridge(final IntSupplier entityIdSupplier, final EntitySpawnBridge entityBridge) {
        this.entityIdSupplier = entityIdSupplier;
        this.entityBridge = entityBridge;
    }

    public static long chunkKey(final int chunkX, final int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

    private static long key(final int chunkX, final int chunkZ) {
        return chunkKey(chunkX, chunkZ);
    }

    public Dimension dimension() {
        return dimension;
    }

    public EntityManager entityManager() {
        return entities;
    }

    @Override
    public WorldTimeEngine dayNightCycle() {
        return dayNightCycle;
    }

    @Override
    public Key key() {
        return dimension.id();
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int height() {
        return height;
    }

    public void setChunkLoader(final AsyncChunkLoader loader) {
        this.chunkLoader = loader;
    }

    @Override
    public CompletableFuture<Chunk> getChunkAsync(final int chunkX, final int chunkZ) {
        final ChunkColumn cached = loaded.get(key(chunkX, chunkZ));
        if (cached != null) {
            return CompletableFuture.completedFuture(wrap(cached));
        }
        final AsyncChunkLoader loader = this.chunkLoader;
        if (loader == null) {
            try {
                return CompletableFuture.completedFuture(wrap(getChunk(chunkX, chunkZ)));
            } catch (final IOException e) {
                return CompletableFuture.failedFuture(e);
            }
        }
        return loader.loadAsync(this, chunkX, chunkZ).thenApply(this::wrap);
    }

    @Override
    public Optional<Chunk> getChunkIfLoaded(final int chunkX, final int chunkZ) {
        final ChunkColumn cached = loaded.get(key(chunkX, chunkZ));
        return Optional.ofNullable(cached).map(this::wrap);
    }

    private ServerChunk wrap(final ChunkColumn column) {
        return new ServerChunk(this, column, blockStates);
    }

    @Override
    public int getBlockStateId(final BlockPos pos) {
        try {
            return blockStates.networkId(getBlock(pos.x(), pos.y(), pos.z()));
        } catch (final IOException e) {
            throw new UncheckedIOException("Lecture du bloc " + pos + " impossible", e);
        }
    }

    @Override
    public boolean setBlockStateId(final BlockPos pos, final int stateId) {
        try {
            return setBlock(pos.x(), pos.y(), pos.z(), blockStates.byId(stateId));
        } catch (final IOException e) {
            throw new UncheckedIOException("Ecriture du bloc " + pos + " impossible", e);
        }
    }

    @Override
    public Collection<? extends Entity> entities() {
        return entities.all();
    }

    @Override
    public Entity entity(final UUID uuid) {
        return entities.byUuid(uuid);
    }

    @Override
    public Entity entity(final int entityId) {
        return entities.byId(entityId);
    }

    public void addEntity(final AbstractEntity entity) {
        entities.add(entity);
        invalidateAudiences();
    }

    public void removeEntity(final AbstractEntity entity) {
        entities.remove(entity);
        invalidateAudiences();
    }

    public ChunkColumn getChunk(final int chunkX, final int chunkZ) throws IOException {
        final long k = key(chunkX, chunkZ);
        final ChunkColumn cached = loaded.get(k);
        if (cached != null) {
            return cached;
        }
        final ChunkColumn column;
        try {
            column = loaded.computeIfAbsent(k, ignored -> {
                try {
                    final ChunkColumn fromDisk = storage.load(dimension, chunkX, chunkZ);
                    if (fromDisk != null) {
                        return fromDisk;
                    }
                    // Un chunk fraichement genere doit etre ecrit au moins une fois.
                    dirty.add(k);
                    return generator.generate(chunkX, chunkZ);
                } catch (final IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (final UncheckedIOException e) {
            throw e.getCause();
        }

        ensureEntitiesLoaded(chunkX, chunkZ);
        return column;
    }

    private void ensureEntitiesLoaded(final int chunkX, final int chunkZ) {
        final long k = key(chunkX, chunkZ);
        if (!entitiesLoaded.add(k)) {
            return;
        }
        final IntSupplier idSupplier = this.entityIdSupplier;
        if (idSupplier == null) {
            entitiesLoaded.remove(k);
            return;
        }
        try {
            final NbtCompound root = entityStorage.load(dimension, chunkX, chunkZ);
            if (root == null) {
                return;
            }
            final List<AbstractEntity> restored = entitySerializer.fromChunkNbt(root, this, idSupplier);
            final EntitySpawnBridge bridge = this.entityBridge;
            for (final AbstractEntity entity : restored) {
                entities.add(entity);
                if (bridge != null) {
                    bridge.onEntityAppear(entity);
                }
            }
            invalidateAudiences();
        } catch (final IOException e) {
            entitiesLoaded.remove(k);
            throw new UncheckedIOException("Unable to load entities for chunk " + chunkX + "," + chunkZ, e);
        }
    }

    private void saveChunkEntities(final int chunkX, final int chunkZ) throws IOException {
        final List<AbstractEntity> inChunk = persistableEntities(chunkX, chunkZ);
        if (inChunk.isEmpty() && !entityStorage.hasChunk(dimension, chunkX, chunkZ)) {
            return;
        }
        final NbtCompound root = entitySerializer.toChunkNbt(chunkX, chunkZ, inChunk);
        entityStorage.save(dimension, chunkX, chunkZ, root);
    }

    private List<AbstractEntity> persistableEntities(final int chunkX, final int chunkZ) {
        final List<AbstractEntity> result = new ArrayList<>();
        for (final AbstractEntity entity : entities.inChunk(new ChunkPos(chunkX, chunkZ))) {
            if (AnvilEntitySerializer.isPersistable(entity)) {
                result.add(entity);
            }
        }
        return result;
    }

    private void unloadChunkEntities(final int chunkX, final int chunkZ) throws IOException {
        final long k = key(chunkX, chunkZ);
        if (!entitiesLoaded.remove(k)) {
            return;
        }
        saveChunkEntities(chunkX, chunkZ);
        final EntitySpawnBridge bridge = this.entityBridge;
        for (final AbstractEntity entity : persistableEntities(chunkX, chunkZ)) {
            entities.remove(entity);
            if (bridge != null) {
                bridge.onEntityDisappear(entity);
            }
        }
        invalidateAudiences();
    }

    public void markDirty(final int chunkX, final int chunkZ) {
        dirty.add(key(chunkX, chunkZ));
    }

    public boolean setBlock(final int x, final int y, final int z, final BlockState state) throws IOException {
        final ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return false;
        }
        column.setBlock(x & 15, y, z & 15, state);
        markDirty(x >> 4, z >> 4);
        return true;
    }

    public BlockState getBlock(final int x, final int y, final int z) throws IOException {
        final ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return BlockState.AIR;
        }
        return column.getBlock(x & 15, y, z & 15);
    }

    public void saveDirty() throws IOException {
        for (final Long k : Set.copyOf(dirty)) {
            final ChunkColumn chunk = loaded.get(k);
            if (chunk != null) {
                storage.save(dimension, chunk);
            }
            dirty.remove(k);
        }
        saveLoadedEntities();
    }

    public void saveAll() throws IOException {
        for (final ChunkColumn chunk : loaded.values()) {
            storage.save(dimension, chunk);
        }
        dirty.clear();
        saveLoadedEntities();
    }

    // Todo : It will be very resource-intensive if there are many entities; this method will need to be modified.
    private void saveLoadedEntities() throws IOException {
        for (final Long k : Set.copyOf(entitiesLoaded)) {
            final int cx = (int) (k >> 32);
            final int cz = (int) (long) k;
            saveChunkEntities(cx, cz);
        }
    }

    public void addViewer(final ChunkViewSource viewer) {
        viewers.add(viewer);
    }

    public void removeViewer(final ChunkViewSource viewer) {
        viewers.remove(viewer);
    }

    public int unloadUnusedChunks() {
        if (loaded.isEmpty()) {
            return 0;
        }
        final Set<Long> wanted = new HashSet<>();
        for (final ChunkViewSource viewer : viewers) {
            viewer.collectViewedChunks(wanted::add);
        }

        int unloaded = 0;
        for (final Long k : loaded.keySet()) {
            if (wanted.contains(k) || dirty.contains(k)) {
                continue;
            }
            if (loaded.remove(k) != null) {
                unloaded++;
                final int cx = (int) (k >> 32);
                final int cz = (int) (long) k;
                try {
                    unloadChunkEntities(cx, cz);
                } catch (final IOException exception) {
                    throw new UncheckedIOException(
                            "Unloading entities from chunk " + cx + "," + cz + "failed.", exception);
                }
            }
        }
        return unloaded;
    }

    public void unloadChunk(final int chunkX, final int chunkZ) throws IOException {
        final long k = key(chunkX, chunkZ);
        if (dirty.remove(k)) {
            final ChunkColumn chunk = loaded.get(k);
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
    public CompletableFuture<Boolean> unloadChunkAsync(final int chunkX, final int chunkZ) {
        final long k = key(chunkX, chunkZ);
        if (!loaded.containsKey(k)) {
            return CompletableFuture.completedFuture(false);
        }

        final Set<Long> wanted = new HashSet<>();
        for (final ChunkViewSource viewer : viewers) {
            viewer.collectViewedChunks(wanted::add);
        }
        if (wanted.contains(k)) {
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                unloadChunk(chunkX, chunkZ);
                return true;
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
