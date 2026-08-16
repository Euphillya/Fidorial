package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.schedulers.LightUpdateDispatcher;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.entity.AnvilEntitySerializer;
import fr.euphyllia.fidorial.server.world.light.ChunkLightData;
import fr.euphyllia.fidorial.server.world.light.LightAccess;
import fr.euphyllia.fidorial.server.world.light.WorldLightManager;
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
import fr.fidorial.world.entity.EntitySpawnBridge;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
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
    private final WorldLightManager lightManager;
    private volatile @Nullable LightUpdateDispatcher lightDispatcher;

    private final Map<Long, ChunkColumn> loaded = new ConcurrentHashMap<>();
    private final Set<Long> dirty = ConcurrentHashMap.newKeySet();
    private final Set<Long> entitiesDirty = ConcurrentHashMap.newKeySet();
    private final Set<Long> entitiesLoaded = ConcurrentHashMap.newKeySet();
    private final Set<ChunkViewSource> viewers = ConcurrentHashMap.newKeySet();
    private volatile @Nullable AsyncChunkLoader chunkLoader;
    private volatile @Nullable IntSupplier entityIdSupplier;
    private volatile @Nullable EntitySpawnBridge entityBridge;
    private volatile @Nullable Iterable<? extends Audience> adventure$audiences;

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
        this.lightManager = new WorldLightManager(minY, height, new WorldLightAccess());
    }

    public void setEntityBridge(final IntSupplier entityIdSupplier, final EntitySpawnBridge entityBridge) {
        this.entityIdSupplier = entityIdSupplier;
        this.entityBridge = entityBridge;
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
        final ChunkColumn cached = loaded.get(ChunkPos.chunkKey(chunkX, chunkZ));
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
        final ChunkColumn cached = loaded.get(ChunkPos.chunkKey(chunkX, chunkZ));
        return Optional.ofNullable(cached).map(this::wrap);
    }

    @Override
    public Optional<Key> blockKeyAt(final BlockPos pos) {
        try {
            final BlockState state = getBlock(pos.x(), pos.y(), pos.z());
            if (state.isAir()) {
                return Optional.empty();
            }
            return Optional.of(state.name());
        } catch (final IOException e) {
            throw new UncheckedIOException("Unable to read block at " + pos, e);
        }
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
    public int blockLight(final BlockPos pos) {
        return blockLightAt(pos.x(), pos.y(), pos.z());
    }

    @Override
    public int skyLight(final BlockPos pos) {
        return skyLightAt(pos.x(), pos.y(), pos.z());
    }

    @Override
    public int lightLevel(final BlockPos pos) {
        return lightLevelAt(pos.x(), pos.y(), pos.z());
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
        markEntitiesDirty(entity.chunk().x(), entity.chunk().z());
        if (entity instanceof ServerPlayer) {
            invalidateAudiences();
        }
    }

    public void removeEntity(final AbstractEntity entity) {
        entities.remove(entity);
        markEntitiesDirty(entity.chunk().x(), entity.chunk().z());
        if (entity instanceof ServerPlayer) {
            invalidateAudiences();
        }
    }

    public ChunkColumn getChunk(final int chunkX, final int chunkZ) throws IOException {
        final long k = ChunkPos.chunkKey(chunkX, chunkZ);
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
        ensureLight(column, chunkX, chunkZ);
        return column;
    }

    private void ensureLight(final ChunkColumn column, final int chunkX, final int chunkZ) {
        if (column.lightPopulated()) {
            return;
        }

        lightManager.lightChunkIfNeeded(column, chunkX, chunkZ);
        final LightUpdateDispatcher dispatcher = lightDispatcher;
        if (dispatcher != null) {
            dispatcher.queueChunkLoad(dimension.id(), chunkX, chunkZ);
        }
    }

    private void ensureEntitiesLoaded(final int chunkX, final int chunkZ) {
        final long k = ChunkPos.chunkKey(chunkX, chunkZ);
        if (!entitiesLoaded.add(k)) {
            return;
        }
        final IntSupplier idSupplier = this.entityIdSupplier;
        if (idSupplier == null) {
            entitiesLoaded.remove(k);
            return;
        }
        try {
            final CompoundBinaryTag root = entityStorage.load(dimension, chunkX, chunkZ);
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
        } catch (final IOException e) {
            entitiesLoaded.remove(k);
            throw new UncheckedIOException("Unable to load entities for chunk " + chunkX + "," + chunkZ, e);
        }
    }

    private void saveChunkEntities(final int chunkX, final int chunkZ, final List<AbstractEntity> inChunk) throws IOException {
        if (inChunk.isEmpty() && !entityStorage.hasChunk(dimension, chunkX, chunkZ)) {
            return;
        }
        final CompoundBinaryTag root = entitySerializer.toChunkNbt(chunkX, chunkZ, inChunk);
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

    private Long2ObjectOpenHashMap<List<AbstractEntity>> bucketPersistableEntities() {
        final Long2ObjectOpenHashMap<List<AbstractEntity>> byChunk = new Long2ObjectOpenHashMap<>();
        for (final AbstractEntity entity : entities.all()) {
            if (!AnvilEntitySerializer.isPersistable(entity)) {
                continue;
            }
            final ChunkPos pos = entity.chunk();
            final long key = ChunkPos.chunkKey(pos.x(), pos.z());
            byChunk.computeIfAbsent(key, _ -> new ObjectArrayList<>()).add(entity);
        }
        return byChunk;
    }

    private void unloadChunkEntities(final int chunkX, final int chunkZ) throws IOException {
        final long k = ChunkPos.chunkKey(chunkX, chunkZ);
        if (!entitiesLoaded.remove(k)) {
            return;
        }
        final List<AbstractEntity> inChunk = persistableEntities(chunkX, chunkZ);
        saveChunkEntities(chunkX, chunkZ, inChunk);
        entitiesDirty.remove(k);

        final EntitySpawnBridge bridge = this.entityBridge;
        for (final AbstractEntity entity : inChunk) {
            entities.remove(entity);

            if (entity instanceof ServerPlayer) {
                invalidateAudiences();
            }

            if (bridge != null) {
                bridge.onEntityDisappear(entity);
            }
        }
    }

    public void markDirty(final int chunkX, final int chunkZ) {
        dirty.add(ChunkPos.chunkKey(chunkX, chunkZ));
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

    public boolean setBiome(final int x, final int y, final int z, final Key biome) throws IOException {
        final ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return false;
        }
        if (!column.setBiome(x & 15, y, z & 15, biome)) {
            return false;
        }
        markDirty(x >> 4, z >> 4);
        return true;
    }

    public @Nullable Key getBiome(final int x, final int y, final int z) throws IOException {
        final ChunkColumn column = getChunk(x >> 4, z >> 4);
        if (y < column.minY() || y >= column.minY() + column.height()) {
            return null;
        }
        return column.getBiome(x & 15, y, z & 15);
    }

    public void saveDirty() throws IOException {
        awaitLightFlush(dirty);
        for (final long k : Set.copyOf(dirty)) {
            final ChunkColumn chunk = loaded.get(k);
            if (chunk != null) {
                storage.save(dimension, chunk);
            }
            dirty.remove(k);
        }
        saveLoadedEntities();
    }

    public void saveAll() throws IOException {
        awaitLightFlush(loaded.keySet());
        for (final ChunkColumn chunk : loaded.values()) {
            storage.save(dimension, chunk);
        }
        dirty.clear();
        saveLoadedEntities();
    }

    public void markEntitiesDirty(final int chunkX, final int chunkZ) {
        entitiesDirty.add(ChunkPos.chunkKey(chunkX, chunkZ));
    }

    private void saveLoadedEntities() throws IOException {
        if (entitiesDirty.isEmpty()) {
            return;
        }
        final Long2ObjectOpenHashMap<List<AbstractEntity>> byChunk = bucketPersistableEntities();

        for (final long k : Set.copyOf(entitiesDirty)) {
            if (!entitiesLoaded.contains(k)) {
                entitiesDirty.remove(k);
                continue;
            }
            final int cx = (int) (k >> 32);
            final int cz = (int) k;
            final List<AbstractEntity> inChunk = byChunk.getOrDefault(k, List.of());
            saveChunkEntities(cx, cz, inChunk);
            entitiesDirty.remove(k);
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
        final LongSet wanted = new LongOpenHashSet();
        for (final ChunkViewSource viewer : viewers) {
            viewer.collectViewedChunks(wanted::add);
        }

        final LongSet toUnload = new LongOpenHashSet();
        for (final long k : loaded.keySet()) {
            if (!wanted.contains(k) && !dirty.contains(k)) {
                toUnload.add(k);
            }
        }
        if (toUnload.isEmpty()) {
            return 0;
        }
        awaitLightFlush(toUnload);

        int unloaded = 0;
        for (final long k : toUnload) {
            if (loaded.remove(k) != null) {
                unloaded++;
                final int cx = (int) (k >> 32);
                final int cz = (int) k;
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
        awaitLightFlush(LongSet.of(ChunkPos.chunkKey(chunkX, chunkZ)));
        final long k = ChunkPos.chunkKey(chunkX, chunkZ);
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
        Iterable<? extends Audience> audiences = adventure$audiences;
        if (audiences == null) {
            audiences = this.entities().stream()
                    .filter(ServerPlayer.class::isInstance)
                    .map(ServerPlayer.class::cast)
                    .toList();
            adventure$audiences = audiences;
        }
        return audiences;
    }

    @Override
    public CompletableFuture<Boolean> unloadChunkAsync(final int chunkX, final int chunkZ) {
        final long k = ChunkPos.chunkKey(chunkX, chunkZ);
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

    public @Nullable ChunkColumn loadedColumn(final int chunkX, final int chunkZ) {
        return loaded.get(ChunkPos.chunkKey(chunkX, chunkZ));
    }

    public WorldLightManager lightManager() {
        return lightManager;
    }

    public void setLightDispatcher(final LightUpdateDispatcher dispatcher) {
        this.lightDispatcher = dispatcher;
    }

    private void awaitLightFlush(final Iterable<Long> chunkKeys) {
        final LightUpdateDispatcher dispatcher = lightDispatcher;
        if (dispatcher != null) {
            dispatcher.flush(dimension.id(), chunkKeys).join();
        }
    }

    public Set<Long> checkBlockLight(final int x, final int y, final int z) {
        final Set<Long> dirtyChunks = lightManager.checkBlock(x, y, z);
        dirty.addAll(dirtyChunks);
        return dirtyChunks;
    }

    public Set<Long> relightChunks(final Set<Long> chunkKeys) {
        final LongSet needsFullRelight = new LongOpenHashSet();
        final LongSet needsEdgeCheck = new LongOpenHashSet();

        for (final long key : chunkKeys) {
            final ChunkColumn column = loadedColumn((int) (key >> 32), (int) key);
            if (column == null) {
                continue;
            }
            (column.lightPopulated() ? needsEdgeCheck : needsFullRelight).add(key);
        }

        final LongSet dirty = new LongOpenHashSet();

        if (!needsFullRelight.isEmpty()) {
            dirty.addAll(lightManager.relightChunks(needsFullRelight));
            for (final long key : needsFullRelight) {
                final ChunkColumn column = loadedColumn((int) (key >> 32), (int) key);
                if (column != null) {
                    column.setLightPopulated(true);
                }
            }
        }

        if (!needsEdgeCheck.isEmpty()) {
            dirty.addAll(lightManager.checkChunkEdges(needsEdgeCheck));
        }

        this.dirty.addAll(dirty);
        return dirty;
    }

    public int blockLightAt(final int x, final int y, final int z) {
        return lightManager.blockLight(x, y, z);
    }

    public int skyLightAt(final int x, final int y, final int z) {
        return lightManager.skyLight(x, y, z);
    }

    public int lightLevelAt(final int x, final int y, final int z) {
        return lightManager.lightLevel(x, y, z);
    }

    private final class WorldLightAccess implements LightAccess {
        @Override
        public int minY() {
            return minY;
        }

        @Override
        public int height() {
            return height;
        }

        @Override
        public BlockState blockAt(final int x, final int y, final int z) {
            if (y < minY || y >= minY + height) {
                return BlockState.AIR;
            }
            final ChunkColumn column = loadedColumn(x >> 4, z >> 4);
            return column == null ? BlockState.AIR : column.getBlock(x & 15, y, z & 15);
        }

        @Override
        public @Nullable ChunkLightData lightAt(final int chunkX, final int chunkZ) {
            final ChunkColumn column = loadedColumn(chunkX, chunkZ);
            return column == null ? null : column.lightData();
        }

        @Override
        public int topNonEmptySectionY(final int chunkX, final int chunkZ) {
            final ChunkColumn column = loadedColumn(chunkX, chunkZ);
            return column == null ? (minY >> 4) - 1 : column.topNonEmptySectionY();
        }

        @Override
        public @Nullable BlockColumnAccess columnAt(final int chunkX, final int chunkZ) {
            final ChunkColumn column = loadedColumn(chunkX, chunkZ);
            if (column == null) return null;
            return (localX, worldY, localZ) ->
                    (worldY < minY || worldY >= minY + height)
                            ? BlockState.AIR
                            : column.getBlock(localX, worldY, localZ);
        }

        @Override
        public boolean isLightPopulated(final int chunkX, final int chunkZ) {
            final ChunkColumn column = loadedColumn(chunkX, chunkZ);
            if (column == null) return false;
            return column.lightPopulated();
        }
    }
}
