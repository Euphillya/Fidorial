package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.anvil.RegionConstants;
import fr.euphyllia.fidorial.server.world.anvil.RegionFile;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityRegionStorage implements AutoCloseable {

    private final WorldPaths paths;
    private final Map<RegionKey, RegionFile> regionCache = new ConcurrentHashMap<>();

    public EntityRegionStorage(final WorldPaths paths) {
        this.paths = paths;
    }

    private record RegionKey(Key dimension, int regionX, int regionZ) {
    }

    private RegionFile region(final Dimension dim, final int chunkX, final int chunkZ) {
        final int rx = RegionConstants.chunkToRegion(chunkX);
        final int rz = RegionConstants.chunkToRegion(chunkZ);
        final RegionKey key = new RegionKey(dim.id(), rx, rz);
        return regionCache.computeIfAbsent(key, k -> {
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

    public @Nullable CompoundBinaryTag load(final Dimension dim, final int chunkX, final int chunkZ) throws IOException {
        final RegionFile rf = region(dim, chunkX, chunkZ);
        synchronized (rf) {
            if (!rf.hasChunk(chunkX, chunkZ)) {
                return null;
            }
            return rf.readChunk(chunkX, chunkZ);
        }
    }

    public void save(final Dimension dim, final int chunkX, final int chunkZ, final CompoundBinaryTag nbt) throws IOException {
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
