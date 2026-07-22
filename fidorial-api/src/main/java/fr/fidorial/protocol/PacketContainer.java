package fr.fidorial.protocol;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface PacketContainer {

    /**
     * @return the packet type described by this container.
     */
    PacketType type();

    /**
     * @return the number of fields in the packet.
     */
    int size();

    /**
     * Reads a field without type conversion.
     *
     * @param index field index (0 = first field written on the wire)
     * @return the raw value, possibly {@code null} for an optional field
     */
    @Nullable
    Object get(int index);

    /**
     * Reads a field, constraining it to a type.
     *
     * @param index field index
     * @param type  expected type
     * @param <T>   expected type
     * @return the converted value
     * @throws ClassCastException if the field is not assignable to {@code type}
     */
    <T> @Nullable T get(int index, Class<T> type);

    /**
     * Replaces the value of a field.
     *
     * @param index field index
     * @param value new value (must be compatible with the original type)
     * @return {@code this}, to chain calls
     */
    PacketContainer set(int index, @Nullable Object value);

    /**
     * @return an immutable copy of all fields, in wire order.
     */
    List<@Nullable Object> values();

    /**
     * @return a deep, mutable and independent copy of this container.
     */
    PacketContainer copy();


    default long getLong(int index) {
        return get(index, Long.class);
    }

    default PacketContainer setLong(int index, long value) {
        return set(index, value);
    }

    default int getInt(int index) {
        return get(index, Integer.class);
    }

    default PacketContainer setInt(int index, int value) {
        return set(index, value);
    }

    default float getFloat(int index) {
        return get(index, Float.class);
    }

    default PacketContainer setFloat(int index, float value) {
        return set(index, value);
    }

    default double getDouble(int index) {
        return get(index, Double.class);
    }

    default PacketContainer setDouble(int index, double value) {
        return set(index, value);
    }

    default boolean getBoolean(int index) {
        return get(index, Boolean.class);
    }

    default PacketContainer setBoolean(int index, boolean value) {
        return set(index, value);
    }

    default byte getByte(int index) {
        return get(index, Byte.class);
    }

    default PacketContainer setByte(int index, byte value) {
        return set(index, value);
    }

    default @Nullable String getString(int index) {
        return get(index, String.class);
    }

    default PacketContainer setString(int index, @Nullable String value) {
        return set(index, value);
    }

    default @Nullable UUID getUuid(int index) {
        return get(index, UUID.class);
    }

    default PacketContainer setUuid(int index, @Nullable UUID value) {
        return set(index, value);
    }

    default @Nullable Component getComponent(int index) {
        return get(index, Component.class);
    }

    default PacketContainer setComponent(int index, @Nullable Component value) {
        return set(index, value);
    }
}
