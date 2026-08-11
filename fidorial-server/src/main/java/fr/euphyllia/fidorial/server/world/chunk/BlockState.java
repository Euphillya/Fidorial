package fr.euphyllia.fidorial.server.world.chunk;

import net.kyori.adventure.key.Key;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public record BlockState(Key name, Map<String, String> properties) {

    public static final BlockState AIR = of("minecraft:air");
    public static final BlockState CAVE_AIR = of("minecraft:cave_air");
    public static final BlockState VOID_AIR = of("minecraft:void_air");
    public static final BlockState OBSIDIAN = BlockState.of("minecraft:obsidian");
    public static final BlockState COBBLESTONE = BlockState.of("minecraft:cobblestone");
    public static final BlockState ENDER_CHEST = BlockState.of("minecraft:ender_chest");
    public static final BlockState WATER = BlockState.of("minecraft:water");
    public static final BlockState LAVA = BlockState.of("minecraft:lava");

    public BlockState {
        properties =
                properties.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new TreeMap<>(properties));
    }

    public static BlockState of(final Key name) {
        return new BlockState(name, Collections.emptyMap());
    }

    private static BlockState of(final String name) {
        return new BlockState(Key.key(name), Collections.emptyMap());
    }

    public boolean isAir() {
        return name.equals(AIR.name) || name.equals(CAVE_AIR.name) || name.equals(VOID_AIR.name);
    }

    public boolean isFluid() {
        return name.equals(WATER.name) || name.equals(LAVA.name);
    }
}
