package fr.euphyllia.fidorial.server.world.nbt;

import org.jspecify.annotations.NonNull;

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

    public boolean contains(String key) {
        return tags.containsKey(key);
    }

    public Nbt get(String key) {
        return tags.get(key);
    }

    public NbtCompound put(String key, Nbt value) {
        tags.put(key, value);
        return this;
    }

    public NbtCompound putByte(String key, int value) {
        return put(key, new NbtByte((byte) value));
    }

    public NbtCompound putBoolean(String key, boolean value) {
        return putByte(key, value ? 1 : 0);
    }

    public NbtCompound putShort(String key, int value) {
        return put(key, new NbtShort((short) value));
    }

    public NbtCompound putInt(String key, int value) {
        return put(key, new NbtInt(value));
    }

    public NbtCompound putLong(String key, long value) {
        return put(key, new NbtLong(value));
    }

    public NbtCompound putFloat(String key, float value) {
        return put(key, new NbtFloat(value));
    }

    public NbtCompound putDouble(String key, double value) {
        return put(key, new NbtDouble(value));
    }

    public NbtCompound putString(String key, String value) {
        return put(key, new NbtString(value));
    }

    public NbtCompound putByteArray(String key, byte[] value) {
        return put(key, new NbtByteArray(value));
    }

    public NbtCompound putIntArray(String key, int[] value) {
        return put(key, new NbtIntArray(value));
    }

    public NbtCompound putLongArray(String key, long[] value) {
        return put(key, new NbtLongArray(value));
    }

    // --- accès typés (retours par défaut si absent / mauvais type) ---

    public byte getByte(String key) {
        return get(key) instanceof NbtByte(byte value) ? value : 0;
    }

    public boolean getBoolean(String key) {
        return getByte(key) != 0;
    }

    public int getInt(String key) {
        return get(key) instanceof NbtInt(int value) ? value : 0;
    }

    public long getLong(String key) {
        return get(key) instanceof NbtLong(long value) ? value : 0L;
    }

    public float getFloat(String key) {
        return get(key) instanceof NbtFloat(float value) ? value : 0f;
    }

    public double getDouble(String key) {
        return get(key) instanceof NbtDouble(double value) ? value : 0d;
    }

    public String getString(String key) {
        return get(key) instanceof NbtString(String value) ? value : "";
    }

    public long[] getLongArray(String key) {
        return get(key) instanceof NbtLongArray(long[] value) ? value : new long[0];
    }

    public int[] getIntArray(String key) {
        return get(key) instanceof NbtIntArray(int[] value) ? value : new int[0];
    }

    public byte[] getByteArray(String key) {
        return get(key) instanceof NbtByteArray(byte[] value) ? value : new byte[0];
    }

    public NbtCompound getCompound(String key) {
        return get(key) instanceof NbtCompound c ? c : null;
    }

    public NbtList getList(String key) {
        return get(key) instanceof NbtList l ? l : null;
    }

    @Override
    public @NonNull Iterator<Nbt> iterator() {
        return tags.values().iterator();
    }

    public Set<Map.Entry<String, Nbt>> entrySet() {
        return tags.entrySet();
    }
}
