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
            WorldPaths paths,
            LevelData levelData,
            ChunkStorage storage,
            EntityRegionStorage entityStorage,
            AnvilEntitySerializer entitySerializer,
            BlockStateRegistry blockStates,
            int minY,
            int height
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

    public static WorldManager openOrCreate(Path worldRoot, BlockStateRegistry blockStates, int minY, int height)
            throws IOException {
        WorldPaths paths = new WorldPaths(worldRoot, WorldPaths.Layout.MODERN);

        LevelData levelData;
        if (paths.levelDat().toFile().isFile()) {
            levelData = LevelData.read(paths.levelDat());
            LOGGER.info("Monde chargé : {} (DataVersion {})", levelData.levelName, levelData.dataVersion);
        } else {
            levelData = new LevelData();
            levelData.write(paths.levelDat());
            LOGGER.info("Nouveau monde créé dans {}", worldRoot);
        }

        AnvilChunkSerializer serializer = new AnvilChunkSerializer();
        ChunkStorage storage = new ChunkStorage(paths, serializer, minY, height, BlockState.AIR, "minecraft:plains");

        EntityRegionStorage entityStorage = new EntityRegionStorage(paths);
        AnvilEntitySerializer entitySerializer = new AnvilEntitySerializer();

        return new WorldManager(paths, levelData, storage, entityStorage, entitySerializer, blockStates, minY, height);
    }

    public ServerWorld registerDimension(Dimension dim, ChunkGenerator generator) {
        return worlds.computeIfAbsent(dim.id(), k -> {
            ServerWorld world = new ServerWorld(
                    dim, storage, entityStorage, entitySerializer, generator, blockStates, minY, height);
            if (chunkLoader != null) {
                world.setChunkLoader(chunkLoader);
            }
            if (entityIdSupplier != null && entityBridge != null) {
                world.setEntityBridge(entityIdSupplier, entityBridge);
            }
            return world;
        });
    }

    public void setEntityBridge(IntSupplier idSupplier, EntitySpawnBridge bridge) {
        this.entityIdSupplier = idSupplier;
        this.entityBridge = bridge;
        for (ServerWorld world : worlds.values()) {
            world.setEntityBridge(idSupplier, bridge);
        }
    }

    public void setChunkLoader(AsyncChunkLoader loader) {
        this.chunkLoader = loader;
        for (ServerWorld world : worlds.values()) {
            world.setChunkLoader(loader);
        }
    }

    public void setDefaultGenerator(ChunkGenerator generator) {
        this.defaultGenerator = generator;
    }

    public ServerWorld overworld() {
        ChunkGenerator chunkGenerator = defaultGenerator;
        ChunkGenerator generator =
                chunkGenerator != null ? chunkGenerator : FlatChunkGenerator.cobblestone(minY, height);
        return registerDimension(Dimension.OVERWORLD, generator);
    }

    public Collection<ServerWorld> worlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public ServerWorld dimension(Dimension dim) {
        return worlds.get(dim.id());
    }

    public LevelData levelData() {
        return levelData;
    }

    public WorldPaths paths() {
        return paths;
    }

    public void saveAll() throws IOException {
        levelData.write(paths.levelDat());
        for (ServerWorld w : worlds.values()) {
            w.saveAll();
        }
        LOGGER.info("World saved ({} dimension(s))", worlds.size());
    }

    public int unloadUnusedChunks() {
        int total = 0;
        for (ServerWorld w : worlds.values()) {
            total += w.unloadUnusedChunks();
        }
        return total;
    }

    public void saveDirty() throws IOException {
        for (ServerWorld w : worlds.values()) {
            w.saveDirty();
        }
    }

    @Override
    public void close() throws IOException {
        saveAll();
        storage.close();
    }
}
