package fr.euphyllia.fidorial.server.world.nbt;

public record NbtByte(byte value) implements Nbt {
    @Override
    public NbtType type() {
        return NbtType.BYTE;
    }
}
