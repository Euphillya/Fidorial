package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.anvil.RegionConstants;
import fr.euphyllia.fidorial.server.world.anvil.RegionFile;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityRegionStorage implements AutoCloseable {

    private final WorldPaths paths;
    private final Map<String, RegionFile> regionCache = new ConcurrentHashMap<>();

    public EntityRegionStorage(final WorldPaths paths) {
        this.paths = paths;
    }

    private static String key(final Dimension dim, final int rx, final int rz) {
        return dim.id() + "@" + rx + "," + rz;
    }

    private RegionFile region(final Dimension dim, final int chunkX, final int chunkZ) {
        final int rx = RegionConstants.chunkToRegion(chunkX);
        final int rz = RegionConstants.chunkToRegion(chunkZ);
        return regionCache.computeIfAbsent(key(dim, rx, rz), k -> {
            final Path file = paths.entitiesDir(dim).resolve(RegionConstants.fileName(rx, rz));
            try {
                return new RegionFile(file);
            } catch (final IOException e) {
                throw new RuntimeException("Unable to open the entities file: " + file, e);
            }
        });
    }

    public boolean hasChunk(final Dimension dim, final int chunkX, final int chunkZ) {
        final RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            return rf.hasChunk(chunkX, chunkZ);
        }
    }

    public @Nullable NbtCompound load(final Dimension dim, final int chunkX, final int chunkZ) throws IOException {
        final RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            if (!rf.hasChunk(chunkX, chunkZ)) {
                return null;
            }
            return rf.readChunk(chunkX, chunkZ);
        }
    }

    public void save(final Dimension dim, final int chunkX, final int chunkZ, final NbtCompound nbt) throws IOException {
        final RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            rf.writeChunk(chunkX, chunkZ, nbt);
        }
    }

    @Override
    public void close() {
        for (final RegionFile rf : regionCache.values()) {
            synchronized (rf) {
                try {
                    rf.close();
                } catch (final IOException ignored) {
                }
            }
        }
        regionCache.clear();
    }
}
