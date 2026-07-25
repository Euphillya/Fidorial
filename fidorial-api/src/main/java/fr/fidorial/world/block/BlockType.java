package fr.fidorial.world.block;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BlockType {

    private final Key key;
    private final List<BlockProperty> properties;
    private final int[] stateIds;
    private final int defaultOrdinal;
    private final Class<?>[] interfaces;
    private final @Nullable BlockData defaultData;

    private BlockType(Key key, List<BlockProperty> properties, int[] stateIds, int defaultOrdinal,
                      List<Class<? extends BlockData>> traits) {
        this.key = key;
        this.properties = List.copyOf(properties);
        int expected = 1;
        for (BlockProperty property : this.properties) {
            expected *= property.values().size();
        }
        if (stateIds.length != expected) {
            throw new IllegalArgumentException("Block '" + key.asString() + "' expects " + expected
                    + " states but got " + stateIds.length);
        }
        if (defaultOrdinal < 0 || defaultOrdinal >= expected) {
            throw new IllegalArgumentException("Default ordinal out of range for '" + key.asString() + "'");
        }
        this.stateIds = stateIds.clone();
        this.defaultOrdinal = defaultOrdinal;

        List<Class<?>> faces = new ArrayList<>(traits.size() + 1);
        faces.add(BlockData.class);
        for (Class<? extends BlockData> trait : traits) {
            if (!faces.contains(trait)) {
                faces.add(trait);
            }
        }
        this.interfaces = faces.toArray(Class<?>[]::new);
        this.defaultData = createData(defaultOrdinal);
    }

    public static BlockType of(Key key, List<BlockProperty> properties, int[] stateIds, int defaultOrdinal) {
        return new BlockType(key, properties, stateIds, defaultOrdinal, BlockTraits.detect(key, properties));
    }

    public static Builder builder(Key key) {
        return new Builder(key);
    }

    public Key key() {
        return key;
    }

    public List<BlockProperty> properties() {
        return properties;
    }

    public @Nullable BlockProperty property(String name) {
        for (BlockProperty property : properties) {
            if (property.name().equals(name)) {
                return property;
            }
        }
        return null;
    }

    public boolean hasProperty(String name) {
        return property(name) != null;
    }

    public int stateCount() {
        return stateIds.length;
    }

    public List<Class<?>> traits() {
        return List.of(interfaces);
    }

    public @Nullable BlockData defaultData() {
        return defaultData;
    }

    public BlockData stateAt(int ordinal) {
        if (ordinal == defaultOrdinal && defaultData != null) {
            return defaultData;
        }
        return createData(ordinal);
    }

    public @Nullable BlockData data(@Nullable Map<String, String> values) {
        if (values == null || values.isEmpty()) {
            return defaultData;
        }
        int ordinal = defaultOrdinal;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            ordinal = withValue(ordinal, entry.getKey(), entry.getValue());
        }
        return stateAt(ordinal);
    }

    public @Nullable BlockData dataOrNull(Map<String, String> values) {
        try {
            return data(values);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private BlockData createData(int ordinal) {
        return (BlockData) Proxy.newProxyInstance(
                BlockType.class.getClassLoader(), interfaces, new DataHandler(this, ordinal));
    }

    private @Nullable String value(int ordinal, String propertyName) {
        int radix = 1;
        for (int i = properties.size() - 1; i >= 0; i--) {
            BlockProperty property = properties.get(i);
            int size = property.values().size();
            if (property.name().equals(propertyName)) {
                return property.values().get((ordinal / radix) % size);
            }
            radix *= size;
        }
        return null;
    }

    private int withValue(int ordinal, String propertyName, String value) {
        int radix = 1;
        for (int i = properties.size() - 1; i >= 0; i--) {
            BlockProperty property = properties.get(i);
            int size = property.values().size();
            if (property.name().equals(propertyName)) {
                int index = property.indexOf(value);
                if (index < 0) {
                    throw new IllegalArgumentException("Invalid value '" + value + "' for property '"
                            + propertyName + "' of block '" + key.asString() + "'");
                }
                int current = (ordinal / radix) % size;
                return ordinal + (index - current) * radix;
            }
            radix *= size;
        }
        throw new IllegalArgumentException("Unknown property '" + propertyName + "' for block '" + key.asString() + "'");
    }

    private Map<String, @Nullable String> valuesOf(int ordinal) {
        Map<String, @Nullable String> map = new LinkedHashMap<>();
        for (BlockProperty property : properties) {
            map.put(property.name(), value(ordinal, property.name()));
        }
        return map;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BlockType type && type.key.equals(key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "BlockType[" + key.asString() + ", states=" + stateIds.length + "]";
    }

    private record DataHandler(BlockType type, int ordinal) implements InvocationHandler {
        @Override
        public @Nullable Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isDefault()) {
                return InvocationHandler.invokeDefault(proxy, method, args);
            }
            return switch (method.getName()) {
                case "type" -> type;
                case "networkId" -> type.stateIds[ordinal];
                case "get" -> type.value(ordinal, (String) args[0]);
                case "with" -> type.stateAt(type.withValue(ordinal, (String) args[0], (String) args[1]));
                case "propertyMap" -> type.valuesOf(ordinal);
                case "equals" -> equalsData(args[0]);
                case "hashCode" -> type.hashCode() * 31 + ordinal;
                case "toString" -> ((BlockData) proxy).asString();
                default -> throw new UnsupportedOperationException(method.getName());
            };
        }

        private boolean equalsData(@Nullable Object other) {
            if (other == null) return false;
            return Proxy.isProxyClass(other.getClass())
                    && Proxy.getInvocationHandler(other) instanceof DataHandler(BlockType type1, int ordinal1)
                    && type1.equals(type)
                    && ordinal1 == ordinal;
        }
    }

    public static final class Builder {

        private final Key key;
        private final List<BlockProperty> properties = new ArrayList<>();
        private final List<Class<? extends BlockData>> extraTraits = new ArrayList<>();
        private int @Nullable [] stateIds;
        private int fixedStateId = -1;
        private Map<String, String> defaultValues = Map.of();

        private Builder(Key key) {
            this.key = key;
        }

        public Builder property(String name, List<String> values) {
            properties.add(new BlockProperty(name, values));
            return this;
        }

        public Builder property(String name, String... values) {
            return property(name, Arrays.asList(values));
        }

        public Builder stateIds(int[] stateIds) {
            this.stateIds = stateIds.clone();
            return this;
        }

        public Builder appearance(int networkId) {
            this.fixedStateId = networkId;
            return this;
        }

        public Builder defaultValues(Map<String, String> values) {
            this.defaultValues = Map.copyOf(values);
            return this;
        }

        public Builder trait(Class<? extends BlockData> trait) {
            extraTraits.add(trait);
            return this;
        }

        public BlockType build() {
            int count = 1;
            for (BlockProperty property : properties) {
                count *= property.values().size();
            }
            int[] ids = stateIds;
            if (ids == null) {
                if (fixedStateId < 0) {
                    throw new IllegalStateException("Either stateIds(...) or appearance(...) must be set");
                }
                ids = new int[count];
                Arrays.fill(ids, fixedStateId);
            }
            List<Class<? extends BlockData>> traits = new ArrayList<>(BlockTraits.detect(key, properties));
            traits.addAll(extraTraits);

            BlockType type = new BlockType(key, properties, ids, 0, traits);
            if (!defaultValues.isEmpty()) {
                int defaultOrdinal = 0;
                for (Map.Entry<String, String> entry : defaultValues.entrySet()) {
                    defaultOrdinal = type.withValue(defaultOrdinal, entry.getKey(), entry.getValue());
                }
                type = new BlockType(key, properties, ids, defaultOrdinal, traits);
            }
            return type;
        }
    }

}
