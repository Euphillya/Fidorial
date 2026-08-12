package fr.euphyllia.fidorial.server.world.nbt;

public record NbtEnd() implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.END;
    }
}
