package fr.euphyllia.fidorial.server.world.nbt;

public record NbtIntArray(int[] value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.INT_ARRAY;
    }
}
