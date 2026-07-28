package fr.euphyllia.fidorial.server.world.chunk;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public record BlockState(String name, Map<String, String> properties) {

    public static final BlockState AIR = of("minecraft:air");
    public static final BlockState CAVE_AIR = of("minecraft:cave_air");
    public static final BlockState VOID_AIR = of("minecraft:void_air");
    public static final BlockState OBSIDIAN = BlockState.of("minecraft:obsidian");
    public static final BlockState COBBLESTONE = BlockState.of("minecraft:cobblestone");
    public static final BlockState ENDER_CHEST = BlockState.of("minecraft:ender_chest");

    public BlockState {
        properties =
                properties.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new TreeMap<>(properties));
    }

    public static BlockState of(final String name) {
        return new BlockState(name, Collections.emptyMap());
    }

    public boolean isAir() {
        return name.equals(AIR.name) || name.equals(CAVE_AIR.name) || name.equals(VOID_AIR.name);
    }
}
