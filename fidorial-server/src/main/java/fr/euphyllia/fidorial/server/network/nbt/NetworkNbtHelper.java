package fr.euphyllia.fidorial.server.network.nbt;

import com.google.gson.*;
import fr.euphyllia.fidorial.server.world.nbt.*;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static fr.euphyllia.fidorial.server.protocol.ProtocolConstants.MAX_NBT_LENGTH;

public final class NetworkNbtHelper {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private NetworkNbtHelper() {}

    public static Nbt convert(JsonElement element) {
        return switch (element) {
            case JsonObject object -> convertObject(object);
            case JsonArray array -> convertArray(array);
            case JsonPrimitive primitive -> convertPrimitive(primitive);
            default -> throw new EncoderException("Element cannot be represented as NBT " + element);
        };
    }

    private static NbtCompound convertObject(JsonObject object) {
        var compound = new NbtCompound();

        for (var entry : object.entrySet()) {
            compound.put(
                    entry.getKey(),
                    "extra".equals(entry.getKey())
                            ? convertArray(entry.getValue().getAsJsonArray())
                            : convert(entry.getValue())
            );
        }

        return compound;
    }

    private static NbtList convertArray(JsonArray array) {
        var list = new NbtList();

        for (JsonElement child : array) {
            if (child instanceof JsonPrimitive primitive && primitive.isString()) {
                var compound = new NbtCompound();
                compound.putString("text", primitive.getAsString());
                list.add(compound);
            } else {
                list.add(convert(child));
            }
        }

        return list;
    }

    private static Nbt convertPrimitive(JsonPrimitive primitive) {
        if (primitive.isBoolean()) {
            return new NbtByte((byte) (primitive.getAsBoolean() ? 1 : 0));
        }

        if (primitive.isString()) {
            return new NbtString(primitive.getAsString());
        }

        String value = primitive.getAsString();
        if (value.indexOf('.') >= 0 || value.indexOf('e') >= 0 || value.indexOf('E') >= 0) {
            return new NbtDouble(Double.parseDouble(value));
        }

        long number = Long.parseLong(value);
        return number >= Integer.MIN_VALUE && number <= Integer.MAX_VALUE
                ? new NbtInt((int) number)
                : new NbtLong(number);
    }

    public static void writeNbt(ByteBuf buf, Nbt nbt) {
        buf.writeByte(nbt.type().id());
        writePayload(buf, nbt);
    }

    private static void writeList(ByteBuf buf, NbtList list) {
        buf.writeByte(list.elementType().id());
        buf.writeInt(list.size());

        for (Nbt child : list) {
            writePayload(buf, child);
        }
    }

    private static void writeCompound(ByteBuf buf, NbtCompound compound) {
        for (var entry : compound.tags().entrySet()) {
            Nbt child = entry.getValue();

            buf.writeByte(child.type().id());
            writeNbtString(buf, entry.getKey());
            writePayload(buf, child);
        }

        buf.writeByte(0);
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

            case NbtList list -> writeList(buf, list);
            case NbtCompound compound -> writeCompound(buf, compound);

            default -> throw new EncoderException("Unsupported NBT type: " + nbt.type());
        }
    }

    private static void writeNbtString(ByteBuf buf, String value) {
        byte[] bytes = value.getBytes(UTF_8);

        if (bytes.length > MAX_NBT_LENGTH) {
            throw new EncoderException("NBT string too long");
        }

        buf.writeShort(bytes.length);
        buf.writeBytes(bytes);
    }
}
