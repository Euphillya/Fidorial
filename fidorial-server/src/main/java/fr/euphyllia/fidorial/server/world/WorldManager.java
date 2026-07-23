package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.entity.AnvilEntitySerializer;
import fr.euphyllia.fidorial.server.world.entity.EntitySpawnBridge;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;
import fr.euphyllia.fidorial.server.world.storage.EntityRegionStorage;
import fr.euphyllia.fidorial.server.world.storage.LevelData;
import fr.euphyllia.fidorial.server.world.storage.WorldPaths;
import fr.euphyllia.fidorial.server.world.time.WorldTimeEngine;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntSupplier;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class WorldManager implements AutoCloseable {

    private static final ComponentLogger LOGGER = getLogger(WorldManager.class);

    private final WorldPaths paths;
    private final LevelData levelData;
    private final ChunkStorage storage;
    private final EntityRegionStorage entityStorage;
    private final AnvilEntitySerializer entitySerializer;
    private final Map<String, ServerWorld> worlds = new ConcurrentHashMap<>();
    private final BlockStateRegistry blockStates;
    private final int minY;
    private final int height;
    private volatile @Nullable ChunkGenerator defaultGenerator;
    private volatile @Nullable AsyncChunkLoader chunkLoader;
    private volatile @Nullable IntSupplier entityIdSupplier;
    private volatile @Nullable EntitySpawnBridge entityBridge;

    private WorldManager(
            final WorldPaths paths,
            final LevelData levelData,
            final ChunkStorage storage,
            final EntityRegionStorage entityStorage,
            final AnvilEntitySerializer entitySerializer,
            final BlockStateRegistry blockStates,
            final int minY,
            final int height
    ) {
        this.paths = paths;
        this.levelData = levelData;
        this.storage = storage;
        this.entityStorage = entityStorage;
        this.entitySerializer = entitySerializer;
        this.blockStates = blockStates;
        this.minY = minY;
        this.height = height;
    }

    public static WorldManager openOrCreate(final Path worldRoot, final BlockStateRegistry blockStates, final int minY, final int height)
            throws IOException {
        final WorldPaths paths = new WorldPaths(worldRoot, WorldPaths.Layout.MODERN);

        final LevelData levelData;
        if (paths.levelDat().toFile().isFile()) {
            levelData = LevelData.read(paths.levelDat());
            LOGGER.info("Monde chargé : {} (DataVersion {})", levelData.levelName, levelData.dataVersion);
        } else {
            levelData = new LevelData();
            levelData.write(paths.levelDat());
            LOGGER.info("Nouveau monde créé dans {}", worldRoot);
        }

        final AnvilChunkSerializer serializer = new AnvilChunkSerializer();
        final ChunkStorage storage = new ChunkStorage(paths, serializer, minY, height, BlockState.AIR, "minecraft:plains");

        final EntityRegionStorage entityStorage = new EntityRegionStorage(paths);
        final AnvilEntitySerializer entitySerializer = new AnvilEntitySerializer();

        return new WorldManager(paths, levelData, storage, entityStorage, entitySerializer, blockStates, minY, height);
    }

    public ServerWorld registerDimension(final Dimension dim, final ChunkGenerator generator) {
        return worlds.computeIfAbsent(dim.id(), k -> {
            final ServerWorld world = new ServerWorld(
                    dim, storage, entityStorage, entitySerializer, generator, blockStates, minY, height);
            if (chunkLoader != null) {
                world.setChunkLoader(chunkLoader);
            }
            if (entityIdSupplier != null && entityBridge != null) {
                world.setEntityBridge(entityIdSupplier, entityBridge);
            }
            restoreTime(world);
            return world;
        });
    }

    private void restoreTime(final ServerWorld world) {
        final LevelData.WorldTime saved = levelData.worldTime(world.dimension().id());
        if (saved == null) {
            return;
        }
        world.dayNightCycle().restore(saved.worldAge(), saved.dayTime(), saved.doDaylightCycle());
        LOGGER.debug(
                "Cycle de {} restaure a {} ticks",
                world.dimension().id(),
                world.dayNightCycle().timeOfDay());
    }

    private void captureTimes() {
        for (final ServerWorld world : worlds.values()) {
            final WorldTimeEngine cycle = world.dayNightCycle();
            levelData.setWorldTime(
                    world.dimension().id(), cycle.worldAge(), cycle.time(), cycle.doDaylightCycle());
        }
    }

    public void setEntityBridge(final IntSupplier idSupplier, final EntitySpawnBridge bridge) {
        this.entityIdSupplier = idSupplier;
        this.entityBridge = bridge;
        for (final ServerWorld world : worlds.values()) {
            world.setEntityBridge(idSupplier, bridge);
        }
    }

    public void setChunkLoader(final AsyncChunkLoader loader) {
        this.chunkLoader = loader;
        for (final ServerWorld world : worlds.values()) {
            world.setChunkLoader(loader);
        }
    }

    public void setDefaultGenerator(final ChunkGenerator generator) {
        this.defaultGenerator = generator;
    }

    public ServerWorld overworld() {
        final ChunkGenerator chunkGenerator = defaultGenerator;
        final ChunkGenerator generator =
                chunkGenerator != null ? chunkGenerator : FlatChunkGenerator.cobblestone(minY, height);
        return registerDimension(Dimension.OVERWORLD, generator);
    }

    public Collection<ServerWorld> worlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public ServerWorld dimension(final Dimension dim) {
        return worlds.get(dim.id());
    }

    public ServerWorld world(final Key worldKey) {
        return worlds.get(worldKey.asString());
    }

    public LevelData levelData() {
        return levelData;
    }

    public WorldPaths paths() {
        return paths;
    }

    public void saveAll() throws IOException {
        captureTimes();
        levelData.write(paths.levelDat());
        for (final ServerWorld w : worlds.values()) {
            w.saveAll();
        }
        LOGGER.info("World saved ({} dimension(s))", worlds.size());
    }

    public int unloadUnusedChunks() {
        int total = 0;
        for (final ServerWorld w : worlds.values()) {
            total += w.unloadUnusedChunks();
        }
        return total;
    }

    public void saveDirty() throws IOException {
        for (final ServerWorld w : worlds.values()) {
            w.saveDirty();
        }
    }

    @Override
    public void close() throws IOException {
        saveAll();
        storage.close();
    }
}
