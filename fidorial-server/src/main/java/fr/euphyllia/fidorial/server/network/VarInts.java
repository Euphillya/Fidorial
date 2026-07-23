package fr.euphyllia.fidorial.server.network;

import com.google.gson.JsonElement;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.text.Component;

import java.nio.charset.StandardCharsets;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.GSON_SERIALIZER;
import static fr.euphyllia.fidorial.server.network.nbt.NetworkNbtHelper.convert;
import static fr.euphyllia.fidorial.server.network.nbt.NetworkNbtHelper.writeNbt;

public final class VarInts {

    private VarInts() {
    }

    public static int readVarInt(final ByteBuf buf) {
        int value = 0;
        int position = 0;
        byte current;
        do {
            current = buf.readByte();
            value |= (current & 0x7F) << position;
            position += 7;
            if (position >= 32) throw new DecoderException("VarInt too large");
        } while ((current & 0x80) != 0);
        return value;
    }

    public static void writeVarInt(final ByteBuf buf, int value) {
        while ((value & ~0x7F) != 0) {
            buf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        buf.writeByte(value);
    }

    public static long readVarLong(final ByteBuf buf) {
        long value = 0;
        int position = 0;
        byte current;
        do {
            current = buf.readByte();
            value |= (long) (current & 0x7F) << position;
            position += 7;
            if (position >= 64) throw new DecoderException("VarLong too large");
        } while ((current & 0x80) != 0);
        return value;
    }

    public static void writeVarLong(final ByteBuf buf, long value) {
        while ((value & ~0x7FL) != 0L) {
            buf.writeByte((int) (value & 0x7FL) | 0x80);
            value >>>= 7;
        }
        buf.writeByte((int) value);
    }

    public static int varIntSize(int value) {
        int size = 1;
        while ((value & ~0x7F) != 0) {
            size++;
            value >>>= 7;
        }
        return size;
    }

    public static String readString(final ByteBuf buf, final int maxLength) {
        final int length = readVarInt(buf);
        if (length < 0 || length > maxLength * 4) {
            throw new DecoderException("Invalid string ( " + length + " bytes)");
        }
        final String s = buf.toString(buf.readerIndex(), length, StandardCharsets.UTF_8);
        buf.skipBytes(length);
        return s;
    }

    public static void writeString(final ByteBuf buf, final String value) {
        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static byte[] readByteArray(final ByteBuf buf, final int maxLength) {
        final int length = readVarInt(buf);
        if (length < 0 || length > maxLength) throw new DecoderException("invalid byte[]");
        final byte[] data = new byte[length];
        buf.readBytes(data);
        return data;
    }

    public static void writeByteArray(final ByteBuf buf, final byte[] data) {
        writeVarInt(buf, data.length);
        buf.writeBytes(data);
    }

    public static void writeComponent(final ByteBuf buf, final Component component) {
        final JsonElement deserialized = GSON_SERIALIZER.serializeToTree(component);
        final Nbt serialized = convert(deserialized);
        writeNbt(buf, serialized);
    }

    public static Component readComponent(final ByteBuf buf, final int maxLength) {
        return GSON_SERIALIZER.deserialize(readString(buf, maxLength));
    }
}
