package fr.euphyllia.fidorial.server.network.nbt;

import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtByte;
import fr.euphyllia.fidorial.server.world.nbt.NbtByteArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtDouble;
import fr.euphyllia.fidorial.server.world.nbt.NbtFloat;
import fr.euphyllia.fidorial.server.world.nbt.NbtInt;
import fr.euphyllia.fidorial.server.world.nbt.NbtIntArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtLong;
import fr.euphyllia.fidorial.server.world.nbt.NbtLongArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtShort;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import net.kyori.adventure.nbt.LongBinaryTag;
import net.kyori.adventure.nbt.ShortBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;

import java.util.Map;

final class NbtBinaryTagBridge {

    private NbtBinaryTagBridge() {
    }

    static Nbt toNbt(BinaryTag tag) {
        return switch (tag) {
            case ByteBinaryTag b -> new NbtByte(b.byteValue());
            case ShortBinaryTag s -> new NbtShort(s.shortValue());
            case IntBinaryTag i -> new NbtInt(i.intValue());
            case LongBinaryTag l -> new NbtLong(l.longValue());
            case FloatBinaryTag f -> new NbtFloat(f.floatValue());
            case DoubleBinaryTag d -> new NbtDouble(d.doubleValue());
            case StringBinaryTag s -> new NbtString(s.value());
            case IntArrayBinaryTag arr -> new NbtIntArray(arr.value());
            case ListBinaryTag list -> {
                NbtList out = new NbtList();
                for (BinaryTag child : list) out.add(toNbt(child));
                yield out;
            }
            case CompoundBinaryTag compound -> {
                NbtCompound out = new NbtCompound();
                for (Map.Entry<String, ? extends BinaryTag> entry : compound) {
                    out.put(entry.getKey(), toNbt(entry.getValue()));
                }
                yield out;
            }
            case EndBinaryTag _ -> new NbtCompound(); // empty payload case
            case ByteArrayBinaryTag b -> new NbtByteArray(b.value());
            case LongArrayBinaryTag l -> new NbtLongArray(l.value());
        };
    }

    static BinaryTag toBinaryTag(Nbt nbt) {
        return switch (nbt) {
            case NbtByte b -> ByteBinaryTag.byteBinaryTag(b.value());
            case NbtShort s -> ShortBinaryTag.shortBinaryTag(s.value());
            case NbtInt i -> IntBinaryTag.intBinaryTag(i.value());
            case NbtLong l -> LongBinaryTag.longBinaryTag(l.value());
            case NbtFloat f -> FloatBinaryTag.floatBinaryTag(f.value());
            case NbtDouble d -> DoubleBinaryTag.doubleBinaryTag(d.value());
            case NbtString s -> StringBinaryTag.stringBinaryTag(s.value());
            case NbtIntArray arr -> IntArrayBinaryTag.intArrayBinaryTag(arr.value());
            case NbtList list -> {
                ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.heterogeneousListBinaryTag();
                for (Nbt child : list) builder.add(toBinaryTag(child));
                yield builder.build();
            }
            case NbtCompound compound -> {
                CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
                for (var entry : compound.tags().entrySet()) {
                    builder.put(entry.getKey(), toBinaryTag(entry.getValue()));
                }
                yield builder.build();
            }
            case NbtByteArray b -> ByteArrayBinaryTag.byteArrayBinaryTag(b.value());

            case NbtLongArray l -> LongArrayBinaryTag.longArrayBinaryTag(l.value());
        };
    }
}
