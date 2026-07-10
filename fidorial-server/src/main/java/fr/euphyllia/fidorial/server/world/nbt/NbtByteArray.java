package fr.euphyllia.fidorial.server.world.nbt;

public record NbtByteArray(byte[] value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.BYTE_ARRAY;
    }
}
