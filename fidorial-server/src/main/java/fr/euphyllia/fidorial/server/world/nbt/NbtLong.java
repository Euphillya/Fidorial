package fr.euphyllia.fidorial.server.world.nbt;

public record NbtLong(long value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.LONG;
    }
}
