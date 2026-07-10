package fr.euphyllia.fidorial.server.world.nbt;

public record NbtString(String value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.STRING;
    }
}
