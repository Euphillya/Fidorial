package fr.euphyllia.fidorial.server.protocol.impl;

import fr.fidorial.protocol.PacketContainer;
import fr.fidorial.protocol.PacketType;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.List;

public final class RecordPacketContainer implements PacketContainer {

    private final PacketType type;
    private final Class<?> recordClass;
    private final Class<?>[] componentTypes;
    private final @Nullable Object[] values;

    private RecordPacketContainer(PacketType type, Class<?> recordClass,
                                  Class<?>[] componentTypes, @Nullable Object[] values) {
        this.type = type;
        this.recordClass = recordClass;
        this.componentTypes = componentTypes;
        this.values = values;
    }

    /**
     * Builds a container by introspecting an existing record.
     *
     * @param type   packet type to associate
     * @param packet record instance to wrap
     * @return a mutable container initialised with the record's values
     */
    public static RecordPacketContainer of(PacketType type, Record packet) {
        RecordComponent[] comps = packet.getClass().getRecordComponents();
        Object[] vals = new Object[comps.length];
        Class<?>[] types = new Class<?>[comps.length];
        try {
            for (int i = 0; i < comps.length; i++) {
                var accessor = comps[i].getAccessor();
                accessor.setAccessible(true);
                vals[i] = accessor.invoke(packet);
                types[i] = comps[i].getType();
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot introspect packet " + packet.getClass(), e);
        }
        return new RecordPacketContainer(type, packet.getClass(), types, vals);
    }

    @Override
    public PacketType type() {
        return type;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public @Nullable Object get(int index) {
        return values[index];
    }

    @Override
    public @Nullable <T> T get(int index, Class<T> type) {
        Object value = values[index];
        if (value == null) {
            return null;
        }
        if (!type.isInstance(value)) {
            throw new ClassCastException("Field " + index + " of type " + value.getClass().getName()
                    + " is not compatible with " + type.getName());
        }
        return type.cast(value);
    }

    @Override
    public PacketContainer set(int index, @Nullable Object value) {
        values[index] = value;
        return this;
    }

    @Override
    public List<@Nullable Object> values() {
        return List.of();
    }

    @Override
    public PacketContainer copy() {
        return new RecordPacketContainer(type, recordClass, componentTypes, values.clone());
    }

    /**
     * Reconstructs an immutable record instance from the current values.
     *
     * @return a new record instance
     */
    public Object rebuild() {
        try {
            Constructor<?> ctor = recordClass.getDeclaredConstructor(componentTypes);
            ctor.setAccessible(true);
            return ctor.newInstance(values);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot rebuild packet " + recordClass, e);
        }
    }
}
