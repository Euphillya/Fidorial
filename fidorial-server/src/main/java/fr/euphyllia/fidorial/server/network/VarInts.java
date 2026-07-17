package fr.euphyllia.fidorial.server.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import fr.euphyllia.fidorial.server.world.nbt.*;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.nio.charset.StandardCharsets;

public final class VarInts {

    private static final GsonComponentSerializer GSON_COMPONENT_SERIALIZER = GsonComponentSerializer.gson();

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

    public static void writeVarLong(ByteBuf buf, long value) {
        while ((value & ~0x7FL) != 0) {
            buf.writeByte((int) ((value & 0x7F) | 0x80));
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

    public static int varLongSize(long value) {
        int size = 1;
        while ((value & ~0x7FL) != 0) {
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
        final String deserialized = GSON_COMPONENT_SERIALIZER.serialize(component);
        final JsonElement element = JsonParser.parseString(deserialized);
        final Nbt serialized = convert(element, null);
        writeNbt(buf, serialized);
    }

    public static Component readComponent(ByteBuf buf, int maxLength) {
        Component component = GSON_COMPONENT_SERIALIZER.deserialize(readString(buf, maxLength));
        return component;
    }

    private static Nbt convert(final JsonElement element, final String parentKey) {
        // shouldn't EVER happen
        if (element.isJsonNull()) {
            throw new IllegalArgumentException("JSON null cannot be represented as NBT");
        }

        if (element.isJsonObject()) {
            NbtCompound compound = new NbtCompound();

            for (var entry : element.getAsJsonObject().entrySet()) {
                compound.put(entry.getKey(), convert(entry.getValue(), entry.getKey()));
            }

            return compound;
        }

        if (element.isJsonArray()) {
            NbtList list = new NbtList();

            for (JsonElement child : element.getAsJsonArray()) {
                // nested
                if ("extra".equals(parentKey)
                        && child.isJsonPrimitive()
                        && child.getAsJsonPrimitive().isString()) {

                    NbtCompound text = new NbtCompound();
                    text.putString("text", child.getAsString());
                    list.add(text);
                } else {
                    list.add(convert(child, parentKey));
                }
            }

            return list;
        }

        JsonPrimitive primitive = element.getAsJsonPrimitive();

        if (primitive.isBoolean()) {
            return new NbtByte((byte) (primitive.getAsBoolean() ? 1 : 0));
        }

        if (primitive.isNumber()) {
            String value = primitive.getAsString();

            if (value.contains(".") || value.contains("e") || value.contains("E")) {
                return new NbtDouble(Double.parseDouble(value));
            }

            long l = Long.parseLong(value);

            if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                return new NbtInt((int) l);
            }

            return new NbtLong(l);
        }

        return new NbtString(primitive.getAsString());
    }

    private static void writeNbt(ByteBuf buf, Nbt nbt) {
        buf.writeByte(nbt.type().id());
        writePayload(buf, nbt);
    }

    private static void writePayload(ByteBuf buf, Nbt nbt) {
        switch (nbt) {
            case NbtByte b -> buf.writeByte(b.value());
            case NbtShort s -> buf.writeShort(s.value());
            case NbtInt i -> buf.writeInt(i.value());
            case NbtLong l -> buf.writeLong(l.value());
            case NbtFloat f -> buf.writeFloat(f.value());
            case NbtDouble d -> buf.writeDouble(d.value());
            case NbtString s -> writeNbtString(buf, s.value());
            case NbtList list -> {
                buf.writeByte(list.elementType().id());
                buf.writeInt(list.size());
                for (Nbt child : list) {
                    writePayload(buf, child);
                }
            }
            case NbtCompound compound -> {
                for (var entry : compound.tags().entrySet()) {
                    Nbt child = entry.getValue();
                    buf.writeByte(child.type().id());
                    writeNbtString(buf, entry.getKey());
                    writePayload(buf, child);
                }
                buf.writeByte(0);
            }
            default -> throw new IllegalArgumentException("Unsupported NBT type: " + nbt.type());
        }
    }

    private static void writeNbtString(ByteBuf buf, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        buf.writeShort(bytes.length);
        buf.writeBytes(bytes);
    }
}
