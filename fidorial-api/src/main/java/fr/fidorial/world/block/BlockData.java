package fr.fidorial.world.block;

import net.kyori.adventure.key.Key;

import java.util.Map;

public interface BlockData {

    Key AIR = Key.key("air");
    Key CAVE_AIR = Key.key("cave_air");
    Key VOID_AIR = Key.key("void_air");

    BlockType type();

    default Key key() {
        return type().key();
    }

    int networkId();

    String get(String property);

    BlockData with(String property, String value);

    Map<String, String> propertyMap();

    default boolean isAir() {
        Key name = key();
        return name.equals(AIR) || name.equals(CAVE_AIR) || name.equals(VOID_AIR);
    }

    default String asString() {
        if (type().properties().isEmpty()) {
            return key().asString();
        }
        StringBuilder builder = new StringBuilder(key().asString()).append('[');
        boolean first = true;
        for (Map.Entry<String, String> entry : propertyMap().entrySet()) {
            if (!first) {
                builder.append(',');
            }
            builder.append(entry.getKey()).append('=').append(entry.getValue());
            first = false;
        }
        return builder.append(']').toString();
    }
}
