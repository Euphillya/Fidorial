package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.BlockType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:block} registry.
 */
public final class BlockTypeKeys {
    /**
     * Key for {@code minecraft:acacia_button}.
     */
    public static final TypedKey<BlockType> ACACIA_BUTTON = create("acacia_button");

    /**
     * Key for {@code minecraft:acacia_door}.
     */
    public static final TypedKey<BlockType> ACACIA_DOOR = create("acacia_door");

    /**
     * Key for {@code minecraft:acacia_fence}.
     */
    public static final TypedKey<BlockType> ACACIA_FENCE = create("acacia_fence");

    /**
     * Key for {@code minecraft:acacia_fence_gate}.
     */
    public static final TypedKey<BlockType> ACACIA_FENCE_GATE = create("acacia_fence_gate");

    /**
     * Key for {@code minecraft:acacia_hanging_sign}.
     */
    public static final TypedKey<BlockType> ACACIA_HANGING_SIGN = create("acacia_hanging_sign");

    /**
     * Key for {@code minecraft:acacia_leaves}.
     */
    public static final TypedKey<BlockType> ACACIA_LEAVES = create("acacia_leaves");

    /**
     * Key for {@code minecraft:acacia_log}.
     */
    public static final TypedKey<BlockType> ACACIA_LOG = create("acacia_log");

    /**
     * Key for {@code minecraft:acacia_planks}.
     */
    public static final TypedKey<BlockType> ACACIA_PLANKS = create("acacia_planks");

    /**
     * Key for {@code minecraft:acacia_pressure_plate}.
     */
    public static final TypedKey<BlockType> ACACIA_PRESSURE_PLATE = create("acacia_pressure_plate");

    /**
     * Key for {@code minecraft:acacia_sapling}.
     */
    public static final TypedKey<BlockType> ACACIA_SAPLING = create("acacia_sapling");

    /**
     * Key for {@code minecraft:acacia_shelf}.
     */
    public static final TypedKey<BlockType> ACACIA_SHELF = create("acacia_shelf");

    /**
     * Key for {@code minecraft:acacia_sign}.
     */
    public static final TypedKey<BlockType> ACACIA_SIGN = create("acacia_sign");

    /**
     * Key for {@code minecraft:acacia_slab}.
     */
    public static final TypedKey<BlockType> ACACIA_SLAB = create("acacia_slab");

    /**
     * Key for {@code minecraft:acacia_stairs}.
     */
    public static final TypedKey<BlockType> ACACIA_STAIRS = create("acacia_stairs");

    /**
     * Key for {@code minecraft:acacia_trapdoor}.
     */
    public static final TypedKey<BlockType> ACACIA_TRAPDOOR = create("acacia_trapdoor");

    /**
     * Key for {@code minecraft:acacia_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> ACACIA_WALL_HANGING_SIGN = create("acacia_wall_hanging_sign");

    /**
     * Key for {@code minecraft:acacia_wall_sign}.
     */
    public static final TypedKey<BlockType> ACACIA_WALL_SIGN = create("acacia_wall_sign");

    /**
     * Key for {@code minecraft:acacia_wood}.
     */
    public static final TypedKey<BlockType> ACACIA_WOOD = create("acacia_wood");

    /**
     * Key for {@code minecraft:activator_rail}.
     */
    public static final TypedKey<BlockType> ACTIVATOR_RAIL = create("activator_rail");

    /**
     * Key for {@code minecraft:air}.
     */
    public static final TypedKey<BlockType> AIR = create("air");

    /**
     * Key for {@code minecraft:allium}.
     */
    public static final TypedKey<BlockType> ALLIUM = create("allium");

    /**
     * Key for {@code minecraft:amethyst_block}.
     */
    public static final TypedKey<BlockType> AMETHYST_BLOCK = create("amethyst_block");

    /**
     * Key for {@code minecraft:amethyst_cluster}.
     */
    public static final TypedKey<BlockType> AMETHYST_CLUSTER = create("amethyst_cluster");

    /**
     * Key for {@code minecraft:ancient_debris}.
     */
    public static final TypedKey<BlockType> ANCIENT_DEBRIS = create("ancient_debris");

    /**
     * Key for {@code minecraft:andesite}.
     */
    public static final TypedKey<BlockType> ANDESITE = create("andesite");

    /**
     * Key for {@code minecraft:andesite_slab}.
     */
    public static final TypedKey<BlockType> ANDESITE_SLAB = create("andesite_slab");

    /**
     * Key for {@code minecraft:andesite_stairs}.
     */
    public static final TypedKey<BlockType> ANDESITE_STAIRS = create("andesite_stairs");

    /**
     * Key for {@code minecraft:andesite_wall}.
     */
    public static final TypedKey<BlockType> ANDESITE_WALL = create("andesite_wall");

    /**
     * Key for {@code minecraft:anvil}.
     */
    public static final TypedKey<BlockType> ANVIL = create("anvil");

    /**
     * Key for {@code minecraft:attached_melon_stem}.
     */
    public static final TypedKey<BlockType> ATTACHED_MELON_STEM = create("attached_melon_stem");

    /**
     * Key for {@code minecraft:attached_pumpkin_stem}.
     */
    public static final TypedKey<BlockType> ATTACHED_PUMPKIN_STEM = create("attached_pumpkin_stem");

    /**
     * Key for {@code minecraft:azalea}.
     */
    public static final TypedKey<BlockType> AZALEA = create("azalea");

    /**
     * Key for {@code minecraft:azalea_leaves}.
     */
    public static final TypedKey<BlockType> AZALEA_LEAVES = create("azalea_leaves");

    /**
     * Key for {@code minecraft:azure_bluet}.
     */
    public static final TypedKey<BlockType> AZURE_BLUET = create("azure_bluet");

    /**
     * Key for {@code minecraft:bamboo}.
     */
    public static final TypedKey<BlockType> BAMBOO = create("bamboo");

    /**
     * Key for {@code minecraft:bamboo_block}.
     */
    public static final TypedKey<BlockType> BAMBOO_BLOCK = create("bamboo_block");

    /**
     * Key for {@code minecraft:bamboo_button}.
     */
    public static final TypedKey<BlockType> BAMBOO_BUTTON = create("bamboo_button");

    /**
     * Key for {@code minecraft:bamboo_door}.
     */
    public static final TypedKey<BlockType> BAMBOO_DOOR = create("bamboo_door");

    /**
     * Key for {@code minecraft:bamboo_fence}.
     */
    public static final TypedKey<BlockType> BAMBOO_FENCE = create("bamboo_fence");

    /**
     * Key for {@code minecraft:bamboo_fence_gate}.
     */
    public static final TypedKey<BlockType> BAMBOO_FENCE_GATE = create("bamboo_fence_gate");

    /**
     * Key for {@code minecraft:bamboo_hanging_sign}.
     */
    public static final TypedKey<BlockType> BAMBOO_HANGING_SIGN = create("bamboo_hanging_sign");

    /**
     * Key for {@code minecraft:bamboo_mosaic}.
     */
    public static final TypedKey<BlockType> BAMBOO_MOSAIC = create("bamboo_mosaic");

    /**
     * Key for {@code minecraft:bamboo_mosaic_slab}.
     */
    public static final TypedKey<BlockType> BAMBOO_MOSAIC_SLAB = create("bamboo_mosaic_slab");

    /**
     * Key for {@code minecraft:bamboo_mosaic_stairs}.
     */
    public static final TypedKey<BlockType> BAMBOO_MOSAIC_STAIRS = create("bamboo_mosaic_stairs");

    /**
     * Key for {@code minecraft:bamboo_planks}.
     */
    public static final TypedKey<BlockType> BAMBOO_PLANKS = create("bamboo_planks");

    /**
     * Key for {@code minecraft:bamboo_pressure_plate}.
     */
    public static final TypedKey<BlockType> BAMBOO_PRESSURE_PLATE = create("bamboo_pressure_plate");

    /**
     * Key for {@code minecraft:bamboo_sapling}.
     */
    public static final TypedKey<BlockType> BAMBOO_SAPLING = create("bamboo_sapling");

    /**
     * Key for {@code minecraft:bamboo_shelf}.
     */
    public static final TypedKey<BlockType> BAMBOO_SHELF = create("bamboo_shelf");

    /**
     * Key for {@code minecraft:bamboo_sign}.
     */
    public static final TypedKey<BlockType> BAMBOO_SIGN = create("bamboo_sign");

    /**
     * Key for {@code minecraft:bamboo_slab}.
     */
    public static final TypedKey<BlockType> BAMBOO_SLAB = create("bamboo_slab");

    /**
     * Key for {@code minecraft:bamboo_stairs}.
     */
    public static final TypedKey<BlockType> BAMBOO_STAIRS = create("bamboo_stairs");

    /**
     * Key for {@code minecraft:bamboo_trapdoor}.
     */
    public static final TypedKey<BlockType> BAMBOO_TRAPDOOR = create("bamboo_trapdoor");

    /**
     * Key for {@code minecraft:bamboo_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> BAMBOO_WALL_HANGING_SIGN = create("bamboo_wall_hanging_sign");

    /**
     * Key for {@code minecraft:bamboo_wall_sign}.
     */
    public static final TypedKey<BlockType> BAMBOO_WALL_SIGN = create("bamboo_wall_sign");

    /**
     * Key for {@code minecraft:barrel}.
     */
    public static final TypedKey<BlockType> BARREL = create("barrel");

    /**
     * Key for {@code minecraft:barrier}.
     */
    public static final TypedKey<BlockType> BARRIER = create("barrier");

    /**
     * Key for {@code minecraft:basalt}.
     */
    public static final TypedKey<BlockType> BASALT = create("basalt");

    /**
     * Key for {@code minecraft:beacon}.
     */
    public static final TypedKey<BlockType> BEACON = create("beacon");

    /**
     * Key for {@code minecraft:bedrock}.
     */
    public static final TypedKey<BlockType> BEDROCK = create("bedrock");

    /**
     * Key for {@code minecraft:beehive}.
     */
    public static final TypedKey<BlockType> BEEHIVE = create("beehive");

    /**
     * Key for {@code minecraft:beetroots}.
     */
    public static final TypedKey<BlockType> BEETROOTS = create("beetroots");

    /**
     * Key for {@code minecraft:bee_nest}.
     */
    public static final TypedKey<BlockType> BEE_NEST = create("bee_nest");

    /**
     * Key for {@code minecraft:bell}.
     */
    public static final TypedKey<BlockType> BELL = create("bell");

    /**
     * Key for {@code minecraft:big_dripleaf}.
     */
    public static final TypedKey<BlockType> BIG_DRIPLEAF = create("big_dripleaf");

    /**
     * Key for {@code minecraft:big_dripleaf_stem}.
     */
    public static final TypedKey<BlockType> BIG_DRIPLEAF_STEM = create("big_dripleaf_stem");

    /**
     * Key for {@code minecraft:birch_button}.
     */
    public static final TypedKey<BlockType> BIRCH_BUTTON = create("birch_button");

    /**
     * Key for {@code minecraft:birch_door}.
     */
    public static final TypedKey<BlockType> BIRCH_DOOR = create("birch_door");

    /**
     * Key for {@code minecraft:birch_fence}.
     */
    public static final TypedKey<BlockType> BIRCH_FENCE = create("birch_fence");

    /**
     * Key for {@code minecraft:birch_fence_gate}.
     */
    public static final TypedKey<BlockType> BIRCH_FENCE_GATE = create("birch_fence_gate");

    /**
     * Key for {@code minecraft:birch_hanging_sign}.
     */
    public static final TypedKey<BlockType> BIRCH_HANGING_SIGN = create("birch_hanging_sign");

    /**
     * Key for {@code minecraft:birch_leaves}.
     */
    public static final TypedKey<BlockType> BIRCH_LEAVES = create("birch_leaves");

    /**
     * Key for {@code minecraft:birch_log}.
     */
    public static final TypedKey<BlockType> BIRCH_LOG = create("birch_log");

    /**
     * Key for {@code minecraft:birch_planks}.
     */
    public static final TypedKey<BlockType> BIRCH_PLANKS = create("birch_planks");

    /**
     * Key for {@code minecraft:birch_pressure_plate}.
     */
    public static final TypedKey<BlockType> BIRCH_PRESSURE_PLATE = create("birch_pressure_plate");

    /**
     * Key for {@code minecraft:birch_sapling}.
     */
    public static final TypedKey<BlockType> BIRCH_SAPLING = create("birch_sapling");

    /**
     * Key for {@code minecraft:birch_shelf}.
     */
    public static final TypedKey<BlockType> BIRCH_SHELF = create("birch_shelf");

    /**
     * Key for {@code minecraft:birch_sign}.
     */
    public static final TypedKey<BlockType> BIRCH_SIGN = create("birch_sign");

    /**
     * Key for {@code minecraft:birch_slab}.
     */
    public static final TypedKey<BlockType> BIRCH_SLAB = create("birch_slab");

    /**
     * Key for {@code minecraft:birch_stairs}.
     */
    public static final TypedKey<BlockType> BIRCH_STAIRS = create("birch_stairs");

    /**
     * Key for {@code minecraft:birch_trapdoor}.
     */
    public static final TypedKey<BlockType> BIRCH_TRAPDOOR = create("birch_trapdoor");

    /**
     * Key for {@code minecraft:birch_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> BIRCH_WALL_HANGING_SIGN = create("birch_wall_hanging_sign");

    /**
     * Key for {@code minecraft:birch_wall_sign}.
     */
    public static final TypedKey<BlockType> BIRCH_WALL_SIGN = create("birch_wall_sign");

    /**
     * Key for {@code minecraft:birch_wood}.
     */
    public static final TypedKey<BlockType> BIRCH_WOOD = create("birch_wood");

    /**
     * Key for {@code minecraft:blackstone}.
     */
    public static final TypedKey<BlockType> BLACKSTONE = create("blackstone");

    /**
     * Key for {@code minecraft:blackstone_slab}.
     */
    public static final TypedKey<BlockType> BLACKSTONE_SLAB = create("blackstone_slab");

    /**
     * Key for {@code minecraft:blackstone_stairs}.
     */
    public static final TypedKey<BlockType> BLACKSTONE_STAIRS = create("blackstone_stairs");

    /**
     * Key for {@code minecraft:blackstone_wall}.
     */
    public static final TypedKey<BlockType> BLACKSTONE_WALL = create("blackstone_wall");

    /**
     * Key for {@code minecraft:black_banner}.
     */
    public static final TypedKey<BlockType> BLACK_BANNER = create("black_banner");

    /**
     * Key for {@code minecraft:black_bed}.
     */
    public static final TypedKey<BlockType> BLACK_BED = create("black_bed");

    /**
     * Key for {@code minecraft:black_candle}.
     */
    public static final TypedKey<BlockType> BLACK_CANDLE = create("black_candle");

    /**
     * Key for {@code minecraft:black_candle_cake}.
     */
    public static final TypedKey<BlockType> BLACK_CANDLE_CAKE = create("black_candle_cake");

    /**
     * Key for {@code minecraft:black_carpet}.
     */
    public static final TypedKey<BlockType> BLACK_CARPET = create("black_carpet");

    /**
     * Key for {@code minecraft:black_concrete}.
     */
    public static final TypedKey<BlockType> BLACK_CONCRETE = create("black_concrete");

    /**
     * Key for {@code minecraft:black_concrete_powder}.
     */
    public static final TypedKey<BlockType> BLACK_CONCRETE_POWDER = create("black_concrete_powder");

    /**
     * Key for {@code minecraft:black_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> BLACK_GLAZED_TERRACOTTA = create("black_glazed_terracotta");

    /**
     * Key for {@code minecraft:black_shulker_box}.
     */
    public static final TypedKey<BlockType> BLACK_SHULKER_BOX = create("black_shulker_box");

    /**
     * Key for {@code minecraft:black_stained_glass}.
     */
    public static final TypedKey<BlockType> BLACK_STAINED_GLASS = create("black_stained_glass");

    /**
     * Key for {@code minecraft:black_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> BLACK_STAINED_GLASS_PANE = create("black_stained_glass_pane");

    /**
     * Key for {@code minecraft:black_terracotta}.
     */
    public static final TypedKey<BlockType> BLACK_TERRACOTTA = create("black_terracotta");

    /**
     * Key for {@code minecraft:black_wall_banner}.
     */
    public static final TypedKey<BlockType> BLACK_WALL_BANNER = create("black_wall_banner");

    /**
     * Key for {@code minecraft:black_wool}.
     */
    public static final TypedKey<BlockType> BLACK_WOOL = create("black_wool");

    /**
     * Key for {@code minecraft:blast_furnace}.
     */
    public static final TypedKey<BlockType> BLAST_FURNACE = create("blast_furnace");

    /**
     * Key for {@code minecraft:blue_banner}.
     */
    public static final TypedKey<BlockType> BLUE_BANNER = create("blue_banner");

    /**
     * Key for {@code minecraft:blue_bed}.
     */
    public static final TypedKey<BlockType> BLUE_BED = create("blue_bed");

    /**
     * Key for {@code minecraft:blue_candle}.
     */
    public static final TypedKey<BlockType> BLUE_CANDLE = create("blue_candle");

    /**
     * Key for {@code minecraft:blue_candle_cake}.
     */
    public static final TypedKey<BlockType> BLUE_CANDLE_CAKE = create("blue_candle_cake");

    /**
     * Key for {@code minecraft:blue_carpet}.
     */
    public static final TypedKey<BlockType> BLUE_CARPET = create("blue_carpet");

    /**
     * Key for {@code minecraft:blue_concrete}.
     */
    public static final TypedKey<BlockType> BLUE_CONCRETE = create("blue_concrete");

    /**
     * Key for {@code minecraft:blue_concrete_powder}.
     */
    public static final TypedKey<BlockType> BLUE_CONCRETE_POWDER = create("blue_concrete_powder");

    /**
     * Key for {@code minecraft:blue_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> BLUE_GLAZED_TERRACOTTA = create("blue_glazed_terracotta");

    /**
     * Key for {@code minecraft:blue_ice}.
     */
    public static final TypedKey<BlockType> BLUE_ICE = create("blue_ice");

    /**
     * Key for {@code minecraft:blue_orchid}.
     */
    public static final TypedKey<BlockType> BLUE_ORCHID = create("blue_orchid");

    /**
     * Key for {@code minecraft:blue_shulker_box}.
     */
    public static final TypedKey<BlockType> BLUE_SHULKER_BOX = create("blue_shulker_box");

    /**
     * Key for {@code minecraft:blue_stained_glass}.
     */
    public static final TypedKey<BlockType> BLUE_STAINED_GLASS = create("blue_stained_glass");

    /**
     * Key for {@code minecraft:blue_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> BLUE_STAINED_GLASS_PANE = create("blue_stained_glass_pane");

    /**
     * Key for {@code minecraft:blue_terracotta}.
     */
    public static final TypedKey<BlockType> BLUE_TERRACOTTA = create("blue_terracotta");

    /**
     * Key for {@code minecraft:blue_wall_banner}.
     */
    public static final TypedKey<BlockType> BLUE_WALL_BANNER = create("blue_wall_banner");

    /**
     * Key for {@code minecraft:blue_wool}.
     */
    public static final TypedKey<BlockType> BLUE_WOOL = create("blue_wool");

    /**
     * Key for {@code minecraft:bone_block}.
     */
    public static final TypedKey<BlockType> BONE_BLOCK = create("bone_block");

    /**
     * Key for {@code minecraft:bookshelf}.
     */
    public static final TypedKey<BlockType> BOOKSHELF = create("bookshelf");

    /**
     * Key for {@code minecraft:brain_coral}.
     */
    public static final TypedKey<BlockType> BRAIN_CORAL = create("brain_coral");

    /**
     * Key for {@code minecraft:brain_coral_block}.
     */
    public static final TypedKey<BlockType> BRAIN_CORAL_BLOCK = create("brain_coral_block");

    /**
     * Key for {@code minecraft:brain_coral_fan}.
     */
    public static final TypedKey<BlockType> BRAIN_CORAL_FAN = create("brain_coral_fan");

    /**
     * Key for {@code minecraft:brain_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> BRAIN_CORAL_WALL_FAN = create("brain_coral_wall_fan");

    /**
     * Key for {@code minecraft:brewing_stand}.
     */
    public static final TypedKey<BlockType> BREWING_STAND = create("brewing_stand");

    /**
     * Key for {@code minecraft:bricks}.
     */
    public static final TypedKey<BlockType> BRICKS = create("bricks");

    /**
     * Key for {@code minecraft:brick_slab}.
     */
    public static final TypedKey<BlockType> BRICK_SLAB = create("brick_slab");

    /**
     * Key for {@code minecraft:brick_stairs}.
     */
    public static final TypedKey<BlockType> BRICK_STAIRS = create("brick_stairs");

    /**
     * Key for {@code minecraft:brick_wall}.
     */
    public static final TypedKey<BlockType> BRICK_WALL = create("brick_wall");

    /**
     * Key for {@code minecraft:brown_banner}.
     */
    public static final TypedKey<BlockType> BROWN_BANNER = create("brown_banner");

    /**
     * Key for {@code minecraft:brown_bed}.
     */
    public static final TypedKey<BlockType> BROWN_BED = create("brown_bed");

    /**
     * Key for {@code minecraft:brown_candle}.
     */
    public static final TypedKey<BlockType> BROWN_CANDLE = create("brown_candle");

    /**
     * Key for {@code minecraft:brown_candle_cake}.
     */
    public static final TypedKey<BlockType> BROWN_CANDLE_CAKE = create("brown_candle_cake");

    /**
     * Key for {@code minecraft:brown_carpet}.
     */
    public static final TypedKey<BlockType> BROWN_CARPET = create("brown_carpet");

    /**
     * Key for {@code minecraft:brown_concrete}.
     */
    public static final TypedKey<BlockType> BROWN_CONCRETE = create("brown_concrete");

    /**
     * Key for {@code minecraft:brown_concrete_powder}.
     */
    public static final TypedKey<BlockType> BROWN_CONCRETE_POWDER = create("brown_concrete_powder");

    /**
     * Key for {@code minecraft:brown_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> BROWN_GLAZED_TERRACOTTA = create("brown_glazed_terracotta");

    /**
     * Key for {@code minecraft:brown_mushroom}.
     */
    public static final TypedKey<BlockType> BROWN_MUSHROOM = create("brown_mushroom");

    /**
     * Key for {@code minecraft:brown_mushroom_block}.
     */
    public static final TypedKey<BlockType> BROWN_MUSHROOM_BLOCK = create("brown_mushroom_block");

    /**
     * Key for {@code minecraft:brown_shulker_box}.
     */
    public static final TypedKey<BlockType> BROWN_SHULKER_BOX = create("brown_shulker_box");

    /**
     * Key for {@code minecraft:brown_stained_glass}.
     */
    public static final TypedKey<BlockType> BROWN_STAINED_GLASS = create("brown_stained_glass");

    /**
     * Key for {@code minecraft:brown_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> BROWN_STAINED_GLASS_PANE = create("brown_stained_glass_pane");

    /**
     * Key for {@code minecraft:brown_terracotta}.
     */
    public static final TypedKey<BlockType> BROWN_TERRACOTTA = create("brown_terracotta");

    /**
     * Key for {@code minecraft:brown_wall_banner}.
     */
    public static final TypedKey<BlockType> BROWN_WALL_BANNER = create("brown_wall_banner");

    /**
     * Key for {@code minecraft:brown_wool}.
     */
    public static final TypedKey<BlockType> BROWN_WOOL = create("brown_wool");

    /**
     * Key for {@code minecraft:bubble_column}.
     */
    public static final TypedKey<BlockType> BUBBLE_COLUMN = create("bubble_column");

    /**
     * Key for {@code minecraft:bubble_coral}.
     */
    public static final TypedKey<BlockType> BUBBLE_CORAL = create("bubble_coral");

    /**
     * Key for {@code minecraft:bubble_coral_block}.
     */
    public static final TypedKey<BlockType> BUBBLE_CORAL_BLOCK = create("bubble_coral_block");

    /**
     * Key for {@code minecraft:bubble_coral_fan}.
     */
    public static final TypedKey<BlockType> BUBBLE_CORAL_FAN = create("bubble_coral_fan");

    /**
     * Key for {@code minecraft:bubble_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> BUBBLE_CORAL_WALL_FAN = create("bubble_coral_wall_fan");

    /**
     * Key for {@code minecraft:budding_amethyst}.
     */
    public static final TypedKey<BlockType> BUDDING_AMETHYST = create("budding_amethyst");

    /**
     * Key for {@code minecraft:bush}.
     */
    public static final TypedKey<BlockType> BUSH = create("bush");

    /**
     * Key for {@code minecraft:cactus}.
     */
    public static final TypedKey<BlockType> CACTUS = create("cactus");

    /**
     * Key for {@code minecraft:cactus_flower}.
     */
    public static final TypedKey<BlockType> CACTUS_FLOWER = create("cactus_flower");

    /**
     * Key for {@code minecraft:cake}.
     */
    public static final TypedKey<BlockType> CAKE = create("cake");

    /**
     * Key for {@code minecraft:calcite}.
     */
    public static final TypedKey<BlockType> CALCITE = create("calcite");

    /**
     * Key for {@code minecraft:calibrated_sculk_sensor}.
     */
    public static final TypedKey<BlockType> CALIBRATED_SCULK_SENSOR = create("calibrated_sculk_sensor");

    /**
     * Key for {@code minecraft:campfire}.
     */
    public static final TypedKey<BlockType> CAMPFIRE = create("campfire");

    /**
     * Key for {@code minecraft:candle}.
     */
    public static final TypedKey<BlockType> CANDLE = create("candle");

    /**
     * Key for {@code minecraft:candle_cake}.
     */
    public static final TypedKey<BlockType> CANDLE_CAKE = create("candle_cake");

    /**
     * Key for {@code minecraft:carrots}.
     */
    public static final TypedKey<BlockType> CARROTS = create("carrots");

    /**
     * Key for {@code minecraft:cartography_table}.
     */
    public static final TypedKey<BlockType> CARTOGRAPHY_TABLE = create("cartography_table");

    /**
     * Key for {@code minecraft:carved_pumpkin}.
     */
    public static final TypedKey<BlockType> CARVED_PUMPKIN = create("carved_pumpkin");

    /**
     * Key for {@code minecraft:cauldron}.
     */
    public static final TypedKey<BlockType> CAULDRON = create("cauldron");

    /**
     * Key for {@code minecraft:cave_air}.
     */
    public static final TypedKey<BlockType> CAVE_AIR = create("cave_air");

    /**
     * Key for {@code minecraft:cave_vines}.
     */
    public static final TypedKey<BlockType> CAVE_VINES = create("cave_vines");

    /**
     * Key for {@code minecraft:cave_vines_plant}.
     */
    public static final TypedKey<BlockType> CAVE_VINES_PLANT = create("cave_vines_plant");

    /**
     * Key for {@code minecraft:chain_command_block}.
     */
    public static final TypedKey<BlockType> CHAIN_COMMAND_BLOCK = create("chain_command_block");

    /**
     * Key for {@code minecraft:cherry_button}.
     */
    public static final TypedKey<BlockType> CHERRY_BUTTON = create("cherry_button");

    /**
     * Key for {@code minecraft:cherry_door}.
     */
    public static final TypedKey<BlockType> CHERRY_DOOR = create("cherry_door");

    /**
     * Key for {@code minecraft:cherry_fence}.
     */
    public static final TypedKey<BlockType> CHERRY_FENCE = create("cherry_fence");

    /**
     * Key for {@code minecraft:cherry_fence_gate}.
     */
    public static final TypedKey<BlockType> CHERRY_FENCE_GATE = create("cherry_fence_gate");

    /**
     * Key for {@code minecraft:cherry_hanging_sign}.
     */
    public static final TypedKey<BlockType> CHERRY_HANGING_SIGN = create("cherry_hanging_sign");

    /**
     * Key for {@code minecraft:cherry_leaves}.
     */
    public static final TypedKey<BlockType> CHERRY_LEAVES = create("cherry_leaves");

    /**
     * Key for {@code minecraft:cherry_log}.
     */
    public static final TypedKey<BlockType> CHERRY_LOG = create("cherry_log");

    /**
     * Key for {@code minecraft:cherry_planks}.
     */
    public static final TypedKey<BlockType> CHERRY_PLANKS = create("cherry_planks");

    /**
     * Key for {@code minecraft:cherry_pressure_plate}.
     */
    public static final TypedKey<BlockType> CHERRY_PRESSURE_PLATE = create("cherry_pressure_plate");

    /**
     * Key for {@code minecraft:cherry_sapling}.
     */
    public static final TypedKey<BlockType> CHERRY_SAPLING = create("cherry_sapling");

    /**
     * Key for {@code minecraft:cherry_shelf}.
     */
    public static final TypedKey<BlockType> CHERRY_SHELF = create("cherry_shelf");

    /**
     * Key for {@code minecraft:cherry_sign}.
     */
    public static final TypedKey<BlockType> CHERRY_SIGN = create("cherry_sign");

    /**
     * Key for {@code minecraft:cherry_slab}.
     */
    public static final TypedKey<BlockType> CHERRY_SLAB = create("cherry_slab");

    /**
     * Key for {@code minecraft:cherry_stairs}.
     */
    public static final TypedKey<BlockType> CHERRY_STAIRS = create("cherry_stairs");

    /**
     * Key for {@code minecraft:cherry_trapdoor}.
     */
    public static final TypedKey<BlockType> CHERRY_TRAPDOOR = create("cherry_trapdoor");

    /**
     * Key for {@code minecraft:cherry_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> CHERRY_WALL_HANGING_SIGN = create("cherry_wall_hanging_sign");

    /**
     * Key for {@code minecraft:cherry_wall_sign}.
     */
    public static final TypedKey<BlockType> CHERRY_WALL_SIGN = create("cherry_wall_sign");

    /**
     * Key for {@code minecraft:cherry_wood}.
     */
    public static final TypedKey<BlockType> CHERRY_WOOD = create("cherry_wood");

    /**
     * Key for {@code minecraft:chest}.
     */
    public static final TypedKey<BlockType> CHEST = create("chest");

    /**
     * Key for {@code minecraft:chipped_anvil}.
     */
    public static final TypedKey<BlockType> CHIPPED_ANVIL = create("chipped_anvil");

    /**
     * Key for {@code minecraft:chiseled_bookshelf}.
     */
    public static final TypedKey<BlockType> CHISELED_BOOKSHELF = create("chiseled_bookshelf");

    /**
     * Key for {@code minecraft:chiseled_cinnabar}.
     */
    public static final TypedKey<BlockType> CHISELED_CINNABAR = create("chiseled_cinnabar");

    /**
     * Key for {@code minecraft:chiseled_copper}.
     */
    public static final TypedKey<BlockType> CHISELED_COPPER = create("chiseled_copper");

    /**
     * Key for {@code minecraft:chiseled_deepslate}.
     */
    public static final TypedKey<BlockType> CHISELED_DEEPSLATE = create("chiseled_deepslate");

    /**
     * Key for {@code minecraft:chiseled_nether_bricks}.
     */
    public static final TypedKey<BlockType> CHISELED_NETHER_BRICKS = create("chiseled_nether_bricks");

    /**
     * Key for {@code minecraft:chiseled_polished_blackstone}.
     */
    public static final TypedKey<BlockType> CHISELED_POLISHED_BLACKSTONE = create("chiseled_polished_blackstone");

    /**
     * Key for {@code minecraft:chiseled_quartz_block}.
     */
    public static final TypedKey<BlockType> CHISELED_QUARTZ_BLOCK = create("chiseled_quartz_block");

    /**
     * Key for {@code minecraft:chiseled_red_sandstone}.
     */
    public static final TypedKey<BlockType> CHISELED_RED_SANDSTONE = create("chiseled_red_sandstone");

    /**
     * Key for {@code minecraft:chiseled_resin_bricks}.
     */
    public static final TypedKey<BlockType> CHISELED_RESIN_BRICKS = create("chiseled_resin_bricks");

    /**
     * Key for {@code minecraft:chiseled_sandstone}.
     */
    public static final TypedKey<BlockType> CHISELED_SANDSTONE = create("chiseled_sandstone");

    /**
     * Key for {@code minecraft:chiseled_stone_bricks}.
     */
    public static final TypedKey<BlockType> CHISELED_STONE_BRICKS = create("chiseled_stone_bricks");

    /**
     * Key for {@code minecraft:chiseled_sulfur}.
     */
    public static final TypedKey<BlockType> CHISELED_SULFUR = create("chiseled_sulfur");

    /**
     * Key for {@code minecraft:chiseled_tuff}.
     */
    public static final TypedKey<BlockType> CHISELED_TUFF = create("chiseled_tuff");

    /**
     * Key for {@code minecraft:chiseled_tuff_bricks}.
     */
    public static final TypedKey<BlockType> CHISELED_TUFF_BRICKS = create("chiseled_tuff_bricks");

    /**
     * Key for {@code minecraft:chorus_flower}.
     */
    public static final TypedKey<BlockType> CHORUS_FLOWER = create("chorus_flower");

    /**
     * Key for {@code minecraft:chorus_plant}.
     */
    public static final TypedKey<BlockType> CHORUS_PLANT = create("chorus_plant");

    /**
     * Key for {@code minecraft:cinnabar}.
     */
    public static final TypedKey<BlockType> CINNABAR = create("cinnabar");

    /**
     * Key for {@code minecraft:cinnabar_bricks}.
     */
    public static final TypedKey<BlockType> CINNABAR_BRICKS = create("cinnabar_bricks");

    /**
     * Key for {@code minecraft:cinnabar_brick_slab}.
     */
    public static final TypedKey<BlockType> CINNABAR_BRICK_SLAB = create("cinnabar_brick_slab");

    /**
     * Key for {@code minecraft:cinnabar_brick_stairs}.
     */
    public static final TypedKey<BlockType> CINNABAR_BRICK_STAIRS = create("cinnabar_brick_stairs");

    /**
     * Key for {@code minecraft:cinnabar_brick_wall}.
     */
    public static final TypedKey<BlockType> CINNABAR_BRICK_WALL = create("cinnabar_brick_wall");

    /**
     * Key for {@code minecraft:cinnabar_slab}.
     */
    public static final TypedKey<BlockType> CINNABAR_SLAB = create("cinnabar_slab");

    /**
     * Key for {@code minecraft:cinnabar_stairs}.
     */
    public static final TypedKey<BlockType> CINNABAR_STAIRS = create("cinnabar_stairs");

    /**
     * Key for {@code minecraft:cinnabar_wall}.
     */
    public static final TypedKey<BlockType> CINNABAR_WALL = create("cinnabar_wall");

    /**
     * Key for {@code minecraft:clay}.
     */
    public static final TypedKey<BlockType> CLAY = create("clay");

    /**
     * Key for {@code minecraft:closed_eyeblossom}.
     */
    public static final TypedKey<BlockType> CLOSED_EYEBLOSSOM = create("closed_eyeblossom");

    /**
     * Key for {@code minecraft:coal_block}.
     */
    public static final TypedKey<BlockType> COAL_BLOCK = create("coal_block");

    /**
     * Key for {@code minecraft:coal_ore}.
     */
    public static final TypedKey<BlockType> COAL_ORE = create("coal_ore");

    /**
     * Key for {@code minecraft:coarse_dirt}.
     */
    public static final TypedKey<BlockType> COARSE_DIRT = create("coarse_dirt");

    /**
     * Key for {@code minecraft:cobbled_deepslate}.
     */
    public static final TypedKey<BlockType> COBBLED_DEEPSLATE = create("cobbled_deepslate");

    /**
     * Key for {@code minecraft:cobbled_deepslate_slab}.
     */
    public static final TypedKey<BlockType> COBBLED_DEEPSLATE_SLAB = create("cobbled_deepslate_slab");

    /**
     * Key for {@code minecraft:cobbled_deepslate_stairs}.
     */
    public static final TypedKey<BlockType> COBBLED_DEEPSLATE_STAIRS = create("cobbled_deepslate_stairs");

    /**
     * Key for {@code minecraft:cobbled_deepslate_wall}.
     */
    public static final TypedKey<BlockType> COBBLED_DEEPSLATE_WALL = create("cobbled_deepslate_wall");

    /**
     * Key for {@code minecraft:cobblestone}.
     */
    public static final TypedKey<BlockType> COBBLESTONE = create("cobblestone");

    /**
     * Key for {@code minecraft:cobblestone_slab}.
     */
    public static final TypedKey<BlockType> COBBLESTONE_SLAB = create("cobblestone_slab");

    /**
     * Key for {@code minecraft:cobblestone_stairs}.
     */
    public static final TypedKey<BlockType> COBBLESTONE_STAIRS = create("cobblestone_stairs");

    /**
     * Key for {@code minecraft:cobblestone_wall}.
     */
    public static final TypedKey<BlockType> COBBLESTONE_WALL = create("cobblestone_wall");

    /**
     * Key for {@code minecraft:cobweb}.
     */
    public static final TypedKey<BlockType> COBWEB = create("cobweb");

    /**
     * Key for {@code minecraft:cocoa}.
     */
    public static final TypedKey<BlockType> COCOA = create("cocoa");

    /**
     * Key for {@code minecraft:command_block}.
     */
    public static final TypedKey<BlockType> COMMAND_BLOCK = create("command_block");

    /**
     * Key for {@code minecraft:comparator}.
     */
    public static final TypedKey<BlockType> COMPARATOR = create("comparator");

    /**
     * Key for {@code minecraft:composter}.
     */
    public static final TypedKey<BlockType> COMPOSTER = create("composter");

    /**
     * Key for {@code minecraft:conduit}.
     */
    public static final TypedKey<BlockType> CONDUIT = create("conduit");

    /**
     * Key for {@code minecraft:copper_bars}.
     */
    public static final TypedKey<BlockType> COPPER_BARS = create("copper_bars");

    /**
     * Key for {@code minecraft:copper_block}.
     */
    public static final TypedKey<BlockType> COPPER_BLOCK = create("copper_block");

    /**
     * Key for {@code minecraft:copper_bulb}.
     */
    public static final TypedKey<BlockType> COPPER_BULB = create("copper_bulb");

    /**
     * Key for {@code minecraft:copper_chain}.
     */
    public static final TypedKey<BlockType> COPPER_CHAIN = create("copper_chain");

    /**
     * Key for {@code minecraft:copper_chest}.
     */
    public static final TypedKey<BlockType> COPPER_CHEST = create("copper_chest");

    /**
     * Key for {@code minecraft:copper_door}.
     */
    public static final TypedKey<BlockType> COPPER_DOOR = create("copper_door");

    /**
     * Key for {@code minecraft:copper_golem_statue}.
     */
    public static final TypedKey<BlockType> COPPER_GOLEM_STATUE = create("copper_golem_statue");

    /**
     * Key for {@code minecraft:copper_grate}.
     */
    public static final TypedKey<BlockType> COPPER_GRATE = create("copper_grate");

    /**
     * Key for {@code minecraft:copper_lantern}.
     */
    public static final TypedKey<BlockType> COPPER_LANTERN = create("copper_lantern");

    /**
     * Key for {@code minecraft:copper_ore}.
     */
    public static final TypedKey<BlockType> COPPER_ORE = create("copper_ore");

    /**
     * Key for {@code minecraft:copper_torch}.
     */
    public static final TypedKey<BlockType> COPPER_TORCH = create("copper_torch");

    /**
     * Key for {@code minecraft:copper_trapdoor}.
     */
    public static final TypedKey<BlockType> COPPER_TRAPDOOR = create("copper_trapdoor");

    /**
     * Key for {@code minecraft:copper_wall_torch}.
     */
    public static final TypedKey<BlockType> COPPER_WALL_TORCH = create("copper_wall_torch");

    /**
     * Key for {@code minecraft:cornflower}.
     */
    public static final TypedKey<BlockType> CORNFLOWER = create("cornflower");

    /**
     * Key for {@code minecraft:cracked_deepslate_bricks}.
     */
    public static final TypedKey<BlockType> CRACKED_DEEPSLATE_BRICKS = create("cracked_deepslate_bricks");

    /**
     * Key for {@code minecraft:cracked_deepslate_tiles}.
     */
    public static final TypedKey<BlockType> CRACKED_DEEPSLATE_TILES = create("cracked_deepslate_tiles");

    /**
     * Key for {@code minecraft:cracked_nether_bricks}.
     */
    public static final TypedKey<BlockType> CRACKED_NETHER_BRICKS = create("cracked_nether_bricks");

    /**
     * Key for {@code minecraft:cracked_polished_blackstone_bricks}.
     */
    public static final TypedKey<BlockType> CRACKED_POLISHED_BLACKSTONE_BRICKS = create("cracked_polished_blackstone_bricks");

    /**
     * Key for {@code minecraft:cracked_stone_bricks}.
     */
    public static final TypedKey<BlockType> CRACKED_STONE_BRICKS = create("cracked_stone_bricks");

    /**
     * Key for {@code minecraft:crafter}.
     */
    public static final TypedKey<BlockType> CRAFTER = create("crafter");

    /**
     * Key for {@code minecraft:crafting_table}.
     */
    public static final TypedKey<BlockType> CRAFTING_TABLE = create("crafting_table");

    /**
     * Key for {@code minecraft:creaking_heart}.
     */
    public static final TypedKey<BlockType> CREAKING_HEART = create("creaking_heart");

    /**
     * Key for {@code minecraft:creeper_head}.
     */
    public static final TypedKey<BlockType> CREEPER_HEAD = create("creeper_head");

    /**
     * Key for {@code minecraft:creeper_wall_head}.
     */
    public static final TypedKey<BlockType> CREEPER_WALL_HEAD = create("creeper_wall_head");

    /**
     * Key for {@code minecraft:crimson_button}.
     */
    public static final TypedKey<BlockType> CRIMSON_BUTTON = create("crimson_button");

    /**
     * Key for {@code minecraft:crimson_door}.
     */
    public static final TypedKey<BlockType> CRIMSON_DOOR = create("crimson_door");

    /**
     * Key for {@code minecraft:crimson_fence}.
     */
    public static final TypedKey<BlockType> CRIMSON_FENCE = create("crimson_fence");

    /**
     * Key for {@code minecraft:crimson_fence_gate}.
     */
    public static final TypedKey<BlockType> CRIMSON_FENCE_GATE = create("crimson_fence_gate");

    /**
     * Key for {@code minecraft:crimson_fungus}.
     */
    public static final TypedKey<BlockType> CRIMSON_FUNGUS = create("crimson_fungus");

    /**
     * Key for {@code minecraft:crimson_hanging_sign}.
     */
    public static final TypedKey<BlockType> CRIMSON_HANGING_SIGN = create("crimson_hanging_sign");

    /**
     * Key for {@code minecraft:crimson_hyphae}.
     */
    public static final TypedKey<BlockType> CRIMSON_HYPHAE = create("crimson_hyphae");

    /**
     * Key for {@code minecraft:crimson_nylium}.
     */
    public static final TypedKey<BlockType> CRIMSON_NYLIUM = create("crimson_nylium");

    /**
     * Key for {@code minecraft:crimson_planks}.
     */
    public static final TypedKey<BlockType> CRIMSON_PLANKS = create("crimson_planks");

    /**
     * Key for {@code minecraft:crimson_pressure_plate}.
     */
    public static final TypedKey<BlockType> CRIMSON_PRESSURE_PLATE = create("crimson_pressure_plate");

    /**
     * Key for {@code minecraft:crimson_roots}.
     */
    public static final TypedKey<BlockType> CRIMSON_ROOTS = create("crimson_roots");

    /**
     * Key for {@code minecraft:crimson_shelf}.
     */
    public static final TypedKey<BlockType> CRIMSON_SHELF = create("crimson_shelf");

    /**
     * Key for {@code minecraft:crimson_sign}.
     */
    public static final TypedKey<BlockType> CRIMSON_SIGN = create("crimson_sign");

    /**
     * Key for {@code minecraft:crimson_slab}.
     */
    public static final TypedKey<BlockType> CRIMSON_SLAB = create("crimson_slab");

    /**
     * Key for {@code minecraft:crimson_stairs}.
     */
    public static final TypedKey<BlockType> CRIMSON_STAIRS = create("crimson_stairs");

    /**
     * Key for {@code minecraft:crimson_stem}.
     */
    public static final TypedKey<BlockType> CRIMSON_STEM = create("crimson_stem");

    /**
     * Key for {@code minecraft:crimson_trapdoor}.
     */
    public static final TypedKey<BlockType> CRIMSON_TRAPDOOR = create("crimson_trapdoor");

    /**
     * Key for {@code minecraft:crimson_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> CRIMSON_WALL_HANGING_SIGN = create("crimson_wall_hanging_sign");

    /**
     * Key for {@code minecraft:crimson_wall_sign}.
     */
    public static final TypedKey<BlockType> CRIMSON_WALL_SIGN = create("crimson_wall_sign");

    /**
     * Key for {@code minecraft:crying_obsidian}.
     */
    public static final TypedKey<BlockType> CRYING_OBSIDIAN = create("crying_obsidian");

    /**
     * Key for {@code minecraft:cut_copper}.
     */
    public static final TypedKey<BlockType> CUT_COPPER = create("cut_copper");

    /**
     * Key for {@code minecraft:cut_copper_slab}.
     */
    public static final TypedKey<BlockType> CUT_COPPER_SLAB = create("cut_copper_slab");

    /**
     * Key for {@code minecraft:cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> CUT_COPPER_STAIRS = create("cut_copper_stairs");

    /**
     * Key for {@code minecraft:cut_red_sandstone}.
     */
    public static final TypedKey<BlockType> CUT_RED_SANDSTONE = create("cut_red_sandstone");

    /**
     * Key for {@code minecraft:cut_red_sandstone_slab}.
     */
    public static final TypedKey<BlockType> CUT_RED_SANDSTONE_SLAB = create("cut_red_sandstone_slab");

    /**
     * Key for {@code minecraft:cut_sandstone}.
     */
    public static final TypedKey<BlockType> CUT_SANDSTONE = create("cut_sandstone");

    /**
     * Key for {@code minecraft:cut_sandstone_slab}.
     */
    public static final TypedKey<BlockType> CUT_SANDSTONE_SLAB = create("cut_sandstone_slab");

    /**
     * Key for {@code minecraft:cyan_banner}.
     */
    public static final TypedKey<BlockType> CYAN_BANNER = create("cyan_banner");

    /**
     * Key for {@code minecraft:cyan_bed}.
     */
    public static final TypedKey<BlockType> CYAN_BED = create("cyan_bed");

    /**
     * Key for {@code minecraft:cyan_candle}.
     */
    public static final TypedKey<BlockType> CYAN_CANDLE = create("cyan_candle");

    /**
     * Key for {@code minecraft:cyan_candle_cake}.
     */
    public static final TypedKey<BlockType> CYAN_CANDLE_CAKE = create("cyan_candle_cake");

    /**
     * Key for {@code minecraft:cyan_carpet}.
     */
    public static final TypedKey<BlockType> CYAN_CARPET = create("cyan_carpet");

    /**
     * Key for {@code minecraft:cyan_concrete}.
     */
    public static final TypedKey<BlockType> CYAN_CONCRETE = create("cyan_concrete");

    /**
     * Key for {@code minecraft:cyan_concrete_powder}.
     */
    public static final TypedKey<BlockType> CYAN_CONCRETE_POWDER = create("cyan_concrete_powder");

    /**
     * Key for {@code minecraft:cyan_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> CYAN_GLAZED_TERRACOTTA = create("cyan_glazed_terracotta");

    /**
     * Key for {@code minecraft:cyan_shulker_box}.
     */
    public static final TypedKey<BlockType> CYAN_SHULKER_BOX = create("cyan_shulker_box");

    /**
     * Key for {@code minecraft:cyan_stained_glass}.
     */
    public static final TypedKey<BlockType> CYAN_STAINED_GLASS = create("cyan_stained_glass");

    /**
     * Key for {@code minecraft:cyan_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> CYAN_STAINED_GLASS_PANE = create("cyan_stained_glass_pane");

    /**
     * Key for {@code minecraft:cyan_terracotta}.
     */
    public static final TypedKey<BlockType> CYAN_TERRACOTTA = create("cyan_terracotta");

    /**
     * Key for {@code minecraft:cyan_wall_banner}.
     */
    public static final TypedKey<BlockType> CYAN_WALL_BANNER = create("cyan_wall_banner");

    /**
     * Key for {@code minecraft:cyan_wool}.
     */
    public static final TypedKey<BlockType> CYAN_WOOL = create("cyan_wool");

    /**
     * Key for {@code minecraft:damaged_anvil}.
     */
    public static final TypedKey<BlockType> DAMAGED_ANVIL = create("damaged_anvil");

    /**
     * Key for {@code minecraft:dandelion}.
     */
    public static final TypedKey<BlockType> DANDELION = create("dandelion");

    /**
     * Key for {@code minecraft:dark_oak_button}.
     */
    public static final TypedKey<BlockType> DARK_OAK_BUTTON = create("dark_oak_button");

    /**
     * Key for {@code minecraft:dark_oak_door}.
     */
    public static final TypedKey<BlockType> DARK_OAK_DOOR = create("dark_oak_door");

    /**
     * Key for {@code minecraft:dark_oak_fence}.
     */
    public static final TypedKey<BlockType> DARK_OAK_FENCE = create("dark_oak_fence");

    /**
     * Key for {@code minecraft:dark_oak_fence_gate}.
     */
    public static final TypedKey<BlockType> DARK_OAK_FENCE_GATE = create("dark_oak_fence_gate");

    /**
     * Key for {@code minecraft:dark_oak_hanging_sign}.
     */
    public static final TypedKey<BlockType> DARK_OAK_HANGING_SIGN = create("dark_oak_hanging_sign");

    /**
     * Key for {@code minecraft:dark_oak_leaves}.
     */
    public static final TypedKey<BlockType> DARK_OAK_LEAVES = create("dark_oak_leaves");

    /**
     * Key for {@code minecraft:dark_oak_log}.
     */
    public static final TypedKey<BlockType> DARK_OAK_LOG = create("dark_oak_log");

    /**
     * Key for {@code minecraft:dark_oak_planks}.
     */
    public static final TypedKey<BlockType> DARK_OAK_PLANKS = create("dark_oak_planks");

    /**
     * Key for {@code minecraft:dark_oak_pressure_plate}.
     */
    public static final TypedKey<BlockType> DARK_OAK_PRESSURE_PLATE = create("dark_oak_pressure_plate");

    /**
     * Key for {@code minecraft:dark_oak_sapling}.
     */
    public static final TypedKey<BlockType> DARK_OAK_SAPLING = create("dark_oak_sapling");

    /**
     * Key for {@code minecraft:dark_oak_shelf}.
     */
    public static final TypedKey<BlockType> DARK_OAK_SHELF = create("dark_oak_shelf");

    /**
     * Key for {@code minecraft:dark_oak_sign}.
     */
    public static final TypedKey<BlockType> DARK_OAK_SIGN = create("dark_oak_sign");

    /**
     * Key for {@code minecraft:dark_oak_slab}.
     */
    public static final TypedKey<BlockType> DARK_OAK_SLAB = create("dark_oak_slab");

    /**
     * Key for {@code minecraft:dark_oak_stairs}.
     */
    public static final TypedKey<BlockType> DARK_OAK_STAIRS = create("dark_oak_stairs");

    /**
     * Key for {@code minecraft:dark_oak_trapdoor}.
     */
    public static final TypedKey<BlockType> DARK_OAK_TRAPDOOR = create("dark_oak_trapdoor");

    /**
     * Key for {@code minecraft:dark_oak_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> DARK_OAK_WALL_HANGING_SIGN = create("dark_oak_wall_hanging_sign");

    /**
     * Key for {@code minecraft:dark_oak_wall_sign}.
     */
    public static final TypedKey<BlockType> DARK_OAK_WALL_SIGN = create("dark_oak_wall_sign");

    /**
     * Key for {@code minecraft:dark_oak_wood}.
     */
    public static final TypedKey<BlockType> DARK_OAK_WOOD = create("dark_oak_wood");

    /**
     * Key for {@code minecraft:dark_prismarine}.
     */
    public static final TypedKey<BlockType> DARK_PRISMARINE = create("dark_prismarine");

    /**
     * Key for {@code minecraft:dark_prismarine_slab}.
     */
    public static final TypedKey<BlockType> DARK_PRISMARINE_SLAB = create("dark_prismarine_slab");

    /**
     * Key for {@code minecraft:dark_prismarine_stairs}.
     */
    public static final TypedKey<BlockType> DARK_PRISMARINE_STAIRS = create("dark_prismarine_stairs");

    /**
     * Key for {@code minecraft:daylight_detector}.
     */
    public static final TypedKey<BlockType> DAYLIGHT_DETECTOR = create("daylight_detector");

    /**
     * Key for {@code minecraft:dead_brain_coral}.
     */
    public static final TypedKey<BlockType> DEAD_BRAIN_CORAL = create("dead_brain_coral");

    /**
     * Key for {@code minecraft:dead_brain_coral_block}.
     */
    public static final TypedKey<BlockType> DEAD_BRAIN_CORAL_BLOCK = create("dead_brain_coral_block");

    /**
     * Key for {@code minecraft:dead_brain_coral_fan}.
     */
    public static final TypedKey<BlockType> DEAD_BRAIN_CORAL_FAN = create("dead_brain_coral_fan");

    /**
     * Key for {@code minecraft:dead_brain_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> DEAD_BRAIN_CORAL_WALL_FAN = create("dead_brain_coral_wall_fan");

    /**
     * Key for {@code minecraft:dead_bubble_coral}.
     */
    public static final TypedKey<BlockType> DEAD_BUBBLE_CORAL = create("dead_bubble_coral");

    /**
     * Key for {@code minecraft:dead_bubble_coral_block}.
     */
    public static final TypedKey<BlockType> DEAD_BUBBLE_CORAL_BLOCK = create("dead_bubble_coral_block");

    /**
     * Key for {@code minecraft:dead_bubble_coral_fan}.
     */
    public static final TypedKey<BlockType> DEAD_BUBBLE_CORAL_FAN = create("dead_bubble_coral_fan");

    /**
     * Key for {@code minecraft:dead_bubble_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> DEAD_BUBBLE_CORAL_WALL_FAN = create("dead_bubble_coral_wall_fan");

    /**
     * Key for {@code minecraft:dead_bush}.
     */
    public static final TypedKey<BlockType> DEAD_BUSH = create("dead_bush");

    /**
     * Key for {@code minecraft:dead_fire_coral}.
     */
    public static final TypedKey<BlockType> DEAD_FIRE_CORAL = create("dead_fire_coral");

    /**
     * Key for {@code minecraft:dead_fire_coral_block}.
     */
    public static final TypedKey<BlockType> DEAD_FIRE_CORAL_BLOCK = create("dead_fire_coral_block");

    /**
     * Key for {@code minecraft:dead_fire_coral_fan}.
     */
    public static final TypedKey<BlockType> DEAD_FIRE_CORAL_FAN = create("dead_fire_coral_fan");

    /**
     * Key for {@code minecraft:dead_fire_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> DEAD_FIRE_CORAL_WALL_FAN = create("dead_fire_coral_wall_fan");

    /**
     * Key for {@code minecraft:dead_horn_coral}.
     */
    public static final TypedKey<BlockType> DEAD_HORN_CORAL = create("dead_horn_coral");

    /**
     * Key for {@code minecraft:dead_horn_coral_block}.
     */
    public static final TypedKey<BlockType> DEAD_HORN_CORAL_BLOCK = create("dead_horn_coral_block");

    /**
     * Key for {@code minecraft:dead_horn_coral_fan}.
     */
    public static final TypedKey<BlockType> DEAD_HORN_CORAL_FAN = create("dead_horn_coral_fan");

    /**
     * Key for {@code minecraft:dead_horn_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> DEAD_HORN_CORAL_WALL_FAN = create("dead_horn_coral_wall_fan");

    /**
     * Key for {@code minecraft:dead_tube_coral}.
     */
    public static final TypedKey<BlockType> DEAD_TUBE_CORAL = create("dead_tube_coral");

    /**
     * Key for {@code minecraft:dead_tube_coral_block}.
     */
    public static final TypedKey<BlockType> DEAD_TUBE_CORAL_BLOCK = create("dead_tube_coral_block");

    /**
     * Key for {@code minecraft:dead_tube_coral_fan}.
     */
    public static final TypedKey<BlockType> DEAD_TUBE_CORAL_FAN = create("dead_tube_coral_fan");

    /**
     * Key for {@code minecraft:dead_tube_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> DEAD_TUBE_CORAL_WALL_FAN = create("dead_tube_coral_wall_fan");

    /**
     * Key for {@code minecraft:decorated_pot}.
     */
    public static final TypedKey<BlockType> DECORATED_POT = create("decorated_pot");

    /**
     * Key for {@code minecraft:deepslate}.
     */
    public static final TypedKey<BlockType> DEEPSLATE = create("deepslate");

    /**
     * Key for {@code minecraft:deepslate_bricks}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_BRICKS = create("deepslate_bricks");

    /**
     * Key for {@code minecraft:deepslate_brick_slab}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_BRICK_SLAB = create("deepslate_brick_slab");

    /**
     * Key for {@code minecraft:deepslate_brick_stairs}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_BRICK_STAIRS = create("deepslate_brick_stairs");

    /**
     * Key for {@code minecraft:deepslate_brick_wall}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_BRICK_WALL = create("deepslate_brick_wall");

    /**
     * Key for {@code minecraft:deepslate_coal_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_COAL_ORE = create("deepslate_coal_ore");

    /**
     * Key for {@code minecraft:deepslate_copper_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_COPPER_ORE = create("deepslate_copper_ore");

    /**
     * Key for {@code minecraft:deepslate_diamond_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_DIAMOND_ORE = create("deepslate_diamond_ore");

    /**
     * Key for {@code minecraft:deepslate_emerald_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_EMERALD_ORE = create("deepslate_emerald_ore");

    /**
     * Key for {@code minecraft:deepslate_gold_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_GOLD_ORE = create("deepslate_gold_ore");

    /**
     * Key for {@code minecraft:deepslate_iron_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_IRON_ORE = create("deepslate_iron_ore");

    /**
     * Key for {@code minecraft:deepslate_lapis_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_LAPIS_ORE = create("deepslate_lapis_ore");

    /**
     * Key for {@code minecraft:deepslate_redstone_ore}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_REDSTONE_ORE = create("deepslate_redstone_ore");

    /**
     * Key for {@code minecraft:deepslate_tiles}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_TILES = create("deepslate_tiles");

    /**
     * Key for {@code minecraft:deepslate_tile_slab}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_TILE_SLAB = create("deepslate_tile_slab");

    /**
     * Key for {@code minecraft:deepslate_tile_stairs}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_TILE_STAIRS = create("deepslate_tile_stairs");

    /**
     * Key for {@code minecraft:deepslate_tile_wall}.
     */
    public static final TypedKey<BlockType> DEEPSLATE_TILE_WALL = create("deepslate_tile_wall");

    /**
     * Key for {@code minecraft:detector_rail}.
     */
    public static final TypedKey<BlockType> DETECTOR_RAIL = create("detector_rail");

    /**
     * Key for {@code minecraft:diamond_block}.
     */
    public static final TypedKey<BlockType> DIAMOND_BLOCK = create("diamond_block");

    /**
     * Key for {@code minecraft:diamond_ore}.
     */
    public static final TypedKey<BlockType> DIAMOND_ORE = create("diamond_ore");

    /**
     * Key for {@code minecraft:diorite}.
     */
    public static final TypedKey<BlockType> DIORITE = create("diorite");

    /**
     * Key for {@code minecraft:diorite_slab}.
     */
    public static final TypedKey<BlockType> DIORITE_SLAB = create("diorite_slab");

    /**
     * Key for {@code minecraft:diorite_stairs}.
     */
    public static final TypedKey<BlockType> DIORITE_STAIRS = create("diorite_stairs");

    /**
     * Key for {@code minecraft:diorite_wall}.
     */
    public static final TypedKey<BlockType> DIORITE_WALL = create("diorite_wall");

    /**
     * Key for {@code minecraft:dirt}.
     */
    public static final TypedKey<BlockType> DIRT = create("dirt");

    /**
     * Key for {@code minecraft:dirt_path}.
     */
    public static final TypedKey<BlockType> DIRT_PATH = create("dirt_path");

    /**
     * Key for {@code minecraft:dispenser}.
     */
    public static final TypedKey<BlockType> DISPENSER = create("dispenser");

    /**
     * Key for {@code minecraft:dragon_egg}.
     */
    public static final TypedKey<BlockType> DRAGON_EGG = create("dragon_egg");

    /**
     * Key for {@code minecraft:dragon_head}.
     */
    public static final TypedKey<BlockType> DRAGON_HEAD = create("dragon_head");

    /**
     * Key for {@code minecraft:dragon_wall_head}.
     */
    public static final TypedKey<BlockType> DRAGON_WALL_HEAD = create("dragon_wall_head");

    /**
     * Key for {@code minecraft:dried_ghast}.
     */
    public static final TypedKey<BlockType> DRIED_GHAST = create("dried_ghast");

    /**
     * Key for {@code minecraft:dried_kelp_block}.
     */
    public static final TypedKey<BlockType> DRIED_KELP_BLOCK = create("dried_kelp_block");

    /**
     * Key for {@code minecraft:dripstone_block}.
     */
    public static final TypedKey<BlockType> DRIPSTONE_BLOCK = create("dripstone_block");

    /**
     * Key for {@code minecraft:dropper}.
     */
    public static final TypedKey<BlockType> DROPPER = create("dropper");

    /**
     * Key for {@code minecraft:emerald_block}.
     */
    public static final TypedKey<BlockType> EMERALD_BLOCK = create("emerald_block");

    /**
     * Key for {@code minecraft:emerald_ore}.
     */
    public static final TypedKey<BlockType> EMERALD_ORE = create("emerald_ore");

    /**
     * Key for {@code minecraft:enchanting_table}.
     */
    public static final TypedKey<BlockType> ENCHANTING_TABLE = create("enchanting_table");

    /**
     * Key for {@code minecraft:ender_chest}.
     */
    public static final TypedKey<BlockType> ENDER_CHEST = create("ender_chest");

    /**
     * Key for {@code minecraft:end_gateway}.
     */
    public static final TypedKey<BlockType> END_GATEWAY = create("end_gateway");

    /**
     * Key for {@code minecraft:end_portal}.
     */
    public static final TypedKey<BlockType> END_PORTAL = create("end_portal");

    /**
     * Key for {@code minecraft:end_portal_frame}.
     */
    public static final TypedKey<BlockType> END_PORTAL_FRAME = create("end_portal_frame");

    /**
     * Key for {@code minecraft:end_rod}.
     */
    public static final TypedKey<BlockType> END_ROD = create("end_rod");

    /**
     * Key for {@code minecraft:end_stone}.
     */
    public static final TypedKey<BlockType> END_STONE = create("end_stone");

    /**
     * Key for {@code minecraft:end_stone_bricks}.
     */
    public static final TypedKey<BlockType> END_STONE_BRICKS = create("end_stone_bricks");

    /**
     * Key for {@code minecraft:end_stone_brick_slab}.
     */
    public static final TypedKey<BlockType> END_STONE_BRICK_SLAB = create("end_stone_brick_slab");

    /**
     * Key for {@code minecraft:end_stone_brick_stairs}.
     */
    public static final TypedKey<BlockType> END_STONE_BRICK_STAIRS = create("end_stone_brick_stairs");

    /**
     * Key for {@code minecraft:end_stone_brick_wall}.
     */
    public static final TypedKey<BlockType> END_STONE_BRICK_WALL = create("end_stone_brick_wall");

    /**
     * Key for {@code minecraft:exposed_chiseled_copper}.
     */
    public static final TypedKey<BlockType> EXPOSED_CHISELED_COPPER = create("exposed_chiseled_copper");

    /**
     * Key for {@code minecraft:exposed_copper}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER = create("exposed_copper");

    /**
     * Key for {@code minecraft:exposed_copper_bars}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_BARS = create("exposed_copper_bars");

    /**
     * Key for {@code minecraft:exposed_copper_bulb}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_BULB = create("exposed_copper_bulb");

    /**
     * Key for {@code minecraft:exposed_copper_chain}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_CHAIN = create("exposed_copper_chain");

    /**
     * Key for {@code minecraft:exposed_copper_chest}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_CHEST = create("exposed_copper_chest");

    /**
     * Key for {@code minecraft:exposed_copper_door}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_DOOR = create("exposed_copper_door");

    /**
     * Key for {@code minecraft:exposed_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_GOLEM_STATUE = create("exposed_copper_golem_statue");

    /**
     * Key for {@code minecraft:exposed_copper_grate}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_GRATE = create("exposed_copper_grate");

    /**
     * Key for {@code minecraft:exposed_copper_lantern}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_LANTERN = create("exposed_copper_lantern");

    /**
     * Key for {@code minecraft:exposed_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> EXPOSED_COPPER_TRAPDOOR = create("exposed_copper_trapdoor");

    /**
     * Key for {@code minecraft:exposed_cut_copper}.
     */
    public static final TypedKey<BlockType> EXPOSED_CUT_COPPER = create("exposed_cut_copper");

    /**
     * Key for {@code minecraft:exposed_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> EXPOSED_CUT_COPPER_SLAB = create("exposed_cut_copper_slab");

    /**
     * Key for {@code minecraft:exposed_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> EXPOSED_CUT_COPPER_STAIRS = create("exposed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:exposed_lightning_rod}.
     */
    public static final TypedKey<BlockType> EXPOSED_LIGHTNING_ROD = create("exposed_lightning_rod");

    /**
     * Key for {@code minecraft:farmland}.
     */
    public static final TypedKey<BlockType> FARMLAND = create("farmland");

    /**
     * Key for {@code minecraft:fern}.
     */
    public static final TypedKey<BlockType> FERN = create("fern");

    /**
     * Key for {@code minecraft:fire}.
     */
    public static final TypedKey<BlockType> FIRE = create("fire");

    /**
     * Key for {@code minecraft:firefly_bush}.
     */
    public static final TypedKey<BlockType> FIREFLY_BUSH = create("firefly_bush");

    /**
     * Key for {@code minecraft:fire_coral}.
     */
    public static final TypedKey<BlockType> FIRE_CORAL = create("fire_coral");

    /**
     * Key for {@code minecraft:fire_coral_block}.
     */
    public static final TypedKey<BlockType> FIRE_CORAL_BLOCK = create("fire_coral_block");

    /**
     * Key for {@code minecraft:fire_coral_fan}.
     */
    public static final TypedKey<BlockType> FIRE_CORAL_FAN = create("fire_coral_fan");

    /**
     * Key for {@code minecraft:fire_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> FIRE_CORAL_WALL_FAN = create("fire_coral_wall_fan");

    /**
     * Key for {@code minecraft:fletching_table}.
     */
    public static final TypedKey<BlockType> FLETCHING_TABLE = create("fletching_table");

    /**
     * Key for {@code minecraft:flowering_azalea}.
     */
    public static final TypedKey<BlockType> FLOWERING_AZALEA = create("flowering_azalea");

    /**
     * Key for {@code minecraft:flowering_azalea_leaves}.
     */
    public static final TypedKey<BlockType> FLOWERING_AZALEA_LEAVES = create("flowering_azalea_leaves");

    /**
     * Key for {@code minecraft:flower_pot}.
     */
    public static final TypedKey<BlockType> FLOWER_POT = create("flower_pot");

    /**
     * Key for {@code minecraft:frogspawn}.
     */
    public static final TypedKey<BlockType> FROGSPAWN = create("frogspawn");

    /**
     * Key for {@code minecraft:frosted_ice}.
     */
    public static final TypedKey<BlockType> FROSTED_ICE = create("frosted_ice");

    /**
     * Key for {@code minecraft:furnace}.
     */
    public static final TypedKey<BlockType> FURNACE = create("furnace");

    /**
     * Key for {@code minecraft:gilded_blackstone}.
     */
    public static final TypedKey<BlockType> GILDED_BLACKSTONE = create("gilded_blackstone");

    /**
     * Key for {@code minecraft:glass}.
     */
    public static final TypedKey<BlockType> GLASS = create("glass");

    /**
     * Key for {@code minecraft:glass_pane}.
     */
    public static final TypedKey<BlockType> GLASS_PANE = create("glass_pane");

    /**
     * Key for {@code minecraft:glowstone}.
     */
    public static final TypedKey<BlockType> GLOWSTONE = create("glowstone");

    /**
     * Key for {@code minecraft:glow_lichen}.
     */
    public static final TypedKey<BlockType> GLOW_LICHEN = create("glow_lichen");

    /**
     * Key for {@code minecraft:golden_dandelion}.
     */
    public static final TypedKey<BlockType> GOLDEN_DANDELION = create("golden_dandelion");

    /**
     * Key for {@code minecraft:gold_block}.
     */
    public static final TypedKey<BlockType> GOLD_BLOCK = create("gold_block");

    /**
     * Key for {@code minecraft:gold_ore}.
     */
    public static final TypedKey<BlockType> GOLD_ORE = create("gold_ore");

    /**
     * Key for {@code minecraft:granite}.
     */
    public static final TypedKey<BlockType> GRANITE = create("granite");

    /**
     * Key for {@code minecraft:granite_slab}.
     */
    public static final TypedKey<BlockType> GRANITE_SLAB = create("granite_slab");

    /**
     * Key for {@code minecraft:granite_stairs}.
     */
    public static final TypedKey<BlockType> GRANITE_STAIRS = create("granite_stairs");

    /**
     * Key for {@code minecraft:granite_wall}.
     */
    public static final TypedKey<BlockType> GRANITE_WALL = create("granite_wall");

    /**
     * Key for {@code minecraft:grass_block}.
     */
    public static final TypedKey<BlockType> GRASS_BLOCK = create("grass_block");

    /**
     * Key for {@code minecraft:gravel}.
     */
    public static final TypedKey<BlockType> GRAVEL = create("gravel");

    /**
     * Key for {@code minecraft:gray_banner}.
     */
    public static final TypedKey<BlockType> GRAY_BANNER = create("gray_banner");

    /**
     * Key for {@code minecraft:gray_bed}.
     */
    public static final TypedKey<BlockType> GRAY_BED = create("gray_bed");

    /**
     * Key for {@code minecraft:gray_candle}.
     */
    public static final TypedKey<BlockType> GRAY_CANDLE = create("gray_candle");

    /**
     * Key for {@code minecraft:gray_candle_cake}.
     */
    public static final TypedKey<BlockType> GRAY_CANDLE_CAKE = create("gray_candle_cake");

    /**
     * Key for {@code minecraft:gray_carpet}.
     */
    public static final TypedKey<BlockType> GRAY_CARPET = create("gray_carpet");

    /**
     * Key for {@code minecraft:gray_concrete}.
     */
    public static final TypedKey<BlockType> GRAY_CONCRETE = create("gray_concrete");

    /**
     * Key for {@code minecraft:gray_concrete_powder}.
     */
    public static final TypedKey<BlockType> GRAY_CONCRETE_POWDER = create("gray_concrete_powder");

    /**
     * Key for {@code minecraft:gray_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> GRAY_GLAZED_TERRACOTTA = create("gray_glazed_terracotta");

    /**
     * Key for {@code minecraft:gray_shulker_box}.
     */
    public static final TypedKey<BlockType> GRAY_SHULKER_BOX = create("gray_shulker_box");

    /**
     * Key for {@code minecraft:gray_stained_glass}.
     */
    public static final TypedKey<BlockType> GRAY_STAINED_GLASS = create("gray_stained_glass");

    /**
     * Key for {@code minecraft:gray_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> GRAY_STAINED_GLASS_PANE = create("gray_stained_glass_pane");

    /**
     * Key for {@code minecraft:gray_terracotta}.
     */
    public static final TypedKey<BlockType> GRAY_TERRACOTTA = create("gray_terracotta");

    /**
     * Key for {@code minecraft:gray_wall_banner}.
     */
    public static final TypedKey<BlockType> GRAY_WALL_BANNER = create("gray_wall_banner");

    /**
     * Key for {@code minecraft:gray_wool}.
     */
    public static final TypedKey<BlockType> GRAY_WOOL = create("gray_wool");

    /**
     * Key for {@code minecraft:green_banner}.
     */
    public static final TypedKey<BlockType> GREEN_BANNER = create("green_banner");

    /**
     * Key for {@code minecraft:green_bed}.
     */
    public static final TypedKey<BlockType> GREEN_BED = create("green_bed");

    /**
     * Key for {@code minecraft:green_candle}.
     */
    public static final TypedKey<BlockType> GREEN_CANDLE = create("green_candle");

    /**
     * Key for {@code minecraft:green_candle_cake}.
     */
    public static final TypedKey<BlockType> GREEN_CANDLE_CAKE = create("green_candle_cake");

    /**
     * Key for {@code minecraft:green_carpet}.
     */
    public static final TypedKey<BlockType> GREEN_CARPET = create("green_carpet");

    /**
     * Key for {@code minecraft:green_concrete}.
     */
    public static final TypedKey<BlockType> GREEN_CONCRETE = create("green_concrete");

    /**
     * Key for {@code minecraft:green_concrete_powder}.
     */
    public static final TypedKey<BlockType> GREEN_CONCRETE_POWDER = create("green_concrete_powder");

    /**
     * Key for {@code minecraft:green_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> GREEN_GLAZED_TERRACOTTA = create("green_glazed_terracotta");

    /**
     * Key for {@code minecraft:green_shulker_box}.
     */
    public static final TypedKey<BlockType> GREEN_SHULKER_BOX = create("green_shulker_box");

    /**
     * Key for {@code minecraft:green_stained_glass}.
     */
    public static final TypedKey<BlockType> GREEN_STAINED_GLASS = create("green_stained_glass");

    /**
     * Key for {@code minecraft:green_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> GREEN_STAINED_GLASS_PANE = create("green_stained_glass_pane");

    /**
     * Key for {@code minecraft:green_terracotta}.
     */
    public static final TypedKey<BlockType> GREEN_TERRACOTTA = create("green_terracotta");

    /**
     * Key for {@code minecraft:green_wall_banner}.
     */
    public static final TypedKey<BlockType> GREEN_WALL_BANNER = create("green_wall_banner");

    /**
     * Key for {@code minecraft:green_wool}.
     */
    public static final TypedKey<BlockType> GREEN_WOOL = create("green_wool");

    /**
     * Key for {@code minecraft:grindstone}.
     */
    public static final TypedKey<BlockType> GRINDSTONE = create("grindstone");

    /**
     * Key for {@code minecraft:hanging_roots}.
     */
    public static final TypedKey<BlockType> HANGING_ROOTS = create("hanging_roots");

    /**
     * Key for {@code minecraft:hay_block}.
     */
    public static final TypedKey<BlockType> HAY_BLOCK = create("hay_block");

    /**
     * Key for {@code minecraft:heavy_core}.
     */
    public static final TypedKey<BlockType> HEAVY_CORE = create("heavy_core");

    /**
     * Key for {@code minecraft:heavy_weighted_pressure_plate}.
     */
    public static final TypedKey<BlockType> HEAVY_WEIGHTED_PRESSURE_PLATE = create("heavy_weighted_pressure_plate");

    /**
     * Key for {@code minecraft:honeycomb_block}.
     */
    public static final TypedKey<BlockType> HONEYCOMB_BLOCK = create("honeycomb_block");

    /**
     * Key for {@code minecraft:honey_block}.
     */
    public static final TypedKey<BlockType> HONEY_BLOCK = create("honey_block");

    /**
     * Key for {@code minecraft:hopper}.
     */
    public static final TypedKey<BlockType> HOPPER = create("hopper");

    /**
     * Key for {@code minecraft:horn_coral}.
     */
    public static final TypedKey<BlockType> HORN_CORAL = create("horn_coral");

    /**
     * Key for {@code minecraft:horn_coral_block}.
     */
    public static final TypedKey<BlockType> HORN_CORAL_BLOCK = create("horn_coral_block");

    /**
     * Key for {@code minecraft:horn_coral_fan}.
     */
    public static final TypedKey<BlockType> HORN_CORAL_FAN = create("horn_coral_fan");

    /**
     * Key for {@code minecraft:horn_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> HORN_CORAL_WALL_FAN = create("horn_coral_wall_fan");

    /**
     * Key for {@code minecraft:ice}.
     */
    public static final TypedKey<BlockType> ICE = create("ice");

    /**
     * Key for {@code minecraft:infested_chiseled_stone_bricks}.
     */
    public static final TypedKey<BlockType> INFESTED_CHISELED_STONE_BRICKS = create("infested_chiseled_stone_bricks");

    /**
     * Key for {@code minecraft:infested_cobblestone}.
     */
    public static final TypedKey<BlockType> INFESTED_COBBLESTONE = create("infested_cobblestone");

    /**
     * Key for {@code minecraft:infested_cracked_stone_bricks}.
     */
    public static final TypedKey<BlockType> INFESTED_CRACKED_STONE_BRICKS = create("infested_cracked_stone_bricks");

    /**
     * Key for {@code minecraft:infested_deepslate}.
     */
    public static final TypedKey<BlockType> INFESTED_DEEPSLATE = create("infested_deepslate");

    /**
     * Key for {@code minecraft:infested_mossy_stone_bricks}.
     */
    public static final TypedKey<BlockType> INFESTED_MOSSY_STONE_BRICKS = create("infested_mossy_stone_bricks");

    /**
     * Key for {@code minecraft:infested_stone}.
     */
    public static final TypedKey<BlockType> INFESTED_STONE = create("infested_stone");

    /**
     * Key for {@code minecraft:infested_stone_bricks}.
     */
    public static final TypedKey<BlockType> INFESTED_STONE_BRICKS = create("infested_stone_bricks");

    /**
     * Key for {@code minecraft:iron_bars}.
     */
    public static final TypedKey<BlockType> IRON_BARS = create("iron_bars");

    /**
     * Key for {@code minecraft:iron_block}.
     */
    public static final TypedKey<BlockType> IRON_BLOCK = create("iron_block");

    /**
     * Key for {@code minecraft:iron_chain}.
     */
    public static final TypedKey<BlockType> IRON_CHAIN = create("iron_chain");

    /**
     * Key for {@code minecraft:iron_door}.
     */
    public static final TypedKey<BlockType> IRON_DOOR = create("iron_door");

    /**
     * Key for {@code minecraft:iron_ore}.
     */
    public static final TypedKey<BlockType> IRON_ORE = create("iron_ore");

    /**
     * Key for {@code minecraft:iron_trapdoor}.
     */
    public static final TypedKey<BlockType> IRON_TRAPDOOR = create("iron_trapdoor");

    /**
     * Key for {@code minecraft:jack_o_lantern}.
     */
    public static final TypedKey<BlockType> JACK_O_LANTERN = create("jack_o_lantern");

    /**
     * Key for {@code minecraft:jigsaw}.
     */
    public static final TypedKey<BlockType> JIGSAW = create("jigsaw");

    /**
     * Key for {@code minecraft:jukebox}.
     */
    public static final TypedKey<BlockType> JUKEBOX = create("jukebox");

    /**
     * Key for {@code minecraft:jungle_button}.
     */
    public static final TypedKey<BlockType> JUNGLE_BUTTON = create("jungle_button");

    /**
     * Key for {@code minecraft:jungle_door}.
     */
    public static final TypedKey<BlockType> JUNGLE_DOOR = create("jungle_door");

    /**
     * Key for {@code minecraft:jungle_fence}.
     */
    public static final TypedKey<BlockType> JUNGLE_FENCE = create("jungle_fence");

    /**
     * Key for {@code minecraft:jungle_fence_gate}.
     */
    public static final TypedKey<BlockType> JUNGLE_FENCE_GATE = create("jungle_fence_gate");

    /**
     * Key for {@code minecraft:jungle_hanging_sign}.
     */
    public static final TypedKey<BlockType> JUNGLE_HANGING_SIGN = create("jungle_hanging_sign");

    /**
     * Key for {@code minecraft:jungle_leaves}.
     */
    public static final TypedKey<BlockType> JUNGLE_LEAVES = create("jungle_leaves");

    /**
     * Key for {@code minecraft:jungle_log}.
     */
    public static final TypedKey<BlockType> JUNGLE_LOG = create("jungle_log");

    /**
     * Key for {@code minecraft:jungle_planks}.
     */
    public static final TypedKey<BlockType> JUNGLE_PLANKS = create("jungle_planks");

    /**
     * Key for {@code minecraft:jungle_pressure_plate}.
     */
    public static final TypedKey<BlockType> JUNGLE_PRESSURE_PLATE = create("jungle_pressure_plate");

    /**
     * Key for {@code minecraft:jungle_sapling}.
     */
    public static final TypedKey<BlockType> JUNGLE_SAPLING = create("jungle_sapling");

    /**
     * Key for {@code minecraft:jungle_shelf}.
     */
    public static final TypedKey<BlockType> JUNGLE_SHELF = create("jungle_shelf");

    /**
     * Key for {@code minecraft:jungle_sign}.
     */
    public static final TypedKey<BlockType> JUNGLE_SIGN = create("jungle_sign");

    /**
     * Key for {@code minecraft:jungle_slab}.
     */
    public static final TypedKey<BlockType> JUNGLE_SLAB = create("jungle_slab");

    /**
     * Key for {@code minecraft:jungle_stairs}.
     */
    public static final TypedKey<BlockType> JUNGLE_STAIRS = create("jungle_stairs");

    /**
     * Key for {@code minecraft:jungle_trapdoor}.
     */
    public static final TypedKey<BlockType> JUNGLE_TRAPDOOR = create("jungle_trapdoor");

    /**
     * Key for {@code minecraft:jungle_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> JUNGLE_WALL_HANGING_SIGN = create("jungle_wall_hanging_sign");

    /**
     * Key for {@code minecraft:jungle_wall_sign}.
     */
    public static final TypedKey<BlockType> JUNGLE_WALL_SIGN = create("jungle_wall_sign");

    /**
     * Key for {@code minecraft:jungle_wood}.
     */
    public static final TypedKey<BlockType> JUNGLE_WOOD = create("jungle_wood");

    /**
     * Key for {@code minecraft:kelp}.
     */
    public static final TypedKey<BlockType> KELP = create("kelp");

    /**
     * Key for {@code minecraft:kelp_plant}.
     */
    public static final TypedKey<BlockType> KELP_PLANT = create("kelp_plant");

    /**
     * Key for {@code minecraft:ladder}.
     */
    public static final TypedKey<BlockType> LADDER = create("ladder");

    /**
     * Key for {@code minecraft:lantern}.
     */
    public static final TypedKey<BlockType> LANTERN = create("lantern");

    /**
     * Key for {@code minecraft:lapis_block}.
     */
    public static final TypedKey<BlockType> LAPIS_BLOCK = create("lapis_block");

    /**
     * Key for {@code minecraft:lapis_ore}.
     */
    public static final TypedKey<BlockType> LAPIS_ORE = create("lapis_ore");

    /**
     * Key for {@code minecraft:large_amethyst_bud}.
     */
    public static final TypedKey<BlockType> LARGE_AMETHYST_BUD = create("large_amethyst_bud");

    /**
     * Key for {@code minecraft:large_fern}.
     */
    public static final TypedKey<BlockType> LARGE_FERN = create("large_fern");

    /**
     * Key for {@code minecraft:lava}.
     */
    public static final TypedKey<BlockType> LAVA = create("lava");

    /**
     * Key for {@code minecraft:lava_cauldron}.
     */
    public static final TypedKey<BlockType> LAVA_CAULDRON = create("lava_cauldron");

    /**
     * Key for {@code minecraft:leaf_litter}.
     */
    public static final TypedKey<BlockType> LEAF_LITTER = create("leaf_litter");

    /**
     * Key for {@code minecraft:lectern}.
     */
    public static final TypedKey<BlockType> LECTERN = create("lectern");

    /**
     * Key for {@code minecraft:lever}.
     */
    public static final TypedKey<BlockType> LEVER = create("lever");

    /**
     * Key for {@code minecraft:light}.
     */
    public static final TypedKey<BlockType> LIGHT = create("light");

    /**
     * Key for {@code minecraft:lightning_rod}.
     */
    public static final TypedKey<BlockType> LIGHTNING_ROD = create("lightning_rod");

    /**
     * Key for {@code minecraft:light_blue_banner}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_BANNER = create("light_blue_banner");

    /**
     * Key for {@code minecraft:light_blue_bed}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_BED = create("light_blue_bed");

    /**
     * Key for {@code minecraft:light_blue_candle}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_CANDLE = create("light_blue_candle");

    /**
     * Key for {@code minecraft:light_blue_candle_cake}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_CANDLE_CAKE = create("light_blue_candle_cake");

    /**
     * Key for {@code minecraft:light_blue_carpet}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_CARPET = create("light_blue_carpet");

    /**
     * Key for {@code minecraft:light_blue_concrete}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_CONCRETE = create("light_blue_concrete");

    /**
     * Key for {@code minecraft:light_blue_concrete_powder}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_CONCRETE_POWDER = create("light_blue_concrete_powder");

    /**
     * Key for {@code minecraft:light_blue_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_GLAZED_TERRACOTTA = create("light_blue_glazed_terracotta");

    /**
     * Key for {@code minecraft:light_blue_shulker_box}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_SHULKER_BOX = create("light_blue_shulker_box");

    /**
     * Key for {@code minecraft:light_blue_stained_glass}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_STAINED_GLASS = create("light_blue_stained_glass");

    /**
     * Key for {@code minecraft:light_blue_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_STAINED_GLASS_PANE = create("light_blue_stained_glass_pane");

    /**
     * Key for {@code minecraft:light_blue_terracotta}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_TERRACOTTA = create("light_blue_terracotta");

    /**
     * Key for {@code minecraft:light_blue_wall_banner}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_WALL_BANNER = create("light_blue_wall_banner");

    /**
     * Key for {@code minecraft:light_blue_wool}.
     */
    public static final TypedKey<BlockType> LIGHT_BLUE_WOOL = create("light_blue_wool");

    /**
     * Key for {@code minecraft:light_gray_banner}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_BANNER = create("light_gray_banner");

    /**
     * Key for {@code minecraft:light_gray_bed}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_BED = create("light_gray_bed");

    /**
     * Key for {@code minecraft:light_gray_candle}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_CANDLE = create("light_gray_candle");

    /**
     * Key for {@code minecraft:light_gray_candle_cake}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_CANDLE_CAKE = create("light_gray_candle_cake");

    /**
     * Key for {@code minecraft:light_gray_carpet}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_CARPET = create("light_gray_carpet");

    /**
     * Key for {@code minecraft:light_gray_concrete}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_CONCRETE = create("light_gray_concrete");

    /**
     * Key for {@code minecraft:light_gray_concrete_powder}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_CONCRETE_POWDER = create("light_gray_concrete_powder");

    /**
     * Key for {@code minecraft:light_gray_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_GLAZED_TERRACOTTA = create("light_gray_glazed_terracotta");

    /**
     * Key for {@code minecraft:light_gray_shulker_box}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_SHULKER_BOX = create("light_gray_shulker_box");

    /**
     * Key for {@code minecraft:light_gray_stained_glass}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_STAINED_GLASS = create("light_gray_stained_glass");

    /**
     * Key for {@code minecraft:light_gray_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_STAINED_GLASS_PANE = create("light_gray_stained_glass_pane");

    /**
     * Key for {@code minecraft:light_gray_terracotta}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_TERRACOTTA = create("light_gray_terracotta");

    /**
     * Key for {@code minecraft:light_gray_wall_banner}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_WALL_BANNER = create("light_gray_wall_banner");

    /**
     * Key for {@code minecraft:light_gray_wool}.
     */
    public static final TypedKey<BlockType> LIGHT_GRAY_WOOL = create("light_gray_wool");

    /**
     * Key for {@code minecraft:light_weighted_pressure_plate}.
     */
    public static final TypedKey<BlockType> LIGHT_WEIGHTED_PRESSURE_PLATE = create("light_weighted_pressure_plate");

    /**
     * Key for {@code minecraft:lilac}.
     */
    public static final TypedKey<BlockType> LILAC = create("lilac");

    /**
     * Key for {@code minecraft:lily_of_the_valley}.
     */
    public static final TypedKey<BlockType> LILY_OF_THE_VALLEY = create("lily_of_the_valley");

    /**
     * Key for {@code minecraft:lily_pad}.
     */
    public static final TypedKey<BlockType> LILY_PAD = create("lily_pad");

    /**
     * Key for {@code minecraft:lime_banner}.
     */
    public static final TypedKey<BlockType> LIME_BANNER = create("lime_banner");

    /**
     * Key for {@code minecraft:lime_bed}.
     */
    public static final TypedKey<BlockType> LIME_BED = create("lime_bed");

    /**
     * Key for {@code minecraft:lime_candle}.
     */
    public static final TypedKey<BlockType> LIME_CANDLE = create("lime_candle");

    /**
     * Key for {@code minecraft:lime_candle_cake}.
     */
    public static final TypedKey<BlockType> LIME_CANDLE_CAKE = create("lime_candle_cake");

    /**
     * Key for {@code minecraft:lime_carpet}.
     */
    public static final TypedKey<BlockType> LIME_CARPET = create("lime_carpet");

    /**
     * Key for {@code minecraft:lime_concrete}.
     */
    public static final TypedKey<BlockType> LIME_CONCRETE = create("lime_concrete");

    /**
     * Key for {@code minecraft:lime_concrete_powder}.
     */
    public static final TypedKey<BlockType> LIME_CONCRETE_POWDER = create("lime_concrete_powder");

    /**
     * Key for {@code minecraft:lime_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> LIME_GLAZED_TERRACOTTA = create("lime_glazed_terracotta");

    /**
     * Key for {@code minecraft:lime_shulker_box}.
     */
    public static final TypedKey<BlockType> LIME_SHULKER_BOX = create("lime_shulker_box");

    /**
     * Key for {@code minecraft:lime_stained_glass}.
     */
    public static final TypedKey<BlockType> LIME_STAINED_GLASS = create("lime_stained_glass");

    /**
     * Key for {@code minecraft:lime_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> LIME_STAINED_GLASS_PANE = create("lime_stained_glass_pane");

    /**
     * Key for {@code minecraft:lime_terracotta}.
     */
    public static final TypedKey<BlockType> LIME_TERRACOTTA = create("lime_terracotta");

    /**
     * Key for {@code minecraft:lime_wall_banner}.
     */
    public static final TypedKey<BlockType> LIME_WALL_BANNER = create("lime_wall_banner");

    /**
     * Key for {@code minecraft:lime_wool}.
     */
    public static final TypedKey<BlockType> LIME_WOOL = create("lime_wool");

    /**
     * Key for {@code minecraft:lodestone}.
     */
    public static final TypedKey<BlockType> LODESTONE = create("lodestone");

    /**
     * Key for {@code minecraft:loom}.
     */
    public static final TypedKey<BlockType> LOOM = create("loom");

    /**
     * Key for {@code minecraft:magenta_banner}.
     */
    public static final TypedKey<BlockType> MAGENTA_BANNER = create("magenta_banner");

    /**
     * Key for {@code minecraft:magenta_bed}.
     */
    public static final TypedKey<BlockType> MAGENTA_BED = create("magenta_bed");

    /**
     * Key for {@code minecraft:magenta_candle}.
     */
    public static final TypedKey<BlockType> MAGENTA_CANDLE = create("magenta_candle");

    /**
     * Key for {@code minecraft:magenta_candle_cake}.
     */
    public static final TypedKey<BlockType> MAGENTA_CANDLE_CAKE = create("magenta_candle_cake");

    /**
     * Key for {@code minecraft:magenta_carpet}.
     */
    public static final TypedKey<BlockType> MAGENTA_CARPET = create("magenta_carpet");

    /**
     * Key for {@code minecraft:magenta_concrete}.
     */
    public static final TypedKey<BlockType> MAGENTA_CONCRETE = create("magenta_concrete");

    /**
     * Key for {@code minecraft:magenta_concrete_powder}.
     */
    public static final TypedKey<BlockType> MAGENTA_CONCRETE_POWDER = create("magenta_concrete_powder");

    /**
     * Key for {@code minecraft:magenta_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> MAGENTA_GLAZED_TERRACOTTA = create("magenta_glazed_terracotta");

    /**
     * Key for {@code minecraft:magenta_shulker_box}.
     */
    public static final TypedKey<BlockType> MAGENTA_SHULKER_BOX = create("magenta_shulker_box");

    /**
     * Key for {@code minecraft:magenta_stained_glass}.
     */
    public static final TypedKey<BlockType> MAGENTA_STAINED_GLASS = create("magenta_stained_glass");

    /**
     * Key for {@code minecraft:magenta_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> MAGENTA_STAINED_GLASS_PANE = create("magenta_stained_glass_pane");

    /**
     * Key for {@code minecraft:magenta_terracotta}.
     */
    public static final TypedKey<BlockType> MAGENTA_TERRACOTTA = create("magenta_terracotta");

    /**
     * Key for {@code minecraft:magenta_wall_banner}.
     */
    public static final TypedKey<BlockType> MAGENTA_WALL_BANNER = create("magenta_wall_banner");

    /**
     * Key for {@code minecraft:magenta_wool}.
     */
    public static final TypedKey<BlockType> MAGENTA_WOOL = create("magenta_wool");

    /**
     * Key for {@code minecraft:magma_block}.
     */
    public static final TypedKey<BlockType> MAGMA_BLOCK = create("magma_block");

    /**
     * Key for {@code minecraft:mangrove_button}.
     */
    public static final TypedKey<BlockType> MANGROVE_BUTTON = create("mangrove_button");

    /**
     * Key for {@code minecraft:mangrove_door}.
     */
    public static final TypedKey<BlockType> MANGROVE_DOOR = create("mangrove_door");

    /**
     * Key for {@code minecraft:mangrove_fence}.
     */
    public static final TypedKey<BlockType> MANGROVE_FENCE = create("mangrove_fence");

    /**
     * Key for {@code minecraft:mangrove_fence_gate}.
     */
    public static final TypedKey<BlockType> MANGROVE_FENCE_GATE = create("mangrove_fence_gate");

    /**
     * Key for {@code minecraft:mangrove_hanging_sign}.
     */
    public static final TypedKey<BlockType> MANGROVE_HANGING_SIGN = create("mangrove_hanging_sign");

    /**
     * Key for {@code minecraft:mangrove_leaves}.
     */
    public static final TypedKey<BlockType> MANGROVE_LEAVES = create("mangrove_leaves");

    /**
     * Key for {@code minecraft:mangrove_log}.
     */
    public static final TypedKey<BlockType> MANGROVE_LOG = create("mangrove_log");

    /**
     * Key for {@code minecraft:mangrove_planks}.
     */
    public static final TypedKey<BlockType> MANGROVE_PLANKS = create("mangrove_planks");

    /**
     * Key for {@code minecraft:mangrove_pressure_plate}.
     */
    public static final TypedKey<BlockType> MANGROVE_PRESSURE_PLATE = create("mangrove_pressure_plate");

    /**
     * Key for {@code minecraft:mangrove_propagule}.
     */
    public static final TypedKey<BlockType> MANGROVE_PROPAGULE = create("mangrove_propagule");

    /**
     * Key for {@code minecraft:mangrove_roots}.
     */
    public static final TypedKey<BlockType> MANGROVE_ROOTS = create("mangrove_roots");

    /**
     * Key for {@code minecraft:mangrove_shelf}.
     */
    public static final TypedKey<BlockType> MANGROVE_SHELF = create("mangrove_shelf");

    /**
     * Key for {@code minecraft:mangrove_sign}.
     */
    public static final TypedKey<BlockType> MANGROVE_SIGN = create("mangrove_sign");

    /**
     * Key for {@code minecraft:mangrove_slab}.
     */
    public static final TypedKey<BlockType> MANGROVE_SLAB = create("mangrove_slab");

    /**
     * Key for {@code minecraft:mangrove_stairs}.
     */
    public static final TypedKey<BlockType> MANGROVE_STAIRS = create("mangrove_stairs");

    /**
     * Key for {@code minecraft:mangrove_trapdoor}.
     */
    public static final TypedKey<BlockType> MANGROVE_TRAPDOOR = create("mangrove_trapdoor");

    /**
     * Key for {@code minecraft:mangrove_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> MANGROVE_WALL_HANGING_SIGN = create("mangrove_wall_hanging_sign");

    /**
     * Key for {@code minecraft:mangrove_wall_sign}.
     */
    public static final TypedKey<BlockType> MANGROVE_WALL_SIGN = create("mangrove_wall_sign");

    /**
     * Key for {@code minecraft:mangrove_wood}.
     */
    public static final TypedKey<BlockType> MANGROVE_WOOD = create("mangrove_wood");

    /**
     * Key for {@code minecraft:medium_amethyst_bud}.
     */
    public static final TypedKey<BlockType> MEDIUM_AMETHYST_BUD = create("medium_amethyst_bud");

    /**
     * Key for {@code minecraft:melon}.
     */
    public static final TypedKey<BlockType> MELON = create("melon");

    /**
     * Key for {@code minecraft:melon_stem}.
     */
    public static final TypedKey<BlockType> MELON_STEM = create("melon_stem");

    /**
     * Key for {@code minecraft:mossy_cobblestone}.
     */
    public static final TypedKey<BlockType> MOSSY_COBBLESTONE = create("mossy_cobblestone");

    /**
     * Key for {@code minecraft:mossy_cobblestone_slab}.
     */
    public static final TypedKey<BlockType> MOSSY_COBBLESTONE_SLAB = create("mossy_cobblestone_slab");

    /**
     * Key for {@code minecraft:mossy_cobblestone_stairs}.
     */
    public static final TypedKey<BlockType> MOSSY_COBBLESTONE_STAIRS = create("mossy_cobblestone_stairs");

    /**
     * Key for {@code minecraft:mossy_cobblestone_wall}.
     */
    public static final TypedKey<BlockType> MOSSY_COBBLESTONE_WALL = create("mossy_cobblestone_wall");

    /**
     * Key for {@code minecraft:mossy_stone_bricks}.
     */
    public static final TypedKey<BlockType> MOSSY_STONE_BRICKS = create("mossy_stone_bricks");

    /**
     * Key for {@code minecraft:mossy_stone_brick_slab}.
     */
    public static final TypedKey<BlockType> MOSSY_STONE_BRICK_SLAB = create("mossy_stone_brick_slab");

    /**
     * Key for {@code minecraft:mossy_stone_brick_stairs}.
     */
    public static final TypedKey<BlockType> MOSSY_STONE_BRICK_STAIRS = create("mossy_stone_brick_stairs");

    /**
     * Key for {@code minecraft:mossy_stone_brick_wall}.
     */
    public static final TypedKey<BlockType> MOSSY_STONE_BRICK_WALL = create("mossy_stone_brick_wall");

    /**
     * Key for {@code minecraft:moss_block}.
     */
    public static final TypedKey<BlockType> MOSS_BLOCK = create("moss_block");

    /**
     * Key for {@code minecraft:moss_carpet}.
     */
    public static final TypedKey<BlockType> MOSS_CARPET = create("moss_carpet");

    /**
     * Key for {@code minecraft:moving_piston}.
     */
    public static final TypedKey<BlockType> MOVING_PISTON = create("moving_piston");

    /**
     * Key for {@code minecraft:mud}.
     */
    public static final TypedKey<BlockType> MUD = create("mud");

    /**
     * Key for {@code minecraft:muddy_mangrove_roots}.
     */
    public static final TypedKey<BlockType> MUDDY_MANGROVE_ROOTS = create("muddy_mangrove_roots");

    /**
     * Key for {@code minecraft:mud_bricks}.
     */
    public static final TypedKey<BlockType> MUD_BRICKS = create("mud_bricks");

    /**
     * Key for {@code minecraft:mud_brick_slab}.
     */
    public static final TypedKey<BlockType> MUD_BRICK_SLAB = create("mud_brick_slab");

    /**
     * Key for {@code minecraft:mud_brick_stairs}.
     */
    public static final TypedKey<BlockType> MUD_BRICK_STAIRS = create("mud_brick_stairs");

    /**
     * Key for {@code minecraft:mud_brick_wall}.
     */
    public static final TypedKey<BlockType> MUD_BRICK_WALL = create("mud_brick_wall");

    /**
     * Key for {@code minecraft:mushroom_stem}.
     */
    public static final TypedKey<BlockType> MUSHROOM_STEM = create("mushroom_stem");

    /**
     * Key for {@code minecraft:mycelium}.
     */
    public static final TypedKey<BlockType> MYCELIUM = create("mycelium");

    /**
     * Key for {@code minecraft:netherite_block}.
     */
    public static final TypedKey<BlockType> NETHERITE_BLOCK = create("netherite_block");

    /**
     * Key for {@code minecraft:netherrack}.
     */
    public static final TypedKey<BlockType> NETHERRACK = create("netherrack");

    /**
     * Key for {@code minecraft:nether_bricks}.
     */
    public static final TypedKey<BlockType> NETHER_BRICKS = create("nether_bricks");

    /**
     * Key for {@code minecraft:nether_brick_fence}.
     */
    public static final TypedKey<BlockType> NETHER_BRICK_FENCE = create("nether_brick_fence");

    /**
     * Key for {@code minecraft:nether_brick_slab}.
     */
    public static final TypedKey<BlockType> NETHER_BRICK_SLAB = create("nether_brick_slab");

    /**
     * Key for {@code minecraft:nether_brick_stairs}.
     */
    public static final TypedKey<BlockType> NETHER_BRICK_STAIRS = create("nether_brick_stairs");

    /**
     * Key for {@code minecraft:nether_brick_wall}.
     */
    public static final TypedKey<BlockType> NETHER_BRICK_WALL = create("nether_brick_wall");

    /**
     * Key for {@code minecraft:nether_gold_ore}.
     */
    public static final TypedKey<BlockType> NETHER_GOLD_ORE = create("nether_gold_ore");

    /**
     * Key for {@code minecraft:nether_portal}.
     */
    public static final TypedKey<BlockType> NETHER_PORTAL = create("nether_portal");

    /**
     * Key for {@code minecraft:nether_quartz_ore}.
     */
    public static final TypedKey<BlockType> NETHER_QUARTZ_ORE = create("nether_quartz_ore");

    /**
     * Key for {@code minecraft:nether_sprouts}.
     */
    public static final TypedKey<BlockType> NETHER_SPROUTS = create("nether_sprouts");

    /**
     * Key for {@code minecraft:nether_wart}.
     */
    public static final TypedKey<BlockType> NETHER_WART = create("nether_wart");

    /**
     * Key for {@code minecraft:nether_wart_block}.
     */
    public static final TypedKey<BlockType> NETHER_WART_BLOCK = create("nether_wart_block");

    /**
     * Key for {@code minecraft:note_block}.
     */
    public static final TypedKey<BlockType> NOTE_BLOCK = create("note_block");

    /**
     * Key for {@code minecraft:oak_button}.
     */
    public static final TypedKey<BlockType> OAK_BUTTON = create("oak_button");

    /**
     * Key for {@code minecraft:oak_door}.
     */
    public static final TypedKey<BlockType> OAK_DOOR = create("oak_door");

    /**
     * Key for {@code minecraft:oak_fence}.
     */
    public static final TypedKey<BlockType> OAK_FENCE = create("oak_fence");

    /**
     * Key for {@code minecraft:oak_fence_gate}.
     */
    public static final TypedKey<BlockType> OAK_FENCE_GATE = create("oak_fence_gate");

    /**
     * Key for {@code minecraft:oak_hanging_sign}.
     */
    public static final TypedKey<BlockType> OAK_HANGING_SIGN = create("oak_hanging_sign");

    /**
     * Key for {@code minecraft:oak_leaves}.
     */
    public static final TypedKey<BlockType> OAK_LEAVES = create("oak_leaves");

    /**
     * Key for {@code minecraft:oak_log}.
     */
    public static final TypedKey<BlockType> OAK_LOG = create("oak_log");

    /**
     * Key for {@code minecraft:oak_planks}.
     */
    public static final TypedKey<BlockType> OAK_PLANKS = create("oak_planks");

    /**
     * Key for {@code minecraft:oak_pressure_plate}.
     */
    public static final TypedKey<BlockType> OAK_PRESSURE_PLATE = create("oak_pressure_plate");

    /**
     * Key for {@code minecraft:oak_sapling}.
     */
    public static final TypedKey<BlockType> OAK_SAPLING = create("oak_sapling");

    /**
     * Key for {@code minecraft:oak_shelf}.
     */
    public static final TypedKey<BlockType> OAK_SHELF = create("oak_shelf");

    /**
     * Key for {@code minecraft:oak_sign}.
     */
    public static final TypedKey<BlockType> OAK_SIGN = create("oak_sign");

    /**
     * Key for {@code minecraft:oak_slab}.
     */
    public static final TypedKey<BlockType> OAK_SLAB = create("oak_slab");

    /**
     * Key for {@code minecraft:oak_stairs}.
     */
    public static final TypedKey<BlockType> OAK_STAIRS = create("oak_stairs");

    /**
     * Key for {@code minecraft:oak_trapdoor}.
     */
    public static final TypedKey<BlockType> OAK_TRAPDOOR = create("oak_trapdoor");

    /**
     * Key for {@code minecraft:oak_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> OAK_WALL_HANGING_SIGN = create("oak_wall_hanging_sign");

    /**
     * Key for {@code minecraft:oak_wall_sign}.
     */
    public static final TypedKey<BlockType> OAK_WALL_SIGN = create("oak_wall_sign");

    /**
     * Key for {@code minecraft:oak_wood}.
     */
    public static final TypedKey<BlockType> OAK_WOOD = create("oak_wood");

    /**
     * Key for {@code minecraft:observer}.
     */
    public static final TypedKey<BlockType> OBSERVER = create("observer");

    /**
     * Key for {@code minecraft:obsidian}.
     */
    public static final TypedKey<BlockType> OBSIDIAN = create("obsidian");

    /**
     * Key for {@code minecraft:ochre_froglight}.
     */
    public static final TypedKey<BlockType> OCHRE_FROGLIGHT = create("ochre_froglight");

    /**
     * Key for {@code minecraft:open_eyeblossom}.
     */
    public static final TypedKey<BlockType> OPEN_EYEBLOSSOM = create("open_eyeblossom");

    /**
     * Key for {@code minecraft:orange_banner}.
     */
    public static final TypedKey<BlockType> ORANGE_BANNER = create("orange_banner");

    /**
     * Key for {@code minecraft:orange_bed}.
     */
    public static final TypedKey<BlockType> ORANGE_BED = create("orange_bed");

    /**
     * Key for {@code minecraft:orange_candle}.
     */
    public static final TypedKey<BlockType> ORANGE_CANDLE = create("orange_candle");

    /**
     * Key for {@code minecraft:orange_candle_cake}.
     */
    public static final TypedKey<BlockType> ORANGE_CANDLE_CAKE = create("orange_candle_cake");

    /**
     * Key for {@code minecraft:orange_carpet}.
     */
    public static final TypedKey<BlockType> ORANGE_CARPET = create("orange_carpet");

    /**
     * Key for {@code minecraft:orange_concrete}.
     */
    public static final TypedKey<BlockType> ORANGE_CONCRETE = create("orange_concrete");

    /**
     * Key for {@code minecraft:orange_concrete_powder}.
     */
    public static final TypedKey<BlockType> ORANGE_CONCRETE_POWDER = create("orange_concrete_powder");

    /**
     * Key for {@code minecraft:orange_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> ORANGE_GLAZED_TERRACOTTA = create("orange_glazed_terracotta");

    /**
     * Key for {@code minecraft:orange_shulker_box}.
     */
    public static final TypedKey<BlockType> ORANGE_SHULKER_BOX = create("orange_shulker_box");

    /**
     * Key for {@code minecraft:orange_stained_glass}.
     */
    public static final TypedKey<BlockType> ORANGE_STAINED_GLASS = create("orange_stained_glass");

    /**
     * Key for {@code minecraft:orange_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> ORANGE_STAINED_GLASS_PANE = create("orange_stained_glass_pane");

    /**
     * Key for {@code minecraft:orange_terracotta}.
     */
    public static final TypedKey<BlockType> ORANGE_TERRACOTTA = create("orange_terracotta");

    /**
     * Key for {@code minecraft:orange_tulip}.
     */
    public static final TypedKey<BlockType> ORANGE_TULIP = create("orange_tulip");

    /**
     * Key for {@code minecraft:orange_wall_banner}.
     */
    public static final TypedKey<BlockType> ORANGE_WALL_BANNER = create("orange_wall_banner");

    /**
     * Key for {@code minecraft:orange_wool}.
     */
    public static final TypedKey<BlockType> ORANGE_WOOL = create("orange_wool");

    /**
     * Key for {@code minecraft:oxeye_daisy}.
     */
    public static final TypedKey<BlockType> OXEYE_DAISY = create("oxeye_daisy");

    /**
     * Key for {@code minecraft:oxidized_chiseled_copper}.
     */
    public static final TypedKey<BlockType> OXIDIZED_CHISELED_COPPER = create("oxidized_chiseled_copper");

    /**
     * Key for {@code minecraft:oxidized_copper}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER = create("oxidized_copper");

    /**
     * Key for {@code minecraft:oxidized_copper_bars}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_BARS = create("oxidized_copper_bars");

    /**
     * Key for {@code minecraft:oxidized_copper_bulb}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_BULB = create("oxidized_copper_bulb");

    /**
     * Key for {@code minecraft:oxidized_copper_chain}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_CHAIN = create("oxidized_copper_chain");

    /**
     * Key for {@code minecraft:oxidized_copper_chest}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_CHEST = create("oxidized_copper_chest");

    /**
     * Key for {@code minecraft:oxidized_copper_door}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_DOOR = create("oxidized_copper_door");

    /**
     * Key for {@code minecraft:oxidized_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_GOLEM_STATUE = create("oxidized_copper_golem_statue");

    /**
     * Key for {@code minecraft:oxidized_copper_grate}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_GRATE = create("oxidized_copper_grate");

    /**
     * Key for {@code minecraft:oxidized_copper_lantern}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_LANTERN = create("oxidized_copper_lantern");

    /**
     * Key for {@code minecraft:oxidized_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> OXIDIZED_COPPER_TRAPDOOR = create("oxidized_copper_trapdoor");

    /**
     * Key for {@code minecraft:oxidized_cut_copper}.
     */
    public static final TypedKey<BlockType> OXIDIZED_CUT_COPPER = create("oxidized_cut_copper");

    /**
     * Key for {@code minecraft:oxidized_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> OXIDIZED_CUT_COPPER_SLAB = create("oxidized_cut_copper_slab");

    /**
     * Key for {@code minecraft:oxidized_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> OXIDIZED_CUT_COPPER_STAIRS = create("oxidized_cut_copper_stairs");

    /**
     * Key for {@code minecraft:oxidized_lightning_rod}.
     */
    public static final TypedKey<BlockType> OXIDIZED_LIGHTNING_ROD = create("oxidized_lightning_rod");

    /**
     * Key for {@code minecraft:packed_ice}.
     */
    public static final TypedKey<BlockType> PACKED_ICE = create("packed_ice");

    /**
     * Key for {@code minecraft:packed_mud}.
     */
    public static final TypedKey<BlockType> PACKED_MUD = create("packed_mud");

    /**
     * Key for {@code minecraft:pale_hanging_moss}.
     */
    public static final TypedKey<BlockType> PALE_HANGING_MOSS = create("pale_hanging_moss");

    /**
     * Key for {@code minecraft:pale_moss_block}.
     */
    public static final TypedKey<BlockType> PALE_MOSS_BLOCK = create("pale_moss_block");

    /**
     * Key for {@code minecraft:pale_moss_carpet}.
     */
    public static final TypedKey<BlockType> PALE_MOSS_CARPET = create("pale_moss_carpet");

    /**
     * Key for {@code minecraft:pale_oak_button}.
     */
    public static final TypedKey<BlockType> PALE_OAK_BUTTON = create("pale_oak_button");

    /**
     * Key for {@code minecraft:pale_oak_door}.
     */
    public static final TypedKey<BlockType> PALE_OAK_DOOR = create("pale_oak_door");

    /**
     * Key for {@code minecraft:pale_oak_fence}.
     */
    public static final TypedKey<BlockType> PALE_OAK_FENCE = create("pale_oak_fence");

    /**
     * Key for {@code minecraft:pale_oak_fence_gate}.
     */
    public static final TypedKey<BlockType> PALE_OAK_FENCE_GATE = create("pale_oak_fence_gate");

    /**
     * Key for {@code minecraft:pale_oak_hanging_sign}.
     */
    public static final TypedKey<BlockType> PALE_OAK_HANGING_SIGN = create("pale_oak_hanging_sign");

    /**
     * Key for {@code minecraft:pale_oak_leaves}.
     */
    public static final TypedKey<BlockType> PALE_OAK_LEAVES = create("pale_oak_leaves");

    /**
     * Key for {@code minecraft:pale_oak_log}.
     */
    public static final TypedKey<BlockType> PALE_OAK_LOG = create("pale_oak_log");

    /**
     * Key for {@code minecraft:pale_oak_planks}.
     */
    public static final TypedKey<BlockType> PALE_OAK_PLANKS = create("pale_oak_planks");

    /**
     * Key for {@code minecraft:pale_oak_pressure_plate}.
     */
    public static final TypedKey<BlockType> PALE_OAK_PRESSURE_PLATE = create("pale_oak_pressure_plate");

    /**
     * Key for {@code minecraft:pale_oak_sapling}.
     */
    public static final TypedKey<BlockType> PALE_OAK_SAPLING = create("pale_oak_sapling");

    /**
     * Key for {@code minecraft:pale_oak_shelf}.
     */
    public static final TypedKey<BlockType> PALE_OAK_SHELF = create("pale_oak_shelf");

    /**
     * Key for {@code minecraft:pale_oak_sign}.
     */
    public static final TypedKey<BlockType> PALE_OAK_SIGN = create("pale_oak_sign");

    /**
     * Key for {@code minecraft:pale_oak_slab}.
     */
    public static final TypedKey<BlockType> PALE_OAK_SLAB = create("pale_oak_slab");

    /**
     * Key for {@code minecraft:pale_oak_stairs}.
     */
    public static final TypedKey<BlockType> PALE_OAK_STAIRS = create("pale_oak_stairs");

    /**
     * Key for {@code minecraft:pale_oak_trapdoor}.
     */
    public static final TypedKey<BlockType> PALE_OAK_TRAPDOOR = create("pale_oak_trapdoor");

    /**
     * Key for {@code minecraft:pale_oak_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> PALE_OAK_WALL_HANGING_SIGN = create("pale_oak_wall_hanging_sign");

    /**
     * Key for {@code minecraft:pale_oak_wall_sign}.
     */
    public static final TypedKey<BlockType> PALE_OAK_WALL_SIGN = create("pale_oak_wall_sign");

    /**
     * Key for {@code minecraft:pale_oak_wood}.
     */
    public static final TypedKey<BlockType> PALE_OAK_WOOD = create("pale_oak_wood");

    /**
     * Key for {@code minecraft:pearlescent_froglight}.
     */
    public static final TypedKey<BlockType> PEARLESCENT_FROGLIGHT = create("pearlescent_froglight");

    /**
     * Key for {@code minecraft:peony}.
     */
    public static final TypedKey<BlockType> PEONY = create("peony");

    /**
     * Key for {@code minecraft:petrified_oak_slab}.
     */
    public static final TypedKey<BlockType> PETRIFIED_OAK_SLAB = create("petrified_oak_slab");

    /**
     * Key for {@code minecraft:piglin_head}.
     */
    public static final TypedKey<BlockType> PIGLIN_HEAD = create("piglin_head");

    /**
     * Key for {@code minecraft:piglin_wall_head}.
     */
    public static final TypedKey<BlockType> PIGLIN_WALL_HEAD = create("piglin_wall_head");

    /**
     * Key for {@code minecraft:pink_banner}.
     */
    public static final TypedKey<BlockType> PINK_BANNER = create("pink_banner");

    /**
     * Key for {@code minecraft:pink_bed}.
     */
    public static final TypedKey<BlockType> PINK_BED = create("pink_bed");

    /**
     * Key for {@code minecraft:pink_candle}.
     */
    public static final TypedKey<BlockType> PINK_CANDLE = create("pink_candle");

    /**
     * Key for {@code minecraft:pink_candle_cake}.
     */
    public static final TypedKey<BlockType> PINK_CANDLE_CAKE = create("pink_candle_cake");

    /**
     * Key for {@code minecraft:pink_carpet}.
     */
    public static final TypedKey<BlockType> PINK_CARPET = create("pink_carpet");

    /**
     * Key for {@code minecraft:pink_concrete}.
     */
    public static final TypedKey<BlockType> PINK_CONCRETE = create("pink_concrete");

    /**
     * Key for {@code minecraft:pink_concrete_powder}.
     */
    public static final TypedKey<BlockType> PINK_CONCRETE_POWDER = create("pink_concrete_powder");

    /**
     * Key for {@code minecraft:pink_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> PINK_GLAZED_TERRACOTTA = create("pink_glazed_terracotta");

    /**
     * Key for {@code minecraft:pink_petals}.
     */
    public static final TypedKey<BlockType> PINK_PETALS = create("pink_petals");

    /**
     * Key for {@code minecraft:pink_shulker_box}.
     */
    public static final TypedKey<BlockType> PINK_SHULKER_BOX = create("pink_shulker_box");

    /**
     * Key for {@code minecraft:pink_stained_glass}.
     */
    public static final TypedKey<BlockType> PINK_STAINED_GLASS = create("pink_stained_glass");

    /**
     * Key for {@code minecraft:pink_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> PINK_STAINED_GLASS_PANE = create("pink_stained_glass_pane");

    /**
     * Key for {@code minecraft:pink_terracotta}.
     */
    public static final TypedKey<BlockType> PINK_TERRACOTTA = create("pink_terracotta");

    /**
     * Key for {@code minecraft:pink_tulip}.
     */
    public static final TypedKey<BlockType> PINK_TULIP = create("pink_tulip");

    /**
     * Key for {@code minecraft:pink_wall_banner}.
     */
    public static final TypedKey<BlockType> PINK_WALL_BANNER = create("pink_wall_banner");

    /**
     * Key for {@code minecraft:pink_wool}.
     */
    public static final TypedKey<BlockType> PINK_WOOL = create("pink_wool");

    /**
     * Key for {@code minecraft:piston}.
     */
    public static final TypedKey<BlockType> PISTON = create("piston");

    /**
     * Key for {@code minecraft:piston_head}.
     */
    public static final TypedKey<BlockType> PISTON_HEAD = create("piston_head");

    /**
     * Key for {@code minecraft:pitcher_crop}.
     */
    public static final TypedKey<BlockType> PITCHER_CROP = create("pitcher_crop");

    /**
     * Key for {@code minecraft:pitcher_plant}.
     */
    public static final TypedKey<BlockType> PITCHER_PLANT = create("pitcher_plant");

    /**
     * Key for {@code minecraft:player_head}.
     */
    public static final TypedKey<BlockType> PLAYER_HEAD = create("player_head");

    /**
     * Key for {@code minecraft:player_wall_head}.
     */
    public static final TypedKey<BlockType> PLAYER_WALL_HEAD = create("player_wall_head");

    /**
     * Key for {@code minecraft:podzol}.
     */
    public static final TypedKey<BlockType> PODZOL = create("podzol");

    /**
     * Key for {@code minecraft:pointed_dripstone}.
     */
    public static final TypedKey<BlockType> POINTED_DRIPSTONE = create("pointed_dripstone");

    /**
     * Key for {@code minecraft:polished_andesite}.
     */
    public static final TypedKey<BlockType> POLISHED_ANDESITE = create("polished_andesite");

    /**
     * Key for {@code minecraft:polished_andesite_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_ANDESITE_SLAB = create("polished_andesite_slab");

    /**
     * Key for {@code minecraft:polished_andesite_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_ANDESITE_STAIRS = create("polished_andesite_stairs");

    /**
     * Key for {@code minecraft:polished_basalt}.
     */
    public static final TypedKey<BlockType> POLISHED_BASALT = create("polished_basalt");

    /**
     * Key for {@code minecraft:polished_blackstone}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE = create("polished_blackstone");

    /**
     * Key for {@code minecraft:polished_blackstone_bricks}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_BRICKS = create("polished_blackstone_bricks");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_BRICK_SLAB = create("polished_blackstone_brick_slab");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_BRICK_STAIRS = create("polished_blackstone_brick_stairs");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_BRICK_WALL = create("polished_blackstone_brick_wall");

    /**
     * Key for {@code minecraft:polished_blackstone_button}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_BUTTON = create("polished_blackstone_button");

    /**
     * Key for {@code minecraft:polished_blackstone_pressure_plate}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_PRESSURE_PLATE = create("polished_blackstone_pressure_plate");

    /**
     * Key for {@code minecraft:polished_blackstone_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_SLAB = create("polished_blackstone_slab");

    /**
     * Key for {@code minecraft:polished_blackstone_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_STAIRS = create("polished_blackstone_stairs");

    /**
     * Key for {@code minecraft:polished_blackstone_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_BLACKSTONE_WALL = create("polished_blackstone_wall");

    /**
     * Key for {@code minecraft:polished_cinnabar}.
     */
    public static final TypedKey<BlockType> POLISHED_CINNABAR = create("polished_cinnabar");

    /**
     * Key for {@code minecraft:polished_cinnabar_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_CINNABAR_SLAB = create("polished_cinnabar_slab");

    /**
     * Key for {@code minecraft:polished_cinnabar_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_CINNABAR_STAIRS = create("polished_cinnabar_stairs");

    /**
     * Key for {@code minecraft:polished_cinnabar_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_CINNABAR_WALL = create("polished_cinnabar_wall");

    /**
     * Key for {@code minecraft:polished_deepslate}.
     */
    public static final TypedKey<BlockType> POLISHED_DEEPSLATE = create("polished_deepslate");

    /**
     * Key for {@code minecraft:polished_deepslate_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_DEEPSLATE_SLAB = create("polished_deepslate_slab");

    /**
     * Key for {@code minecraft:polished_deepslate_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_DEEPSLATE_STAIRS = create("polished_deepslate_stairs");

    /**
     * Key for {@code minecraft:polished_deepslate_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_DEEPSLATE_WALL = create("polished_deepslate_wall");

    /**
     * Key for {@code minecraft:polished_diorite}.
     */
    public static final TypedKey<BlockType> POLISHED_DIORITE = create("polished_diorite");

    /**
     * Key for {@code minecraft:polished_diorite_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_DIORITE_SLAB = create("polished_diorite_slab");

    /**
     * Key for {@code minecraft:polished_diorite_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_DIORITE_STAIRS = create("polished_diorite_stairs");

    /**
     * Key for {@code minecraft:polished_granite}.
     */
    public static final TypedKey<BlockType> POLISHED_GRANITE = create("polished_granite");

    /**
     * Key for {@code minecraft:polished_granite_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_GRANITE_SLAB = create("polished_granite_slab");

    /**
     * Key for {@code minecraft:polished_granite_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_GRANITE_STAIRS = create("polished_granite_stairs");

    /**
     * Key for {@code minecraft:polished_sulfur}.
     */
    public static final TypedKey<BlockType> POLISHED_SULFUR = create("polished_sulfur");

    /**
     * Key for {@code minecraft:polished_sulfur_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_SULFUR_SLAB = create("polished_sulfur_slab");

    /**
     * Key for {@code minecraft:polished_sulfur_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_SULFUR_STAIRS = create("polished_sulfur_stairs");

    /**
     * Key for {@code minecraft:polished_sulfur_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_SULFUR_WALL = create("polished_sulfur_wall");

    /**
     * Key for {@code minecraft:polished_tuff}.
     */
    public static final TypedKey<BlockType> POLISHED_TUFF = create("polished_tuff");

    /**
     * Key for {@code minecraft:polished_tuff_slab}.
     */
    public static final TypedKey<BlockType> POLISHED_TUFF_SLAB = create("polished_tuff_slab");

    /**
     * Key for {@code minecraft:polished_tuff_stairs}.
     */
    public static final TypedKey<BlockType> POLISHED_TUFF_STAIRS = create("polished_tuff_stairs");

    /**
     * Key for {@code minecraft:polished_tuff_wall}.
     */
    public static final TypedKey<BlockType> POLISHED_TUFF_WALL = create("polished_tuff_wall");

    /**
     * Key for {@code minecraft:poppy}.
     */
    public static final TypedKey<BlockType> POPPY = create("poppy");

    /**
     * Key for {@code minecraft:potatoes}.
     */
    public static final TypedKey<BlockType> POTATOES = create("potatoes");

    /**
     * Key for {@code minecraft:potent_sulfur}.
     */
    public static final TypedKey<BlockType> POTENT_SULFUR = create("potent_sulfur");

    /**
     * Key for {@code minecraft:potted_acacia_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_ACACIA_SAPLING = create("potted_acacia_sapling");

    /**
     * Key for {@code minecraft:potted_allium}.
     */
    public static final TypedKey<BlockType> POTTED_ALLIUM = create("potted_allium");

    /**
     * Key for {@code minecraft:potted_azalea_bush}.
     */
    public static final TypedKey<BlockType> POTTED_AZALEA_BUSH = create("potted_azalea_bush");

    /**
     * Key for {@code minecraft:potted_azure_bluet}.
     */
    public static final TypedKey<BlockType> POTTED_AZURE_BLUET = create("potted_azure_bluet");

    /**
     * Key for {@code minecraft:potted_bamboo}.
     */
    public static final TypedKey<BlockType> POTTED_BAMBOO = create("potted_bamboo");

    /**
     * Key for {@code minecraft:potted_birch_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_BIRCH_SAPLING = create("potted_birch_sapling");

    /**
     * Key for {@code minecraft:potted_blue_orchid}.
     */
    public static final TypedKey<BlockType> POTTED_BLUE_ORCHID = create("potted_blue_orchid");

    /**
     * Key for {@code minecraft:potted_brown_mushroom}.
     */
    public static final TypedKey<BlockType> POTTED_BROWN_MUSHROOM = create("potted_brown_mushroom");

    /**
     * Key for {@code minecraft:potted_cactus}.
     */
    public static final TypedKey<BlockType> POTTED_CACTUS = create("potted_cactus");

    /**
     * Key for {@code minecraft:potted_cherry_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_CHERRY_SAPLING = create("potted_cherry_sapling");

    /**
     * Key for {@code minecraft:potted_closed_eyeblossom}.
     */
    public static final TypedKey<BlockType> POTTED_CLOSED_EYEBLOSSOM = create("potted_closed_eyeblossom");

    /**
     * Key for {@code minecraft:potted_cornflower}.
     */
    public static final TypedKey<BlockType> POTTED_CORNFLOWER = create("potted_cornflower");

    /**
     * Key for {@code minecraft:potted_crimson_fungus}.
     */
    public static final TypedKey<BlockType> POTTED_CRIMSON_FUNGUS = create("potted_crimson_fungus");

    /**
     * Key for {@code minecraft:potted_crimson_roots}.
     */
    public static final TypedKey<BlockType> POTTED_CRIMSON_ROOTS = create("potted_crimson_roots");

    /**
     * Key for {@code minecraft:potted_dandelion}.
     */
    public static final TypedKey<BlockType> POTTED_DANDELION = create("potted_dandelion");

    /**
     * Key for {@code minecraft:potted_dark_oak_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_DARK_OAK_SAPLING = create("potted_dark_oak_sapling");

    /**
     * Key for {@code minecraft:potted_dead_bush}.
     */
    public static final TypedKey<BlockType> POTTED_DEAD_BUSH = create("potted_dead_bush");

    /**
     * Key for {@code minecraft:potted_fern}.
     */
    public static final TypedKey<BlockType> POTTED_FERN = create("potted_fern");

    /**
     * Key for {@code minecraft:potted_flowering_azalea_bush}.
     */
    public static final TypedKey<BlockType> POTTED_FLOWERING_AZALEA_BUSH = create("potted_flowering_azalea_bush");

    /**
     * Key for {@code minecraft:potted_golden_dandelion}.
     */
    public static final TypedKey<BlockType> POTTED_GOLDEN_DANDELION = create("potted_golden_dandelion");

    /**
     * Key for {@code minecraft:potted_jungle_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_JUNGLE_SAPLING = create("potted_jungle_sapling");

    /**
     * Key for {@code minecraft:potted_lily_of_the_valley}.
     */
    public static final TypedKey<BlockType> POTTED_LILY_OF_THE_VALLEY = create("potted_lily_of_the_valley");

    /**
     * Key for {@code minecraft:potted_mangrove_propagule}.
     */
    public static final TypedKey<BlockType> POTTED_MANGROVE_PROPAGULE = create("potted_mangrove_propagule");

    /**
     * Key for {@code minecraft:potted_oak_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_OAK_SAPLING = create("potted_oak_sapling");

    /**
     * Key for {@code minecraft:potted_open_eyeblossom}.
     */
    public static final TypedKey<BlockType> POTTED_OPEN_EYEBLOSSOM = create("potted_open_eyeblossom");

    /**
     * Key for {@code minecraft:potted_orange_tulip}.
     */
    public static final TypedKey<BlockType> POTTED_ORANGE_TULIP = create("potted_orange_tulip");

    /**
     * Key for {@code minecraft:potted_oxeye_daisy}.
     */
    public static final TypedKey<BlockType> POTTED_OXEYE_DAISY = create("potted_oxeye_daisy");

    /**
     * Key for {@code minecraft:potted_pale_oak_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_PALE_OAK_SAPLING = create("potted_pale_oak_sapling");

    /**
     * Key for {@code minecraft:potted_pink_tulip}.
     */
    public static final TypedKey<BlockType> POTTED_PINK_TULIP = create("potted_pink_tulip");

    /**
     * Key for {@code minecraft:potted_poppy}.
     */
    public static final TypedKey<BlockType> POTTED_POPPY = create("potted_poppy");

    /**
     * Key for {@code minecraft:potted_red_mushroom}.
     */
    public static final TypedKey<BlockType> POTTED_RED_MUSHROOM = create("potted_red_mushroom");

    /**
     * Key for {@code minecraft:potted_red_tulip}.
     */
    public static final TypedKey<BlockType> POTTED_RED_TULIP = create("potted_red_tulip");

    /**
     * Key for {@code minecraft:potted_spruce_sapling}.
     */
    public static final TypedKey<BlockType> POTTED_SPRUCE_SAPLING = create("potted_spruce_sapling");

    /**
     * Key for {@code minecraft:potted_torchflower}.
     */
    public static final TypedKey<BlockType> POTTED_TORCHFLOWER = create("potted_torchflower");

    /**
     * Key for {@code minecraft:potted_warped_fungus}.
     */
    public static final TypedKey<BlockType> POTTED_WARPED_FUNGUS = create("potted_warped_fungus");

    /**
     * Key for {@code minecraft:potted_warped_roots}.
     */
    public static final TypedKey<BlockType> POTTED_WARPED_ROOTS = create("potted_warped_roots");

    /**
     * Key for {@code minecraft:potted_white_tulip}.
     */
    public static final TypedKey<BlockType> POTTED_WHITE_TULIP = create("potted_white_tulip");

    /**
     * Key for {@code minecraft:potted_wither_rose}.
     */
    public static final TypedKey<BlockType> POTTED_WITHER_ROSE = create("potted_wither_rose");

    /**
     * Key for {@code minecraft:powder_snow}.
     */
    public static final TypedKey<BlockType> POWDER_SNOW = create("powder_snow");

    /**
     * Key for {@code minecraft:powder_snow_cauldron}.
     */
    public static final TypedKey<BlockType> POWDER_SNOW_CAULDRON = create("powder_snow_cauldron");

    /**
     * Key for {@code minecraft:powered_rail}.
     */
    public static final TypedKey<BlockType> POWERED_RAIL = create("powered_rail");

    /**
     * Key for {@code minecraft:prismarine}.
     */
    public static final TypedKey<BlockType> PRISMARINE = create("prismarine");

    /**
     * Key for {@code minecraft:prismarine_bricks}.
     */
    public static final TypedKey<BlockType> PRISMARINE_BRICKS = create("prismarine_bricks");

    /**
     * Key for {@code minecraft:prismarine_brick_slab}.
     */
    public static final TypedKey<BlockType> PRISMARINE_BRICK_SLAB = create("prismarine_brick_slab");

    /**
     * Key for {@code minecraft:prismarine_brick_stairs}.
     */
    public static final TypedKey<BlockType> PRISMARINE_BRICK_STAIRS = create("prismarine_brick_stairs");

    /**
     * Key for {@code minecraft:prismarine_slab}.
     */
    public static final TypedKey<BlockType> PRISMARINE_SLAB = create("prismarine_slab");

    /**
     * Key for {@code minecraft:prismarine_stairs}.
     */
    public static final TypedKey<BlockType> PRISMARINE_STAIRS = create("prismarine_stairs");

    /**
     * Key for {@code minecraft:prismarine_wall}.
     */
    public static final TypedKey<BlockType> PRISMARINE_WALL = create("prismarine_wall");

    /**
     * Key for {@code minecraft:pumpkin}.
     */
    public static final TypedKey<BlockType> PUMPKIN = create("pumpkin");

    /**
     * Key for {@code minecraft:pumpkin_stem}.
     */
    public static final TypedKey<BlockType> PUMPKIN_STEM = create("pumpkin_stem");

    /**
     * Key for {@code minecraft:purple_banner}.
     */
    public static final TypedKey<BlockType> PURPLE_BANNER = create("purple_banner");

    /**
     * Key for {@code minecraft:purple_bed}.
     */
    public static final TypedKey<BlockType> PURPLE_BED = create("purple_bed");

    /**
     * Key for {@code minecraft:purple_candle}.
     */
    public static final TypedKey<BlockType> PURPLE_CANDLE = create("purple_candle");

    /**
     * Key for {@code minecraft:purple_candle_cake}.
     */
    public static final TypedKey<BlockType> PURPLE_CANDLE_CAKE = create("purple_candle_cake");

    /**
     * Key for {@code minecraft:purple_carpet}.
     */
    public static final TypedKey<BlockType> PURPLE_CARPET = create("purple_carpet");

    /**
     * Key for {@code minecraft:purple_concrete}.
     */
    public static final TypedKey<BlockType> PURPLE_CONCRETE = create("purple_concrete");

    /**
     * Key for {@code minecraft:purple_concrete_powder}.
     */
    public static final TypedKey<BlockType> PURPLE_CONCRETE_POWDER = create("purple_concrete_powder");

    /**
     * Key for {@code minecraft:purple_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> PURPLE_GLAZED_TERRACOTTA = create("purple_glazed_terracotta");

    /**
     * Key for {@code minecraft:purple_shulker_box}.
     */
    public static final TypedKey<BlockType> PURPLE_SHULKER_BOX = create("purple_shulker_box");

    /**
     * Key for {@code minecraft:purple_stained_glass}.
     */
    public static final TypedKey<BlockType> PURPLE_STAINED_GLASS = create("purple_stained_glass");

    /**
     * Key for {@code minecraft:purple_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> PURPLE_STAINED_GLASS_PANE = create("purple_stained_glass_pane");

    /**
     * Key for {@code minecraft:purple_terracotta}.
     */
    public static final TypedKey<BlockType> PURPLE_TERRACOTTA = create("purple_terracotta");

    /**
     * Key for {@code minecraft:purple_wall_banner}.
     */
    public static final TypedKey<BlockType> PURPLE_WALL_BANNER = create("purple_wall_banner");

    /**
     * Key for {@code minecraft:purple_wool}.
     */
    public static final TypedKey<BlockType> PURPLE_WOOL = create("purple_wool");

    /**
     * Key for {@code minecraft:purpur_block}.
     */
    public static final TypedKey<BlockType> PURPUR_BLOCK = create("purpur_block");

    /**
     * Key for {@code minecraft:purpur_pillar}.
     */
    public static final TypedKey<BlockType> PURPUR_PILLAR = create("purpur_pillar");

    /**
     * Key for {@code minecraft:purpur_slab}.
     */
    public static final TypedKey<BlockType> PURPUR_SLAB = create("purpur_slab");

    /**
     * Key for {@code minecraft:purpur_stairs}.
     */
    public static final TypedKey<BlockType> PURPUR_STAIRS = create("purpur_stairs");

    /**
     * Key for {@code minecraft:quartz_block}.
     */
    public static final TypedKey<BlockType> QUARTZ_BLOCK = create("quartz_block");

    /**
     * Key for {@code minecraft:quartz_bricks}.
     */
    public static final TypedKey<BlockType> QUARTZ_BRICKS = create("quartz_bricks");

    /**
     * Key for {@code minecraft:quartz_pillar}.
     */
    public static final TypedKey<BlockType> QUARTZ_PILLAR = create("quartz_pillar");

    /**
     * Key for {@code minecraft:quartz_slab}.
     */
    public static final TypedKey<BlockType> QUARTZ_SLAB = create("quartz_slab");

    /**
     * Key for {@code minecraft:quartz_stairs}.
     */
    public static final TypedKey<BlockType> QUARTZ_STAIRS = create("quartz_stairs");

    /**
     * Key for {@code minecraft:rail}.
     */
    public static final TypedKey<BlockType> RAIL = create("rail");

    /**
     * Key for {@code minecraft:raw_copper_block}.
     */
    public static final TypedKey<BlockType> RAW_COPPER_BLOCK = create("raw_copper_block");

    /**
     * Key for {@code minecraft:raw_gold_block}.
     */
    public static final TypedKey<BlockType> RAW_GOLD_BLOCK = create("raw_gold_block");

    /**
     * Key for {@code minecraft:raw_iron_block}.
     */
    public static final TypedKey<BlockType> RAW_IRON_BLOCK = create("raw_iron_block");

    /**
     * Key for {@code minecraft:redstone_block}.
     */
    public static final TypedKey<BlockType> REDSTONE_BLOCK = create("redstone_block");

    /**
     * Key for {@code minecraft:redstone_lamp}.
     */
    public static final TypedKey<BlockType> REDSTONE_LAMP = create("redstone_lamp");

    /**
     * Key for {@code minecraft:redstone_ore}.
     */
    public static final TypedKey<BlockType> REDSTONE_ORE = create("redstone_ore");

    /**
     * Key for {@code minecraft:redstone_torch}.
     */
    public static final TypedKey<BlockType> REDSTONE_TORCH = create("redstone_torch");

    /**
     * Key for {@code minecraft:redstone_wall_torch}.
     */
    public static final TypedKey<BlockType> REDSTONE_WALL_TORCH = create("redstone_wall_torch");

    /**
     * Key for {@code minecraft:redstone_wire}.
     */
    public static final TypedKey<BlockType> REDSTONE_WIRE = create("redstone_wire");

    /**
     * Key for {@code minecraft:red_banner}.
     */
    public static final TypedKey<BlockType> RED_BANNER = create("red_banner");

    /**
     * Key for {@code minecraft:red_bed}.
     */
    public static final TypedKey<BlockType> RED_BED = create("red_bed");

    /**
     * Key for {@code minecraft:red_candle}.
     */
    public static final TypedKey<BlockType> RED_CANDLE = create("red_candle");

    /**
     * Key for {@code minecraft:red_candle_cake}.
     */
    public static final TypedKey<BlockType> RED_CANDLE_CAKE = create("red_candle_cake");

    /**
     * Key for {@code minecraft:red_carpet}.
     */
    public static final TypedKey<BlockType> RED_CARPET = create("red_carpet");

    /**
     * Key for {@code minecraft:red_concrete}.
     */
    public static final TypedKey<BlockType> RED_CONCRETE = create("red_concrete");

    /**
     * Key for {@code minecraft:red_concrete_powder}.
     */
    public static final TypedKey<BlockType> RED_CONCRETE_POWDER = create("red_concrete_powder");

    /**
     * Key for {@code minecraft:red_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> RED_GLAZED_TERRACOTTA = create("red_glazed_terracotta");

    /**
     * Key for {@code minecraft:red_mushroom}.
     */
    public static final TypedKey<BlockType> RED_MUSHROOM = create("red_mushroom");

    /**
     * Key for {@code minecraft:red_mushroom_block}.
     */
    public static final TypedKey<BlockType> RED_MUSHROOM_BLOCK = create("red_mushroom_block");

    /**
     * Key for {@code minecraft:red_nether_bricks}.
     */
    public static final TypedKey<BlockType> RED_NETHER_BRICKS = create("red_nether_bricks");

    /**
     * Key for {@code minecraft:red_nether_brick_slab}.
     */
    public static final TypedKey<BlockType> RED_NETHER_BRICK_SLAB = create("red_nether_brick_slab");

    /**
     * Key for {@code minecraft:red_nether_brick_stairs}.
     */
    public static final TypedKey<BlockType> RED_NETHER_BRICK_STAIRS = create("red_nether_brick_stairs");

    /**
     * Key for {@code minecraft:red_nether_brick_wall}.
     */
    public static final TypedKey<BlockType> RED_NETHER_BRICK_WALL = create("red_nether_brick_wall");

    /**
     * Key for {@code minecraft:red_sand}.
     */
    public static final TypedKey<BlockType> RED_SAND = create("red_sand");

    /**
     * Key for {@code minecraft:red_sandstone}.
     */
    public static final TypedKey<BlockType> RED_SANDSTONE = create("red_sandstone");

    /**
     * Key for {@code minecraft:red_sandstone_slab}.
     */
    public static final TypedKey<BlockType> RED_SANDSTONE_SLAB = create("red_sandstone_slab");

    /**
     * Key for {@code minecraft:red_sandstone_stairs}.
     */
    public static final TypedKey<BlockType> RED_SANDSTONE_STAIRS = create("red_sandstone_stairs");

    /**
     * Key for {@code minecraft:red_sandstone_wall}.
     */
    public static final TypedKey<BlockType> RED_SANDSTONE_WALL = create("red_sandstone_wall");

    /**
     * Key for {@code minecraft:red_shulker_box}.
     */
    public static final TypedKey<BlockType> RED_SHULKER_BOX = create("red_shulker_box");

    /**
     * Key for {@code minecraft:red_stained_glass}.
     */
    public static final TypedKey<BlockType> RED_STAINED_GLASS = create("red_stained_glass");

    /**
     * Key for {@code minecraft:red_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> RED_STAINED_GLASS_PANE = create("red_stained_glass_pane");

    /**
     * Key for {@code minecraft:red_terracotta}.
     */
    public static final TypedKey<BlockType> RED_TERRACOTTA = create("red_terracotta");

    /**
     * Key for {@code minecraft:red_tulip}.
     */
    public static final TypedKey<BlockType> RED_TULIP = create("red_tulip");

    /**
     * Key for {@code minecraft:red_wall_banner}.
     */
    public static final TypedKey<BlockType> RED_WALL_BANNER = create("red_wall_banner");

    /**
     * Key for {@code minecraft:red_wool}.
     */
    public static final TypedKey<BlockType> RED_WOOL = create("red_wool");

    /**
     * Key for {@code minecraft:reinforced_deepslate}.
     */
    public static final TypedKey<BlockType> REINFORCED_DEEPSLATE = create("reinforced_deepslate");

    /**
     * Key for {@code minecraft:repeater}.
     */
    public static final TypedKey<BlockType> REPEATER = create("repeater");

    /**
     * Key for {@code minecraft:repeating_command_block}.
     */
    public static final TypedKey<BlockType> REPEATING_COMMAND_BLOCK = create("repeating_command_block");

    /**
     * Key for {@code minecraft:resin_block}.
     */
    public static final TypedKey<BlockType> RESIN_BLOCK = create("resin_block");

    /**
     * Key for {@code minecraft:resin_bricks}.
     */
    public static final TypedKey<BlockType> RESIN_BRICKS = create("resin_bricks");

    /**
     * Key for {@code minecraft:resin_brick_slab}.
     */
    public static final TypedKey<BlockType> RESIN_BRICK_SLAB = create("resin_brick_slab");

    /**
     * Key for {@code minecraft:resin_brick_stairs}.
     */
    public static final TypedKey<BlockType> RESIN_BRICK_STAIRS = create("resin_brick_stairs");

    /**
     * Key for {@code minecraft:resin_brick_wall}.
     */
    public static final TypedKey<BlockType> RESIN_BRICK_WALL = create("resin_brick_wall");

    /**
     * Key for {@code minecraft:resin_clump}.
     */
    public static final TypedKey<BlockType> RESIN_CLUMP = create("resin_clump");

    /**
     * Key for {@code minecraft:respawn_anchor}.
     */
    public static final TypedKey<BlockType> RESPAWN_ANCHOR = create("respawn_anchor");

    /**
     * Key for {@code minecraft:rooted_dirt}.
     */
    public static final TypedKey<BlockType> ROOTED_DIRT = create("rooted_dirt");

    /**
     * Key for {@code minecraft:rose_bush}.
     */
    public static final TypedKey<BlockType> ROSE_BUSH = create("rose_bush");

    /**
     * Key for {@code minecraft:sand}.
     */
    public static final TypedKey<BlockType> SAND = create("sand");

    /**
     * Key for {@code minecraft:sandstone}.
     */
    public static final TypedKey<BlockType> SANDSTONE = create("sandstone");

    /**
     * Key for {@code minecraft:sandstone_slab}.
     */
    public static final TypedKey<BlockType> SANDSTONE_SLAB = create("sandstone_slab");

    /**
     * Key for {@code minecraft:sandstone_stairs}.
     */
    public static final TypedKey<BlockType> SANDSTONE_STAIRS = create("sandstone_stairs");

    /**
     * Key for {@code minecraft:sandstone_wall}.
     */
    public static final TypedKey<BlockType> SANDSTONE_WALL = create("sandstone_wall");

    /**
     * Key for {@code minecraft:scaffolding}.
     */
    public static final TypedKey<BlockType> SCAFFOLDING = create("scaffolding");

    /**
     * Key for {@code minecraft:sculk}.
     */
    public static final TypedKey<BlockType> SCULK = create("sculk");

    /**
     * Key for {@code minecraft:sculk_catalyst}.
     */
    public static final TypedKey<BlockType> SCULK_CATALYST = create("sculk_catalyst");

    /**
     * Key for {@code minecraft:sculk_sensor}.
     */
    public static final TypedKey<BlockType> SCULK_SENSOR = create("sculk_sensor");

    /**
     * Key for {@code minecraft:sculk_shrieker}.
     */
    public static final TypedKey<BlockType> SCULK_SHRIEKER = create("sculk_shrieker");

    /**
     * Key for {@code minecraft:sculk_vein}.
     */
    public static final TypedKey<BlockType> SCULK_VEIN = create("sculk_vein");

    /**
     * Key for {@code minecraft:seagrass}.
     */
    public static final TypedKey<BlockType> SEAGRASS = create("seagrass");

    /**
     * Key for {@code minecraft:sea_lantern}.
     */
    public static final TypedKey<BlockType> SEA_LANTERN = create("sea_lantern");

    /**
     * Key for {@code minecraft:sea_pickle}.
     */
    public static final TypedKey<BlockType> SEA_PICKLE = create("sea_pickle");

    /**
     * Key for {@code minecraft:short_dry_grass}.
     */
    public static final TypedKey<BlockType> SHORT_DRY_GRASS = create("short_dry_grass");

    /**
     * Key for {@code minecraft:short_grass}.
     */
    public static final TypedKey<BlockType> SHORT_GRASS = create("short_grass");

    /**
     * Key for {@code minecraft:shroomlight}.
     */
    public static final TypedKey<BlockType> SHROOMLIGHT = create("shroomlight");

    /**
     * Key for {@code minecraft:shulker_box}.
     */
    public static final TypedKey<BlockType> SHULKER_BOX = create("shulker_box");

    /**
     * Key for {@code minecraft:skeleton_skull}.
     */
    public static final TypedKey<BlockType> SKELETON_SKULL = create("skeleton_skull");

    /**
     * Key for {@code minecraft:skeleton_wall_skull}.
     */
    public static final TypedKey<BlockType> SKELETON_WALL_SKULL = create("skeleton_wall_skull");

    /**
     * Key for {@code minecraft:slime_block}.
     */
    public static final TypedKey<BlockType> SLIME_BLOCK = create("slime_block");

    /**
     * Key for {@code minecraft:small_amethyst_bud}.
     */
    public static final TypedKey<BlockType> SMALL_AMETHYST_BUD = create("small_amethyst_bud");

    /**
     * Key for {@code minecraft:small_dripleaf}.
     */
    public static final TypedKey<BlockType> SMALL_DRIPLEAF = create("small_dripleaf");

    /**
     * Key for {@code minecraft:smithing_table}.
     */
    public static final TypedKey<BlockType> SMITHING_TABLE = create("smithing_table");

    /**
     * Key for {@code minecraft:smoker}.
     */
    public static final TypedKey<BlockType> SMOKER = create("smoker");

    /**
     * Key for {@code minecraft:smooth_basalt}.
     */
    public static final TypedKey<BlockType> SMOOTH_BASALT = create("smooth_basalt");

    /**
     * Key for {@code minecraft:smooth_quartz}.
     */
    public static final TypedKey<BlockType> SMOOTH_QUARTZ = create("smooth_quartz");

    /**
     * Key for {@code minecraft:smooth_quartz_slab}.
     */
    public static final TypedKey<BlockType> SMOOTH_QUARTZ_SLAB = create("smooth_quartz_slab");

    /**
     * Key for {@code minecraft:smooth_quartz_stairs}.
     */
    public static final TypedKey<BlockType> SMOOTH_QUARTZ_STAIRS = create("smooth_quartz_stairs");

    /**
     * Key for {@code minecraft:smooth_red_sandstone}.
     */
    public static final TypedKey<BlockType> SMOOTH_RED_SANDSTONE = create("smooth_red_sandstone");

    /**
     * Key for {@code minecraft:smooth_red_sandstone_slab}.
     */
    public static final TypedKey<BlockType> SMOOTH_RED_SANDSTONE_SLAB = create("smooth_red_sandstone_slab");

    /**
     * Key for {@code minecraft:smooth_red_sandstone_stairs}.
     */
    public static final TypedKey<BlockType> SMOOTH_RED_SANDSTONE_STAIRS = create("smooth_red_sandstone_stairs");

    /**
     * Key for {@code minecraft:smooth_sandstone}.
     */
    public static final TypedKey<BlockType> SMOOTH_SANDSTONE = create("smooth_sandstone");

    /**
     * Key for {@code minecraft:smooth_sandstone_slab}.
     */
    public static final TypedKey<BlockType> SMOOTH_SANDSTONE_SLAB = create("smooth_sandstone_slab");

    /**
     * Key for {@code minecraft:smooth_sandstone_stairs}.
     */
    public static final TypedKey<BlockType> SMOOTH_SANDSTONE_STAIRS = create("smooth_sandstone_stairs");

    /**
     * Key for {@code minecraft:smooth_stone}.
     */
    public static final TypedKey<BlockType> SMOOTH_STONE = create("smooth_stone");

    /**
     * Key for {@code minecraft:smooth_stone_slab}.
     */
    public static final TypedKey<BlockType> SMOOTH_STONE_SLAB = create("smooth_stone_slab");

    /**
     * Key for {@code minecraft:sniffer_egg}.
     */
    public static final TypedKey<BlockType> SNIFFER_EGG = create("sniffer_egg");

    /**
     * Key for {@code minecraft:snow}.
     */
    public static final TypedKey<BlockType> SNOW = create("snow");

    /**
     * Key for {@code minecraft:snow_block}.
     */
    public static final TypedKey<BlockType> SNOW_BLOCK = create("snow_block");

    /**
     * Key for {@code minecraft:soul_campfire}.
     */
    public static final TypedKey<BlockType> SOUL_CAMPFIRE = create("soul_campfire");

    /**
     * Key for {@code minecraft:soul_fire}.
     */
    public static final TypedKey<BlockType> SOUL_FIRE = create("soul_fire");

    /**
     * Key for {@code minecraft:soul_lantern}.
     */
    public static final TypedKey<BlockType> SOUL_LANTERN = create("soul_lantern");

    /**
     * Key for {@code minecraft:soul_sand}.
     */
    public static final TypedKey<BlockType> SOUL_SAND = create("soul_sand");

    /**
     * Key for {@code minecraft:soul_soil}.
     */
    public static final TypedKey<BlockType> SOUL_SOIL = create("soul_soil");

    /**
     * Key for {@code minecraft:soul_torch}.
     */
    public static final TypedKey<BlockType> SOUL_TORCH = create("soul_torch");

    /**
     * Key for {@code minecraft:soul_wall_torch}.
     */
    public static final TypedKey<BlockType> SOUL_WALL_TORCH = create("soul_wall_torch");

    /**
     * Key for {@code minecraft:spawner}.
     */
    public static final TypedKey<BlockType> SPAWNER = create("spawner");

    /**
     * Key for {@code minecraft:sponge}.
     */
    public static final TypedKey<BlockType> SPONGE = create("sponge");

    /**
     * Key for {@code minecraft:spore_blossom}.
     */
    public static final TypedKey<BlockType> SPORE_BLOSSOM = create("spore_blossom");

    /**
     * Key for {@code minecraft:spruce_button}.
     */
    public static final TypedKey<BlockType> SPRUCE_BUTTON = create("spruce_button");

    /**
     * Key for {@code minecraft:spruce_door}.
     */
    public static final TypedKey<BlockType> SPRUCE_DOOR = create("spruce_door");

    /**
     * Key for {@code minecraft:spruce_fence}.
     */
    public static final TypedKey<BlockType> SPRUCE_FENCE = create("spruce_fence");

    /**
     * Key for {@code minecraft:spruce_fence_gate}.
     */
    public static final TypedKey<BlockType> SPRUCE_FENCE_GATE = create("spruce_fence_gate");

    /**
     * Key for {@code minecraft:spruce_hanging_sign}.
     */
    public static final TypedKey<BlockType> SPRUCE_HANGING_SIGN = create("spruce_hanging_sign");

    /**
     * Key for {@code minecraft:spruce_leaves}.
     */
    public static final TypedKey<BlockType> SPRUCE_LEAVES = create("spruce_leaves");

    /**
     * Key for {@code minecraft:spruce_log}.
     */
    public static final TypedKey<BlockType> SPRUCE_LOG = create("spruce_log");

    /**
     * Key for {@code minecraft:spruce_planks}.
     */
    public static final TypedKey<BlockType> SPRUCE_PLANKS = create("spruce_planks");

    /**
     * Key for {@code minecraft:spruce_pressure_plate}.
     */
    public static final TypedKey<BlockType> SPRUCE_PRESSURE_PLATE = create("spruce_pressure_plate");

    /**
     * Key for {@code minecraft:spruce_sapling}.
     */
    public static final TypedKey<BlockType> SPRUCE_SAPLING = create("spruce_sapling");

    /**
     * Key for {@code minecraft:spruce_shelf}.
     */
    public static final TypedKey<BlockType> SPRUCE_SHELF = create("spruce_shelf");

    /**
     * Key for {@code minecraft:spruce_sign}.
     */
    public static final TypedKey<BlockType> SPRUCE_SIGN = create("spruce_sign");

    /**
     * Key for {@code minecraft:spruce_slab}.
     */
    public static final TypedKey<BlockType> SPRUCE_SLAB = create("spruce_slab");

    /**
     * Key for {@code minecraft:spruce_stairs}.
     */
    public static final TypedKey<BlockType> SPRUCE_STAIRS = create("spruce_stairs");

    /**
     * Key for {@code minecraft:spruce_trapdoor}.
     */
    public static final TypedKey<BlockType> SPRUCE_TRAPDOOR = create("spruce_trapdoor");

    /**
     * Key for {@code minecraft:spruce_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> SPRUCE_WALL_HANGING_SIGN = create("spruce_wall_hanging_sign");

    /**
     * Key for {@code minecraft:spruce_wall_sign}.
     */
    public static final TypedKey<BlockType> SPRUCE_WALL_SIGN = create("spruce_wall_sign");

    /**
     * Key for {@code minecraft:spruce_wood}.
     */
    public static final TypedKey<BlockType> SPRUCE_WOOD = create("spruce_wood");

    /**
     * Key for {@code minecraft:sticky_piston}.
     */
    public static final TypedKey<BlockType> STICKY_PISTON = create("sticky_piston");

    /**
     * Key for {@code minecraft:stone}.
     */
    public static final TypedKey<BlockType> STONE = create("stone");

    /**
     * Key for {@code minecraft:stonecutter}.
     */
    public static final TypedKey<BlockType> STONECUTTER = create("stonecutter");

    /**
     * Key for {@code minecraft:stone_bricks}.
     */
    public static final TypedKey<BlockType> STONE_BRICKS = create("stone_bricks");

    /**
     * Key for {@code minecraft:stone_brick_slab}.
     */
    public static final TypedKey<BlockType> STONE_BRICK_SLAB = create("stone_brick_slab");

    /**
     * Key for {@code minecraft:stone_brick_stairs}.
     */
    public static final TypedKey<BlockType> STONE_BRICK_STAIRS = create("stone_brick_stairs");

    /**
     * Key for {@code minecraft:stone_brick_wall}.
     */
    public static final TypedKey<BlockType> STONE_BRICK_WALL = create("stone_brick_wall");

    /**
     * Key for {@code minecraft:stone_button}.
     */
    public static final TypedKey<BlockType> STONE_BUTTON = create("stone_button");

    /**
     * Key for {@code minecraft:stone_pressure_plate}.
     */
    public static final TypedKey<BlockType> STONE_PRESSURE_PLATE = create("stone_pressure_plate");

    /**
     * Key for {@code minecraft:stone_slab}.
     */
    public static final TypedKey<BlockType> STONE_SLAB = create("stone_slab");

    /**
     * Key for {@code minecraft:stone_stairs}.
     */
    public static final TypedKey<BlockType> STONE_STAIRS = create("stone_stairs");

    /**
     * Key for {@code minecraft:stripped_acacia_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_ACACIA_LOG = create("stripped_acacia_log");

    /**
     * Key for {@code minecraft:stripped_acacia_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_ACACIA_WOOD = create("stripped_acacia_wood");

    /**
     * Key for {@code minecraft:stripped_bamboo_block}.
     */
    public static final TypedKey<BlockType> STRIPPED_BAMBOO_BLOCK = create("stripped_bamboo_block");

    /**
     * Key for {@code minecraft:stripped_birch_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_BIRCH_LOG = create("stripped_birch_log");

    /**
     * Key for {@code minecraft:stripped_birch_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_BIRCH_WOOD = create("stripped_birch_wood");

    /**
     * Key for {@code minecraft:stripped_cherry_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_CHERRY_LOG = create("stripped_cherry_log");

    /**
     * Key for {@code minecraft:stripped_cherry_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_CHERRY_WOOD = create("stripped_cherry_wood");

    /**
     * Key for {@code minecraft:stripped_crimson_hyphae}.
     */
    public static final TypedKey<BlockType> STRIPPED_CRIMSON_HYPHAE = create("stripped_crimson_hyphae");

    /**
     * Key for {@code minecraft:stripped_crimson_stem}.
     */
    public static final TypedKey<BlockType> STRIPPED_CRIMSON_STEM = create("stripped_crimson_stem");

    /**
     * Key for {@code minecraft:stripped_dark_oak_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_DARK_OAK_LOG = create("stripped_dark_oak_log");

    /**
     * Key for {@code minecraft:stripped_dark_oak_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_DARK_OAK_WOOD = create("stripped_dark_oak_wood");

    /**
     * Key for {@code minecraft:stripped_jungle_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_JUNGLE_LOG = create("stripped_jungle_log");

    /**
     * Key for {@code minecraft:stripped_jungle_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_JUNGLE_WOOD = create("stripped_jungle_wood");

    /**
     * Key for {@code minecraft:stripped_mangrove_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_MANGROVE_LOG = create("stripped_mangrove_log");

    /**
     * Key for {@code minecraft:stripped_mangrove_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_MANGROVE_WOOD = create("stripped_mangrove_wood");

    /**
     * Key for {@code minecraft:stripped_oak_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_OAK_LOG = create("stripped_oak_log");

    /**
     * Key for {@code minecraft:stripped_oak_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_OAK_WOOD = create("stripped_oak_wood");

    /**
     * Key for {@code minecraft:stripped_pale_oak_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_PALE_OAK_LOG = create("stripped_pale_oak_log");

    /**
     * Key for {@code minecraft:stripped_pale_oak_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_PALE_OAK_WOOD = create("stripped_pale_oak_wood");

    /**
     * Key for {@code minecraft:stripped_spruce_log}.
     */
    public static final TypedKey<BlockType> STRIPPED_SPRUCE_LOG = create("stripped_spruce_log");

    /**
     * Key for {@code minecraft:stripped_spruce_wood}.
     */
    public static final TypedKey<BlockType> STRIPPED_SPRUCE_WOOD = create("stripped_spruce_wood");

    /**
     * Key for {@code minecraft:stripped_warped_hyphae}.
     */
    public static final TypedKey<BlockType> STRIPPED_WARPED_HYPHAE = create("stripped_warped_hyphae");

    /**
     * Key for {@code minecraft:stripped_warped_stem}.
     */
    public static final TypedKey<BlockType> STRIPPED_WARPED_STEM = create("stripped_warped_stem");

    /**
     * Key for {@code minecraft:structure_block}.
     */
    public static final TypedKey<BlockType> STRUCTURE_BLOCK = create("structure_block");

    /**
     * Key for {@code minecraft:structure_void}.
     */
    public static final TypedKey<BlockType> STRUCTURE_VOID = create("structure_void");

    /**
     * Key for {@code minecraft:sugar_cane}.
     */
    public static final TypedKey<BlockType> SUGAR_CANE = create("sugar_cane");

    /**
     * Key for {@code minecraft:sulfur}.
     */
    public static final TypedKey<BlockType> SULFUR = create("sulfur");

    /**
     * Key for {@code minecraft:sulfur_bricks}.
     */
    public static final TypedKey<BlockType> SULFUR_BRICKS = create("sulfur_bricks");

    /**
     * Key for {@code minecraft:sulfur_brick_slab}.
     */
    public static final TypedKey<BlockType> SULFUR_BRICK_SLAB = create("sulfur_brick_slab");

    /**
     * Key for {@code minecraft:sulfur_brick_stairs}.
     */
    public static final TypedKey<BlockType> SULFUR_BRICK_STAIRS = create("sulfur_brick_stairs");

    /**
     * Key for {@code minecraft:sulfur_brick_wall}.
     */
    public static final TypedKey<BlockType> SULFUR_BRICK_WALL = create("sulfur_brick_wall");

    /**
     * Key for {@code minecraft:sulfur_slab}.
     */
    public static final TypedKey<BlockType> SULFUR_SLAB = create("sulfur_slab");

    /**
     * Key for {@code minecraft:sulfur_spike}.
     */
    public static final TypedKey<BlockType> SULFUR_SPIKE = create("sulfur_spike");

    /**
     * Key for {@code minecraft:sulfur_stairs}.
     */
    public static final TypedKey<BlockType> SULFUR_STAIRS = create("sulfur_stairs");

    /**
     * Key for {@code minecraft:sulfur_wall}.
     */
    public static final TypedKey<BlockType> SULFUR_WALL = create("sulfur_wall");

    /**
     * Key for {@code minecraft:sunflower}.
     */
    public static final TypedKey<BlockType> SUNFLOWER = create("sunflower");

    /**
     * Key for {@code minecraft:suspicious_gravel}.
     */
    public static final TypedKey<BlockType> SUSPICIOUS_GRAVEL = create("suspicious_gravel");

    /**
     * Key for {@code minecraft:suspicious_sand}.
     */
    public static final TypedKey<BlockType> SUSPICIOUS_SAND = create("suspicious_sand");

    /**
     * Key for {@code minecraft:sweet_berry_bush}.
     */
    public static final TypedKey<BlockType> SWEET_BERRY_BUSH = create("sweet_berry_bush");

    /**
     * Key for {@code minecraft:tall_dry_grass}.
     */
    public static final TypedKey<BlockType> TALL_DRY_GRASS = create("tall_dry_grass");

    /**
     * Key for {@code minecraft:tall_grass}.
     */
    public static final TypedKey<BlockType> TALL_GRASS = create("tall_grass");

    /**
     * Key for {@code minecraft:tall_seagrass}.
     */
    public static final TypedKey<BlockType> TALL_SEAGRASS = create("tall_seagrass");

    /**
     * Key for {@code minecraft:target}.
     */
    public static final TypedKey<BlockType> TARGET = create("target");

    /**
     * Key for {@code minecraft:terracotta}.
     */
    public static final TypedKey<BlockType> TERRACOTTA = create("terracotta");

    /**
     * Key for {@code minecraft:test_block}.
     */
    public static final TypedKey<BlockType> TEST_BLOCK = create("test_block");

    /**
     * Key for {@code minecraft:test_instance_block}.
     */
    public static final TypedKey<BlockType> TEST_INSTANCE_BLOCK = create("test_instance_block");

    /**
     * Key for {@code minecraft:tinted_glass}.
     */
    public static final TypedKey<BlockType> TINTED_GLASS = create("tinted_glass");

    /**
     * Key for {@code minecraft:tnt}.
     */
    public static final TypedKey<BlockType> TNT = create("tnt");

    /**
     * Key for {@code minecraft:torch}.
     */
    public static final TypedKey<BlockType> TORCH = create("torch");

    /**
     * Key for {@code minecraft:torchflower}.
     */
    public static final TypedKey<BlockType> TORCHFLOWER = create("torchflower");

    /**
     * Key for {@code minecraft:torchflower_crop}.
     */
    public static final TypedKey<BlockType> TORCHFLOWER_CROP = create("torchflower_crop");

    /**
     * Key for {@code minecraft:trapped_chest}.
     */
    public static final TypedKey<BlockType> TRAPPED_CHEST = create("trapped_chest");

    /**
     * Key for {@code minecraft:trial_spawner}.
     */
    public static final TypedKey<BlockType> TRIAL_SPAWNER = create("trial_spawner");

    /**
     * Key for {@code minecraft:tripwire}.
     */
    public static final TypedKey<BlockType> TRIPWIRE = create("tripwire");

    /**
     * Key for {@code minecraft:tripwire_hook}.
     */
    public static final TypedKey<BlockType> TRIPWIRE_HOOK = create("tripwire_hook");

    /**
     * Key for {@code minecraft:tube_coral}.
     */
    public static final TypedKey<BlockType> TUBE_CORAL = create("tube_coral");

    /**
     * Key for {@code minecraft:tube_coral_block}.
     */
    public static final TypedKey<BlockType> TUBE_CORAL_BLOCK = create("tube_coral_block");

    /**
     * Key for {@code minecraft:tube_coral_fan}.
     */
    public static final TypedKey<BlockType> TUBE_CORAL_FAN = create("tube_coral_fan");

    /**
     * Key for {@code minecraft:tube_coral_wall_fan}.
     */
    public static final TypedKey<BlockType> TUBE_CORAL_WALL_FAN = create("tube_coral_wall_fan");

    /**
     * Key for {@code minecraft:tuff}.
     */
    public static final TypedKey<BlockType> TUFF = create("tuff");

    /**
     * Key for {@code minecraft:tuff_bricks}.
     */
    public static final TypedKey<BlockType> TUFF_BRICKS = create("tuff_bricks");

    /**
     * Key for {@code minecraft:tuff_brick_slab}.
     */
    public static final TypedKey<BlockType> TUFF_BRICK_SLAB = create("tuff_brick_slab");

    /**
     * Key for {@code minecraft:tuff_brick_stairs}.
     */
    public static final TypedKey<BlockType> TUFF_BRICK_STAIRS = create("tuff_brick_stairs");

    /**
     * Key for {@code minecraft:tuff_brick_wall}.
     */
    public static final TypedKey<BlockType> TUFF_BRICK_WALL = create("tuff_brick_wall");

    /**
     * Key for {@code minecraft:tuff_slab}.
     */
    public static final TypedKey<BlockType> TUFF_SLAB = create("tuff_slab");

    /**
     * Key for {@code minecraft:tuff_stairs}.
     */
    public static final TypedKey<BlockType> TUFF_STAIRS = create("tuff_stairs");

    /**
     * Key for {@code minecraft:tuff_wall}.
     */
    public static final TypedKey<BlockType> TUFF_WALL = create("tuff_wall");

    /**
     * Key for {@code minecraft:turtle_egg}.
     */
    public static final TypedKey<BlockType> TURTLE_EGG = create("turtle_egg");

    /**
     * Key for {@code minecraft:twisting_vines}.
     */
    public static final TypedKey<BlockType> TWISTING_VINES = create("twisting_vines");

    /**
     * Key for {@code minecraft:twisting_vines_plant}.
     */
    public static final TypedKey<BlockType> TWISTING_VINES_PLANT = create("twisting_vines_plant");

    /**
     * Key for {@code minecraft:vault}.
     */
    public static final TypedKey<BlockType> VAULT = create("vault");

    /**
     * Key for {@code minecraft:verdant_froglight}.
     */
    public static final TypedKey<BlockType> VERDANT_FROGLIGHT = create("verdant_froglight");

    /**
     * Key for {@code minecraft:vine}.
     */
    public static final TypedKey<BlockType> VINE = create("vine");

    /**
     * Key for {@code minecraft:void_air}.
     */
    public static final TypedKey<BlockType> VOID_AIR = create("void_air");

    /**
     * Key for {@code minecraft:wall_torch}.
     */
    public static final TypedKey<BlockType> WALL_TORCH = create("wall_torch");

    /**
     * Key for {@code minecraft:warped_button}.
     */
    public static final TypedKey<BlockType> WARPED_BUTTON = create("warped_button");

    /**
     * Key for {@code minecraft:warped_door}.
     */
    public static final TypedKey<BlockType> WARPED_DOOR = create("warped_door");

    /**
     * Key for {@code minecraft:warped_fence}.
     */
    public static final TypedKey<BlockType> WARPED_FENCE = create("warped_fence");

    /**
     * Key for {@code minecraft:warped_fence_gate}.
     */
    public static final TypedKey<BlockType> WARPED_FENCE_GATE = create("warped_fence_gate");

    /**
     * Key for {@code minecraft:warped_fungus}.
     */
    public static final TypedKey<BlockType> WARPED_FUNGUS = create("warped_fungus");

    /**
     * Key for {@code minecraft:warped_hanging_sign}.
     */
    public static final TypedKey<BlockType> WARPED_HANGING_SIGN = create("warped_hanging_sign");

    /**
     * Key for {@code minecraft:warped_hyphae}.
     */
    public static final TypedKey<BlockType> WARPED_HYPHAE = create("warped_hyphae");

    /**
     * Key for {@code minecraft:warped_nylium}.
     */
    public static final TypedKey<BlockType> WARPED_NYLIUM = create("warped_nylium");

    /**
     * Key for {@code minecraft:warped_planks}.
     */
    public static final TypedKey<BlockType> WARPED_PLANKS = create("warped_planks");

    /**
     * Key for {@code minecraft:warped_pressure_plate}.
     */
    public static final TypedKey<BlockType> WARPED_PRESSURE_PLATE = create("warped_pressure_plate");

    /**
     * Key for {@code minecraft:warped_roots}.
     */
    public static final TypedKey<BlockType> WARPED_ROOTS = create("warped_roots");

    /**
     * Key for {@code minecraft:warped_shelf}.
     */
    public static final TypedKey<BlockType> WARPED_SHELF = create("warped_shelf");

    /**
     * Key for {@code minecraft:warped_sign}.
     */
    public static final TypedKey<BlockType> WARPED_SIGN = create("warped_sign");

    /**
     * Key for {@code minecraft:warped_slab}.
     */
    public static final TypedKey<BlockType> WARPED_SLAB = create("warped_slab");

    /**
     * Key for {@code minecraft:warped_stairs}.
     */
    public static final TypedKey<BlockType> WARPED_STAIRS = create("warped_stairs");

    /**
     * Key for {@code minecraft:warped_stem}.
     */
    public static final TypedKey<BlockType> WARPED_STEM = create("warped_stem");

    /**
     * Key for {@code minecraft:warped_trapdoor}.
     */
    public static final TypedKey<BlockType> WARPED_TRAPDOOR = create("warped_trapdoor");

    /**
     * Key for {@code minecraft:warped_wall_hanging_sign}.
     */
    public static final TypedKey<BlockType> WARPED_WALL_HANGING_SIGN = create("warped_wall_hanging_sign");

    /**
     * Key for {@code minecraft:warped_wall_sign}.
     */
    public static final TypedKey<BlockType> WARPED_WALL_SIGN = create("warped_wall_sign");

    /**
     * Key for {@code minecraft:warped_wart_block}.
     */
    public static final TypedKey<BlockType> WARPED_WART_BLOCK = create("warped_wart_block");

    /**
     * Key for {@code minecraft:water}.
     */
    public static final TypedKey<BlockType> WATER = create("water");

    /**
     * Key for {@code minecraft:water_cauldron}.
     */
    public static final TypedKey<BlockType> WATER_CAULDRON = create("water_cauldron");

    /**
     * Key for {@code minecraft:waxed_chiseled_copper}.
     */
    public static final TypedKey<BlockType> WAXED_CHISELED_COPPER = create("waxed_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_copper_bars}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_BARS = create("waxed_copper_bars");

    /**
     * Key for {@code minecraft:waxed_copper_block}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_BLOCK = create("waxed_copper_block");

    /**
     * Key for {@code minecraft:waxed_copper_bulb}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_BULB = create("waxed_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_copper_chain}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_CHAIN = create("waxed_copper_chain");

    /**
     * Key for {@code minecraft:waxed_copper_chest}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_CHEST = create("waxed_copper_chest");

    /**
     * Key for {@code minecraft:waxed_copper_door}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_DOOR = create("waxed_copper_door");

    /**
     * Key for {@code minecraft:waxed_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_GOLEM_STATUE = create("waxed_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_copper_grate}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_GRATE = create("waxed_copper_grate");

    /**
     * Key for {@code minecraft:waxed_copper_lantern}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_LANTERN = create("waxed_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> WAXED_COPPER_TRAPDOOR = create("waxed_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_cut_copper}.
     */
    public static final TypedKey<BlockType> WAXED_CUT_COPPER = create("waxed_cut_copper");

    /**
     * Key for {@code minecraft:waxed_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> WAXED_CUT_COPPER_SLAB = create("waxed_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> WAXED_CUT_COPPER_STAIRS = create("waxed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_exposed_chiseled_copper}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_CHISELED_COPPER = create("waxed_exposed_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_copper}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER = create("waxed_exposed_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_bars}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_BARS = create("waxed_exposed_copper_bars");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_bulb}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_BULB = create("waxed_exposed_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_chain}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_CHAIN = create("waxed_exposed_copper_chain");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_chest}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_CHEST = create("waxed_exposed_copper_chest");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_door}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_DOOR = create("waxed_exposed_copper_door");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_GOLEM_STATUE = create("waxed_exposed_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_grate}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_GRATE = create("waxed_exposed_copper_grate");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_lantern}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_LANTERN = create("waxed_exposed_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_COPPER_TRAPDOOR = create("waxed_exposed_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_CUT_COPPER = create("waxed_exposed_cut_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_CUT_COPPER_SLAB = create("waxed_exposed_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_CUT_COPPER_STAIRS = create("waxed_exposed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_exposed_lightning_rod}.
     */
    public static final TypedKey<BlockType> WAXED_EXPOSED_LIGHTNING_ROD = create("waxed_exposed_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_lightning_rod}.
     */
    public static final TypedKey<BlockType> WAXED_LIGHTNING_ROD = create("waxed_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_oxidized_chiseled_copper}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_CHISELED_COPPER = create("waxed_oxidized_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER = create("waxed_oxidized_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_bars}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_BARS = create("waxed_oxidized_copper_bars");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_bulb}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_BULB = create("waxed_oxidized_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_chain}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_CHAIN = create("waxed_oxidized_copper_chain");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_chest}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_CHEST = create("waxed_oxidized_copper_chest");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_door}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_DOOR = create("waxed_oxidized_copper_door");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_GOLEM_STATUE = create("waxed_oxidized_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_grate}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_GRATE = create("waxed_oxidized_copper_grate");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_lantern}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_LANTERN = create("waxed_oxidized_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_COPPER_TRAPDOOR = create("waxed_oxidized_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_CUT_COPPER = create("waxed_oxidized_cut_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_CUT_COPPER_SLAB = create("waxed_oxidized_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_CUT_COPPER_STAIRS = create("waxed_oxidized_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_oxidized_lightning_rod}.
     */
    public static final TypedKey<BlockType> WAXED_OXIDIZED_LIGHTNING_ROD = create("waxed_oxidized_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_weathered_chiseled_copper}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_CHISELED_COPPER = create("waxed_weathered_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_copper}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER = create("waxed_weathered_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_bars}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_BARS = create("waxed_weathered_copper_bars");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_bulb}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_BULB = create("waxed_weathered_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_chain}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_CHAIN = create("waxed_weathered_copper_chain");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_chest}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_CHEST = create("waxed_weathered_copper_chest");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_door}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_DOOR = create("waxed_weathered_copper_door");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_GOLEM_STATUE = create("waxed_weathered_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_grate}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_GRATE = create("waxed_weathered_copper_grate");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_lantern}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_LANTERN = create("waxed_weathered_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_COPPER_TRAPDOOR = create("waxed_weathered_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_CUT_COPPER = create("waxed_weathered_cut_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_CUT_COPPER_SLAB = create("waxed_weathered_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_CUT_COPPER_STAIRS = create("waxed_weathered_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_weathered_lightning_rod}.
     */
    public static final TypedKey<BlockType> WAXED_WEATHERED_LIGHTNING_ROD = create("waxed_weathered_lightning_rod");

    /**
     * Key for {@code minecraft:weathered_chiseled_copper}.
     */
    public static final TypedKey<BlockType> WEATHERED_CHISELED_COPPER = create("weathered_chiseled_copper");

    /**
     * Key for {@code minecraft:weathered_copper}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER = create("weathered_copper");

    /**
     * Key for {@code minecraft:weathered_copper_bars}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_BARS = create("weathered_copper_bars");

    /**
     * Key for {@code minecraft:weathered_copper_bulb}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_BULB = create("weathered_copper_bulb");

    /**
     * Key for {@code minecraft:weathered_copper_chain}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_CHAIN = create("weathered_copper_chain");

    /**
     * Key for {@code minecraft:weathered_copper_chest}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_CHEST = create("weathered_copper_chest");

    /**
     * Key for {@code minecraft:weathered_copper_door}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_DOOR = create("weathered_copper_door");

    /**
     * Key for {@code minecraft:weathered_copper_golem_statue}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_GOLEM_STATUE = create("weathered_copper_golem_statue");

    /**
     * Key for {@code minecraft:weathered_copper_grate}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_GRATE = create("weathered_copper_grate");

    /**
     * Key for {@code minecraft:weathered_copper_lantern}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_LANTERN = create("weathered_copper_lantern");

    /**
     * Key for {@code minecraft:weathered_copper_trapdoor}.
     */
    public static final TypedKey<BlockType> WEATHERED_COPPER_TRAPDOOR = create("weathered_copper_trapdoor");

    /**
     * Key for {@code minecraft:weathered_cut_copper}.
     */
    public static final TypedKey<BlockType> WEATHERED_CUT_COPPER = create("weathered_cut_copper");

    /**
     * Key for {@code minecraft:weathered_cut_copper_slab}.
     */
    public static final TypedKey<BlockType> WEATHERED_CUT_COPPER_SLAB = create("weathered_cut_copper_slab");

    /**
     * Key for {@code minecraft:weathered_cut_copper_stairs}.
     */
    public static final TypedKey<BlockType> WEATHERED_CUT_COPPER_STAIRS = create("weathered_cut_copper_stairs");

    /**
     * Key for {@code minecraft:weathered_lightning_rod}.
     */
    public static final TypedKey<BlockType> WEATHERED_LIGHTNING_ROD = create("weathered_lightning_rod");

    /**
     * Key for {@code minecraft:weeping_vines}.
     */
    public static final TypedKey<BlockType> WEEPING_VINES = create("weeping_vines");

    /**
     * Key for {@code minecraft:weeping_vines_plant}.
     */
    public static final TypedKey<BlockType> WEEPING_VINES_PLANT = create("weeping_vines_plant");

    /**
     * Key for {@code minecraft:wet_sponge}.
     */
    public static final TypedKey<BlockType> WET_SPONGE = create("wet_sponge");

    /**
     * Key for {@code minecraft:wheat}.
     */
    public static final TypedKey<BlockType> WHEAT = create("wheat");

    /**
     * Key for {@code minecraft:white_banner}.
     */
    public static final TypedKey<BlockType> WHITE_BANNER = create("white_banner");

    /**
     * Key for {@code minecraft:white_bed}.
     */
    public static final TypedKey<BlockType> WHITE_BED = create("white_bed");

    /**
     * Key for {@code minecraft:white_candle}.
     */
    public static final TypedKey<BlockType> WHITE_CANDLE = create("white_candle");

    /**
     * Key for {@code minecraft:white_candle_cake}.
     */
    public static final TypedKey<BlockType> WHITE_CANDLE_CAKE = create("white_candle_cake");

    /**
     * Key for {@code minecraft:white_carpet}.
     */
    public static final TypedKey<BlockType> WHITE_CARPET = create("white_carpet");

    /**
     * Key for {@code minecraft:white_concrete}.
     */
    public static final TypedKey<BlockType> WHITE_CONCRETE = create("white_concrete");

    /**
     * Key for {@code minecraft:white_concrete_powder}.
     */
    public static final TypedKey<BlockType> WHITE_CONCRETE_POWDER = create("white_concrete_powder");

    /**
     * Key for {@code minecraft:white_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> WHITE_GLAZED_TERRACOTTA = create("white_glazed_terracotta");

    /**
     * Key for {@code minecraft:white_shulker_box}.
     */
    public static final TypedKey<BlockType> WHITE_SHULKER_BOX = create("white_shulker_box");

    /**
     * Key for {@code minecraft:white_stained_glass}.
     */
    public static final TypedKey<BlockType> WHITE_STAINED_GLASS = create("white_stained_glass");

    /**
     * Key for {@code minecraft:white_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> WHITE_STAINED_GLASS_PANE = create("white_stained_glass_pane");

    /**
     * Key for {@code minecraft:white_terracotta}.
     */
    public static final TypedKey<BlockType> WHITE_TERRACOTTA = create("white_terracotta");

    /**
     * Key for {@code minecraft:white_tulip}.
     */
    public static final TypedKey<BlockType> WHITE_TULIP = create("white_tulip");

    /**
     * Key for {@code minecraft:white_wall_banner}.
     */
    public static final TypedKey<BlockType> WHITE_WALL_BANNER = create("white_wall_banner");

    /**
     * Key for {@code minecraft:white_wool}.
     */
    public static final TypedKey<BlockType> WHITE_WOOL = create("white_wool");

    /**
     * Key for {@code minecraft:wildflowers}.
     */
    public static final TypedKey<BlockType> WILDFLOWERS = create("wildflowers");

    /**
     * Key for {@code minecraft:wither_rose}.
     */
    public static final TypedKey<BlockType> WITHER_ROSE = create("wither_rose");

    /**
     * Key for {@code minecraft:wither_skeleton_skull}.
     */
    public static final TypedKey<BlockType> WITHER_SKELETON_SKULL = create("wither_skeleton_skull");

    /**
     * Key for {@code minecraft:wither_skeleton_wall_skull}.
     */
    public static final TypedKey<BlockType> WITHER_SKELETON_WALL_SKULL = create("wither_skeleton_wall_skull");

    /**
     * Key for {@code minecraft:yellow_banner}.
     */
    public static final TypedKey<BlockType> YELLOW_BANNER = create("yellow_banner");

    /**
     * Key for {@code minecraft:yellow_bed}.
     */
    public static final TypedKey<BlockType> YELLOW_BED = create("yellow_bed");

    /**
     * Key for {@code minecraft:yellow_candle}.
     */
    public static final TypedKey<BlockType> YELLOW_CANDLE = create("yellow_candle");

    /**
     * Key for {@code minecraft:yellow_candle_cake}.
     */
    public static final TypedKey<BlockType> YELLOW_CANDLE_CAKE = create("yellow_candle_cake");

    /**
     * Key for {@code minecraft:yellow_carpet}.
     */
    public static final TypedKey<BlockType> YELLOW_CARPET = create("yellow_carpet");

    /**
     * Key for {@code minecraft:yellow_concrete}.
     */
    public static final TypedKey<BlockType> YELLOW_CONCRETE = create("yellow_concrete");

    /**
     * Key for {@code minecraft:yellow_concrete_powder}.
     */
    public static final TypedKey<BlockType> YELLOW_CONCRETE_POWDER = create("yellow_concrete_powder");

    /**
     * Key for {@code minecraft:yellow_glazed_terracotta}.
     */
    public static final TypedKey<BlockType> YELLOW_GLAZED_TERRACOTTA = create("yellow_glazed_terracotta");

    /**
     * Key for {@code minecraft:yellow_shulker_box}.
     */
    public static final TypedKey<BlockType> YELLOW_SHULKER_BOX = create("yellow_shulker_box");

    /**
     * Key for {@code minecraft:yellow_stained_glass}.
     */
    public static final TypedKey<BlockType> YELLOW_STAINED_GLASS = create("yellow_stained_glass");

    /**
     * Key for {@code minecraft:yellow_stained_glass_pane}.
     */
    public static final TypedKey<BlockType> YELLOW_STAINED_GLASS_PANE = create("yellow_stained_glass_pane");

    /**
     * Key for {@code minecraft:yellow_terracotta}.
     */
    public static final TypedKey<BlockType> YELLOW_TERRACOTTA = create("yellow_terracotta");

    /**
     * Key for {@code minecraft:yellow_wall_banner}.
     */
    public static final TypedKey<BlockType> YELLOW_WALL_BANNER = create("yellow_wall_banner");

    /**
     * Key for {@code minecraft:yellow_wool}.
     */
    public static final TypedKey<BlockType> YELLOW_WOOL = create("yellow_wool");

    /**
     * Key for {@code minecraft:zombie_head}.
     */
    public static final TypedKey<BlockType> ZOMBIE_HEAD = create("zombie_head");

    /**
     * Key for {@code minecraft:zombie_wall_head}.
     */
    public static final TypedKey<BlockType> ZOMBIE_WALL_HEAD = create("zombie_wall_head");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<BlockType>> VALUES = List.of(
        AIR,
        STONE,
        GRANITE,
        POLISHED_GRANITE,
        DIORITE,
        POLISHED_DIORITE,
        ANDESITE,
        POLISHED_ANDESITE,
        GRASS_BLOCK,
        DIRT,
        COARSE_DIRT,
        PODZOL,
        COBBLESTONE,
        OAK_PLANKS,
        SPRUCE_PLANKS,
        BIRCH_PLANKS,
        JUNGLE_PLANKS,
        ACACIA_PLANKS,
        CHERRY_PLANKS,
        DARK_OAK_PLANKS,
        PALE_OAK_WOOD,
        PALE_OAK_PLANKS,
        MANGROVE_PLANKS,
        BAMBOO_PLANKS,
        BAMBOO_MOSAIC,
        OAK_SAPLING,
        SPRUCE_SAPLING,
        BIRCH_SAPLING,
        JUNGLE_SAPLING,
        ACACIA_SAPLING,
        CHERRY_SAPLING,
        DARK_OAK_SAPLING,
        PALE_OAK_SAPLING,
        MANGROVE_PROPAGULE,
        BEDROCK,
        WATER,
        LAVA,
        SAND,
        SUSPICIOUS_SAND,
        RED_SAND,
        GRAVEL,
        SUSPICIOUS_GRAVEL,
        GOLD_ORE,
        DEEPSLATE_GOLD_ORE,
        IRON_ORE,
        DEEPSLATE_IRON_ORE,
        COAL_ORE,
        DEEPSLATE_COAL_ORE,
        NETHER_GOLD_ORE,
        OAK_LOG,
        SPRUCE_LOG,
        BIRCH_LOG,
        JUNGLE_LOG,
        ACACIA_LOG,
        CHERRY_LOG,
        DARK_OAK_LOG,
        PALE_OAK_LOG,
        MANGROVE_LOG,
        MANGROVE_ROOTS,
        MUDDY_MANGROVE_ROOTS,
        BAMBOO_BLOCK,
        STRIPPED_SPRUCE_LOG,
        STRIPPED_BIRCH_LOG,
        STRIPPED_JUNGLE_LOG,
        STRIPPED_ACACIA_LOG,
        STRIPPED_CHERRY_LOG,
        STRIPPED_DARK_OAK_LOG,
        STRIPPED_PALE_OAK_LOG,
        STRIPPED_OAK_LOG,
        STRIPPED_MANGROVE_LOG,
        STRIPPED_BAMBOO_BLOCK,
        OAK_WOOD,
        SPRUCE_WOOD,
        BIRCH_WOOD,
        JUNGLE_WOOD,
        ACACIA_WOOD,
        CHERRY_WOOD,
        DARK_OAK_WOOD,
        MANGROVE_WOOD,
        STRIPPED_OAK_WOOD,
        STRIPPED_SPRUCE_WOOD,
        STRIPPED_BIRCH_WOOD,
        STRIPPED_JUNGLE_WOOD,
        STRIPPED_ACACIA_WOOD,
        STRIPPED_CHERRY_WOOD,
        STRIPPED_DARK_OAK_WOOD,
        STRIPPED_PALE_OAK_WOOD,
        STRIPPED_MANGROVE_WOOD,
        OAK_LEAVES,
        SPRUCE_LEAVES,
        BIRCH_LEAVES,
        JUNGLE_LEAVES,
        ACACIA_LEAVES,
        CHERRY_LEAVES,
        DARK_OAK_LEAVES,
        PALE_OAK_LEAVES,
        MANGROVE_LEAVES,
        AZALEA_LEAVES,
        FLOWERING_AZALEA_LEAVES,
        SPONGE,
        WET_SPONGE,
        GLASS,
        LAPIS_ORE,
        DEEPSLATE_LAPIS_ORE,
        LAPIS_BLOCK,
        DISPENSER,
        SANDSTONE,
        CHISELED_SANDSTONE,
        CUT_SANDSTONE,
        NOTE_BLOCK,
        WHITE_BED,
        ORANGE_BED,
        MAGENTA_BED,
        LIGHT_BLUE_BED,
        YELLOW_BED,
        LIME_BED,
        PINK_BED,
        GRAY_BED,
        LIGHT_GRAY_BED,
        CYAN_BED,
        PURPLE_BED,
        BLUE_BED,
        BROWN_BED,
        GREEN_BED,
        RED_BED,
        BLACK_BED,
        POWERED_RAIL,
        DETECTOR_RAIL,
        STICKY_PISTON,
        COBWEB,
        SHORT_GRASS,
        FERN,
        DEAD_BUSH,
        BUSH,
        SHORT_DRY_GRASS,
        TALL_DRY_GRASS,
        SEAGRASS,
        TALL_SEAGRASS,
        PISTON,
        PISTON_HEAD,
        WHITE_WOOL,
        ORANGE_WOOL,
        MAGENTA_WOOL,
        LIGHT_BLUE_WOOL,
        YELLOW_WOOL,
        LIME_WOOL,
        PINK_WOOL,
        GRAY_WOOL,
        LIGHT_GRAY_WOOL,
        CYAN_WOOL,
        PURPLE_WOOL,
        BLUE_WOOL,
        BROWN_WOOL,
        GREEN_WOOL,
        RED_WOOL,
        BLACK_WOOL,
        MOVING_PISTON,
        DANDELION,
        GOLDEN_DANDELION,
        TORCHFLOWER,
        POPPY,
        BLUE_ORCHID,
        ALLIUM,
        AZURE_BLUET,
        RED_TULIP,
        ORANGE_TULIP,
        WHITE_TULIP,
        PINK_TULIP,
        OXEYE_DAISY,
        CORNFLOWER,
        WITHER_ROSE,
        LILY_OF_THE_VALLEY,
        BROWN_MUSHROOM,
        RED_MUSHROOM,
        GOLD_BLOCK,
        IRON_BLOCK,
        BRICKS,
        TNT,
        BOOKSHELF,
        CHISELED_BOOKSHELF,
        ACACIA_SHELF,
        BAMBOO_SHELF,
        BIRCH_SHELF,
        CHERRY_SHELF,
        CRIMSON_SHELF,
        DARK_OAK_SHELF,
        JUNGLE_SHELF,
        MANGROVE_SHELF,
        OAK_SHELF,
        PALE_OAK_SHELF,
        SPRUCE_SHELF,
        WARPED_SHELF,
        MOSSY_COBBLESTONE,
        OBSIDIAN,
        TORCH,
        WALL_TORCH,
        FIRE,
        SOUL_FIRE,
        SPAWNER,
        CREAKING_HEART,
        OAK_STAIRS,
        CHEST,
        REDSTONE_WIRE,
        DIAMOND_ORE,
        DEEPSLATE_DIAMOND_ORE,
        DIAMOND_BLOCK,
        CRAFTING_TABLE,
        WHEAT,
        FARMLAND,
        FURNACE,
        OAK_SIGN,
        SPRUCE_SIGN,
        BIRCH_SIGN,
        ACACIA_SIGN,
        CHERRY_SIGN,
        JUNGLE_SIGN,
        DARK_OAK_SIGN,
        PALE_OAK_SIGN,
        MANGROVE_SIGN,
        BAMBOO_SIGN,
        OAK_DOOR,
        LADDER,
        RAIL,
        COBBLESTONE_STAIRS,
        OAK_WALL_SIGN,
        SPRUCE_WALL_SIGN,
        BIRCH_WALL_SIGN,
        ACACIA_WALL_SIGN,
        CHERRY_WALL_SIGN,
        JUNGLE_WALL_SIGN,
        DARK_OAK_WALL_SIGN,
        PALE_OAK_WALL_SIGN,
        MANGROVE_WALL_SIGN,
        BAMBOO_WALL_SIGN,
        OAK_HANGING_SIGN,
        SPRUCE_HANGING_SIGN,
        BIRCH_HANGING_SIGN,
        ACACIA_HANGING_SIGN,
        CHERRY_HANGING_SIGN,
        JUNGLE_HANGING_SIGN,
        DARK_OAK_HANGING_SIGN,
        PALE_OAK_HANGING_SIGN,
        CRIMSON_HANGING_SIGN,
        WARPED_HANGING_SIGN,
        MANGROVE_HANGING_SIGN,
        BAMBOO_HANGING_SIGN,
        OAK_WALL_HANGING_SIGN,
        SPRUCE_WALL_HANGING_SIGN,
        BIRCH_WALL_HANGING_SIGN,
        ACACIA_WALL_HANGING_SIGN,
        CHERRY_WALL_HANGING_SIGN,
        JUNGLE_WALL_HANGING_SIGN,
        DARK_OAK_WALL_HANGING_SIGN,
        PALE_OAK_WALL_HANGING_SIGN,
        MANGROVE_WALL_HANGING_SIGN,
        CRIMSON_WALL_HANGING_SIGN,
        WARPED_WALL_HANGING_SIGN,
        BAMBOO_WALL_HANGING_SIGN,
        LEVER,
        STONE_PRESSURE_PLATE,
        IRON_DOOR,
        OAK_PRESSURE_PLATE,
        SPRUCE_PRESSURE_PLATE,
        BIRCH_PRESSURE_PLATE,
        JUNGLE_PRESSURE_PLATE,
        ACACIA_PRESSURE_PLATE,
        CHERRY_PRESSURE_PLATE,
        DARK_OAK_PRESSURE_PLATE,
        PALE_OAK_PRESSURE_PLATE,
        MANGROVE_PRESSURE_PLATE,
        BAMBOO_PRESSURE_PLATE,
        REDSTONE_ORE,
        DEEPSLATE_REDSTONE_ORE,
        REDSTONE_TORCH,
        REDSTONE_WALL_TORCH,
        STONE_BUTTON,
        SNOW,
        ICE,
        SNOW_BLOCK,
        CACTUS,
        CACTUS_FLOWER,
        CLAY,
        SUGAR_CANE,
        JUKEBOX,
        OAK_FENCE,
        NETHERRACK,
        SOUL_SAND,
        SOUL_SOIL,
        BASALT,
        POLISHED_BASALT,
        SOUL_TORCH,
        SOUL_WALL_TORCH,
        COPPER_TORCH,
        COPPER_WALL_TORCH,
        GLOWSTONE,
        NETHER_PORTAL,
        CARVED_PUMPKIN,
        JACK_O_LANTERN,
        CAKE,
        REPEATER,
        WHITE_STAINED_GLASS,
        ORANGE_STAINED_GLASS,
        MAGENTA_STAINED_GLASS,
        LIGHT_BLUE_STAINED_GLASS,
        YELLOW_STAINED_GLASS,
        LIME_STAINED_GLASS,
        PINK_STAINED_GLASS,
        GRAY_STAINED_GLASS,
        LIGHT_GRAY_STAINED_GLASS,
        CYAN_STAINED_GLASS,
        PURPLE_STAINED_GLASS,
        BLUE_STAINED_GLASS,
        BROWN_STAINED_GLASS,
        GREEN_STAINED_GLASS,
        RED_STAINED_GLASS,
        BLACK_STAINED_GLASS,
        OAK_TRAPDOOR,
        SPRUCE_TRAPDOOR,
        BIRCH_TRAPDOOR,
        JUNGLE_TRAPDOOR,
        ACACIA_TRAPDOOR,
        CHERRY_TRAPDOOR,
        DARK_OAK_TRAPDOOR,
        PALE_OAK_TRAPDOOR,
        MANGROVE_TRAPDOOR,
        BAMBOO_TRAPDOOR,
        STONE_BRICKS,
        MOSSY_STONE_BRICKS,
        CRACKED_STONE_BRICKS,
        CHISELED_STONE_BRICKS,
        PACKED_MUD,
        MUD_BRICKS,
        INFESTED_STONE,
        INFESTED_COBBLESTONE,
        INFESTED_STONE_BRICKS,
        INFESTED_MOSSY_STONE_BRICKS,
        INFESTED_CRACKED_STONE_BRICKS,
        INFESTED_CHISELED_STONE_BRICKS,
        BROWN_MUSHROOM_BLOCK,
        RED_MUSHROOM_BLOCK,
        MUSHROOM_STEM,
        IRON_BARS,
        COPPER_BARS,
        EXPOSED_COPPER_BARS,
        WEATHERED_COPPER_BARS,
        OXIDIZED_COPPER_BARS,
        WAXED_COPPER_BARS,
        WAXED_EXPOSED_COPPER_BARS,
        WAXED_WEATHERED_COPPER_BARS,
        WAXED_OXIDIZED_COPPER_BARS,
        IRON_CHAIN,
        COPPER_CHAIN,
        EXPOSED_COPPER_CHAIN,
        WEATHERED_COPPER_CHAIN,
        OXIDIZED_COPPER_CHAIN,
        WAXED_COPPER_CHAIN,
        WAXED_EXPOSED_COPPER_CHAIN,
        WAXED_WEATHERED_COPPER_CHAIN,
        WAXED_OXIDIZED_COPPER_CHAIN,
        GLASS_PANE,
        PUMPKIN,
        MELON,
        ATTACHED_PUMPKIN_STEM,
        ATTACHED_MELON_STEM,
        PUMPKIN_STEM,
        MELON_STEM,
        VINE,
        GLOW_LICHEN,
        RESIN_CLUMP,
        OAK_FENCE_GATE,
        BRICK_STAIRS,
        STONE_BRICK_STAIRS,
        MUD_BRICK_STAIRS,
        MYCELIUM,
        LILY_PAD,
        RESIN_BLOCK,
        RESIN_BRICKS,
        RESIN_BRICK_STAIRS,
        RESIN_BRICK_SLAB,
        RESIN_BRICK_WALL,
        CHISELED_RESIN_BRICKS,
        NETHER_BRICKS,
        NETHER_BRICK_FENCE,
        NETHER_BRICK_STAIRS,
        NETHER_WART,
        ENCHANTING_TABLE,
        BREWING_STAND,
        CAULDRON,
        WATER_CAULDRON,
        LAVA_CAULDRON,
        POWDER_SNOW_CAULDRON,
        END_PORTAL,
        END_PORTAL_FRAME,
        END_STONE,
        DRAGON_EGG,
        REDSTONE_LAMP,
        COCOA,
        SANDSTONE_STAIRS,
        EMERALD_ORE,
        DEEPSLATE_EMERALD_ORE,
        ENDER_CHEST,
        TRIPWIRE_HOOK,
        TRIPWIRE,
        EMERALD_BLOCK,
        SPRUCE_STAIRS,
        BIRCH_STAIRS,
        JUNGLE_STAIRS,
        COMMAND_BLOCK,
        BEACON,
        COBBLESTONE_WALL,
        MOSSY_COBBLESTONE_WALL,
        FLOWER_POT,
        POTTED_TORCHFLOWER,
        POTTED_OAK_SAPLING,
        POTTED_SPRUCE_SAPLING,
        POTTED_BIRCH_SAPLING,
        POTTED_JUNGLE_SAPLING,
        POTTED_ACACIA_SAPLING,
        POTTED_CHERRY_SAPLING,
        POTTED_DARK_OAK_SAPLING,
        POTTED_PALE_OAK_SAPLING,
        POTTED_MANGROVE_PROPAGULE,
        POTTED_FERN,
        POTTED_DANDELION,
        POTTED_GOLDEN_DANDELION,
        POTTED_POPPY,
        POTTED_BLUE_ORCHID,
        POTTED_ALLIUM,
        POTTED_AZURE_BLUET,
        POTTED_RED_TULIP,
        POTTED_ORANGE_TULIP,
        POTTED_WHITE_TULIP,
        POTTED_PINK_TULIP,
        POTTED_OXEYE_DAISY,
        POTTED_CORNFLOWER,
        POTTED_LILY_OF_THE_VALLEY,
        POTTED_WITHER_ROSE,
        POTTED_RED_MUSHROOM,
        POTTED_BROWN_MUSHROOM,
        POTTED_DEAD_BUSH,
        POTTED_CACTUS,
        CARROTS,
        POTATOES,
        OAK_BUTTON,
        SPRUCE_BUTTON,
        BIRCH_BUTTON,
        JUNGLE_BUTTON,
        ACACIA_BUTTON,
        CHERRY_BUTTON,
        DARK_OAK_BUTTON,
        PALE_OAK_BUTTON,
        MANGROVE_BUTTON,
        BAMBOO_BUTTON,
        SKELETON_SKULL,
        SKELETON_WALL_SKULL,
        WITHER_SKELETON_SKULL,
        WITHER_SKELETON_WALL_SKULL,
        ZOMBIE_HEAD,
        ZOMBIE_WALL_HEAD,
        PLAYER_HEAD,
        PLAYER_WALL_HEAD,
        CREEPER_HEAD,
        CREEPER_WALL_HEAD,
        DRAGON_HEAD,
        DRAGON_WALL_HEAD,
        PIGLIN_HEAD,
        PIGLIN_WALL_HEAD,
        ANVIL,
        CHIPPED_ANVIL,
        DAMAGED_ANVIL,
        TRAPPED_CHEST,
        LIGHT_WEIGHTED_PRESSURE_PLATE,
        HEAVY_WEIGHTED_PRESSURE_PLATE,
        COMPARATOR,
        DAYLIGHT_DETECTOR,
        REDSTONE_BLOCK,
        NETHER_QUARTZ_ORE,
        HOPPER,
        QUARTZ_BLOCK,
        CHISELED_QUARTZ_BLOCK,
        QUARTZ_PILLAR,
        QUARTZ_STAIRS,
        ACTIVATOR_RAIL,
        DROPPER,
        WHITE_TERRACOTTA,
        ORANGE_TERRACOTTA,
        MAGENTA_TERRACOTTA,
        LIGHT_BLUE_TERRACOTTA,
        YELLOW_TERRACOTTA,
        LIME_TERRACOTTA,
        PINK_TERRACOTTA,
        GRAY_TERRACOTTA,
        LIGHT_GRAY_TERRACOTTA,
        CYAN_TERRACOTTA,
        PURPLE_TERRACOTTA,
        BLUE_TERRACOTTA,
        BROWN_TERRACOTTA,
        GREEN_TERRACOTTA,
        RED_TERRACOTTA,
        BLACK_TERRACOTTA,
        WHITE_STAINED_GLASS_PANE,
        ORANGE_STAINED_GLASS_PANE,
        MAGENTA_STAINED_GLASS_PANE,
        LIGHT_BLUE_STAINED_GLASS_PANE,
        YELLOW_STAINED_GLASS_PANE,
        LIME_STAINED_GLASS_PANE,
        PINK_STAINED_GLASS_PANE,
        GRAY_STAINED_GLASS_PANE,
        LIGHT_GRAY_STAINED_GLASS_PANE,
        CYAN_STAINED_GLASS_PANE,
        PURPLE_STAINED_GLASS_PANE,
        BLUE_STAINED_GLASS_PANE,
        BROWN_STAINED_GLASS_PANE,
        GREEN_STAINED_GLASS_PANE,
        RED_STAINED_GLASS_PANE,
        BLACK_STAINED_GLASS_PANE,
        ACACIA_STAIRS,
        CHERRY_STAIRS,
        DARK_OAK_STAIRS,
        PALE_OAK_STAIRS,
        MANGROVE_STAIRS,
        BAMBOO_STAIRS,
        BAMBOO_MOSAIC_STAIRS,
        SLIME_BLOCK,
        BARRIER,
        LIGHT,
        IRON_TRAPDOOR,
        PRISMARINE,
        PRISMARINE_BRICKS,
        DARK_PRISMARINE,
        PRISMARINE_STAIRS,
        PRISMARINE_BRICK_STAIRS,
        DARK_PRISMARINE_STAIRS,
        PRISMARINE_SLAB,
        PRISMARINE_BRICK_SLAB,
        DARK_PRISMARINE_SLAB,
        SEA_LANTERN,
        HAY_BLOCK,
        WHITE_CARPET,
        ORANGE_CARPET,
        MAGENTA_CARPET,
        LIGHT_BLUE_CARPET,
        YELLOW_CARPET,
        LIME_CARPET,
        PINK_CARPET,
        GRAY_CARPET,
        LIGHT_GRAY_CARPET,
        CYAN_CARPET,
        PURPLE_CARPET,
        BLUE_CARPET,
        BROWN_CARPET,
        GREEN_CARPET,
        RED_CARPET,
        BLACK_CARPET,
        TERRACOTTA,
        COAL_BLOCK,
        PACKED_ICE,
        SUNFLOWER,
        LILAC,
        ROSE_BUSH,
        PEONY,
        TALL_GRASS,
        LARGE_FERN,
        WHITE_BANNER,
        ORANGE_BANNER,
        MAGENTA_BANNER,
        LIGHT_BLUE_BANNER,
        YELLOW_BANNER,
        LIME_BANNER,
        PINK_BANNER,
        GRAY_BANNER,
        LIGHT_GRAY_BANNER,
        CYAN_BANNER,
        PURPLE_BANNER,
        BLUE_BANNER,
        BROWN_BANNER,
        GREEN_BANNER,
        RED_BANNER,
        BLACK_BANNER,
        WHITE_WALL_BANNER,
        ORANGE_WALL_BANNER,
        MAGENTA_WALL_BANNER,
        LIGHT_BLUE_WALL_BANNER,
        YELLOW_WALL_BANNER,
        LIME_WALL_BANNER,
        PINK_WALL_BANNER,
        GRAY_WALL_BANNER,
        LIGHT_GRAY_WALL_BANNER,
        CYAN_WALL_BANNER,
        PURPLE_WALL_BANNER,
        BLUE_WALL_BANNER,
        BROWN_WALL_BANNER,
        GREEN_WALL_BANNER,
        RED_WALL_BANNER,
        BLACK_WALL_BANNER,
        RED_SANDSTONE,
        CHISELED_RED_SANDSTONE,
        CUT_RED_SANDSTONE,
        RED_SANDSTONE_STAIRS,
        OAK_SLAB,
        SPRUCE_SLAB,
        BIRCH_SLAB,
        JUNGLE_SLAB,
        ACACIA_SLAB,
        CHERRY_SLAB,
        DARK_OAK_SLAB,
        PALE_OAK_SLAB,
        MANGROVE_SLAB,
        BAMBOO_SLAB,
        BAMBOO_MOSAIC_SLAB,
        STONE_SLAB,
        SMOOTH_STONE_SLAB,
        SANDSTONE_SLAB,
        CUT_SANDSTONE_SLAB,
        PETRIFIED_OAK_SLAB,
        COBBLESTONE_SLAB,
        BRICK_SLAB,
        STONE_BRICK_SLAB,
        MUD_BRICK_SLAB,
        NETHER_BRICK_SLAB,
        QUARTZ_SLAB,
        RED_SANDSTONE_SLAB,
        CUT_RED_SANDSTONE_SLAB,
        PURPUR_SLAB,
        SMOOTH_STONE,
        SMOOTH_SANDSTONE,
        SMOOTH_QUARTZ,
        SMOOTH_RED_SANDSTONE,
        SPRUCE_FENCE_GATE,
        BIRCH_FENCE_GATE,
        JUNGLE_FENCE_GATE,
        ACACIA_FENCE_GATE,
        CHERRY_FENCE_GATE,
        DARK_OAK_FENCE_GATE,
        PALE_OAK_FENCE_GATE,
        MANGROVE_FENCE_GATE,
        BAMBOO_FENCE_GATE,
        SPRUCE_FENCE,
        BIRCH_FENCE,
        JUNGLE_FENCE,
        ACACIA_FENCE,
        CHERRY_FENCE,
        DARK_OAK_FENCE,
        PALE_OAK_FENCE,
        MANGROVE_FENCE,
        BAMBOO_FENCE,
        SPRUCE_DOOR,
        BIRCH_DOOR,
        JUNGLE_DOOR,
        ACACIA_DOOR,
        CHERRY_DOOR,
        DARK_OAK_DOOR,
        PALE_OAK_DOOR,
        MANGROVE_DOOR,
        BAMBOO_DOOR,
        END_ROD,
        CHORUS_PLANT,
        CHORUS_FLOWER,
        PURPUR_BLOCK,
        PURPUR_PILLAR,
        PURPUR_STAIRS,
        END_STONE_BRICKS,
        TORCHFLOWER_CROP,
        PITCHER_CROP,
        PITCHER_PLANT,
        BEETROOTS,
        DIRT_PATH,
        END_GATEWAY,
        REPEATING_COMMAND_BLOCK,
        CHAIN_COMMAND_BLOCK,
        FROSTED_ICE,
        MAGMA_BLOCK,
        NETHER_WART_BLOCK,
        RED_NETHER_BRICKS,
        BONE_BLOCK,
        STRUCTURE_VOID,
        OBSERVER,
        SHULKER_BOX,
        WHITE_SHULKER_BOX,
        ORANGE_SHULKER_BOX,
        MAGENTA_SHULKER_BOX,
        LIGHT_BLUE_SHULKER_BOX,
        YELLOW_SHULKER_BOX,
        LIME_SHULKER_BOX,
        PINK_SHULKER_BOX,
        GRAY_SHULKER_BOX,
        LIGHT_GRAY_SHULKER_BOX,
        CYAN_SHULKER_BOX,
        PURPLE_SHULKER_BOX,
        BLUE_SHULKER_BOX,
        BROWN_SHULKER_BOX,
        GREEN_SHULKER_BOX,
        RED_SHULKER_BOX,
        BLACK_SHULKER_BOX,
        WHITE_GLAZED_TERRACOTTA,
        ORANGE_GLAZED_TERRACOTTA,
        MAGENTA_GLAZED_TERRACOTTA,
        LIGHT_BLUE_GLAZED_TERRACOTTA,
        YELLOW_GLAZED_TERRACOTTA,
        LIME_GLAZED_TERRACOTTA,
        PINK_GLAZED_TERRACOTTA,
        GRAY_GLAZED_TERRACOTTA,
        LIGHT_GRAY_GLAZED_TERRACOTTA,
        CYAN_GLAZED_TERRACOTTA,
        PURPLE_GLAZED_TERRACOTTA,
        BLUE_GLAZED_TERRACOTTA,
        BROWN_GLAZED_TERRACOTTA,
        GREEN_GLAZED_TERRACOTTA,
        RED_GLAZED_TERRACOTTA,
        BLACK_GLAZED_TERRACOTTA,
        WHITE_CONCRETE,
        ORANGE_CONCRETE,
        MAGENTA_CONCRETE,
        LIGHT_BLUE_CONCRETE,
        YELLOW_CONCRETE,
        LIME_CONCRETE,
        PINK_CONCRETE,
        GRAY_CONCRETE,
        LIGHT_GRAY_CONCRETE,
        CYAN_CONCRETE,
        PURPLE_CONCRETE,
        BLUE_CONCRETE,
        BROWN_CONCRETE,
        GREEN_CONCRETE,
        RED_CONCRETE,
        BLACK_CONCRETE,
        WHITE_CONCRETE_POWDER,
        ORANGE_CONCRETE_POWDER,
        MAGENTA_CONCRETE_POWDER,
        LIGHT_BLUE_CONCRETE_POWDER,
        YELLOW_CONCRETE_POWDER,
        LIME_CONCRETE_POWDER,
        PINK_CONCRETE_POWDER,
        GRAY_CONCRETE_POWDER,
        LIGHT_GRAY_CONCRETE_POWDER,
        CYAN_CONCRETE_POWDER,
        PURPLE_CONCRETE_POWDER,
        BLUE_CONCRETE_POWDER,
        BROWN_CONCRETE_POWDER,
        GREEN_CONCRETE_POWDER,
        RED_CONCRETE_POWDER,
        BLACK_CONCRETE_POWDER,
        KELP,
        KELP_PLANT,
        DRIED_KELP_BLOCK,
        TURTLE_EGG,
        SNIFFER_EGG,
        DRIED_GHAST,
        DEAD_TUBE_CORAL_BLOCK,
        DEAD_BRAIN_CORAL_BLOCK,
        DEAD_BUBBLE_CORAL_BLOCK,
        DEAD_FIRE_CORAL_BLOCK,
        DEAD_HORN_CORAL_BLOCK,
        TUBE_CORAL_BLOCK,
        BRAIN_CORAL_BLOCK,
        BUBBLE_CORAL_BLOCK,
        FIRE_CORAL_BLOCK,
        HORN_CORAL_BLOCK,
        DEAD_TUBE_CORAL,
        DEAD_BRAIN_CORAL,
        DEAD_BUBBLE_CORAL,
        DEAD_FIRE_CORAL,
        DEAD_HORN_CORAL,
        TUBE_CORAL,
        BRAIN_CORAL,
        BUBBLE_CORAL,
        FIRE_CORAL,
        HORN_CORAL,
        DEAD_TUBE_CORAL_FAN,
        DEAD_BRAIN_CORAL_FAN,
        DEAD_BUBBLE_CORAL_FAN,
        DEAD_FIRE_CORAL_FAN,
        DEAD_HORN_CORAL_FAN,
        TUBE_CORAL_FAN,
        BRAIN_CORAL_FAN,
        BUBBLE_CORAL_FAN,
        FIRE_CORAL_FAN,
        HORN_CORAL_FAN,
        DEAD_TUBE_CORAL_WALL_FAN,
        DEAD_BRAIN_CORAL_WALL_FAN,
        DEAD_BUBBLE_CORAL_WALL_FAN,
        DEAD_FIRE_CORAL_WALL_FAN,
        DEAD_HORN_CORAL_WALL_FAN,
        TUBE_CORAL_WALL_FAN,
        BRAIN_CORAL_WALL_FAN,
        BUBBLE_CORAL_WALL_FAN,
        FIRE_CORAL_WALL_FAN,
        HORN_CORAL_WALL_FAN,
        SEA_PICKLE,
        BLUE_ICE,
        CONDUIT,
        BAMBOO_SAPLING,
        BAMBOO,
        POTTED_BAMBOO,
        VOID_AIR,
        CAVE_AIR,
        BUBBLE_COLUMN,
        POLISHED_GRANITE_STAIRS,
        SMOOTH_RED_SANDSTONE_STAIRS,
        MOSSY_STONE_BRICK_STAIRS,
        POLISHED_DIORITE_STAIRS,
        MOSSY_COBBLESTONE_STAIRS,
        END_STONE_BRICK_STAIRS,
        STONE_STAIRS,
        SMOOTH_SANDSTONE_STAIRS,
        SMOOTH_QUARTZ_STAIRS,
        GRANITE_STAIRS,
        ANDESITE_STAIRS,
        RED_NETHER_BRICK_STAIRS,
        POLISHED_ANDESITE_STAIRS,
        DIORITE_STAIRS,
        POLISHED_GRANITE_SLAB,
        SMOOTH_RED_SANDSTONE_SLAB,
        MOSSY_STONE_BRICK_SLAB,
        POLISHED_DIORITE_SLAB,
        MOSSY_COBBLESTONE_SLAB,
        END_STONE_BRICK_SLAB,
        SMOOTH_SANDSTONE_SLAB,
        SMOOTH_QUARTZ_SLAB,
        GRANITE_SLAB,
        ANDESITE_SLAB,
        RED_NETHER_BRICK_SLAB,
        POLISHED_ANDESITE_SLAB,
        DIORITE_SLAB,
        BRICK_WALL,
        PRISMARINE_WALL,
        RED_SANDSTONE_WALL,
        MOSSY_STONE_BRICK_WALL,
        GRANITE_WALL,
        STONE_BRICK_WALL,
        MUD_BRICK_WALL,
        NETHER_BRICK_WALL,
        ANDESITE_WALL,
        RED_NETHER_BRICK_WALL,
        SANDSTONE_WALL,
        END_STONE_BRICK_WALL,
        DIORITE_WALL,
        SCAFFOLDING,
        LOOM,
        BARREL,
        SMOKER,
        BLAST_FURNACE,
        CARTOGRAPHY_TABLE,
        FLETCHING_TABLE,
        GRINDSTONE,
        LECTERN,
        SMITHING_TABLE,
        STONECUTTER,
        BELL,
        LANTERN,
        SOUL_LANTERN,
        COPPER_LANTERN,
        EXPOSED_COPPER_LANTERN,
        WEATHERED_COPPER_LANTERN,
        OXIDIZED_COPPER_LANTERN,
        WAXED_COPPER_LANTERN,
        WAXED_EXPOSED_COPPER_LANTERN,
        WAXED_WEATHERED_COPPER_LANTERN,
        WAXED_OXIDIZED_COPPER_LANTERN,
        CAMPFIRE,
        SOUL_CAMPFIRE,
        SWEET_BERRY_BUSH,
        WARPED_STEM,
        STRIPPED_WARPED_STEM,
        WARPED_HYPHAE,
        STRIPPED_WARPED_HYPHAE,
        WARPED_NYLIUM,
        WARPED_FUNGUS,
        WARPED_WART_BLOCK,
        WARPED_ROOTS,
        NETHER_SPROUTS,
        CRIMSON_STEM,
        STRIPPED_CRIMSON_STEM,
        CRIMSON_HYPHAE,
        STRIPPED_CRIMSON_HYPHAE,
        CRIMSON_NYLIUM,
        CRIMSON_FUNGUS,
        SHROOMLIGHT,
        WEEPING_VINES,
        WEEPING_VINES_PLANT,
        TWISTING_VINES,
        TWISTING_VINES_PLANT,
        CRIMSON_ROOTS,
        CRIMSON_PLANKS,
        WARPED_PLANKS,
        CRIMSON_SLAB,
        WARPED_SLAB,
        CRIMSON_PRESSURE_PLATE,
        WARPED_PRESSURE_PLATE,
        CRIMSON_FENCE,
        WARPED_FENCE,
        CRIMSON_TRAPDOOR,
        WARPED_TRAPDOOR,
        CRIMSON_FENCE_GATE,
        WARPED_FENCE_GATE,
        CRIMSON_STAIRS,
        WARPED_STAIRS,
        CRIMSON_BUTTON,
        WARPED_BUTTON,
        CRIMSON_DOOR,
        WARPED_DOOR,
        CRIMSON_SIGN,
        WARPED_SIGN,
        CRIMSON_WALL_SIGN,
        WARPED_WALL_SIGN,
        STRUCTURE_BLOCK,
        JIGSAW,
        TEST_BLOCK,
        TEST_INSTANCE_BLOCK,
        COMPOSTER,
        TARGET,
        BEE_NEST,
        BEEHIVE,
        HONEY_BLOCK,
        HONEYCOMB_BLOCK,
        NETHERITE_BLOCK,
        ANCIENT_DEBRIS,
        CRYING_OBSIDIAN,
        RESPAWN_ANCHOR,
        POTTED_CRIMSON_FUNGUS,
        POTTED_WARPED_FUNGUS,
        POTTED_CRIMSON_ROOTS,
        POTTED_WARPED_ROOTS,
        LODESTONE,
        BLACKSTONE,
        BLACKSTONE_STAIRS,
        BLACKSTONE_WALL,
        BLACKSTONE_SLAB,
        POLISHED_BLACKSTONE,
        POLISHED_BLACKSTONE_BRICKS,
        CRACKED_POLISHED_BLACKSTONE_BRICKS,
        CHISELED_POLISHED_BLACKSTONE,
        POLISHED_BLACKSTONE_BRICK_SLAB,
        POLISHED_BLACKSTONE_BRICK_STAIRS,
        POLISHED_BLACKSTONE_BRICK_WALL,
        GILDED_BLACKSTONE,
        POLISHED_BLACKSTONE_STAIRS,
        POLISHED_BLACKSTONE_SLAB,
        POLISHED_BLACKSTONE_PRESSURE_PLATE,
        POLISHED_BLACKSTONE_BUTTON,
        POLISHED_BLACKSTONE_WALL,
        CHISELED_NETHER_BRICKS,
        CRACKED_NETHER_BRICKS,
        QUARTZ_BRICKS,
        CANDLE,
        WHITE_CANDLE,
        ORANGE_CANDLE,
        MAGENTA_CANDLE,
        LIGHT_BLUE_CANDLE,
        YELLOW_CANDLE,
        LIME_CANDLE,
        PINK_CANDLE,
        GRAY_CANDLE,
        LIGHT_GRAY_CANDLE,
        CYAN_CANDLE,
        PURPLE_CANDLE,
        BLUE_CANDLE,
        BROWN_CANDLE,
        GREEN_CANDLE,
        RED_CANDLE,
        BLACK_CANDLE,
        CANDLE_CAKE,
        WHITE_CANDLE_CAKE,
        ORANGE_CANDLE_CAKE,
        MAGENTA_CANDLE_CAKE,
        LIGHT_BLUE_CANDLE_CAKE,
        YELLOW_CANDLE_CAKE,
        LIME_CANDLE_CAKE,
        PINK_CANDLE_CAKE,
        GRAY_CANDLE_CAKE,
        LIGHT_GRAY_CANDLE_CAKE,
        CYAN_CANDLE_CAKE,
        PURPLE_CANDLE_CAKE,
        BLUE_CANDLE_CAKE,
        BROWN_CANDLE_CAKE,
        GREEN_CANDLE_CAKE,
        RED_CANDLE_CAKE,
        BLACK_CANDLE_CAKE,
        AMETHYST_BLOCK,
        BUDDING_AMETHYST,
        AMETHYST_CLUSTER,
        LARGE_AMETHYST_BUD,
        MEDIUM_AMETHYST_BUD,
        SMALL_AMETHYST_BUD,
        TUFF,
        TUFF_SLAB,
        TUFF_STAIRS,
        TUFF_WALL,
        POLISHED_TUFF,
        POLISHED_TUFF_SLAB,
        POLISHED_TUFF_STAIRS,
        POLISHED_TUFF_WALL,
        CHISELED_TUFF,
        TUFF_BRICKS,
        TUFF_BRICK_SLAB,
        TUFF_BRICK_STAIRS,
        TUFF_BRICK_WALL,
        CHISELED_TUFF_BRICKS,
        SULFUR,
        POTENT_SULFUR,
        SULFUR_SLAB,
        SULFUR_STAIRS,
        SULFUR_WALL,
        POLISHED_SULFUR,
        POLISHED_SULFUR_SLAB,
        POLISHED_SULFUR_STAIRS,
        POLISHED_SULFUR_WALL,
        SULFUR_BRICKS,
        SULFUR_BRICK_SLAB,
        SULFUR_BRICK_STAIRS,
        SULFUR_BRICK_WALL,
        CHISELED_SULFUR,
        CINNABAR,
        CINNABAR_SLAB,
        CINNABAR_STAIRS,
        CINNABAR_WALL,
        POLISHED_CINNABAR,
        POLISHED_CINNABAR_SLAB,
        POLISHED_CINNABAR_STAIRS,
        POLISHED_CINNABAR_WALL,
        CINNABAR_BRICKS,
        CINNABAR_BRICK_SLAB,
        CINNABAR_BRICK_STAIRS,
        CINNABAR_BRICK_WALL,
        CHISELED_CINNABAR,
        CALCITE,
        TINTED_GLASS,
        POWDER_SNOW,
        SCULK_SENSOR,
        CALIBRATED_SCULK_SENSOR,
        SCULK,
        SCULK_VEIN,
        SCULK_CATALYST,
        SCULK_SHRIEKER,
        COPPER_BLOCK,
        EXPOSED_COPPER,
        WEATHERED_COPPER,
        OXIDIZED_COPPER,
        WAXED_COPPER_BLOCK,
        WAXED_EXPOSED_COPPER,
        WAXED_WEATHERED_COPPER,
        WAXED_OXIDIZED_COPPER,
        COPPER_ORE,
        DEEPSLATE_COPPER_ORE,
        CUT_COPPER,
        EXPOSED_CUT_COPPER,
        WEATHERED_CUT_COPPER,
        OXIDIZED_CUT_COPPER,
        WAXED_CUT_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_OXIDIZED_CUT_COPPER,
        CHISELED_COPPER,
        EXPOSED_CHISELED_COPPER,
        WEATHERED_CHISELED_COPPER,
        OXIDIZED_CHISELED_COPPER,
        WAXED_CHISELED_COPPER,
        WAXED_EXPOSED_CHISELED_COPPER,
        WAXED_WEATHERED_CHISELED_COPPER,
        WAXED_OXIDIZED_CHISELED_COPPER,
        CUT_COPPER_STAIRS,
        EXPOSED_CUT_COPPER_STAIRS,
        WEATHERED_CUT_COPPER_STAIRS,
        OXIDIZED_CUT_COPPER_STAIRS,
        WAXED_CUT_COPPER_STAIRS,
        WAXED_EXPOSED_CUT_COPPER_STAIRS,
        WAXED_WEATHERED_CUT_COPPER_STAIRS,
        WAXED_OXIDIZED_CUT_COPPER_STAIRS,
        CUT_COPPER_SLAB,
        EXPOSED_CUT_COPPER_SLAB,
        WEATHERED_CUT_COPPER_SLAB,
        OXIDIZED_CUT_COPPER_SLAB,
        WAXED_CUT_COPPER_SLAB,
        WAXED_EXPOSED_CUT_COPPER_SLAB,
        WAXED_WEATHERED_CUT_COPPER_SLAB,
        WAXED_OXIDIZED_CUT_COPPER_SLAB,
        COPPER_DOOR,
        EXPOSED_COPPER_DOOR,
        WEATHERED_COPPER_DOOR,
        OXIDIZED_COPPER_DOOR,
        WAXED_COPPER_DOOR,
        WAXED_EXPOSED_COPPER_DOOR,
        WAXED_WEATHERED_COPPER_DOOR,
        WAXED_OXIDIZED_COPPER_DOOR,
        COPPER_TRAPDOOR,
        EXPOSED_COPPER_TRAPDOOR,
        WEATHERED_COPPER_TRAPDOOR,
        OXIDIZED_COPPER_TRAPDOOR,
        WAXED_COPPER_TRAPDOOR,
        WAXED_EXPOSED_COPPER_TRAPDOOR,
        WAXED_WEATHERED_COPPER_TRAPDOOR,
        WAXED_OXIDIZED_COPPER_TRAPDOOR,
        COPPER_GRATE,
        EXPOSED_COPPER_GRATE,
        WEATHERED_COPPER_GRATE,
        OXIDIZED_COPPER_GRATE,
        WAXED_COPPER_GRATE,
        WAXED_EXPOSED_COPPER_GRATE,
        WAXED_WEATHERED_COPPER_GRATE,
        WAXED_OXIDIZED_COPPER_GRATE,
        COPPER_BULB,
        EXPOSED_COPPER_BULB,
        WEATHERED_COPPER_BULB,
        OXIDIZED_COPPER_BULB,
        WAXED_COPPER_BULB,
        WAXED_EXPOSED_COPPER_BULB,
        WAXED_WEATHERED_COPPER_BULB,
        WAXED_OXIDIZED_COPPER_BULB,
        COPPER_CHEST,
        EXPOSED_COPPER_CHEST,
        WEATHERED_COPPER_CHEST,
        OXIDIZED_COPPER_CHEST,
        WAXED_COPPER_CHEST,
        WAXED_EXPOSED_COPPER_CHEST,
        WAXED_WEATHERED_COPPER_CHEST,
        WAXED_OXIDIZED_COPPER_CHEST,
        COPPER_GOLEM_STATUE,
        EXPOSED_COPPER_GOLEM_STATUE,
        WEATHERED_COPPER_GOLEM_STATUE,
        OXIDIZED_COPPER_GOLEM_STATUE,
        WAXED_COPPER_GOLEM_STATUE,
        WAXED_EXPOSED_COPPER_GOLEM_STATUE,
        WAXED_WEATHERED_COPPER_GOLEM_STATUE,
        WAXED_OXIDIZED_COPPER_GOLEM_STATUE,
        LIGHTNING_ROD,
        EXPOSED_LIGHTNING_ROD,
        WEATHERED_LIGHTNING_ROD,
        OXIDIZED_LIGHTNING_ROD,
        WAXED_LIGHTNING_ROD,
        WAXED_EXPOSED_LIGHTNING_ROD,
        WAXED_WEATHERED_LIGHTNING_ROD,
        WAXED_OXIDIZED_LIGHTNING_ROD,
        DRIPSTONE_BLOCK,
        POINTED_DRIPSTONE,
        SULFUR_SPIKE,
        CAVE_VINES,
        CAVE_VINES_PLANT,
        SPORE_BLOSSOM,
        AZALEA,
        FLOWERING_AZALEA,
        MOSS_CARPET,
        PINK_PETALS,
        WILDFLOWERS,
        LEAF_LITTER,
        MOSS_BLOCK,
        BIG_DRIPLEAF,
        BIG_DRIPLEAF_STEM,
        SMALL_DRIPLEAF,
        HANGING_ROOTS,
        ROOTED_DIRT,
        MUD,
        DEEPSLATE,
        COBBLED_DEEPSLATE,
        COBBLED_DEEPSLATE_STAIRS,
        COBBLED_DEEPSLATE_SLAB,
        COBBLED_DEEPSLATE_WALL,
        POLISHED_DEEPSLATE,
        POLISHED_DEEPSLATE_STAIRS,
        POLISHED_DEEPSLATE_SLAB,
        POLISHED_DEEPSLATE_WALL,
        DEEPSLATE_TILES,
        DEEPSLATE_TILE_STAIRS,
        DEEPSLATE_TILE_SLAB,
        DEEPSLATE_TILE_WALL,
        DEEPSLATE_BRICKS,
        DEEPSLATE_BRICK_STAIRS,
        DEEPSLATE_BRICK_SLAB,
        DEEPSLATE_BRICK_WALL,
        CHISELED_DEEPSLATE,
        CRACKED_DEEPSLATE_BRICKS,
        CRACKED_DEEPSLATE_TILES,
        INFESTED_DEEPSLATE,
        SMOOTH_BASALT,
        RAW_IRON_BLOCK,
        RAW_COPPER_BLOCK,
        RAW_GOLD_BLOCK,
        POTTED_AZALEA_BUSH,
        POTTED_FLOWERING_AZALEA_BUSH,
        OCHRE_FROGLIGHT,
        VERDANT_FROGLIGHT,
        PEARLESCENT_FROGLIGHT,
        FROGSPAWN,
        REINFORCED_DEEPSLATE,
        DECORATED_POT,
        CRAFTER,
        TRIAL_SPAWNER,
        VAULT,
        HEAVY_CORE,
        PALE_MOSS_BLOCK,
        PALE_MOSS_CARPET,
        PALE_HANGING_MOSS,
        OPEN_EYEBLOSSOM,
        CLOSED_EYEBLOSSOM,
        POTTED_OPEN_EYEBLOSSOM,
        POTTED_CLOSED_EYEBLOSSOM,
        FIREFLY_BUSH
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("acacia_logs"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"))),
        Map.entry(Key.key("air"), List.of(Key.key("air"), Key.key("cave_air"), Key.key("void_air"))),
        Map.entry(Key.key("all_hanging_signs"), List.of(Key.key("acacia_hanging_sign"), Key.key("acacia_wall_hanging_sign"), Key.key("bamboo_hanging_sign"), Key.key("bamboo_wall_hanging_sign"), Key.key("birch_hanging_sign"), Key.key("birch_wall_hanging_sign"), Key.key("cherry_hanging_sign"), Key.key("cherry_wall_hanging_sign"), Key.key("crimson_hanging_sign"), Key.key("crimson_wall_hanging_sign"), Key.key("dark_oak_hanging_sign"), Key.key("dark_oak_wall_hanging_sign"), Key.key("jungle_hanging_sign"), Key.key("jungle_wall_hanging_sign"), Key.key("mangrove_hanging_sign"), Key.key("mangrove_wall_hanging_sign"), Key.key("oak_hanging_sign"), Key.key("oak_wall_hanging_sign"), Key.key("pale_oak_hanging_sign"), Key.key("pale_oak_wall_hanging_sign"), Key.key("spruce_hanging_sign"), Key.key("spruce_wall_hanging_sign"), Key.key("warped_hanging_sign"), Key.key("warped_wall_hanging_sign"))),
        Map.entry(Key.key("all_signs"), List.of(Key.key("acacia_hanging_sign"), Key.key("acacia_sign"), Key.key("acacia_wall_hanging_sign"), Key.key("acacia_wall_sign"), Key.key("bamboo_hanging_sign"), Key.key("bamboo_sign"), Key.key("bamboo_wall_hanging_sign"), Key.key("bamboo_wall_sign"), Key.key("birch_hanging_sign"), Key.key("birch_sign"), Key.key("birch_wall_hanging_sign"), Key.key("birch_wall_sign"), Key.key("cherry_hanging_sign"), Key.key("cherry_sign"), Key.key("cherry_wall_hanging_sign"), Key.key("cherry_wall_sign"), Key.key("crimson_hanging_sign"), Key.key("crimson_sign"), Key.key("crimson_wall_hanging_sign"), Key.key("crimson_wall_sign"), Key.key("dark_oak_hanging_sign"), Key.key("dark_oak_sign"), Key.key("dark_oak_wall_hanging_sign"), Key.key("dark_oak_wall_sign"), Key.key("jungle_hanging_sign"), Key.key("jungle_sign"), Key.key("jungle_wall_hanging_sign"), Key.key("jungle_wall_sign"), Key.key("mangrove_hanging_sign"), Key.key("mangrove_sign"), Key.key("mangrove_wall_hanging_sign"), Key.key("mangrove_wall_sign"), Key.key("oak_hanging_sign"), Key.key("oak_sign"), Key.key("oak_wall_hanging_sign"), Key.key("oak_wall_sign"), Key.key("pale_oak_hanging_sign"), Key.key("pale_oak_sign"), Key.key("pale_oak_wall_hanging_sign"), Key.key("pale_oak_wall_sign"), Key.key("spruce_hanging_sign"), Key.key("spruce_sign"), Key.key("spruce_wall_hanging_sign"), Key.key("spruce_wall_sign"), Key.key("warped_hanging_sign"), Key.key("warped_sign"), Key.key("warped_wall_hanging_sign"), Key.key("warped_wall_sign"))),
        Map.entry(Key.key("ancient_city_replaceable"), List.of(Key.key("cobbled_deepslate"), Key.key("cracked_deepslate_bricks"), Key.key("cracked_deepslate_tiles"), Key.key("deepslate"), Key.key("deepslate_brick_slab"), Key.key("deepslate_brick_stairs"), Key.key("deepslate_brick_wall"), Key.key("deepslate_bricks"), Key.key("deepslate_tile_slab"), Key.key("deepslate_tile_wall"), Key.key("deepslate_tiles"), Key.key("gray_wool"))),
        Map.entry(Key.key("animals_spawnable_on"), List.of(Key.key("grass_block"))),
        Map.entry(Key.key("anvil"), List.of(Key.key("anvil"), Key.key("chipped_anvil"), Key.key("damaged_anvil"))),
        Map.entry(Key.key("armadillo_spawnable_on"), List.of(Key.key("brown_terracotta"), Key.key("coarse_dirt"), Key.key("grass_block"), Key.key("light_gray_terracotta"), Key.key("orange_terracotta"), Key.key("red_sand"), Key.key("red_terracotta"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("axolotls_spawnable_on"), List.of(Key.key("clay"))),
        Map.entry(Key.key("azalea_grows_on"), List.of(Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("coarse_dirt"), Key.key("cyan_terracotta"), Key.key("dirt"), Key.key("grass_block"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("orange_terracotta"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("powder_snow"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("snow_block"), Key.key("suspicious_sand"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("azalea_root_replaceable"), List.of(Key.key("andesite"), Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("clay"), Key.key("coarse_dirt"), Key.key("cyan_terracotta"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("gravel"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("orange_terracotta"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("powder_snow"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("snow_block"), Key.key("stone"), Key.key("terracotta"), Key.key("tuff"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("badlands_terracotta"), List.of(Key.key("brown_terracotta"), Key.key("light_gray_terracotta"), Key.key("orange_terracotta"), Key.key("red_terracotta"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("bamboo_blocks"), List.of(Key.key("bamboo_block"), Key.key("stripped_bamboo_block"))),
        Map.entry(Key.key("banners"), List.of(Key.key("black_banner"), Key.key("black_wall_banner"), Key.key("blue_banner"), Key.key("blue_wall_banner"), Key.key("brown_banner"), Key.key("brown_wall_banner"), Key.key("cyan_banner"), Key.key("cyan_wall_banner"), Key.key("gray_banner"), Key.key("gray_wall_banner"), Key.key("green_banner"), Key.key("green_wall_banner"), Key.key("light_blue_banner"), Key.key("light_blue_wall_banner"), Key.key("light_gray_banner"), Key.key("light_gray_wall_banner"), Key.key("lime_banner"), Key.key("lime_wall_banner"), Key.key("magenta_banner"), Key.key("magenta_wall_banner"), Key.key("orange_banner"), Key.key("orange_wall_banner"), Key.key("pink_banner"), Key.key("pink_wall_banner"), Key.key("purple_banner"), Key.key("purple_wall_banner"), Key.key("red_banner"), Key.key("red_wall_banner"), Key.key("white_banner"), Key.key("white_wall_banner"), Key.key("yellow_banner"), Key.key("yellow_wall_banner"))),
        Map.entry(Key.key("bars"), List.of(Key.key("copper_bars"), Key.key("exposed_copper_bars"), Key.key("iron_bars"), Key.key("oxidized_copper_bars"), Key.key("waxed_copper_bars"), Key.key("waxed_exposed_copper_bars"), Key.key("waxed_oxidized_copper_bars"), Key.key("waxed_weathered_copper_bars"), Key.key("weathered_copper_bars"))),
        Map.entry(Key.key("base_stone_nether"), List.of(Key.key("basalt"), Key.key("blackstone"), Key.key("netherrack"))),
        Map.entry(Key.key("base_stone_overworld"), List.of(Key.key("andesite"), Key.key("deepslate"), Key.key("diorite"), Key.key("granite"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("bats_spawnable_on"), List.of(Key.key("andesite"), Key.key("deepslate"), Key.key("diorite"), Key.key("granite"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("beacon_base_blocks"), List.of(Key.key("diamond_block"), Key.key("emerald_block"), Key.key("gold_block"), Key.key("iron_block"), Key.key("netherite_block"))),
        Map.entry(Key.key("beds"), List.of(Key.key("black_bed"), Key.key("blue_bed"), Key.key("brown_bed"), Key.key("cyan_bed"), Key.key("gray_bed"), Key.key("green_bed"), Key.key("light_blue_bed"), Key.key("light_gray_bed"), Key.key("lime_bed"), Key.key("magenta_bed"), Key.key("orange_bed"), Key.key("pink_bed"), Key.key("purple_bed"), Key.key("red_bed"), Key.key("white_bed"), Key.key("yellow_bed"))),
        Map.entry(Key.key("bee_attractive"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("cactus_flower"), Key.key("cherry_leaves"), Key.key("chorus_flower"), Key.key("cornflower"), Key.key("dandelion"), Key.key("flowering_azalea"), Key.key("flowering_azalea_leaves"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_propagule"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("peony"), Key.key("pink_petals"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("spore_blossom"), Key.key("sunflower"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wildflowers"), Key.key("wither_rose"))),
        Map.entry(Key.key("bee_growables"), List.of(Key.key("beetroots"), Key.key("carrots"), Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("melon_stem"), Key.key("pitcher_crop"), Key.key("potatoes"), Key.key("pumpkin_stem"), Key.key("sweet_berry_bush"), Key.key("torchflower_crop"), Key.key("wheat"))),
        Map.entry(Key.key("beehives"), List.of(Key.key("bee_nest"), Key.key("beehive"))),
        Map.entry(Key.key("beneath_bamboo_podzol_replaceable"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("beneath_tree_podzol_replaceable"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("birch_logs"), List.of(Key.key("birch_log"), Key.key("birch_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"))),
        Map.entry(Key.key("blocks_wind_charge_explosions"), List.of(Key.key("barrier"), Key.key("bedrock"))),
        Map.entry(Key.key("buttons"), List.of(Key.key("acacia_button"), Key.key("bamboo_button"), Key.key("birch_button"), Key.key("cherry_button"), Key.key("crimson_button"), Key.key("dark_oak_button"), Key.key("jungle_button"), Key.key("mangrove_button"), Key.key("oak_button"), Key.key("pale_oak_button"), Key.key("polished_blackstone_button"), Key.key("spruce_button"), Key.key("stone_button"), Key.key("warped_button"))),
        Map.entry(Key.key("camel_sand_step_sound_blocks"), List.of(Key.key("black_concrete_powder"), Key.key("blue_concrete_powder"), Key.key("brown_concrete_powder"), Key.key("cyan_concrete_powder"), Key.key("gray_concrete_powder"), Key.key("green_concrete_powder"), Key.key("light_blue_concrete_powder"), Key.key("light_gray_concrete_powder"), Key.key("lime_concrete_powder"), Key.key("magenta_concrete_powder"), Key.key("orange_concrete_powder"), Key.key("pink_concrete_powder"), Key.key("purple_concrete_powder"), Key.key("red_concrete_powder"), Key.key("red_sand"), Key.key("sand"), Key.key("suspicious_sand"), Key.key("white_concrete_powder"), Key.key("yellow_concrete_powder"))),
        Map.entry(Key.key("camels_spawnable_on"), List.of(Key.key("red_sand"), Key.key("sand"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("campfires"), List.of(Key.key("campfire"), Key.key("soul_campfire"))),
        Map.entry(Key.key("can_glide_through"), List.of(Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("twisting_vines"), Key.key("twisting_vines_plant"), Key.key("vine"), Key.key("weeping_vines"), Key.key("weeping_vines_plant"))),
        Map.entry(Key.key("candle_cakes"), List.of(Key.key("black_candle_cake"), Key.key("blue_candle_cake"), Key.key("brown_candle_cake"), Key.key("candle_cake"), Key.key("cyan_candle_cake"), Key.key("gray_candle_cake"), Key.key("green_candle_cake"), Key.key("light_blue_candle_cake"), Key.key("light_gray_candle_cake"), Key.key("lime_candle_cake"), Key.key("magenta_candle_cake"), Key.key("orange_candle_cake"), Key.key("pink_candle_cake"), Key.key("purple_candle_cake"), Key.key("red_candle_cake"), Key.key("white_candle_cake"), Key.key("yellow_candle_cake"))),
        Map.entry(Key.key("candles"), List.of(Key.key("black_candle"), Key.key("blue_candle"), Key.key("brown_candle"), Key.key("candle"), Key.key("cyan_candle"), Key.key("gray_candle"), Key.key("green_candle"), Key.key("light_blue_candle"), Key.key("light_gray_candle"), Key.key("lime_candle"), Key.key("magenta_candle"), Key.key("orange_candle"), Key.key("pink_candle"), Key.key("purple_candle"), Key.key("red_candle"), Key.key("white_candle"), Key.key("yellow_candle"))),
        Map.entry(Key.key("cannot_replace_below_tree_trunk"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("cannot_support_kelp"), List.of(Key.key("magma_block"))),
        Map.entry(Key.key("cannot_support_seagrass"), List.of(Key.key("magma_block"))),
        Map.entry(Key.key("cannot_support_snow_layer"), List.of(Key.key("barrier"), Key.key("ice"), Key.key("packed_ice"))),
        Map.entry(Key.key("cauldrons"), List.of(Key.key("cauldron"), Key.key("lava_cauldron"), Key.key("powder_snow_cauldron"), Key.key("water_cauldron"))),
        Map.entry(Key.key("causes_continuous_geyser_eruptions"), List.of(Key.key("lava"))),
        Map.entry(Key.key("causes_periodic_geyser_eruptions"), List.of(Key.key("magma_block"))),
        Map.entry(Key.key("cave_vines"), List.of(Key.key("cave_vines"), Key.key("cave_vines_plant"))),
        Map.entry(Key.key("ceiling_hanging_signs"), List.of(Key.key("acacia_hanging_sign"), Key.key("bamboo_hanging_sign"), Key.key("birch_hanging_sign"), Key.key("cherry_hanging_sign"), Key.key("crimson_hanging_sign"), Key.key("dark_oak_hanging_sign"), Key.key("jungle_hanging_sign"), Key.key("mangrove_hanging_sign"), Key.key("oak_hanging_sign"), Key.key("pale_oak_hanging_sign"), Key.key("spruce_hanging_sign"), Key.key("warped_hanging_sign"))),
        Map.entry(Key.key("chains"), List.of(Key.key("copper_chain"), Key.key("exposed_copper_chain"), Key.key("iron_chain"), Key.key("oxidized_copper_chain"), Key.key("waxed_copper_chain"), Key.key("waxed_exposed_copper_chain"), Key.key("waxed_oxidized_copper_chain"), Key.key("waxed_weathered_copper_chain"), Key.key("weathered_copper_chain"))),
        Map.entry(Key.key("cherry_logs"), List.of(Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"))),
        Map.entry(Key.key("climbable"), List.of(Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("ladder"), Key.key("scaffolding"), Key.key("twisting_vines"), Key.key("twisting_vines_plant"), Key.key("vine"), Key.key("weeping_vines"), Key.key("weeping_vines_plant"))),
        Map.entry(Key.key("coal_ores"), List.of(Key.key("coal_ore"), Key.key("deepslate_coal_ore"))),
        Map.entry(Key.key("combination_step_sound_blocks"), List.of(Key.key("black_carpet"), Key.key("blue_carpet"), Key.key("brown_carpet"), Key.key("crimson_roots"), Key.key("cyan_carpet"), Key.key("gray_carpet"), Key.key("green_carpet"), Key.key("light_blue_carpet"), Key.key("light_gray_carpet"), Key.key("lime_carpet"), Key.key("magenta_carpet"), Key.key("moss_carpet"), Key.key("nether_sprouts"), Key.key("orange_carpet"), Key.key("pale_moss_carpet"), Key.key("pink_carpet"), Key.key("purple_carpet"), Key.key("red_carpet"), Key.key("resin_clump"), Key.key("snow"), Key.key("warped_roots"), Key.key("white_carpet"), Key.key("yellow_carpet"))),
        Map.entry(Key.key("completes_find_tree_tutorial"), List.of(Key.key("acacia_leaves"), Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_leaves"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_leaves"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_leaves"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("nether_wart_block"), Key.key("oak_leaves"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_leaves"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_leaves"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("concrete"), List.of(Key.key("black_concrete"), Key.key("blue_concrete"), Key.key("brown_concrete"), Key.key("cyan_concrete"), Key.key("gray_concrete"), Key.key("green_concrete"), Key.key("light_blue_concrete"), Key.key("light_gray_concrete"), Key.key("lime_concrete"), Key.key("magenta_concrete"), Key.key("orange_concrete"), Key.key("pink_concrete"), Key.key("purple_concrete"), Key.key("red_concrete"), Key.key("white_concrete"), Key.key("yellow_concrete"))),
        Map.entry(Key.key("concrete_powders"), List.of(Key.key("black_concrete_powder"), Key.key("blue_concrete_powder"), Key.key("brown_concrete_powder"), Key.key("cyan_concrete_powder"), Key.key("gray_concrete_powder"), Key.key("green_concrete_powder"), Key.key("light_blue_concrete_powder"), Key.key("light_gray_concrete_powder"), Key.key("lime_concrete_powder"), Key.key("magenta_concrete_powder"), Key.key("orange_concrete_powder"), Key.key("pink_concrete_powder"), Key.key("purple_concrete_powder"), Key.key("red_concrete_powder"), Key.key("white_concrete_powder"), Key.key("yellow_concrete_powder"))),
        Map.entry(Key.key("convertable_to_mud"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("copper"), List.of(Key.key("copper_block"), Key.key("exposed_copper"), Key.key("oxidized_copper"), Key.key("waxed_copper_block"), Key.key("waxed_exposed_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_weathered_copper"), Key.key("weathered_copper"))),
        Map.entry(Key.key("copper_chests"), List.of(Key.key("copper_chest"), Key.key("exposed_copper_chest"), Key.key("oxidized_copper_chest"), Key.key("waxed_copper_chest"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_weathered_copper_chest"), Key.key("weathered_copper_chest"))),
        Map.entry(Key.key("copper_golem_statues"), List.of(Key.key("copper_golem_statue"), Key.key("exposed_copper_golem_statue"), Key.key("oxidized_copper_golem_statue"), Key.key("waxed_copper_golem_statue"), Key.key("waxed_exposed_copper_golem_statue"), Key.key("waxed_oxidized_copper_golem_statue"), Key.key("waxed_weathered_copper_golem_statue"), Key.key("weathered_copper_golem_statue"))),
        Map.entry(Key.key("copper_ores"), List.of(Key.key("copper_ore"), Key.key("deepslate_copper_ore"))),
        Map.entry(Key.key("coral_blocks"), List.of(Key.key("brain_coral_block"), Key.key("bubble_coral_block"), Key.key("fire_coral_block"), Key.key("horn_coral_block"), Key.key("tube_coral_block"))),
        Map.entry(Key.key("coral_plants"), List.of(Key.key("brain_coral"), Key.key("bubble_coral"), Key.key("fire_coral"), Key.key("horn_coral"), Key.key("tube_coral"))),
        Map.entry(Key.key("corals"), List.of(Key.key("brain_coral"), Key.key("brain_coral_fan"), Key.key("bubble_coral"), Key.key("bubble_coral_fan"), Key.key("fire_coral"), Key.key("fire_coral_fan"), Key.key("horn_coral"), Key.key("horn_coral_fan"), Key.key("tube_coral"), Key.key("tube_coral_fan"))),
        Map.entry(Key.key("crimson_stems"), List.of(Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"))),
        Map.entry(Key.key("crops"), List.of(Key.key("beetroots"), Key.key("carrots"), Key.key("melon_stem"), Key.key("pitcher_crop"), Key.key("potatoes"), Key.key("pumpkin_stem"), Key.key("torchflower_crop"), Key.key("wheat"))),
        Map.entry(Key.key("crystal_sound_blocks"), List.of(Key.key("amethyst_block"), Key.key("budding_amethyst"))),
        Map.entry(Key.key("dampens_vibrations"), List.of(Key.key("black_carpet"), Key.key("black_wool"), Key.key("blue_carpet"), Key.key("blue_wool"), Key.key("brown_carpet"), Key.key("brown_wool"), Key.key("cyan_carpet"), Key.key("cyan_wool"), Key.key("gray_carpet"), Key.key("gray_wool"), Key.key("green_carpet"), Key.key("green_wool"), Key.key("light_blue_carpet"), Key.key("light_blue_wool"), Key.key("light_gray_carpet"), Key.key("light_gray_wool"), Key.key("lime_carpet"), Key.key("lime_wool"), Key.key("magenta_carpet"), Key.key("magenta_wool"), Key.key("orange_carpet"), Key.key("orange_wool"), Key.key("pink_carpet"), Key.key("pink_wool"), Key.key("purple_carpet"), Key.key("purple_wool"), Key.key("red_carpet"), Key.key("red_wool"), Key.key("white_carpet"), Key.key("white_wool"), Key.key("yellow_carpet"), Key.key("yellow_wool"))),
        Map.entry(Key.key("dark_oak_logs"), List.of(Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"))),
        Map.entry(Key.key("deepslate_ore_replaceables"), List.of(Key.key("deepslate"), Key.key("tuff"))),
        Map.entry(Key.key("default_immune_to"), List.of()),
        Map.entry(Key.key("diamond_ores"), List.of(Key.key("deepslate_diamond_ore"), Key.key("diamond_ore"))),
        Map.entry(Key.key("dirt"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("does_not_block_hoppers"), List.of(Key.key("bee_nest"), Key.key("beehive"))),
        Map.entry(Key.key("doors"), List.of(Key.key("acacia_door"), Key.key("bamboo_door"), Key.key("birch_door"), Key.key("cherry_door"), Key.key("copper_door"), Key.key("crimson_door"), Key.key("dark_oak_door"), Key.key("exposed_copper_door"), Key.key("iron_door"), Key.key("jungle_door"), Key.key("mangrove_door"), Key.key("oak_door"), Key.key("oxidized_copper_door"), Key.key("pale_oak_door"), Key.key("spruce_door"), Key.key("warped_door"), Key.key("waxed_copper_door"), Key.key("waxed_exposed_copper_door"), Key.key("waxed_oxidized_copper_door"), Key.key("waxed_weathered_copper_door"), Key.key("weathered_copper_door"))),
        Map.entry(Key.key("dragon_immune"), List.of(Key.key("barrier"), Key.key("bedrock"), Key.key("chain_command_block"), Key.key("command_block"), Key.key("crying_obsidian"), Key.key("end_gateway"), Key.key("end_portal"), Key.key("end_portal_frame"), Key.key("end_stone"), Key.key("iron_bars"), Key.key("jigsaw"), Key.key("moving_piston"), Key.key("obsidian"), Key.key("reinforced_deepslate"), Key.key("repeating_command_block"), Key.key("respawn_anchor"), Key.key("structure_block"), Key.key("test_block"), Key.key("test_instance_block"))),
        Map.entry(Key.key("dragon_transparent"), List.of(Key.key("fire"), Key.key("light"), Key.key("soul_fire"))),
        Map.entry(Key.key("dripstone_replaceable_blocks"), List.of(Key.key("andesite"), Key.key("deepslate"), Key.key("diorite"), Key.key("granite"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("edible_for_sheep"), List.of(Key.key("fern"), Key.key("short_dry_grass"), Key.key("short_grass"), Key.key("tall_dry_grass"))),
        Map.entry(Key.key("emerald_ores"), List.of(Key.key("deepslate_emerald_ore"), Key.key("emerald_ore"))),
        Map.entry(Key.key("enables_bubble_column_drag_down"), List.of(Key.key("magma_block"))),
        Map.entry(Key.key("enables_bubble_column_push_up"), List.of(Key.key("soul_sand"))),
        Map.entry(Key.key("enchantment_power_provider"), List.of(Key.key("bookshelf"))),
        Map.entry(Key.key("enchantment_power_transmitter"), List.of(Key.key("air"), Key.key("bubble_column"), Key.key("bush"), Key.key("cave_air"), Key.key("crimson_roots"), Key.key("dead_bush"), Key.key("fern"), Key.key("fire"), Key.key("glow_lichen"), Key.key("hanging_roots"), Key.key("large_fern"), Key.key("lava"), Key.key("leaf_litter"), Key.key("light"), Key.key("nether_sprouts"), Key.key("resin_clump"), Key.key("seagrass"), Key.key("short_dry_grass"), Key.key("short_grass"), Key.key("snow"), Key.key("soul_fire"), Key.key("structure_void"), Key.key("tall_dry_grass"), Key.key("tall_grass"), Key.key("tall_seagrass"), Key.key("vine"), Key.key("void_air"), Key.key("warped_roots"), Key.key("water"))),
        Map.entry(Key.key("enderman_holdable"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("brown_mushroom"), Key.key("cactus"), Key.key("cactus_flower"), Key.key("carved_pumpkin"), Key.key("clay"), Key.key("closed_eyeblossom"), Key.key("coarse_dirt"), Key.key("cornflower"), Key.key("crimson_fungus"), Key.key("crimson_nylium"), Key.key("crimson_roots"), Key.key("dandelion"), Key.key("dirt"), Key.key("golden_dandelion"), Key.key("grass_block"), Key.key("gravel"), Key.key("lily_of_the_valley"), Key.key("melon"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("pale_moss_block"), Key.key("pink_tulip"), Key.key("podzol"), Key.key("poppy"), Key.key("pumpkin"), Key.key("red_mushroom"), Key.key("red_sand"), Key.key("red_tulip"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("tnt"), Key.key("torchflower"), Key.key("warped_fungus"), Key.key("warped_nylium"), Key.key("warped_roots"), Key.key("white_tulip"), Key.key("wither_rose"))),
        Map.entry(Key.key("fall_damage_resetting"), List.of(Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("cobweb"), Key.key("ladder"), Key.key("scaffolding"), Key.key("sweet_berry_bush"), Key.key("twisting_vines"), Key.key("twisting_vines_plant"), Key.key("vine"), Key.key("weeping_vines"), Key.key("weeping_vines_plant"))),
        Map.entry(Key.key("features_cannot_replace"), List.of(Key.key("bedrock"), Key.key("chest"), Key.key("end_portal_frame"), Key.key("reinforced_deepslate"), Key.key("spawner"), Key.key("trial_spawner"), Key.key("vault"))),
        Map.entry(Key.key("fence_gates"), List.of(Key.key("acacia_fence_gate"), Key.key("bamboo_fence_gate"), Key.key("birch_fence_gate"), Key.key("cherry_fence_gate"), Key.key("crimson_fence_gate"), Key.key("dark_oak_fence_gate"), Key.key("jungle_fence_gate"), Key.key("mangrove_fence_gate"), Key.key("oak_fence_gate"), Key.key("pale_oak_fence_gate"), Key.key("spruce_fence_gate"), Key.key("warped_fence_gate"))),
        Map.entry(Key.key("fences"), List.of(Key.key("acacia_fence"), Key.key("bamboo_fence"), Key.key("birch_fence"), Key.key("cherry_fence"), Key.key("crimson_fence"), Key.key("dark_oak_fence"), Key.key("jungle_fence"), Key.key("mangrove_fence"), Key.key("nether_brick_fence"), Key.key("oak_fence"), Key.key("pale_oak_fence"), Key.key("spruce_fence"), Key.key("warped_fence"))),
        Map.entry(Key.key("fire"), List.of(Key.key("fire"), Key.key("soul_fire"))),
        Map.entry(Key.key("flower_pots"), List.of(Key.key("flower_pot"), Key.key("potted_acacia_sapling"), Key.key("potted_allium"), Key.key("potted_azalea_bush"), Key.key("potted_azure_bluet"), Key.key("potted_bamboo"), Key.key("potted_birch_sapling"), Key.key("potted_blue_orchid"), Key.key("potted_brown_mushroom"), Key.key("potted_cactus"), Key.key("potted_cherry_sapling"), Key.key("potted_closed_eyeblossom"), Key.key("potted_cornflower"), Key.key("potted_crimson_fungus"), Key.key("potted_crimson_roots"), Key.key("potted_dandelion"), Key.key("potted_dark_oak_sapling"), Key.key("potted_dead_bush"), Key.key("potted_fern"), Key.key("potted_flowering_azalea_bush"), Key.key("potted_golden_dandelion"), Key.key("potted_jungle_sapling"), Key.key("potted_lily_of_the_valley"), Key.key("potted_mangrove_propagule"), Key.key("potted_oak_sapling"), Key.key("potted_open_eyeblossom"), Key.key("potted_orange_tulip"), Key.key("potted_oxeye_daisy"), Key.key("potted_pale_oak_sapling"), Key.key("potted_pink_tulip"), Key.key("potted_poppy"), Key.key("potted_red_mushroom"), Key.key("potted_red_tulip"), Key.key("potted_spruce_sapling"), Key.key("potted_torchflower"), Key.key("potted_warped_fungus"), Key.key("potted_warped_roots"), Key.key("potted_white_tulip"), Key.key("potted_wither_rose"))),
        Map.entry(Key.key("flowers"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("cactus_flower"), Key.key("cherry_leaves"), Key.key("chorus_flower"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("dandelion"), Key.key("flowering_azalea"), Key.key("flowering_azalea_leaves"), Key.key("golden_dandelion"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_propagule"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("peony"), Key.key("pink_petals"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("spore_blossom"), Key.key("sunflower"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wildflowers"), Key.key("wither_rose"))),
        Map.entry(Key.key("forest_rock_can_place_on"), List.of(Key.key("andesite"), Key.key("coarse_dirt"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("fox_immune_to"), List.of(Key.key("sweet_berry_bush"))),
        Map.entry(Key.key("foxes_spawnable_on"), List.of(Key.key("coarse_dirt"), Key.key("grass_block"), Key.key("podzol"), Key.key("snow"), Key.key("snow_block"))),
        Map.entry(Key.key("frog_prefer_jump_to"), List.of(Key.key("big_dripleaf"), Key.key("lily_pad"))),
        Map.entry(Key.key("frogs_spawnable_on"), List.of(Key.key("grass_block"), Key.key("mangrove_roots"), Key.key("mud"), Key.key("muddy_mangrove_roots"))),
        Map.entry(Key.key("geode_invalid_blocks"), List.of(Key.key("bedrock"), Key.key("blue_ice"), Key.key("ice"), Key.key("lava"), Key.key("packed_ice"), Key.key("water"))),
        Map.entry(Key.key("glazed_terracotta"), List.of(Key.key("black_glazed_terracotta"), Key.key("blue_glazed_terracotta"), Key.key("brown_glazed_terracotta"), Key.key("cyan_glazed_terracotta"), Key.key("gray_glazed_terracotta"), Key.key("green_glazed_terracotta"), Key.key("light_blue_glazed_terracotta"), Key.key("light_gray_glazed_terracotta"), Key.key("lime_glazed_terracotta"), Key.key("magenta_glazed_terracotta"), Key.key("orange_glazed_terracotta"), Key.key("pink_glazed_terracotta"), Key.key("purple_glazed_terracotta"), Key.key("red_glazed_terracotta"), Key.key("white_glazed_terracotta"), Key.key("yellow_glazed_terracotta"))),
        Map.entry(Key.key("goats_spawnable_on"), List.of(Key.key("grass_block"), Key.key("gravel"), Key.key("packed_ice"), Key.key("snow"), Key.key("snow_block"), Key.key("stone"))),
        Map.entry(Key.key("gold_ores"), List.of(Key.key("deepslate_gold_ore"), Key.key("gold_ore"), Key.key("nether_gold_ore"))),
        Map.entry(Key.key("grass_blocks"), List.of(Key.key("grass_block"), Key.key("mycelium"), Key.key("podzol"))),
        Map.entry(Key.key("grows_crops"), List.of(Key.key("farmland"))),
        Map.entry(Key.key("guarded_by_piglins"), List.of(Key.key("barrel"), Key.key("black_shulker_box"), Key.key("blue_shulker_box"), Key.key("brown_shulker_box"), Key.key("chest"), Key.key("copper_chest"), Key.key("cyan_shulker_box"), Key.key("deepslate_gold_ore"), Key.key("ender_chest"), Key.key("exposed_copper_chest"), Key.key("gilded_blackstone"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("gray_shulker_box"), Key.key("green_shulker_box"), Key.key("light_blue_shulker_box"), Key.key("light_gray_shulker_box"), Key.key("lime_shulker_box"), Key.key("magenta_shulker_box"), Key.key("nether_gold_ore"), Key.key("orange_shulker_box"), Key.key("oxidized_copper_chest"), Key.key("pink_shulker_box"), Key.key("purple_shulker_box"), Key.key("raw_gold_block"), Key.key("red_shulker_box"), Key.key("shulker_box"), Key.key("trapped_chest"), Key.key("waxed_copper_chest"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_weathered_copper_chest"), Key.key("weathered_copper_chest"), Key.key("white_shulker_box"), Key.key("yellow_shulker_box"))),
        Map.entry(Key.key("happy_ghast_avoids"), List.of(Key.key("cactus"), Key.key("fire"), Key.key("magma_block"), Key.key("pointed_dripstone"), Key.key("sulfur_spike"), Key.key("sweet_berry_bush"), Key.key("wither_rose"))),
        Map.entry(Key.key("hoglin_repellents"), List.of(Key.key("nether_portal"), Key.key("potted_warped_fungus"), Key.key("respawn_anchor"), Key.key("warped_fungus"))),
        Map.entry(Key.key("huge_brown_mushroom_can_place_on"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("warped_nylium"))),
        Map.entry(Key.key("huge_red_mushroom_can_place_on"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("warped_nylium"))),
        Map.entry(Key.key("ice"), List.of(Key.key("blue_ice"), Key.key("frosted_ice"), Key.key("ice"), Key.key("packed_ice"))),
        Map.entry(Key.key("ice_spike_replaceable"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("ice"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("snow_block"))),
        Map.entry(Key.key("impermeable"), List.of(Key.key("barrier"), Key.key("black_stained_glass"), Key.key("blue_stained_glass"), Key.key("brown_stained_glass"), Key.key("cyan_stained_glass"), Key.key("glass"), Key.key("gray_stained_glass"), Key.key("green_stained_glass"), Key.key("light_blue_stained_glass"), Key.key("light_gray_stained_glass"), Key.key("lime_stained_glass"), Key.key("magenta_stained_glass"), Key.key("orange_stained_glass"), Key.key("pink_stained_glass"), Key.key("purple_stained_glass"), Key.key("red_stained_glass"), Key.key("tinted_glass"), Key.key("white_stained_glass"), Key.key("yellow_stained_glass"))),
        Map.entry(Key.key("incorrect_for_copper_tool"), List.of(Key.key("ancient_debris"), Key.key("crying_obsidian"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_redstone_ore"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("raw_gold_block"), Key.key("redstone_ore"), Key.key("respawn_anchor"))),
        Map.entry(Key.key("incorrect_for_diamond_tool"), List.of()),
        Map.entry(Key.key("incorrect_for_gold_tool"), List.of(Key.key("ancient_debris"), Key.key("chiseled_copper"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_chest"), Key.key("copper_grate"), Key.key("copper_ore"), Key.key("copper_trapdoor"), Key.key("crafter"), Key.key("crying_obsidian"), Key.key("cut_copper"), Key.key("cut_copper_slab"), Key.key("cut_copper_stairs"), Key.key("deepslate_copper_ore"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_iron_ore"), Key.key("deepslate_lapis_ore"), Key.key("deepslate_redstone_ore"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bulb"), Key.key("exposed_copper_chest"), Key.key("exposed_copper_grate"), Key.key("exposed_copper_trapdoor"), Key.key("exposed_cut_copper"), Key.key("exposed_cut_copper_slab"), Key.key("exposed_cut_copper_stairs"), Key.key("exposed_lightning_rod"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("iron_block"), Key.key("iron_ore"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("lightning_rod"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_copper_chest"), Key.key("oxidized_copper_grate"), Key.key("oxidized_copper_trapdoor"), Key.key("oxidized_cut_copper"), Key.key("oxidized_cut_copper_slab"), Key.key("oxidized_cut_copper_stairs"), Key.key("oxidized_lightning_rod"), Key.key("raw_copper_block"), Key.key("raw_gold_block"), Key.key("raw_iron_block"), Key.key("redstone_ore"), Key.key("respawn_anchor"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_copper_chest"), Key.key("waxed_copper_grate"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_cut_copper"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_exposed_copper_grate"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_oxidized_copper_grate"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_copper_chest"), Key.key("waxed_weathered_copper_grate"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("waxed_weathered_cut_copper"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bulb"), Key.key("weathered_copper_chest"), Key.key("weathered_copper_grate"), Key.key("weathered_copper_trapdoor"), Key.key("weathered_cut_copper"), Key.key("weathered_cut_copper_slab"), Key.key("weathered_cut_copper_stairs"), Key.key("weathered_lightning_rod"))),
        Map.entry(Key.key("incorrect_for_iron_tool"), List.of(Key.key("ancient_debris"), Key.key("crying_obsidian"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("respawn_anchor"))),
        Map.entry(Key.key("incorrect_for_netherite_tool"), List.of()),
        Map.entry(Key.key("incorrect_for_stone_tool"), List.of(Key.key("ancient_debris"), Key.key("crying_obsidian"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_redstone_ore"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("raw_gold_block"), Key.key("redstone_ore"), Key.key("respawn_anchor"))),
        Map.entry(Key.key("incorrect_for_wooden_tool"), List.of(Key.key("ancient_debris"), Key.key("chiseled_copper"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_chest"), Key.key("copper_grate"), Key.key("copper_ore"), Key.key("copper_trapdoor"), Key.key("crafter"), Key.key("crying_obsidian"), Key.key("cut_copper"), Key.key("cut_copper_slab"), Key.key("cut_copper_stairs"), Key.key("deepslate_copper_ore"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_iron_ore"), Key.key("deepslate_lapis_ore"), Key.key("deepslate_redstone_ore"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bulb"), Key.key("exposed_copper_chest"), Key.key("exposed_copper_grate"), Key.key("exposed_copper_trapdoor"), Key.key("exposed_cut_copper"), Key.key("exposed_cut_copper_slab"), Key.key("exposed_cut_copper_stairs"), Key.key("exposed_lightning_rod"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("iron_block"), Key.key("iron_ore"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("lightning_rod"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_copper_chest"), Key.key("oxidized_copper_grate"), Key.key("oxidized_copper_trapdoor"), Key.key("oxidized_cut_copper"), Key.key("oxidized_cut_copper_slab"), Key.key("oxidized_cut_copper_stairs"), Key.key("oxidized_lightning_rod"), Key.key("raw_copper_block"), Key.key("raw_gold_block"), Key.key("raw_iron_block"), Key.key("redstone_ore"), Key.key("respawn_anchor"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_copper_chest"), Key.key("waxed_copper_grate"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_cut_copper"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_exposed_copper_grate"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_oxidized_copper_grate"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_copper_chest"), Key.key("waxed_weathered_copper_grate"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("waxed_weathered_cut_copper"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bulb"), Key.key("weathered_copper_chest"), Key.key("weathered_copper_grate"), Key.key("weathered_copper_trapdoor"), Key.key("weathered_cut_copper"), Key.key("weathered_cut_copper_slab"), Key.key("weathered_cut_copper_stairs"), Key.key("weathered_lightning_rod"))),
        Map.entry(Key.key("infiniburn_end"), List.of(Key.key("bedrock"), Key.key("magma_block"), Key.key("netherrack"))),
        Map.entry(Key.key("infiniburn_nether"), List.of(Key.key("magma_block"), Key.key("netherrack"))),
        Map.entry(Key.key("infiniburn_overworld"), List.of(Key.key("magma_block"), Key.key("netherrack"))),
        Map.entry(Key.key("inside_step_sound_blocks"), List.of(Key.key("glow_lichen"), Key.key("leaf_litter"), Key.key("lily_pad"), Key.key("pink_petals"), Key.key("powder_snow"), Key.key("sculk_vein"), Key.key("small_amethyst_bud"), Key.key("wildflowers"))),
        Map.entry(Key.key("invalid_spawn_inside"), List.of(Key.key("end_gateway"), Key.key("end_portal"))),
        Map.entry(Key.key("iron_ores"), List.of(Key.key("deepslate_iron_ore"), Key.key("iron_ore"))),
        Map.entry(Key.key("jungle_logs"), List.of(Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"))),
        Map.entry(Key.key("lanterns"), List.of(Key.key("copper_lantern"), Key.key("exposed_copper_lantern"), Key.key("lantern"), Key.key("oxidized_copper_lantern"), Key.key("soul_lantern"), Key.key("waxed_copper_lantern"), Key.key("waxed_exposed_copper_lantern"), Key.key("waxed_oxidized_copper_lantern"), Key.key("waxed_weathered_copper_lantern"), Key.key("weathered_copper_lantern"))),
        Map.entry(Key.key("lapis_ores"), List.of(Key.key("deepslate_lapis_ore"), Key.key("lapis_ore"))),
        Map.entry(Key.key("lava_pool_stone_cannot_replace"), List.of(Key.key("acacia_leaves"), Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("azalea_leaves"), Key.key("bedrock"), Key.key("birch_leaves"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_leaves"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("chest"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_leaves"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("end_portal_frame"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_leaves"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_leaves"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_leaves"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("reinforced_deepslate"), Key.key("spawner"), Key.key("spruce_leaves"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("trial_spawner"), Key.key("vault"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("leaves"), List.of(Key.key("acacia_leaves"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("cherry_leaves"), Key.key("dark_oak_leaves"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("mangrove_leaves"), Key.key("oak_leaves"), Key.key("pale_oak_leaves"), Key.key("spruce_leaves"))),
        Map.entry(Key.key("lightning_rods"), List.of(Key.key("exposed_lightning_rod"), Key.key("lightning_rod"), Key.key("oxidized_lightning_rod"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_lightning_rod"))),
        Map.entry(Key.key("logs"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("logs_that_burn"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"))),
        Map.entry(Key.key("lush_ground_replaceable"), List.of(Key.key("andesite"), Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("clay"), Key.key("coarse_dirt"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("gravel"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("maintains_farmland"), List.of(Key.key("acacia_fence_gate"), Key.key("attached_melon_stem"), Key.key("attached_pumpkin_stem"), Key.key("bamboo_fence_gate"), Key.key("beetroots"), Key.key("birch_fence_gate"), Key.key("carrots"), Key.key("cherry_fence_gate"), Key.key("crimson_fence_gate"), Key.key("dark_oak_fence_gate"), Key.key("jungle_fence_gate"), Key.key("mangrove_fence_gate"), Key.key("melon_stem"), Key.key("moving_piston"), Key.key("oak_fence_gate"), Key.key("pale_oak_fence_gate"), Key.key("pitcher_crop"), Key.key("potatoes"), Key.key("pumpkin_stem"), Key.key("spruce_fence_gate"), Key.key("torchflower"), Key.key("torchflower_crop"), Key.key("warped_fence_gate"), Key.key("wheat"))),
        Map.entry(Key.key("mangrove_logs"), List.of(Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"))),
        Map.entry(Key.key("mangrove_logs_can_grow_through"), List.of(Key.key("mangrove_leaves"), Key.key("mangrove_log"), Key.key("mangrove_propagule"), Key.key("mangrove_roots"), Key.key("moss_carpet"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("vine"))),
        Map.entry(Key.key("mangrove_roots_can_grow_through"), List.of(Key.key("mangrove_propagule"), Key.key("mangrove_roots"), Key.key("moss_carpet"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("snow"), Key.key("vine"))),
        Map.entry(Key.key("mineable/axe"), List.of(Key.key("acacia_button"), Key.key("acacia_door"), Key.key("acacia_fence"), Key.key("acacia_fence_gate"), Key.key("acacia_hanging_sign"), Key.key("acacia_log"), Key.key("acacia_planks"), Key.key("acacia_pressure_plate"), Key.key("acacia_shelf"), Key.key("acacia_sign"), Key.key("acacia_slab"), Key.key("acacia_stairs"), Key.key("acacia_trapdoor"), Key.key("acacia_wall_hanging_sign"), Key.key("acacia_wall_sign"), Key.key("acacia_wood"), Key.key("bamboo"), Key.key("bamboo_block"), Key.key("bamboo_button"), Key.key("bamboo_door"), Key.key("bamboo_fence"), Key.key("bamboo_fence_gate"), Key.key("bamboo_hanging_sign"), Key.key("bamboo_mosaic"), Key.key("bamboo_mosaic_slab"), Key.key("bamboo_mosaic_stairs"), Key.key("bamboo_planks"), Key.key("bamboo_pressure_plate"), Key.key("bamboo_shelf"), Key.key("bamboo_sign"), Key.key("bamboo_slab"), Key.key("bamboo_stairs"), Key.key("bamboo_trapdoor"), Key.key("bamboo_wall_hanging_sign"), Key.key("bamboo_wall_sign"), Key.key("barrel"), Key.key("bee_nest"), Key.key("beehive"), Key.key("big_dripleaf"), Key.key("big_dripleaf_stem"), Key.key("birch_button"), Key.key("birch_door"), Key.key("birch_fence"), Key.key("birch_fence_gate"), Key.key("birch_hanging_sign"), Key.key("birch_log"), Key.key("birch_planks"), Key.key("birch_pressure_plate"), Key.key("birch_shelf"), Key.key("birch_sign"), Key.key("birch_slab"), Key.key("birch_stairs"), Key.key("birch_trapdoor"), Key.key("birch_wall_hanging_sign"), Key.key("birch_wall_sign"), Key.key("birch_wood"), Key.key("black_banner"), Key.key("black_wall_banner"), Key.key("blue_banner"), Key.key("blue_wall_banner"), Key.key("bookshelf"), Key.key("brown_banner"), Key.key("brown_mushroom_block"), Key.key("brown_wall_banner"), Key.key("campfire"), Key.key("cartography_table"), Key.key("carved_pumpkin"), Key.key("cherry_button"), Key.key("cherry_door"), Key.key("cherry_fence"), Key.key("cherry_fence_gate"), Key.key("cherry_hanging_sign"), Key.key("cherry_log"), Key.key("cherry_planks"), Key.key("cherry_pressure_plate"), Key.key("cherry_shelf"), Key.key("cherry_sign"), Key.key("cherry_slab"), Key.key("cherry_stairs"), Key.key("cherry_trapdoor"), Key.key("cherry_wall_hanging_sign"), Key.key("cherry_wall_sign"), Key.key("cherry_wood"), Key.key("chest"), Key.key("chiseled_bookshelf"), Key.key("chorus_flower"), Key.key("chorus_plant"), Key.key("cocoa"), Key.key("composter"), Key.key("crafting_table"), Key.key("creaking_heart"), Key.key("crimson_button"), Key.key("crimson_door"), Key.key("crimson_fence"), Key.key("crimson_fence_gate"), Key.key("crimson_hanging_sign"), Key.key("crimson_hyphae"), Key.key("crimson_planks"), Key.key("crimson_pressure_plate"), Key.key("crimson_shelf"), Key.key("crimson_sign"), Key.key("crimson_slab"), Key.key("crimson_stairs"), Key.key("crimson_stem"), Key.key("crimson_trapdoor"), Key.key("crimson_wall_hanging_sign"), Key.key("crimson_wall_sign"), Key.key("cyan_banner"), Key.key("cyan_wall_banner"), Key.key("dark_oak_button"), Key.key("dark_oak_door"), Key.key("dark_oak_fence"), Key.key("dark_oak_fence_gate"), Key.key("dark_oak_hanging_sign"), Key.key("dark_oak_log"), Key.key("dark_oak_planks"), Key.key("dark_oak_pressure_plate"), Key.key("dark_oak_shelf"), Key.key("dark_oak_sign"), Key.key("dark_oak_slab"), Key.key("dark_oak_stairs"), Key.key("dark_oak_trapdoor"), Key.key("dark_oak_wall_hanging_sign"), Key.key("dark_oak_wall_sign"), Key.key("dark_oak_wood"), Key.key("daylight_detector"), Key.key("fletching_table"), Key.key("glow_lichen"), Key.key("gray_banner"), Key.key("gray_wall_banner"), Key.key("green_banner"), Key.key("green_wall_banner"), Key.key("jack_o_lantern"), Key.key("jukebox"), Key.key("jungle_button"), Key.key("jungle_door"), Key.key("jungle_fence"), Key.key("jungle_fence_gate"), Key.key("jungle_hanging_sign"), Key.key("jungle_log"), Key.key("jungle_planks"), Key.key("jungle_pressure_plate"), Key.key("jungle_shelf"), Key.key("jungle_sign"), Key.key("jungle_slab"), Key.key("jungle_stairs"), Key.key("jungle_trapdoor"), Key.key("jungle_wall_hanging_sign"), Key.key("jungle_wall_sign"), Key.key("jungle_wood"), Key.key("ladder"), Key.key("lectern"), Key.key("light_blue_banner"), Key.key("light_blue_wall_banner"), Key.key("light_gray_banner"), Key.key("light_gray_wall_banner"), Key.key("lime_banner"), Key.key("lime_wall_banner"), Key.key("loom"), Key.key("magenta_banner"), Key.key("magenta_wall_banner"), Key.key("mangrove_button"), Key.key("mangrove_door"), Key.key("mangrove_fence"), Key.key("mangrove_fence_gate"), Key.key("mangrove_hanging_sign"), Key.key("mangrove_log"), Key.key("mangrove_planks"), Key.key("mangrove_pressure_plate"), Key.key("mangrove_roots"), Key.key("mangrove_shelf"), Key.key("mangrove_sign"), Key.key("mangrove_slab"), Key.key("mangrove_stairs"), Key.key("mangrove_trapdoor"), Key.key("mangrove_wall_hanging_sign"), Key.key("mangrove_wall_sign"), Key.key("mangrove_wood"), Key.key("melon"), Key.key("mushroom_stem"), Key.key("note_block"), Key.key("oak_button"), Key.key("oak_door"), Key.key("oak_fence"), Key.key("oak_fence_gate"), Key.key("oak_hanging_sign"), Key.key("oak_log"), Key.key("oak_planks"), Key.key("oak_pressure_plate"), Key.key("oak_shelf"), Key.key("oak_sign"), Key.key("oak_slab"), Key.key("oak_stairs"), Key.key("oak_trapdoor"), Key.key("oak_wall_hanging_sign"), Key.key("oak_wall_sign"), Key.key("oak_wood"), Key.key("orange_banner"), Key.key("orange_wall_banner"), Key.key("pale_oak_button"), Key.key("pale_oak_door"), Key.key("pale_oak_fence"), Key.key("pale_oak_fence_gate"), Key.key("pale_oak_hanging_sign"), Key.key("pale_oak_log"), Key.key("pale_oak_planks"), Key.key("pale_oak_pressure_plate"), Key.key("pale_oak_shelf"), Key.key("pale_oak_sign"), Key.key("pale_oak_slab"), Key.key("pale_oak_stairs"), Key.key("pale_oak_trapdoor"), Key.key("pale_oak_wall_hanging_sign"), Key.key("pale_oak_wall_sign"), Key.key("pale_oak_wood"), Key.key("pink_banner"), Key.key("pink_wall_banner"), Key.key("pumpkin"), Key.key("purple_banner"), Key.key("purple_wall_banner"), Key.key("red_banner"), Key.key("red_mushroom_block"), Key.key("red_wall_banner"), Key.key("smithing_table"), Key.key("soul_campfire"), Key.key("spruce_button"), Key.key("spruce_door"), Key.key("spruce_fence"), Key.key("spruce_fence_gate"), Key.key("spruce_hanging_sign"), Key.key("spruce_log"), Key.key("spruce_planks"), Key.key("spruce_pressure_plate"), Key.key("spruce_shelf"), Key.key("spruce_sign"), Key.key("spruce_slab"), Key.key("spruce_stairs"), Key.key("spruce_trapdoor"), Key.key("spruce_wall_hanging_sign"), Key.key("spruce_wall_sign"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_bamboo_block"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("trapped_chest"), Key.key("vine"), Key.key("warped_button"), Key.key("warped_door"), Key.key("warped_fence"), Key.key("warped_fence_gate"), Key.key("warped_hanging_sign"), Key.key("warped_hyphae"), Key.key("warped_planks"), Key.key("warped_pressure_plate"), Key.key("warped_shelf"), Key.key("warped_sign"), Key.key("warped_slab"), Key.key("warped_stairs"), Key.key("warped_stem"), Key.key("warped_trapdoor"), Key.key("warped_wall_hanging_sign"), Key.key("warped_wall_sign"), Key.key("white_banner"), Key.key("white_wall_banner"), Key.key("yellow_banner"), Key.key("yellow_wall_banner"))),
        Map.entry(Key.key("mineable/hoe"), List.of(Key.key("acacia_leaves"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("calibrated_sculk_sensor"), Key.key("cherry_leaves"), Key.key("dark_oak_leaves"), Key.key("dried_kelp_block"), Key.key("flowering_azalea_leaves"), Key.key("hay_block"), Key.key("jungle_leaves"), Key.key("mangrove_leaves"), Key.key("moss_block"), Key.key("moss_carpet"), Key.key("nether_wart_block"), Key.key("oak_leaves"), Key.key("pale_moss_block"), Key.key("pale_moss_carpet"), Key.key("pale_oak_leaves"), Key.key("sculk"), Key.key("sculk_catalyst"), Key.key("sculk_sensor"), Key.key("sculk_shrieker"), Key.key("sculk_vein"), Key.key("shroomlight"), Key.key("sponge"), Key.key("spruce_leaves"), Key.key("target"), Key.key("warped_wart_block"), Key.key("wet_sponge"))),
        Map.entry(Key.key("mineable/pickaxe"), List.of(Key.key("activator_rail"), Key.key("amethyst_block"), Key.key("amethyst_cluster"), Key.key("ancient_debris"), Key.key("andesite"), Key.key("andesite_slab"), Key.key("andesite_stairs"), Key.key("andesite_wall"), Key.key("anvil"), Key.key("basalt"), Key.key("bell"), Key.key("black_concrete"), Key.key("black_glazed_terracotta"), Key.key("black_shulker_box"), Key.key("black_terracotta"), Key.key("blackstone"), Key.key("blackstone_slab"), Key.key("blackstone_stairs"), Key.key("blackstone_wall"), Key.key("blast_furnace"), Key.key("blue_concrete"), Key.key("blue_glazed_terracotta"), Key.key("blue_ice"), Key.key("blue_shulker_box"), Key.key("blue_terracotta"), Key.key("bone_block"), Key.key("brain_coral_block"), Key.key("brewing_stand"), Key.key("brick_slab"), Key.key("brick_stairs"), Key.key("brick_wall"), Key.key("bricks"), Key.key("brown_concrete"), Key.key("brown_glazed_terracotta"), Key.key("brown_shulker_box"), Key.key("brown_terracotta"), Key.key("bubble_coral_block"), Key.key("budding_amethyst"), Key.key("calcite"), Key.key("cauldron"), Key.key("chipped_anvil"), Key.key("chiseled_cinnabar"), Key.key("chiseled_copper"), Key.key("chiseled_deepslate"), Key.key("chiseled_nether_bricks"), Key.key("chiseled_polished_blackstone"), Key.key("chiseled_quartz_block"), Key.key("chiseled_red_sandstone"), Key.key("chiseled_resin_bricks"), Key.key("chiseled_sandstone"), Key.key("chiseled_stone_bricks"), Key.key("chiseled_sulfur"), Key.key("chiseled_tuff"), Key.key("chiseled_tuff_bricks"), Key.key("cinnabar"), Key.key("cinnabar_brick_slab"), Key.key("cinnabar_brick_stairs"), Key.key("cinnabar_brick_wall"), Key.key("cinnabar_bricks"), Key.key("cinnabar_slab"), Key.key("cinnabar_stairs"), Key.key("cinnabar_wall"), Key.key("coal_block"), Key.key("coal_ore"), Key.key("cobbled_deepslate"), Key.key("cobbled_deepslate_slab"), Key.key("cobbled_deepslate_stairs"), Key.key("cobbled_deepslate_wall"), Key.key("cobblestone"), Key.key("cobblestone_slab"), Key.key("cobblestone_stairs"), Key.key("cobblestone_wall"), Key.key("conduit"), Key.key("copper_bars"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_chain"), Key.key("copper_chest"), Key.key("copper_door"), Key.key("copper_golem_statue"), Key.key("copper_grate"), Key.key("copper_lantern"), Key.key("copper_ore"), Key.key("copper_trapdoor"), Key.key("cracked_deepslate_bricks"), Key.key("cracked_deepslate_tiles"), Key.key("cracked_nether_bricks"), Key.key("cracked_polished_blackstone_bricks"), Key.key("cracked_stone_bricks"), Key.key("crafter"), Key.key("crimson_nylium"), Key.key("crying_obsidian"), Key.key("cut_copper"), Key.key("cut_copper_slab"), Key.key("cut_copper_stairs"), Key.key("cut_red_sandstone"), Key.key("cut_red_sandstone_slab"), Key.key("cut_sandstone"), Key.key("cut_sandstone_slab"), Key.key("cyan_concrete"), Key.key("cyan_glazed_terracotta"), Key.key("cyan_shulker_box"), Key.key("cyan_terracotta"), Key.key("damaged_anvil"), Key.key("dark_prismarine"), Key.key("dark_prismarine_slab"), Key.key("dark_prismarine_stairs"), Key.key("dead_brain_coral"), Key.key("dead_brain_coral_block"), Key.key("dead_brain_coral_fan"), Key.key("dead_brain_coral_wall_fan"), Key.key("dead_bubble_coral"), Key.key("dead_bubble_coral_block"), Key.key("dead_bubble_coral_fan"), Key.key("dead_bubble_coral_wall_fan"), Key.key("dead_fire_coral"), Key.key("dead_fire_coral_block"), Key.key("dead_fire_coral_fan"), Key.key("dead_fire_coral_wall_fan"), Key.key("dead_horn_coral"), Key.key("dead_horn_coral_block"), Key.key("dead_horn_coral_fan"), Key.key("dead_horn_coral_wall_fan"), Key.key("dead_tube_coral"), Key.key("dead_tube_coral_block"), Key.key("dead_tube_coral_fan"), Key.key("dead_tube_coral_wall_fan"), Key.key("deepslate"), Key.key("deepslate_brick_slab"), Key.key("deepslate_brick_stairs"), Key.key("deepslate_brick_wall"), Key.key("deepslate_bricks"), Key.key("deepslate_coal_ore"), Key.key("deepslate_copper_ore"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_iron_ore"), Key.key("deepslate_lapis_ore"), Key.key("deepslate_redstone_ore"), Key.key("deepslate_tile_slab"), Key.key("deepslate_tile_stairs"), Key.key("deepslate_tile_wall"), Key.key("deepslate_tiles"), Key.key("detector_rail"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("diorite"), Key.key("diorite_slab"), Key.key("diorite_stairs"), Key.key("diorite_wall"), Key.key("dispenser"), Key.key("dripstone_block"), Key.key("dropper"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("enchanting_table"), Key.key("end_stone"), Key.key("end_stone_brick_slab"), Key.key("end_stone_brick_stairs"), Key.key("end_stone_brick_wall"), Key.key("end_stone_bricks"), Key.key("ender_chest"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bars"), Key.key("exposed_copper_bulb"), Key.key("exposed_copper_chain"), Key.key("exposed_copper_chest"), Key.key("exposed_copper_door"), Key.key("exposed_copper_golem_statue"), Key.key("exposed_copper_grate"), Key.key("exposed_copper_lantern"), Key.key("exposed_copper_trapdoor"), Key.key("exposed_cut_copper"), Key.key("exposed_cut_copper_slab"), Key.key("exposed_cut_copper_stairs"), Key.key("exposed_lightning_rod"), Key.key("fire_coral_block"), Key.key("furnace"), Key.key("gilded_blackstone"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("granite"), Key.key("granite_slab"), Key.key("granite_stairs"), Key.key("granite_wall"), Key.key("gray_concrete"), Key.key("gray_glazed_terracotta"), Key.key("gray_shulker_box"), Key.key("gray_terracotta"), Key.key("green_concrete"), Key.key("green_glazed_terracotta"), Key.key("green_shulker_box"), Key.key("green_terracotta"), Key.key("grindstone"), Key.key("heavy_core"), Key.key("heavy_weighted_pressure_plate"), Key.key("hopper"), Key.key("horn_coral_block"), Key.key("ice"), Key.key("infested_chiseled_stone_bricks"), Key.key("infested_cobblestone"), Key.key("infested_cracked_stone_bricks"), Key.key("infested_deepslate"), Key.key("infested_mossy_stone_bricks"), Key.key("infested_stone"), Key.key("infested_stone_bricks"), Key.key("iron_bars"), Key.key("iron_block"), Key.key("iron_chain"), Key.key("iron_door"), Key.key("iron_ore"), Key.key("iron_trapdoor"), Key.key("lantern"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("large_amethyst_bud"), Key.key("lava_cauldron"), Key.key("light_blue_concrete"), Key.key("light_blue_glazed_terracotta"), Key.key("light_blue_shulker_box"), Key.key("light_blue_terracotta"), Key.key("light_gray_concrete"), Key.key("light_gray_glazed_terracotta"), Key.key("light_gray_shulker_box"), Key.key("light_gray_terracotta"), Key.key("light_weighted_pressure_plate"), Key.key("lightning_rod"), Key.key("lime_concrete"), Key.key("lime_glazed_terracotta"), Key.key("lime_shulker_box"), Key.key("lime_terracotta"), Key.key("lodestone"), Key.key("magenta_concrete"), Key.key("magenta_glazed_terracotta"), Key.key("magenta_shulker_box"), Key.key("magenta_terracotta"), Key.key("magma_block"), Key.key("medium_amethyst_bud"), Key.key("mossy_cobblestone"), Key.key("mossy_cobblestone_slab"), Key.key("mossy_cobblestone_stairs"), Key.key("mossy_cobblestone_wall"), Key.key("mossy_stone_brick_slab"), Key.key("mossy_stone_brick_stairs"), Key.key("mossy_stone_brick_wall"), Key.key("mossy_stone_bricks"), Key.key("mud_brick_slab"), Key.key("mud_brick_stairs"), Key.key("mud_brick_wall"), Key.key("mud_bricks"), Key.key("nether_brick_fence"), Key.key("nether_brick_slab"), Key.key("nether_brick_stairs"), Key.key("nether_brick_wall"), Key.key("nether_bricks"), Key.key("nether_gold_ore"), Key.key("nether_quartz_ore"), Key.key("netherite_block"), Key.key("netherrack"), Key.key("observer"), Key.key("obsidian"), Key.key("orange_concrete"), Key.key("orange_glazed_terracotta"), Key.key("orange_shulker_box"), Key.key("orange_terracotta"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bars"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_copper_chain"), Key.key("oxidized_copper_chest"), Key.key("oxidized_copper_door"), Key.key("oxidized_copper_golem_statue"), Key.key("oxidized_copper_grate"), Key.key("oxidized_copper_lantern"), Key.key("oxidized_copper_trapdoor"), Key.key("oxidized_cut_copper"), Key.key("oxidized_cut_copper_slab"), Key.key("oxidized_cut_copper_stairs"), Key.key("oxidized_lightning_rod"), Key.key("packed_ice"), Key.key("packed_mud"), Key.key("petrified_oak_slab"), Key.key("pink_concrete"), Key.key("pink_glazed_terracotta"), Key.key("pink_shulker_box"), Key.key("pink_terracotta"), Key.key("piston"), Key.key("piston_head"), Key.key("pointed_dripstone"), Key.key("polished_andesite"), Key.key("polished_andesite_slab"), Key.key("polished_andesite_stairs"), Key.key("polished_basalt"), Key.key("polished_blackstone"), Key.key("polished_blackstone_brick_slab"), Key.key("polished_blackstone_brick_stairs"), Key.key("polished_blackstone_brick_wall"), Key.key("polished_blackstone_bricks"), Key.key("polished_blackstone_button"), Key.key("polished_blackstone_pressure_plate"), Key.key("polished_blackstone_slab"), Key.key("polished_blackstone_stairs"), Key.key("polished_blackstone_wall"), Key.key("polished_cinnabar"), Key.key("polished_cinnabar_slab"), Key.key("polished_cinnabar_stairs"), Key.key("polished_cinnabar_wall"), Key.key("polished_deepslate"), Key.key("polished_deepslate_slab"), Key.key("polished_deepslate_stairs"), Key.key("polished_deepslate_wall"), Key.key("polished_diorite"), Key.key("polished_diorite_slab"), Key.key("polished_diorite_stairs"), Key.key("polished_granite"), Key.key("polished_granite_slab"), Key.key("polished_granite_stairs"), Key.key("polished_sulfur"), Key.key("polished_sulfur_slab"), Key.key("polished_sulfur_stairs"), Key.key("polished_sulfur_wall"), Key.key("polished_tuff"), Key.key("polished_tuff_slab"), Key.key("polished_tuff_stairs"), Key.key("polished_tuff_wall"), Key.key("potent_sulfur"), Key.key("powder_snow_cauldron"), Key.key("powered_rail"), Key.key("prismarine"), Key.key("prismarine_brick_slab"), Key.key("prismarine_brick_stairs"), Key.key("prismarine_bricks"), Key.key("prismarine_slab"), Key.key("prismarine_stairs"), Key.key("prismarine_wall"), Key.key("purple_concrete"), Key.key("purple_glazed_terracotta"), Key.key("purple_shulker_box"), Key.key("purple_terracotta"), Key.key("purpur_block"), Key.key("purpur_pillar"), Key.key("purpur_slab"), Key.key("purpur_stairs"), Key.key("quartz_block"), Key.key("quartz_bricks"), Key.key("quartz_pillar"), Key.key("quartz_slab"), Key.key("quartz_stairs"), Key.key("rail"), Key.key("raw_copper_block"), Key.key("raw_gold_block"), Key.key("raw_iron_block"), Key.key("red_concrete"), Key.key("red_glazed_terracotta"), Key.key("red_nether_brick_slab"), Key.key("red_nether_brick_stairs"), Key.key("red_nether_brick_wall"), Key.key("red_nether_bricks"), Key.key("red_sandstone"), Key.key("red_sandstone_slab"), Key.key("red_sandstone_stairs"), Key.key("red_sandstone_wall"), Key.key("red_shulker_box"), Key.key("red_terracotta"), Key.key("redstone_block"), Key.key("redstone_ore"), Key.key("resin_brick_slab"), Key.key("resin_brick_stairs"), Key.key("resin_brick_wall"), Key.key("resin_bricks"), Key.key("respawn_anchor"), Key.key("sandstone"), Key.key("sandstone_slab"), Key.key("sandstone_stairs"), Key.key("sandstone_wall"), Key.key("shulker_box"), Key.key("small_amethyst_bud"), Key.key("smoker"), Key.key("smooth_basalt"), Key.key("smooth_quartz"), Key.key("smooth_quartz_slab"), Key.key("smooth_quartz_stairs"), Key.key("smooth_red_sandstone"), Key.key("smooth_red_sandstone_slab"), Key.key("smooth_red_sandstone_stairs"), Key.key("smooth_sandstone"), Key.key("smooth_sandstone_slab"), Key.key("smooth_sandstone_stairs"), Key.key("smooth_stone"), Key.key("smooth_stone_slab"), Key.key("soul_lantern"), Key.key("spawner"), Key.key("sticky_piston"), Key.key("stone"), Key.key("stone_brick_slab"), Key.key("stone_brick_stairs"), Key.key("stone_brick_wall"), Key.key("stone_bricks"), Key.key("stone_button"), Key.key("stone_pressure_plate"), Key.key("stone_slab"), Key.key("stone_stairs"), Key.key("stonecutter"), Key.key("sulfur"), Key.key("sulfur_brick_slab"), Key.key("sulfur_brick_stairs"), Key.key("sulfur_brick_wall"), Key.key("sulfur_bricks"), Key.key("sulfur_slab"), Key.key("sulfur_spike"), Key.key("sulfur_stairs"), Key.key("sulfur_wall"), Key.key("terracotta"), Key.key("tube_coral_block"), Key.key("tuff"), Key.key("tuff_brick_slab"), Key.key("tuff_brick_stairs"), Key.key("tuff_brick_wall"), Key.key("tuff_bricks"), Key.key("tuff_slab"), Key.key("tuff_stairs"), Key.key("tuff_wall"), Key.key("warped_nylium"), Key.key("water_cauldron"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_bars"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_copper_chain"), Key.key("waxed_copper_chest"), Key.key("waxed_copper_door"), Key.key("waxed_copper_golem_statue"), Key.key("waxed_copper_grate"), Key.key("waxed_copper_lantern"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_cut_copper"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bars"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_copper_chain"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_exposed_copper_door"), Key.key("waxed_exposed_copper_golem_statue"), Key.key("waxed_exposed_copper_grate"), Key.key("waxed_exposed_copper_lantern"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bars"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_copper_chain"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_oxidized_copper_door"), Key.key("waxed_oxidized_copper_golem_statue"), Key.key("waxed_oxidized_copper_grate"), Key.key("waxed_oxidized_copper_lantern"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bars"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_copper_chain"), Key.key("waxed_weathered_copper_chest"), Key.key("waxed_weathered_copper_door"), Key.key("waxed_weathered_copper_golem_statue"), Key.key("waxed_weathered_copper_grate"), Key.key("waxed_weathered_copper_lantern"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("waxed_weathered_cut_copper"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bars"), Key.key("weathered_copper_bulb"), Key.key("weathered_copper_chain"), Key.key("weathered_copper_chest"), Key.key("weathered_copper_door"), Key.key("weathered_copper_golem_statue"), Key.key("weathered_copper_grate"), Key.key("weathered_copper_lantern"), Key.key("weathered_copper_trapdoor"), Key.key("weathered_cut_copper"), Key.key("weathered_cut_copper_slab"), Key.key("weathered_cut_copper_stairs"), Key.key("weathered_lightning_rod"), Key.key("white_concrete"), Key.key("white_glazed_terracotta"), Key.key("white_shulker_box"), Key.key("white_terracotta"), Key.key("yellow_concrete"), Key.key("yellow_glazed_terracotta"), Key.key("yellow_shulker_box"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("mineable/shovel"), List.of(Key.key("black_concrete_powder"), Key.key("blue_concrete_powder"), Key.key("brown_concrete_powder"), Key.key("clay"), Key.key("coarse_dirt"), Key.key("cyan_concrete_powder"), Key.key("dirt"), Key.key("dirt_path"), Key.key("farmland"), Key.key("grass_block"), Key.key("gravel"), Key.key("gray_concrete_powder"), Key.key("green_concrete_powder"), Key.key("light_blue_concrete_powder"), Key.key("light_gray_concrete_powder"), Key.key("lime_concrete_powder"), Key.key("magenta_concrete_powder"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("orange_concrete_powder"), Key.key("pink_concrete_powder"), Key.key("podzol"), Key.key("purple_concrete_powder"), Key.key("red_concrete_powder"), Key.key("red_sand"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("snow"), Key.key("snow_block"), Key.key("soul_sand"), Key.key("soul_soil"), Key.key("suspicious_gravel"), Key.key("suspicious_sand"), Key.key("white_concrete_powder"), Key.key("yellow_concrete_powder"))),
        Map.entry(Key.key("mob_interactable_doors"), List.of(Key.key("acacia_door"), Key.key("bamboo_door"), Key.key("birch_door"), Key.key("cherry_door"), Key.key("copper_door"), Key.key("crimson_door"), Key.key("dark_oak_door"), Key.key("exposed_copper_door"), Key.key("jungle_door"), Key.key("mangrove_door"), Key.key("oak_door"), Key.key("oxidized_copper_door"), Key.key("pale_oak_door"), Key.key("spruce_door"), Key.key("warped_door"), Key.key("waxed_copper_door"), Key.key("waxed_exposed_copper_door"), Key.key("waxed_oxidized_copper_door"), Key.key("waxed_weathered_copper_door"), Key.key("weathered_copper_door"))),
        Map.entry(Key.key("mooshrooms_spawnable_on"), List.of(Key.key("mycelium"))),
        Map.entry(Key.key("moss_blocks"), List.of(Key.key("moss_block"), Key.key("pale_moss_block"))),
        Map.entry(Key.key("moss_replaceable"), List.of(Key.key("andesite"), Key.key("cave_vines"), Key.key("cave_vines_plant"), Key.key("coarse_dirt"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("stone"), Key.key("tuff"))),
        Map.entry(Key.key("mud"), List.of(Key.key("mud"), Key.key("muddy_mangrove_roots"))),
        Map.entry(Key.key("needs_diamond_tool"), List.of(Key.key("ancient_debris"), Key.key("crying_obsidian"), Key.key("netherite_block"), Key.key("obsidian"), Key.key("respawn_anchor"))),
        Map.entry(Key.key("needs_iron_tool"), List.of(Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_redstone_ore"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("raw_gold_block"), Key.key("redstone_ore"))),
        Map.entry(Key.key("needs_stone_tool"), List.of(Key.key("chiseled_copper"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_chest"), Key.key("copper_grate"), Key.key("copper_ore"), Key.key("copper_trapdoor"), Key.key("crafter"), Key.key("cut_copper"), Key.key("cut_copper_slab"), Key.key("cut_copper_stairs"), Key.key("deepslate_copper_ore"), Key.key("deepslate_iron_ore"), Key.key("deepslate_lapis_ore"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bulb"), Key.key("exposed_copper_chest"), Key.key("exposed_copper_grate"), Key.key("exposed_copper_trapdoor"), Key.key("exposed_cut_copper"), Key.key("exposed_cut_copper_slab"), Key.key("exposed_cut_copper_stairs"), Key.key("exposed_lightning_rod"), Key.key("iron_block"), Key.key("iron_ore"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("lightning_rod"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_copper_chest"), Key.key("oxidized_copper_grate"), Key.key("oxidized_copper_trapdoor"), Key.key("oxidized_cut_copper"), Key.key("oxidized_cut_copper_slab"), Key.key("oxidized_cut_copper_stairs"), Key.key("oxidized_lightning_rod"), Key.key("raw_copper_block"), Key.key("raw_iron_block"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_copper_chest"), Key.key("waxed_copper_grate"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_cut_copper"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_exposed_copper_grate"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_oxidized_copper_grate"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_copper_chest"), Key.key("waxed_weathered_copper_grate"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("waxed_weathered_cut_copper"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bulb"), Key.key("weathered_copper_chest"), Key.key("weathered_copper_grate"), Key.key("weathered_copper_trapdoor"), Key.key("weathered_cut_copper"), Key.key("weathered_cut_copper_slab"), Key.key("weathered_cut_copper_stairs"), Key.key("weathered_lightning_rod"))),
        Map.entry(Key.key("nether_carver_replaceables"), List.of(Key.key("andesite"), Key.key("basalt"), Key.key("blackstone"), Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("nether_wart_block"), Key.key("netherrack"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_sand"), Key.key("soul_soil"), Key.key("stone"), Key.key("tuff"), Key.key("warped_nylium"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("nylium"), List.of(Key.key("crimson_nylium"), Key.key("warped_nylium"))),
        Map.entry(Key.key("oak_logs"), List.of(Key.key("oak_log"), Key.key("oak_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"))),
        Map.entry(Key.key("occludes_vibration_signals"), List.of(Key.key("black_wool"), Key.key("blue_wool"), Key.key("brown_wool"), Key.key("cyan_wool"), Key.key("gray_wool"), Key.key("green_wool"), Key.key("light_blue_wool"), Key.key("light_gray_wool"), Key.key("lime_wool"), Key.key("magenta_wool"), Key.key("orange_wool"), Key.key("pink_wool"), Key.key("purple_wool"), Key.key("red_wool"), Key.key("white_wool"), Key.key("yellow_wool"))),
        Map.entry(Key.key("overrides_mushroom_light_requirement"), List.of(Key.key("crimson_nylium"), Key.key("mycelium"), Key.key("podzol"), Key.key("warped_nylium"))),
        Map.entry(Key.key("overworld_carver_replaceables"), List.of(Key.key("andesite"), Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("calcite"), Key.key("cinnabar"), Key.key("coarse_dirt"), Key.key("copper_ore"), Key.key("cyan_terracotta"), Key.key("deepslate"), Key.key("deepslate_copper_ore"), Key.key("deepslate_iron_ore"), Key.key("diorite"), Key.key("dirt"), Key.key("granite"), Key.key("grass_block"), Key.key("gravel"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("iron_ore"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("orange_terracotta"), Key.key("packed_ice"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("potent_sulfur"), Key.key("powder_snow"), Key.key("purple_terracotta"), Key.key("raw_copper_block"), Key.key("raw_iron_block"), Key.key("red_sand"), Key.key("red_sandstone"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("sandstone"), Key.key("snow"), Key.key("snow_block"), Key.key("stone"), Key.key("sulfur"), Key.key("suspicious_gravel"), Key.key("suspicious_sand"), Key.key("terracotta"), Key.key("tuff"), Key.key("water"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("overworld_natural_logs"), List.of(Key.key("acacia_log"), Key.key("birch_log"), Key.key("cherry_log"), Key.key("dark_oak_log"), Key.key("jungle_log"), Key.key("mangrove_log"), Key.key("oak_log"), Key.key("pale_oak_log"), Key.key("spruce_log"))),
        Map.entry(Key.key("pale_oak_logs"), List.of(Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"))),
        Map.entry(Key.key("parrots_spawnable_on"), List.of(Key.key("acacia_leaves"), Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("air"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_leaves"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_leaves"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("flowering_azalea_leaves"), Key.key("grass_block"), Key.key("jungle_leaves"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_leaves"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_leaves"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_leaves"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_leaves"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("piglin_repellents"), List.of(Key.key("soul_campfire"), Key.key("soul_fire"), Key.key("soul_lantern"), Key.key("soul_torch"), Key.key("soul_wall_torch"))),
        Map.entry(Key.key("planks"), List.of(Key.key("acacia_planks"), Key.key("bamboo_planks"), Key.key("birch_planks"), Key.key("cherry_planks"), Key.key("crimson_planks"), Key.key("dark_oak_planks"), Key.key("jungle_planks"), Key.key("mangrove_planks"), Key.key("oak_planks"), Key.key("pale_oak_planks"), Key.key("spruce_planks"), Key.key("warped_planks"))),
        Map.entry(Key.key("polar_bear_immune_to"), List.of(Key.key("powder_snow"))),
        Map.entry(Key.key("polar_bears_spawnable_on_alternate"), List.of(Key.key("ice"))),
        Map.entry(Key.key("portals"), List.of(Key.key("end_gateway"), Key.key("end_portal"), Key.key("nether_portal"))),
        Map.entry(Key.key("pressure_plates"), List.of(Key.key("acacia_pressure_plate"), Key.key("bamboo_pressure_plate"), Key.key("birch_pressure_plate"), Key.key("cherry_pressure_plate"), Key.key("crimson_pressure_plate"), Key.key("dark_oak_pressure_plate"), Key.key("heavy_weighted_pressure_plate"), Key.key("jungle_pressure_plate"), Key.key("light_weighted_pressure_plate"), Key.key("mangrove_pressure_plate"), Key.key("oak_pressure_plate"), Key.key("pale_oak_pressure_plate"), Key.key("polished_blackstone_pressure_plate"), Key.key("spruce_pressure_plate"), Key.key("stone_pressure_plate"), Key.key("warped_pressure_plate"))),
        Map.entry(Key.key("prevent_mob_spawning_inside"), List.of(Key.key("activator_rail"), Key.key("detector_rail"), Key.key("powered_rail"), Key.key("rail"))),
        Map.entry(Key.key("prevents_nearby_leaf_decay"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("rabbits_spawnable_on"), List.of(Key.key("grass_block"), Key.key("sand"), Key.key("snow"), Key.key("snow_block"))),
        Map.entry(Key.key("rails"), List.of(Key.key("activator_rail"), Key.key("detector_rail"), Key.key("powered_rail"), Key.key("rail"))),
        Map.entry(Key.key("redstone_ores"), List.of(Key.key("deepslate_redstone_ore"), Key.key("redstone_ore"))),
        Map.entry(Key.key("replaceable"), List.of(Key.key("air"), Key.key("bubble_column"), Key.key("bush"), Key.key("cave_air"), Key.key("crimson_roots"), Key.key("dead_bush"), Key.key("fern"), Key.key("fire"), Key.key("glow_lichen"), Key.key("hanging_roots"), Key.key("large_fern"), Key.key("lava"), Key.key("leaf_litter"), Key.key("light"), Key.key("nether_sprouts"), Key.key("resin_clump"), Key.key("seagrass"), Key.key("short_dry_grass"), Key.key("short_grass"), Key.key("snow"), Key.key("soul_fire"), Key.key("structure_void"), Key.key("tall_dry_grass"), Key.key("tall_grass"), Key.key("tall_seagrass"), Key.key("vine"), Key.key("void_air"), Key.key("warped_roots"), Key.key("water"))),
        Map.entry(Key.key("replaceable_by_mushrooms"), List.of(Key.key("acacia_leaves"), Key.key("allium"), Key.key("azalea_leaves"), Key.key("azure_bluet"), Key.key("birch_leaves"), Key.key("blue_orchid"), Key.key("brown_mushroom"), Key.key("brown_mushroom_block"), Key.key("bush"), Key.key("cherry_leaves"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("crimson_roots"), Key.key("dandelion"), Key.key("dark_oak_leaves"), Key.key("dead_bush"), Key.key("fern"), Key.key("firefly_bush"), Key.key("flowering_azalea_leaves"), Key.key("glow_lichen"), Key.key("golden_dandelion"), Key.key("hanging_roots"), Key.key("jungle_leaves"), Key.key("large_fern"), Key.key("leaf_litter"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_leaves"), Key.key("nether_sprouts"), Key.key("oak_leaves"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("pale_moss_carpet"), Key.key("pale_oak_leaves"), Key.key("peony"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_mushroom"), Key.key("red_mushroom_block"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("seagrass"), Key.key("short_dry_grass"), Key.key("short_grass"), Key.key("spruce_leaves"), Key.key("sunflower"), Key.key("tall_dry_grass"), Key.key("tall_grass"), Key.key("tall_seagrass"), Key.key("torchflower"), Key.key("vine"), Key.key("warped_roots"), Key.key("water"), Key.key("white_tulip"), Key.key("wither_rose"))),
        Map.entry(Key.key("replaceable_by_trees"), List.of(Key.key("acacia_leaves"), Key.key("allium"), Key.key("azalea_leaves"), Key.key("azure_bluet"), Key.key("birch_leaves"), Key.key("blue_orchid"), Key.key("bush"), Key.key("cherry_leaves"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("crimson_roots"), Key.key("dandelion"), Key.key("dark_oak_leaves"), Key.key("dead_bush"), Key.key("fern"), Key.key("firefly_bush"), Key.key("flowering_azalea_leaves"), Key.key("glow_lichen"), Key.key("golden_dandelion"), Key.key("hanging_roots"), Key.key("jungle_leaves"), Key.key("large_fern"), Key.key("leaf_litter"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_leaves"), Key.key("nether_sprouts"), Key.key("oak_leaves"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("pale_moss_carpet"), Key.key("pale_oak_leaves"), Key.key("peony"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("seagrass"), Key.key("short_dry_grass"), Key.key("short_grass"), Key.key("spruce_leaves"), Key.key("sunflower"), Key.key("tall_dry_grass"), Key.key("tall_grass"), Key.key("tall_seagrass"), Key.key("torchflower"), Key.key("vine"), Key.key("warped_roots"), Key.key("water"), Key.key("white_tulip"), Key.key("wither_rose"))),
        Map.entry(Key.key("sand"), List.of(Key.key("red_sand"), Key.key("sand"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("saplings"), List.of(Key.key("acacia_sapling"), Key.key("azalea"), Key.key("birch_sapling"), Key.key("cherry_sapling"), Key.key("dark_oak_sapling"), Key.key("flowering_azalea"), Key.key("jungle_sapling"), Key.key("mangrove_propagule"), Key.key("oak_sapling"), Key.key("pale_oak_sapling"), Key.key("spruce_sapling"))),
        Map.entry(Key.key("sculk_replaceable"), List.of(Key.key("andesite"), Key.key("basalt"), Key.key("black_terracotta"), Key.key("blackstone"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("calcite"), Key.key("cinnabar"), Key.key("clay"), Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("cyan_terracotta"), Key.key("deepslate"), Key.key("diorite"), Key.key("dirt"), Key.key("dripstone_block"), Key.key("end_stone"), Key.key("granite"), Key.key("grass_block"), Key.key("gravel"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("netherrack"), Key.key("orange_terracotta"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_sandstone"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("sandstone"), Key.key("smooth_basalt"), Key.key("soul_sand"), Key.key("soul_soil"), Key.key("stone"), Key.key("sulfur"), Key.key("terracotta"), Key.key("tuff"), Key.key("warped_nylium"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("sculk_replaceable_world_gen"), List.of(Key.key("andesite"), Key.key("basalt"), Key.key("black_terracotta"), Key.key("blackstone"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("calcite"), Key.key("cinnabar"), Key.key("clay"), Key.key("coarse_dirt"), Key.key("cobbled_deepslate"), Key.key("cracked_deepslate_bricks"), Key.key("cracked_deepslate_tiles"), Key.key("crimson_nylium"), Key.key("cyan_terracotta"), Key.key("deepslate"), Key.key("deepslate_bricks"), Key.key("deepslate_tiles"), Key.key("diorite"), Key.key("dirt"), Key.key("dripstone_block"), Key.key("end_stone"), Key.key("granite"), Key.key("grass_block"), Key.key("gravel"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("netherrack"), Key.key("orange_terracotta"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("polished_deepslate"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_sandstone"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("sandstone"), Key.key("smooth_basalt"), Key.key("soul_sand"), Key.key("soul_soil"), Key.key("stone"), Key.key("sulfur"), Key.key("terracotta"), Key.key("tuff"), Key.key("warped_nylium"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("shears_extreme_breaking_speed"), List.of(Key.key("acacia_leaves"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("cherry_leaves"), Key.key("dark_oak_leaves"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("mangrove_leaves"), Key.key("oak_leaves"), Key.key("pale_oak_leaves"), Key.key("spruce_leaves"))),
        Map.entry(Key.key("shears_major_breaking_speed"), List.of(Key.key("black_wool"), Key.key("blue_wool"), Key.key("brown_wool"), Key.key("cyan_wool"), Key.key("gray_wool"), Key.key("green_wool"), Key.key("light_blue_wool"), Key.key("light_gray_wool"), Key.key("lime_wool"), Key.key("magenta_wool"), Key.key("orange_wool"), Key.key("pink_wool"), Key.key("purple_wool"), Key.key("red_wool"), Key.key("white_wool"), Key.key("yellow_wool"))),
        Map.entry(Key.key("shears_minor_breaking_speed"), List.of(Key.key("glow_lichen"), Key.key("vine"))),
        Map.entry(Key.key("shulker_boxes"), List.of(Key.key("black_shulker_box"), Key.key("blue_shulker_box"), Key.key("brown_shulker_box"), Key.key("cyan_shulker_box"), Key.key("gray_shulker_box"), Key.key("green_shulker_box"), Key.key("light_blue_shulker_box"), Key.key("light_gray_shulker_box"), Key.key("lime_shulker_box"), Key.key("magenta_shulker_box"), Key.key("orange_shulker_box"), Key.key("pink_shulker_box"), Key.key("purple_shulker_box"), Key.key("red_shulker_box"), Key.key("shulker_box"), Key.key("white_shulker_box"), Key.key("yellow_shulker_box"))),
        Map.entry(Key.key("signs"), List.of(Key.key("acacia_sign"), Key.key("acacia_wall_sign"), Key.key("bamboo_sign"), Key.key("bamboo_wall_sign"), Key.key("birch_sign"), Key.key("birch_wall_sign"), Key.key("cherry_sign"), Key.key("cherry_wall_sign"), Key.key("crimson_sign"), Key.key("crimson_wall_sign"), Key.key("dark_oak_sign"), Key.key("dark_oak_wall_sign"), Key.key("jungle_sign"), Key.key("jungle_wall_sign"), Key.key("mangrove_sign"), Key.key("mangrove_wall_sign"), Key.key("oak_sign"), Key.key("oak_wall_sign"), Key.key("pale_oak_sign"), Key.key("pale_oak_wall_sign"), Key.key("spruce_sign"), Key.key("spruce_wall_sign"), Key.key("warped_sign"), Key.key("warped_wall_sign"))),
        Map.entry(Key.key("slabs"), List.of(Key.key("acacia_slab"), Key.key("andesite_slab"), Key.key("bamboo_mosaic_slab"), Key.key("bamboo_slab"), Key.key("birch_slab"), Key.key("blackstone_slab"), Key.key("brick_slab"), Key.key("cherry_slab"), Key.key("cinnabar_brick_slab"), Key.key("cinnabar_slab"), Key.key("cobbled_deepslate_slab"), Key.key("cobblestone_slab"), Key.key("crimson_slab"), Key.key("cut_copper_slab"), Key.key("cut_red_sandstone_slab"), Key.key("cut_sandstone_slab"), Key.key("dark_oak_slab"), Key.key("dark_prismarine_slab"), Key.key("deepslate_brick_slab"), Key.key("deepslate_tile_slab"), Key.key("diorite_slab"), Key.key("end_stone_brick_slab"), Key.key("exposed_cut_copper_slab"), Key.key("granite_slab"), Key.key("jungle_slab"), Key.key("mangrove_slab"), Key.key("mossy_cobblestone_slab"), Key.key("mossy_stone_brick_slab"), Key.key("mud_brick_slab"), Key.key("nether_brick_slab"), Key.key("oak_slab"), Key.key("oxidized_cut_copper_slab"), Key.key("pale_oak_slab"), Key.key("petrified_oak_slab"), Key.key("polished_andesite_slab"), Key.key("polished_blackstone_brick_slab"), Key.key("polished_blackstone_slab"), Key.key("polished_cinnabar_slab"), Key.key("polished_deepslate_slab"), Key.key("polished_diorite_slab"), Key.key("polished_granite_slab"), Key.key("polished_sulfur_slab"), Key.key("polished_tuff_slab"), Key.key("prismarine_brick_slab"), Key.key("prismarine_slab"), Key.key("purpur_slab"), Key.key("quartz_slab"), Key.key("red_nether_brick_slab"), Key.key("red_sandstone_slab"), Key.key("resin_brick_slab"), Key.key("sandstone_slab"), Key.key("smooth_quartz_slab"), Key.key("smooth_red_sandstone_slab"), Key.key("smooth_sandstone_slab"), Key.key("smooth_stone_slab"), Key.key("spruce_slab"), Key.key("stone_brick_slab"), Key.key("stone_slab"), Key.key("sulfur_brick_slab"), Key.key("sulfur_slab"), Key.key("tuff_brick_slab"), Key.key("tuff_slab"), Key.key("warped_slab"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("weathered_cut_copper_slab"))),
        Map.entry(Key.key("small_flowers"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("dandelion"), Key.key("golden_dandelion"), Key.key("lily_of_the_valley"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("pink_tulip"), Key.key("poppy"), Key.key("red_tulip"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wither_rose"))),
        Map.entry(Key.key("smelts_to_glass"), List.of(Key.key("red_sand"), Key.key("sand"))),
        Map.entry(Key.key("snaps_goat_horn"), List.of(Key.key("acacia_log"), Key.key("birch_log"), Key.key("cherry_log"), Key.key("coal_ore"), Key.key("copper_ore"), Key.key("dark_oak_log"), Key.key("emerald_ore"), Key.key("iron_ore"), Key.key("jungle_log"), Key.key("mangrove_log"), Key.key("oak_log"), Key.key("packed_ice"), Key.key("pale_oak_log"), Key.key("spruce_log"), Key.key("stone"))),
        Map.entry(Key.key("sniffer_diggable_block"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("sniffer_egg_hatch_boost"), List.of(Key.key("moss_block"))),
        Map.entry(Key.key("snow"), List.of(Key.key("powder_snow"), Key.key("snow"), Key.key("snow_block"))),
        Map.entry(Key.key("snow_golem_immune_to"), List.of(Key.key("powder_snow"))),
        Map.entry(Key.key("soul_fire_base_blocks"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("soul_speed_blocks"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("speleothems"), List.of(Key.key("pointed_dripstone"), Key.key("sulfur_spike"))),
        Map.entry(Key.key("spruce_logs"), List.of(Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"))),
        Map.entry(Key.key("stairs"), List.of(Key.key("acacia_stairs"), Key.key("andesite_stairs"), Key.key("bamboo_mosaic_stairs"), Key.key("bamboo_stairs"), Key.key("birch_stairs"), Key.key("blackstone_stairs"), Key.key("brick_stairs"), Key.key("cherry_stairs"), Key.key("cinnabar_brick_stairs"), Key.key("cinnabar_stairs"), Key.key("cobbled_deepslate_stairs"), Key.key("cobblestone_stairs"), Key.key("crimson_stairs"), Key.key("cut_copper_stairs"), Key.key("dark_oak_stairs"), Key.key("dark_prismarine_stairs"), Key.key("deepslate_brick_stairs"), Key.key("deepslate_tile_stairs"), Key.key("diorite_stairs"), Key.key("end_stone_brick_stairs"), Key.key("exposed_cut_copper_stairs"), Key.key("granite_stairs"), Key.key("jungle_stairs"), Key.key("mangrove_stairs"), Key.key("mossy_cobblestone_stairs"), Key.key("mossy_stone_brick_stairs"), Key.key("mud_brick_stairs"), Key.key("nether_brick_stairs"), Key.key("oak_stairs"), Key.key("oxidized_cut_copper_stairs"), Key.key("pale_oak_stairs"), Key.key("polished_andesite_stairs"), Key.key("polished_blackstone_brick_stairs"), Key.key("polished_blackstone_stairs"), Key.key("polished_cinnabar_stairs"), Key.key("polished_deepslate_stairs"), Key.key("polished_diorite_stairs"), Key.key("polished_granite_stairs"), Key.key("polished_sulfur_stairs"), Key.key("polished_tuff_stairs"), Key.key("prismarine_brick_stairs"), Key.key("prismarine_stairs"), Key.key("purpur_stairs"), Key.key("quartz_stairs"), Key.key("red_nether_brick_stairs"), Key.key("red_sandstone_stairs"), Key.key("resin_brick_stairs"), Key.key("sandstone_stairs"), Key.key("smooth_quartz_stairs"), Key.key("smooth_red_sandstone_stairs"), Key.key("smooth_sandstone_stairs"), Key.key("spruce_stairs"), Key.key("stone_brick_stairs"), Key.key("stone_stairs"), Key.key("sulfur_brick_stairs"), Key.key("sulfur_stairs"), Key.key("tuff_brick_stairs"), Key.key("tuff_stairs"), Key.key("warped_stairs"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("weathered_cut_copper_stairs"))),
        Map.entry(Key.key("standing_signs"), List.of(Key.key("acacia_sign"), Key.key("bamboo_sign"), Key.key("birch_sign"), Key.key("cherry_sign"), Key.key("crimson_sign"), Key.key("dark_oak_sign"), Key.key("jungle_sign"), Key.key("mangrove_sign"), Key.key("oak_sign"), Key.key("pale_oak_sign"), Key.key("spruce_sign"), Key.key("warped_sign"))),
        Map.entry(Key.key("stone_bricks"), List.of(Key.key("chiseled_stone_bricks"), Key.key("cracked_stone_bricks"), Key.key("mossy_stone_bricks"), Key.key("stone_bricks"))),
        Map.entry(Key.key("stone_buttons"), List.of(Key.key("polished_blackstone_button"), Key.key("stone_button"))),
        Map.entry(Key.key("stone_ore_replaceables"), List.of(Key.key("andesite"), Key.key("diorite"), Key.key("granite"), Key.key("stone"))),
        Map.entry(Key.key("stone_pressure_plates"), List.of(Key.key("polished_blackstone_pressure_plate"), Key.key("stone_pressure_plate"))),
        Map.entry(Key.key("stray_immune_to"), List.of(Key.key("powder_snow"))),
        Map.entry(Key.key("strider_warm_blocks"), List.of(Key.key("lava"))),
        Map.entry(Key.key("substrate_overworld"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("sulfur_spike_replaceable_blocks"), List.of(Key.key("cinnabar"), Key.key("sulfur"))),
        Map.entry(Key.key("support_override_cactus_flower"), List.of(Key.key("cactus"), Key.key("farmland"))),
        Map.entry(Key.key("support_override_snow_layer"), List.of(Key.key("honey_block"), Key.key("mud"), Key.key("soul_sand"))),
        Map.entry(Key.key("supports_azalea"), List.of(Key.key("clay"), Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_bamboo"), List.of(Key.key("bamboo"), Key.key("bamboo_sapling"), Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("gravel"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("red_sand"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("suspicious_gravel"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("supports_big_dripleaf"), List.of(Key.key("clay"), Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_cactus"), List.of(Key.key("red_sand"), Key.key("sand"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("supports_chorus_flower"), List.of(Key.key("end_stone"))),
        Map.entry(Key.key("supports_chorus_plant"), List.of(Key.key("end_stone"))),
        Map.entry(Key.key("supports_cocoa"), List.of(Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"))),
        Map.entry(Key.key("supports_crimson_fungus"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_soil"), Key.key("warped_nylium"))),
        Map.entry(Key.key("supports_crimson_roots"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_soil"), Key.key("warped_nylium"))),
        Map.entry(Key.key("supports_crops"), List.of(Key.key("farmland"))),
        Map.entry(Key.key("supports_dry_vegetation"), List.of(Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("coarse_dirt"), Key.key("cyan_terracotta"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("orange_terracotta"), Key.key("pale_moss_block"), Key.key("pink_terracotta"), Key.key("podzol"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_terracotta"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("suspicious_sand"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("supports_frogspawn"), List.of()),
        Map.entry(Key.key("supports_hanging_mangrove_propagule"), List.of(Key.key("mangrove_leaves"))),
        Map.entry(Key.key("supports_lily_pad"), List.of(Key.key("frosted_ice"), Key.key("ice"))),
        Map.entry(Key.key("supports_mangrove_propagule"), List.of(Key.key("clay"), Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_melon_stem"), List.of(Key.key("farmland"))),
        Map.entry(Key.key("supports_melon_stem_fruit"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_nether_sprouts"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_soil"), Key.key("warped_nylium"))),
        Map.entry(Key.key("supports_nether_wart"), List.of(Key.key("soul_sand"))),
        Map.entry(Key.key("supports_pumpkin_stem"), List.of(Key.key("farmland"))),
        Map.entry(Key.key("supports_pumpkin_stem_fruit"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_small_dripleaf"), List.of(Key.key("clay"), Key.key("moss_block"))),
        Map.entry(Key.key("supports_stem_crops"), List.of(Key.key("farmland"))),
        Map.entry(Key.key("supports_stem_fruit"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_sugar_cane"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("red_sand"), Key.key("rooted_dirt"), Key.key("sand"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("supports_sugar_cane_adjacently"), List.of(Key.key("frosted_ice"))),
        Map.entry(Key.key("supports_vegetation"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("supports_warped_fungus"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_soil"), Key.key("warped_nylium"))),
        Map.entry(Key.key("supports_warped_roots"), List.of(Key.key("coarse_dirt"), Key.key("crimson_nylium"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_soil"), Key.key("warped_nylium"))),
        Map.entry(Key.key("supports_wither_rose"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("farmland"), Key.key("grass_block"), Key.key("moss_block"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("mycelium"), Key.key("netherrack"), Key.key("pale_moss_block"), Key.key("podzol"), Key.key("rooted_dirt"), Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("suppresses_bounce"), List.of(Key.key("honey_block"))),
        Map.entry(Key.key("sword_efficient"), List.of(Key.key("acacia_leaves"), Key.key("azalea_leaves"), Key.key("big_dripleaf"), Key.key("big_dripleaf_stem"), Key.key("birch_leaves"), Key.key("carved_pumpkin"), Key.key("cherry_leaves"), Key.key("chorus_flower"), Key.key("chorus_plant"), Key.key("cocoa"), Key.key("dark_oak_leaves"), Key.key("flowering_azalea_leaves"), Key.key("glow_lichen"), Key.key("jack_o_lantern"), Key.key("jungle_leaves"), Key.key("mangrove_leaves"), Key.key("melon"), Key.key("oak_leaves"), Key.key("pale_oak_leaves"), Key.key("pumpkin"), Key.key("spruce_leaves"), Key.key("vine"))),
        Map.entry(Key.key("sword_instantly_mines"), List.of(Key.key("bamboo"), Key.key("bamboo_sapling"))),
        Map.entry(Key.key("terracotta"), List.of(Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("cyan_terracotta"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("orange_terracotta"), Key.key("pink_terracotta"), Key.key("purple_terracotta"), Key.key("red_terracotta"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("trail_ruins_replaceable"), List.of(Key.key("gravel"))),
        Map.entry(Key.key("trapdoors"), List.of(Key.key("acacia_trapdoor"), Key.key("bamboo_trapdoor"), Key.key("birch_trapdoor"), Key.key("cherry_trapdoor"), Key.key("copper_trapdoor"), Key.key("crimson_trapdoor"), Key.key("dark_oak_trapdoor"), Key.key("exposed_copper_trapdoor"), Key.key("iron_trapdoor"), Key.key("jungle_trapdoor"), Key.key("mangrove_trapdoor"), Key.key("oak_trapdoor"), Key.key("oxidized_copper_trapdoor"), Key.key("pale_oak_trapdoor"), Key.key("spruce_trapdoor"), Key.key("warped_trapdoor"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("weathered_copper_trapdoor"))),
        Map.entry(Key.key("triggers_ambient_desert_dry_vegetation_block_sounds"), List.of(Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("cyan_terracotta"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("orange_terracotta"), Key.key("pink_terracotta"), Key.key("purple_terracotta"), Key.key("red_sand"), Key.key("red_terracotta"), Key.key("sand"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("triggers_ambient_desert_sand_block_sounds"), List.of(Key.key("red_sand"), Key.key("sand"))),
        Map.entry(Key.key("triggers_ambient_dried_ghast_block_sounds"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("underwater_bonemeals"), List.of(Key.key("brain_coral"), Key.key("brain_coral_fan"), Key.key("brain_coral_wall_fan"), Key.key("bubble_coral"), Key.key("bubble_coral_fan"), Key.key("bubble_coral_wall_fan"), Key.key("fire_coral"), Key.key("fire_coral_fan"), Key.key("fire_coral_wall_fan"), Key.key("horn_coral"), Key.key("horn_coral_fan"), Key.key("horn_coral_wall_fan"), Key.key("seagrass"), Key.key("tube_coral"), Key.key("tube_coral_fan"), Key.key("tube_coral_wall_fan"))),
        Map.entry(Key.key("unstable_bottom_center"), List.of(Key.key("acacia_fence_gate"), Key.key("bamboo_fence_gate"), Key.key("birch_fence_gate"), Key.key("cherry_fence_gate"), Key.key("crimson_fence_gate"), Key.key("dark_oak_fence_gate"), Key.key("jungle_fence_gate"), Key.key("mangrove_fence_gate"), Key.key("oak_fence_gate"), Key.key("pale_oak_fence_gate"), Key.key("spruce_fence_gate"), Key.key("warped_fence_gate"))),
        Map.entry(Key.key("valid_spawn"), List.of(Key.key("grass_block"), Key.key("podzol"))),
        Map.entry(Key.key("vibration_resonators"), List.of(Key.key("amethyst_block"))),
        Map.entry(Key.key("wall_corals"), List.of(Key.key("brain_coral_wall_fan"), Key.key("bubble_coral_wall_fan"), Key.key("fire_coral_wall_fan"), Key.key("horn_coral_wall_fan"), Key.key("tube_coral_wall_fan"))),
        Map.entry(Key.key("wall_hanging_signs"), List.of(Key.key("acacia_wall_hanging_sign"), Key.key("bamboo_wall_hanging_sign"), Key.key("birch_wall_hanging_sign"), Key.key("cherry_wall_hanging_sign"), Key.key("crimson_wall_hanging_sign"), Key.key("dark_oak_wall_hanging_sign"), Key.key("jungle_wall_hanging_sign"), Key.key("mangrove_wall_hanging_sign"), Key.key("oak_wall_hanging_sign"), Key.key("pale_oak_wall_hanging_sign"), Key.key("spruce_wall_hanging_sign"), Key.key("warped_wall_hanging_sign"))),
        Map.entry(Key.key("wall_post_override"), List.of(Key.key("acacia_pressure_plate"), Key.key("acacia_sign"), Key.key("acacia_wall_sign"), Key.key("bamboo_pressure_plate"), Key.key("bamboo_sign"), Key.key("bamboo_wall_sign"), Key.key("birch_pressure_plate"), Key.key("birch_sign"), Key.key("birch_wall_sign"), Key.key("black_banner"), Key.key("black_wall_banner"), Key.key("blue_banner"), Key.key("blue_wall_banner"), Key.key("brown_banner"), Key.key("brown_wall_banner"), Key.key("cactus_flower"), Key.key("cherry_pressure_plate"), Key.key("cherry_sign"), Key.key("cherry_wall_sign"), Key.key("copper_torch"), Key.key("crimson_pressure_plate"), Key.key("crimson_sign"), Key.key("crimson_wall_sign"), Key.key("cyan_banner"), Key.key("cyan_wall_banner"), Key.key("dark_oak_pressure_plate"), Key.key("dark_oak_sign"), Key.key("dark_oak_wall_sign"), Key.key("gray_banner"), Key.key("gray_wall_banner"), Key.key("green_banner"), Key.key("green_wall_banner"), Key.key("heavy_weighted_pressure_plate"), Key.key("jungle_pressure_plate"), Key.key("jungle_sign"), Key.key("jungle_wall_sign"), Key.key("light_blue_banner"), Key.key("light_blue_wall_banner"), Key.key("light_gray_banner"), Key.key("light_gray_wall_banner"), Key.key("light_weighted_pressure_plate"), Key.key("lime_banner"), Key.key("lime_wall_banner"), Key.key("magenta_banner"), Key.key("magenta_wall_banner"), Key.key("mangrove_pressure_plate"), Key.key("mangrove_sign"), Key.key("mangrove_wall_sign"), Key.key("oak_pressure_plate"), Key.key("oak_sign"), Key.key("oak_wall_sign"), Key.key("orange_banner"), Key.key("orange_wall_banner"), Key.key("pale_oak_pressure_plate"), Key.key("pale_oak_sign"), Key.key("pale_oak_wall_sign"), Key.key("pink_banner"), Key.key("pink_wall_banner"), Key.key("polished_blackstone_pressure_plate"), Key.key("purple_banner"), Key.key("purple_wall_banner"), Key.key("red_banner"), Key.key("red_wall_banner"), Key.key("redstone_torch"), Key.key("soul_torch"), Key.key("spruce_pressure_plate"), Key.key("spruce_sign"), Key.key("spruce_wall_sign"), Key.key("stone_pressure_plate"), Key.key("torch"), Key.key("tripwire"), Key.key("warped_pressure_plate"), Key.key("warped_sign"), Key.key("warped_wall_sign"), Key.key("white_banner"), Key.key("white_wall_banner"), Key.key("yellow_banner"), Key.key("yellow_wall_banner"))),
        Map.entry(Key.key("wall_signs"), List.of(Key.key("acacia_wall_sign"), Key.key("bamboo_wall_sign"), Key.key("birch_wall_sign"), Key.key("cherry_wall_sign"), Key.key("crimson_wall_sign"), Key.key("dark_oak_wall_sign"), Key.key("jungle_wall_sign"), Key.key("mangrove_wall_sign"), Key.key("oak_wall_sign"), Key.key("pale_oak_wall_sign"), Key.key("spruce_wall_sign"), Key.key("warped_wall_sign"))),
        Map.entry(Key.key("walls"), List.of(Key.key("andesite_wall"), Key.key("blackstone_wall"), Key.key("brick_wall"), Key.key("cinnabar_brick_wall"), Key.key("cinnabar_wall"), Key.key("cobbled_deepslate_wall"), Key.key("cobblestone_wall"), Key.key("deepslate_brick_wall"), Key.key("deepslate_tile_wall"), Key.key("diorite_wall"), Key.key("end_stone_brick_wall"), Key.key("granite_wall"), Key.key("mossy_cobblestone_wall"), Key.key("mossy_stone_brick_wall"), Key.key("mud_brick_wall"), Key.key("nether_brick_wall"), Key.key("polished_blackstone_brick_wall"), Key.key("polished_blackstone_wall"), Key.key("polished_cinnabar_wall"), Key.key("polished_deepslate_wall"), Key.key("polished_sulfur_wall"), Key.key("polished_tuff_wall"), Key.key("prismarine_wall"), Key.key("red_nether_brick_wall"), Key.key("red_sandstone_wall"), Key.key("resin_brick_wall"), Key.key("sandstone_wall"), Key.key("stone_brick_wall"), Key.key("sulfur_brick_wall"), Key.key("sulfur_wall"), Key.key("tuff_brick_wall"), Key.key("tuff_wall"))),
        Map.entry(Key.key("warped_stems"), List.of(Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("wart_blocks"), List.of(Key.key("nether_wart_block"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("wither_immune"), List.of(Key.key("barrier"), Key.key("bedrock"), Key.key("chain_command_block"), Key.key("command_block"), Key.key("end_gateway"), Key.key("end_portal"), Key.key("end_portal_frame"), Key.key("jigsaw"), Key.key("light"), Key.key("moving_piston"), Key.key("reinforced_deepslate"), Key.key("repeating_command_block"), Key.key("structure_block"), Key.key("test_block"), Key.key("test_instance_block"))),
        Map.entry(Key.key("wither_immune_to"), List.of(Key.key("wither_rose"))),
        Map.entry(Key.key("wither_skeleton_immune_to"), List.of(Key.key("wither_rose"))),
        Map.entry(Key.key("wither_summon_base_blocks"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("wolves_spawnable_on"), List.of(Key.key("coarse_dirt"), Key.key("grass_block"), Key.key("podzol"), Key.key("snow"), Key.key("snow_block"))),
        Map.entry(Key.key("wooden_buttons"), List.of(Key.key("acacia_button"), Key.key("bamboo_button"), Key.key("birch_button"), Key.key("cherry_button"), Key.key("crimson_button"), Key.key("dark_oak_button"), Key.key("jungle_button"), Key.key("mangrove_button"), Key.key("oak_button"), Key.key("pale_oak_button"), Key.key("spruce_button"), Key.key("warped_button"))),
        Map.entry(Key.key("wooden_doors"), List.of(Key.key("acacia_door"), Key.key("bamboo_door"), Key.key("birch_door"), Key.key("cherry_door"), Key.key("crimson_door"), Key.key("dark_oak_door"), Key.key("jungle_door"), Key.key("mangrove_door"), Key.key("oak_door"), Key.key("pale_oak_door"), Key.key("spruce_door"), Key.key("warped_door"))),
        Map.entry(Key.key("wooden_fences"), List.of(Key.key("acacia_fence"), Key.key("bamboo_fence"), Key.key("birch_fence"), Key.key("cherry_fence"), Key.key("crimson_fence"), Key.key("dark_oak_fence"), Key.key("jungle_fence"), Key.key("mangrove_fence"), Key.key("oak_fence"), Key.key("pale_oak_fence"), Key.key("spruce_fence"), Key.key("warped_fence"))),
        Map.entry(Key.key("wooden_pressure_plates"), List.of(Key.key("acacia_pressure_plate"), Key.key("bamboo_pressure_plate"), Key.key("birch_pressure_plate"), Key.key("cherry_pressure_plate"), Key.key("crimson_pressure_plate"), Key.key("dark_oak_pressure_plate"), Key.key("jungle_pressure_plate"), Key.key("mangrove_pressure_plate"), Key.key("oak_pressure_plate"), Key.key("pale_oak_pressure_plate"), Key.key("spruce_pressure_plate"), Key.key("warped_pressure_plate"))),
        Map.entry(Key.key("wooden_shelves"), List.of(Key.key("acacia_shelf"), Key.key("bamboo_shelf"), Key.key("birch_shelf"), Key.key("cherry_shelf"), Key.key("crimson_shelf"), Key.key("dark_oak_shelf"), Key.key("jungle_shelf"), Key.key("mangrove_shelf"), Key.key("oak_shelf"), Key.key("pale_oak_shelf"), Key.key("spruce_shelf"), Key.key("warped_shelf"))),
        Map.entry(Key.key("wooden_slabs"), List.of(Key.key("acacia_slab"), Key.key("bamboo_slab"), Key.key("birch_slab"), Key.key("cherry_slab"), Key.key("crimson_slab"), Key.key("dark_oak_slab"), Key.key("jungle_slab"), Key.key("mangrove_slab"), Key.key("oak_slab"), Key.key("pale_oak_slab"), Key.key("spruce_slab"), Key.key("warped_slab"))),
        Map.entry(Key.key("wooden_stairs"), List.of(Key.key("acacia_stairs"), Key.key("bamboo_stairs"), Key.key("birch_stairs"), Key.key("cherry_stairs"), Key.key("crimson_stairs"), Key.key("dark_oak_stairs"), Key.key("jungle_stairs"), Key.key("mangrove_stairs"), Key.key("oak_stairs"), Key.key("pale_oak_stairs"), Key.key("spruce_stairs"), Key.key("warped_stairs"))),
        Map.entry(Key.key("wooden_trapdoors"), List.of(Key.key("acacia_trapdoor"), Key.key("bamboo_trapdoor"), Key.key("birch_trapdoor"), Key.key("cherry_trapdoor"), Key.key("crimson_trapdoor"), Key.key("dark_oak_trapdoor"), Key.key("jungle_trapdoor"), Key.key("mangrove_trapdoor"), Key.key("oak_trapdoor"), Key.key("pale_oak_trapdoor"), Key.key("spruce_trapdoor"), Key.key("warped_trapdoor"))),
        Map.entry(Key.key("wool"), List.of(Key.key("black_wool"), Key.key("blue_wool"), Key.key("brown_wool"), Key.key("cyan_wool"), Key.key("gray_wool"), Key.key("green_wool"), Key.key("light_blue_wool"), Key.key("light_gray_wool"), Key.key("lime_wool"), Key.key("magenta_wool"), Key.key("orange_wool"), Key.key("pink_wool"), Key.key("purple_wool"), Key.key("red_wool"), Key.key("white_wool"), Key.key("yellow_wool"))),
        Map.entry(Key.key("wool_carpets"), List.of(Key.key("black_carpet"), Key.key("blue_carpet"), Key.key("brown_carpet"), Key.key("cyan_carpet"), Key.key("gray_carpet"), Key.key("green_carpet"), Key.key("light_blue_carpet"), Key.key("light_gray_carpet"), Key.key("lime_carpet"), Key.key("magenta_carpet"), Key.key("orange_carpet"), Key.key("pink_carpet"), Key.key("purple_carpet"), Key.key("red_carpet"), Key.key("white_carpet"), Key.key("yellow_carpet")))
    );

    private BlockTypeKeys() {
        throw new UnsupportedOperationException("BlockTypeKeys cannot be instantiated.");
    }

    private static TypedKey<BlockType> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.BLOCK, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<BlockType>> values() {
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
