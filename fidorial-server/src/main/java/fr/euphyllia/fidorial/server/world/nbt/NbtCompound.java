package fr.euphyllia.fidorial.server.world.nbt;

import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class NbtCompound implements Nbt, Iterable<Nbt> {

    private final Map<String, Nbt> tags = new LinkedHashMap<>();

    @Override
    public NbtType type() {
        return NbtType.COMPOUND;
    }

    public Map<String, Nbt> tags() {
        return tags;
    }

    public Set<String> keys() {
        return tags.keySet();
    }

    public boolean contains(final String key) {
        return tags.containsKey(key);
    }

    public Nbt get(final String key) {
        return tags.get(key);
    }

    public NbtCompound put(final String key, final Nbt value) {
        tags.put(key, value);
        return this;
    }

    public NbtCompound putByte(final String key, final int value) {
        return put(key, new NbtByte((byte) value));
    }

    public NbtCompound putBoolean(final String key, final boolean value) {
        return putByte(key, value ? 1 : 0);
    }

    public NbtCompound putShort(final String key, final int value) {
        return put(key, new NbtShort((short) value));
    }

    public NbtCompound putInt(final String key, final int value) {
        return put(key, new NbtInt(value));
    }

    public NbtCompound putLong(final String key, final long value) {
        return put(key, new NbtLong(value));
    }

    public NbtCompound putFloat(final String key, final float value) {
        return put(key, new NbtFloat(value));
    }

    public NbtCompound putDouble(final String key, final double value) {
        return put(key, new NbtDouble(value));
    }

    public NbtCompound putString(final String key, final String value) {
        return put(key, new NbtString(value));
    }

    public NbtCompound putByteArray(final String key, final byte[] value) {
        return put(key, new NbtByteArray(value));
    }

    public NbtCompound putIntArray(final String key, final int[] value) {
        return put(key, new NbtIntArray(value));
    }

    public NbtCompound putLongArray(final String key, final long[] value) {
        return put(key, new NbtLongArray(value));
    }

    public byte getByte(final String key) {
        return get(key) instanceof NbtByte(final byte value) ? value : 0;
    }

    public boolean getBoolean(final String key) {
        return getByte(key) != 0;
    }

    public int getInt(final String key) {
        return get(key) instanceof NbtInt(final int value) ? value : 0;
    }

    public long getLong(final String key) {
        return get(key) instanceof NbtLong(final long value) ? value : 0L;
    }

    public float getFloat(final String key) {
        return get(key) instanceof NbtFloat(final float value) ? value : 0f;
    }

    public double getDouble(final String key) {
        return get(key) instanceof NbtDouble(final double value) ? value : 0d;
    }

    public String getString(final String key) {
        return get(key) instanceof NbtString(final String value) ? value : "";
    }

    public long[] getLongArray(final String key) {
        return get(key) instanceof NbtLongArray(final long[] value) ? value : new long[0];
    }

    public int[] getIntArray(final String key) {
        return get(key) instanceof NbtIntArray(final int[] value) ? value : new int[0];
    }

    public byte[] getByteArray(final String key) {
        return get(key) instanceof NbtByteArray(final byte[] value) ? value : new byte[0];
    }

    public @Nullable NbtCompound getCompound(final String key) {
        return get(key) instanceof final NbtCompound c ? c : null;
    }

    public @Nullable NbtList getList(final String key) {
        return get(key) instanceof final NbtList l ? l : null;
    }

    @Override
    public Iterator<Nbt> iterator() {
        return tags.values().iterator();
    }

    public Set<Map.Entry<String, Nbt>> entrySet() {
        return tags.entrySet();
    }
}
