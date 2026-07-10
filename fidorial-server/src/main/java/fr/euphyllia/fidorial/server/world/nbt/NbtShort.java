package fr.euphyllia.fidorial.server.world.nbt;

public record NbtShort(short value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.SHORT;
    }
}
