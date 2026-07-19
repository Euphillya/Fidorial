package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.anvil.RegionConstants;
import fr.euphyllia.fidorial.server.world.anvil.RegionFile;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityRegionStorage implements AutoCloseable {

    private final WorldPaths paths;
    private final Map<String, RegionFile> regionCache = new ConcurrentHashMap<>();

    public EntityRegionStorage(WorldPaths paths) {
        this.paths = paths;
    }

    private static String key(Dimension dim, int rx, int rz) {
        return dim.id() + "@" + rx + "," + rz;
    }

    private RegionFile region(Dimension dim, int chunkX, int chunkZ) {
        int rx = RegionConstants.chunkToRegion(chunkX);
        int rz = RegionConstants.chunkToRegion(chunkZ);
        return regionCache.computeIfAbsent(key(dim, rx, rz), k -> {
            Path file = paths.entitiesDir(dim).resolve(RegionConstants.fileName(rx, rz));
            try {
                return new RegionFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Impossible d'ouvrir le fichier entities " + file, e);
            }
        });
    }

    public boolean hasChunk(Dimension dim, int chunkX, int chunkZ) {
        RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            return rf.hasChunk(chunkX, chunkZ);
        }
    }

    public NbtCompound load(Dimension dim, int chunkX, int chunkZ) throws IOException {
        RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            if (!rf.hasChunk(chunkX, chunkZ)) {
                return null;
            }
            return rf.readChunk(chunkX, chunkZ);
        }
    }

    public void save(Dimension dim, int chunkX, int chunkZ, NbtCompound nbt) throws IOException {
        RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            rf.writeChunk(chunkX, chunkZ, nbt);
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
