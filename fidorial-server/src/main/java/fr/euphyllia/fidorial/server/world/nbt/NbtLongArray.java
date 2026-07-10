package fr.euphyllia.fidorial.server.world.nbt;

public record NbtLongArray(long[] value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.LONG_ARRAY;
    }
}
