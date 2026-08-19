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
        final long mixed = h * 0x9E3779B97F4A7C15L;
        return Long.hashCode(mixed);
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
