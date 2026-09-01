package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Biome;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:worldgen/biome} registry.
 */
public final class BiomeKeys {
    /**
     * Key for {@code minecraft:badlands}.
     */
    public static final TypedKey<Biome> BADLANDS = create("badlands");

    /**
     * Key for {@code minecraft:bamboo_jungle}.
     */
    public static final TypedKey<Biome> BAMBOO_JUNGLE = create("bamboo_jungle");

    /**
     * Key for {@code minecraft:basalt_deltas}.
     */
    public static final TypedKey<Biome> BASALT_DELTAS = create("basalt_deltas");

    /**
     * Key for {@code minecraft:beach}.
     */
    public static final TypedKey<Biome> BEACH = create("beach");

    /**
     * Key for {@code minecraft:birch_forest}.
     */
    public static final TypedKey<Biome> BIRCH_FOREST = create("birch_forest");

    /**
     * Key for {@code minecraft:cherry_grove}.
     */
    public static final TypedKey<Biome> CHERRY_GROVE = create("cherry_grove");

    /**
     * Key for {@code minecraft:cold_ocean}.
     */
    public static final TypedKey<Biome> COLD_OCEAN = create("cold_ocean");

    /**
     * Key for {@code minecraft:crimson_forest}.
     */
    public static final TypedKey<Biome> CRIMSON_FOREST = create("crimson_forest");

    /**
     * Key for {@code minecraft:dark_forest}.
     */
    public static final TypedKey<Biome> DARK_FOREST = create("dark_forest");

    /**
     * Key for {@code minecraft:deep_cold_ocean}.
     */
    public static final TypedKey<Biome> DEEP_COLD_OCEAN = create("deep_cold_ocean");

    /**
     * Key for {@code minecraft:deep_dark}.
     */
    public static final TypedKey<Biome> DEEP_DARK = create("deep_dark");

    /**
     * Key for {@code minecraft:deep_frozen_ocean}.
     */
    public static final TypedKey<Biome> DEEP_FROZEN_OCEAN = create("deep_frozen_ocean");

    /**
     * Key for {@code minecraft:deep_lukewarm_ocean}.
     */
    public static final TypedKey<Biome> DEEP_LUKEWARM_OCEAN = create("deep_lukewarm_ocean");

    /**
     * Key for {@code minecraft:deep_ocean}.
     */
    public static final TypedKey<Biome> DEEP_OCEAN = create("deep_ocean");

    /**
     * Key for {@code minecraft:desert}.
     */
    public static final TypedKey<Biome> DESERT = create("desert");

    /**
     * Key for {@code minecraft:dripstone_caves}.
     */
    public static final TypedKey<Biome> DRIPSTONE_CAVES = create("dripstone_caves");

    /**
     * Key for {@code minecraft:end_barrens}.
     */
    public static final TypedKey<Biome> END_BARRENS = create("end_barrens");

    /**
     * Key for {@code minecraft:end_highlands}.
     */
    public static final TypedKey<Biome> END_HIGHLANDS = create("end_highlands");

    /**
     * Key for {@code minecraft:end_midlands}.
     */
    public static final TypedKey<Biome> END_MIDLANDS = create("end_midlands");

    /**
     * Key for {@code minecraft:eroded_badlands}.
     */
    public static final TypedKey<Biome> ERODED_BADLANDS = create("eroded_badlands");

    /**
     * Key for {@code minecraft:flower_forest}.
     */
    public static final TypedKey<Biome> FLOWER_FOREST = create("flower_forest");

    /**
     * Key for {@code minecraft:forest}.
     */
    public static final TypedKey<Biome> FOREST = create("forest");

    /**
     * Key for {@code minecraft:frozen_ocean}.
     */
    public static final TypedKey<Biome> FROZEN_OCEAN = create("frozen_ocean");

    /**
     * Key for {@code minecraft:frozen_peaks}.
     */
    public static final TypedKey<Biome> FROZEN_PEAKS = create("frozen_peaks");

    /**
     * Key for {@code minecraft:frozen_river}.
     */
    public static final TypedKey<Biome> FROZEN_RIVER = create("frozen_river");

    /**
     * Key for {@code minecraft:grove}.
     */
    public static final TypedKey<Biome> GROVE = create("grove");

    /**
     * Key for {@code minecraft:ice_spikes}.
     */
    public static final TypedKey<Biome> ICE_SPIKES = create("ice_spikes");

    /**
     * Key for {@code minecraft:jagged_peaks}.
     */
    public static final TypedKey<Biome> JAGGED_PEAKS = create("jagged_peaks");

    /**
     * Key for {@code minecraft:jungle}.
     */
    public static final TypedKey<Biome> JUNGLE = create("jungle");

    /**
     * Key for {@code minecraft:lukewarm_ocean}.
     */
    public static final TypedKey<Biome> LUKEWARM_OCEAN = create("lukewarm_ocean");

    /**
     * Key for {@code minecraft:lush_caves}.
     */
    public static final TypedKey<Biome> LUSH_CAVES = create("lush_caves");

    /**
     * Key for {@code minecraft:mangrove_swamp}.
     */
    public static final TypedKey<Biome> MANGROVE_SWAMP = create("mangrove_swamp");

    /**
     * Key for {@code minecraft:meadow}.
     */
    public static final TypedKey<Biome> MEADOW = create("meadow");

    /**
     * Key for {@code minecraft:mushroom_fields}.
     */
    public static final TypedKey<Biome> MUSHROOM_FIELDS = create("mushroom_fields");

    /**
     * Key for {@code minecraft:nether_wastes}.
     */
    public static final TypedKey<Biome> NETHER_WASTES = create("nether_wastes");

    /**
     * Key for {@code minecraft:ocean}.
     */
    public static final TypedKey<Biome> OCEAN = create("ocean");

    /**
     * Key for {@code minecraft:old_growth_birch_forest}.
     */
    public static final TypedKey<Biome> OLD_GROWTH_BIRCH_FOREST = create("old_growth_birch_forest");

    /**
     * Key for {@code minecraft:old_growth_pine_taiga}.
     */
    public static final TypedKey<Biome> OLD_GROWTH_PINE_TAIGA = create("old_growth_pine_taiga");

    /**
     * Key for {@code minecraft:old_growth_spruce_taiga}.
     */
    public static final TypedKey<Biome> OLD_GROWTH_SPRUCE_TAIGA = create("old_growth_spruce_taiga");

    /**
     * Key for {@code minecraft:pale_garden}.
     */
    public static final TypedKey<Biome> PALE_GARDEN = create("pale_garden");

    /**
     * Key for {@code minecraft:plains}.
     */
    public static final TypedKey<Biome> PLAINS = create("plains");

    /**
     * Key for {@code minecraft:river}.
     */
    public static final TypedKey<Biome> RIVER = create("river");

    /**
     * Key for {@code minecraft:savanna}.
     */
    public static final TypedKey<Biome> SAVANNA = create("savanna");

    /**
     * Key for {@code minecraft:savanna_plateau}.
     */
    public static final TypedKey<Biome> SAVANNA_PLATEAU = create("savanna_plateau");

    /**
     * Key for {@code minecraft:small_end_islands}.
     */
    public static final TypedKey<Biome> SMALL_END_ISLANDS = create("small_end_islands");

    /**
     * Key for {@code minecraft:snowy_beach}.
     */
    public static final TypedKey<Biome> SNOWY_BEACH = create("snowy_beach");

    /**
     * Key for {@code minecraft:snowy_plains}.
     */
    public static final TypedKey<Biome> SNOWY_PLAINS = create("snowy_plains");

    /**
     * Key for {@code minecraft:snowy_slopes}.
     */
    public static final TypedKey<Biome> SNOWY_SLOPES = create("snowy_slopes");

    /**
     * Key for {@code minecraft:snowy_taiga}.
     */
    public static final TypedKey<Biome> SNOWY_TAIGA = create("snowy_taiga");

    /**
     * Key for {@code minecraft:soul_sand_valley}.
     */
    public static final TypedKey<Biome> SOUL_SAND_VALLEY = create("soul_sand_valley");

    /**
     * Key for {@code minecraft:sparse_jungle}.
     */
    public static final TypedKey<Biome> SPARSE_JUNGLE = create("sparse_jungle");

    /**
     * Key for {@code minecraft:stony_peaks}.
     */
    public static final TypedKey<Biome> STONY_PEAKS = create("stony_peaks");

    /**
     * Key for {@code minecraft:stony_shore}.
     */
    public static final TypedKey<Biome> STONY_SHORE = create("stony_shore");

    /**
     * Key for {@code minecraft:sulfur_caves}.
     */
    public static final TypedKey<Biome> SULFUR_CAVES = create("sulfur_caves");

    /**
     * Key for {@code minecraft:sunflower_plains}.
     */
    public static final TypedKey<Biome> SUNFLOWER_PLAINS = create("sunflower_plains");

    /**
     * Key for {@code minecraft:swamp}.
     */
    public static final TypedKey<Biome> SWAMP = create("swamp");

    /**
     * Key for {@code minecraft:taiga}.
     */
    public static final TypedKey<Biome> TAIGA = create("taiga");

    /**
     * Key for {@code minecraft:the_end}.
     */
    public static final TypedKey<Biome> THE_END = create("the_end");

    /**
     * Key for {@code minecraft:the_void}.
     */
    public static final TypedKey<Biome> THE_VOID = create("the_void");

    /**
     * Key for {@code minecraft:warm_ocean}.
     */
    public static final TypedKey<Biome> WARM_OCEAN = create("warm_ocean");

    /**
     * Key for {@code minecraft:warped_forest}.
     */
    public static final TypedKey<Biome> WARPED_FOREST = create("warped_forest");

    /**
     * Key for {@code minecraft:windswept_forest}.
     */
    public static final TypedKey<Biome> WINDSWEPT_FOREST = create("windswept_forest");

    /**
     * Key for {@code minecraft:windswept_gravelly_hills}.
     */
    public static final TypedKey<Biome> WINDSWEPT_GRAVELLY_HILLS = create("windswept_gravelly_hills");

    /**
     * Key for {@code minecraft:windswept_hills}.
     */
    public static final TypedKey<Biome> WINDSWEPT_HILLS = create("windswept_hills");

    /**
     * Key for {@code minecraft:windswept_savanna}.
     */
    public static final TypedKey<Biome> WINDSWEPT_SAVANNA = create("windswept_savanna");

    /**
     * Key for {@code minecraft:wooded_badlands}.
     */
    public static final TypedKey<Biome> WOODED_BADLANDS = create("wooded_badlands");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<Biome>> VALUES = List.of(
        BADLANDS,
        BAMBOO_JUNGLE,
        BASALT_DELTAS,
        BEACH,
        BIRCH_FOREST,
        CHERRY_GROVE,
        COLD_OCEAN,
        CRIMSON_FOREST,
        DARK_FOREST,
        DEEP_COLD_OCEAN,
        DEEP_DARK,
        DEEP_FROZEN_OCEAN,
        DEEP_LUKEWARM_OCEAN,
        DEEP_OCEAN,
        DESERT,
        DRIPSTONE_CAVES,
        END_BARRENS,
        END_HIGHLANDS,
        END_MIDLANDS,
        ERODED_BADLANDS,
        FLOWER_FOREST,
        FOREST,
        FROZEN_OCEAN,
        FROZEN_PEAKS,
        FROZEN_RIVER,
        GROVE,
        ICE_SPIKES,
        JAGGED_PEAKS,
        JUNGLE,
        LUKEWARM_OCEAN,
        LUSH_CAVES,
        MANGROVE_SWAMP,
        MEADOW,
        MUSHROOM_FIELDS,
        NETHER_WASTES,
        OCEAN,
        OLD_GROWTH_BIRCH_FOREST,
        OLD_GROWTH_PINE_TAIGA,
        OLD_GROWTH_SPRUCE_TAIGA,
        PALE_GARDEN,
        PLAINS,
        RIVER,
        SAVANNA,
        SAVANNA_PLATEAU,
        SMALL_END_ISLANDS,
        SNOWY_BEACH,
        SNOWY_PLAINS,
        SNOWY_SLOPES,
        SNOWY_TAIGA,
        SOUL_SAND_VALLEY,
        SPARSE_JUNGLE,
        STONY_PEAKS,
        STONY_SHORE,
        SULFUR_CAVES,
        SUNFLOWER_PLAINS,
        SWAMP,
        TAIGA,
        THE_END,
        THE_VOID,
        WARM_OCEAN,
        WARPED_FOREST,
        WINDSWEPT_FOREST,
        WINDSWEPT_GRAVELLY_HILLS,
        WINDSWEPT_HILLS,
        WINDSWEPT_SAVANNA,
        WOODED_BADLANDS
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("allows_surface_slime_spawns"), List.of(Key.key("mangrove_swamp"), Key.key("swamp"))),
        Map.entry(Key.key("allows_tropical_fish_spawns_at_any_height"), List.of(Key.key("lush_caves"))),
        Map.entry(Key.key("has_structure/ancient_city"), List.of(Key.key("deep_dark"))),
        Map.entry(Key.key("has_structure/bastion_remnant"), List.of(Key.key("crimson_forest"), Key.key("nether_wastes"), Key.key("soul_sand_valley"), Key.key("warped_forest"))),
        Map.entry(Key.key("has_structure/buried_treasure"), List.of(Key.key("beach"), Key.key("snowy_beach"))),
        Map.entry(Key.key("has_structure/desert_pyramid"), List.of(Key.key("desert"))),
        Map.entry(Key.key("has_structure/end_city"), List.of(Key.key("end_highlands"), Key.key("end_midlands"))),
        Map.entry(Key.key("has_structure/igloo"), List.of(Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"))),
        Map.entry(Key.key("has_structure/jungle_temple"), List.of(Key.key("bamboo_jungle"), Key.key("jungle"))),
        Map.entry(Key.key("has_structure/mineshaft"), List.of(Key.key("bamboo_jungle"), Key.key("beach"), Key.key("birch_forest"), Key.key("cherry_grove"), Key.key("cold_ocean"), Key.key("dark_forest"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("desert"), Key.key("dripstone_caves"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("jungle"), Key.key("lukewarm_ocean"), Key.key("lush_caves"), Key.key("mangrove_swamp"), Key.key("meadow"), Key.key("mushroom_fields"), Key.key("ocean"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("river"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("sparse_jungle"), Key.key("stony_peaks"), Key.key("stony_shore"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("swamp"), Key.key("taiga"), Key.key("warm_ocean"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"))),
        Map.entry(Key.key("has_structure/mineshaft_mesa"), List.of(Key.key("badlands"), Key.key("eroded_badlands"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("has_structure/nether_fortress"), List.of(Key.key("basalt_deltas"), Key.key("crimson_forest"), Key.key("nether_wastes"), Key.key("soul_sand_valley"), Key.key("warped_forest"))),
        Map.entry(Key.key("has_structure/nether_fossil"), List.of(Key.key("soul_sand_valley"))),
        Map.entry(Key.key("has_structure/ocean_monument"), List.of(Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"))),
        Map.entry(Key.key("has_structure/ocean_ruin_cold"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("ocean"))),
        Map.entry(Key.key("has_structure/ocean_ruin_warm"), List.of(Key.key("deep_lukewarm_ocean"), Key.key("lukewarm_ocean"), Key.key("warm_ocean"))),
        Map.entry(Key.key("has_structure/pillager_outpost"), List.of(Key.key("cherry_grove"), Key.key("desert"), Key.key("frozen_peaks"), Key.key("grove"), Key.key("jagged_peaks"), Key.key("meadow"), Key.key("plains"), Key.key("savanna"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("stony_peaks"), Key.key("taiga"))),
        Map.entry(Key.key("has_structure/ruined_portal_desert"), List.of(Key.key("desert"))),
        Map.entry(Key.key("has_structure/ruined_portal_jungle"), List.of(Key.key("bamboo_jungle"), Key.key("jungle"), Key.key("sparse_jungle"))),
        Map.entry(Key.key("has_structure/ruined_portal_mountain"), List.of(Key.key("badlands"), Key.key("cherry_grove"), Key.key("eroded_badlands"), Key.key("frozen_peaks"), Key.key("jagged_peaks"), Key.key("meadow"), Key.key("savanna_plateau"), Key.key("snowy_slopes"), Key.key("stony_peaks"), Key.key("stony_shore"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("has_structure/ruined_portal_nether"), List.of(Key.key("basalt_deltas"), Key.key("crimson_forest"), Key.key("nether_wastes"), Key.key("soul_sand_valley"), Key.key("warped_forest"))),
        Map.entry(Key.key("has_structure/ruined_portal_ocean"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("lukewarm_ocean"), Key.key("ocean"), Key.key("warm_ocean"))),
        Map.entry(Key.key("has_structure/ruined_portal_standard"), List.of(Key.key("beach"), Key.key("birch_forest"), Key.key("dark_forest"), Key.key("dripstone_caves"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("lush_caves"), Key.key("mushroom_fields"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("river"), Key.key("savanna"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_taiga"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("taiga"))),
        Map.entry(Key.key("has_structure/ruined_portal_swamp"), List.of(Key.key("mangrove_swamp"), Key.key("swamp"))),
        Map.entry(Key.key("has_structure/shipwreck"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("lukewarm_ocean"), Key.key("ocean"), Key.key("warm_ocean"))),
        Map.entry(Key.key("has_structure/shipwreck_beached"), List.of(Key.key("beach"), Key.key("snowy_beach"))),
        Map.entry(Key.key("has_structure/stronghold"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("beach"), Key.key("birch_forest"), Key.key("cherry_grove"), Key.key("cold_ocean"), Key.key("dark_forest"), Key.key("deep_cold_ocean"), Key.key("deep_dark"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("desert"), Key.key("dripstone_caves"), Key.key("eroded_badlands"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("jungle"), Key.key("lukewarm_ocean"), Key.key("lush_caves"), Key.key("mangrove_swamp"), Key.key("meadow"), Key.key("mushroom_fields"), Key.key("ocean"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("river"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("sparse_jungle"), Key.key("stony_peaks"), Key.key("stony_shore"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("swamp"), Key.key("taiga"), Key.key("warm_ocean"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("has_structure/swamp_hut"), List.of(Key.key("swamp"))),
        Map.entry(Key.key("has_structure/trail_ruins"), List.of(Key.key("jungle"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("snowy_taiga"), Key.key("taiga"))),
        Map.entry(Key.key("has_structure/trial_chambers"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("beach"), Key.key("birch_forest"), Key.key("cherry_grove"), Key.key("cold_ocean"), Key.key("dark_forest"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("desert"), Key.key("dripstone_caves"), Key.key("eroded_badlands"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("jungle"), Key.key("lukewarm_ocean"), Key.key("lush_caves"), Key.key("mangrove_swamp"), Key.key("meadow"), Key.key("mushroom_fields"), Key.key("ocean"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("river"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("sparse_jungle"), Key.key("stony_peaks"), Key.key("stony_shore"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("swamp"), Key.key("taiga"), Key.key("warm_ocean"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("has_structure/village_desert"), List.of(Key.key("desert"))),
        Map.entry(Key.key("has_structure/village_plains"), List.of(Key.key("meadow"), Key.key("plains"))),
        Map.entry(Key.key("has_structure/village_savanna"), List.of(Key.key("savanna"))),
        Map.entry(Key.key("has_structure/village_snowy"), List.of(Key.key("snowy_plains"))),
        Map.entry(Key.key("has_structure/village_taiga"), List.of(Key.key("taiga"))),
        Map.entry(Key.key("has_structure/woodland_mansion"), List.of(Key.key("dark_forest"), Key.key("pale_garden"))),
        Map.entry(Key.key("is_badlands"), List.of(Key.key("badlands"), Key.key("eroded_badlands"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("is_beach"), List.of(Key.key("beach"), Key.key("snowy_beach"))),
        Map.entry(Key.key("is_deep_ocean"), List.of(Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"))),
        Map.entry(Key.key("is_end"), List.of(Key.key("end_barrens"), Key.key("end_highlands"), Key.key("end_midlands"), Key.key("small_end_islands"), Key.key("the_end"))),
        Map.entry(Key.key("is_forest"), List.of(Key.key("birch_forest"), Key.key("dark_forest"), Key.key("flower_forest"), Key.key("forest"), Key.key("grove"), Key.key("old_growth_birch_forest"), Key.key("pale_garden"))),
        Map.entry(Key.key("is_hill"), List.of(Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"))),
        Map.entry(Key.key("is_jungle"), List.of(Key.key("bamboo_jungle"), Key.key("jungle"), Key.key("sparse_jungle"))),
        Map.entry(Key.key("is_mountain"), List.of(Key.key("cherry_grove"), Key.key("frozen_peaks"), Key.key("jagged_peaks"), Key.key("meadow"), Key.key("snowy_slopes"), Key.key("stony_peaks"))),
        Map.entry(Key.key("is_nether"), List.of(Key.key("basalt_deltas"), Key.key("crimson_forest"), Key.key("nether_wastes"), Key.key("soul_sand_valley"), Key.key("warped_forest"))),
        Map.entry(Key.key("is_ocean"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("lukewarm_ocean"), Key.key("ocean"), Key.key("warm_ocean"))),
        Map.entry(Key.key("is_overworld"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("beach"), Key.key("birch_forest"), Key.key("cherry_grove"), Key.key("cold_ocean"), Key.key("dark_forest"), Key.key("deep_cold_ocean"), Key.key("deep_dark"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("desert"), Key.key("dripstone_caves"), Key.key("eroded_badlands"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("jungle"), Key.key("lukewarm_ocean"), Key.key("lush_caves"), Key.key("mangrove_swamp"), Key.key("meadow"), Key.key("mushroom_fields"), Key.key("ocean"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("river"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("sparse_jungle"), Key.key("stony_peaks"), Key.key("stony_shore"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("swamp"), Key.key("taiga"), Key.key("warm_ocean"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("is_river"), List.of(Key.key("frozen_river"), Key.key("river"))),
        Map.entry(Key.key("is_savanna"), List.of(Key.key("savanna"), Key.key("savanna_plateau"), Key.key("windswept_savanna"))),
        Map.entry(Key.key("is_taiga"), List.of(Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("snowy_taiga"), Key.key("taiga"))),
        Map.entry(Key.key("mineshaft_blocking"), List.of(Key.key("deep_dark"))),
        Map.entry(Key.key("more_frequent_drowned_spawns"), List.of(Key.key("frozen_river"), Key.key("river"))),
        Map.entry(Key.key("polar_bears_spawn_on_alternate_blocks"), List.of(Key.key("deep_frozen_ocean"), Key.key("frozen_ocean"))),
        Map.entry(Key.key("produces_corals_from_bonemeal"), List.of(Key.key("warm_ocean"))),
        Map.entry(Key.key("reduce_water_ambient_spawns"), List.of(Key.key("frozen_river"), Key.key("river"))),
        Map.entry(Key.key("required_ocean_monument_surrounding"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("frozen_river"), Key.key("lukewarm_ocean"), Key.key("ocean"), Key.key("river"), Key.key("warm_ocean"))),
        Map.entry(Key.key("spawns_cold_variant_farm_animals"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_dark"), Key.key("deep_frozen_ocean"), Key.key("end_barrens"), Key.key("end_highlands"), Key.key("end_midlands"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("small_end_islands"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("stony_peaks"), Key.key("taiga"), Key.key("the_end"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"))),
        Map.entry(Key.key("spawns_cold_variant_frogs"), List.of(Key.key("deep_dark"), Key.key("deep_frozen_ocean"), Key.key("end_barrens"), Key.key("end_highlands"), Key.key("end_midlands"), Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("small_end_islands"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("the_end"))),
        Map.entry(Key.key("spawns_coral_variant_zombie_nautilus"), List.of(Key.key("warm_ocean"))),
        Map.entry(Key.key("spawns_gold_rabbits"), List.of(Key.key("desert"))),
        Map.entry(Key.key("spawns_snow_foxes"), List.of(Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"))),
        Map.entry(Key.key("spawns_warm_variant_farm_animals"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("basalt_deltas"), Key.key("crimson_forest"), Key.key("deep_lukewarm_ocean"), Key.key("desert"), Key.key("eroded_badlands"), Key.key("jungle"), Key.key("lukewarm_ocean"), Key.key("mangrove_swamp"), Key.key("nether_wastes"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("soul_sand_valley"), Key.key("sparse_jungle"), Key.key("warm_ocean"), Key.key("warped_forest"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("spawns_warm_variant_frogs"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("basalt_deltas"), Key.key("crimson_forest"), Key.key("desert"), Key.key("eroded_badlands"), Key.key("jungle"), Key.key("mangrove_swamp"), Key.key("nether_wastes"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("soul_sand_valley"), Key.key("sparse_jungle"), Key.key("warm_ocean"), Key.key("warped_forest"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("spawns_white_rabbits"), List.of(Key.key("frozen_ocean"), Key.key("frozen_peaks"), Key.key("frozen_river"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("snowy_beach"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"))),
        Map.entry(Key.key("stronghold_biased_to"), List.of(Key.key("badlands"), Key.key("bamboo_jungle"), Key.key("birch_forest"), Key.key("cherry_grove"), Key.key("dark_forest"), Key.key("desert"), Key.key("dripstone_caves"), Key.key("eroded_badlands"), Key.key("flower_forest"), Key.key("forest"), Key.key("frozen_peaks"), Key.key("grove"), Key.key("ice_spikes"), Key.key("jagged_peaks"), Key.key("jungle"), Key.key("lush_caves"), Key.key("meadow"), Key.key("mushroom_fields"), Key.key("old_growth_birch_forest"), Key.key("old_growth_pine_taiga"), Key.key("old_growth_spruce_taiga"), Key.key("pale_garden"), Key.key("plains"), Key.key("savanna"), Key.key("savanna_plateau"), Key.key("snowy_plains"), Key.key("snowy_slopes"), Key.key("snowy_taiga"), Key.key("sparse_jungle"), Key.key("stony_peaks"), Key.key("sulfur_caves"), Key.key("sunflower_plains"), Key.key("taiga"), Key.key("windswept_forest"), Key.key("windswept_gravelly_hills"), Key.key("windswept_hills"), Key.key("windswept_savanna"), Key.key("wooded_badlands"))),
        Map.entry(Key.key("water_on_map_outlines"), List.of(Key.key("cold_ocean"), Key.key("deep_cold_ocean"), Key.key("deep_frozen_ocean"), Key.key("deep_lukewarm_ocean"), Key.key("deep_ocean"), Key.key("frozen_ocean"), Key.key("frozen_river"), Key.key("lukewarm_ocean"), Key.key("mangrove_swamp"), Key.key("ocean"), Key.key("river"), Key.key("swamp"), Key.key("warm_ocean"))),
        Map.entry(Key.key("without_wandering_trader_spawns"), List.of(Key.key("the_void"))),
        Map.entry(Key.key("without_zombie_sieges"), List.of(Key.key("mushroom_fields")))
    );

    private BiomeKeys() {
        throw new UnsupportedOperationException("BiomeKeys cannot be instantiated.");
    }

    private static TypedKey<Biome> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.BIOME, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<Biome>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return TAGS;
    }
}
