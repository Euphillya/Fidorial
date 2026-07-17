package fr.euphyllia.fidorial.server.network;

import fr.euphyllia.fidorial.api.world.BlockPos;
import fr.euphyllia.fidorial.server.world.nbt.*;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public final class PacketBuffer {

    private final ByteBuf buf;

    public PacketBuffer(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf nettyBuf() {
        return buf;
    }

    public int readableBytes() {
        return buf.readableBytes();
    }

    public int readVarInt() {
        return VarInts.readVarInt(buf);
    }

    public PacketBuffer writeVarInt(int value) {
        VarInts.writeVarInt(buf, value);
        return this;
    }

    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte current;
        do {
            current = buf.readByte();
            value |= (long) (current & 0x7F) << position;
            position += 7;
            if (position >= 64) throw new DecoderException("VarLong trop grand");
        } while ((current & 0x80) != 0);
        return value;
    }

    public PacketBuffer writeVarLong(long value) {
        while ((value & ~0x7FL) != 0) {
            buf.writeByte((int) ((value & 0x7F) | 0x80));
            value >>>= 7;
        }
        buf.writeByte((int) value);
        return this;
    }

    public boolean readBoolean() {
        return buf.readBoolean();
    }

    public byte readByte() {
        return buf.readByte();
    }

    public int readUByte() {
        return buf.readUnsignedByte();
    }

    public short readShort() {
        return buf.readShort();
    }

    public int readUShort() {
        return buf.readUnsignedShort();
    }

    public int readInt() {
        return buf.readInt();
    }

    public long readLong() {
        return buf.readLong();
    }

    public float readFloat() {
        return buf.readFloat();
    }

    public double readDouble() {
        return buf.readDouble();
    }

    public PacketBuffer writeBoolean(boolean v) {
        buf.writeBoolean(v);
        return this;
    }

    public PacketBuffer writeByte(int v) {
        buf.writeByte(v);
        return this;
    }

    public PacketBuffer writeShort(int v) {
        buf.writeShort(v);
        return this;
    }

    public PacketBuffer writeInt(int v) {
        buf.writeInt(v);
        return this;
    }

    public PacketBuffer writeLong(long v) {
        buf.writeLong(v);
        return this;
    }

    public PacketBuffer writeFloat(float v) {
        buf.writeFloat(v);
        return this;
    }

    public PacketBuffer writeDouble(double v) {
        buf.writeDouble(v);
        return this;
    }

    public String readString(int maxLength) {
        return VarInts.readString(buf, maxLength);
    }

    public PacketBuffer writeString(String value) {
        VarInts.writeString(buf, value);
        return this;
    }

    public String readIdentifier() {
        return VarInts.readString(buf, 32767);
    }

    public PacketBuffer writeIdentifier(String id) {
        VarInts.writeString(buf, id);
        return this;
    }

    public byte[] readByteArray(int maxLength) {
        return VarInts.readByteArray(buf, maxLength);
    }

    public PacketBuffer writeByteArray(byte[] data) {
        VarInts.writeByteArray(buf, data);
        return this;
    }

    public PacketBuffer writeComponent(Component message) {
        VarInts.writeComponent(buf, message);
        return this;
    }

    public Component readComponent(int maxLength) {
        return VarInts.readComponent(buf, maxLength);
    }

    public PacketBuffer writeRawBytes(byte[] data) {
        buf.writeBytes(data);
        return this;
    }

    public UUID readUuid() {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public PacketBuffer writeUuid(UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    public BlockPos readPosition() {
        long packed = buf.readLong();
        int x = (int) (packed >> 38);
        int y = (int) (packed << 52 >> 52);
        int z = (int) (packed << 26 >> 38);
        return new BlockPos(x, y, z);
    }

    public PacketBuffer writePosition(int x, int y, int z) {
        long packed = ((long) (x & 0x3FFFFFF) << 38)
                | ((long) (z & 0x3FFFFFF) << 12)
                | (y & 0xFFF);
        buf.writeLong(packed);
        return this;
    }

    public PacketBuffer writeVarIntArray(int[] values) {
        VarInts.writeVarInt(buf, values.length);
        for (int v : values) VarInts.writeVarInt(buf, v);
        return this;
    }

    public PacketBuffer writeLongArray(long[] values) {
        VarInts.writeVarInt(buf, values.length);
        for (long v : values) buf.writeLong(v);
        return this;
    }

    public PacketBuffer writeBitSet(long[] words) {
        return writeLongArray(words);
    }
}
