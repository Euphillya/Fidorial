package fr.euphyllia.fidorial.server.network;

import fr.fidorial.world.BlockPos;
import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;

import java.util.BitSet;
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

    public BitSet readFixedBitSet(int bits) {
        int bytes = (bits + 7) / 8;

        byte[] data = new byte[bytes];
        buf.readBytes(data);

        return BitSet.valueOf(data);
    }

    public PacketBuffer writeFixedBitSet(BitSet bitSet, int bits) {
        int bytes = (bits + 7) / 8;
        byte[] data = new byte[bytes];

        byte[] source = bitSet.toByteArray();
        System.arraycopy(source, 0, data, 0, Math.min(source.length, data.length));

        buf.writeBytes(data);
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

    public byte[] readOptionalByteArray(int maxLength) {
        if (!readBoolean()) {
            return null;
        }
        return VarInts.readByteArray(buf, maxLength);
    }

    public byte[] readRemainingBytes() {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return data;
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

    public PacketBuffer writeAngle(float degrees) {
        buf.writeByte((int) (degrees * 256f / 360f));
        return this;
    }

    private static final double LP_VEC3_ABS_MAX = 1.7179869183E10;
    private static final double LP_VEC3_ABS_MIN = 3.051944088384301E-5;
    private static final double LP_VEC3_MAX_QUANTIZED = 32766.0;

    public PacketBuffer writeLpVec3(double x, double y, double z) {
        x = lpSanitize(x);
        y = lpSanitize(y);
        z = lpSanitize(z);
        double max = Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(z)));
        if (max < LP_VEC3_ABS_MIN) {
            buf.writeByte(0);
            return this;
        }
        long scale = (long) Math.ceil(max);
        boolean continuation = (scale & 0b11L) != scale;
        long flags = continuation ? (scale & 0b11L) | 0b100L : scale;
        long packed = flags
                | lpPack(x / scale) << 3
                | lpPack(y / scale) << 18
                | lpPack(z / scale) << 33;
        buf.writeByte((int) packed);
        buf.writeByte((int) (packed >> 8));
        buf.writeInt((int) (packed >> 16));
        if (continuation) {
            writeVarInt((int) (scale >> 2));
        }
        return this;
    }

    private static double lpSanitize(double value) {
        return Double.isNaN(value) ? 0.0 : Math.clamp(value, -LP_VEC3_ABS_MAX, LP_VEC3_ABS_MAX);
    }

    private static long lpPack(double value) {
        return Math.round((value * 0.5 + 0.5) * LP_VEC3_MAX_QUANTIZED);
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
