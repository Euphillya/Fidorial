package fr.euphyllia.fidorial.server.world.chunk;

import net.kyori.adventure.key.Key;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class BlockState {

    private static final Key AIR_KEY = Key.key("minecraft:air");
    private static final Key CAVE_AIR_KEY = Key.key("minecraft:cave_air");
    private static final Key VOID_AIR_KEY = Key.key("minecraft:void_air");
    private static final Key WATER_KEY = Key.key("minecraft:water");
    private static final Key LAVA_KEY = Key.key("minecraft:lava");

    public static final BlockState AIR = of(AIR_KEY);
    public static final BlockState CAVE_AIR = of(CAVE_AIR_KEY);
    public static final BlockState VOID_AIR = of(VOID_AIR_KEY);
    public static final BlockState OBSIDIAN = of("minecraft:obsidian");
    public static final BlockState COBBLESTONE = of("minecraft:cobblestone");
    public static final BlockState ENDER_CHEST = of("minecraft:ender_chest");
    public static final BlockState WATER = of(WATER_KEY);
    public static final BlockState LAVA = of(LAVA_KEY);

    private final Key name;
    private final Map<String, String> properties;
    private final int hashCode;
    private final boolean air;
    private final boolean fluid;

    private volatile LightProperties lightProperties;

    public BlockState(final Key name, final Map<String, String> properties) {
        this.name = name;
        this.properties = properties.isEmpty()
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new TreeMap<>(properties));
        this.hashCode = Objects.hash(this.name, this.properties);
        this.air = name.equals(AIR_KEY) || name.equals(CAVE_AIR_KEY) || name.equals(VOID_AIR_KEY);
        this.fluid = name.equals(WATER_KEY) || name.equals(LAVA_KEY);
    }

    public static BlockState of(final Key name) {
        return new BlockState(name, Collections.emptyMap());
    }

    private static BlockState of(final String name) {
        return new BlockState(Key.key(name), Collections.emptyMap());
    }

    public Key name() {
        return name;
    }

    public Map<String, String> properties() {
        return properties;
    }

    public boolean isAir() {
        return air;
    }

    public boolean isFluid() {
        return fluid;
    }

    public LightProperties lightProperties() {
        return lightProperties;
    }

    public void setLightProperties(final LightProperties props) {
        this.lightProperties = props;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BlockState other)) return false;
        return hashCode == other.hashCode && name.equals(other.name) && properties.equals(other.properties);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "BlockState[name=" + name + ", properties=" + properties + "]";
    }

    public record LightProperties(int opacity, int emission) {
    }
}
