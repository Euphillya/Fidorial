package fr.euphyllia.fidorial.server.world;

final class ChunkMapKey {
    final long raw;
    private final int hash;

    private ChunkMapKey(final long raw) {
        this.raw = raw;
        this.hash = mix(raw);
    }

    static ChunkMapKey of(final long raw) {
        return new ChunkMapKey(raw);
    }

    private static int mix(long h) {
        h ^= (h >>> 33);
        h *= 0xff51afd7ed558ccdL;
        h ^= (h >>> 33);
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= (h >>> 33);
        return (int) h;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof ChunkMapKey other && other.raw == this.raw;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
