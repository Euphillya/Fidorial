package fr.euphyllia.fidorial.server.world.chunk;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public record BlockState(String name, Map<String, String> properties) {

    public static final BlockState AIR = of("minecraft:air");

    public BlockState {
        properties =
                properties.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new TreeMap<>(properties));
    }

    public static BlockState of(String name) {
        return new BlockState(name, Collections.emptyMap());
    }

    public boolean isAir() {
        return name.equals("minecraft:air") || name.equals("minecraft:cave_air") || name.equals("minecraft:void_air");
    }
}
