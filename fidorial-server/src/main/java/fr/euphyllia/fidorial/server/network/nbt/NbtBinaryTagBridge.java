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

public final class NbtBinaryTagBridge {

    private NbtBinaryTagBridge() {
    }

    public static Nbt toNbt(final BinaryTag tag) {
        return switch (tag) {
            case final ByteBinaryTag b -> new NbtByte(b.byteValue());
            case final ShortBinaryTag s -> new NbtShort(s.shortValue());
            case final IntBinaryTag i -> new NbtInt(i.intValue());
            case final LongBinaryTag l -> new NbtLong(l.longValue());
            case final FloatBinaryTag f -> new NbtFloat(f.floatValue());
            case final DoubleBinaryTag d -> new NbtDouble(d.doubleValue());
            case final StringBinaryTag s -> new NbtString(s.value());
            case final IntArrayBinaryTag arr -> new NbtIntArray(arr.value());
            case final ListBinaryTag list -> {
                final NbtList out = new NbtList();
                for (final BinaryTag child : list) out.add(toNbt(child));
                yield out;
            }
            case final CompoundBinaryTag compound -> {
                final NbtCompound out = new NbtCompound();
                for (final Map.Entry<String, ? extends BinaryTag> entry : compound) {
                    out.put(entry.getKey(), toNbt(entry.getValue()));
                }
                yield out;
            }
            case EndBinaryTag _ -> new NbtCompound(); // empty payload case
            case final ByteArrayBinaryTag b -> new NbtByteArray(b.value());
            case final LongArrayBinaryTag l -> new NbtLongArray(l.value());
        };
    }

    static BinaryTag toBinaryTag(final Nbt nbt) {
        return switch (nbt) {
            case final NbtByte b -> ByteBinaryTag.byteBinaryTag(b.value());
            case final NbtShort s -> ShortBinaryTag.shortBinaryTag(s.value());
            case final NbtInt i -> IntBinaryTag.intBinaryTag(i.value());
            case final NbtLong l -> LongBinaryTag.longBinaryTag(l.value());
            case final NbtFloat f -> FloatBinaryTag.floatBinaryTag(f.value());
            case final NbtDouble d -> DoubleBinaryTag.doubleBinaryTag(d.value());
            case final NbtString s -> StringBinaryTag.stringBinaryTag(s.value());
            case final NbtIntArray arr -> IntArrayBinaryTag.intArrayBinaryTag(arr.value());
            case final NbtList list -> {
                final ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.heterogeneousListBinaryTag();
                for (final Nbt child : list) builder.add(toBinaryTag(child));
                yield builder.build();
            }
            case final NbtCompound compound -> {
                final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
                for (final var entry : compound.tags().entrySet()) {
                    builder.put(entry.getKey(), toBinaryTag(entry.getValue()));
                }
                yield builder.build();
            }
            case final NbtByteArray b -> ByteArrayBinaryTag.byteArrayBinaryTag(b.value());

            case final NbtLongArray l -> LongArrayBinaryTag.longArrayBinaryTag(l.value());
        };
    }
}
