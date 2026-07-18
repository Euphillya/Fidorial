package fr.euphyllia.fidorial.server.network;

import com.google.gson.JsonElement;
import fr.euphyllia.fidorial.server.world.nbt.*;
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

    public static int readVarInt(ByteBuf buf) {
        int value = 0;
        int position = 0;
        byte current;
        do {
            current = buf.readByte();
            value |= (current & 0x7F) << position;
            position += 7;
            if (position >= 32) throw new DecoderException("VarInt trop grand");
        } while ((current & 0x80) != 0);
        return value;
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        while ((value & ~0x7F) != 0) {
            buf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        buf.writeByte(value);
    }

    public static int varIntSize(int value) {
        int size = 1;
        while ((value & ~0x7F) != 0) {
            size++;
            value >>>= 7;
        }
        return size;
    }

    public static String readString(ByteBuf buf, int maxLength) {
        int length = readVarInt(buf);
        if (length < 0 || length > maxLength * 4) {
            throw new DecoderException("String invalide (" + length + " octets)");
        }
        String s = buf.toString(buf.readerIndex(), length, StandardCharsets.UTF_8);
        buf.skipBytes(length);
        return s;
    }

    public static void writeString(ByteBuf buf, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static byte[] readByteArray(ByteBuf buf, int maxLength) {
        int length = readVarInt(buf);
        if (length < 0 || length > maxLength) throw new DecoderException("byte[] invalide");
        byte[] data = new byte[length];
        buf.readBytes(data);
        return data;
    }

    public static void writeByteArray(ByteBuf buf, byte[] data) {
        writeVarInt(buf, data.length);
        buf.writeBytes(data);
    }

    public static void writeComponent(ByteBuf buf, Component component) {
        final JsonElement deserialized = GSON_SERIALIZER.serializeToTree(component);
        final Nbt serialized = convert(deserialized);
        writeNbt(buf, serialized);
    }

    public static Component readComponent(ByteBuf buf, int maxLength) {
        return GSON_SERIALIZER.deserialize(readString(buf, maxLength));
    }
}
