package fr.fidorial.world.block;

import net.kyori.adventure.key.Key;

import java.util.Map;

public interface BlockData {

    BlockType type();

    default Key key() {
        return type().key();
    }

    int networkId();

    String get(String property);

    BlockData with(String property, String value);

    Map<String, String> propertyMap();

    default boolean isAir() {
        String name = key().asString();
        return name.equals("minecraft:air")
                || name.equals("minecraft:cave_air")
                || name.equals("minecraft:void_air");
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
