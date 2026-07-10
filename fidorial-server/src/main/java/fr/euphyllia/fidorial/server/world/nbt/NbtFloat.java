package fr.euphyllia.fidorial.server.world.nbt;

public record NbtFloat(float value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.FLOAT;
    }
}
