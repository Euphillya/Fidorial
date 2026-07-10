package fr.euphyllia.fidorial.server.world.nbt;

public sealed interface Nbt
        permits NbtByte, NbtShort, NbtInt, NbtLong, NbtFloat, NbtDouble,
        NbtByteArray, NbtString, NbtIntArray, NbtLongArray,
        NbtCompound, NbtList {

    NbtType type();
}
