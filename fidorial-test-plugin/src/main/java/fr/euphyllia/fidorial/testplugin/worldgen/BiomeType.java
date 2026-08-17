package fr.euphyllia.fidorial.testplugin.worldgen;

import fr.fidorial.registry.keys.BiomeKeys;
import net.kyori.adventure.key.Key;

import java.util.HashMap;
import java.util.Map;


public enum BiomeType {

    OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    DEEP_OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    COLD_OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    DEEP_COLD_OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    FROZEN_OCEAN(SurfaceKind.OCEAN_FLOOR, true),
    DEEP_FROZEN_OCEAN(SurfaceKind.OCEAN_FLOOR, true),
    LUKEWARM_OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    DEEP_LUKEWARM_OCEAN(SurfaceKind.OCEAN_FLOOR, false),
    WARM_OCEAN(SurfaceKind.SAND, false),

    RIVER(SurfaceKind.OCEAN_FLOOR, false),
    FROZEN_RIVER(SurfaceKind.OCEAN_FLOOR, true),
    BEACH(SurfaceKind.SAND, false),
    SNOWY_BEACH(SurfaceKind.SAND, true),
    STONY_SHORE(SurfaceKind.GRAVEL, false),

    PLAINS(SurfaceKind.GRASS, false),
    SUNFLOWER_PLAINS(SurfaceKind.GRASS, false),
    SNOWY_PLAINS(SurfaceKind.GRASS, true),
    ICE_SPIKES(SurfaceKind.SNOW_BLOCK, true),
    MEADOW(SurfaceKind.GRASS, false),
    CHERRY_GROVE(SurfaceKind.GRASS, false),
    FOREST(SurfaceKind.GRASS, false),
    FLOWER_FOREST(SurfaceKind.GRASS, false),
    BIRCH_FOREST(SurfaceKind.GRASS, false),
    OLD_GROWTH_BIRCH_FOREST(SurfaceKind.GRASS, false),
    DARK_FOREST(SurfaceKind.GRASS, false),
    PALE_GARDEN(SurfaceKind.GRASS, false),
    TAIGA(SurfaceKind.GRASS, false),
    SNOWY_TAIGA(SurfaceKind.GRASS, true),
    OLD_GROWTH_SPRUCE_TAIGA(SurfaceKind.PODZOL, false),
    OLD_GROWTH_PINE_TAIGA(SurfaceKind.PODZOL, false),
    GROVE(SurfaceKind.SNOW_BLOCK, true),

    DESERT(SurfaceKind.SAND, false),
    SAVANNA(SurfaceKind.GRASS, false),
    SAVANNA_PLATEAU(SurfaceKind.GRASS, false),
    WINDSWEPT_SAVANNA(SurfaceKind.GRASS, false),
    JUNGLE(SurfaceKind.GRASS, false),
    SPARSE_JUNGLE(SurfaceKind.GRASS, false),
    BAMBOO_JUNGLE(SurfaceKind.GRASS, false),
    BADLANDS(SurfaceKind.TERRACOTTA, false),
    ERODED_BADLANDS(SurfaceKind.TERRACOTTA, false),
    WOODED_BADLANDS(SurfaceKind.TERRACOTTA, false),

    SWAMP(SurfaceKind.SWAMP, false),
    MANGROVE_SWAMP(SurfaceKind.MUD, false),

    WINDSWEPT_HILLS(SurfaceKind.GRASS, false),
    WINDSWEPT_GRAVELLY_HILLS(SurfaceKind.GRAVEL, false),
    WINDSWEPT_FOREST(SurfaceKind.GRASS, false),
    SNOWY_SLOPES(SurfaceKind.POWDER_SNOW, true),
    JAGGED_PEAKS(SurfaceKind.STONE_SNOW, true),
    FROZEN_PEAKS(SurfaceKind.PACKED_ICE, true),
    STONY_PEAKS(SurfaceKind.STONY_PEAKS, false),

    MUSHROOM_FIELDS(SurfaceKind.MYCELIUM, false),

    LUSH_CAVES(SurfaceKind.GRASS, false),
    DRIPSTONE_CAVES(SurfaceKind.STONE, false),
    SULFUR_CAVES(SurfaceKind.STONE, false),
    DEEP_DARK(SurfaceKind.STONE, false);

    private static final Map<Key, BiomeType> BY_KEY = new HashMap<>();

    static {
        register(BiomeKeys.OCEAN.key(), OCEAN);
        register(BiomeKeys.DEEP_OCEAN.key(), DEEP_OCEAN);
        register(BiomeKeys.COLD_OCEAN.key(), COLD_OCEAN);
        register(BiomeKeys.DEEP_COLD_OCEAN.key(), DEEP_COLD_OCEAN);
        register(BiomeKeys.FROZEN_OCEAN.key(), FROZEN_OCEAN);
        register(BiomeKeys.DEEP_FROZEN_OCEAN.key(), DEEP_FROZEN_OCEAN);
        register(BiomeKeys.LUKEWARM_OCEAN.key(), LUKEWARM_OCEAN);
        register(BiomeKeys.DEEP_LUKEWARM_OCEAN.key(), DEEP_LUKEWARM_OCEAN);
        register(BiomeKeys.WARM_OCEAN.key(), WARM_OCEAN);
        register(BiomeKeys.RIVER.key(), RIVER);
        register(BiomeKeys.FROZEN_RIVER.key(), FROZEN_RIVER);
        register(BiomeKeys.BEACH.key(), BEACH);
        register(BiomeKeys.SNOWY_BEACH.key(), SNOWY_BEACH);
        register(BiomeKeys.STONY_SHORE.key(), STONY_SHORE);
        register(BiomeKeys.PLAINS.key(), PLAINS);
        register(BiomeKeys.SUNFLOWER_PLAINS.key(), SUNFLOWER_PLAINS);
        register(BiomeKeys.SNOWY_PLAINS.key(), SNOWY_PLAINS);
        register(BiomeKeys.ICE_SPIKES.key(), ICE_SPIKES);
        register(BiomeKeys.MEADOW.key(), MEADOW);
        register(BiomeKeys.CHERRY_GROVE.key(), CHERRY_GROVE);
        register(BiomeKeys.FOREST.key(), FOREST);
        register(BiomeKeys.FLOWER_FOREST.key(), FLOWER_FOREST);
        register(BiomeKeys.BIRCH_FOREST.key(), BIRCH_FOREST);
        register(BiomeKeys.OLD_GROWTH_BIRCH_FOREST.key(), OLD_GROWTH_BIRCH_FOREST);
        register(BiomeKeys.DARK_FOREST.key(), DARK_FOREST);
        register(BiomeKeys.PALE_GARDEN.key(), PALE_GARDEN);
        register(BiomeKeys.TAIGA.key(), TAIGA);
        register(BiomeKeys.SNOWY_TAIGA.key(), SNOWY_TAIGA);
        register(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA.key(), OLD_GROWTH_SPRUCE_TAIGA);
        register(BiomeKeys.OLD_GROWTH_PINE_TAIGA.key(), OLD_GROWTH_PINE_TAIGA);
        register(BiomeKeys.GROVE.key(), GROVE);
        register(BiomeKeys.DESERT.key(), DESERT);
        register(BiomeKeys.SAVANNA.key(), SAVANNA);
        register(BiomeKeys.SAVANNA_PLATEAU.key(), SAVANNA_PLATEAU);
        register(BiomeKeys.WINDSWEPT_SAVANNA.key(), WINDSWEPT_SAVANNA);
        register(BiomeKeys.JUNGLE.key(), JUNGLE);
        register(BiomeKeys.SPARSE_JUNGLE.key(), SPARSE_JUNGLE);
        register(BiomeKeys.BAMBOO_JUNGLE.key(), BAMBOO_JUNGLE);
        register(BiomeKeys.BADLANDS.key(), BADLANDS);
        register(BiomeKeys.ERODED_BADLANDS.key(), ERODED_BADLANDS);
        register(BiomeKeys.WOODED_BADLANDS.key(), WOODED_BADLANDS);
        register(BiomeKeys.SWAMP.key(), SWAMP);
        register(BiomeKeys.MANGROVE_SWAMP.key(), MANGROVE_SWAMP);
        register(BiomeKeys.WINDSWEPT_HILLS.key(), WINDSWEPT_HILLS);
        register(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS.key(), WINDSWEPT_GRAVELLY_HILLS);
        register(BiomeKeys.WINDSWEPT_FOREST.key(), WINDSWEPT_FOREST);
        register(BiomeKeys.SNOWY_SLOPES.key(), SNOWY_SLOPES);
        register(BiomeKeys.JAGGED_PEAKS.key(), JAGGED_PEAKS);
        register(BiomeKeys.FROZEN_PEAKS.key(), FROZEN_PEAKS);
        register(BiomeKeys.STONY_PEAKS.key(), STONY_PEAKS);
        register(BiomeKeys.MUSHROOM_FIELDS.key(), MUSHROOM_FIELDS);
        register(BiomeKeys.LUSH_CAVES.key(), LUSH_CAVES);
        register(BiomeKeys.DRIPSTONE_CAVES.key(), DRIPSTONE_CAVES);
        register(BiomeKeys.SULFUR_CAVES.key(), SULFUR_CAVES);
        register(BiomeKeys.DEEP_DARK.key(), DEEP_DARK);
    }

    private final SurfaceKind surface;
    private final boolean cold;

    BiomeType(final SurfaceKind surface, final boolean cold) {
        this.surface = surface;
        this.cold = cold;
    }

    private static void register(final Key key, final BiomeType type) {
        BY_KEY.put(key, type);
    }

    public static BiomeType of(final Key key) {
        return BY_KEY.getOrDefault(key, PLAINS);
    }

    public SurfaceKind surface() {
        return surface;
    }

    public boolean cold() {
        return cold;
    }

    public boolean cave() {
        return this == LUSH_CAVES || this == DRIPSTONE_CAVES || this == SULFUR_CAVES || this == DEEP_DARK;
    }
}
