package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.storage.ChunkStorage;
import fr.euphyllia.fidorial.server.world.storage.Dimension;
import fr.euphyllia.fidorial.server.world.storage.LevelData;
import fr.euphyllia.fidorial.server.world.storage.WorldPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WorldManager implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldManager.class);

    private final WorldPaths paths;
    private final LevelData levelData;
    private final ChunkStorage storage;
    private final Map<String, World> worlds = new ConcurrentHashMap<>();
    private final int minY;
    private final int height;

    private WorldManager(WorldPaths paths, LevelData levelData, ChunkStorage storage,
                         int minY, int height) {
        this.paths = paths;
        this.levelData = levelData;
        this.storage = storage;
        this.minY = minY;
        this.height = height;
    }

    /**
     * Ouvre le monde s'il existe (lecture du {@code level.dat}), sinon le crée.
     *
     * @param worldRoot dossier racine du monde (ex. {@code world/})
     * @param minY      hauteur minimale (ex. -64)
     * @param height    hauteur totale (ex. 384)
     */
    public static WorldManager openOrCreate(Path worldRoot, int minY, int height) throws IOException {
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
        ChunkStorage storage = new ChunkStorage(paths, serializer, minY, height,
                BlockState.AIR, "minecraft:plains");

        return new WorldManager(paths, levelData, storage, minY, height);
    }

    public World registerDimension(Dimension dim, ChunkGenerator generator) {
        return worlds.computeIfAbsent(dim.id(), k -> new World(dim, storage, generator));
    }

    public World overworld() {
        return worlds.computeIfAbsent(Dimension.OVERWORLD.id(), k -> new World(
                Dimension.OVERWORLD, storage, FlatChunkGenerator.cobblestone(minY, height)));
    }

    public World dimension(Dimension dim) {
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
        for (World w : worlds.values()) {
            w.saveAll();
        }
        LOGGER.info("Monde sauvegardé ({} dimension(s))", worlds.size());
    }

    public void saveDirty() throws IOException {
        for (World w : worlds.values()) {
            w.saveDirty();
        }
    }

    @Override
    public void close() throws IOException {
        saveAll();
        storage.close();
    }
}
