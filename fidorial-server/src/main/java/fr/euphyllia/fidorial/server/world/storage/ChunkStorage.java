package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.anvil.RegionConstants;
import fr.euphyllia.fidorial.server.world.anvil.RegionFile;
import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChunkStorage implements AutoCloseable {

    private final WorldPaths paths;
    private final AnvilChunkSerializer serializer;
    private final int minY;
    private final int height;
    private final BlockState defaultBlock;
    private final String defaultBiome;

    private final Map<String, RegionFile> regionCache = new ConcurrentHashMap<>();

    public ChunkStorage(WorldPaths paths, AnvilChunkSerializer serializer,
                        int minY, int height, BlockState defaultBlock, String defaultBiome) {
        this.paths = paths;
        this.serializer = serializer;
        this.minY = minY;
        this.height = height;
        this.defaultBlock = defaultBlock;
        this.defaultBiome = defaultBiome;
    }

    private static String key(Dimension dim, int rx, int rz) {
        return dim.id() + "@" + rx + "," + rz;
    }

    private RegionFile region(Dimension dim, int chunkX, int chunkZ) {
        int rx = RegionConstants.chunkToRegion(chunkX);
        int rz = RegionConstants.chunkToRegion(chunkZ);
        return regionCache.computeIfAbsent(key(dim, rx, rz), k -> {
            Path file = paths.regionDir(dim).resolve(RegionConstants.fileName(rx, rz));
            try {
                return new RegionFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Impossible d'ouvrir le fichier région " + file, e);
            }
        });
    }

    public ChunkColumn load(Dimension dim, int chunkX, int chunkZ) throws IOException {
        RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            if (!rf.hasChunk(chunkX, chunkZ)) return null;
            NbtCompound nbt = rf.readChunk(chunkX, chunkZ);
            if (nbt == null) return null;
            return serializer.fromNbt(nbt, minY, height, defaultBlock, defaultBiome);
        }
    }

    public void save(Dimension dim, ChunkColumn chunk) throws IOException {
        chunk.setLastUpdate(System.currentTimeMillis() / 20L); // en ticks approx.
        NbtCompound nbt = serializer.toNbt(chunk);
        RegionFile rf = region(dim, chunk.chunkX(), chunk.chunkZ());
        synchronized (rf) {
            rf.writeChunk(chunk.chunkX(), chunk.chunkZ(), nbt);
        }
    }

    @Override
    public void close() {
        for (RegionFile rf : regionCache.values()) {
            synchronized (rf) {
                try {
                    rf.close();
                } catch (IOException ignored) {
                }
            }
        }
        regionCache.clear();
    }
}
