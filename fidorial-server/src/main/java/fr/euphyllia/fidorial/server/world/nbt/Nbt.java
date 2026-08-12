package fr.euphyllia.fidorial.server.world.nbt;

public sealed interface Nbt
        permits NbtByte, NbtByteArray, NbtCompound, NbtDouble, NbtEnd, NbtFloat, NbtInt, NbtIntArray, NbtList, NbtLong, NbtLongArray, NbtShort, NbtString {

    NbtType type();
}
