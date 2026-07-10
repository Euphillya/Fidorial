package fr.euphyllia.fidorial.server.world.nbt;

public record NbtDouble(double value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.DOUBLE;
    }
}
