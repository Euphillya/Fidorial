package fr.euphyllia.fidorial.server.world.nbt;

public record NbtInt(int value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.INT;
    }
}
