package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Item;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:item} registry.
 */
public final class ItemKeys {
    /**
     * Key for {@code minecraft:acacia_boat}.
     */
    public static final TypedKey<Item> ACACIA_BOAT = create("acacia_boat");

    /**
     * Key for {@code minecraft:acacia_button}.
     */
    public static final TypedKey<Item> ACACIA_BUTTON = create("acacia_button");

    /**
     * Key for {@code minecraft:acacia_chest_boat}.
     */
    public static final TypedKey<Item> ACACIA_CHEST_BOAT = create("acacia_chest_boat");

    /**
     * Key for {@code minecraft:acacia_door}.
     */
    public static final TypedKey<Item> ACACIA_DOOR = create("acacia_door");

    /**
     * Key for {@code minecraft:acacia_fence}.
     */
    public static final TypedKey<Item> ACACIA_FENCE = create("acacia_fence");

    /**
     * Key for {@code minecraft:acacia_fence_gate}.
     */
    public static final TypedKey<Item> ACACIA_FENCE_GATE = create("acacia_fence_gate");

    /**
     * Key for {@code minecraft:acacia_hanging_sign}.
     */
    public static final TypedKey<Item> ACACIA_HANGING_SIGN = create("acacia_hanging_sign");

    /**
     * Key for {@code minecraft:acacia_leaves}.
     */
    public static final TypedKey<Item> ACACIA_LEAVES = create("acacia_leaves");

    /**
     * Key for {@code minecraft:acacia_log}.
     */
    public static final TypedKey<Item> ACACIA_LOG = create("acacia_log");

    /**
     * Key for {@code minecraft:acacia_planks}.
     */
    public static final TypedKey<Item> ACACIA_PLANKS = create("acacia_planks");

    /**
     * Key for {@code minecraft:acacia_pressure_plate}.
     */
    public static final TypedKey<Item> ACACIA_PRESSURE_PLATE = create("acacia_pressure_plate");

    /**
     * Key for {@code minecraft:acacia_sapling}.
     */
    public static final TypedKey<Item> ACACIA_SAPLING = create("acacia_sapling");

    /**
     * Key for {@code minecraft:acacia_shelf}.
     */
    public static final TypedKey<Item> ACACIA_SHELF = create("acacia_shelf");

    /**
     * Key for {@code minecraft:acacia_sign}.
     */
    public static final TypedKey<Item> ACACIA_SIGN = create("acacia_sign");

    /**
     * Key for {@code minecraft:acacia_slab}.
     */
    public static final TypedKey<Item> ACACIA_SLAB = create("acacia_slab");

    /**
     * Key for {@code minecraft:acacia_stairs}.
     */
    public static final TypedKey<Item> ACACIA_STAIRS = create("acacia_stairs");

    /**
     * Key for {@code minecraft:acacia_trapdoor}.
     */
    public static final TypedKey<Item> ACACIA_TRAPDOOR = create("acacia_trapdoor");

    /**
     * Key for {@code minecraft:acacia_wood}.
     */
    public static final TypedKey<Item> ACACIA_WOOD = create("acacia_wood");

    /**
     * Key for {@code minecraft:activator_rail}.
     */
    public static final TypedKey<Item> ACTIVATOR_RAIL = create("activator_rail");

    /**
     * Key for {@code minecraft:air}.
     */
    public static final TypedKey<Item> AIR = create("air");

    /**
     * Key for {@code minecraft:allay_spawn_egg}.
     */
    public static final TypedKey<Item> ALLAY_SPAWN_EGG = create("allay_spawn_egg");

    /**
     * Key for {@code minecraft:allium}.
     */
    public static final TypedKey<Item> ALLIUM = create("allium");

    /**
     * Key for {@code minecraft:amethyst_block}.
     */
    public static final TypedKey<Item> AMETHYST_BLOCK = create("amethyst_block");

    /**
     * Key for {@code minecraft:amethyst_cluster}.
     */
    public static final TypedKey<Item> AMETHYST_CLUSTER = create("amethyst_cluster");

    /**
     * Key for {@code minecraft:amethyst_shard}.
     */
    public static final TypedKey<Item> AMETHYST_SHARD = create("amethyst_shard");

    /**
     * Key for {@code minecraft:ancient_debris}.
     */
    public static final TypedKey<Item> ANCIENT_DEBRIS = create("ancient_debris");

    /**
     * Key for {@code minecraft:andesite}.
     */
    public static final TypedKey<Item> ANDESITE = create("andesite");

    /**
     * Key for {@code minecraft:andesite_slab}.
     */
    public static final TypedKey<Item> ANDESITE_SLAB = create("andesite_slab");

    /**
     * Key for {@code minecraft:andesite_stairs}.
     */
    public static final TypedKey<Item> ANDESITE_STAIRS = create("andesite_stairs");

    /**
     * Key for {@code minecraft:andesite_wall}.
     */
    public static final TypedKey<Item> ANDESITE_WALL = create("andesite_wall");

    /**
     * Key for {@code minecraft:angler_pottery_sherd}.
     */
    public static final TypedKey<Item> ANGLER_POTTERY_SHERD = create("angler_pottery_sherd");

    /**
     * Key for {@code minecraft:anvil}.
     */
    public static final TypedKey<Item> ANVIL = create("anvil");

    /**
     * Key for {@code minecraft:apple}.
     */
    public static final TypedKey<Item> APPLE = create("apple");

    /**
     * Key for {@code minecraft:archer_pottery_sherd}.
     */
    public static final TypedKey<Item> ARCHER_POTTERY_SHERD = create("archer_pottery_sherd");

    /**
     * Key for {@code minecraft:armadillo_scute}.
     */
    public static final TypedKey<Item> ARMADILLO_SCUTE = create("armadillo_scute");

    /**
     * Key for {@code minecraft:armadillo_spawn_egg}.
     */
    public static final TypedKey<Item> ARMADILLO_SPAWN_EGG = create("armadillo_spawn_egg");

    /**
     * Key for {@code minecraft:armor_stand}.
     */
    public static final TypedKey<Item> ARMOR_STAND = create("armor_stand");

    /**
     * Key for {@code minecraft:arms_up_pottery_sherd}.
     */
    public static final TypedKey<Item> ARMS_UP_POTTERY_SHERD = create("arms_up_pottery_sherd");

    /**
     * Key for {@code minecraft:arrow}.
     */
    public static final TypedKey<Item> ARROW = create("arrow");

    /**
     * Key for {@code minecraft:axolotl_bucket}.
     */
    public static final TypedKey<Item> AXOLOTL_BUCKET = create("axolotl_bucket");

    /**
     * Key for {@code minecraft:axolotl_spawn_egg}.
     */
    public static final TypedKey<Item> AXOLOTL_SPAWN_EGG = create("axolotl_spawn_egg");

    /**
     * Key for {@code minecraft:azalea}.
     */
    public static final TypedKey<Item> AZALEA = create("azalea");

    /**
     * Key for {@code minecraft:azalea_leaves}.
     */
    public static final TypedKey<Item> AZALEA_LEAVES = create("azalea_leaves");

    /**
     * Key for {@code minecraft:azure_bluet}.
     */
    public static final TypedKey<Item> AZURE_BLUET = create("azure_bluet");

    /**
     * Key for {@code minecraft:baked_potato}.
     */
    public static final TypedKey<Item> BAKED_POTATO = create("baked_potato");

    /**
     * Key for {@code minecraft:bamboo}.
     */
    public static final TypedKey<Item> BAMBOO = create("bamboo");

    /**
     * Key for {@code minecraft:bamboo_block}.
     */
    public static final TypedKey<Item> BAMBOO_BLOCK = create("bamboo_block");

    /**
     * Key for {@code minecraft:bamboo_button}.
     */
    public static final TypedKey<Item> BAMBOO_BUTTON = create("bamboo_button");

    /**
     * Key for {@code minecraft:bamboo_chest_raft}.
     */
    public static final TypedKey<Item> BAMBOO_CHEST_RAFT = create("bamboo_chest_raft");

    /**
     * Key for {@code minecraft:bamboo_door}.
     */
    public static final TypedKey<Item> BAMBOO_DOOR = create("bamboo_door");

    /**
     * Key for {@code minecraft:bamboo_fence}.
     */
    public static final TypedKey<Item> BAMBOO_FENCE = create("bamboo_fence");

    /**
     * Key for {@code minecraft:bamboo_fence_gate}.
     */
    public static final TypedKey<Item> BAMBOO_FENCE_GATE = create("bamboo_fence_gate");

    /**
     * Key for {@code minecraft:bamboo_hanging_sign}.
     */
    public static final TypedKey<Item> BAMBOO_HANGING_SIGN = create("bamboo_hanging_sign");

    /**
     * Key for {@code minecraft:bamboo_mosaic}.
     */
    public static final TypedKey<Item> BAMBOO_MOSAIC = create("bamboo_mosaic");

    /**
     * Key for {@code minecraft:bamboo_mosaic_slab}.
     */
    public static final TypedKey<Item> BAMBOO_MOSAIC_SLAB = create("bamboo_mosaic_slab");

    /**
     * Key for {@code minecraft:bamboo_mosaic_stairs}.
     */
    public static final TypedKey<Item> BAMBOO_MOSAIC_STAIRS = create("bamboo_mosaic_stairs");

    /**
     * Key for {@code minecraft:bamboo_planks}.
     */
    public static final TypedKey<Item> BAMBOO_PLANKS = create("bamboo_planks");

    /**
     * Key for {@code minecraft:bamboo_pressure_plate}.
     */
    public static final TypedKey<Item> BAMBOO_PRESSURE_PLATE = create("bamboo_pressure_plate");

    /**
     * Key for {@code minecraft:bamboo_raft}.
     */
    public static final TypedKey<Item> BAMBOO_RAFT = create("bamboo_raft");

    /**
     * Key for {@code minecraft:bamboo_shelf}.
     */
    public static final TypedKey<Item> BAMBOO_SHELF = create("bamboo_shelf");

    /**
     * Key for {@code minecraft:bamboo_sign}.
     */
    public static final TypedKey<Item> BAMBOO_SIGN = create("bamboo_sign");

    /**
     * Key for {@code minecraft:bamboo_slab}.
     */
    public static final TypedKey<Item> BAMBOO_SLAB = create("bamboo_slab");

    /**
     * Key for {@code minecraft:bamboo_stairs}.
     */
    public static final TypedKey<Item> BAMBOO_STAIRS = create("bamboo_stairs");

    /**
     * Key for {@code minecraft:bamboo_trapdoor}.
     */
    public static final TypedKey<Item> BAMBOO_TRAPDOOR = create("bamboo_trapdoor");

    /**
     * Key for {@code minecraft:barrel}.
     */
    public static final TypedKey<Item> BARREL = create("barrel");

    /**
     * Key for {@code minecraft:barrier}.
     */
    public static final TypedKey<Item> BARRIER = create("barrier");

    /**
     * Key for {@code minecraft:basalt}.
     */
    public static final TypedKey<Item> BASALT = create("basalt");

    /**
     * Key for {@code minecraft:bat_spawn_egg}.
     */
    public static final TypedKey<Item> BAT_SPAWN_EGG = create("bat_spawn_egg");

    /**
     * Key for {@code minecraft:beacon}.
     */
    public static final TypedKey<Item> BEACON = create("beacon");

    /**
     * Key for {@code minecraft:bedrock}.
     */
    public static final TypedKey<Item> BEDROCK = create("bedrock");

    /**
     * Key for {@code minecraft:beef}.
     */
    public static final TypedKey<Item> BEEF = create("beef");

    /**
     * Key for {@code minecraft:beehive}.
     */
    public static final TypedKey<Item> BEEHIVE = create("beehive");

    /**
     * Key for {@code minecraft:beetroot}.
     */
    public static final TypedKey<Item> BEETROOT = create("beetroot");

    /**
     * Key for {@code minecraft:beetroot_seeds}.
     */
    public static final TypedKey<Item> BEETROOT_SEEDS = create("beetroot_seeds");

    /**
     * Key for {@code minecraft:beetroot_soup}.
     */
    public static final TypedKey<Item> BEETROOT_SOUP = create("beetroot_soup");

    /**
     * Key for {@code minecraft:bee_nest}.
     */
    public static final TypedKey<Item> BEE_NEST = create("bee_nest");

    /**
     * Key for {@code minecraft:bee_spawn_egg}.
     */
    public static final TypedKey<Item> BEE_SPAWN_EGG = create("bee_spawn_egg");

    /**
     * Key for {@code minecraft:bell}.
     */
    public static final TypedKey<Item> BELL = create("bell");

    /**
     * Key for {@code minecraft:big_dripleaf}.
     */
    public static final TypedKey<Item> BIG_DRIPLEAF = create("big_dripleaf");

    /**
     * Key for {@code minecraft:birch_boat}.
     */
    public static final TypedKey<Item> BIRCH_BOAT = create("birch_boat");

    /**
     * Key for {@code minecraft:birch_button}.
     */
    public static final TypedKey<Item> BIRCH_BUTTON = create("birch_button");

    /**
     * Key for {@code minecraft:birch_chest_boat}.
     */
    public static final TypedKey<Item> BIRCH_CHEST_BOAT = create("birch_chest_boat");

    /**
     * Key for {@code minecraft:birch_door}.
     */
    public static final TypedKey<Item> BIRCH_DOOR = create("birch_door");

    /**
     * Key for {@code minecraft:birch_fence}.
     */
    public static final TypedKey<Item> BIRCH_FENCE = create("birch_fence");

    /**
     * Key for {@code minecraft:birch_fence_gate}.
     */
    public static final TypedKey<Item> BIRCH_FENCE_GATE = create("birch_fence_gate");

    /**
     * Key for {@code minecraft:birch_hanging_sign}.
     */
    public static final TypedKey<Item> BIRCH_HANGING_SIGN = create("birch_hanging_sign");

    /**
     * Key for {@code minecraft:birch_leaves}.
     */
    public static final TypedKey<Item> BIRCH_LEAVES = create("birch_leaves");

    /**
     * Key for {@code minecraft:birch_log}.
     */
    public static final TypedKey<Item> BIRCH_LOG = create("birch_log");

    /**
     * Key for {@code minecraft:birch_planks}.
     */
    public static final TypedKey<Item> BIRCH_PLANKS = create("birch_planks");

    /**
     * Key for {@code minecraft:birch_pressure_plate}.
     */
    public static final TypedKey<Item> BIRCH_PRESSURE_PLATE = create("birch_pressure_plate");

    /**
     * Key for {@code minecraft:birch_sapling}.
     */
    public static final TypedKey<Item> BIRCH_SAPLING = create("birch_sapling");

    /**
     * Key for {@code minecraft:birch_shelf}.
     */
    public static final TypedKey<Item> BIRCH_SHELF = create("birch_shelf");

    /**
     * Key for {@code minecraft:birch_sign}.
     */
    public static final TypedKey<Item> BIRCH_SIGN = create("birch_sign");

    /**
     * Key for {@code minecraft:birch_slab}.
     */
    public static final TypedKey<Item> BIRCH_SLAB = create("birch_slab");

    /**
     * Key for {@code minecraft:birch_stairs}.
     */
    public static final TypedKey<Item> BIRCH_STAIRS = create("birch_stairs");

    /**
     * Key for {@code minecraft:birch_trapdoor}.
     */
    public static final TypedKey<Item> BIRCH_TRAPDOOR = create("birch_trapdoor");

    /**
     * Key for {@code minecraft:birch_wood}.
     */
    public static final TypedKey<Item> BIRCH_WOOD = create("birch_wood");

    /**
     * Key for {@code minecraft:blackstone}.
     */
    public static final TypedKey<Item> BLACKSTONE = create("blackstone");

    /**
     * Key for {@code minecraft:blackstone_slab}.
     */
    public static final TypedKey<Item> BLACKSTONE_SLAB = create("blackstone_slab");

    /**
     * Key for {@code minecraft:blackstone_stairs}.
     */
    public static final TypedKey<Item> BLACKSTONE_STAIRS = create("blackstone_stairs");

    /**
     * Key for {@code minecraft:blackstone_wall}.
     */
    public static final TypedKey<Item> BLACKSTONE_WALL = create("blackstone_wall");

    /**
     * Key for {@code minecraft:black_banner}.
     */
    public static final TypedKey<Item> BLACK_BANNER = create("black_banner");

    /**
     * Key for {@code minecraft:black_bed}.
     */
    public static final TypedKey<Item> BLACK_BED = create("black_bed");

    /**
     * Key for {@code minecraft:black_bundle}.
     */
    public static final TypedKey<Item> BLACK_BUNDLE = create("black_bundle");

    /**
     * Key for {@code minecraft:black_candle}.
     */
    public static final TypedKey<Item> BLACK_CANDLE = create("black_candle");

    /**
     * Key for {@code minecraft:black_carpet}.
     */
    public static final TypedKey<Item> BLACK_CARPET = create("black_carpet");

    /**
     * Key for {@code minecraft:black_concrete}.
     */
    public static final TypedKey<Item> BLACK_CONCRETE = create("black_concrete");

    /**
     * Key for {@code minecraft:black_concrete_powder}.
     */
    public static final TypedKey<Item> BLACK_CONCRETE_POWDER = create("black_concrete_powder");

    /**
     * Key for {@code minecraft:black_dye}.
     */
    public static final TypedKey<Item> BLACK_DYE = create("black_dye");

    /**
     * Key for {@code minecraft:black_glazed_terracotta}.
     */
    public static final TypedKey<Item> BLACK_GLAZED_TERRACOTTA = create("black_glazed_terracotta");

    /**
     * Key for {@code minecraft:black_harness}.
     */
    public static final TypedKey<Item> BLACK_HARNESS = create("black_harness");

    /**
     * Key for {@code minecraft:black_shulker_box}.
     */
    public static final TypedKey<Item> BLACK_SHULKER_BOX = create("black_shulker_box");

    /**
     * Key for {@code minecraft:black_stained_glass}.
     */
    public static final TypedKey<Item> BLACK_STAINED_GLASS = create("black_stained_glass");

    /**
     * Key for {@code minecraft:black_stained_glass_pane}.
     */
    public static final TypedKey<Item> BLACK_STAINED_GLASS_PANE = create("black_stained_glass_pane");

    /**
     * Key for {@code minecraft:black_terracotta}.
     */
    public static final TypedKey<Item> BLACK_TERRACOTTA = create("black_terracotta");

    /**
     * Key for {@code minecraft:black_wool}.
     */
    public static final TypedKey<Item> BLACK_WOOL = create("black_wool");

    /**
     * Key for {@code minecraft:blade_pottery_sherd}.
     */
    public static final TypedKey<Item> BLADE_POTTERY_SHERD = create("blade_pottery_sherd");

    /**
     * Key for {@code minecraft:blast_furnace}.
     */
    public static final TypedKey<Item> BLAST_FURNACE = create("blast_furnace");

    /**
     * Key for {@code minecraft:blaze_powder}.
     */
    public static final TypedKey<Item> BLAZE_POWDER = create("blaze_powder");

    /**
     * Key for {@code minecraft:blaze_rod}.
     */
    public static final TypedKey<Item> BLAZE_ROD = create("blaze_rod");

    /**
     * Key for {@code minecraft:blaze_spawn_egg}.
     */
    public static final TypedKey<Item> BLAZE_SPAWN_EGG = create("blaze_spawn_egg");

    /**
     * Key for {@code minecraft:blue_banner}.
     */
    public static final TypedKey<Item> BLUE_BANNER = create("blue_banner");

    /**
     * Key for {@code minecraft:blue_bed}.
     */
    public static final TypedKey<Item> BLUE_BED = create("blue_bed");

    /**
     * Key for {@code minecraft:blue_bundle}.
     */
    public static final TypedKey<Item> BLUE_BUNDLE = create("blue_bundle");

    /**
     * Key for {@code minecraft:blue_candle}.
     */
    public static final TypedKey<Item> BLUE_CANDLE = create("blue_candle");

    /**
     * Key for {@code minecraft:blue_carpet}.
     */
    public static final TypedKey<Item> BLUE_CARPET = create("blue_carpet");

    /**
     * Key for {@code minecraft:blue_concrete}.
     */
    public static final TypedKey<Item> BLUE_CONCRETE = create("blue_concrete");

    /**
     * Key for {@code minecraft:blue_concrete_powder}.
     */
    public static final TypedKey<Item> BLUE_CONCRETE_POWDER = create("blue_concrete_powder");

    /**
     * Key for {@code minecraft:blue_dye}.
     */
    public static final TypedKey<Item> BLUE_DYE = create("blue_dye");

    /**
     * Key for {@code minecraft:blue_egg}.
     */
    public static final TypedKey<Item> BLUE_EGG = create("blue_egg");

    /**
     * Key for {@code minecraft:blue_glazed_terracotta}.
     */
    public static final TypedKey<Item> BLUE_GLAZED_TERRACOTTA = create("blue_glazed_terracotta");

    /**
     * Key for {@code minecraft:blue_harness}.
     */
    public static final TypedKey<Item> BLUE_HARNESS = create("blue_harness");

    /**
     * Key for {@code minecraft:blue_ice}.
     */
    public static final TypedKey<Item> BLUE_ICE = create("blue_ice");

    /**
     * Key for {@code minecraft:blue_orchid}.
     */
    public static final TypedKey<Item> BLUE_ORCHID = create("blue_orchid");

    /**
     * Key for {@code minecraft:blue_shulker_box}.
     */
    public static final TypedKey<Item> BLUE_SHULKER_BOX = create("blue_shulker_box");

    /**
     * Key for {@code minecraft:blue_stained_glass}.
     */
    public static final TypedKey<Item> BLUE_STAINED_GLASS = create("blue_stained_glass");

    /**
     * Key for {@code minecraft:blue_stained_glass_pane}.
     */
    public static final TypedKey<Item> BLUE_STAINED_GLASS_PANE = create("blue_stained_glass_pane");

    /**
     * Key for {@code minecraft:blue_terracotta}.
     */
    public static final TypedKey<Item> BLUE_TERRACOTTA = create("blue_terracotta");

    /**
     * Key for {@code minecraft:blue_wool}.
     */
    public static final TypedKey<Item> BLUE_WOOL = create("blue_wool");

    /**
     * Key for {@code minecraft:bogged_spawn_egg}.
     */
    public static final TypedKey<Item> BOGGED_SPAWN_EGG = create("bogged_spawn_egg");

    /**
     * Key for {@code minecraft:bolt_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> BOLT_ARMOR_TRIM_SMITHING_TEMPLATE = create("bolt_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:bone}.
     */
    public static final TypedKey<Item> BONE = create("bone");

    /**
     * Key for {@code minecraft:bone_block}.
     */
    public static final TypedKey<Item> BONE_BLOCK = create("bone_block");

    /**
     * Key for {@code minecraft:bone_meal}.
     */
    public static final TypedKey<Item> BONE_MEAL = create("bone_meal");

    /**
     * Key for {@code minecraft:book}.
     */
    public static final TypedKey<Item> BOOK = create("book");

    /**
     * Key for {@code minecraft:bookshelf}.
     */
    public static final TypedKey<Item> BOOKSHELF = create("bookshelf");

    /**
     * Key for {@code minecraft:bordure_indented_banner_pattern}.
     */
    public static final TypedKey<Item> BORDURE_INDENTED_BANNER_PATTERN = create("bordure_indented_banner_pattern");

    /**
     * Key for {@code minecraft:bow}.
     */
    public static final TypedKey<Item> BOW = create("bow");

    /**
     * Key for {@code minecraft:bowl}.
     */
    public static final TypedKey<Item> BOWL = create("bowl");

    /**
     * Key for {@code minecraft:brain_coral}.
     */
    public static final TypedKey<Item> BRAIN_CORAL = create("brain_coral");

    /**
     * Key for {@code minecraft:brain_coral_block}.
     */
    public static final TypedKey<Item> BRAIN_CORAL_BLOCK = create("brain_coral_block");

    /**
     * Key for {@code minecraft:brain_coral_fan}.
     */
    public static final TypedKey<Item> BRAIN_CORAL_FAN = create("brain_coral_fan");

    /**
     * Key for {@code minecraft:bread}.
     */
    public static final TypedKey<Item> BREAD = create("bread");

    /**
     * Key for {@code minecraft:breeze_rod}.
     */
    public static final TypedKey<Item> BREEZE_ROD = create("breeze_rod");

    /**
     * Key for {@code minecraft:breeze_spawn_egg}.
     */
    public static final TypedKey<Item> BREEZE_SPAWN_EGG = create("breeze_spawn_egg");

    /**
     * Key for {@code minecraft:brewer_pottery_sherd}.
     */
    public static final TypedKey<Item> BREWER_POTTERY_SHERD = create("brewer_pottery_sherd");

    /**
     * Key for {@code minecraft:brewing_stand}.
     */
    public static final TypedKey<Item> BREWING_STAND = create("brewing_stand");

    /**
     * Key for {@code minecraft:brick}.
     */
    public static final TypedKey<Item> BRICK = create("brick");

    /**
     * Key for {@code minecraft:bricks}.
     */
    public static final TypedKey<Item> BRICKS = create("bricks");

    /**
     * Key for {@code minecraft:brick_slab}.
     */
    public static final TypedKey<Item> BRICK_SLAB = create("brick_slab");

    /**
     * Key for {@code minecraft:brick_stairs}.
     */
    public static final TypedKey<Item> BRICK_STAIRS = create("brick_stairs");

    /**
     * Key for {@code minecraft:brick_wall}.
     */
    public static final TypedKey<Item> BRICK_WALL = create("brick_wall");

    /**
     * Key for {@code minecraft:brown_banner}.
     */
    public static final TypedKey<Item> BROWN_BANNER = create("brown_banner");

    /**
     * Key for {@code minecraft:brown_bed}.
     */
    public static final TypedKey<Item> BROWN_BED = create("brown_bed");

    /**
     * Key for {@code minecraft:brown_bundle}.
     */
    public static final TypedKey<Item> BROWN_BUNDLE = create("brown_bundle");

    /**
     * Key for {@code minecraft:brown_candle}.
     */
    public static final TypedKey<Item> BROWN_CANDLE = create("brown_candle");

    /**
     * Key for {@code minecraft:brown_carpet}.
     */
    public static final TypedKey<Item> BROWN_CARPET = create("brown_carpet");

    /**
     * Key for {@code minecraft:brown_concrete}.
     */
    public static final TypedKey<Item> BROWN_CONCRETE = create("brown_concrete");

    /**
     * Key for {@code minecraft:brown_concrete_powder}.
     */
    public static final TypedKey<Item> BROWN_CONCRETE_POWDER = create("brown_concrete_powder");

    /**
     * Key for {@code minecraft:brown_dye}.
     */
    public static final TypedKey<Item> BROWN_DYE = create("brown_dye");

    /**
     * Key for {@code minecraft:brown_egg}.
     */
    public static final TypedKey<Item> BROWN_EGG = create("brown_egg");

    /**
     * Key for {@code minecraft:brown_glazed_terracotta}.
     */
    public static final TypedKey<Item> BROWN_GLAZED_TERRACOTTA = create("brown_glazed_terracotta");

    /**
     * Key for {@code minecraft:brown_harness}.
     */
    public static final TypedKey<Item> BROWN_HARNESS = create("brown_harness");

    /**
     * Key for {@code minecraft:brown_mushroom}.
     */
    public static final TypedKey<Item> BROWN_MUSHROOM = create("brown_mushroom");

    /**
     * Key for {@code minecraft:brown_mushroom_block}.
     */
    public static final TypedKey<Item> BROWN_MUSHROOM_BLOCK = create("brown_mushroom_block");

    /**
     * Key for {@code minecraft:brown_shulker_box}.
     */
    public static final TypedKey<Item> BROWN_SHULKER_BOX = create("brown_shulker_box");

    /**
     * Key for {@code minecraft:brown_stained_glass}.
     */
    public static final TypedKey<Item> BROWN_STAINED_GLASS = create("brown_stained_glass");

    /**
     * Key for {@code minecraft:brown_stained_glass_pane}.
     */
    public static final TypedKey<Item> BROWN_STAINED_GLASS_PANE = create("brown_stained_glass_pane");

    /**
     * Key for {@code minecraft:brown_terracotta}.
     */
    public static final TypedKey<Item> BROWN_TERRACOTTA = create("brown_terracotta");

    /**
     * Key for {@code minecraft:brown_wool}.
     */
    public static final TypedKey<Item> BROWN_WOOL = create("brown_wool");

    /**
     * Key for {@code minecraft:brush}.
     */
    public static final TypedKey<Item> BRUSH = create("brush");

    /**
     * Key for {@code minecraft:bubble_coral}.
     */
    public static final TypedKey<Item> BUBBLE_CORAL = create("bubble_coral");

    /**
     * Key for {@code minecraft:bubble_coral_block}.
     */
    public static final TypedKey<Item> BUBBLE_CORAL_BLOCK = create("bubble_coral_block");

    /**
     * Key for {@code minecraft:bubble_coral_fan}.
     */
    public static final TypedKey<Item> BUBBLE_CORAL_FAN = create("bubble_coral_fan");

    /**
     * Key for {@code minecraft:bucket}.
     */
    public static final TypedKey<Item> BUCKET = create("bucket");

    /**
     * Key for {@code minecraft:budding_amethyst}.
     */
    public static final TypedKey<Item> BUDDING_AMETHYST = create("budding_amethyst");

    /**
     * Key for {@code minecraft:bundle}.
     */
    public static final TypedKey<Item> BUNDLE = create("bundle");

    /**
     * Key for {@code minecraft:burn_pottery_sherd}.
     */
    public static final TypedKey<Item> BURN_POTTERY_SHERD = create("burn_pottery_sherd");

    /**
     * Key for {@code minecraft:bush}.
     */
    public static final TypedKey<Item> BUSH = create("bush");

    /**
     * Key for {@code minecraft:cactus}.
     */
    public static final TypedKey<Item> CACTUS = create("cactus");

    /**
     * Key for {@code minecraft:cactus_flower}.
     */
    public static final TypedKey<Item> CACTUS_FLOWER = create("cactus_flower");

    /**
     * Key for {@code minecraft:cake}.
     */
    public static final TypedKey<Item> CAKE = create("cake");

    /**
     * Key for {@code minecraft:calcite}.
     */
    public static final TypedKey<Item> CALCITE = create("calcite");

    /**
     * Key for {@code minecraft:calibrated_sculk_sensor}.
     */
    public static final TypedKey<Item> CALIBRATED_SCULK_SENSOR = create("calibrated_sculk_sensor");

    /**
     * Key for {@code minecraft:camel_husk_spawn_egg}.
     */
    public static final TypedKey<Item> CAMEL_HUSK_SPAWN_EGG = create("camel_husk_spawn_egg");

    /**
     * Key for {@code minecraft:camel_spawn_egg}.
     */
    public static final TypedKey<Item> CAMEL_SPAWN_EGG = create("camel_spawn_egg");

    /**
     * Key for {@code minecraft:campfire}.
     */
    public static final TypedKey<Item> CAMPFIRE = create("campfire");

    /**
     * Key for {@code minecraft:candle}.
     */
    public static final TypedKey<Item> CANDLE = create("candle");

    /**
     * Key for {@code minecraft:carrot}.
     */
    public static final TypedKey<Item> CARROT = create("carrot");

    /**
     * Key for {@code minecraft:carrot_on_a_stick}.
     */
    public static final TypedKey<Item> CARROT_ON_A_STICK = create("carrot_on_a_stick");

    /**
     * Key for {@code minecraft:cartography_table}.
     */
    public static final TypedKey<Item> CARTOGRAPHY_TABLE = create("cartography_table");

    /**
     * Key for {@code minecraft:carved_pumpkin}.
     */
    public static final TypedKey<Item> CARVED_PUMPKIN = create("carved_pumpkin");

    /**
     * Key for {@code minecraft:cat_spawn_egg}.
     */
    public static final TypedKey<Item> CAT_SPAWN_EGG = create("cat_spawn_egg");

    /**
     * Key for {@code minecraft:cauldron}.
     */
    public static final TypedKey<Item> CAULDRON = create("cauldron");

    /**
     * Key for {@code minecraft:cave_spider_spawn_egg}.
     */
    public static final TypedKey<Item> CAVE_SPIDER_SPAWN_EGG = create("cave_spider_spawn_egg");

    /**
     * Key for {@code minecraft:chainmail_boots}.
     */
    public static final TypedKey<Item> CHAINMAIL_BOOTS = create("chainmail_boots");

    /**
     * Key for {@code minecraft:chainmail_chestplate}.
     */
    public static final TypedKey<Item> CHAINMAIL_CHESTPLATE = create("chainmail_chestplate");

    /**
     * Key for {@code minecraft:chainmail_helmet}.
     */
    public static final TypedKey<Item> CHAINMAIL_HELMET = create("chainmail_helmet");

    /**
     * Key for {@code minecraft:chainmail_leggings}.
     */
    public static final TypedKey<Item> CHAINMAIL_LEGGINGS = create("chainmail_leggings");

    /**
     * Key for {@code minecraft:chain_command_block}.
     */
    public static final TypedKey<Item> CHAIN_COMMAND_BLOCK = create("chain_command_block");

    /**
     * Key for {@code minecraft:charcoal}.
     */
    public static final TypedKey<Item> CHARCOAL = create("charcoal");

    /**
     * Key for {@code minecraft:cherry_boat}.
     */
    public static final TypedKey<Item> CHERRY_BOAT = create("cherry_boat");

    /**
     * Key for {@code minecraft:cherry_button}.
     */
    public static final TypedKey<Item> CHERRY_BUTTON = create("cherry_button");

    /**
     * Key for {@code minecraft:cherry_chest_boat}.
     */
    public static final TypedKey<Item> CHERRY_CHEST_BOAT = create("cherry_chest_boat");

    /**
     * Key for {@code minecraft:cherry_door}.
     */
    public static final TypedKey<Item> CHERRY_DOOR = create("cherry_door");

    /**
     * Key for {@code minecraft:cherry_fence}.
     */
    public static final TypedKey<Item> CHERRY_FENCE = create("cherry_fence");

    /**
     * Key for {@code minecraft:cherry_fence_gate}.
     */
    public static final TypedKey<Item> CHERRY_FENCE_GATE = create("cherry_fence_gate");

    /**
     * Key for {@code minecraft:cherry_hanging_sign}.
     */
    public static final TypedKey<Item> CHERRY_HANGING_SIGN = create("cherry_hanging_sign");

    /**
     * Key for {@code minecraft:cherry_leaves}.
     */
    public static final TypedKey<Item> CHERRY_LEAVES = create("cherry_leaves");

    /**
     * Key for {@code minecraft:cherry_log}.
     */
    public static final TypedKey<Item> CHERRY_LOG = create("cherry_log");

    /**
     * Key for {@code minecraft:cherry_planks}.
     */
    public static final TypedKey<Item> CHERRY_PLANKS = create("cherry_planks");

    /**
     * Key for {@code minecraft:cherry_pressure_plate}.
     */
    public static final TypedKey<Item> CHERRY_PRESSURE_PLATE = create("cherry_pressure_plate");

    /**
     * Key for {@code minecraft:cherry_sapling}.
     */
    public static final TypedKey<Item> CHERRY_SAPLING = create("cherry_sapling");

    /**
     * Key for {@code minecraft:cherry_shelf}.
     */
    public static final TypedKey<Item> CHERRY_SHELF = create("cherry_shelf");

    /**
     * Key for {@code minecraft:cherry_sign}.
     */
    public static final TypedKey<Item> CHERRY_SIGN = create("cherry_sign");

    /**
     * Key for {@code minecraft:cherry_slab}.
     */
    public static final TypedKey<Item> CHERRY_SLAB = create("cherry_slab");

    /**
     * Key for {@code minecraft:cherry_stairs}.
     */
    public static final TypedKey<Item> CHERRY_STAIRS = create("cherry_stairs");

    /**
     * Key for {@code minecraft:cherry_trapdoor}.
     */
    public static final TypedKey<Item> CHERRY_TRAPDOOR = create("cherry_trapdoor");

    /**
     * Key for {@code minecraft:cherry_wood}.
     */
    public static final TypedKey<Item> CHERRY_WOOD = create("cherry_wood");

    /**
     * Key for {@code minecraft:chest}.
     */
    public static final TypedKey<Item> CHEST = create("chest");

    /**
     * Key for {@code minecraft:chest_minecart}.
     */
    public static final TypedKey<Item> CHEST_MINECART = create("chest_minecart");

    /**
     * Key for {@code minecraft:chicken}.
     */
    public static final TypedKey<Item> CHICKEN = create("chicken");

    /**
     * Key for {@code minecraft:chicken_spawn_egg}.
     */
    public static final TypedKey<Item> CHICKEN_SPAWN_EGG = create("chicken_spawn_egg");

    /**
     * Key for {@code minecraft:chipped_anvil}.
     */
    public static final TypedKey<Item> CHIPPED_ANVIL = create("chipped_anvil");

    /**
     * Key for {@code minecraft:chiseled_bookshelf}.
     */
    public static final TypedKey<Item> CHISELED_BOOKSHELF = create("chiseled_bookshelf");

    /**
     * Key for {@code minecraft:chiseled_cinnabar}.
     */
    public static final TypedKey<Item> CHISELED_CINNABAR = create("chiseled_cinnabar");

    /**
     * Key for {@code minecraft:chiseled_copper}.
     */
    public static final TypedKey<Item> CHISELED_COPPER = create("chiseled_copper");

    /**
     * Key for {@code minecraft:chiseled_deepslate}.
     */
    public static final TypedKey<Item> CHISELED_DEEPSLATE = create("chiseled_deepslate");

    /**
     * Key for {@code minecraft:chiseled_nether_bricks}.
     */
    public static final TypedKey<Item> CHISELED_NETHER_BRICKS = create("chiseled_nether_bricks");

    /**
     * Key for {@code minecraft:chiseled_polished_blackstone}.
     */
    public static final TypedKey<Item> CHISELED_POLISHED_BLACKSTONE = create("chiseled_polished_blackstone");

    /**
     * Key for {@code minecraft:chiseled_quartz_block}.
     */
    public static final TypedKey<Item> CHISELED_QUARTZ_BLOCK = create("chiseled_quartz_block");

    /**
     * Key for {@code minecraft:chiseled_red_sandstone}.
     */
    public static final TypedKey<Item> CHISELED_RED_SANDSTONE = create("chiseled_red_sandstone");

    /**
     * Key for {@code minecraft:chiseled_resin_bricks}.
     */
    public static final TypedKey<Item> CHISELED_RESIN_BRICKS = create("chiseled_resin_bricks");

    /**
     * Key for {@code minecraft:chiseled_sandstone}.
     */
    public static final TypedKey<Item> CHISELED_SANDSTONE = create("chiseled_sandstone");

    /**
     * Key for {@code minecraft:chiseled_stone_bricks}.
     */
    public static final TypedKey<Item> CHISELED_STONE_BRICKS = create("chiseled_stone_bricks");

    /**
     * Key for {@code minecraft:chiseled_sulfur}.
     */
    public static final TypedKey<Item> CHISELED_SULFUR = create("chiseled_sulfur");

    /**
     * Key for {@code minecraft:chiseled_tuff}.
     */
    public static final TypedKey<Item> CHISELED_TUFF = create("chiseled_tuff");

    /**
     * Key for {@code minecraft:chiseled_tuff_bricks}.
     */
    public static final TypedKey<Item> CHISELED_TUFF_BRICKS = create("chiseled_tuff_bricks");

    /**
     * Key for {@code minecraft:chorus_flower}.
     */
    public static final TypedKey<Item> CHORUS_FLOWER = create("chorus_flower");

    /**
     * Key for {@code minecraft:chorus_fruit}.
     */
    public static final TypedKey<Item> CHORUS_FRUIT = create("chorus_fruit");

    /**
     * Key for {@code minecraft:chorus_plant}.
     */
    public static final TypedKey<Item> CHORUS_PLANT = create("chorus_plant");

    /**
     * Key for {@code minecraft:cinnabar}.
     */
    public static final TypedKey<Item> CINNABAR = create("cinnabar");

    /**
     * Key for {@code minecraft:cinnabar_bricks}.
     */
    public static final TypedKey<Item> CINNABAR_BRICKS = create("cinnabar_bricks");

    /**
     * Key for {@code minecraft:cinnabar_brick_slab}.
     */
    public static final TypedKey<Item> CINNABAR_BRICK_SLAB = create("cinnabar_brick_slab");

    /**
     * Key for {@code minecraft:cinnabar_brick_stairs}.
     */
    public static final TypedKey<Item> CINNABAR_BRICK_STAIRS = create("cinnabar_brick_stairs");

    /**
     * Key for {@code minecraft:cinnabar_brick_wall}.
     */
    public static final TypedKey<Item> CINNABAR_BRICK_WALL = create("cinnabar_brick_wall");

    /**
     * Key for {@code minecraft:cinnabar_slab}.
     */
    public static final TypedKey<Item> CINNABAR_SLAB = create("cinnabar_slab");

    /**
     * Key for {@code minecraft:cinnabar_stairs}.
     */
    public static final TypedKey<Item> CINNABAR_STAIRS = create("cinnabar_stairs");

    /**
     * Key for {@code minecraft:cinnabar_wall}.
     */
    public static final TypedKey<Item> CINNABAR_WALL = create("cinnabar_wall");

    /**
     * Key for {@code minecraft:clay}.
     */
    public static final TypedKey<Item> CLAY = create("clay");

    /**
     * Key for {@code minecraft:clay_ball}.
     */
    public static final TypedKey<Item> CLAY_BALL = create("clay_ball");

    /**
     * Key for {@code minecraft:clock}.
     */
    public static final TypedKey<Item> CLOCK = create("clock");

    /**
     * Key for {@code minecraft:closed_eyeblossom}.
     */
    public static final TypedKey<Item> CLOSED_EYEBLOSSOM = create("closed_eyeblossom");

    /**
     * Key for {@code minecraft:coal}.
     */
    public static final TypedKey<Item> COAL = create("coal");

    /**
     * Key for {@code minecraft:coal_block}.
     */
    public static final TypedKey<Item> COAL_BLOCK = create("coal_block");

    /**
     * Key for {@code minecraft:coal_ore}.
     */
    public static final TypedKey<Item> COAL_ORE = create("coal_ore");

    /**
     * Key for {@code minecraft:coarse_dirt}.
     */
    public static final TypedKey<Item> COARSE_DIRT = create("coarse_dirt");

    /**
     * Key for {@code minecraft:coast_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> COAST_ARMOR_TRIM_SMITHING_TEMPLATE = create("coast_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:cobbled_deepslate}.
     */
    public static final TypedKey<Item> COBBLED_DEEPSLATE = create("cobbled_deepslate");

    /**
     * Key for {@code minecraft:cobbled_deepslate_slab}.
     */
    public static final TypedKey<Item> COBBLED_DEEPSLATE_SLAB = create("cobbled_deepslate_slab");

    /**
     * Key for {@code minecraft:cobbled_deepslate_stairs}.
     */
    public static final TypedKey<Item> COBBLED_DEEPSLATE_STAIRS = create("cobbled_deepslate_stairs");

    /**
     * Key for {@code minecraft:cobbled_deepslate_wall}.
     */
    public static final TypedKey<Item> COBBLED_DEEPSLATE_WALL = create("cobbled_deepslate_wall");

    /**
     * Key for {@code minecraft:cobblestone}.
     */
    public static final TypedKey<Item> COBBLESTONE = create("cobblestone");

    /**
     * Key for {@code minecraft:cobblestone_slab}.
     */
    public static final TypedKey<Item> COBBLESTONE_SLAB = create("cobblestone_slab");

    /**
     * Key for {@code minecraft:cobblestone_stairs}.
     */
    public static final TypedKey<Item> COBBLESTONE_STAIRS = create("cobblestone_stairs");

    /**
     * Key for {@code minecraft:cobblestone_wall}.
     */
    public static final TypedKey<Item> COBBLESTONE_WALL = create("cobblestone_wall");

    /**
     * Key for {@code minecraft:cobweb}.
     */
    public static final TypedKey<Item> COBWEB = create("cobweb");

    /**
     * Key for {@code minecraft:cocoa_beans}.
     */
    public static final TypedKey<Item> COCOA_BEANS = create("cocoa_beans");

    /**
     * Key for {@code minecraft:cod}.
     */
    public static final TypedKey<Item> COD = create("cod");

    /**
     * Key for {@code minecraft:cod_bucket}.
     */
    public static final TypedKey<Item> COD_BUCKET = create("cod_bucket");

    /**
     * Key for {@code minecraft:cod_spawn_egg}.
     */
    public static final TypedKey<Item> COD_SPAWN_EGG = create("cod_spawn_egg");

    /**
     * Key for {@code minecraft:command_block}.
     */
    public static final TypedKey<Item> COMMAND_BLOCK = create("command_block");

    /**
     * Key for {@code minecraft:command_block_minecart}.
     */
    public static final TypedKey<Item> COMMAND_BLOCK_MINECART = create("command_block_minecart");

    /**
     * Key for {@code minecraft:comparator}.
     */
    public static final TypedKey<Item> COMPARATOR = create("comparator");

    /**
     * Key for {@code minecraft:compass}.
     */
    public static final TypedKey<Item> COMPASS = create("compass");

    /**
     * Key for {@code minecraft:composter}.
     */
    public static final TypedKey<Item> COMPOSTER = create("composter");

    /**
     * Key for {@code minecraft:conduit}.
     */
    public static final TypedKey<Item> CONDUIT = create("conduit");

    /**
     * Key for {@code minecraft:cooked_beef}.
     */
    public static final TypedKey<Item> COOKED_BEEF = create("cooked_beef");

    /**
     * Key for {@code minecraft:cooked_chicken}.
     */
    public static final TypedKey<Item> COOKED_CHICKEN = create("cooked_chicken");

    /**
     * Key for {@code minecraft:cooked_cod}.
     */
    public static final TypedKey<Item> COOKED_COD = create("cooked_cod");

    /**
     * Key for {@code minecraft:cooked_mutton}.
     */
    public static final TypedKey<Item> COOKED_MUTTON = create("cooked_mutton");

    /**
     * Key for {@code minecraft:cooked_porkchop}.
     */
    public static final TypedKey<Item> COOKED_PORKCHOP = create("cooked_porkchop");

    /**
     * Key for {@code minecraft:cooked_rabbit}.
     */
    public static final TypedKey<Item> COOKED_RABBIT = create("cooked_rabbit");

    /**
     * Key for {@code minecraft:cooked_salmon}.
     */
    public static final TypedKey<Item> COOKED_SALMON = create("cooked_salmon");

    /**
     * Key for {@code minecraft:cookie}.
     */
    public static final TypedKey<Item> COOKIE = create("cookie");

    /**
     * Key for {@code minecraft:copper_axe}.
     */
    public static final TypedKey<Item> COPPER_AXE = create("copper_axe");

    /**
     * Key for {@code minecraft:copper_bars}.
     */
    public static final TypedKey<Item> COPPER_BARS = create("copper_bars");

    /**
     * Key for {@code minecraft:copper_block}.
     */
    public static final TypedKey<Item> COPPER_BLOCK = create("copper_block");

    /**
     * Key for {@code minecraft:copper_boots}.
     */
    public static final TypedKey<Item> COPPER_BOOTS = create("copper_boots");

    /**
     * Key for {@code minecraft:copper_bulb}.
     */
    public static final TypedKey<Item> COPPER_BULB = create("copper_bulb");

    /**
     * Key for {@code minecraft:copper_chain}.
     */
    public static final TypedKey<Item> COPPER_CHAIN = create("copper_chain");

    /**
     * Key for {@code minecraft:copper_chest}.
     */
    public static final TypedKey<Item> COPPER_CHEST = create("copper_chest");

    /**
     * Key for {@code minecraft:copper_chestplate}.
     */
    public static final TypedKey<Item> COPPER_CHESTPLATE = create("copper_chestplate");

    /**
     * Key for {@code minecraft:copper_door}.
     */
    public static final TypedKey<Item> COPPER_DOOR = create("copper_door");

    /**
     * Key for {@code minecraft:copper_golem_spawn_egg}.
     */
    public static final TypedKey<Item> COPPER_GOLEM_SPAWN_EGG = create("copper_golem_spawn_egg");

    /**
     * Key for {@code minecraft:copper_golem_statue}.
     */
    public static final TypedKey<Item> COPPER_GOLEM_STATUE = create("copper_golem_statue");

    /**
     * Key for {@code minecraft:copper_grate}.
     */
    public static final TypedKey<Item> COPPER_GRATE = create("copper_grate");

    /**
     * Key for {@code minecraft:copper_helmet}.
     */
    public static final TypedKey<Item> COPPER_HELMET = create("copper_helmet");

    /**
     * Key for {@code minecraft:copper_hoe}.
     */
    public static final TypedKey<Item> COPPER_HOE = create("copper_hoe");

    /**
     * Key for {@code minecraft:copper_horse_armor}.
     */
    public static final TypedKey<Item> COPPER_HORSE_ARMOR = create("copper_horse_armor");

    /**
     * Key for {@code minecraft:copper_ingot}.
     */
    public static final TypedKey<Item> COPPER_INGOT = create("copper_ingot");

    /**
     * Key for {@code minecraft:copper_lantern}.
     */
    public static final TypedKey<Item> COPPER_LANTERN = create("copper_lantern");

    /**
     * Key for {@code minecraft:copper_leggings}.
     */
    public static final TypedKey<Item> COPPER_LEGGINGS = create("copper_leggings");

    /**
     * Key for {@code minecraft:copper_nautilus_armor}.
     */
    public static final TypedKey<Item> COPPER_NAUTILUS_ARMOR = create("copper_nautilus_armor");

    /**
     * Key for {@code minecraft:copper_nugget}.
     */
    public static final TypedKey<Item> COPPER_NUGGET = create("copper_nugget");

    /**
     * Key for {@code minecraft:copper_ore}.
     */
    public static final TypedKey<Item> COPPER_ORE = create("copper_ore");

    /**
     * Key for {@code minecraft:copper_pickaxe}.
     */
    public static final TypedKey<Item> COPPER_PICKAXE = create("copper_pickaxe");

    /**
     * Key for {@code minecraft:copper_shovel}.
     */
    public static final TypedKey<Item> COPPER_SHOVEL = create("copper_shovel");

    /**
     * Key for {@code minecraft:copper_spear}.
     */
    public static final TypedKey<Item> COPPER_SPEAR = create("copper_spear");

    /**
     * Key for {@code minecraft:copper_sword}.
     */
    public static final TypedKey<Item> COPPER_SWORD = create("copper_sword");

    /**
     * Key for {@code minecraft:copper_torch}.
     */
    public static final TypedKey<Item> COPPER_TORCH = create("copper_torch");

    /**
     * Key for {@code minecraft:copper_trapdoor}.
     */
    public static final TypedKey<Item> COPPER_TRAPDOOR = create("copper_trapdoor");

    /**
     * Key for {@code minecraft:cornflower}.
     */
    public static final TypedKey<Item> CORNFLOWER = create("cornflower");

    /**
     * Key for {@code minecraft:cow_spawn_egg}.
     */
    public static final TypedKey<Item> COW_SPAWN_EGG = create("cow_spawn_egg");

    /**
     * Key for {@code minecraft:cracked_deepslate_bricks}.
     */
    public static final TypedKey<Item> CRACKED_DEEPSLATE_BRICKS = create("cracked_deepslate_bricks");

    /**
     * Key for {@code minecraft:cracked_deepslate_tiles}.
     */
    public static final TypedKey<Item> CRACKED_DEEPSLATE_TILES = create("cracked_deepslate_tiles");

    /**
     * Key for {@code minecraft:cracked_nether_bricks}.
     */
    public static final TypedKey<Item> CRACKED_NETHER_BRICKS = create("cracked_nether_bricks");

    /**
     * Key for {@code minecraft:cracked_polished_blackstone_bricks}.
     */
    public static final TypedKey<Item> CRACKED_POLISHED_BLACKSTONE_BRICKS = create("cracked_polished_blackstone_bricks");

    /**
     * Key for {@code minecraft:cracked_stone_bricks}.
     */
    public static final TypedKey<Item> CRACKED_STONE_BRICKS = create("cracked_stone_bricks");

    /**
     * Key for {@code minecraft:crafter}.
     */
    public static final TypedKey<Item> CRAFTER = create("crafter");

    /**
     * Key for {@code minecraft:crafting_table}.
     */
    public static final TypedKey<Item> CRAFTING_TABLE = create("crafting_table");

    /**
     * Key for {@code minecraft:creaking_heart}.
     */
    public static final TypedKey<Item> CREAKING_HEART = create("creaking_heart");

    /**
     * Key for {@code minecraft:creaking_spawn_egg}.
     */
    public static final TypedKey<Item> CREAKING_SPAWN_EGG = create("creaking_spawn_egg");

    /**
     * Key for {@code minecraft:creeper_banner_pattern}.
     */
    public static final TypedKey<Item> CREEPER_BANNER_PATTERN = create("creeper_banner_pattern");

    /**
     * Key for {@code minecraft:creeper_head}.
     */
    public static final TypedKey<Item> CREEPER_HEAD = create("creeper_head");

    /**
     * Key for {@code minecraft:creeper_spawn_egg}.
     */
    public static final TypedKey<Item> CREEPER_SPAWN_EGG = create("creeper_spawn_egg");

    /**
     * Key for {@code minecraft:crimson_button}.
     */
    public static final TypedKey<Item> CRIMSON_BUTTON = create("crimson_button");

    /**
     * Key for {@code minecraft:crimson_door}.
     */
    public static final TypedKey<Item> CRIMSON_DOOR = create("crimson_door");

    /**
     * Key for {@code minecraft:crimson_fence}.
     */
    public static final TypedKey<Item> CRIMSON_FENCE = create("crimson_fence");

    /**
     * Key for {@code minecraft:crimson_fence_gate}.
     */
    public static final TypedKey<Item> CRIMSON_FENCE_GATE = create("crimson_fence_gate");

    /**
     * Key for {@code minecraft:crimson_fungus}.
     */
    public static final TypedKey<Item> CRIMSON_FUNGUS = create("crimson_fungus");

    /**
     * Key for {@code minecraft:crimson_hanging_sign}.
     */
    public static final TypedKey<Item> CRIMSON_HANGING_SIGN = create("crimson_hanging_sign");

    /**
     * Key for {@code minecraft:crimson_hyphae}.
     */
    public static final TypedKey<Item> CRIMSON_HYPHAE = create("crimson_hyphae");

    /**
     * Key for {@code minecraft:crimson_nylium}.
     */
    public static final TypedKey<Item> CRIMSON_NYLIUM = create("crimson_nylium");

    /**
     * Key for {@code minecraft:crimson_planks}.
     */
    public static final TypedKey<Item> CRIMSON_PLANKS = create("crimson_planks");

    /**
     * Key for {@code minecraft:crimson_pressure_plate}.
     */
    public static final TypedKey<Item> CRIMSON_PRESSURE_PLATE = create("crimson_pressure_plate");

    /**
     * Key for {@code minecraft:crimson_roots}.
     */
    public static final TypedKey<Item> CRIMSON_ROOTS = create("crimson_roots");

    /**
     * Key for {@code minecraft:crimson_shelf}.
     */
    public static final TypedKey<Item> CRIMSON_SHELF = create("crimson_shelf");

    /**
     * Key for {@code minecraft:crimson_sign}.
     */
    public static final TypedKey<Item> CRIMSON_SIGN = create("crimson_sign");

    /**
     * Key for {@code minecraft:crimson_slab}.
     */
    public static final TypedKey<Item> CRIMSON_SLAB = create("crimson_slab");

    /**
     * Key for {@code minecraft:crimson_stairs}.
     */
    public static final TypedKey<Item> CRIMSON_STAIRS = create("crimson_stairs");

    /**
     * Key for {@code minecraft:crimson_stem}.
     */
    public static final TypedKey<Item> CRIMSON_STEM = create("crimson_stem");

    /**
     * Key for {@code minecraft:crimson_trapdoor}.
     */
    public static final TypedKey<Item> CRIMSON_TRAPDOOR = create("crimson_trapdoor");

    /**
     * Key for {@code minecraft:crossbow}.
     */
    public static final TypedKey<Item> CROSSBOW = create("crossbow");

    /**
     * Key for {@code minecraft:crying_obsidian}.
     */
    public static final TypedKey<Item> CRYING_OBSIDIAN = create("crying_obsidian");

    /**
     * Key for {@code minecraft:cut_copper}.
     */
    public static final TypedKey<Item> CUT_COPPER = create("cut_copper");

    /**
     * Key for {@code minecraft:cut_copper_slab}.
     */
    public static final TypedKey<Item> CUT_COPPER_SLAB = create("cut_copper_slab");

    /**
     * Key for {@code minecraft:cut_copper_stairs}.
     */
    public static final TypedKey<Item> CUT_COPPER_STAIRS = create("cut_copper_stairs");

    /**
     * Key for {@code minecraft:cut_red_sandstone}.
     */
    public static final TypedKey<Item> CUT_RED_SANDSTONE = create("cut_red_sandstone");

    /**
     * Key for {@code minecraft:cut_red_sandstone_slab}.
     */
    public static final TypedKey<Item> CUT_RED_SANDSTONE_SLAB = create("cut_red_sandstone_slab");

    /**
     * Key for {@code minecraft:cut_sandstone}.
     */
    public static final TypedKey<Item> CUT_SANDSTONE = create("cut_sandstone");

    /**
     * Key for {@code minecraft:cut_sandstone_slab}.
     */
    public static final TypedKey<Item> CUT_SANDSTONE_SLAB = create("cut_sandstone_slab");

    /**
     * Key for {@code minecraft:cyan_banner}.
     */
    public static final TypedKey<Item> CYAN_BANNER = create("cyan_banner");

    /**
     * Key for {@code minecraft:cyan_bed}.
     */
    public static final TypedKey<Item> CYAN_BED = create("cyan_bed");

    /**
     * Key for {@code minecraft:cyan_bundle}.
     */
    public static final TypedKey<Item> CYAN_BUNDLE = create("cyan_bundle");

    /**
     * Key for {@code minecraft:cyan_candle}.
     */
    public static final TypedKey<Item> CYAN_CANDLE = create("cyan_candle");

    /**
     * Key for {@code minecraft:cyan_carpet}.
     */
    public static final TypedKey<Item> CYAN_CARPET = create("cyan_carpet");

    /**
     * Key for {@code minecraft:cyan_concrete}.
     */
    public static final TypedKey<Item> CYAN_CONCRETE = create("cyan_concrete");

    /**
     * Key for {@code minecraft:cyan_concrete_powder}.
     */
    public static final TypedKey<Item> CYAN_CONCRETE_POWDER = create("cyan_concrete_powder");

    /**
     * Key for {@code minecraft:cyan_dye}.
     */
    public static final TypedKey<Item> CYAN_DYE = create("cyan_dye");

    /**
     * Key for {@code minecraft:cyan_glazed_terracotta}.
     */
    public static final TypedKey<Item> CYAN_GLAZED_TERRACOTTA = create("cyan_glazed_terracotta");

    /**
     * Key for {@code minecraft:cyan_harness}.
     */
    public static final TypedKey<Item> CYAN_HARNESS = create("cyan_harness");

    /**
     * Key for {@code minecraft:cyan_shulker_box}.
     */
    public static final TypedKey<Item> CYAN_SHULKER_BOX = create("cyan_shulker_box");

    /**
     * Key for {@code minecraft:cyan_stained_glass}.
     */
    public static final TypedKey<Item> CYAN_STAINED_GLASS = create("cyan_stained_glass");

    /**
     * Key for {@code minecraft:cyan_stained_glass_pane}.
     */
    public static final TypedKey<Item> CYAN_STAINED_GLASS_PANE = create("cyan_stained_glass_pane");

    /**
     * Key for {@code minecraft:cyan_terracotta}.
     */
    public static final TypedKey<Item> CYAN_TERRACOTTA = create("cyan_terracotta");

    /**
     * Key for {@code minecraft:cyan_wool}.
     */
    public static final TypedKey<Item> CYAN_WOOL = create("cyan_wool");

    /**
     * Key for {@code minecraft:damaged_anvil}.
     */
    public static final TypedKey<Item> DAMAGED_ANVIL = create("damaged_anvil");

    /**
     * Key for {@code minecraft:dandelion}.
     */
    public static final TypedKey<Item> DANDELION = create("dandelion");

    /**
     * Key for {@code minecraft:danger_pottery_sherd}.
     */
    public static final TypedKey<Item> DANGER_POTTERY_SHERD = create("danger_pottery_sherd");

    /**
     * Key for {@code minecraft:dark_oak_boat}.
     */
    public static final TypedKey<Item> DARK_OAK_BOAT = create("dark_oak_boat");

    /**
     * Key for {@code minecraft:dark_oak_button}.
     */
    public static final TypedKey<Item> DARK_OAK_BUTTON = create("dark_oak_button");

    /**
     * Key for {@code minecraft:dark_oak_chest_boat}.
     */
    public static final TypedKey<Item> DARK_OAK_CHEST_BOAT = create("dark_oak_chest_boat");

    /**
     * Key for {@code minecraft:dark_oak_door}.
     */
    public static final TypedKey<Item> DARK_OAK_DOOR = create("dark_oak_door");

    /**
     * Key for {@code minecraft:dark_oak_fence}.
     */
    public static final TypedKey<Item> DARK_OAK_FENCE = create("dark_oak_fence");

    /**
     * Key for {@code minecraft:dark_oak_fence_gate}.
     */
    public static final TypedKey<Item> DARK_OAK_FENCE_GATE = create("dark_oak_fence_gate");

    /**
     * Key for {@code minecraft:dark_oak_hanging_sign}.
     */
    public static final TypedKey<Item> DARK_OAK_HANGING_SIGN = create("dark_oak_hanging_sign");

    /**
     * Key for {@code minecraft:dark_oak_leaves}.
     */
    public static final TypedKey<Item> DARK_OAK_LEAVES = create("dark_oak_leaves");

    /**
     * Key for {@code minecraft:dark_oak_log}.
     */
    public static final TypedKey<Item> DARK_OAK_LOG = create("dark_oak_log");

    /**
     * Key for {@code minecraft:dark_oak_planks}.
     */
    public static final TypedKey<Item> DARK_OAK_PLANKS = create("dark_oak_planks");

    /**
     * Key for {@code minecraft:dark_oak_pressure_plate}.
     */
    public static final TypedKey<Item> DARK_OAK_PRESSURE_PLATE = create("dark_oak_pressure_plate");

    /**
     * Key for {@code minecraft:dark_oak_sapling}.
     */
    public static final TypedKey<Item> DARK_OAK_SAPLING = create("dark_oak_sapling");

    /**
     * Key for {@code minecraft:dark_oak_shelf}.
     */
    public static final TypedKey<Item> DARK_OAK_SHELF = create("dark_oak_shelf");

    /**
     * Key for {@code minecraft:dark_oak_sign}.
     */
    public static final TypedKey<Item> DARK_OAK_SIGN = create("dark_oak_sign");

    /**
     * Key for {@code minecraft:dark_oak_slab}.
     */
    public static final TypedKey<Item> DARK_OAK_SLAB = create("dark_oak_slab");

    /**
     * Key for {@code minecraft:dark_oak_stairs}.
     */
    public static final TypedKey<Item> DARK_OAK_STAIRS = create("dark_oak_stairs");

    /**
     * Key for {@code minecraft:dark_oak_trapdoor}.
     */
    public static final TypedKey<Item> DARK_OAK_TRAPDOOR = create("dark_oak_trapdoor");

    /**
     * Key for {@code minecraft:dark_oak_wood}.
     */
    public static final TypedKey<Item> DARK_OAK_WOOD = create("dark_oak_wood");

    /**
     * Key for {@code minecraft:dark_prismarine}.
     */
    public static final TypedKey<Item> DARK_PRISMARINE = create("dark_prismarine");

    /**
     * Key for {@code minecraft:dark_prismarine_slab}.
     */
    public static final TypedKey<Item> DARK_PRISMARINE_SLAB = create("dark_prismarine_slab");

    /**
     * Key for {@code minecraft:dark_prismarine_stairs}.
     */
    public static final TypedKey<Item> DARK_PRISMARINE_STAIRS = create("dark_prismarine_stairs");

    /**
     * Key for {@code minecraft:daylight_detector}.
     */
    public static final TypedKey<Item> DAYLIGHT_DETECTOR = create("daylight_detector");

    /**
     * Key for {@code minecraft:dead_brain_coral}.
     */
    public static final TypedKey<Item> DEAD_BRAIN_CORAL = create("dead_brain_coral");

    /**
     * Key for {@code minecraft:dead_brain_coral_block}.
     */
    public static final TypedKey<Item> DEAD_BRAIN_CORAL_BLOCK = create("dead_brain_coral_block");

    /**
     * Key for {@code minecraft:dead_brain_coral_fan}.
     */
    public static final TypedKey<Item> DEAD_BRAIN_CORAL_FAN = create("dead_brain_coral_fan");

    /**
     * Key for {@code minecraft:dead_bubble_coral}.
     */
    public static final TypedKey<Item> DEAD_BUBBLE_CORAL = create("dead_bubble_coral");

    /**
     * Key for {@code minecraft:dead_bubble_coral_block}.
     */
    public static final TypedKey<Item> DEAD_BUBBLE_CORAL_BLOCK = create("dead_bubble_coral_block");

    /**
     * Key for {@code minecraft:dead_bubble_coral_fan}.
     */
    public static final TypedKey<Item> DEAD_BUBBLE_CORAL_FAN = create("dead_bubble_coral_fan");

    /**
     * Key for {@code minecraft:dead_bush}.
     */
    public static final TypedKey<Item> DEAD_BUSH = create("dead_bush");

    /**
     * Key for {@code minecraft:dead_fire_coral}.
     */
    public static final TypedKey<Item> DEAD_FIRE_CORAL = create("dead_fire_coral");

    /**
     * Key for {@code minecraft:dead_fire_coral_block}.
     */
    public static final TypedKey<Item> DEAD_FIRE_CORAL_BLOCK = create("dead_fire_coral_block");

    /**
     * Key for {@code minecraft:dead_fire_coral_fan}.
     */
    public static final TypedKey<Item> DEAD_FIRE_CORAL_FAN = create("dead_fire_coral_fan");

    /**
     * Key for {@code minecraft:dead_horn_coral}.
     */
    public static final TypedKey<Item> DEAD_HORN_CORAL = create("dead_horn_coral");

    /**
     * Key for {@code minecraft:dead_horn_coral_block}.
     */
    public static final TypedKey<Item> DEAD_HORN_CORAL_BLOCK = create("dead_horn_coral_block");

    /**
     * Key for {@code minecraft:dead_horn_coral_fan}.
     */
    public static final TypedKey<Item> DEAD_HORN_CORAL_FAN = create("dead_horn_coral_fan");

    /**
     * Key for {@code minecraft:dead_tube_coral}.
     */
    public static final TypedKey<Item> DEAD_TUBE_CORAL = create("dead_tube_coral");

    /**
     * Key for {@code minecraft:dead_tube_coral_block}.
     */
    public static final TypedKey<Item> DEAD_TUBE_CORAL_BLOCK = create("dead_tube_coral_block");

    /**
     * Key for {@code minecraft:dead_tube_coral_fan}.
     */
    public static final TypedKey<Item> DEAD_TUBE_CORAL_FAN = create("dead_tube_coral_fan");

    /**
     * Key for {@code minecraft:debug_stick}.
     */
    public static final TypedKey<Item> DEBUG_STICK = create("debug_stick");

    /**
     * Key for {@code minecraft:decorated_pot}.
     */
    public static final TypedKey<Item> DECORATED_POT = create("decorated_pot");

    /**
     * Key for {@code minecraft:deepslate}.
     */
    public static final TypedKey<Item> DEEPSLATE = create("deepslate");

    /**
     * Key for {@code minecraft:deepslate_bricks}.
     */
    public static final TypedKey<Item> DEEPSLATE_BRICKS = create("deepslate_bricks");

    /**
     * Key for {@code minecraft:deepslate_brick_slab}.
     */
    public static final TypedKey<Item> DEEPSLATE_BRICK_SLAB = create("deepslate_brick_slab");

    /**
     * Key for {@code minecraft:deepslate_brick_stairs}.
     */
    public static final TypedKey<Item> DEEPSLATE_BRICK_STAIRS = create("deepslate_brick_stairs");

    /**
     * Key for {@code minecraft:deepslate_brick_wall}.
     */
    public static final TypedKey<Item> DEEPSLATE_BRICK_WALL = create("deepslate_brick_wall");

    /**
     * Key for {@code minecraft:deepslate_coal_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_COAL_ORE = create("deepslate_coal_ore");

    /**
     * Key for {@code minecraft:deepslate_copper_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_COPPER_ORE = create("deepslate_copper_ore");

    /**
     * Key for {@code minecraft:deepslate_diamond_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_DIAMOND_ORE = create("deepslate_diamond_ore");

    /**
     * Key for {@code minecraft:deepslate_emerald_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_EMERALD_ORE = create("deepslate_emerald_ore");

    /**
     * Key for {@code minecraft:deepslate_gold_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_GOLD_ORE = create("deepslate_gold_ore");

    /**
     * Key for {@code minecraft:deepslate_iron_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_IRON_ORE = create("deepslate_iron_ore");

    /**
     * Key for {@code minecraft:deepslate_lapis_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_LAPIS_ORE = create("deepslate_lapis_ore");

    /**
     * Key for {@code minecraft:deepslate_redstone_ore}.
     */
    public static final TypedKey<Item> DEEPSLATE_REDSTONE_ORE = create("deepslate_redstone_ore");

    /**
     * Key for {@code minecraft:deepslate_tiles}.
     */
    public static final TypedKey<Item> DEEPSLATE_TILES = create("deepslate_tiles");

    /**
     * Key for {@code minecraft:deepslate_tile_slab}.
     */
    public static final TypedKey<Item> DEEPSLATE_TILE_SLAB = create("deepslate_tile_slab");

    /**
     * Key for {@code minecraft:deepslate_tile_stairs}.
     */
    public static final TypedKey<Item> DEEPSLATE_TILE_STAIRS = create("deepslate_tile_stairs");

    /**
     * Key for {@code minecraft:deepslate_tile_wall}.
     */
    public static final TypedKey<Item> DEEPSLATE_TILE_WALL = create("deepslate_tile_wall");

    /**
     * Key for {@code minecraft:detector_rail}.
     */
    public static final TypedKey<Item> DETECTOR_RAIL = create("detector_rail");

    /**
     * Key for {@code minecraft:diamond}.
     */
    public static final TypedKey<Item> DIAMOND = create("diamond");

    /**
     * Key for {@code minecraft:diamond_axe}.
     */
    public static final TypedKey<Item> DIAMOND_AXE = create("diamond_axe");

    /**
     * Key for {@code minecraft:diamond_block}.
     */
    public static final TypedKey<Item> DIAMOND_BLOCK = create("diamond_block");

    /**
     * Key for {@code minecraft:diamond_boots}.
     */
    public static final TypedKey<Item> DIAMOND_BOOTS = create("diamond_boots");

    /**
     * Key for {@code minecraft:diamond_chestplate}.
     */
    public static final TypedKey<Item> DIAMOND_CHESTPLATE = create("diamond_chestplate");

    /**
     * Key for {@code minecraft:diamond_helmet}.
     */
    public static final TypedKey<Item> DIAMOND_HELMET = create("diamond_helmet");

    /**
     * Key for {@code minecraft:diamond_hoe}.
     */
    public static final TypedKey<Item> DIAMOND_HOE = create("diamond_hoe");

    /**
     * Key for {@code minecraft:diamond_horse_armor}.
     */
    public static final TypedKey<Item> DIAMOND_HORSE_ARMOR = create("diamond_horse_armor");

    /**
     * Key for {@code minecraft:diamond_leggings}.
     */
    public static final TypedKey<Item> DIAMOND_LEGGINGS = create("diamond_leggings");

    /**
     * Key for {@code minecraft:diamond_nautilus_armor}.
     */
    public static final TypedKey<Item> DIAMOND_NAUTILUS_ARMOR = create("diamond_nautilus_armor");

    /**
     * Key for {@code minecraft:diamond_ore}.
     */
    public static final TypedKey<Item> DIAMOND_ORE = create("diamond_ore");

    /**
     * Key for {@code minecraft:diamond_pickaxe}.
     */
    public static final TypedKey<Item> DIAMOND_PICKAXE = create("diamond_pickaxe");

    /**
     * Key for {@code minecraft:diamond_shovel}.
     */
    public static final TypedKey<Item> DIAMOND_SHOVEL = create("diamond_shovel");

    /**
     * Key for {@code minecraft:diamond_spear}.
     */
    public static final TypedKey<Item> DIAMOND_SPEAR = create("diamond_spear");

    /**
     * Key for {@code minecraft:diamond_sword}.
     */
    public static final TypedKey<Item> DIAMOND_SWORD = create("diamond_sword");

    /**
     * Key for {@code minecraft:diorite}.
     */
    public static final TypedKey<Item> DIORITE = create("diorite");

    /**
     * Key for {@code minecraft:diorite_slab}.
     */
    public static final TypedKey<Item> DIORITE_SLAB = create("diorite_slab");

    /**
     * Key for {@code minecraft:diorite_stairs}.
     */
    public static final TypedKey<Item> DIORITE_STAIRS = create("diorite_stairs");

    /**
     * Key for {@code minecraft:diorite_wall}.
     */
    public static final TypedKey<Item> DIORITE_WALL = create("diorite_wall");

    /**
     * Key for {@code minecraft:dirt}.
     */
    public static final TypedKey<Item> DIRT = create("dirt");

    /**
     * Key for {@code minecraft:dirt_path}.
     */
    public static final TypedKey<Item> DIRT_PATH = create("dirt_path");

    /**
     * Key for {@code minecraft:disc_fragment_5}.
     */
    public static final TypedKey<Item> DISC_FRAGMENT_5 = create("disc_fragment_5");

    /**
     * Key for {@code minecraft:dispenser}.
     */
    public static final TypedKey<Item> DISPENSER = create("dispenser");

    /**
     * Key for {@code minecraft:dolphin_spawn_egg}.
     */
    public static final TypedKey<Item> DOLPHIN_SPAWN_EGG = create("dolphin_spawn_egg");

    /**
     * Key for {@code minecraft:donkey_spawn_egg}.
     */
    public static final TypedKey<Item> DONKEY_SPAWN_EGG = create("donkey_spawn_egg");

    /**
     * Key for {@code minecraft:dragon_breath}.
     */
    public static final TypedKey<Item> DRAGON_BREATH = create("dragon_breath");

    /**
     * Key for {@code minecraft:dragon_egg}.
     */
    public static final TypedKey<Item> DRAGON_EGG = create("dragon_egg");

    /**
     * Key for {@code minecraft:dragon_head}.
     */
    public static final TypedKey<Item> DRAGON_HEAD = create("dragon_head");

    /**
     * Key for {@code minecraft:dried_ghast}.
     */
    public static final TypedKey<Item> DRIED_GHAST = create("dried_ghast");

    /**
     * Key for {@code minecraft:dried_kelp}.
     */
    public static final TypedKey<Item> DRIED_KELP = create("dried_kelp");

    /**
     * Key for {@code minecraft:dried_kelp_block}.
     */
    public static final TypedKey<Item> DRIED_KELP_BLOCK = create("dried_kelp_block");

    /**
     * Key for {@code minecraft:dripstone_block}.
     */
    public static final TypedKey<Item> DRIPSTONE_BLOCK = create("dripstone_block");

    /**
     * Key for {@code minecraft:dropper}.
     */
    public static final TypedKey<Item> DROPPER = create("dropper");

    /**
     * Key for {@code minecraft:drowned_spawn_egg}.
     */
    public static final TypedKey<Item> DROWNED_SPAWN_EGG = create("drowned_spawn_egg");

    /**
     * Key for {@code minecraft:dune_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> DUNE_ARMOR_TRIM_SMITHING_TEMPLATE = create("dune_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:echo_shard}.
     */
    public static final TypedKey<Item> ECHO_SHARD = create("echo_shard");

    /**
     * Key for {@code minecraft:egg}.
     */
    public static final TypedKey<Item> EGG = create("egg");

    /**
     * Key for {@code minecraft:elder_guardian_spawn_egg}.
     */
    public static final TypedKey<Item> ELDER_GUARDIAN_SPAWN_EGG = create("elder_guardian_spawn_egg");

    /**
     * Key for {@code minecraft:elytra}.
     */
    public static final TypedKey<Item> ELYTRA = create("elytra");

    /**
     * Key for {@code minecraft:emerald}.
     */
    public static final TypedKey<Item> EMERALD = create("emerald");

    /**
     * Key for {@code minecraft:emerald_block}.
     */
    public static final TypedKey<Item> EMERALD_BLOCK = create("emerald_block");

    /**
     * Key for {@code minecraft:emerald_ore}.
     */
    public static final TypedKey<Item> EMERALD_ORE = create("emerald_ore");

    /**
     * Key for {@code minecraft:enchanted_book}.
     */
    public static final TypedKey<Item> ENCHANTED_BOOK = create("enchanted_book");

    /**
     * Key for {@code minecraft:enchanted_golden_apple}.
     */
    public static final TypedKey<Item> ENCHANTED_GOLDEN_APPLE = create("enchanted_golden_apple");

    /**
     * Key for {@code minecraft:enchanting_table}.
     */
    public static final TypedKey<Item> ENCHANTING_TABLE = create("enchanting_table");

    /**
     * Key for {@code minecraft:enderman_spawn_egg}.
     */
    public static final TypedKey<Item> ENDERMAN_SPAWN_EGG = create("enderman_spawn_egg");

    /**
     * Key for {@code minecraft:endermite_spawn_egg}.
     */
    public static final TypedKey<Item> ENDERMITE_SPAWN_EGG = create("endermite_spawn_egg");

    /**
     * Key for {@code minecraft:ender_chest}.
     */
    public static final TypedKey<Item> ENDER_CHEST = create("ender_chest");

    /**
     * Key for {@code minecraft:ender_dragon_spawn_egg}.
     */
    public static final TypedKey<Item> ENDER_DRAGON_SPAWN_EGG = create("ender_dragon_spawn_egg");

    /**
     * Key for {@code minecraft:ender_eye}.
     */
    public static final TypedKey<Item> ENDER_EYE = create("ender_eye");

    /**
     * Key for {@code minecraft:ender_pearl}.
     */
    public static final TypedKey<Item> ENDER_PEARL = create("ender_pearl");

    /**
     * Key for {@code minecraft:end_crystal}.
     */
    public static final TypedKey<Item> END_CRYSTAL = create("end_crystal");

    /**
     * Key for {@code minecraft:end_portal_frame}.
     */
    public static final TypedKey<Item> END_PORTAL_FRAME = create("end_portal_frame");

    /**
     * Key for {@code minecraft:end_rod}.
     */
    public static final TypedKey<Item> END_ROD = create("end_rod");

    /**
     * Key for {@code minecraft:end_stone}.
     */
    public static final TypedKey<Item> END_STONE = create("end_stone");

    /**
     * Key for {@code minecraft:end_stone_bricks}.
     */
    public static final TypedKey<Item> END_STONE_BRICKS = create("end_stone_bricks");

    /**
     * Key for {@code minecraft:end_stone_brick_slab}.
     */
    public static final TypedKey<Item> END_STONE_BRICK_SLAB = create("end_stone_brick_slab");

    /**
     * Key for {@code minecraft:end_stone_brick_stairs}.
     */
    public static final TypedKey<Item> END_STONE_BRICK_STAIRS = create("end_stone_brick_stairs");

    /**
     * Key for {@code minecraft:end_stone_brick_wall}.
     */
    public static final TypedKey<Item> END_STONE_BRICK_WALL = create("end_stone_brick_wall");

    /**
     * Key for {@code minecraft:evoker_spawn_egg}.
     */
    public static final TypedKey<Item> EVOKER_SPAWN_EGG = create("evoker_spawn_egg");

    /**
     * Key for {@code minecraft:experience_bottle}.
     */
    public static final TypedKey<Item> EXPERIENCE_BOTTLE = create("experience_bottle");

    /**
     * Key for {@code minecraft:explorer_pottery_sherd}.
     */
    public static final TypedKey<Item> EXPLORER_POTTERY_SHERD = create("explorer_pottery_sherd");

    /**
     * Key for {@code minecraft:exposed_chiseled_copper}.
     */
    public static final TypedKey<Item> EXPOSED_CHISELED_COPPER = create("exposed_chiseled_copper");

    /**
     * Key for {@code minecraft:exposed_copper}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER = create("exposed_copper");

    /**
     * Key for {@code minecraft:exposed_copper_bars}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_BARS = create("exposed_copper_bars");

    /**
     * Key for {@code minecraft:exposed_copper_bulb}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_BULB = create("exposed_copper_bulb");

    /**
     * Key for {@code minecraft:exposed_copper_chain}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_CHAIN = create("exposed_copper_chain");

    /**
     * Key for {@code minecraft:exposed_copper_chest}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_CHEST = create("exposed_copper_chest");

    /**
     * Key for {@code minecraft:exposed_copper_door}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_DOOR = create("exposed_copper_door");

    /**
     * Key for {@code minecraft:exposed_copper_golem_statue}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_GOLEM_STATUE = create("exposed_copper_golem_statue");

    /**
     * Key for {@code minecraft:exposed_copper_grate}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_GRATE = create("exposed_copper_grate");

    /**
     * Key for {@code minecraft:exposed_copper_lantern}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_LANTERN = create("exposed_copper_lantern");

    /**
     * Key for {@code minecraft:exposed_copper_trapdoor}.
     */
    public static final TypedKey<Item> EXPOSED_COPPER_TRAPDOOR = create("exposed_copper_trapdoor");

    /**
     * Key for {@code minecraft:exposed_cut_copper}.
     */
    public static final TypedKey<Item> EXPOSED_CUT_COPPER = create("exposed_cut_copper");

    /**
     * Key for {@code minecraft:exposed_cut_copper_slab}.
     */
    public static final TypedKey<Item> EXPOSED_CUT_COPPER_SLAB = create("exposed_cut_copper_slab");

    /**
     * Key for {@code minecraft:exposed_cut_copper_stairs}.
     */
    public static final TypedKey<Item> EXPOSED_CUT_COPPER_STAIRS = create("exposed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:exposed_lightning_rod}.
     */
    public static final TypedKey<Item> EXPOSED_LIGHTNING_ROD = create("exposed_lightning_rod");

    /**
     * Key for {@code minecraft:eye_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> EYE_ARMOR_TRIM_SMITHING_TEMPLATE = create("eye_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:farmland}.
     */
    public static final TypedKey<Item> FARMLAND = create("farmland");

    /**
     * Key for {@code minecraft:feather}.
     */
    public static final TypedKey<Item> FEATHER = create("feather");

    /**
     * Key for {@code minecraft:fermented_spider_eye}.
     */
    public static final TypedKey<Item> FERMENTED_SPIDER_EYE = create("fermented_spider_eye");

    /**
     * Key for {@code minecraft:fern}.
     */
    public static final TypedKey<Item> FERN = create("fern");

    /**
     * Key for {@code minecraft:field_masoned_banner_pattern}.
     */
    public static final TypedKey<Item> FIELD_MASONED_BANNER_PATTERN = create("field_masoned_banner_pattern");

    /**
     * Key for {@code minecraft:filled_map}.
     */
    public static final TypedKey<Item> FILLED_MAP = create("filled_map");

    /**
     * Key for {@code minecraft:firefly_bush}.
     */
    public static final TypedKey<Item> FIREFLY_BUSH = create("firefly_bush");

    /**
     * Key for {@code minecraft:firework_rocket}.
     */
    public static final TypedKey<Item> FIREWORK_ROCKET = create("firework_rocket");

    /**
     * Key for {@code minecraft:firework_star}.
     */
    public static final TypedKey<Item> FIREWORK_STAR = create("firework_star");

    /**
     * Key for {@code minecraft:fire_charge}.
     */
    public static final TypedKey<Item> FIRE_CHARGE = create("fire_charge");

    /**
     * Key for {@code minecraft:fire_coral}.
     */
    public static final TypedKey<Item> FIRE_CORAL = create("fire_coral");

    /**
     * Key for {@code minecraft:fire_coral_block}.
     */
    public static final TypedKey<Item> FIRE_CORAL_BLOCK = create("fire_coral_block");

    /**
     * Key for {@code minecraft:fire_coral_fan}.
     */
    public static final TypedKey<Item> FIRE_CORAL_FAN = create("fire_coral_fan");

    /**
     * Key for {@code minecraft:fishing_rod}.
     */
    public static final TypedKey<Item> FISHING_ROD = create("fishing_rod");

    /**
     * Key for {@code minecraft:fletching_table}.
     */
    public static final TypedKey<Item> FLETCHING_TABLE = create("fletching_table");

    /**
     * Key for {@code minecraft:flint}.
     */
    public static final TypedKey<Item> FLINT = create("flint");

    /**
     * Key for {@code minecraft:flint_and_steel}.
     */
    public static final TypedKey<Item> FLINT_AND_STEEL = create("flint_and_steel");

    /**
     * Key for {@code minecraft:flowering_azalea}.
     */
    public static final TypedKey<Item> FLOWERING_AZALEA = create("flowering_azalea");

    /**
     * Key for {@code minecraft:flowering_azalea_leaves}.
     */
    public static final TypedKey<Item> FLOWERING_AZALEA_LEAVES = create("flowering_azalea_leaves");

    /**
     * Key for {@code minecraft:flower_banner_pattern}.
     */
    public static final TypedKey<Item> FLOWER_BANNER_PATTERN = create("flower_banner_pattern");

    /**
     * Key for {@code minecraft:flower_pot}.
     */
    public static final TypedKey<Item> FLOWER_POT = create("flower_pot");

    /**
     * Key for {@code minecraft:flow_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> FLOW_ARMOR_TRIM_SMITHING_TEMPLATE = create("flow_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:flow_banner_pattern}.
     */
    public static final TypedKey<Item> FLOW_BANNER_PATTERN = create("flow_banner_pattern");

    /**
     * Key for {@code minecraft:flow_pottery_sherd}.
     */
    public static final TypedKey<Item> FLOW_POTTERY_SHERD = create("flow_pottery_sherd");

    /**
     * Key for {@code minecraft:fox_spawn_egg}.
     */
    public static final TypedKey<Item> FOX_SPAWN_EGG = create("fox_spawn_egg");

    /**
     * Key for {@code minecraft:friend_pottery_sherd}.
     */
    public static final TypedKey<Item> FRIEND_POTTERY_SHERD = create("friend_pottery_sherd");

    /**
     * Key for {@code minecraft:frogspawn}.
     */
    public static final TypedKey<Item> FROGSPAWN = create("frogspawn");

    /**
     * Key for {@code minecraft:frog_spawn_egg}.
     */
    public static final TypedKey<Item> FROG_SPAWN_EGG = create("frog_spawn_egg");

    /**
     * Key for {@code minecraft:furnace}.
     */
    public static final TypedKey<Item> FURNACE = create("furnace");

    /**
     * Key for {@code minecraft:furnace_minecart}.
     */
    public static final TypedKey<Item> FURNACE_MINECART = create("furnace_minecart");

    /**
     * Key for {@code minecraft:ghast_spawn_egg}.
     */
    public static final TypedKey<Item> GHAST_SPAWN_EGG = create("ghast_spawn_egg");

    /**
     * Key for {@code minecraft:ghast_tear}.
     */
    public static final TypedKey<Item> GHAST_TEAR = create("ghast_tear");

    /**
     * Key for {@code minecraft:gilded_blackstone}.
     */
    public static final TypedKey<Item> GILDED_BLACKSTONE = create("gilded_blackstone");

    /**
     * Key for {@code minecraft:glass}.
     */
    public static final TypedKey<Item> GLASS = create("glass");

    /**
     * Key for {@code minecraft:glass_bottle}.
     */
    public static final TypedKey<Item> GLASS_BOTTLE = create("glass_bottle");

    /**
     * Key for {@code minecraft:glass_pane}.
     */
    public static final TypedKey<Item> GLASS_PANE = create("glass_pane");

    /**
     * Key for {@code minecraft:glistering_melon_slice}.
     */
    public static final TypedKey<Item> GLISTERING_MELON_SLICE = create("glistering_melon_slice");

    /**
     * Key for {@code minecraft:globe_banner_pattern}.
     */
    public static final TypedKey<Item> GLOBE_BANNER_PATTERN = create("globe_banner_pattern");

    /**
     * Key for {@code minecraft:glowstone}.
     */
    public static final TypedKey<Item> GLOWSTONE = create("glowstone");

    /**
     * Key for {@code minecraft:glowstone_dust}.
     */
    public static final TypedKey<Item> GLOWSTONE_DUST = create("glowstone_dust");

    /**
     * Key for {@code minecraft:glow_berries}.
     */
    public static final TypedKey<Item> GLOW_BERRIES = create("glow_berries");

    /**
     * Key for {@code minecraft:glow_ink_sac}.
     */
    public static final TypedKey<Item> GLOW_INK_SAC = create("glow_ink_sac");

    /**
     * Key for {@code minecraft:glow_item_frame}.
     */
    public static final TypedKey<Item> GLOW_ITEM_FRAME = create("glow_item_frame");

    /**
     * Key for {@code minecraft:glow_lichen}.
     */
    public static final TypedKey<Item> GLOW_LICHEN = create("glow_lichen");

    /**
     * Key for {@code minecraft:glow_squid_spawn_egg}.
     */
    public static final TypedKey<Item> GLOW_SQUID_SPAWN_EGG = create("glow_squid_spawn_egg");

    /**
     * Key for {@code minecraft:goat_horn}.
     */
    public static final TypedKey<Item> GOAT_HORN = create("goat_horn");

    /**
     * Key for {@code minecraft:goat_spawn_egg}.
     */
    public static final TypedKey<Item> GOAT_SPAWN_EGG = create("goat_spawn_egg");

    /**
     * Key for {@code minecraft:golden_apple}.
     */
    public static final TypedKey<Item> GOLDEN_APPLE = create("golden_apple");

    /**
     * Key for {@code minecraft:golden_axe}.
     */
    public static final TypedKey<Item> GOLDEN_AXE = create("golden_axe");

    /**
     * Key for {@code minecraft:golden_boots}.
     */
    public static final TypedKey<Item> GOLDEN_BOOTS = create("golden_boots");

    /**
     * Key for {@code minecraft:golden_carrot}.
     */
    public static final TypedKey<Item> GOLDEN_CARROT = create("golden_carrot");

    /**
     * Key for {@code minecraft:golden_chestplate}.
     */
    public static final TypedKey<Item> GOLDEN_CHESTPLATE = create("golden_chestplate");

    /**
     * Key for {@code minecraft:golden_dandelion}.
     */
    public static final TypedKey<Item> GOLDEN_DANDELION = create("golden_dandelion");

    /**
     * Key for {@code minecraft:golden_helmet}.
     */
    public static final TypedKey<Item> GOLDEN_HELMET = create("golden_helmet");

    /**
     * Key for {@code minecraft:golden_hoe}.
     */
    public static final TypedKey<Item> GOLDEN_HOE = create("golden_hoe");

    /**
     * Key for {@code minecraft:golden_horse_armor}.
     */
    public static final TypedKey<Item> GOLDEN_HORSE_ARMOR = create("golden_horse_armor");

    /**
     * Key for {@code minecraft:golden_leggings}.
     */
    public static final TypedKey<Item> GOLDEN_LEGGINGS = create("golden_leggings");

    /**
     * Key for {@code minecraft:golden_nautilus_armor}.
     */
    public static final TypedKey<Item> GOLDEN_NAUTILUS_ARMOR = create("golden_nautilus_armor");

    /**
     * Key for {@code minecraft:golden_pickaxe}.
     */
    public static final TypedKey<Item> GOLDEN_PICKAXE = create("golden_pickaxe");

    /**
     * Key for {@code minecraft:golden_shovel}.
     */
    public static final TypedKey<Item> GOLDEN_SHOVEL = create("golden_shovel");

    /**
     * Key for {@code minecraft:golden_spear}.
     */
    public static final TypedKey<Item> GOLDEN_SPEAR = create("golden_spear");

    /**
     * Key for {@code minecraft:golden_sword}.
     */
    public static final TypedKey<Item> GOLDEN_SWORD = create("golden_sword");

    /**
     * Key for {@code minecraft:gold_block}.
     */
    public static final TypedKey<Item> GOLD_BLOCK = create("gold_block");

    /**
     * Key for {@code minecraft:gold_ingot}.
     */
    public static final TypedKey<Item> GOLD_INGOT = create("gold_ingot");

    /**
     * Key for {@code minecraft:gold_nugget}.
     */
    public static final TypedKey<Item> GOLD_NUGGET = create("gold_nugget");

    /**
     * Key for {@code minecraft:gold_ore}.
     */
    public static final TypedKey<Item> GOLD_ORE = create("gold_ore");

    /**
     * Key for {@code minecraft:granite}.
     */
    public static final TypedKey<Item> GRANITE = create("granite");

    /**
     * Key for {@code minecraft:granite_slab}.
     */
    public static final TypedKey<Item> GRANITE_SLAB = create("granite_slab");

    /**
     * Key for {@code minecraft:granite_stairs}.
     */
    public static final TypedKey<Item> GRANITE_STAIRS = create("granite_stairs");

    /**
     * Key for {@code minecraft:granite_wall}.
     */
    public static final TypedKey<Item> GRANITE_WALL = create("granite_wall");

    /**
     * Key for {@code minecraft:grass_block}.
     */
    public static final TypedKey<Item> GRASS_BLOCK = create("grass_block");

    /**
     * Key for {@code minecraft:gravel}.
     */
    public static final TypedKey<Item> GRAVEL = create("gravel");

    /**
     * Key for {@code minecraft:gray_banner}.
     */
    public static final TypedKey<Item> GRAY_BANNER = create("gray_banner");

    /**
     * Key for {@code minecraft:gray_bed}.
     */
    public static final TypedKey<Item> GRAY_BED = create("gray_bed");

    /**
     * Key for {@code minecraft:gray_bundle}.
     */
    public static final TypedKey<Item> GRAY_BUNDLE = create("gray_bundle");

    /**
     * Key for {@code minecraft:gray_candle}.
     */
    public static final TypedKey<Item> GRAY_CANDLE = create("gray_candle");

    /**
     * Key for {@code minecraft:gray_carpet}.
     */
    public static final TypedKey<Item> GRAY_CARPET = create("gray_carpet");

    /**
     * Key for {@code minecraft:gray_concrete}.
     */
    public static final TypedKey<Item> GRAY_CONCRETE = create("gray_concrete");

    /**
     * Key for {@code minecraft:gray_concrete_powder}.
     */
    public static final TypedKey<Item> GRAY_CONCRETE_POWDER = create("gray_concrete_powder");

    /**
     * Key for {@code minecraft:gray_dye}.
     */
    public static final TypedKey<Item> GRAY_DYE = create("gray_dye");

    /**
     * Key for {@code minecraft:gray_glazed_terracotta}.
     */
    public static final TypedKey<Item> GRAY_GLAZED_TERRACOTTA = create("gray_glazed_terracotta");

    /**
     * Key for {@code minecraft:gray_harness}.
     */
    public static final TypedKey<Item> GRAY_HARNESS = create("gray_harness");

    /**
     * Key for {@code minecraft:gray_shulker_box}.
     */
    public static final TypedKey<Item> GRAY_SHULKER_BOX = create("gray_shulker_box");

    /**
     * Key for {@code minecraft:gray_stained_glass}.
     */
    public static final TypedKey<Item> GRAY_STAINED_GLASS = create("gray_stained_glass");

    /**
     * Key for {@code minecraft:gray_stained_glass_pane}.
     */
    public static final TypedKey<Item> GRAY_STAINED_GLASS_PANE = create("gray_stained_glass_pane");

    /**
     * Key for {@code minecraft:gray_terracotta}.
     */
    public static final TypedKey<Item> GRAY_TERRACOTTA = create("gray_terracotta");

    /**
     * Key for {@code minecraft:gray_wool}.
     */
    public static final TypedKey<Item> GRAY_WOOL = create("gray_wool");

    /**
     * Key for {@code minecraft:green_banner}.
     */
    public static final TypedKey<Item> GREEN_BANNER = create("green_banner");

    /**
     * Key for {@code minecraft:green_bed}.
     */
    public static final TypedKey<Item> GREEN_BED = create("green_bed");

    /**
     * Key for {@code minecraft:green_bundle}.
     */
    public static final TypedKey<Item> GREEN_BUNDLE = create("green_bundle");

    /**
     * Key for {@code minecraft:green_candle}.
     */
    public static final TypedKey<Item> GREEN_CANDLE = create("green_candle");

    /**
     * Key for {@code minecraft:green_carpet}.
     */
    public static final TypedKey<Item> GREEN_CARPET = create("green_carpet");

    /**
     * Key for {@code minecraft:green_concrete}.
     */
    public static final TypedKey<Item> GREEN_CONCRETE = create("green_concrete");

    /**
     * Key for {@code minecraft:green_concrete_powder}.
     */
    public static final TypedKey<Item> GREEN_CONCRETE_POWDER = create("green_concrete_powder");

    /**
     * Key for {@code minecraft:green_dye}.
     */
    public static final TypedKey<Item> GREEN_DYE = create("green_dye");

    /**
     * Key for {@code minecraft:green_glazed_terracotta}.
     */
    public static final TypedKey<Item> GREEN_GLAZED_TERRACOTTA = create("green_glazed_terracotta");

    /**
     * Key for {@code minecraft:green_harness}.
     */
    public static final TypedKey<Item> GREEN_HARNESS = create("green_harness");

    /**
     * Key for {@code minecraft:green_shulker_box}.
     */
    public static final TypedKey<Item> GREEN_SHULKER_BOX = create("green_shulker_box");

    /**
     * Key for {@code minecraft:green_stained_glass}.
     */
    public static final TypedKey<Item> GREEN_STAINED_GLASS = create("green_stained_glass");

    /**
     * Key for {@code minecraft:green_stained_glass_pane}.
     */
    public static final TypedKey<Item> GREEN_STAINED_GLASS_PANE = create("green_stained_glass_pane");

    /**
     * Key for {@code minecraft:green_terracotta}.
     */
    public static final TypedKey<Item> GREEN_TERRACOTTA = create("green_terracotta");

    /**
     * Key for {@code minecraft:green_wool}.
     */
    public static final TypedKey<Item> GREEN_WOOL = create("green_wool");

    /**
     * Key for {@code minecraft:grindstone}.
     */
    public static final TypedKey<Item> GRINDSTONE = create("grindstone");

    /**
     * Key for {@code minecraft:guardian_spawn_egg}.
     */
    public static final TypedKey<Item> GUARDIAN_SPAWN_EGG = create("guardian_spawn_egg");

    /**
     * Key for {@code minecraft:gunpowder}.
     */
    public static final TypedKey<Item> GUNPOWDER = create("gunpowder");

    /**
     * Key for {@code minecraft:guster_banner_pattern}.
     */
    public static final TypedKey<Item> GUSTER_BANNER_PATTERN = create("guster_banner_pattern");

    /**
     * Key for {@code minecraft:guster_pottery_sherd}.
     */
    public static final TypedKey<Item> GUSTER_POTTERY_SHERD = create("guster_pottery_sherd");

    /**
     * Key for {@code minecraft:hanging_roots}.
     */
    public static final TypedKey<Item> HANGING_ROOTS = create("hanging_roots");

    /**
     * Key for {@code minecraft:happy_ghast_spawn_egg}.
     */
    public static final TypedKey<Item> HAPPY_GHAST_SPAWN_EGG = create("happy_ghast_spawn_egg");

    /**
     * Key for {@code minecraft:hay_block}.
     */
    public static final TypedKey<Item> HAY_BLOCK = create("hay_block");

    /**
     * Key for {@code minecraft:heartbreak_pottery_sherd}.
     */
    public static final TypedKey<Item> HEARTBREAK_POTTERY_SHERD = create("heartbreak_pottery_sherd");

    /**
     * Key for {@code minecraft:heart_of_the_sea}.
     */
    public static final TypedKey<Item> HEART_OF_THE_SEA = create("heart_of_the_sea");

    /**
     * Key for {@code minecraft:heart_pottery_sherd}.
     */
    public static final TypedKey<Item> HEART_POTTERY_SHERD = create("heart_pottery_sherd");

    /**
     * Key for {@code minecraft:heavy_core}.
     */
    public static final TypedKey<Item> HEAVY_CORE = create("heavy_core");

    /**
     * Key for {@code minecraft:heavy_weighted_pressure_plate}.
     */
    public static final TypedKey<Item> HEAVY_WEIGHTED_PRESSURE_PLATE = create("heavy_weighted_pressure_plate");

    /**
     * Key for {@code minecraft:hoglin_spawn_egg}.
     */
    public static final TypedKey<Item> HOGLIN_SPAWN_EGG = create("hoglin_spawn_egg");

    /**
     * Key for {@code minecraft:honeycomb}.
     */
    public static final TypedKey<Item> HONEYCOMB = create("honeycomb");

    /**
     * Key for {@code minecraft:honeycomb_block}.
     */
    public static final TypedKey<Item> HONEYCOMB_BLOCK = create("honeycomb_block");

    /**
     * Key for {@code minecraft:honey_block}.
     */
    public static final TypedKey<Item> HONEY_BLOCK = create("honey_block");

    /**
     * Key for {@code minecraft:honey_bottle}.
     */
    public static final TypedKey<Item> HONEY_BOTTLE = create("honey_bottle");

    /**
     * Key for {@code minecraft:hopper}.
     */
    public static final TypedKey<Item> HOPPER = create("hopper");

    /**
     * Key for {@code minecraft:hopper_minecart}.
     */
    public static final TypedKey<Item> HOPPER_MINECART = create("hopper_minecart");

    /**
     * Key for {@code minecraft:horn_coral}.
     */
    public static final TypedKey<Item> HORN_CORAL = create("horn_coral");

    /**
     * Key for {@code minecraft:horn_coral_block}.
     */
    public static final TypedKey<Item> HORN_CORAL_BLOCK = create("horn_coral_block");

    /**
     * Key for {@code minecraft:horn_coral_fan}.
     */
    public static final TypedKey<Item> HORN_CORAL_FAN = create("horn_coral_fan");

    /**
     * Key for {@code minecraft:horse_spawn_egg}.
     */
    public static final TypedKey<Item> HORSE_SPAWN_EGG = create("horse_spawn_egg");

    /**
     * Key for {@code minecraft:host_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> HOST_ARMOR_TRIM_SMITHING_TEMPLATE = create("host_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:howl_pottery_sherd}.
     */
    public static final TypedKey<Item> HOWL_POTTERY_SHERD = create("howl_pottery_sherd");

    /**
     * Key for {@code minecraft:husk_spawn_egg}.
     */
    public static final TypedKey<Item> HUSK_SPAWN_EGG = create("husk_spawn_egg");

    /**
     * Key for {@code minecraft:ice}.
     */
    public static final TypedKey<Item> ICE = create("ice");

    /**
     * Key for {@code minecraft:infested_chiseled_stone_bricks}.
     */
    public static final TypedKey<Item> INFESTED_CHISELED_STONE_BRICKS = create("infested_chiseled_stone_bricks");

    /**
     * Key for {@code minecraft:infested_cobblestone}.
     */
    public static final TypedKey<Item> INFESTED_COBBLESTONE = create("infested_cobblestone");

    /**
     * Key for {@code minecraft:infested_cracked_stone_bricks}.
     */
    public static final TypedKey<Item> INFESTED_CRACKED_STONE_BRICKS = create("infested_cracked_stone_bricks");

    /**
     * Key for {@code minecraft:infested_deepslate}.
     */
    public static final TypedKey<Item> INFESTED_DEEPSLATE = create("infested_deepslate");

    /**
     * Key for {@code minecraft:infested_mossy_stone_bricks}.
     */
    public static final TypedKey<Item> INFESTED_MOSSY_STONE_BRICKS = create("infested_mossy_stone_bricks");

    /**
     * Key for {@code minecraft:infested_stone}.
     */
    public static final TypedKey<Item> INFESTED_STONE = create("infested_stone");

    /**
     * Key for {@code minecraft:infested_stone_bricks}.
     */
    public static final TypedKey<Item> INFESTED_STONE_BRICKS = create("infested_stone_bricks");

    /**
     * Key for {@code minecraft:ink_sac}.
     */
    public static final TypedKey<Item> INK_SAC = create("ink_sac");

    /**
     * Key for {@code minecraft:iron_axe}.
     */
    public static final TypedKey<Item> IRON_AXE = create("iron_axe");

    /**
     * Key for {@code minecraft:iron_bars}.
     */
    public static final TypedKey<Item> IRON_BARS = create("iron_bars");

    /**
     * Key for {@code minecraft:iron_block}.
     */
    public static final TypedKey<Item> IRON_BLOCK = create("iron_block");

    /**
     * Key for {@code minecraft:iron_boots}.
     */
    public static final TypedKey<Item> IRON_BOOTS = create("iron_boots");

    /**
     * Key for {@code minecraft:iron_chain}.
     */
    public static final TypedKey<Item> IRON_CHAIN = create("iron_chain");

    /**
     * Key for {@code minecraft:iron_chestplate}.
     */
    public static final TypedKey<Item> IRON_CHESTPLATE = create("iron_chestplate");

    /**
     * Key for {@code minecraft:iron_door}.
     */
    public static final TypedKey<Item> IRON_DOOR = create("iron_door");

    /**
     * Key for {@code minecraft:iron_golem_spawn_egg}.
     */
    public static final TypedKey<Item> IRON_GOLEM_SPAWN_EGG = create("iron_golem_spawn_egg");

    /**
     * Key for {@code minecraft:iron_helmet}.
     */
    public static final TypedKey<Item> IRON_HELMET = create("iron_helmet");

    /**
     * Key for {@code minecraft:iron_hoe}.
     */
    public static final TypedKey<Item> IRON_HOE = create("iron_hoe");

    /**
     * Key for {@code minecraft:iron_horse_armor}.
     */
    public static final TypedKey<Item> IRON_HORSE_ARMOR = create("iron_horse_armor");

    /**
     * Key for {@code minecraft:iron_ingot}.
     */
    public static final TypedKey<Item> IRON_INGOT = create("iron_ingot");

    /**
     * Key for {@code minecraft:iron_leggings}.
     */
    public static final TypedKey<Item> IRON_LEGGINGS = create("iron_leggings");

    /**
     * Key for {@code minecraft:iron_nautilus_armor}.
     */
    public static final TypedKey<Item> IRON_NAUTILUS_ARMOR = create("iron_nautilus_armor");

    /**
     * Key for {@code minecraft:iron_nugget}.
     */
    public static final TypedKey<Item> IRON_NUGGET = create("iron_nugget");

    /**
     * Key for {@code minecraft:iron_ore}.
     */
    public static final TypedKey<Item> IRON_ORE = create("iron_ore");

    /**
     * Key for {@code minecraft:iron_pickaxe}.
     */
    public static final TypedKey<Item> IRON_PICKAXE = create("iron_pickaxe");

    /**
     * Key for {@code minecraft:iron_shovel}.
     */
    public static final TypedKey<Item> IRON_SHOVEL = create("iron_shovel");

    /**
     * Key for {@code minecraft:iron_spear}.
     */
    public static final TypedKey<Item> IRON_SPEAR = create("iron_spear");

    /**
     * Key for {@code minecraft:iron_sword}.
     */
    public static final TypedKey<Item> IRON_SWORD = create("iron_sword");

    /**
     * Key for {@code minecraft:iron_trapdoor}.
     */
    public static final TypedKey<Item> IRON_TRAPDOOR = create("iron_trapdoor");

    /**
     * Key for {@code minecraft:item_frame}.
     */
    public static final TypedKey<Item> ITEM_FRAME = create("item_frame");

    /**
     * Key for {@code minecraft:jack_o_lantern}.
     */
    public static final TypedKey<Item> JACK_O_LANTERN = create("jack_o_lantern");

    /**
     * Key for {@code minecraft:jigsaw}.
     */
    public static final TypedKey<Item> JIGSAW = create("jigsaw");

    /**
     * Key for {@code minecraft:jukebox}.
     */
    public static final TypedKey<Item> JUKEBOX = create("jukebox");

    /**
     * Key for {@code minecraft:jungle_boat}.
     */
    public static final TypedKey<Item> JUNGLE_BOAT = create("jungle_boat");

    /**
     * Key for {@code minecraft:jungle_button}.
     */
    public static final TypedKey<Item> JUNGLE_BUTTON = create("jungle_button");

    /**
     * Key for {@code minecraft:jungle_chest_boat}.
     */
    public static final TypedKey<Item> JUNGLE_CHEST_BOAT = create("jungle_chest_boat");

    /**
     * Key for {@code minecraft:jungle_door}.
     */
    public static final TypedKey<Item> JUNGLE_DOOR = create("jungle_door");

    /**
     * Key for {@code minecraft:jungle_fence}.
     */
    public static final TypedKey<Item> JUNGLE_FENCE = create("jungle_fence");

    /**
     * Key for {@code minecraft:jungle_fence_gate}.
     */
    public static final TypedKey<Item> JUNGLE_FENCE_GATE = create("jungle_fence_gate");

    /**
     * Key for {@code minecraft:jungle_hanging_sign}.
     */
    public static final TypedKey<Item> JUNGLE_HANGING_SIGN = create("jungle_hanging_sign");

    /**
     * Key for {@code minecraft:jungle_leaves}.
     */
    public static final TypedKey<Item> JUNGLE_LEAVES = create("jungle_leaves");

    /**
     * Key for {@code minecraft:jungle_log}.
     */
    public static final TypedKey<Item> JUNGLE_LOG = create("jungle_log");

    /**
     * Key for {@code minecraft:jungle_planks}.
     */
    public static final TypedKey<Item> JUNGLE_PLANKS = create("jungle_planks");

    /**
     * Key for {@code minecraft:jungle_pressure_plate}.
     */
    public static final TypedKey<Item> JUNGLE_PRESSURE_PLATE = create("jungle_pressure_plate");

    /**
     * Key for {@code minecraft:jungle_sapling}.
     */
    public static final TypedKey<Item> JUNGLE_SAPLING = create("jungle_sapling");

    /**
     * Key for {@code minecraft:jungle_shelf}.
     */
    public static final TypedKey<Item> JUNGLE_SHELF = create("jungle_shelf");

    /**
     * Key for {@code minecraft:jungle_sign}.
     */
    public static final TypedKey<Item> JUNGLE_SIGN = create("jungle_sign");

    /**
     * Key for {@code minecraft:jungle_slab}.
     */
    public static final TypedKey<Item> JUNGLE_SLAB = create("jungle_slab");

    /**
     * Key for {@code minecraft:jungle_stairs}.
     */
    public static final TypedKey<Item> JUNGLE_STAIRS = create("jungle_stairs");

    /**
     * Key for {@code minecraft:jungle_trapdoor}.
     */
    public static final TypedKey<Item> JUNGLE_TRAPDOOR = create("jungle_trapdoor");

    /**
     * Key for {@code minecraft:jungle_wood}.
     */
    public static final TypedKey<Item> JUNGLE_WOOD = create("jungle_wood");

    /**
     * Key for {@code minecraft:kelp}.
     */
    public static final TypedKey<Item> KELP = create("kelp");

    /**
     * Key for {@code minecraft:knowledge_book}.
     */
    public static final TypedKey<Item> KNOWLEDGE_BOOK = create("knowledge_book");

    /**
     * Key for {@code minecraft:ladder}.
     */
    public static final TypedKey<Item> LADDER = create("ladder");

    /**
     * Key for {@code minecraft:lantern}.
     */
    public static final TypedKey<Item> LANTERN = create("lantern");

    /**
     * Key for {@code minecraft:lapis_block}.
     */
    public static final TypedKey<Item> LAPIS_BLOCK = create("lapis_block");

    /**
     * Key for {@code minecraft:lapis_lazuli}.
     */
    public static final TypedKey<Item> LAPIS_LAZULI = create("lapis_lazuli");

    /**
     * Key for {@code minecraft:lapis_ore}.
     */
    public static final TypedKey<Item> LAPIS_ORE = create("lapis_ore");

    /**
     * Key for {@code minecraft:large_amethyst_bud}.
     */
    public static final TypedKey<Item> LARGE_AMETHYST_BUD = create("large_amethyst_bud");

    /**
     * Key for {@code minecraft:large_fern}.
     */
    public static final TypedKey<Item> LARGE_FERN = create("large_fern");

    /**
     * Key for {@code minecraft:lava_bucket}.
     */
    public static final TypedKey<Item> LAVA_BUCKET = create("lava_bucket");

    /**
     * Key for {@code minecraft:lead}.
     */
    public static final TypedKey<Item> LEAD = create("lead");

    /**
     * Key for {@code minecraft:leaf_litter}.
     */
    public static final TypedKey<Item> LEAF_LITTER = create("leaf_litter");

    /**
     * Key for {@code minecraft:leather}.
     */
    public static final TypedKey<Item> LEATHER = create("leather");

    /**
     * Key for {@code minecraft:leather_boots}.
     */
    public static final TypedKey<Item> LEATHER_BOOTS = create("leather_boots");

    /**
     * Key for {@code minecraft:leather_chestplate}.
     */
    public static final TypedKey<Item> LEATHER_CHESTPLATE = create("leather_chestplate");

    /**
     * Key for {@code minecraft:leather_helmet}.
     */
    public static final TypedKey<Item> LEATHER_HELMET = create("leather_helmet");

    /**
     * Key for {@code minecraft:leather_horse_armor}.
     */
    public static final TypedKey<Item> LEATHER_HORSE_ARMOR = create("leather_horse_armor");

    /**
     * Key for {@code minecraft:leather_leggings}.
     */
    public static final TypedKey<Item> LEATHER_LEGGINGS = create("leather_leggings");

    /**
     * Key for {@code minecraft:lectern}.
     */
    public static final TypedKey<Item> LECTERN = create("lectern");

    /**
     * Key for {@code minecraft:lever}.
     */
    public static final TypedKey<Item> LEVER = create("lever");

    /**
     * Key for {@code minecraft:light}.
     */
    public static final TypedKey<Item> LIGHT = create("light");

    /**
     * Key for {@code minecraft:lightning_rod}.
     */
    public static final TypedKey<Item> LIGHTNING_ROD = create("lightning_rod");

    /**
     * Key for {@code minecraft:light_blue_banner}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_BANNER = create("light_blue_banner");

    /**
     * Key for {@code minecraft:light_blue_bed}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_BED = create("light_blue_bed");

    /**
     * Key for {@code minecraft:light_blue_bundle}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_BUNDLE = create("light_blue_bundle");

    /**
     * Key for {@code minecraft:light_blue_candle}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_CANDLE = create("light_blue_candle");

    /**
     * Key for {@code minecraft:light_blue_carpet}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_CARPET = create("light_blue_carpet");

    /**
     * Key for {@code minecraft:light_blue_concrete}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_CONCRETE = create("light_blue_concrete");

    /**
     * Key for {@code minecraft:light_blue_concrete_powder}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_CONCRETE_POWDER = create("light_blue_concrete_powder");

    /**
     * Key for {@code minecraft:light_blue_dye}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_DYE = create("light_blue_dye");

    /**
     * Key for {@code minecraft:light_blue_glazed_terracotta}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_GLAZED_TERRACOTTA = create("light_blue_glazed_terracotta");

    /**
     * Key for {@code minecraft:light_blue_harness}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_HARNESS = create("light_blue_harness");

    /**
     * Key for {@code minecraft:light_blue_shulker_box}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_SHULKER_BOX = create("light_blue_shulker_box");

    /**
     * Key for {@code minecraft:light_blue_stained_glass}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_STAINED_GLASS = create("light_blue_stained_glass");

    /**
     * Key for {@code minecraft:light_blue_stained_glass_pane}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_STAINED_GLASS_PANE = create("light_blue_stained_glass_pane");

    /**
     * Key for {@code minecraft:light_blue_terracotta}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_TERRACOTTA = create("light_blue_terracotta");

    /**
     * Key for {@code minecraft:light_blue_wool}.
     */
    public static final TypedKey<Item> LIGHT_BLUE_WOOL = create("light_blue_wool");

    /**
     * Key for {@code minecraft:light_gray_banner}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_BANNER = create("light_gray_banner");

    /**
     * Key for {@code minecraft:light_gray_bed}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_BED = create("light_gray_bed");

    /**
     * Key for {@code minecraft:light_gray_bundle}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_BUNDLE = create("light_gray_bundle");

    /**
     * Key for {@code minecraft:light_gray_candle}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_CANDLE = create("light_gray_candle");

    /**
     * Key for {@code minecraft:light_gray_carpet}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_CARPET = create("light_gray_carpet");

    /**
     * Key for {@code minecraft:light_gray_concrete}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_CONCRETE = create("light_gray_concrete");

    /**
     * Key for {@code minecraft:light_gray_concrete_powder}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_CONCRETE_POWDER = create("light_gray_concrete_powder");

    /**
     * Key for {@code minecraft:light_gray_dye}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_DYE = create("light_gray_dye");

    /**
     * Key for {@code minecraft:light_gray_glazed_terracotta}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_GLAZED_TERRACOTTA = create("light_gray_glazed_terracotta");

    /**
     * Key for {@code minecraft:light_gray_harness}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_HARNESS = create("light_gray_harness");

    /**
     * Key for {@code minecraft:light_gray_shulker_box}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_SHULKER_BOX = create("light_gray_shulker_box");

    /**
     * Key for {@code minecraft:light_gray_stained_glass}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_STAINED_GLASS = create("light_gray_stained_glass");

    /**
     * Key for {@code minecraft:light_gray_stained_glass_pane}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_STAINED_GLASS_PANE = create("light_gray_stained_glass_pane");

    /**
     * Key for {@code minecraft:light_gray_terracotta}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_TERRACOTTA = create("light_gray_terracotta");

    /**
     * Key for {@code minecraft:light_gray_wool}.
     */
    public static final TypedKey<Item> LIGHT_GRAY_WOOL = create("light_gray_wool");

    /**
     * Key for {@code minecraft:light_weighted_pressure_plate}.
     */
    public static final TypedKey<Item> LIGHT_WEIGHTED_PRESSURE_PLATE = create("light_weighted_pressure_plate");

    /**
     * Key for {@code minecraft:lilac}.
     */
    public static final TypedKey<Item> LILAC = create("lilac");

    /**
     * Key for {@code minecraft:lily_of_the_valley}.
     */
    public static final TypedKey<Item> LILY_OF_THE_VALLEY = create("lily_of_the_valley");

    /**
     * Key for {@code minecraft:lily_pad}.
     */
    public static final TypedKey<Item> LILY_PAD = create("lily_pad");

    /**
     * Key for {@code minecraft:lime_banner}.
     */
    public static final TypedKey<Item> LIME_BANNER = create("lime_banner");

    /**
     * Key for {@code minecraft:lime_bed}.
     */
    public static final TypedKey<Item> LIME_BED = create("lime_bed");

    /**
     * Key for {@code minecraft:lime_bundle}.
     */
    public static final TypedKey<Item> LIME_BUNDLE = create("lime_bundle");

    /**
     * Key for {@code minecraft:lime_candle}.
     */
    public static final TypedKey<Item> LIME_CANDLE = create("lime_candle");

    /**
     * Key for {@code minecraft:lime_carpet}.
     */
    public static final TypedKey<Item> LIME_CARPET = create("lime_carpet");

    /**
     * Key for {@code minecraft:lime_concrete}.
     */
    public static final TypedKey<Item> LIME_CONCRETE = create("lime_concrete");

    /**
     * Key for {@code minecraft:lime_concrete_powder}.
     */
    public static final TypedKey<Item> LIME_CONCRETE_POWDER = create("lime_concrete_powder");

    /**
     * Key for {@code minecraft:lime_dye}.
     */
    public static final TypedKey<Item> LIME_DYE = create("lime_dye");

    /**
     * Key for {@code minecraft:lime_glazed_terracotta}.
     */
    public static final TypedKey<Item> LIME_GLAZED_TERRACOTTA = create("lime_glazed_terracotta");

    /**
     * Key for {@code minecraft:lime_harness}.
     */
    public static final TypedKey<Item> LIME_HARNESS = create("lime_harness");

    /**
     * Key for {@code minecraft:lime_shulker_box}.
     */
    public static final TypedKey<Item> LIME_SHULKER_BOX = create("lime_shulker_box");

    /**
     * Key for {@code minecraft:lime_stained_glass}.
     */
    public static final TypedKey<Item> LIME_STAINED_GLASS = create("lime_stained_glass");

    /**
     * Key for {@code minecraft:lime_stained_glass_pane}.
     */
    public static final TypedKey<Item> LIME_STAINED_GLASS_PANE = create("lime_stained_glass_pane");

    /**
     * Key for {@code minecraft:lime_terracotta}.
     */
    public static final TypedKey<Item> LIME_TERRACOTTA = create("lime_terracotta");

    /**
     * Key for {@code minecraft:lime_wool}.
     */
    public static final TypedKey<Item> LIME_WOOL = create("lime_wool");

    /**
     * Key for {@code minecraft:lingering_potion}.
     */
    public static final TypedKey<Item> LINGERING_POTION = create("lingering_potion");

    /**
     * Key for {@code minecraft:llama_spawn_egg}.
     */
    public static final TypedKey<Item> LLAMA_SPAWN_EGG = create("llama_spawn_egg");

    /**
     * Key for {@code minecraft:lodestone}.
     */
    public static final TypedKey<Item> LODESTONE = create("lodestone");

    /**
     * Key for {@code minecraft:loom}.
     */
    public static final TypedKey<Item> LOOM = create("loom");

    /**
     * Key for {@code minecraft:mace}.
     */
    public static final TypedKey<Item> MACE = create("mace");

    /**
     * Key for {@code minecraft:magenta_banner}.
     */
    public static final TypedKey<Item> MAGENTA_BANNER = create("magenta_banner");

    /**
     * Key for {@code minecraft:magenta_bed}.
     */
    public static final TypedKey<Item> MAGENTA_BED = create("magenta_bed");

    /**
     * Key for {@code minecraft:magenta_bundle}.
     */
    public static final TypedKey<Item> MAGENTA_BUNDLE = create("magenta_bundle");

    /**
     * Key for {@code minecraft:magenta_candle}.
     */
    public static final TypedKey<Item> MAGENTA_CANDLE = create("magenta_candle");

    /**
     * Key for {@code minecraft:magenta_carpet}.
     */
    public static final TypedKey<Item> MAGENTA_CARPET = create("magenta_carpet");

    /**
     * Key for {@code minecraft:magenta_concrete}.
     */
    public static final TypedKey<Item> MAGENTA_CONCRETE = create("magenta_concrete");

    /**
     * Key for {@code minecraft:magenta_concrete_powder}.
     */
    public static final TypedKey<Item> MAGENTA_CONCRETE_POWDER = create("magenta_concrete_powder");

    /**
     * Key for {@code minecraft:magenta_dye}.
     */
    public static final TypedKey<Item> MAGENTA_DYE = create("magenta_dye");

    /**
     * Key for {@code minecraft:magenta_glazed_terracotta}.
     */
    public static final TypedKey<Item> MAGENTA_GLAZED_TERRACOTTA = create("magenta_glazed_terracotta");

    /**
     * Key for {@code minecraft:magenta_harness}.
     */
    public static final TypedKey<Item> MAGENTA_HARNESS = create("magenta_harness");

    /**
     * Key for {@code minecraft:magenta_shulker_box}.
     */
    public static final TypedKey<Item> MAGENTA_SHULKER_BOX = create("magenta_shulker_box");

    /**
     * Key for {@code minecraft:magenta_stained_glass}.
     */
    public static final TypedKey<Item> MAGENTA_STAINED_GLASS = create("magenta_stained_glass");

    /**
     * Key for {@code minecraft:magenta_stained_glass_pane}.
     */
    public static final TypedKey<Item> MAGENTA_STAINED_GLASS_PANE = create("magenta_stained_glass_pane");

    /**
     * Key for {@code minecraft:magenta_terracotta}.
     */
    public static final TypedKey<Item> MAGENTA_TERRACOTTA = create("magenta_terracotta");

    /**
     * Key for {@code minecraft:magenta_wool}.
     */
    public static final TypedKey<Item> MAGENTA_WOOL = create("magenta_wool");

    /**
     * Key for {@code minecraft:magma_block}.
     */
    public static final TypedKey<Item> MAGMA_BLOCK = create("magma_block");

    /**
     * Key for {@code minecraft:magma_cream}.
     */
    public static final TypedKey<Item> MAGMA_CREAM = create("magma_cream");

    /**
     * Key for {@code minecraft:magma_cube_spawn_egg}.
     */
    public static final TypedKey<Item> MAGMA_CUBE_SPAWN_EGG = create("magma_cube_spawn_egg");

    /**
     * Key for {@code minecraft:mangrove_boat}.
     */
    public static final TypedKey<Item> MANGROVE_BOAT = create("mangrove_boat");

    /**
     * Key for {@code minecraft:mangrove_button}.
     */
    public static final TypedKey<Item> MANGROVE_BUTTON = create("mangrove_button");

    /**
     * Key for {@code minecraft:mangrove_chest_boat}.
     */
    public static final TypedKey<Item> MANGROVE_CHEST_BOAT = create("mangrove_chest_boat");

    /**
     * Key for {@code minecraft:mangrove_door}.
     */
    public static final TypedKey<Item> MANGROVE_DOOR = create("mangrove_door");

    /**
     * Key for {@code minecraft:mangrove_fence}.
     */
    public static final TypedKey<Item> MANGROVE_FENCE = create("mangrove_fence");

    /**
     * Key for {@code minecraft:mangrove_fence_gate}.
     */
    public static final TypedKey<Item> MANGROVE_FENCE_GATE = create("mangrove_fence_gate");

    /**
     * Key for {@code minecraft:mangrove_hanging_sign}.
     */
    public static final TypedKey<Item> MANGROVE_HANGING_SIGN = create("mangrove_hanging_sign");

    /**
     * Key for {@code minecraft:mangrove_leaves}.
     */
    public static final TypedKey<Item> MANGROVE_LEAVES = create("mangrove_leaves");

    /**
     * Key for {@code minecraft:mangrove_log}.
     */
    public static final TypedKey<Item> MANGROVE_LOG = create("mangrove_log");

    /**
     * Key for {@code minecraft:mangrove_planks}.
     */
    public static final TypedKey<Item> MANGROVE_PLANKS = create("mangrove_planks");

    /**
     * Key for {@code minecraft:mangrove_pressure_plate}.
     */
    public static final TypedKey<Item> MANGROVE_PRESSURE_PLATE = create("mangrove_pressure_plate");

    /**
     * Key for {@code minecraft:mangrove_propagule}.
     */
    public static final TypedKey<Item> MANGROVE_PROPAGULE = create("mangrove_propagule");

    /**
     * Key for {@code minecraft:mangrove_roots}.
     */
    public static final TypedKey<Item> MANGROVE_ROOTS = create("mangrove_roots");

    /**
     * Key for {@code minecraft:mangrove_shelf}.
     */
    public static final TypedKey<Item> MANGROVE_SHELF = create("mangrove_shelf");

    /**
     * Key for {@code minecraft:mangrove_sign}.
     */
    public static final TypedKey<Item> MANGROVE_SIGN = create("mangrove_sign");

    /**
     * Key for {@code minecraft:mangrove_slab}.
     */
    public static final TypedKey<Item> MANGROVE_SLAB = create("mangrove_slab");

    /**
     * Key for {@code minecraft:mangrove_stairs}.
     */
    public static final TypedKey<Item> MANGROVE_STAIRS = create("mangrove_stairs");

    /**
     * Key for {@code minecraft:mangrove_trapdoor}.
     */
    public static final TypedKey<Item> MANGROVE_TRAPDOOR = create("mangrove_trapdoor");

    /**
     * Key for {@code minecraft:mangrove_wood}.
     */
    public static final TypedKey<Item> MANGROVE_WOOD = create("mangrove_wood");

    /**
     * Key for {@code minecraft:map}.
     */
    public static final TypedKey<Item> MAP = create("map");

    /**
     * Key for {@code minecraft:medium_amethyst_bud}.
     */
    public static final TypedKey<Item> MEDIUM_AMETHYST_BUD = create("medium_amethyst_bud");

    /**
     * Key for {@code minecraft:melon}.
     */
    public static final TypedKey<Item> MELON = create("melon");

    /**
     * Key for {@code minecraft:melon_seeds}.
     */
    public static final TypedKey<Item> MELON_SEEDS = create("melon_seeds");

    /**
     * Key for {@code minecraft:melon_slice}.
     */
    public static final TypedKey<Item> MELON_SLICE = create("melon_slice");

    /**
     * Key for {@code minecraft:milk_bucket}.
     */
    public static final TypedKey<Item> MILK_BUCKET = create("milk_bucket");

    /**
     * Key for {@code minecraft:minecart}.
     */
    public static final TypedKey<Item> MINECART = create("minecart");

    /**
     * Key for {@code minecraft:miner_pottery_sherd}.
     */
    public static final TypedKey<Item> MINER_POTTERY_SHERD = create("miner_pottery_sherd");

    /**
     * Key for {@code minecraft:mojang_banner_pattern}.
     */
    public static final TypedKey<Item> MOJANG_BANNER_PATTERN = create("mojang_banner_pattern");

    /**
     * Key for {@code minecraft:mooshroom_spawn_egg}.
     */
    public static final TypedKey<Item> MOOSHROOM_SPAWN_EGG = create("mooshroom_spawn_egg");

    /**
     * Key for {@code minecraft:mossy_cobblestone}.
     */
    public static final TypedKey<Item> MOSSY_COBBLESTONE = create("mossy_cobblestone");

    /**
     * Key for {@code minecraft:mossy_cobblestone_slab}.
     */
    public static final TypedKey<Item> MOSSY_COBBLESTONE_SLAB = create("mossy_cobblestone_slab");

    /**
     * Key for {@code minecraft:mossy_cobblestone_stairs}.
     */
    public static final TypedKey<Item> MOSSY_COBBLESTONE_STAIRS = create("mossy_cobblestone_stairs");

    /**
     * Key for {@code minecraft:mossy_cobblestone_wall}.
     */
    public static final TypedKey<Item> MOSSY_COBBLESTONE_WALL = create("mossy_cobblestone_wall");

    /**
     * Key for {@code minecraft:mossy_stone_bricks}.
     */
    public static final TypedKey<Item> MOSSY_STONE_BRICKS = create("mossy_stone_bricks");

    /**
     * Key for {@code minecraft:mossy_stone_brick_slab}.
     */
    public static final TypedKey<Item> MOSSY_STONE_BRICK_SLAB = create("mossy_stone_brick_slab");

    /**
     * Key for {@code minecraft:mossy_stone_brick_stairs}.
     */
    public static final TypedKey<Item> MOSSY_STONE_BRICK_STAIRS = create("mossy_stone_brick_stairs");

    /**
     * Key for {@code minecraft:mossy_stone_brick_wall}.
     */
    public static final TypedKey<Item> MOSSY_STONE_BRICK_WALL = create("mossy_stone_brick_wall");

    /**
     * Key for {@code minecraft:moss_block}.
     */
    public static final TypedKey<Item> MOSS_BLOCK = create("moss_block");

    /**
     * Key for {@code minecraft:moss_carpet}.
     */
    public static final TypedKey<Item> MOSS_CARPET = create("moss_carpet");

    /**
     * Key for {@code minecraft:mourner_pottery_sherd}.
     */
    public static final TypedKey<Item> MOURNER_POTTERY_SHERD = create("mourner_pottery_sherd");

    /**
     * Key for {@code minecraft:mud}.
     */
    public static final TypedKey<Item> MUD = create("mud");

    /**
     * Key for {@code minecraft:muddy_mangrove_roots}.
     */
    public static final TypedKey<Item> MUDDY_MANGROVE_ROOTS = create("muddy_mangrove_roots");

    /**
     * Key for {@code minecraft:mud_bricks}.
     */
    public static final TypedKey<Item> MUD_BRICKS = create("mud_bricks");

    /**
     * Key for {@code minecraft:mud_brick_slab}.
     */
    public static final TypedKey<Item> MUD_BRICK_SLAB = create("mud_brick_slab");

    /**
     * Key for {@code minecraft:mud_brick_stairs}.
     */
    public static final TypedKey<Item> MUD_BRICK_STAIRS = create("mud_brick_stairs");

    /**
     * Key for {@code minecraft:mud_brick_wall}.
     */
    public static final TypedKey<Item> MUD_BRICK_WALL = create("mud_brick_wall");

    /**
     * Key for {@code minecraft:mule_spawn_egg}.
     */
    public static final TypedKey<Item> MULE_SPAWN_EGG = create("mule_spawn_egg");

    /**
     * Key for {@code minecraft:mushroom_stem}.
     */
    public static final TypedKey<Item> MUSHROOM_STEM = create("mushroom_stem");

    /**
     * Key for {@code minecraft:mushroom_stew}.
     */
    public static final TypedKey<Item> MUSHROOM_STEW = create("mushroom_stew");

    /**
     * Key for {@code minecraft:music_disc_11}.
     */
    public static final TypedKey<Item> MUSIC_DISC_11 = create("music_disc_11");

    /**
     * Key for {@code minecraft:music_disc_13}.
     */
    public static final TypedKey<Item> MUSIC_DISC_13 = create("music_disc_13");

    /**
     * Key for {@code minecraft:music_disc_5}.
     */
    public static final TypedKey<Item> MUSIC_DISC_5 = create("music_disc_5");

    /**
     * Key for {@code minecraft:music_disc_blocks}.
     */
    public static final TypedKey<Item> MUSIC_DISC_BLOCKS = create("music_disc_blocks");

    /**
     * Key for {@code minecraft:music_disc_bounce}.
     */
    public static final TypedKey<Item> MUSIC_DISC_BOUNCE = create("music_disc_bounce");

    /**
     * Key for {@code minecraft:music_disc_cat}.
     */
    public static final TypedKey<Item> MUSIC_DISC_CAT = create("music_disc_cat");

    /**
     * Key for {@code minecraft:music_disc_chirp}.
     */
    public static final TypedKey<Item> MUSIC_DISC_CHIRP = create("music_disc_chirp");

    /**
     * Key for {@code minecraft:music_disc_creator}.
     */
    public static final TypedKey<Item> MUSIC_DISC_CREATOR = create("music_disc_creator");

    /**
     * Key for {@code minecraft:music_disc_creator_music_box}.
     */
    public static final TypedKey<Item> MUSIC_DISC_CREATOR_MUSIC_BOX = create("music_disc_creator_music_box");

    /**
     * Key for {@code minecraft:music_disc_far}.
     */
    public static final TypedKey<Item> MUSIC_DISC_FAR = create("music_disc_far");

    /**
     * Key for {@code minecraft:music_disc_lava_chicken}.
     */
    public static final TypedKey<Item> MUSIC_DISC_LAVA_CHICKEN = create("music_disc_lava_chicken");

    /**
     * Key for {@code minecraft:music_disc_mall}.
     */
    public static final TypedKey<Item> MUSIC_DISC_MALL = create("music_disc_mall");

    /**
     * Key for {@code minecraft:music_disc_mellohi}.
     */
    public static final TypedKey<Item> MUSIC_DISC_MELLOHI = create("music_disc_mellohi");

    /**
     * Key for {@code minecraft:music_disc_otherside}.
     */
    public static final TypedKey<Item> MUSIC_DISC_OTHERSIDE = create("music_disc_otherside");

    /**
     * Key for {@code minecraft:music_disc_pigstep}.
     */
    public static final TypedKey<Item> MUSIC_DISC_PIGSTEP = create("music_disc_pigstep");

    /**
     * Key for {@code minecraft:music_disc_precipice}.
     */
    public static final TypedKey<Item> MUSIC_DISC_PRECIPICE = create("music_disc_precipice");

    /**
     * Key for {@code minecraft:music_disc_relic}.
     */
    public static final TypedKey<Item> MUSIC_DISC_RELIC = create("music_disc_relic");

    /**
     * Key for {@code minecraft:music_disc_stal}.
     */
    public static final TypedKey<Item> MUSIC_DISC_STAL = create("music_disc_stal");

    /**
     * Key for {@code minecraft:music_disc_strad}.
     */
    public static final TypedKey<Item> MUSIC_DISC_STRAD = create("music_disc_strad");

    /**
     * Key for {@code minecraft:music_disc_tears}.
     */
    public static final TypedKey<Item> MUSIC_DISC_TEARS = create("music_disc_tears");

    /**
     * Key for {@code minecraft:music_disc_wait}.
     */
    public static final TypedKey<Item> MUSIC_DISC_WAIT = create("music_disc_wait");

    /**
     * Key for {@code minecraft:music_disc_ward}.
     */
    public static final TypedKey<Item> MUSIC_DISC_WARD = create("music_disc_ward");

    /**
     * Key for {@code minecraft:mutton}.
     */
    public static final TypedKey<Item> MUTTON = create("mutton");

    /**
     * Key for {@code minecraft:mycelium}.
     */
    public static final TypedKey<Item> MYCELIUM = create("mycelium");

    /**
     * Key for {@code minecraft:name_tag}.
     */
    public static final TypedKey<Item> NAME_TAG = create("name_tag");

    /**
     * Key for {@code minecraft:nautilus_shell}.
     */
    public static final TypedKey<Item> NAUTILUS_SHELL = create("nautilus_shell");

    /**
     * Key for {@code minecraft:nautilus_spawn_egg}.
     */
    public static final TypedKey<Item> NAUTILUS_SPAWN_EGG = create("nautilus_spawn_egg");

    /**
     * Key for {@code minecraft:netherite_axe}.
     */
    public static final TypedKey<Item> NETHERITE_AXE = create("netherite_axe");

    /**
     * Key for {@code minecraft:netherite_block}.
     */
    public static final TypedKey<Item> NETHERITE_BLOCK = create("netherite_block");

    /**
     * Key for {@code minecraft:netherite_boots}.
     */
    public static final TypedKey<Item> NETHERITE_BOOTS = create("netherite_boots");

    /**
     * Key for {@code minecraft:netherite_chestplate}.
     */
    public static final TypedKey<Item> NETHERITE_CHESTPLATE = create("netherite_chestplate");

    /**
     * Key for {@code minecraft:netherite_helmet}.
     */
    public static final TypedKey<Item> NETHERITE_HELMET = create("netherite_helmet");

    /**
     * Key for {@code minecraft:netherite_hoe}.
     */
    public static final TypedKey<Item> NETHERITE_HOE = create("netherite_hoe");

    /**
     * Key for {@code minecraft:netherite_horse_armor}.
     */
    public static final TypedKey<Item> NETHERITE_HORSE_ARMOR = create("netherite_horse_armor");

    /**
     * Key for {@code minecraft:netherite_ingot}.
     */
    public static final TypedKey<Item> NETHERITE_INGOT = create("netherite_ingot");

    /**
     * Key for {@code minecraft:netherite_leggings}.
     */
    public static final TypedKey<Item> NETHERITE_LEGGINGS = create("netherite_leggings");

    /**
     * Key for {@code minecraft:netherite_nautilus_armor}.
     */
    public static final TypedKey<Item> NETHERITE_NAUTILUS_ARMOR = create("netherite_nautilus_armor");

    /**
     * Key for {@code minecraft:netherite_pickaxe}.
     */
    public static final TypedKey<Item> NETHERITE_PICKAXE = create("netherite_pickaxe");

    /**
     * Key for {@code minecraft:netherite_scrap}.
     */
    public static final TypedKey<Item> NETHERITE_SCRAP = create("netherite_scrap");

    /**
     * Key for {@code minecraft:netherite_shovel}.
     */
    public static final TypedKey<Item> NETHERITE_SHOVEL = create("netherite_shovel");

    /**
     * Key for {@code minecraft:netherite_spear}.
     */
    public static final TypedKey<Item> NETHERITE_SPEAR = create("netherite_spear");

    /**
     * Key for {@code minecraft:netherite_sword}.
     */
    public static final TypedKey<Item> NETHERITE_SWORD = create("netherite_sword");

    /**
     * Key for {@code minecraft:netherite_upgrade_smithing_template}.
     */
    public static final TypedKey<Item> NETHERITE_UPGRADE_SMITHING_TEMPLATE = create("netherite_upgrade_smithing_template");

    /**
     * Key for {@code minecraft:netherrack}.
     */
    public static final TypedKey<Item> NETHERRACK = create("netherrack");

    /**
     * Key for {@code minecraft:nether_brick}.
     */
    public static final TypedKey<Item> NETHER_BRICK = create("nether_brick");

    /**
     * Key for {@code minecraft:nether_bricks}.
     */
    public static final TypedKey<Item> NETHER_BRICKS = create("nether_bricks");

    /**
     * Key for {@code minecraft:nether_brick_fence}.
     */
    public static final TypedKey<Item> NETHER_BRICK_FENCE = create("nether_brick_fence");

    /**
     * Key for {@code minecraft:nether_brick_slab}.
     */
    public static final TypedKey<Item> NETHER_BRICK_SLAB = create("nether_brick_slab");

    /**
     * Key for {@code minecraft:nether_brick_stairs}.
     */
    public static final TypedKey<Item> NETHER_BRICK_STAIRS = create("nether_brick_stairs");

    /**
     * Key for {@code minecraft:nether_brick_wall}.
     */
    public static final TypedKey<Item> NETHER_BRICK_WALL = create("nether_brick_wall");

    /**
     * Key for {@code minecraft:nether_gold_ore}.
     */
    public static final TypedKey<Item> NETHER_GOLD_ORE = create("nether_gold_ore");

    /**
     * Key for {@code minecraft:nether_quartz_ore}.
     */
    public static final TypedKey<Item> NETHER_QUARTZ_ORE = create("nether_quartz_ore");

    /**
     * Key for {@code minecraft:nether_sprouts}.
     */
    public static final TypedKey<Item> NETHER_SPROUTS = create("nether_sprouts");

    /**
     * Key for {@code minecraft:nether_star}.
     */
    public static final TypedKey<Item> NETHER_STAR = create("nether_star");

    /**
     * Key for {@code minecraft:nether_wart}.
     */
    public static final TypedKey<Item> NETHER_WART = create("nether_wart");

    /**
     * Key for {@code minecraft:nether_wart_block}.
     */
    public static final TypedKey<Item> NETHER_WART_BLOCK = create("nether_wart_block");

    /**
     * Key for {@code minecraft:note_block}.
     */
    public static final TypedKey<Item> NOTE_BLOCK = create("note_block");

    /**
     * Key for {@code minecraft:oak_boat}.
     */
    public static final TypedKey<Item> OAK_BOAT = create("oak_boat");

    /**
     * Key for {@code minecraft:oak_button}.
     */
    public static final TypedKey<Item> OAK_BUTTON = create("oak_button");

    /**
     * Key for {@code minecraft:oak_chest_boat}.
     */
    public static final TypedKey<Item> OAK_CHEST_BOAT = create("oak_chest_boat");

    /**
     * Key for {@code minecraft:oak_door}.
     */
    public static final TypedKey<Item> OAK_DOOR = create("oak_door");

    /**
     * Key for {@code minecraft:oak_fence}.
     */
    public static final TypedKey<Item> OAK_FENCE = create("oak_fence");

    /**
     * Key for {@code minecraft:oak_fence_gate}.
     */
    public static final TypedKey<Item> OAK_FENCE_GATE = create("oak_fence_gate");

    /**
     * Key for {@code minecraft:oak_hanging_sign}.
     */
    public static final TypedKey<Item> OAK_HANGING_SIGN = create("oak_hanging_sign");

    /**
     * Key for {@code minecraft:oak_leaves}.
     */
    public static final TypedKey<Item> OAK_LEAVES = create("oak_leaves");

    /**
     * Key for {@code minecraft:oak_log}.
     */
    public static final TypedKey<Item> OAK_LOG = create("oak_log");

    /**
     * Key for {@code minecraft:oak_planks}.
     */
    public static final TypedKey<Item> OAK_PLANKS = create("oak_planks");

    /**
     * Key for {@code minecraft:oak_pressure_plate}.
     */
    public static final TypedKey<Item> OAK_PRESSURE_PLATE = create("oak_pressure_plate");

    /**
     * Key for {@code minecraft:oak_sapling}.
     */
    public static final TypedKey<Item> OAK_SAPLING = create("oak_sapling");

    /**
     * Key for {@code minecraft:oak_shelf}.
     */
    public static final TypedKey<Item> OAK_SHELF = create("oak_shelf");

    /**
     * Key for {@code minecraft:oak_sign}.
     */
    public static final TypedKey<Item> OAK_SIGN = create("oak_sign");

    /**
     * Key for {@code minecraft:oak_slab}.
     */
    public static final TypedKey<Item> OAK_SLAB = create("oak_slab");

    /**
     * Key for {@code minecraft:oak_stairs}.
     */
    public static final TypedKey<Item> OAK_STAIRS = create("oak_stairs");

    /**
     * Key for {@code minecraft:oak_trapdoor}.
     */
    public static final TypedKey<Item> OAK_TRAPDOOR = create("oak_trapdoor");

    /**
     * Key for {@code minecraft:oak_wood}.
     */
    public static final TypedKey<Item> OAK_WOOD = create("oak_wood");

    /**
     * Key for {@code minecraft:observer}.
     */
    public static final TypedKey<Item> OBSERVER = create("observer");

    /**
     * Key for {@code minecraft:obsidian}.
     */
    public static final TypedKey<Item> OBSIDIAN = create("obsidian");

    /**
     * Key for {@code minecraft:ocelot_spawn_egg}.
     */
    public static final TypedKey<Item> OCELOT_SPAWN_EGG = create("ocelot_spawn_egg");

    /**
     * Key for {@code minecraft:ochre_froglight}.
     */
    public static final TypedKey<Item> OCHRE_FROGLIGHT = create("ochre_froglight");

    /**
     * Key for {@code minecraft:ominous_bottle}.
     */
    public static final TypedKey<Item> OMINOUS_BOTTLE = create("ominous_bottle");

    /**
     * Key for {@code minecraft:ominous_trial_key}.
     */
    public static final TypedKey<Item> OMINOUS_TRIAL_KEY = create("ominous_trial_key");

    /**
     * Key for {@code minecraft:open_eyeblossom}.
     */
    public static final TypedKey<Item> OPEN_EYEBLOSSOM = create("open_eyeblossom");

    /**
     * Key for {@code minecraft:orange_banner}.
     */
    public static final TypedKey<Item> ORANGE_BANNER = create("orange_banner");

    /**
     * Key for {@code minecraft:orange_bed}.
     */
    public static final TypedKey<Item> ORANGE_BED = create("orange_bed");

    /**
     * Key for {@code minecraft:orange_bundle}.
     */
    public static final TypedKey<Item> ORANGE_BUNDLE = create("orange_bundle");

    /**
     * Key for {@code minecraft:orange_candle}.
     */
    public static final TypedKey<Item> ORANGE_CANDLE = create("orange_candle");

    /**
     * Key for {@code minecraft:orange_carpet}.
     */
    public static final TypedKey<Item> ORANGE_CARPET = create("orange_carpet");

    /**
     * Key for {@code minecraft:orange_concrete}.
     */
    public static final TypedKey<Item> ORANGE_CONCRETE = create("orange_concrete");

    /**
     * Key for {@code minecraft:orange_concrete_powder}.
     */
    public static final TypedKey<Item> ORANGE_CONCRETE_POWDER = create("orange_concrete_powder");

    /**
     * Key for {@code minecraft:orange_dye}.
     */
    public static final TypedKey<Item> ORANGE_DYE = create("orange_dye");

    /**
     * Key for {@code minecraft:orange_glazed_terracotta}.
     */
    public static final TypedKey<Item> ORANGE_GLAZED_TERRACOTTA = create("orange_glazed_terracotta");

    /**
     * Key for {@code minecraft:orange_harness}.
     */
    public static final TypedKey<Item> ORANGE_HARNESS = create("orange_harness");

    /**
     * Key for {@code minecraft:orange_shulker_box}.
     */
    public static final TypedKey<Item> ORANGE_SHULKER_BOX = create("orange_shulker_box");

    /**
     * Key for {@code minecraft:orange_stained_glass}.
     */
    public static final TypedKey<Item> ORANGE_STAINED_GLASS = create("orange_stained_glass");

    /**
     * Key for {@code minecraft:orange_stained_glass_pane}.
     */
    public static final TypedKey<Item> ORANGE_STAINED_GLASS_PANE = create("orange_stained_glass_pane");

    /**
     * Key for {@code minecraft:orange_terracotta}.
     */
    public static final TypedKey<Item> ORANGE_TERRACOTTA = create("orange_terracotta");

    /**
     * Key for {@code minecraft:orange_tulip}.
     */
    public static final TypedKey<Item> ORANGE_TULIP = create("orange_tulip");

    /**
     * Key for {@code minecraft:orange_wool}.
     */
    public static final TypedKey<Item> ORANGE_WOOL = create("orange_wool");

    /**
     * Key for {@code minecraft:oxeye_daisy}.
     */
    public static final TypedKey<Item> OXEYE_DAISY = create("oxeye_daisy");

    /**
     * Key for {@code minecraft:oxidized_chiseled_copper}.
     */
    public static final TypedKey<Item> OXIDIZED_CHISELED_COPPER = create("oxidized_chiseled_copper");

    /**
     * Key for {@code minecraft:oxidized_copper}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER = create("oxidized_copper");

    /**
     * Key for {@code minecraft:oxidized_copper_bars}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_BARS = create("oxidized_copper_bars");

    /**
     * Key for {@code minecraft:oxidized_copper_bulb}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_BULB = create("oxidized_copper_bulb");

    /**
     * Key for {@code minecraft:oxidized_copper_chain}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_CHAIN = create("oxidized_copper_chain");

    /**
     * Key for {@code minecraft:oxidized_copper_chest}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_CHEST = create("oxidized_copper_chest");

    /**
     * Key for {@code minecraft:oxidized_copper_door}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_DOOR = create("oxidized_copper_door");

    /**
     * Key for {@code minecraft:oxidized_copper_golem_statue}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_GOLEM_STATUE = create("oxidized_copper_golem_statue");

    /**
     * Key for {@code minecraft:oxidized_copper_grate}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_GRATE = create("oxidized_copper_grate");

    /**
     * Key for {@code minecraft:oxidized_copper_lantern}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_LANTERN = create("oxidized_copper_lantern");

    /**
     * Key for {@code minecraft:oxidized_copper_trapdoor}.
     */
    public static final TypedKey<Item> OXIDIZED_COPPER_TRAPDOOR = create("oxidized_copper_trapdoor");

    /**
     * Key for {@code minecraft:oxidized_cut_copper}.
     */
    public static final TypedKey<Item> OXIDIZED_CUT_COPPER = create("oxidized_cut_copper");

    /**
     * Key for {@code minecraft:oxidized_cut_copper_slab}.
     */
    public static final TypedKey<Item> OXIDIZED_CUT_COPPER_SLAB = create("oxidized_cut_copper_slab");

    /**
     * Key for {@code minecraft:oxidized_cut_copper_stairs}.
     */
    public static final TypedKey<Item> OXIDIZED_CUT_COPPER_STAIRS = create("oxidized_cut_copper_stairs");

    /**
     * Key for {@code minecraft:oxidized_lightning_rod}.
     */
    public static final TypedKey<Item> OXIDIZED_LIGHTNING_ROD = create("oxidized_lightning_rod");

    /**
     * Key for {@code minecraft:packed_ice}.
     */
    public static final TypedKey<Item> PACKED_ICE = create("packed_ice");

    /**
     * Key for {@code minecraft:packed_mud}.
     */
    public static final TypedKey<Item> PACKED_MUD = create("packed_mud");

    /**
     * Key for {@code minecraft:painting}.
     */
    public static final TypedKey<Item> PAINTING = create("painting");

    /**
     * Key for {@code minecraft:pale_hanging_moss}.
     */
    public static final TypedKey<Item> PALE_HANGING_MOSS = create("pale_hanging_moss");

    /**
     * Key for {@code minecraft:pale_moss_block}.
     */
    public static final TypedKey<Item> PALE_MOSS_BLOCK = create("pale_moss_block");

    /**
     * Key for {@code minecraft:pale_moss_carpet}.
     */
    public static final TypedKey<Item> PALE_MOSS_CARPET = create("pale_moss_carpet");

    /**
     * Key for {@code minecraft:pale_oak_boat}.
     */
    public static final TypedKey<Item> PALE_OAK_BOAT = create("pale_oak_boat");

    /**
     * Key for {@code minecraft:pale_oak_button}.
     */
    public static final TypedKey<Item> PALE_OAK_BUTTON = create("pale_oak_button");

    /**
     * Key for {@code minecraft:pale_oak_chest_boat}.
     */
    public static final TypedKey<Item> PALE_OAK_CHEST_BOAT = create("pale_oak_chest_boat");

    /**
     * Key for {@code minecraft:pale_oak_door}.
     */
    public static final TypedKey<Item> PALE_OAK_DOOR = create("pale_oak_door");

    /**
     * Key for {@code minecraft:pale_oak_fence}.
     */
    public static final TypedKey<Item> PALE_OAK_FENCE = create("pale_oak_fence");

    /**
     * Key for {@code minecraft:pale_oak_fence_gate}.
     */
    public static final TypedKey<Item> PALE_OAK_FENCE_GATE = create("pale_oak_fence_gate");

    /**
     * Key for {@code minecraft:pale_oak_hanging_sign}.
     */
    public static final TypedKey<Item> PALE_OAK_HANGING_SIGN = create("pale_oak_hanging_sign");

    /**
     * Key for {@code minecraft:pale_oak_leaves}.
     */
    public static final TypedKey<Item> PALE_OAK_LEAVES = create("pale_oak_leaves");

    /**
     * Key for {@code minecraft:pale_oak_log}.
     */
    public static final TypedKey<Item> PALE_OAK_LOG = create("pale_oak_log");

    /**
     * Key for {@code minecraft:pale_oak_planks}.
     */
    public static final TypedKey<Item> PALE_OAK_PLANKS = create("pale_oak_planks");

    /**
     * Key for {@code minecraft:pale_oak_pressure_plate}.
     */
    public static final TypedKey<Item> PALE_OAK_PRESSURE_PLATE = create("pale_oak_pressure_plate");

    /**
     * Key for {@code minecraft:pale_oak_sapling}.
     */
    public static final TypedKey<Item> PALE_OAK_SAPLING = create("pale_oak_sapling");

    /**
     * Key for {@code minecraft:pale_oak_shelf}.
     */
    public static final TypedKey<Item> PALE_OAK_SHELF = create("pale_oak_shelf");

    /**
     * Key for {@code minecraft:pale_oak_sign}.
     */
    public static final TypedKey<Item> PALE_OAK_SIGN = create("pale_oak_sign");

    /**
     * Key for {@code minecraft:pale_oak_slab}.
     */
    public static final TypedKey<Item> PALE_OAK_SLAB = create("pale_oak_slab");

    /**
     * Key for {@code minecraft:pale_oak_stairs}.
     */
    public static final TypedKey<Item> PALE_OAK_STAIRS = create("pale_oak_stairs");

    /**
     * Key for {@code minecraft:pale_oak_trapdoor}.
     */
    public static final TypedKey<Item> PALE_OAK_TRAPDOOR = create("pale_oak_trapdoor");

    /**
     * Key for {@code minecraft:pale_oak_wood}.
     */
    public static final TypedKey<Item> PALE_OAK_WOOD = create("pale_oak_wood");

    /**
     * Key for {@code minecraft:panda_spawn_egg}.
     */
    public static final TypedKey<Item> PANDA_SPAWN_EGG = create("panda_spawn_egg");

    /**
     * Key for {@code minecraft:paper}.
     */
    public static final TypedKey<Item> PAPER = create("paper");

    /**
     * Key for {@code minecraft:parched_spawn_egg}.
     */
    public static final TypedKey<Item> PARCHED_SPAWN_EGG = create("parched_spawn_egg");

    /**
     * Key for {@code minecraft:parrot_spawn_egg}.
     */
    public static final TypedKey<Item> PARROT_SPAWN_EGG = create("parrot_spawn_egg");

    /**
     * Key for {@code minecraft:pearlescent_froglight}.
     */
    public static final TypedKey<Item> PEARLESCENT_FROGLIGHT = create("pearlescent_froglight");

    /**
     * Key for {@code minecraft:peony}.
     */
    public static final TypedKey<Item> PEONY = create("peony");

    /**
     * Key for {@code minecraft:petrified_oak_slab}.
     */
    public static final TypedKey<Item> PETRIFIED_OAK_SLAB = create("petrified_oak_slab");

    /**
     * Key for {@code minecraft:phantom_membrane}.
     */
    public static final TypedKey<Item> PHANTOM_MEMBRANE = create("phantom_membrane");

    /**
     * Key for {@code minecraft:phantom_spawn_egg}.
     */
    public static final TypedKey<Item> PHANTOM_SPAWN_EGG = create("phantom_spawn_egg");

    /**
     * Key for {@code minecraft:piglin_banner_pattern}.
     */
    public static final TypedKey<Item> PIGLIN_BANNER_PATTERN = create("piglin_banner_pattern");

    /**
     * Key for {@code minecraft:piglin_brute_spawn_egg}.
     */
    public static final TypedKey<Item> PIGLIN_BRUTE_SPAWN_EGG = create("piglin_brute_spawn_egg");

    /**
     * Key for {@code minecraft:piglin_head}.
     */
    public static final TypedKey<Item> PIGLIN_HEAD = create("piglin_head");

    /**
     * Key for {@code minecraft:piglin_spawn_egg}.
     */
    public static final TypedKey<Item> PIGLIN_SPAWN_EGG = create("piglin_spawn_egg");

    /**
     * Key for {@code minecraft:pig_spawn_egg}.
     */
    public static final TypedKey<Item> PIG_SPAWN_EGG = create("pig_spawn_egg");

    /**
     * Key for {@code minecraft:pillager_spawn_egg}.
     */
    public static final TypedKey<Item> PILLAGER_SPAWN_EGG = create("pillager_spawn_egg");

    /**
     * Key for {@code minecraft:pink_banner}.
     */
    public static final TypedKey<Item> PINK_BANNER = create("pink_banner");

    /**
     * Key for {@code minecraft:pink_bed}.
     */
    public static final TypedKey<Item> PINK_BED = create("pink_bed");

    /**
     * Key for {@code minecraft:pink_bundle}.
     */
    public static final TypedKey<Item> PINK_BUNDLE = create("pink_bundle");

    /**
     * Key for {@code minecraft:pink_candle}.
     */
    public static final TypedKey<Item> PINK_CANDLE = create("pink_candle");

    /**
     * Key for {@code minecraft:pink_carpet}.
     */
    public static final TypedKey<Item> PINK_CARPET = create("pink_carpet");

    /**
     * Key for {@code minecraft:pink_concrete}.
     */
    public static final TypedKey<Item> PINK_CONCRETE = create("pink_concrete");

    /**
     * Key for {@code minecraft:pink_concrete_powder}.
     */
    public static final TypedKey<Item> PINK_CONCRETE_POWDER = create("pink_concrete_powder");

    /**
     * Key for {@code minecraft:pink_dye}.
     */
    public static final TypedKey<Item> PINK_DYE = create("pink_dye");

    /**
     * Key for {@code minecraft:pink_glazed_terracotta}.
     */
    public static final TypedKey<Item> PINK_GLAZED_TERRACOTTA = create("pink_glazed_terracotta");

    /**
     * Key for {@code minecraft:pink_harness}.
     */
    public static final TypedKey<Item> PINK_HARNESS = create("pink_harness");

    /**
     * Key for {@code minecraft:pink_petals}.
     */
    public static final TypedKey<Item> PINK_PETALS = create("pink_petals");

    /**
     * Key for {@code minecraft:pink_shulker_box}.
     */
    public static final TypedKey<Item> PINK_SHULKER_BOX = create("pink_shulker_box");

    /**
     * Key for {@code minecraft:pink_stained_glass}.
     */
    public static final TypedKey<Item> PINK_STAINED_GLASS = create("pink_stained_glass");

    /**
     * Key for {@code minecraft:pink_stained_glass_pane}.
     */
    public static final TypedKey<Item> PINK_STAINED_GLASS_PANE = create("pink_stained_glass_pane");

    /**
     * Key for {@code minecraft:pink_terracotta}.
     */
    public static final TypedKey<Item> PINK_TERRACOTTA = create("pink_terracotta");

    /**
     * Key for {@code minecraft:pink_tulip}.
     */
    public static final TypedKey<Item> PINK_TULIP = create("pink_tulip");

    /**
     * Key for {@code minecraft:pink_wool}.
     */
    public static final TypedKey<Item> PINK_WOOL = create("pink_wool");

    /**
     * Key for {@code minecraft:piston}.
     */
    public static final TypedKey<Item> PISTON = create("piston");

    /**
     * Key for {@code minecraft:pitcher_plant}.
     */
    public static final TypedKey<Item> PITCHER_PLANT = create("pitcher_plant");

    /**
     * Key for {@code minecraft:pitcher_pod}.
     */
    public static final TypedKey<Item> PITCHER_POD = create("pitcher_pod");

    /**
     * Key for {@code minecraft:player_head}.
     */
    public static final TypedKey<Item> PLAYER_HEAD = create("player_head");

    /**
     * Key for {@code minecraft:plenty_pottery_sherd}.
     */
    public static final TypedKey<Item> PLENTY_POTTERY_SHERD = create("plenty_pottery_sherd");

    /**
     * Key for {@code minecraft:podzol}.
     */
    public static final TypedKey<Item> PODZOL = create("podzol");

    /**
     * Key for {@code minecraft:pointed_dripstone}.
     */
    public static final TypedKey<Item> POINTED_DRIPSTONE = create("pointed_dripstone");

    /**
     * Key for {@code minecraft:poisonous_potato}.
     */
    public static final TypedKey<Item> POISONOUS_POTATO = create("poisonous_potato");

    /**
     * Key for {@code minecraft:polar_bear_spawn_egg}.
     */
    public static final TypedKey<Item> POLAR_BEAR_SPAWN_EGG = create("polar_bear_spawn_egg");

    /**
     * Key for {@code minecraft:polished_andesite}.
     */
    public static final TypedKey<Item> POLISHED_ANDESITE = create("polished_andesite");

    /**
     * Key for {@code minecraft:polished_andesite_slab}.
     */
    public static final TypedKey<Item> POLISHED_ANDESITE_SLAB = create("polished_andesite_slab");

    /**
     * Key for {@code minecraft:polished_andesite_stairs}.
     */
    public static final TypedKey<Item> POLISHED_ANDESITE_STAIRS = create("polished_andesite_stairs");

    /**
     * Key for {@code minecraft:polished_basalt}.
     */
    public static final TypedKey<Item> POLISHED_BASALT = create("polished_basalt");

    /**
     * Key for {@code minecraft:polished_blackstone}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE = create("polished_blackstone");

    /**
     * Key for {@code minecraft:polished_blackstone_bricks}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_BRICKS = create("polished_blackstone_bricks");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_slab}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_BRICK_SLAB = create("polished_blackstone_brick_slab");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_stairs}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_BRICK_STAIRS = create("polished_blackstone_brick_stairs");

    /**
     * Key for {@code minecraft:polished_blackstone_brick_wall}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_BRICK_WALL = create("polished_blackstone_brick_wall");

    /**
     * Key for {@code minecraft:polished_blackstone_button}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_BUTTON = create("polished_blackstone_button");

    /**
     * Key for {@code minecraft:polished_blackstone_pressure_plate}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_PRESSURE_PLATE = create("polished_blackstone_pressure_plate");

    /**
     * Key for {@code minecraft:polished_blackstone_slab}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_SLAB = create("polished_blackstone_slab");

    /**
     * Key for {@code minecraft:polished_blackstone_stairs}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_STAIRS = create("polished_blackstone_stairs");

    /**
     * Key for {@code minecraft:polished_blackstone_wall}.
     */
    public static final TypedKey<Item> POLISHED_BLACKSTONE_WALL = create("polished_blackstone_wall");

    /**
     * Key for {@code minecraft:polished_cinnabar}.
     */
    public static final TypedKey<Item> POLISHED_CINNABAR = create("polished_cinnabar");

    /**
     * Key for {@code minecraft:polished_cinnabar_slab}.
     */
    public static final TypedKey<Item> POLISHED_CINNABAR_SLAB = create("polished_cinnabar_slab");

    /**
     * Key for {@code minecraft:polished_cinnabar_stairs}.
     */
    public static final TypedKey<Item> POLISHED_CINNABAR_STAIRS = create("polished_cinnabar_stairs");

    /**
     * Key for {@code minecraft:polished_cinnabar_wall}.
     */
    public static final TypedKey<Item> POLISHED_CINNABAR_WALL = create("polished_cinnabar_wall");

    /**
     * Key for {@code minecraft:polished_deepslate}.
     */
    public static final TypedKey<Item> POLISHED_DEEPSLATE = create("polished_deepslate");

    /**
     * Key for {@code minecraft:polished_deepslate_slab}.
     */
    public static final TypedKey<Item> POLISHED_DEEPSLATE_SLAB = create("polished_deepslate_slab");

    /**
     * Key for {@code minecraft:polished_deepslate_stairs}.
     */
    public static final TypedKey<Item> POLISHED_DEEPSLATE_STAIRS = create("polished_deepslate_stairs");

    /**
     * Key for {@code minecraft:polished_deepslate_wall}.
     */
    public static final TypedKey<Item> POLISHED_DEEPSLATE_WALL = create("polished_deepslate_wall");

    /**
     * Key for {@code minecraft:polished_diorite}.
     */
    public static final TypedKey<Item> POLISHED_DIORITE = create("polished_diorite");

    /**
     * Key for {@code minecraft:polished_diorite_slab}.
     */
    public static final TypedKey<Item> POLISHED_DIORITE_SLAB = create("polished_diorite_slab");

    /**
     * Key for {@code minecraft:polished_diorite_stairs}.
     */
    public static final TypedKey<Item> POLISHED_DIORITE_STAIRS = create("polished_diorite_stairs");

    /**
     * Key for {@code minecraft:polished_granite}.
     */
    public static final TypedKey<Item> POLISHED_GRANITE = create("polished_granite");

    /**
     * Key for {@code minecraft:polished_granite_slab}.
     */
    public static final TypedKey<Item> POLISHED_GRANITE_SLAB = create("polished_granite_slab");

    /**
     * Key for {@code minecraft:polished_granite_stairs}.
     */
    public static final TypedKey<Item> POLISHED_GRANITE_STAIRS = create("polished_granite_stairs");

    /**
     * Key for {@code minecraft:polished_sulfur}.
     */
    public static final TypedKey<Item> POLISHED_SULFUR = create("polished_sulfur");

    /**
     * Key for {@code minecraft:polished_sulfur_slab}.
     */
    public static final TypedKey<Item> POLISHED_SULFUR_SLAB = create("polished_sulfur_slab");

    /**
     * Key for {@code minecraft:polished_sulfur_stairs}.
     */
    public static final TypedKey<Item> POLISHED_SULFUR_STAIRS = create("polished_sulfur_stairs");

    /**
     * Key for {@code minecraft:polished_sulfur_wall}.
     */
    public static final TypedKey<Item> POLISHED_SULFUR_WALL = create("polished_sulfur_wall");

    /**
     * Key for {@code minecraft:polished_tuff}.
     */
    public static final TypedKey<Item> POLISHED_TUFF = create("polished_tuff");

    /**
     * Key for {@code minecraft:polished_tuff_slab}.
     */
    public static final TypedKey<Item> POLISHED_TUFF_SLAB = create("polished_tuff_slab");

    /**
     * Key for {@code minecraft:polished_tuff_stairs}.
     */
    public static final TypedKey<Item> POLISHED_TUFF_STAIRS = create("polished_tuff_stairs");

    /**
     * Key for {@code minecraft:polished_tuff_wall}.
     */
    public static final TypedKey<Item> POLISHED_TUFF_WALL = create("polished_tuff_wall");

    /**
     * Key for {@code minecraft:popped_chorus_fruit}.
     */
    public static final TypedKey<Item> POPPED_CHORUS_FRUIT = create("popped_chorus_fruit");

    /**
     * Key for {@code minecraft:poppy}.
     */
    public static final TypedKey<Item> POPPY = create("poppy");

    /**
     * Key for {@code minecraft:porkchop}.
     */
    public static final TypedKey<Item> PORKCHOP = create("porkchop");

    /**
     * Key for {@code minecraft:potato}.
     */
    public static final TypedKey<Item> POTATO = create("potato");

    /**
     * Key for {@code minecraft:potent_sulfur}.
     */
    public static final TypedKey<Item> POTENT_SULFUR = create("potent_sulfur");

    /**
     * Key for {@code minecraft:potion}.
     */
    public static final TypedKey<Item> POTION = create("potion");

    /**
     * Key for {@code minecraft:powder_snow_bucket}.
     */
    public static final TypedKey<Item> POWDER_SNOW_BUCKET = create("powder_snow_bucket");

    /**
     * Key for {@code minecraft:powered_rail}.
     */
    public static final TypedKey<Item> POWERED_RAIL = create("powered_rail");

    /**
     * Key for {@code minecraft:prismarine}.
     */
    public static final TypedKey<Item> PRISMARINE = create("prismarine");

    /**
     * Key for {@code minecraft:prismarine_bricks}.
     */
    public static final TypedKey<Item> PRISMARINE_BRICKS = create("prismarine_bricks");

    /**
     * Key for {@code minecraft:prismarine_brick_slab}.
     */
    public static final TypedKey<Item> PRISMARINE_BRICK_SLAB = create("prismarine_brick_slab");

    /**
     * Key for {@code minecraft:prismarine_brick_stairs}.
     */
    public static final TypedKey<Item> PRISMARINE_BRICK_STAIRS = create("prismarine_brick_stairs");

    /**
     * Key for {@code minecraft:prismarine_crystals}.
     */
    public static final TypedKey<Item> PRISMARINE_CRYSTALS = create("prismarine_crystals");

    /**
     * Key for {@code minecraft:prismarine_shard}.
     */
    public static final TypedKey<Item> PRISMARINE_SHARD = create("prismarine_shard");

    /**
     * Key for {@code minecraft:prismarine_slab}.
     */
    public static final TypedKey<Item> PRISMARINE_SLAB = create("prismarine_slab");

    /**
     * Key for {@code minecraft:prismarine_stairs}.
     */
    public static final TypedKey<Item> PRISMARINE_STAIRS = create("prismarine_stairs");

    /**
     * Key for {@code minecraft:prismarine_wall}.
     */
    public static final TypedKey<Item> PRISMARINE_WALL = create("prismarine_wall");

    /**
     * Key for {@code minecraft:prize_pottery_sherd}.
     */
    public static final TypedKey<Item> PRIZE_POTTERY_SHERD = create("prize_pottery_sherd");

    /**
     * Key for {@code minecraft:pufferfish}.
     */
    public static final TypedKey<Item> PUFFERFISH = create("pufferfish");

    /**
     * Key for {@code minecraft:pufferfish_bucket}.
     */
    public static final TypedKey<Item> PUFFERFISH_BUCKET = create("pufferfish_bucket");

    /**
     * Key for {@code minecraft:pufferfish_spawn_egg}.
     */
    public static final TypedKey<Item> PUFFERFISH_SPAWN_EGG = create("pufferfish_spawn_egg");

    /**
     * Key for {@code minecraft:pumpkin}.
     */
    public static final TypedKey<Item> PUMPKIN = create("pumpkin");

    /**
     * Key for {@code minecraft:pumpkin_pie}.
     */
    public static final TypedKey<Item> PUMPKIN_PIE = create("pumpkin_pie");

    /**
     * Key for {@code minecraft:pumpkin_seeds}.
     */
    public static final TypedKey<Item> PUMPKIN_SEEDS = create("pumpkin_seeds");

    /**
     * Key for {@code minecraft:purple_banner}.
     */
    public static final TypedKey<Item> PURPLE_BANNER = create("purple_banner");

    /**
     * Key for {@code minecraft:purple_bed}.
     */
    public static final TypedKey<Item> PURPLE_BED = create("purple_bed");

    /**
     * Key for {@code minecraft:purple_bundle}.
     */
    public static final TypedKey<Item> PURPLE_BUNDLE = create("purple_bundle");

    /**
     * Key for {@code minecraft:purple_candle}.
     */
    public static final TypedKey<Item> PURPLE_CANDLE = create("purple_candle");

    /**
     * Key for {@code minecraft:purple_carpet}.
     */
    public static final TypedKey<Item> PURPLE_CARPET = create("purple_carpet");

    /**
     * Key for {@code minecraft:purple_concrete}.
     */
    public static final TypedKey<Item> PURPLE_CONCRETE = create("purple_concrete");

    /**
     * Key for {@code minecraft:purple_concrete_powder}.
     */
    public static final TypedKey<Item> PURPLE_CONCRETE_POWDER = create("purple_concrete_powder");

    /**
     * Key for {@code minecraft:purple_dye}.
     */
    public static final TypedKey<Item> PURPLE_DYE = create("purple_dye");

    /**
     * Key for {@code minecraft:purple_glazed_terracotta}.
     */
    public static final TypedKey<Item> PURPLE_GLAZED_TERRACOTTA = create("purple_glazed_terracotta");

    /**
     * Key for {@code minecraft:purple_harness}.
     */
    public static final TypedKey<Item> PURPLE_HARNESS = create("purple_harness");

    /**
     * Key for {@code minecraft:purple_shulker_box}.
     */
    public static final TypedKey<Item> PURPLE_SHULKER_BOX = create("purple_shulker_box");

    /**
     * Key for {@code minecraft:purple_stained_glass}.
     */
    public static final TypedKey<Item> PURPLE_STAINED_GLASS = create("purple_stained_glass");

    /**
     * Key for {@code minecraft:purple_stained_glass_pane}.
     */
    public static final TypedKey<Item> PURPLE_STAINED_GLASS_PANE = create("purple_stained_glass_pane");

    /**
     * Key for {@code minecraft:purple_terracotta}.
     */
    public static final TypedKey<Item> PURPLE_TERRACOTTA = create("purple_terracotta");

    /**
     * Key for {@code minecraft:purple_wool}.
     */
    public static final TypedKey<Item> PURPLE_WOOL = create("purple_wool");

    /**
     * Key for {@code minecraft:purpur_block}.
     */
    public static final TypedKey<Item> PURPUR_BLOCK = create("purpur_block");

    /**
     * Key for {@code minecraft:purpur_pillar}.
     */
    public static final TypedKey<Item> PURPUR_PILLAR = create("purpur_pillar");

    /**
     * Key for {@code minecraft:purpur_slab}.
     */
    public static final TypedKey<Item> PURPUR_SLAB = create("purpur_slab");

    /**
     * Key for {@code minecraft:purpur_stairs}.
     */
    public static final TypedKey<Item> PURPUR_STAIRS = create("purpur_stairs");

    /**
     * Key for {@code minecraft:quartz}.
     */
    public static final TypedKey<Item> QUARTZ = create("quartz");

    /**
     * Key for {@code minecraft:quartz_block}.
     */
    public static final TypedKey<Item> QUARTZ_BLOCK = create("quartz_block");

    /**
     * Key for {@code minecraft:quartz_bricks}.
     */
    public static final TypedKey<Item> QUARTZ_BRICKS = create("quartz_bricks");

    /**
     * Key for {@code minecraft:quartz_pillar}.
     */
    public static final TypedKey<Item> QUARTZ_PILLAR = create("quartz_pillar");

    /**
     * Key for {@code minecraft:quartz_slab}.
     */
    public static final TypedKey<Item> QUARTZ_SLAB = create("quartz_slab");

    /**
     * Key for {@code minecraft:quartz_stairs}.
     */
    public static final TypedKey<Item> QUARTZ_STAIRS = create("quartz_stairs");

    /**
     * Key for {@code minecraft:rabbit}.
     */
    public static final TypedKey<Item> RABBIT = create("rabbit");

    /**
     * Key for {@code minecraft:rabbit_foot}.
     */
    public static final TypedKey<Item> RABBIT_FOOT = create("rabbit_foot");

    /**
     * Key for {@code minecraft:rabbit_hide}.
     */
    public static final TypedKey<Item> RABBIT_HIDE = create("rabbit_hide");

    /**
     * Key for {@code minecraft:rabbit_spawn_egg}.
     */
    public static final TypedKey<Item> RABBIT_SPAWN_EGG = create("rabbit_spawn_egg");

    /**
     * Key for {@code minecraft:rabbit_stew}.
     */
    public static final TypedKey<Item> RABBIT_STEW = create("rabbit_stew");

    /**
     * Key for {@code minecraft:rail}.
     */
    public static final TypedKey<Item> RAIL = create("rail");

    /**
     * Key for {@code minecraft:raiser_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> RAISER_ARMOR_TRIM_SMITHING_TEMPLATE = create("raiser_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:ravager_spawn_egg}.
     */
    public static final TypedKey<Item> RAVAGER_SPAWN_EGG = create("ravager_spawn_egg");

    /**
     * Key for {@code minecraft:raw_copper}.
     */
    public static final TypedKey<Item> RAW_COPPER = create("raw_copper");

    /**
     * Key for {@code minecraft:raw_copper_block}.
     */
    public static final TypedKey<Item> RAW_COPPER_BLOCK = create("raw_copper_block");

    /**
     * Key for {@code minecraft:raw_gold}.
     */
    public static final TypedKey<Item> RAW_GOLD = create("raw_gold");

    /**
     * Key for {@code minecraft:raw_gold_block}.
     */
    public static final TypedKey<Item> RAW_GOLD_BLOCK = create("raw_gold_block");

    /**
     * Key for {@code minecraft:raw_iron}.
     */
    public static final TypedKey<Item> RAW_IRON = create("raw_iron");

    /**
     * Key for {@code minecraft:raw_iron_block}.
     */
    public static final TypedKey<Item> RAW_IRON_BLOCK = create("raw_iron_block");

    /**
     * Key for {@code minecraft:recovery_compass}.
     */
    public static final TypedKey<Item> RECOVERY_COMPASS = create("recovery_compass");

    /**
     * Key for {@code minecraft:redstone}.
     */
    public static final TypedKey<Item> REDSTONE = create("redstone");

    /**
     * Key for {@code minecraft:redstone_block}.
     */
    public static final TypedKey<Item> REDSTONE_BLOCK = create("redstone_block");

    /**
     * Key for {@code minecraft:redstone_lamp}.
     */
    public static final TypedKey<Item> REDSTONE_LAMP = create("redstone_lamp");

    /**
     * Key for {@code minecraft:redstone_ore}.
     */
    public static final TypedKey<Item> REDSTONE_ORE = create("redstone_ore");

    /**
     * Key for {@code minecraft:redstone_torch}.
     */
    public static final TypedKey<Item> REDSTONE_TORCH = create("redstone_torch");

    /**
     * Key for {@code minecraft:red_banner}.
     */
    public static final TypedKey<Item> RED_BANNER = create("red_banner");

    /**
     * Key for {@code minecraft:red_bed}.
     */
    public static final TypedKey<Item> RED_BED = create("red_bed");

    /**
     * Key for {@code minecraft:red_bundle}.
     */
    public static final TypedKey<Item> RED_BUNDLE = create("red_bundle");

    /**
     * Key for {@code minecraft:red_candle}.
     */
    public static final TypedKey<Item> RED_CANDLE = create("red_candle");

    /**
     * Key for {@code minecraft:red_carpet}.
     */
    public static final TypedKey<Item> RED_CARPET = create("red_carpet");

    /**
     * Key for {@code minecraft:red_concrete}.
     */
    public static final TypedKey<Item> RED_CONCRETE = create("red_concrete");

    /**
     * Key for {@code minecraft:red_concrete_powder}.
     */
    public static final TypedKey<Item> RED_CONCRETE_POWDER = create("red_concrete_powder");

    /**
     * Key for {@code minecraft:red_dye}.
     */
    public static final TypedKey<Item> RED_DYE = create("red_dye");

    /**
     * Key for {@code minecraft:red_glazed_terracotta}.
     */
    public static final TypedKey<Item> RED_GLAZED_TERRACOTTA = create("red_glazed_terracotta");

    /**
     * Key for {@code minecraft:red_harness}.
     */
    public static final TypedKey<Item> RED_HARNESS = create("red_harness");

    /**
     * Key for {@code minecraft:red_mushroom}.
     */
    public static final TypedKey<Item> RED_MUSHROOM = create("red_mushroom");

    /**
     * Key for {@code minecraft:red_mushroom_block}.
     */
    public static final TypedKey<Item> RED_MUSHROOM_BLOCK = create("red_mushroom_block");

    /**
     * Key for {@code minecraft:red_nether_bricks}.
     */
    public static final TypedKey<Item> RED_NETHER_BRICKS = create("red_nether_bricks");

    /**
     * Key for {@code minecraft:red_nether_brick_slab}.
     */
    public static final TypedKey<Item> RED_NETHER_BRICK_SLAB = create("red_nether_brick_slab");

    /**
     * Key for {@code minecraft:red_nether_brick_stairs}.
     */
    public static final TypedKey<Item> RED_NETHER_BRICK_STAIRS = create("red_nether_brick_stairs");

    /**
     * Key for {@code minecraft:red_nether_brick_wall}.
     */
    public static final TypedKey<Item> RED_NETHER_BRICK_WALL = create("red_nether_brick_wall");

    /**
     * Key for {@code minecraft:red_sand}.
     */
    public static final TypedKey<Item> RED_SAND = create("red_sand");

    /**
     * Key for {@code minecraft:red_sandstone}.
     */
    public static final TypedKey<Item> RED_SANDSTONE = create("red_sandstone");

    /**
     * Key for {@code minecraft:red_sandstone_slab}.
     */
    public static final TypedKey<Item> RED_SANDSTONE_SLAB = create("red_sandstone_slab");

    /**
     * Key for {@code minecraft:red_sandstone_stairs}.
     */
    public static final TypedKey<Item> RED_SANDSTONE_STAIRS = create("red_sandstone_stairs");

    /**
     * Key for {@code minecraft:red_sandstone_wall}.
     */
    public static final TypedKey<Item> RED_SANDSTONE_WALL = create("red_sandstone_wall");

    /**
     * Key for {@code minecraft:red_shulker_box}.
     */
    public static final TypedKey<Item> RED_SHULKER_BOX = create("red_shulker_box");

    /**
     * Key for {@code minecraft:red_stained_glass}.
     */
    public static final TypedKey<Item> RED_STAINED_GLASS = create("red_stained_glass");

    /**
     * Key for {@code minecraft:red_stained_glass_pane}.
     */
    public static final TypedKey<Item> RED_STAINED_GLASS_PANE = create("red_stained_glass_pane");

    /**
     * Key for {@code minecraft:red_terracotta}.
     */
    public static final TypedKey<Item> RED_TERRACOTTA = create("red_terracotta");

    /**
     * Key for {@code minecraft:red_tulip}.
     */
    public static final TypedKey<Item> RED_TULIP = create("red_tulip");

    /**
     * Key for {@code minecraft:red_wool}.
     */
    public static final TypedKey<Item> RED_WOOL = create("red_wool");

    /**
     * Key for {@code minecraft:reinforced_deepslate}.
     */
    public static final TypedKey<Item> REINFORCED_DEEPSLATE = create("reinforced_deepslate");

    /**
     * Key for {@code minecraft:repeater}.
     */
    public static final TypedKey<Item> REPEATER = create("repeater");

    /**
     * Key for {@code minecraft:repeating_command_block}.
     */
    public static final TypedKey<Item> REPEATING_COMMAND_BLOCK = create("repeating_command_block");

    /**
     * Key for {@code minecraft:resin_block}.
     */
    public static final TypedKey<Item> RESIN_BLOCK = create("resin_block");

    /**
     * Key for {@code minecraft:resin_brick}.
     */
    public static final TypedKey<Item> RESIN_BRICK = create("resin_brick");

    /**
     * Key for {@code minecraft:resin_bricks}.
     */
    public static final TypedKey<Item> RESIN_BRICKS = create("resin_bricks");

    /**
     * Key for {@code minecraft:resin_brick_slab}.
     */
    public static final TypedKey<Item> RESIN_BRICK_SLAB = create("resin_brick_slab");

    /**
     * Key for {@code minecraft:resin_brick_stairs}.
     */
    public static final TypedKey<Item> RESIN_BRICK_STAIRS = create("resin_brick_stairs");

    /**
     * Key for {@code minecraft:resin_brick_wall}.
     */
    public static final TypedKey<Item> RESIN_BRICK_WALL = create("resin_brick_wall");

    /**
     * Key for {@code minecraft:resin_clump}.
     */
    public static final TypedKey<Item> RESIN_CLUMP = create("resin_clump");

    /**
     * Key for {@code minecraft:respawn_anchor}.
     */
    public static final TypedKey<Item> RESPAWN_ANCHOR = create("respawn_anchor");

    /**
     * Key for {@code minecraft:rib_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> RIB_ARMOR_TRIM_SMITHING_TEMPLATE = create("rib_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:rooted_dirt}.
     */
    public static final TypedKey<Item> ROOTED_DIRT = create("rooted_dirt");

    /**
     * Key for {@code minecraft:rose_bush}.
     */
    public static final TypedKey<Item> ROSE_BUSH = create("rose_bush");

    /**
     * Key for {@code minecraft:rotten_flesh}.
     */
    public static final TypedKey<Item> ROTTEN_FLESH = create("rotten_flesh");

    /**
     * Key for {@code minecraft:saddle}.
     */
    public static final TypedKey<Item> SADDLE = create("saddle");

    /**
     * Key for {@code minecraft:salmon}.
     */
    public static final TypedKey<Item> SALMON = create("salmon");

    /**
     * Key for {@code minecraft:salmon_bucket}.
     */
    public static final TypedKey<Item> SALMON_BUCKET = create("salmon_bucket");

    /**
     * Key for {@code minecraft:salmon_spawn_egg}.
     */
    public static final TypedKey<Item> SALMON_SPAWN_EGG = create("salmon_spawn_egg");

    /**
     * Key for {@code minecraft:sand}.
     */
    public static final TypedKey<Item> SAND = create("sand");

    /**
     * Key for {@code minecraft:sandstone}.
     */
    public static final TypedKey<Item> SANDSTONE = create("sandstone");

    /**
     * Key for {@code minecraft:sandstone_slab}.
     */
    public static final TypedKey<Item> SANDSTONE_SLAB = create("sandstone_slab");

    /**
     * Key for {@code minecraft:sandstone_stairs}.
     */
    public static final TypedKey<Item> SANDSTONE_STAIRS = create("sandstone_stairs");

    /**
     * Key for {@code minecraft:sandstone_wall}.
     */
    public static final TypedKey<Item> SANDSTONE_WALL = create("sandstone_wall");

    /**
     * Key for {@code minecraft:scaffolding}.
     */
    public static final TypedKey<Item> SCAFFOLDING = create("scaffolding");

    /**
     * Key for {@code minecraft:scrape_pottery_sherd}.
     */
    public static final TypedKey<Item> SCRAPE_POTTERY_SHERD = create("scrape_pottery_sherd");

    /**
     * Key for {@code minecraft:sculk}.
     */
    public static final TypedKey<Item> SCULK = create("sculk");

    /**
     * Key for {@code minecraft:sculk_catalyst}.
     */
    public static final TypedKey<Item> SCULK_CATALYST = create("sculk_catalyst");

    /**
     * Key for {@code minecraft:sculk_sensor}.
     */
    public static final TypedKey<Item> SCULK_SENSOR = create("sculk_sensor");

    /**
     * Key for {@code minecraft:sculk_shrieker}.
     */
    public static final TypedKey<Item> SCULK_SHRIEKER = create("sculk_shrieker");

    /**
     * Key for {@code minecraft:sculk_vein}.
     */
    public static final TypedKey<Item> SCULK_VEIN = create("sculk_vein");

    /**
     * Key for {@code minecraft:seagrass}.
     */
    public static final TypedKey<Item> SEAGRASS = create("seagrass");

    /**
     * Key for {@code minecraft:sea_lantern}.
     */
    public static final TypedKey<Item> SEA_LANTERN = create("sea_lantern");

    /**
     * Key for {@code minecraft:sea_pickle}.
     */
    public static final TypedKey<Item> SEA_PICKLE = create("sea_pickle");

    /**
     * Key for {@code minecraft:sentry_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE = create("sentry_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:shaper_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE = create("shaper_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:sheaf_pottery_sherd}.
     */
    public static final TypedKey<Item> SHEAF_POTTERY_SHERD = create("sheaf_pottery_sherd");

    /**
     * Key for {@code minecraft:shears}.
     */
    public static final TypedKey<Item> SHEARS = create("shears");

    /**
     * Key for {@code minecraft:sheep_spawn_egg}.
     */
    public static final TypedKey<Item> SHEEP_SPAWN_EGG = create("sheep_spawn_egg");

    /**
     * Key for {@code minecraft:shelter_pottery_sherd}.
     */
    public static final TypedKey<Item> SHELTER_POTTERY_SHERD = create("shelter_pottery_sherd");

    /**
     * Key for {@code minecraft:shield}.
     */
    public static final TypedKey<Item> SHIELD = create("shield");

    /**
     * Key for {@code minecraft:short_dry_grass}.
     */
    public static final TypedKey<Item> SHORT_DRY_GRASS = create("short_dry_grass");

    /**
     * Key for {@code minecraft:short_grass}.
     */
    public static final TypedKey<Item> SHORT_GRASS = create("short_grass");

    /**
     * Key for {@code minecraft:shroomlight}.
     */
    public static final TypedKey<Item> SHROOMLIGHT = create("shroomlight");

    /**
     * Key for {@code minecraft:shulker_box}.
     */
    public static final TypedKey<Item> SHULKER_BOX = create("shulker_box");

    /**
     * Key for {@code minecraft:shulker_shell}.
     */
    public static final TypedKey<Item> SHULKER_SHELL = create("shulker_shell");

    /**
     * Key for {@code minecraft:shulker_spawn_egg}.
     */
    public static final TypedKey<Item> SHULKER_SPAWN_EGG = create("shulker_spawn_egg");

    /**
     * Key for {@code minecraft:silence_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE = create("silence_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:silverfish_spawn_egg}.
     */
    public static final TypedKey<Item> SILVERFISH_SPAWN_EGG = create("silverfish_spawn_egg");

    /**
     * Key for {@code minecraft:skeleton_horse_spawn_egg}.
     */
    public static final TypedKey<Item> SKELETON_HORSE_SPAWN_EGG = create("skeleton_horse_spawn_egg");

    /**
     * Key for {@code minecraft:skeleton_skull}.
     */
    public static final TypedKey<Item> SKELETON_SKULL = create("skeleton_skull");

    /**
     * Key for {@code minecraft:skeleton_spawn_egg}.
     */
    public static final TypedKey<Item> SKELETON_SPAWN_EGG = create("skeleton_spawn_egg");

    /**
     * Key for {@code minecraft:skull_banner_pattern}.
     */
    public static final TypedKey<Item> SKULL_BANNER_PATTERN = create("skull_banner_pattern");

    /**
     * Key for {@code minecraft:skull_pottery_sherd}.
     */
    public static final TypedKey<Item> SKULL_POTTERY_SHERD = create("skull_pottery_sherd");

    /**
     * Key for {@code minecraft:slime_ball}.
     */
    public static final TypedKey<Item> SLIME_BALL = create("slime_ball");

    /**
     * Key for {@code minecraft:slime_block}.
     */
    public static final TypedKey<Item> SLIME_BLOCK = create("slime_block");

    /**
     * Key for {@code minecraft:slime_spawn_egg}.
     */
    public static final TypedKey<Item> SLIME_SPAWN_EGG = create("slime_spawn_egg");

    /**
     * Key for {@code minecraft:small_amethyst_bud}.
     */
    public static final TypedKey<Item> SMALL_AMETHYST_BUD = create("small_amethyst_bud");

    /**
     * Key for {@code minecraft:small_dripleaf}.
     */
    public static final TypedKey<Item> SMALL_DRIPLEAF = create("small_dripleaf");

    /**
     * Key for {@code minecraft:smithing_table}.
     */
    public static final TypedKey<Item> SMITHING_TABLE = create("smithing_table");

    /**
     * Key for {@code minecraft:smoker}.
     */
    public static final TypedKey<Item> SMOKER = create("smoker");

    /**
     * Key for {@code minecraft:smooth_basalt}.
     */
    public static final TypedKey<Item> SMOOTH_BASALT = create("smooth_basalt");

    /**
     * Key for {@code minecraft:smooth_quartz}.
     */
    public static final TypedKey<Item> SMOOTH_QUARTZ = create("smooth_quartz");

    /**
     * Key for {@code minecraft:smooth_quartz_slab}.
     */
    public static final TypedKey<Item> SMOOTH_QUARTZ_SLAB = create("smooth_quartz_slab");

    /**
     * Key for {@code minecraft:smooth_quartz_stairs}.
     */
    public static final TypedKey<Item> SMOOTH_QUARTZ_STAIRS = create("smooth_quartz_stairs");

    /**
     * Key for {@code minecraft:smooth_red_sandstone}.
     */
    public static final TypedKey<Item> SMOOTH_RED_SANDSTONE = create("smooth_red_sandstone");

    /**
     * Key for {@code minecraft:smooth_red_sandstone_slab}.
     */
    public static final TypedKey<Item> SMOOTH_RED_SANDSTONE_SLAB = create("smooth_red_sandstone_slab");

    /**
     * Key for {@code minecraft:smooth_red_sandstone_stairs}.
     */
    public static final TypedKey<Item> SMOOTH_RED_SANDSTONE_STAIRS = create("smooth_red_sandstone_stairs");

    /**
     * Key for {@code minecraft:smooth_sandstone}.
     */
    public static final TypedKey<Item> SMOOTH_SANDSTONE = create("smooth_sandstone");

    /**
     * Key for {@code minecraft:smooth_sandstone_slab}.
     */
    public static final TypedKey<Item> SMOOTH_SANDSTONE_SLAB = create("smooth_sandstone_slab");

    /**
     * Key for {@code minecraft:smooth_sandstone_stairs}.
     */
    public static final TypedKey<Item> SMOOTH_SANDSTONE_STAIRS = create("smooth_sandstone_stairs");

    /**
     * Key for {@code minecraft:smooth_stone}.
     */
    public static final TypedKey<Item> SMOOTH_STONE = create("smooth_stone");

    /**
     * Key for {@code minecraft:smooth_stone_slab}.
     */
    public static final TypedKey<Item> SMOOTH_STONE_SLAB = create("smooth_stone_slab");

    /**
     * Key for {@code minecraft:sniffer_egg}.
     */
    public static final TypedKey<Item> SNIFFER_EGG = create("sniffer_egg");

    /**
     * Key for {@code minecraft:sniffer_spawn_egg}.
     */
    public static final TypedKey<Item> SNIFFER_SPAWN_EGG = create("sniffer_spawn_egg");

    /**
     * Key for {@code minecraft:snort_pottery_sherd}.
     */
    public static final TypedKey<Item> SNORT_POTTERY_SHERD = create("snort_pottery_sherd");

    /**
     * Key for {@code minecraft:snout_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE = create("snout_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:snow}.
     */
    public static final TypedKey<Item> SNOW = create("snow");

    /**
     * Key for {@code minecraft:snowball}.
     */
    public static final TypedKey<Item> SNOWBALL = create("snowball");

    /**
     * Key for {@code minecraft:snow_block}.
     */
    public static final TypedKey<Item> SNOW_BLOCK = create("snow_block");

    /**
     * Key for {@code minecraft:snow_golem_spawn_egg}.
     */
    public static final TypedKey<Item> SNOW_GOLEM_SPAWN_EGG = create("snow_golem_spawn_egg");

    /**
     * Key for {@code minecraft:soul_campfire}.
     */
    public static final TypedKey<Item> SOUL_CAMPFIRE = create("soul_campfire");

    /**
     * Key for {@code minecraft:soul_lantern}.
     */
    public static final TypedKey<Item> SOUL_LANTERN = create("soul_lantern");

    /**
     * Key for {@code minecraft:soul_sand}.
     */
    public static final TypedKey<Item> SOUL_SAND = create("soul_sand");

    /**
     * Key for {@code minecraft:soul_soil}.
     */
    public static final TypedKey<Item> SOUL_SOIL = create("soul_soil");

    /**
     * Key for {@code minecraft:soul_torch}.
     */
    public static final TypedKey<Item> SOUL_TORCH = create("soul_torch");

    /**
     * Key for {@code minecraft:spawner}.
     */
    public static final TypedKey<Item> SPAWNER = create("spawner");

    /**
     * Key for {@code minecraft:spectral_arrow}.
     */
    public static final TypedKey<Item> SPECTRAL_ARROW = create("spectral_arrow");

    /**
     * Key for {@code minecraft:spider_eye}.
     */
    public static final TypedKey<Item> SPIDER_EYE = create("spider_eye");

    /**
     * Key for {@code minecraft:spider_spawn_egg}.
     */
    public static final TypedKey<Item> SPIDER_SPAWN_EGG = create("spider_spawn_egg");

    /**
     * Key for {@code minecraft:spire_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE = create("spire_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:splash_potion}.
     */
    public static final TypedKey<Item> SPLASH_POTION = create("splash_potion");

    /**
     * Key for {@code minecraft:sponge}.
     */
    public static final TypedKey<Item> SPONGE = create("sponge");

    /**
     * Key for {@code minecraft:spore_blossom}.
     */
    public static final TypedKey<Item> SPORE_BLOSSOM = create("spore_blossom");

    /**
     * Key for {@code minecraft:spruce_boat}.
     */
    public static final TypedKey<Item> SPRUCE_BOAT = create("spruce_boat");

    /**
     * Key for {@code minecraft:spruce_button}.
     */
    public static final TypedKey<Item> SPRUCE_BUTTON = create("spruce_button");

    /**
     * Key for {@code minecraft:spruce_chest_boat}.
     */
    public static final TypedKey<Item> SPRUCE_CHEST_BOAT = create("spruce_chest_boat");

    /**
     * Key for {@code minecraft:spruce_door}.
     */
    public static final TypedKey<Item> SPRUCE_DOOR = create("spruce_door");

    /**
     * Key for {@code minecraft:spruce_fence}.
     */
    public static final TypedKey<Item> SPRUCE_FENCE = create("spruce_fence");

    /**
     * Key for {@code minecraft:spruce_fence_gate}.
     */
    public static final TypedKey<Item> SPRUCE_FENCE_GATE = create("spruce_fence_gate");

    /**
     * Key for {@code minecraft:spruce_hanging_sign}.
     */
    public static final TypedKey<Item> SPRUCE_HANGING_SIGN = create("spruce_hanging_sign");

    /**
     * Key for {@code minecraft:spruce_leaves}.
     */
    public static final TypedKey<Item> SPRUCE_LEAVES = create("spruce_leaves");

    /**
     * Key for {@code minecraft:spruce_log}.
     */
    public static final TypedKey<Item> SPRUCE_LOG = create("spruce_log");

    /**
     * Key for {@code minecraft:spruce_planks}.
     */
    public static final TypedKey<Item> SPRUCE_PLANKS = create("spruce_planks");

    /**
     * Key for {@code minecraft:spruce_pressure_plate}.
     */
    public static final TypedKey<Item> SPRUCE_PRESSURE_PLATE = create("spruce_pressure_plate");

    /**
     * Key for {@code minecraft:spruce_sapling}.
     */
    public static final TypedKey<Item> SPRUCE_SAPLING = create("spruce_sapling");

    /**
     * Key for {@code minecraft:spruce_shelf}.
     */
    public static final TypedKey<Item> SPRUCE_SHELF = create("spruce_shelf");

    /**
     * Key for {@code minecraft:spruce_sign}.
     */
    public static final TypedKey<Item> SPRUCE_SIGN = create("spruce_sign");

    /**
     * Key for {@code minecraft:spruce_slab}.
     */
    public static final TypedKey<Item> SPRUCE_SLAB = create("spruce_slab");

    /**
     * Key for {@code minecraft:spruce_stairs}.
     */
    public static final TypedKey<Item> SPRUCE_STAIRS = create("spruce_stairs");

    /**
     * Key for {@code minecraft:spruce_trapdoor}.
     */
    public static final TypedKey<Item> SPRUCE_TRAPDOOR = create("spruce_trapdoor");

    /**
     * Key for {@code minecraft:spruce_wood}.
     */
    public static final TypedKey<Item> SPRUCE_WOOD = create("spruce_wood");

    /**
     * Key for {@code minecraft:spyglass}.
     */
    public static final TypedKey<Item> SPYGLASS = create("spyglass");

    /**
     * Key for {@code minecraft:squid_spawn_egg}.
     */
    public static final TypedKey<Item> SQUID_SPAWN_EGG = create("squid_spawn_egg");

    /**
     * Key for {@code minecraft:stick}.
     */
    public static final TypedKey<Item> STICK = create("stick");

    /**
     * Key for {@code minecraft:sticky_piston}.
     */
    public static final TypedKey<Item> STICKY_PISTON = create("sticky_piston");

    /**
     * Key for {@code minecraft:stone}.
     */
    public static final TypedKey<Item> STONE = create("stone");

    /**
     * Key for {@code minecraft:stonecutter}.
     */
    public static final TypedKey<Item> STONECUTTER = create("stonecutter");

    /**
     * Key for {@code minecraft:stone_axe}.
     */
    public static final TypedKey<Item> STONE_AXE = create("stone_axe");

    /**
     * Key for {@code minecraft:stone_bricks}.
     */
    public static final TypedKey<Item> STONE_BRICKS = create("stone_bricks");

    /**
     * Key for {@code minecraft:stone_brick_slab}.
     */
    public static final TypedKey<Item> STONE_BRICK_SLAB = create("stone_brick_slab");

    /**
     * Key for {@code minecraft:stone_brick_stairs}.
     */
    public static final TypedKey<Item> STONE_BRICK_STAIRS = create("stone_brick_stairs");

    /**
     * Key for {@code minecraft:stone_brick_wall}.
     */
    public static final TypedKey<Item> STONE_BRICK_WALL = create("stone_brick_wall");

    /**
     * Key for {@code minecraft:stone_button}.
     */
    public static final TypedKey<Item> STONE_BUTTON = create("stone_button");

    /**
     * Key for {@code minecraft:stone_hoe}.
     */
    public static final TypedKey<Item> STONE_HOE = create("stone_hoe");

    /**
     * Key for {@code minecraft:stone_pickaxe}.
     */
    public static final TypedKey<Item> STONE_PICKAXE = create("stone_pickaxe");

    /**
     * Key for {@code minecraft:stone_pressure_plate}.
     */
    public static final TypedKey<Item> STONE_PRESSURE_PLATE = create("stone_pressure_plate");

    /**
     * Key for {@code minecraft:stone_shovel}.
     */
    public static final TypedKey<Item> STONE_SHOVEL = create("stone_shovel");

    /**
     * Key for {@code minecraft:stone_slab}.
     */
    public static final TypedKey<Item> STONE_SLAB = create("stone_slab");

    /**
     * Key for {@code minecraft:stone_spear}.
     */
    public static final TypedKey<Item> STONE_SPEAR = create("stone_spear");

    /**
     * Key for {@code minecraft:stone_stairs}.
     */
    public static final TypedKey<Item> STONE_STAIRS = create("stone_stairs");

    /**
     * Key for {@code minecraft:stone_sword}.
     */
    public static final TypedKey<Item> STONE_SWORD = create("stone_sword");

    /**
     * Key for {@code minecraft:stray_spawn_egg}.
     */
    public static final TypedKey<Item> STRAY_SPAWN_EGG = create("stray_spawn_egg");

    /**
     * Key for {@code minecraft:strider_spawn_egg}.
     */
    public static final TypedKey<Item> STRIDER_SPAWN_EGG = create("strider_spawn_egg");

    /**
     * Key for {@code minecraft:string}.
     */
    public static final TypedKey<Item> STRING = create("string");

    /**
     * Key for {@code minecraft:stripped_acacia_log}.
     */
    public static final TypedKey<Item> STRIPPED_ACACIA_LOG = create("stripped_acacia_log");

    /**
     * Key for {@code minecraft:stripped_acacia_wood}.
     */
    public static final TypedKey<Item> STRIPPED_ACACIA_WOOD = create("stripped_acacia_wood");

    /**
     * Key for {@code minecraft:stripped_bamboo_block}.
     */
    public static final TypedKey<Item> STRIPPED_BAMBOO_BLOCK = create("stripped_bamboo_block");

    /**
     * Key for {@code minecraft:stripped_birch_log}.
     */
    public static final TypedKey<Item> STRIPPED_BIRCH_LOG = create("stripped_birch_log");

    /**
     * Key for {@code minecraft:stripped_birch_wood}.
     */
    public static final TypedKey<Item> STRIPPED_BIRCH_WOOD = create("stripped_birch_wood");

    /**
     * Key for {@code minecraft:stripped_cherry_log}.
     */
    public static final TypedKey<Item> STRIPPED_CHERRY_LOG = create("stripped_cherry_log");

    /**
     * Key for {@code minecraft:stripped_cherry_wood}.
     */
    public static final TypedKey<Item> STRIPPED_CHERRY_WOOD = create("stripped_cherry_wood");

    /**
     * Key for {@code minecraft:stripped_crimson_hyphae}.
     */
    public static final TypedKey<Item> STRIPPED_CRIMSON_HYPHAE = create("stripped_crimson_hyphae");

    /**
     * Key for {@code minecraft:stripped_crimson_stem}.
     */
    public static final TypedKey<Item> STRIPPED_CRIMSON_STEM = create("stripped_crimson_stem");

    /**
     * Key for {@code minecraft:stripped_dark_oak_log}.
     */
    public static final TypedKey<Item> STRIPPED_DARK_OAK_LOG = create("stripped_dark_oak_log");

    /**
     * Key for {@code minecraft:stripped_dark_oak_wood}.
     */
    public static final TypedKey<Item> STRIPPED_DARK_OAK_WOOD = create("stripped_dark_oak_wood");

    /**
     * Key for {@code minecraft:stripped_jungle_log}.
     */
    public static final TypedKey<Item> STRIPPED_JUNGLE_LOG = create("stripped_jungle_log");

    /**
     * Key for {@code minecraft:stripped_jungle_wood}.
     */
    public static final TypedKey<Item> STRIPPED_JUNGLE_WOOD = create("stripped_jungle_wood");

    /**
     * Key for {@code minecraft:stripped_mangrove_log}.
     */
    public static final TypedKey<Item> STRIPPED_MANGROVE_LOG = create("stripped_mangrove_log");

    /**
     * Key for {@code minecraft:stripped_mangrove_wood}.
     */
    public static final TypedKey<Item> STRIPPED_MANGROVE_WOOD = create("stripped_mangrove_wood");

    /**
     * Key for {@code minecraft:stripped_oak_log}.
     */
    public static final TypedKey<Item> STRIPPED_OAK_LOG = create("stripped_oak_log");

    /**
     * Key for {@code minecraft:stripped_oak_wood}.
     */
    public static final TypedKey<Item> STRIPPED_OAK_WOOD = create("stripped_oak_wood");

    /**
     * Key for {@code minecraft:stripped_pale_oak_log}.
     */
    public static final TypedKey<Item> STRIPPED_PALE_OAK_LOG = create("stripped_pale_oak_log");

    /**
     * Key for {@code minecraft:stripped_pale_oak_wood}.
     */
    public static final TypedKey<Item> STRIPPED_PALE_OAK_WOOD = create("stripped_pale_oak_wood");

    /**
     * Key for {@code minecraft:stripped_spruce_log}.
     */
    public static final TypedKey<Item> STRIPPED_SPRUCE_LOG = create("stripped_spruce_log");

    /**
     * Key for {@code minecraft:stripped_spruce_wood}.
     */
    public static final TypedKey<Item> STRIPPED_SPRUCE_WOOD = create("stripped_spruce_wood");

    /**
     * Key for {@code minecraft:stripped_warped_hyphae}.
     */
    public static final TypedKey<Item> STRIPPED_WARPED_HYPHAE = create("stripped_warped_hyphae");

    /**
     * Key for {@code minecraft:stripped_warped_stem}.
     */
    public static final TypedKey<Item> STRIPPED_WARPED_STEM = create("stripped_warped_stem");

    /**
     * Key for {@code minecraft:structure_block}.
     */
    public static final TypedKey<Item> STRUCTURE_BLOCK = create("structure_block");

    /**
     * Key for {@code minecraft:structure_void}.
     */
    public static final TypedKey<Item> STRUCTURE_VOID = create("structure_void");

    /**
     * Key for {@code minecraft:sugar}.
     */
    public static final TypedKey<Item> SUGAR = create("sugar");

    /**
     * Key for {@code minecraft:sugar_cane}.
     */
    public static final TypedKey<Item> SUGAR_CANE = create("sugar_cane");

    /**
     * Key for {@code minecraft:sulfur}.
     */
    public static final TypedKey<Item> SULFUR = create("sulfur");

    /**
     * Key for {@code minecraft:sulfur_bricks}.
     */
    public static final TypedKey<Item> SULFUR_BRICKS = create("sulfur_bricks");

    /**
     * Key for {@code minecraft:sulfur_brick_slab}.
     */
    public static final TypedKey<Item> SULFUR_BRICK_SLAB = create("sulfur_brick_slab");

    /**
     * Key for {@code minecraft:sulfur_brick_stairs}.
     */
    public static final TypedKey<Item> SULFUR_BRICK_STAIRS = create("sulfur_brick_stairs");

    /**
     * Key for {@code minecraft:sulfur_brick_wall}.
     */
    public static final TypedKey<Item> SULFUR_BRICK_WALL = create("sulfur_brick_wall");

    /**
     * Key for {@code minecraft:sulfur_cube_bucket}.
     */
    public static final TypedKey<Item> SULFUR_CUBE_BUCKET = create("sulfur_cube_bucket");

    /**
     * Key for {@code minecraft:sulfur_cube_spawn_egg}.
     */
    public static final TypedKey<Item> SULFUR_CUBE_SPAWN_EGG = create("sulfur_cube_spawn_egg");

    /**
     * Key for {@code minecraft:sulfur_slab}.
     */
    public static final TypedKey<Item> SULFUR_SLAB = create("sulfur_slab");

    /**
     * Key for {@code minecraft:sulfur_spike}.
     */
    public static final TypedKey<Item> SULFUR_SPIKE = create("sulfur_spike");

    /**
     * Key for {@code minecraft:sulfur_stairs}.
     */
    public static final TypedKey<Item> SULFUR_STAIRS = create("sulfur_stairs");

    /**
     * Key for {@code minecraft:sulfur_wall}.
     */
    public static final TypedKey<Item> SULFUR_WALL = create("sulfur_wall");

    /**
     * Key for {@code minecraft:sunflower}.
     */
    public static final TypedKey<Item> SUNFLOWER = create("sunflower");

    /**
     * Key for {@code minecraft:suspicious_gravel}.
     */
    public static final TypedKey<Item> SUSPICIOUS_GRAVEL = create("suspicious_gravel");

    /**
     * Key for {@code minecraft:suspicious_sand}.
     */
    public static final TypedKey<Item> SUSPICIOUS_SAND = create("suspicious_sand");

    /**
     * Key for {@code minecraft:suspicious_stew}.
     */
    public static final TypedKey<Item> SUSPICIOUS_STEW = create("suspicious_stew");

    /**
     * Key for {@code minecraft:sweet_berries}.
     */
    public static final TypedKey<Item> SWEET_BERRIES = create("sweet_berries");

    /**
     * Key for {@code minecraft:tadpole_bucket}.
     */
    public static final TypedKey<Item> TADPOLE_BUCKET = create("tadpole_bucket");

    /**
     * Key for {@code minecraft:tadpole_spawn_egg}.
     */
    public static final TypedKey<Item> TADPOLE_SPAWN_EGG = create("tadpole_spawn_egg");

    /**
     * Key for {@code minecraft:tall_dry_grass}.
     */
    public static final TypedKey<Item> TALL_DRY_GRASS = create("tall_dry_grass");

    /**
     * Key for {@code minecraft:tall_grass}.
     */
    public static final TypedKey<Item> TALL_GRASS = create("tall_grass");

    /**
     * Key for {@code minecraft:target}.
     */
    public static final TypedKey<Item> TARGET = create("target");

    /**
     * Key for {@code minecraft:terracotta}.
     */
    public static final TypedKey<Item> TERRACOTTA = create("terracotta");

    /**
     * Key for {@code minecraft:test_block}.
     */
    public static final TypedKey<Item> TEST_BLOCK = create("test_block");

    /**
     * Key for {@code minecraft:test_instance_block}.
     */
    public static final TypedKey<Item> TEST_INSTANCE_BLOCK = create("test_instance_block");

    /**
     * Key for {@code minecraft:tide_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> TIDE_ARMOR_TRIM_SMITHING_TEMPLATE = create("tide_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:tinted_glass}.
     */
    public static final TypedKey<Item> TINTED_GLASS = create("tinted_glass");

    /**
     * Key for {@code minecraft:tipped_arrow}.
     */
    public static final TypedKey<Item> TIPPED_ARROW = create("tipped_arrow");

    /**
     * Key for {@code minecraft:tnt}.
     */
    public static final TypedKey<Item> TNT = create("tnt");

    /**
     * Key for {@code minecraft:tnt_minecart}.
     */
    public static final TypedKey<Item> TNT_MINECART = create("tnt_minecart");

    /**
     * Key for {@code minecraft:torch}.
     */
    public static final TypedKey<Item> TORCH = create("torch");

    /**
     * Key for {@code minecraft:torchflower}.
     */
    public static final TypedKey<Item> TORCHFLOWER = create("torchflower");

    /**
     * Key for {@code minecraft:torchflower_seeds}.
     */
    public static final TypedKey<Item> TORCHFLOWER_SEEDS = create("torchflower_seeds");

    /**
     * Key for {@code minecraft:totem_of_undying}.
     */
    public static final TypedKey<Item> TOTEM_OF_UNDYING = create("totem_of_undying");

    /**
     * Key for {@code minecraft:trader_llama_spawn_egg}.
     */
    public static final TypedKey<Item> TRADER_LLAMA_SPAWN_EGG = create("trader_llama_spawn_egg");

    /**
     * Key for {@code minecraft:trapped_chest}.
     */
    public static final TypedKey<Item> TRAPPED_CHEST = create("trapped_chest");

    /**
     * Key for {@code minecraft:trial_key}.
     */
    public static final TypedKey<Item> TRIAL_KEY = create("trial_key");

    /**
     * Key for {@code minecraft:trial_spawner}.
     */
    public static final TypedKey<Item> TRIAL_SPAWNER = create("trial_spawner");

    /**
     * Key for {@code minecraft:trident}.
     */
    public static final TypedKey<Item> TRIDENT = create("trident");

    /**
     * Key for {@code minecraft:tripwire_hook}.
     */
    public static final TypedKey<Item> TRIPWIRE_HOOK = create("tripwire_hook");

    /**
     * Key for {@code minecraft:tropical_fish}.
     */
    public static final TypedKey<Item> TROPICAL_FISH = create("tropical_fish");

    /**
     * Key for {@code minecraft:tropical_fish_bucket}.
     */
    public static final TypedKey<Item> TROPICAL_FISH_BUCKET = create("tropical_fish_bucket");

    /**
     * Key for {@code minecraft:tropical_fish_spawn_egg}.
     */
    public static final TypedKey<Item> TROPICAL_FISH_SPAWN_EGG = create("tropical_fish_spawn_egg");

    /**
     * Key for {@code minecraft:tube_coral}.
     */
    public static final TypedKey<Item> TUBE_CORAL = create("tube_coral");

    /**
     * Key for {@code minecraft:tube_coral_block}.
     */
    public static final TypedKey<Item> TUBE_CORAL_BLOCK = create("tube_coral_block");

    /**
     * Key for {@code minecraft:tube_coral_fan}.
     */
    public static final TypedKey<Item> TUBE_CORAL_FAN = create("tube_coral_fan");

    /**
     * Key for {@code minecraft:tuff}.
     */
    public static final TypedKey<Item> TUFF = create("tuff");

    /**
     * Key for {@code minecraft:tuff_bricks}.
     */
    public static final TypedKey<Item> TUFF_BRICKS = create("tuff_bricks");

    /**
     * Key for {@code minecraft:tuff_brick_slab}.
     */
    public static final TypedKey<Item> TUFF_BRICK_SLAB = create("tuff_brick_slab");

    /**
     * Key for {@code minecraft:tuff_brick_stairs}.
     */
    public static final TypedKey<Item> TUFF_BRICK_STAIRS = create("tuff_brick_stairs");

    /**
     * Key for {@code minecraft:tuff_brick_wall}.
     */
    public static final TypedKey<Item> TUFF_BRICK_WALL = create("tuff_brick_wall");

    /**
     * Key for {@code minecraft:tuff_slab}.
     */
    public static final TypedKey<Item> TUFF_SLAB = create("tuff_slab");

    /**
     * Key for {@code minecraft:tuff_stairs}.
     */
    public static final TypedKey<Item> TUFF_STAIRS = create("tuff_stairs");

    /**
     * Key for {@code minecraft:tuff_wall}.
     */
    public static final TypedKey<Item> TUFF_WALL = create("tuff_wall");

    /**
     * Key for {@code minecraft:turtle_egg}.
     */
    public static final TypedKey<Item> TURTLE_EGG = create("turtle_egg");

    /**
     * Key for {@code minecraft:turtle_helmet}.
     */
    public static final TypedKey<Item> TURTLE_HELMET = create("turtle_helmet");

    /**
     * Key for {@code minecraft:turtle_scute}.
     */
    public static final TypedKey<Item> TURTLE_SCUTE = create("turtle_scute");

    /**
     * Key for {@code minecraft:turtle_spawn_egg}.
     */
    public static final TypedKey<Item> TURTLE_SPAWN_EGG = create("turtle_spawn_egg");

    /**
     * Key for {@code minecraft:twisting_vines}.
     */
    public static final TypedKey<Item> TWISTING_VINES = create("twisting_vines");

    /**
     * Key for {@code minecraft:vault}.
     */
    public static final TypedKey<Item> VAULT = create("vault");

    /**
     * Key for {@code minecraft:verdant_froglight}.
     */
    public static final TypedKey<Item> VERDANT_FROGLIGHT = create("verdant_froglight");

    /**
     * Key for {@code minecraft:vex_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> VEX_ARMOR_TRIM_SMITHING_TEMPLATE = create("vex_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:vex_spawn_egg}.
     */
    public static final TypedKey<Item> VEX_SPAWN_EGG = create("vex_spawn_egg");

    /**
     * Key for {@code minecraft:villager_spawn_egg}.
     */
    public static final TypedKey<Item> VILLAGER_SPAWN_EGG = create("villager_spawn_egg");

    /**
     * Key for {@code minecraft:vindicator_spawn_egg}.
     */
    public static final TypedKey<Item> VINDICATOR_SPAWN_EGG = create("vindicator_spawn_egg");

    /**
     * Key for {@code minecraft:vine}.
     */
    public static final TypedKey<Item> VINE = create("vine");

    /**
     * Key for {@code minecraft:wandering_trader_spawn_egg}.
     */
    public static final TypedKey<Item> WANDERING_TRADER_SPAWN_EGG = create("wandering_trader_spawn_egg");

    /**
     * Key for {@code minecraft:warden_spawn_egg}.
     */
    public static final TypedKey<Item> WARDEN_SPAWN_EGG = create("warden_spawn_egg");

    /**
     * Key for {@code minecraft:ward_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> WARD_ARMOR_TRIM_SMITHING_TEMPLATE = create("ward_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:warped_button}.
     */
    public static final TypedKey<Item> WARPED_BUTTON = create("warped_button");

    /**
     * Key for {@code minecraft:warped_door}.
     */
    public static final TypedKey<Item> WARPED_DOOR = create("warped_door");

    /**
     * Key for {@code minecraft:warped_fence}.
     */
    public static final TypedKey<Item> WARPED_FENCE = create("warped_fence");

    /**
     * Key for {@code minecraft:warped_fence_gate}.
     */
    public static final TypedKey<Item> WARPED_FENCE_GATE = create("warped_fence_gate");

    /**
     * Key for {@code minecraft:warped_fungus}.
     */
    public static final TypedKey<Item> WARPED_FUNGUS = create("warped_fungus");

    /**
     * Key for {@code minecraft:warped_fungus_on_a_stick}.
     */
    public static final TypedKey<Item> WARPED_FUNGUS_ON_A_STICK = create("warped_fungus_on_a_stick");

    /**
     * Key for {@code minecraft:warped_hanging_sign}.
     */
    public static final TypedKey<Item> WARPED_HANGING_SIGN = create("warped_hanging_sign");

    /**
     * Key for {@code minecraft:warped_hyphae}.
     */
    public static final TypedKey<Item> WARPED_HYPHAE = create("warped_hyphae");

    /**
     * Key for {@code minecraft:warped_nylium}.
     */
    public static final TypedKey<Item> WARPED_NYLIUM = create("warped_nylium");

    /**
     * Key for {@code minecraft:warped_planks}.
     */
    public static final TypedKey<Item> WARPED_PLANKS = create("warped_planks");

    /**
     * Key for {@code minecraft:warped_pressure_plate}.
     */
    public static final TypedKey<Item> WARPED_PRESSURE_PLATE = create("warped_pressure_plate");

    /**
     * Key for {@code minecraft:warped_roots}.
     */
    public static final TypedKey<Item> WARPED_ROOTS = create("warped_roots");

    /**
     * Key for {@code minecraft:warped_shelf}.
     */
    public static final TypedKey<Item> WARPED_SHELF = create("warped_shelf");

    /**
     * Key for {@code minecraft:warped_sign}.
     */
    public static final TypedKey<Item> WARPED_SIGN = create("warped_sign");

    /**
     * Key for {@code minecraft:warped_slab}.
     */
    public static final TypedKey<Item> WARPED_SLAB = create("warped_slab");

    /**
     * Key for {@code minecraft:warped_stairs}.
     */
    public static final TypedKey<Item> WARPED_STAIRS = create("warped_stairs");

    /**
     * Key for {@code minecraft:warped_stem}.
     */
    public static final TypedKey<Item> WARPED_STEM = create("warped_stem");

    /**
     * Key for {@code minecraft:warped_trapdoor}.
     */
    public static final TypedKey<Item> WARPED_TRAPDOOR = create("warped_trapdoor");

    /**
     * Key for {@code minecraft:warped_wart_block}.
     */
    public static final TypedKey<Item> WARPED_WART_BLOCK = create("warped_wart_block");

    /**
     * Key for {@code minecraft:water_bucket}.
     */
    public static final TypedKey<Item> WATER_BUCKET = create("water_bucket");

    /**
     * Key for {@code minecraft:waxed_chiseled_copper}.
     */
    public static final TypedKey<Item> WAXED_CHISELED_COPPER = create("waxed_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_copper_bars}.
     */
    public static final TypedKey<Item> WAXED_COPPER_BARS = create("waxed_copper_bars");

    /**
     * Key for {@code minecraft:waxed_copper_block}.
     */
    public static final TypedKey<Item> WAXED_COPPER_BLOCK = create("waxed_copper_block");

    /**
     * Key for {@code minecraft:waxed_copper_bulb}.
     */
    public static final TypedKey<Item> WAXED_COPPER_BULB = create("waxed_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_copper_chain}.
     */
    public static final TypedKey<Item> WAXED_COPPER_CHAIN = create("waxed_copper_chain");

    /**
     * Key for {@code minecraft:waxed_copper_chest}.
     */
    public static final TypedKey<Item> WAXED_COPPER_CHEST = create("waxed_copper_chest");

    /**
     * Key for {@code minecraft:waxed_copper_door}.
     */
    public static final TypedKey<Item> WAXED_COPPER_DOOR = create("waxed_copper_door");

    /**
     * Key for {@code minecraft:waxed_copper_golem_statue}.
     */
    public static final TypedKey<Item> WAXED_COPPER_GOLEM_STATUE = create("waxed_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_copper_grate}.
     */
    public static final TypedKey<Item> WAXED_COPPER_GRATE = create("waxed_copper_grate");

    /**
     * Key for {@code minecraft:waxed_copper_lantern}.
     */
    public static final TypedKey<Item> WAXED_COPPER_LANTERN = create("waxed_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_copper_trapdoor}.
     */
    public static final TypedKey<Item> WAXED_COPPER_TRAPDOOR = create("waxed_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_cut_copper}.
     */
    public static final TypedKey<Item> WAXED_CUT_COPPER = create("waxed_cut_copper");

    /**
     * Key for {@code minecraft:waxed_cut_copper_slab}.
     */
    public static final TypedKey<Item> WAXED_CUT_COPPER_SLAB = create("waxed_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_cut_copper_stairs}.
     */
    public static final TypedKey<Item> WAXED_CUT_COPPER_STAIRS = create("waxed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_exposed_chiseled_copper}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_CHISELED_COPPER = create("waxed_exposed_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_copper}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER = create("waxed_exposed_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_bars}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_BARS = create("waxed_exposed_copper_bars");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_bulb}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_BULB = create("waxed_exposed_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_chain}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_CHAIN = create("waxed_exposed_copper_chain");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_chest}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_CHEST = create("waxed_exposed_copper_chest");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_door}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_DOOR = create("waxed_exposed_copper_door");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_golem_statue}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_GOLEM_STATUE = create("waxed_exposed_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_grate}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_GRATE = create("waxed_exposed_copper_grate");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_lantern}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_LANTERN = create("waxed_exposed_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_exposed_copper_trapdoor}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_COPPER_TRAPDOOR = create("waxed_exposed_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_CUT_COPPER = create("waxed_exposed_cut_copper");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper_slab}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_CUT_COPPER_SLAB = create("waxed_exposed_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_exposed_cut_copper_stairs}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_CUT_COPPER_STAIRS = create("waxed_exposed_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_exposed_lightning_rod}.
     */
    public static final TypedKey<Item> WAXED_EXPOSED_LIGHTNING_ROD = create("waxed_exposed_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_lightning_rod}.
     */
    public static final TypedKey<Item> WAXED_LIGHTNING_ROD = create("waxed_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_oxidized_chiseled_copper}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_CHISELED_COPPER = create("waxed_oxidized_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER = create("waxed_oxidized_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_bars}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_BARS = create("waxed_oxidized_copper_bars");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_bulb}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_BULB = create("waxed_oxidized_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_chain}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_CHAIN = create("waxed_oxidized_copper_chain");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_chest}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_CHEST = create("waxed_oxidized_copper_chest");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_door}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_DOOR = create("waxed_oxidized_copper_door");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_golem_statue}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_GOLEM_STATUE = create("waxed_oxidized_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_grate}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_GRATE = create("waxed_oxidized_copper_grate");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_lantern}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_LANTERN = create("waxed_oxidized_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_oxidized_copper_trapdoor}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_COPPER_TRAPDOOR = create("waxed_oxidized_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_CUT_COPPER = create("waxed_oxidized_cut_copper");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper_slab}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_CUT_COPPER_SLAB = create("waxed_oxidized_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_oxidized_cut_copper_stairs}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_CUT_COPPER_STAIRS = create("waxed_oxidized_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_oxidized_lightning_rod}.
     */
    public static final TypedKey<Item> WAXED_OXIDIZED_LIGHTNING_ROD = create("waxed_oxidized_lightning_rod");

    /**
     * Key for {@code minecraft:waxed_weathered_chiseled_copper}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_CHISELED_COPPER = create("waxed_weathered_chiseled_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_copper}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER = create("waxed_weathered_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_bars}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_BARS = create("waxed_weathered_copper_bars");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_bulb}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_BULB = create("waxed_weathered_copper_bulb");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_chain}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_CHAIN = create("waxed_weathered_copper_chain");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_chest}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_CHEST = create("waxed_weathered_copper_chest");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_door}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_DOOR = create("waxed_weathered_copper_door");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_golem_statue}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_GOLEM_STATUE = create("waxed_weathered_copper_golem_statue");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_grate}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_GRATE = create("waxed_weathered_copper_grate");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_lantern}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_LANTERN = create("waxed_weathered_copper_lantern");

    /**
     * Key for {@code minecraft:waxed_weathered_copper_trapdoor}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_COPPER_TRAPDOOR = create("waxed_weathered_copper_trapdoor");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_CUT_COPPER = create("waxed_weathered_cut_copper");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper_slab}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_CUT_COPPER_SLAB = create("waxed_weathered_cut_copper_slab");

    /**
     * Key for {@code minecraft:waxed_weathered_cut_copper_stairs}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_CUT_COPPER_STAIRS = create("waxed_weathered_cut_copper_stairs");

    /**
     * Key for {@code minecraft:waxed_weathered_lightning_rod}.
     */
    public static final TypedKey<Item> WAXED_WEATHERED_LIGHTNING_ROD = create("waxed_weathered_lightning_rod");

    /**
     * Key for {@code minecraft:wayfinder_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE = create("wayfinder_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:weathered_chiseled_copper}.
     */
    public static final TypedKey<Item> WEATHERED_CHISELED_COPPER = create("weathered_chiseled_copper");

    /**
     * Key for {@code minecraft:weathered_copper}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER = create("weathered_copper");

    /**
     * Key for {@code minecraft:weathered_copper_bars}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_BARS = create("weathered_copper_bars");

    /**
     * Key for {@code minecraft:weathered_copper_bulb}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_BULB = create("weathered_copper_bulb");

    /**
     * Key for {@code minecraft:weathered_copper_chain}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_CHAIN = create("weathered_copper_chain");

    /**
     * Key for {@code minecraft:weathered_copper_chest}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_CHEST = create("weathered_copper_chest");

    /**
     * Key for {@code minecraft:weathered_copper_door}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_DOOR = create("weathered_copper_door");

    /**
     * Key for {@code minecraft:weathered_copper_golem_statue}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_GOLEM_STATUE = create("weathered_copper_golem_statue");

    /**
     * Key for {@code minecraft:weathered_copper_grate}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_GRATE = create("weathered_copper_grate");

    /**
     * Key for {@code minecraft:weathered_copper_lantern}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_LANTERN = create("weathered_copper_lantern");

    /**
     * Key for {@code minecraft:weathered_copper_trapdoor}.
     */
    public static final TypedKey<Item> WEATHERED_COPPER_TRAPDOOR = create("weathered_copper_trapdoor");

    /**
     * Key for {@code minecraft:weathered_cut_copper}.
     */
    public static final TypedKey<Item> WEATHERED_CUT_COPPER = create("weathered_cut_copper");

    /**
     * Key for {@code minecraft:weathered_cut_copper_slab}.
     */
    public static final TypedKey<Item> WEATHERED_CUT_COPPER_SLAB = create("weathered_cut_copper_slab");

    /**
     * Key for {@code minecraft:weathered_cut_copper_stairs}.
     */
    public static final TypedKey<Item> WEATHERED_CUT_COPPER_STAIRS = create("weathered_cut_copper_stairs");

    /**
     * Key for {@code minecraft:weathered_lightning_rod}.
     */
    public static final TypedKey<Item> WEATHERED_LIGHTNING_ROD = create("weathered_lightning_rod");

    /**
     * Key for {@code minecraft:weeping_vines}.
     */
    public static final TypedKey<Item> WEEPING_VINES = create("weeping_vines");

    /**
     * Key for {@code minecraft:wet_sponge}.
     */
    public static final TypedKey<Item> WET_SPONGE = create("wet_sponge");

    /**
     * Key for {@code minecraft:wheat}.
     */
    public static final TypedKey<Item> WHEAT = create("wheat");

    /**
     * Key for {@code minecraft:wheat_seeds}.
     */
    public static final TypedKey<Item> WHEAT_SEEDS = create("wheat_seeds");

    /**
     * Key for {@code minecraft:white_banner}.
     */
    public static final TypedKey<Item> WHITE_BANNER = create("white_banner");

    /**
     * Key for {@code minecraft:white_bed}.
     */
    public static final TypedKey<Item> WHITE_BED = create("white_bed");

    /**
     * Key for {@code minecraft:white_bundle}.
     */
    public static final TypedKey<Item> WHITE_BUNDLE = create("white_bundle");

    /**
     * Key for {@code minecraft:white_candle}.
     */
    public static final TypedKey<Item> WHITE_CANDLE = create("white_candle");

    /**
     * Key for {@code minecraft:white_carpet}.
     */
    public static final TypedKey<Item> WHITE_CARPET = create("white_carpet");

    /**
     * Key for {@code minecraft:white_concrete}.
     */
    public static final TypedKey<Item> WHITE_CONCRETE = create("white_concrete");

    /**
     * Key for {@code minecraft:white_concrete_powder}.
     */
    public static final TypedKey<Item> WHITE_CONCRETE_POWDER = create("white_concrete_powder");

    /**
     * Key for {@code minecraft:white_dye}.
     */
    public static final TypedKey<Item> WHITE_DYE = create("white_dye");

    /**
     * Key for {@code minecraft:white_glazed_terracotta}.
     */
    public static final TypedKey<Item> WHITE_GLAZED_TERRACOTTA = create("white_glazed_terracotta");

    /**
     * Key for {@code minecraft:white_harness}.
     */
    public static final TypedKey<Item> WHITE_HARNESS = create("white_harness");

    /**
     * Key for {@code minecraft:white_shulker_box}.
     */
    public static final TypedKey<Item> WHITE_SHULKER_BOX = create("white_shulker_box");

    /**
     * Key for {@code minecraft:white_stained_glass}.
     */
    public static final TypedKey<Item> WHITE_STAINED_GLASS = create("white_stained_glass");

    /**
     * Key for {@code minecraft:white_stained_glass_pane}.
     */
    public static final TypedKey<Item> WHITE_STAINED_GLASS_PANE = create("white_stained_glass_pane");

    /**
     * Key for {@code minecraft:white_terracotta}.
     */
    public static final TypedKey<Item> WHITE_TERRACOTTA = create("white_terracotta");

    /**
     * Key for {@code minecraft:white_tulip}.
     */
    public static final TypedKey<Item> WHITE_TULIP = create("white_tulip");

    /**
     * Key for {@code minecraft:white_wool}.
     */
    public static final TypedKey<Item> WHITE_WOOL = create("white_wool");

    /**
     * Key for {@code minecraft:wildflowers}.
     */
    public static final TypedKey<Item> WILDFLOWERS = create("wildflowers");

    /**
     * Key for {@code minecraft:wild_armor_trim_smithing_template}.
     */
    public static final TypedKey<Item> WILD_ARMOR_TRIM_SMITHING_TEMPLATE = create("wild_armor_trim_smithing_template");

    /**
     * Key for {@code minecraft:wind_charge}.
     */
    public static final TypedKey<Item> WIND_CHARGE = create("wind_charge");

    /**
     * Key for {@code minecraft:witch_spawn_egg}.
     */
    public static final TypedKey<Item> WITCH_SPAWN_EGG = create("witch_spawn_egg");

    /**
     * Key for {@code minecraft:wither_rose}.
     */
    public static final TypedKey<Item> WITHER_ROSE = create("wither_rose");

    /**
     * Key for {@code minecraft:wither_skeleton_skull}.
     */
    public static final TypedKey<Item> WITHER_SKELETON_SKULL = create("wither_skeleton_skull");

    /**
     * Key for {@code minecraft:wither_skeleton_spawn_egg}.
     */
    public static final TypedKey<Item> WITHER_SKELETON_SPAWN_EGG = create("wither_skeleton_spawn_egg");

    /**
     * Key for {@code minecraft:wither_spawn_egg}.
     */
    public static final TypedKey<Item> WITHER_SPAWN_EGG = create("wither_spawn_egg");

    /**
     * Key for {@code minecraft:wolf_armor}.
     */
    public static final TypedKey<Item> WOLF_ARMOR = create("wolf_armor");

    /**
     * Key for {@code minecraft:wolf_spawn_egg}.
     */
    public static final TypedKey<Item> WOLF_SPAWN_EGG = create("wolf_spawn_egg");

    /**
     * Key for {@code minecraft:wooden_axe}.
     */
    public static final TypedKey<Item> WOODEN_AXE = create("wooden_axe");

    /**
     * Key for {@code minecraft:wooden_hoe}.
     */
    public static final TypedKey<Item> WOODEN_HOE = create("wooden_hoe");

    /**
     * Key for {@code minecraft:wooden_pickaxe}.
     */
    public static final TypedKey<Item> WOODEN_PICKAXE = create("wooden_pickaxe");

    /**
     * Key for {@code minecraft:wooden_shovel}.
     */
    public static final TypedKey<Item> WOODEN_SHOVEL = create("wooden_shovel");

    /**
     * Key for {@code minecraft:wooden_spear}.
     */
    public static final TypedKey<Item> WOODEN_SPEAR = create("wooden_spear");

    /**
     * Key for {@code minecraft:wooden_sword}.
     */
    public static final TypedKey<Item> WOODEN_SWORD = create("wooden_sword");

    /**
     * Key for {@code minecraft:writable_book}.
     */
    public static final TypedKey<Item> WRITABLE_BOOK = create("writable_book");

    /**
     * Key for {@code minecraft:written_book}.
     */
    public static final TypedKey<Item> WRITTEN_BOOK = create("written_book");

    /**
     * Key for {@code minecraft:yellow_banner}.
     */
    public static final TypedKey<Item> YELLOW_BANNER = create("yellow_banner");

    /**
     * Key for {@code minecraft:yellow_bed}.
     */
    public static final TypedKey<Item> YELLOW_BED = create("yellow_bed");

    /**
     * Key for {@code minecraft:yellow_bundle}.
     */
    public static final TypedKey<Item> YELLOW_BUNDLE = create("yellow_bundle");

    /**
     * Key for {@code minecraft:yellow_candle}.
     */
    public static final TypedKey<Item> YELLOW_CANDLE = create("yellow_candle");

    /**
     * Key for {@code minecraft:yellow_carpet}.
     */
    public static final TypedKey<Item> YELLOW_CARPET = create("yellow_carpet");

    /**
     * Key for {@code minecraft:yellow_concrete}.
     */
    public static final TypedKey<Item> YELLOW_CONCRETE = create("yellow_concrete");

    /**
     * Key for {@code minecraft:yellow_concrete_powder}.
     */
    public static final TypedKey<Item> YELLOW_CONCRETE_POWDER = create("yellow_concrete_powder");

    /**
     * Key for {@code minecraft:yellow_dye}.
     */
    public static final TypedKey<Item> YELLOW_DYE = create("yellow_dye");

    /**
     * Key for {@code minecraft:yellow_glazed_terracotta}.
     */
    public static final TypedKey<Item> YELLOW_GLAZED_TERRACOTTA = create("yellow_glazed_terracotta");

    /**
     * Key for {@code minecraft:yellow_harness}.
     */
    public static final TypedKey<Item> YELLOW_HARNESS = create("yellow_harness");

    /**
     * Key for {@code minecraft:yellow_shulker_box}.
     */
    public static final TypedKey<Item> YELLOW_SHULKER_BOX = create("yellow_shulker_box");

    /**
     * Key for {@code minecraft:yellow_stained_glass}.
     */
    public static final TypedKey<Item> YELLOW_STAINED_GLASS = create("yellow_stained_glass");

    /**
     * Key for {@code minecraft:yellow_stained_glass_pane}.
     */
    public static final TypedKey<Item> YELLOW_STAINED_GLASS_PANE = create("yellow_stained_glass_pane");

    /**
     * Key for {@code minecraft:yellow_terracotta}.
     */
    public static final TypedKey<Item> YELLOW_TERRACOTTA = create("yellow_terracotta");

    /**
     * Key for {@code minecraft:yellow_wool}.
     */
    public static final TypedKey<Item> YELLOW_WOOL = create("yellow_wool");

    /**
     * Key for {@code minecraft:zoglin_spawn_egg}.
     */
    public static final TypedKey<Item> ZOGLIN_SPAWN_EGG = create("zoglin_spawn_egg");

    /**
     * Key for {@code minecraft:zombie_head}.
     */
    public static final TypedKey<Item> ZOMBIE_HEAD = create("zombie_head");

    /**
     * Key for {@code minecraft:zombie_horse_spawn_egg}.
     */
    public static final TypedKey<Item> ZOMBIE_HORSE_SPAWN_EGG = create("zombie_horse_spawn_egg");

    /**
     * Key for {@code minecraft:zombie_nautilus_spawn_egg}.
     */
    public static final TypedKey<Item> ZOMBIE_NAUTILUS_SPAWN_EGG = create("zombie_nautilus_spawn_egg");

    /**
     * Key for {@code minecraft:zombie_spawn_egg}.
     */
    public static final TypedKey<Item> ZOMBIE_SPAWN_EGG = create("zombie_spawn_egg");

    /**
     * Key for {@code minecraft:zombie_villager_spawn_egg}.
     */
    public static final TypedKey<Item> ZOMBIE_VILLAGER_SPAWN_EGG = create("zombie_villager_spawn_egg");

    /**
     * Key for {@code minecraft:zombified_piglin_spawn_egg}.
     */
    public static final TypedKey<Item> ZOMBIFIED_PIGLIN_SPAWN_EGG = create("zombified_piglin_spawn_egg");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<Item>> VALUES = List.of(
        AIR,
        STONE,
        GRANITE,
        POLISHED_GRANITE,
        DIORITE,
        POLISHED_DIORITE,
        ANDESITE,
        POLISHED_ANDESITE,
        DEEPSLATE,
        COBBLED_DEEPSLATE,
        POLISHED_DEEPSLATE,
        CALCITE,
        TUFF,
        TUFF_SLAB,
        TUFF_STAIRS,
        TUFF_WALL,
        CHISELED_TUFF,
        POLISHED_TUFF,
        POLISHED_TUFF_SLAB,
        POLISHED_TUFF_STAIRS,
        POLISHED_TUFF_WALL,
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
        DRIPSTONE_BLOCK,
        GRASS_BLOCK,
        DIRT,
        COARSE_DIRT,
        PODZOL,
        ROOTED_DIRT,
        MUD,
        CRIMSON_NYLIUM,
        WARPED_NYLIUM,
        COBBLESTONE,
        OAK_PLANKS,
        SPRUCE_PLANKS,
        BIRCH_PLANKS,
        JUNGLE_PLANKS,
        ACACIA_PLANKS,
        CHERRY_PLANKS,
        DARK_OAK_PLANKS,
        PALE_OAK_PLANKS,
        MANGROVE_PLANKS,
        BAMBOO_PLANKS,
        CRIMSON_PLANKS,
        WARPED_PLANKS,
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
        SAND,
        SUSPICIOUS_SAND,
        SUSPICIOUS_GRAVEL,
        RED_SAND,
        GRAVEL,
        COAL_ORE,
        DEEPSLATE_COAL_ORE,
        IRON_ORE,
        DEEPSLATE_IRON_ORE,
        COPPER_ORE,
        DEEPSLATE_COPPER_ORE,
        GOLD_ORE,
        DEEPSLATE_GOLD_ORE,
        REDSTONE_ORE,
        DEEPSLATE_REDSTONE_ORE,
        EMERALD_ORE,
        DEEPSLATE_EMERALD_ORE,
        LAPIS_ORE,
        DEEPSLATE_LAPIS_ORE,
        DIAMOND_ORE,
        DEEPSLATE_DIAMOND_ORE,
        NETHER_GOLD_ORE,
        NETHER_QUARTZ_ORE,
        ANCIENT_DEBRIS,
        COAL_BLOCK,
        RAW_IRON_BLOCK,
        RAW_COPPER_BLOCK,
        RAW_GOLD_BLOCK,
        HEAVY_CORE,
        AMETHYST_BLOCK,
        BUDDING_AMETHYST,
        IRON_BLOCK,
        COPPER_BLOCK,
        EXPOSED_COPPER,
        WEATHERED_COPPER,
        OXIDIZED_COPPER,
        WAXED_COPPER_BLOCK,
        WAXED_EXPOSED_COPPER,
        WAXED_WEATHERED_COPPER,
        WAXED_OXIDIZED_COPPER,
        GOLD_BLOCK,
        DIAMOND_BLOCK,
        NETHERITE_BLOCK,
        CHISELED_COPPER,
        EXPOSED_CHISELED_COPPER,
        WEATHERED_CHISELED_COPPER,
        OXIDIZED_CHISELED_COPPER,
        WAXED_CHISELED_COPPER,
        WAXED_EXPOSED_CHISELED_COPPER,
        WAXED_WEATHERED_CHISELED_COPPER,
        WAXED_OXIDIZED_CHISELED_COPPER,
        CUT_COPPER,
        EXPOSED_CUT_COPPER,
        WEATHERED_CUT_COPPER,
        OXIDIZED_CUT_COPPER,
        WAXED_CUT_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_OXIDIZED_CUT_COPPER,
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
        OAK_LOG,
        SPRUCE_LOG,
        BIRCH_LOG,
        JUNGLE_LOG,
        ACACIA_LOG,
        CHERRY_LOG,
        PALE_OAK_LOG,
        DARK_OAK_LOG,
        MANGROVE_LOG,
        MANGROVE_ROOTS,
        MUDDY_MANGROVE_ROOTS,
        CRIMSON_STEM,
        WARPED_STEM,
        BAMBOO_BLOCK,
        STRIPPED_OAK_LOG,
        STRIPPED_SPRUCE_LOG,
        STRIPPED_BIRCH_LOG,
        STRIPPED_JUNGLE_LOG,
        STRIPPED_ACACIA_LOG,
        STRIPPED_CHERRY_LOG,
        STRIPPED_DARK_OAK_LOG,
        STRIPPED_PALE_OAK_LOG,
        STRIPPED_MANGROVE_LOG,
        STRIPPED_CRIMSON_STEM,
        STRIPPED_WARPED_STEM,
        STRIPPED_OAK_WOOD,
        STRIPPED_SPRUCE_WOOD,
        STRIPPED_BIRCH_WOOD,
        STRIPPED_JUNGLE_WOOD,
        STRIPPED_ACACIA_WOOD,
        STRIPPED_CHERRY_WOOD,
        STRIPPED_DARK_OAK_WOOD,
        STRIPPED_PALE_OAK_WOOD,
        STRIPPED_MANGROVE_WOOD,
        STRIPPED_CRIMSON_HYPHAE,
        STRIPPED_WARPED_HYPHAE,
        STRIPPED_BAMBOO_BLOCK,
        OAK_WOOD,
        SPRUCE_WOOD,
        BIRCH_WOOD,
        JUNGLE_WOOD,
        ACACIA_WOOD,
        CHERRY_WOOD,
        PALE_OAK_WOOD,
        DARK_OAK_WOOD,
        MANGROVE_WOOD,
        CRIMSON_HYPHAE,
        WARPED_HYPHAE,
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
        TINTED_GLASS,
        LAPIS_BLOCK,
        SANDSTONE,
        CHISELED_SANDSTONE,
        CUT_SANDSTONE,
        COBWEB,
        SHORT_GRASS,
        FERN,
        BUSH,
        AZALEA,
        FLOWERING_AZALEA,
        DEAD_BUSH,
        FIREFLY_BUSH,
        SHORT_DRY_GRASS,
        TALL_DRY_GRASS,
        SEAGRASS,
        SEA_PICKLE,
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
        DANDELION,
        GOLDEN_DANDELION,
        OPEN_EYEBLOSSOM,
        CLOSED_EYEBLOSSOM,
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
        LILY_OF_THE_VALLEY,
        WITHER_ROSE,
        TORCHFLOWER,
        PITCHER_PLANT,
        SPORE_BLOSSOM,
        BROWN_MUSHROOM,
        RED_MUSHROOM,
        CRIMSON_FUNGUS,
        WARPED_FUNGUS,
        CRIMSON_ROOTS,
        WARPED_ROOTS,
        NETHER_SPROUTS,
        WEEPING_VINES,
        TWISTING_VINES,
        SUGAR_CANE,
        KELP,
        PINK_PETALS,
        WILDFLOWERS,
        LEAF_LITTER,
        MOSS_CARPET,
        MOSS_BLOCK,
        PALE_MOSS_CARPET,
        PALE_HANGING_MOSS,
        PALE_MOSS_BLOCK,
        HANGING_ROOTS,
        BIG_DRIPLEAF,
        SMALL_DRIPLEAF,
        BAMBOO,
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
        CRIMSON_SLAB,
        WARPED_SLAB,
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
        PRISMARINE_SLAB,
        PRISMARINE_BRICK_SLAB,
        DARK_PRISMARINE_SLAB,
        SMOOTH_QUARTZ,
        SMOOTH_RED_SANDSTONE,
        SMOOTH_SANDSTONE,
        SMOOTH_STONE,
        BRICKS,
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
        BOOKSHELF,
        CHISELED_BOOKSHELF,
        DECORATED_POT,
        MOSSY_COBBLESTONE,
        OBSIDIAN,
        TORCH,
        END_ROD,
        CHORUS_PLANT,
        CHORUS_FLOWER,
        PURPUR_BLOCK,
        PURPUR_PILLAR,
        PURPUR_STAIRS,
        SPAWNER,
        CREAKING_HEART,
        CHEST,
        CRAFTING_TABLE,
        FARMLAND,
        FURNACE,
        LADDER,
        COBBLESTONE_STAIRS,
        SNOW,
        ICE,
        SNOW_BLOCK,
        CACTUS,
        CACTUS_FLOWER,
        CLAY,
        JUKEBOX,
        OAK_FENCE,
        SPRUCE_FENCE,
        BIRCH_FENCE,
        JUNGLE_FENCE,
        ACACIA_FENCE,
        CHERRY_FENCE,
        DARK_OAK_FENCE,
        PALE_OAK_FENCE,
        MANGROVE_FENCE,
        BAMBOO_FENCE,
        CRIMSON_FENCE,
        WARPED_FENCE,
        PUMPKIN,
        CARVED_PUMPKIN,
        JACK_O_LANTERN,
        NETHERRACK,
        SOUL_SAND,
        SOUL_SOIL,
        BASALT,
        POLISHED_BASALT,
        SMOOTH_BASALT,
        SOUL_TORCH,
        COPPER_TORCH,
        GLOWSTONE,
        INFESTED_STONE,
        INFESTED_COBBLESTONE,
        INFESTED_STONE_BRICKS,
        INFESTED_MOSSY_STONE_BRICKS,
        INFESTED_CRACKED_STONE_BRICKS,
        INFESTED_CHISELED_STONE_BRICKS,
        INFESTED_DEEPSLATE,
        STONE_BRICKS,
        MOSSY_STONE_BRICKS,
        CRACKED_STONE_BRICKS,
        CHISELED_STONE_BRICKS,
        PACKED_MUD,
        MUD_BRICKS,
        DEEPSLATE_BRICKS,
        CRACKED_DEEPSLATE_BRICKS,
        DEEPSLATE_TILES,
        CRACKED_DEEPSLATE_TILES,
        CHISELED_DEEPSLATE,
        REINFORCED_DEEPSLATE,
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
        MELON,
        VINE,
        GLOW_LICHEN,
        RESIN_CLUMP,
        RESIN_BLOCK,
        RESIN_BRICKS,
        RESIN_BRICK_STAIRS,
        RESIN_BRICK_SLAB,
        RESIN_BRICK_WALL,
        CHISELED_RESIN_BRICKS,
        BRICK_STAIRS,
        STONE_BRICK_STAIRS,
        MUD_BRICK_STAIRS,
        MYCELIUM,
        LILY_PAD,
        NETHER_BRICKS,
        CRACKED_NETHER_BRICKS,
        CHISELED_NETHER_BRICKS,
        NETHER_BRICK_FENCE,
        NETHER_BRICK_STAIRS,
        SCULK,
        SCULK_VEIN,
        SCULK_CATALYST,
        SCULK_SHRIEKER,
        ENCHANTING_TABLE,
        END_PORTAL_FRAME,
        END_STONE,
        END_STONE_BRICKS,
        DRAGON_EGG,
        SANDSTONE_STAIRS,
        ENDER_CHEST,
        EMERALD_BLOCK,
        OAK_STAIRS,
        SPRUCE_STAIRS,
        BIRCH_STAIRS,
        JUNGLE_STAIRS,
        ACACIA_STAIRS,
        CHERRY_STAIRS,
        DARK_OAK_STAIRS,
        PALE_OAK_STAIRS,
        MANGROVE_STAIRS,
        BAMBOO_STAIRS,
        BAMBOO_MOSAIC_STAIRS,
        CRIMSON_STAIRS,
        WARPED_STAIRS,
        COMMAND_BLOCK,
        BEACON,
        COBBLESTONE_WALL,
        MOSSY_COBBLESTONE_WALL,
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
        BLACKSTONE_WALL,
        POLISHED_BLACKSTONE_WALL,
        POLISHED_BLACKSTONE_BRICK_WALL,
        COBBLED_DEEPSLATE_WALL,
        POLISHED_DEEPSLATE_WALL,
        DEEPSLATE_BRICK_WALL,
        DEEPSLATE_TILE_WALL,
        ANVIL,
        CHIPPED_ANVIL,
        DAMAGED_ANVIL,
        CHISELED_QUARTZ_BLOCK,
        QUARTZ_BLOCK,
        QUARTZ_BRICKS,
        QUARTZ_PILLAR,
        QUARTZ_STAIRS,
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
        BARRIER,
        LIGHT,
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
        PACKED_ICE,
        DIRT_PATH,
        SUNFLOWER,
        LILAC,
        ROSE_BUSH,
        PEONY,
        TALL_GRASS,
        LARGE_FERN,
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
        PRISMARINE,
        PRISMARINE_BRICKS,
        DARK_PRISMARINE,
        PRISMARINE_STAIRS,
        PRISMARINE_BRICK_STAIRS,
        DARK_PRISMARINE_STAIRS,
        SEA_LANTERN,
        RED_SANDSTONE,
        CHISELED_RED_SANDSTONE,
        CUT_RED_SANDSTONE,
        RED_SANDSTONE_STAIRS,
        REPEATING_COMMAND_BLOCK,
        CHAIN_COMMAND_BLOCK,
        MAGMA_BLOCK,
        NETHER_WART_BLOCK,
        WARPED_WART_BLOCK,
        RED_NETHER_BRICKS,
        BONE_BLOCK,
        STRUCTURE_VOID,
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
        TUBE_CORAL,
        BRAIN_CORAL,
        BUBBLE_CORAL,
        FIRE_CORAL,
        HORN_CORAL,
        DEAD_BRAIN_CORAL,
        DEAD_BUBBLE_CORAL,
        DEAD_FIRE_CORAL,
        DEAD_HORN_CORAL,
        DEAD_TUBE_CORAL,
        TUBE_CORAL_FAN,
        BRAIN_CORAL_FAN,
        BUBBLE_CORAL_FAN,
        FIRE_CORAL_FAN,
        HORN_CORAL_FAN,
        DEAD_TUBE_CORAL_FAN,
        DEAD_BRAIN_CORAL_FAN,
        DEAD_BUBBLE_CORAL_FAN,
        DEAD_FIRE_CORAL_FAN,
        DEAD_HORN_CORAL_FAN,
        BLUE_ICE,
        CONDUIT,
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
        COBBLED_DEEPSLATE_STAIRS,
        POLISHED_DEEPSLATE_STAIRS,
        DEEPSLATE_BRICK_STAIRS,
        DEEPSLATE_TILE_STAIRS,
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
        COBBLED_DEEPSLATE_SLAB,
        POLISHED_DEEPSLATE_SLAB,
        DEEPSLATE_BRICK_SLAB,
        DEEPSLATE_TILE_SLAB,
        SCAFFOLDING,
        REDSTONE,
        REDSTONE_TORCH,
        REDSTONE_BLOCK,
        REPEATER,
        COMPARATOR,
        PISTON,
        STICKY_PISTON,
        SLIME_BLOCK,
        HONEY_BLOCK,
        OBSERVER,
        HOPPER,
        DISPENSER,
        DROPPER,
        LECTERN,
        TARGET,
        LEVER,
        LIGHTNING_ROD,
        EXPOSED_LIGHTNING_ROD,
        WEATHERED_LIGHTNING_ROD,
        OXIDIZED_LIGHTNING_ROD,
        WAXED_LIGHTNING_ROD,
        WAXED_EXPOSED_LIGHTNING_ROD,
        WAXED_WEATHERED_LIGHTNING_ROD,
        WAXED_OXIDIZED_LIGHTNING_ROD,
        DAYLIGHT_DETECTOR,
        SCULK_SENSOR,
        CALIBRATED_SCULK_SENSOR,
        TRIPWIRE_HOOK,
        TRAPPED_CHEST,
        TNT,
        REDSTONE_LAMP,
        NOTE_BLOCK,
        STONE_BUTTON,
        POLISHED_BLACKSTONE_BUTTON,
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
        CRIMSON_BUTTON,
        WARPED_BUTTON,
        STONE_PRESSURE_PLATE,
        POLISHED_BLACKSTONE_PRESSURE_PLATE,
        LIGHT_WEIGHTED_PRESSURE_PLATE,
        HEAVY_WEIGHTED_PRESSURE_PLATE,
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
        CRIMSON_PRESSURE_PLATE,
        WARPED_PRESSURE_PLATE,
        IRON_DOOR,
        OAK_DOOR,
        SPRUCE_DOOR,
        BIRCH_DOOR,
        JUNGLE_DOOR,
        ACACIA_DOOR,
        CHERRY_DOOR,
        DARK_OAK_DOOR,
        PALE_OAK_DOOR,
        MANGROVE_DOOR,
        BAMBOO_DOOR,
        CRIMSON_DOOR,
        WARPED_DOOR,
        COPPER_DOOR,
        EXPOSED_COPPER_DOOR,
        WEATHERED_COPPER_DOOR,
        OXIDIZED_COPPER_DOOR,
        WAXED_COPPER_DOOR,
        WAXED_EXPOSED_COPPER_DOOR,
        WAXED_WEATHERED_COPPER_DOOR,
        WAXED_OXIDIZED_COPPER_DOOR,
        IRON_TRAPDOOR,
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
        CRIMSON_TRAPDOOR,
        WARPED_TRAPDOOR,
        COPPER_TRAPDOOR,
        EXPOSED_COPPER_TRAPDOOR,
        WEATHERED_COPPER_TRAPDOOR,
        OXIDIZED_COPPER_TRAPDOOR,
        WAXED_COPPER_TRAPDOOR,
        WAXED_EXPOSED_COPPER_TRAPDOOR,
        WAXED_WEATHERED_COPPER_TRAPDOOR,
        WAXED_OXIDIZED_COPPER_TRAPDOOR,
        OAK_FENCE_GATE,
        SPRUCE_FENCE_GATE,
        BIRCH_FENCE_GATE,
        JUNGLE_FENCE_GATE,
        ACACIA_FENCE_GATE,
        CHERRY_FENCE_GATE,
        DARK_OAK_FENCE_GATE,
        PALE_OAK_FENCE_GATE,
        MANGROVE_FENCE_GATE,
        BAMBOO_FENCE_GATE,
        CRIMSON_FENCE_GATE,
        WARPED_FENCE_GATE,
        POWERED_RAIL,
        DETECTOR_RAIL,
        RAIL,
        ACTIVATOR_RAIL,
        SADDLE,
        WHITE_HARNESS,
        ORANGE_HARNESS,
        MAGENTA_HARNESS,
        LIGHT_BLUE_HARNESS,
        YELLOW_HARNESS,
        LIME_HARNESS,
        PINK_HARNESS,
        GRAY_HARNESS,
        LIGHT_GRAY_HARNESS,
        CYAN_HARNESS,
        PURPLE_HARNESS,
        BLUE_HARNESS,
        BROWN_HARNESS,
        GREEN_HARNESS,
        RED_HARNESS,
        BLACK_HARNESS,
        MINECART,
        CHEST_MINECART,
        FURNACE_MINECART,
        TNT_MINECART,
        HOPPER_MINECART,
        CARROT_ON_A_STICK,
        WARPED_FUNGUS_ON_A_STICK,
        PHANTOM_MEMBRANE,
        ELYTRA,
        OAK_BOAT,
        OAK_CHEST_BOAT,
        SPRUCE_BOAT,
        SPRUCE_CHEST_BOAT,
        BIRCH_BOAT,
        BIRCH_CHEST_BOAT,
        JUNGLE_BOAT,
        JUNGLE_CHEST_BOAT,
        ACACIA_BOAT,
        ACACIA_CHEST_BOAT,
        CHERRY_BOAT,
        CHERRY_CHEST_BOAT,
        DARK_OAK_BOAT,
        DARK_OAK_CHEST_BOAT,
        PALE_OAK_BOAT,
        PALE_OAK_CHEST_BOAT,
        MANGROVE_BOAT,
        MANGROVE_CHEST_BOAT,
        BAMBOO_RAFT,
        BAMBOO_CHEST_RAFT,
        STRUCTURE_BLOCK,
        JIGSAW,
        TEST_BLOCK,
        TEST_INSTANCE_BLOCK,
        TURTLE_HELMET,
        TURTLE_SCUTE,
        ARMADILLO_SCUTE,
        WOLF_ARMOR,
        FLINT_AND_STEEL,
        BOWL,
        APPLE,
        BOW,
        ARROW,
        COAL,
        CHARCOAL,
        DIAMOND,
        EMERALD,
        LAPIS_LAZULI,
        QUARTZ,
        AMETHYST_SHARD,
        RAW_IRON,
        IRON_INGOT,
        RAW_COPPER,
        COPPER_INGOT,
        RAW_GOLD,
        GOLD_INGOT,
        NETHERITE_INGOT,
        NETHERITE_SCRAP,
        WOODEN_SWORD,
        WOODEN_SHOVEL,
        WOODEN_PICKAXE,
        WOODEN_AXE,
        WOODEN_HOE,
        COPPER_SWORD,
        COPPER_SHOVEL,
        COPPER_PICKAXE,
        COPPER_AXE,
        COPPER_HOE,
        STONE_SWORD,
        STONE_SHOVEL,
        STONE_PICKAXE,
        STONE_AXE,
        STONE_HOE,
        GOLDEN_SWORD,
        GOLDEN_SHOVEL,
        GOLDEN_PICKAXE,
        GOLDEN_AXE,
        GOLDEN_HOE,
        IRON_SWORD,
        IRON_SHOVEL,
        IRON_PICKAXE,
        IRON_AXE,
        IRON_HOE,
        DIAMOND_SWORD,
        DIAMOND_SHOVEL,
        DIAMOND_PICKAXE,
        DIAMOND_AXE,
        DIAMOND_HOE,
        NETHERITE_SWORD,
        NETHERITE_SHOVEL,
        NETHERITE_PICKAXE,
        NETHERITE_AXE,
        NETHERITE_HOE,
        STICK,
        MUSHROOM_STEW,
        STRING,
        FEATHER,
        GUNPOWDER,
        WHEAT_SEEDS,
        WHEAT,
        BREAD,
        LEATHER_HELMET,
        LEATHER_CHESTPLATE,
        LEATHER_LEGGINGS,
        LEATHER_BOOTS,
        COPPER_HELMET,
        COPPER_CHESTPLATE,
        COPPER_LEGGINGS,
        COPPER_BOOTS,
        CHAINMAIL_HELMET,
        CHAINMAIL_CHESTPLATE,
        CHAINMAIL_LEGGINGS,
        CHAINMAIL_BOOTS,
        IRON_HELMET,
        IRON_CHESTPLATE,
        IRON_LEGGINGS,
        IRON_BOOTS,
        DIAMOND_HELMET,
        DIAMOND_CHESTPLATE,
        DIAMOND_LEGGINGS,
        DIAMOND_BOOTS,
        GOLDEN_HELMET,
        GOLDEN_CHESTPLATE,
        GOLDEN_LEGGINGS,
        GOLDEN_BOOTS,
        NETHERITE_HELMET,
        NETHERITE_CHESTPLATE,
        NETHERITE_LEGGINGS,
        NETHERITE_BOOTS,
        FLINT,
        PORKCHOP,
        COOKED_PORKCHOP,
        PAINTING,
        GOLDEN_APPLE,
        ENCHANTED_GOLDEN_APPLE,
        OAK_SIGN,
        SPRUCE_SIGN,
        BIRCH_SIGN,
        JUNGLE_SIGN,
        ACACIA_SIGN,
        CHERRY_SIGN,
        DARK_OAK_SIGN,
        PALE_OAK_SIGN,
        MANGROVE_SIGN,
        BAMBOO_SIGN,
        CRIMSON_SIGN,
        WARPED_SIGN,
        OAK_HANGING_SIGN,
        SPRUCE_HANGING_SIGN,
        BIRCH_HANGING_SIGN,
        JUNGLE_HANGING_SIGN,
        ACACIA_HANGING_SIGN,
        CHERRY_HANGING_SIGN,
        DARK_OAK_HANGING_SIGN,
        PALE_OAK_HANGING_SIGN,
        MANGROVE_HANGING_SIGN,
        BAMBOO_HANGING_SIGN,
        CRIMSON_HANGING_SIGN,
        WARPED_HANGING_SIGN,
        BUCKET,
        WATER_BUCKET,
        LAVA_BUCKET,
        POWDER_SNOW_BUCKET,
        SNOWBALL,
        LEATHER,
        MILK_BUCKET,
        PUFFERFISH_BUCKET,
        SALMON_BUCKET,
        COD_BUCKET,
        TROPICAL_FISH_BUCKET,
        AXOLOTL_BUCKET,
        SULFUR_CUBE_BUCKET,
        TADPOLE_BUCKET,
        BRICK,
        CLAY_BALL,
        DRIED_KELP_BLOCK,
        PAPER,
        BOOK,
        SLIME_BALL,
        EGG,
        BLUE_EGG,
        BROWN_EGG,
        COMPASS,
        RECOVERY_COMPASS,
        BUNDLE,
        WHITE_BUNDLE,
        ORANGE_BUNDLE,
        MAGENTA_BUNDLE,
        LIGHT_BLUE_BUNDLE,
        YELLOW_BUNDLE,
        LIME_BUNDLE,
        PINK_BUNDLE,
        GRAY_BUNDLE,
        LIGHT_GRAY_BUNDLE,
        CYAN_BUNDLE,
        PURPLE_BUNDLE,
        BLUE_BUNDLE,
        BROWN_BUNDLE,
        GREEN_BUNDLE,
        RED_BUNDLE,
        BLACK_BUNDLE,
        FISHING_ROD,
        CLOCK,
        SPYGLASS,
        GLOWSTONE_DUST,
        COD,
        SALMON,
        TROPICAL_FISH,
        PUFFERFISH,
        COOKED_COD,
        COOKED_SALMON,
        INK_SAC,
        GLOW_INK_SAC,
        COCOA_BEANS,
        WHITE_DYE,
        ORANGE_DYE,
        MAGENTA_DYE,
        LIGHT_BLUE_DYE,
        YELLOW_DYE,
        LIME_DYE,
        PINK_DYE,
        GRAY_DYE,
        LIGHT_GRAY_DYE,
        CYAN_DYE,
        PURPLE_DYE,
        BLUE_DYE,
        BROWN_DYE,
        GREEN_DYE,
        RED_DYE,
        BLACK_DYE,
        BONE_MEAL,
        BONE,
        SUGAR,
        CAKE,
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
        COOKIE,
        CRAFTER,
        FILLED_MAP,
        SHEARS,
        MELON_SLICE,
        DRIED_KELP,
        PUMPKIN_SEEDS,
        MELON_SEEDS,
        BEEF,
        COOKED_BEEF,
        CHICKEN,
        COOKED_CHICKEN,
        ROTTEN_FLESH,
        ENDER_PEARL,
        BLAZE_ROD,
        GHAST_TEAR,
        GOLD_NUGGET,
        NETHER_WART,
        GLASS_BOTTLE,
        POTION,
        SPIDER_EYE,
        FERMENTED_SPIDER_EYE,
        BLAZE_POWDER,
        MAGMA_CREAM,
        BREWING_STAND,
        CAULDRON,
        ENDER_EYE,
        GLISTERING_MELON_SLICE,
        CHICKEN_SPAWN_EGG,
        COW_SPAWN_EGG,
        PIG_SPAWN_EGG,
        SHEEP_SPAWN_EGG,
        CAMEL_SPAWN_EGG,
        DONKEY_SPAWN_EGG,
        HORSE_SPAWN_EGG,
        MULE_SPAWN_EGG,
        CAT_SPAWN_EGG,
        PARROT_SPAWN_EGG,
        WOLF_SPAWN_EGG,
        ARMADILLO_SPAWN_EGG,
        BAT_SPAWN_EGG,
        BEE_SPAWN_EGG,
        FOX_SPAWN_EGG,
        GOAT_SPAWN_EGG,
        LLAMA_SPAWN_EGG,
        OCELOT_SPAWN_EGG,
        PANDA_SPAWN_EGG,
        POLAR_BEAR_SPAWN_EGG,
        RABBIT_SPAWN_EGG,
        AXOLOTL_SPAWN_EGG,
        COD_SPAWN_EGG,
        DOLPHIN_SPAWN_EGG,
        FROG_SPAWN_EGG,
        GLOW_SQUID_SPAWN_EGG,
        NAUTILUS_SPAWN_EGG,
        PUFFERFISH_SPAWN_EGG,
        SALMON_SPAWN_EGG,
        SQUID_SPAWN_EGG,
        TADPOLE_SPAWN_EGG,
        TROPICAL_FISH_SPAWN_EGG,
        TURTLE_SPAWN_EGG,
        ALLAY_SPAWN_EGG,
        MOOSHROOM_SPAWN_EGG,
        SNIFFER_SPAWN_EGG,
        SULFUR_CUBE_SPAWN_EGG,
        COPPER_GOLEM_SPAWN_EGG,
        IRON_GOLEM_SPAWN_EGG,
        SNOW_GOLEM_SPAWN_EGG,
        TRADER_LLAMA_SPAWN_EGG,
        VILLAGER_SPAWN_EGG,
        WANDERING_TRADER_SPAWN_EGG,
        BOGGED_SPAWN_EGG,
        CAMEL_HUSK_SPAWN_EGG,
        DROWNED_SPAWN_EGG,
        HUSK_SPAWN_EGG,
        PARCHED_SPAWN_EGG,
        SKELETON_SPAWN_EGG,
        SKELETON_HORSE_SPAWN_EGG,
        STRAY_SPAWN_EGG,
        WITHER_SPAWN_EGG,
        WITHER_SKELETON_SPAWN_EGG,
        ZOMBIE_SPAWN_EGG,
        ZOMBIE_HORSE_SPAWN_EGG,
        ZOMBIE_NAUTILUS_SPAWN_EGG,
        ZOMBIE_VILLAGER_SPAWN_EGG,
        CAVE_SPIDER_SPAWN_EGG,
        SPIDER_SPAWN_EGG,
        BREEZE_SPAWN_EGG,
        CREAKING_SPAWN_EGG,
        CREEPER_SPAWN_EGG,
        ELDER_GUARDIAN_SPAWN_EGG,
        GUARDIAN_SPAWN_EGG,
        PHANTOM_SPAWN_EGG,
        SILVERFISH_SPAWN_EGG,
        SLIME_SPAWN_EGG,
        WARDEN_SPAWN_EGG,
        WITCH_SPAWN_EGG,
        EVOKER_SPAWN_EGG,
        PILLAGER_SPAWN_EGG,
        RAVAGER_SPAWN_EGG,
        VINDICATOR_SPAWN_EGG,
        VEX_SPAWN_EGG,
        BLAZE_SPAWN_EGG,
        GHAST_SPAWN_EGG,
        HAPPY_GHAST_SPAWN_EGG,
        HOGLIN_SPAWN_EGG,
        MAGMA_CUBE_SPAWN_EGG,
        PIGLIN_SPAWN_EGG,
        PIGLIN_BRUTE_SPAWN_EGG,
        STRIDER_SPAWN_EGG,
        ZOGLIN_SPAWN_EGG,
        ZOMBIFIED_PIGLIN_SPAWN_EGG,
        ENDER_DRAGON_SPAWN_EGG,
        ENDERMAN_SPAWN_EGG,
        ENDERMITE_SPAWN_EGG,
        SHULKER_SPAWN_EGG,
        EXPERIENCE_BOTTLE,
        FIRE_CHARGE,
        WIND_CHARGE,
        WRITABLE_BOOK,
        WRITTEN_BOOK,
        BREEZE_ROD,
        MACE,
        ITEM_FRAME,
        GLOW_ITEM_FRAME,
        FLOWER_POT,
        CARROT,
        POTATO,
        BAKED_POTATO,
        POISONOUS_POTATO,
        MAP,
        GOLDEN_CARROT,
        SKELETON_SKULL,
        WITHER_SKELETON_SKULL,
        PLAYER_HEAD,
        ZOMBIE_HEAD,
        CREEPER_HEAD,
        DRAGON_HEAD,
        PIGLIN_HEAD,
        NETHER_STAR,
        PUMPKIN_PIE,
        FIREWORK_ROCKET,
        FIREWORK_STAR,
        ENCHANTED_BOOK,
        NETHER_BRICK,
        RESIN_BRICK,
        PRISMARINE_SHARD,
        PRISMARINE_CRYSTALS,
        RABBIT,
        COOKED_RABBIT,
        RABBIT_STEW,
        RABBIT_FOOT,
        RABBIT_HIDE,
        ARMOR_STAND,
        COPPER_HORSE_ARMOR,
        IRON_HORSE_ARMOR,
        GOLDEN_HORSE_ARMOR,
        DIAMOND_HORSE_ARMOR,
        NETHERITE_HORSE_ARMOR,
        LEATHER_HORSE_ARMOR,
        LEAD,
        NAME_TAG,
        COMMAND_BLOCK_MINECART,
        MUTTON,
        COOKED_MUTTON,
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
        END_CRYSTAL,
        CHORUS_FRUIT,
        POPPED_CHORUS_FRUIT,
        TORCHFLOWER_SEEDS,
        PITCHER_POD,
        BEETROOT,
        BEETROOT_SEEDS,
        BEETROOT_SOUP,
        DRAGON_BREATH,
        SPLASH_POTION,
        SPECTRAL_ARROW,
        TIPPED_ARROW,
        LINGERING_POTION,
        SHIELD,
        WOODEN_SPEAR,
        STONE_SPEAR,
        COPPER_SPEAR,
        IRON_SPEAR,
        GOLDEN_SPEAR,
        DIAMOND_SPEAR,
        NETHERITE_SPEAR,
        TOTEM_OF_UNDYING,
        SHULKER_SHELL,
        IRON_NUGGET,
        COPPER_NUGGET,
        KNOWLEDGE_BOOK,
        DEBUG_STICK,
        MUSIC_DISC_13,
        MUSIC_DISC_CAT,
        MUSIC_DISC_BLOCKS,
        MUSIC_DISC_BOUNCE,
        MUSIC_DISC_CHIRP,
        MUSIC_DISC_CREATOR,
        MUSIC_DISC_CREATOR_MUSIC_BOX,
        MUSIC_DISC_FAR,
        MUSIC_DISC_LAVA_CHICKEN,
        MUSIC_DISC_MALL,
        MUSIC_DISC_MELLOHI,
        MUSIC_DISC_STAL,
        MUSIC_DISC_STRAD,
        MUSIC_DISC_WARD,
        MUSIC_DISC_11,
        MUSIC_DISC_WAIT,
        MUSIC_DISC_OTHERSIDE,
        MUSIC_DISC_RELIC,
        MUSIC_DISC_5,
        MUSIC_DISC_PIGSTEP,
        MUSIC_DISC_PRECIPICE,
        MUSIC_DISC_TEARS,
        DISC_FRAGMENT_5,
        TRIDENT,
        NAUTILUS_SHELL,
        IRON_NAUTILUS_ARMOR,
        GOLDEN_NAUTILUS_ARMOR,
        DIAMOND_NAUTILUS_ARMOR,
        NETHERITE_NAUTILUS_ARMOR,
        COPPER_NAUTILUS_ARMOR,
        HEART_OF_THE_SEA,
        CROSSBOW,
        SUSPICIOUS_STEW,
        LOOM,
        FLOWER_BANNER_PATTERN,
        CREEPER_BANNER_PATTERN,
        SKULL_BANNER_PATTERN,
        MOJANG_BANNER_PATTERN,
        GLOBE_BANNER_PATTERN,
        PIGLIN_BANNER_PATTERN,
        FLOW_BANNER_PATTERN,
        GUSTER_BANNER_PATTERN,
        FIELD_MASONED_BANNER_PATTERN,
        BORDURE_INDENTED_BANNER_PATTERN,
        GOAT_HORN,
        COMPOSTER,
        BARREL,
        SMOKER,
        BLAST_FURNACE,
        CARTOGRAPHY_TABLE,
        FLETCHING_TABLE,
        GRINDSTONE,
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
        SWEET_BERRIES,
        GLOW_BERRIES,
        CAMPFIRE,
        SOUL_CAMPFIRE,
        SHROOMLIGHT,
        HONEYCOMB,
        BEE_NEST,
        BEEHIVE,
        HONEY_BOTTLE,
        HONEYCOMB_BLOCK,
        LODESTONE,
        CRYING_OBSIDIAN,
        BLACKSTONE,
        BLACKSTONE_SLAB,
        BLACKSTONE_STAIRS,
        GILDED_BLACKSTONE,
        POLISHED_BLACKSTONE,
        POLISHED_BLACKSTONE_SLAB,
        POLISHED_BLACKSTONE_STAIRS,
        CHISELED_POLISHED_BLACKSTONE,
        POLISHED_BLACKSTONE_BRICKS,
        POLISHED_BLACKSTONE_BRICK_SLAB,
        POLISHED_BLACKSTONE_BRICK_STAIRS,
        CRACKED_POLISHED_BLACKSTONE_BRICKS,
        RESPAWN_ANCHOR,
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
        SMALL_AMETHYST_BUD,
        MEDIUM_AMETHYST_BUD,
        LARGE_AMETHYST_BUD,
        AMETHYST_CLUSTER,
        POINTED_DRIPSTONE,
        SULFUR_SPIKE,
        OCHRE_FROGLIGHT,
        VERDANT_FROGLIGHT,
        PEARLESCENT_FROGLIGHT,
        FROGSPAWN,
        ECHO_SHARD,
        BRUSH,
        NETHERITE_UPGRADE_SMITHING_TEMPLATE,
        SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
        DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
        COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
        WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
        WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
        EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
        VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
        TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
        SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
        RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
        SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
        WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
        SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
        SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
        RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
        HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
        FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,
        BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
        ANGLER_POTTERY_SHERD,
        ARCHER_POTTERY_SHERD,
        ARMS_UP_POTTERY_SHERD,
        BLADE_POTTERY_SHERD,
        BREWER_POTTERY_SHERD,
        BURN_POTTERY_SHERD,
        DANGER_POTTERY_SHERD,
        EXPLORER_POTTERY_SHERD,
        FLOW_POTTERY_SHERD,
        FRIEND_POTTERY_SHERD,
        GUSTER_POTTERY_SHERD,
        HEART_POTTERY_SHERD,
        HEARTBREAK_POTTERY_SHERD,
        HOWL_POTTERY_SHERD,
        MINER_POTTERY_SHERD,
        MOURNER_POTTERY_SHERD,
        PLENTY_POTTERY_SHERD,
        PRIZE_POTTERY_SHERD,
        SCRAPE_POTTERY_SHERD,
        SHEAF_POTTERY_SHERD,
        SHELTER_POTTERY_SHERD,
        SKULL_POTTERY_SHERD,
        SNORT_POTTERY_SHERD,
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
        TRIAL_SPAWNER,
        TRIAL_KEY,
        OMINOUS_TRIAL_KEY,
        VAULT,
        OMINOUS_BOTTLE
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("acacia_logs"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"))),
        Map.entry(Key.key("anvil"), List.of(Key.key("anvil"), Key.key("chipped_anvil"), Key.key("damaged_anvil"))),
        Map.entry(Key.key("armadillo_food"), List.of(Key.key("spider_eye"))),
        Map.entry(Key.key("arrows"), List.of(Key.key("arrow"), Key.key("spectral_arrow"), Key.key("tipped_arrow"))),
        Map.entry(Key.key("axes"), List.of(Key.key("copper_axe"), Key.key("diamond_axe"), Key.key("golden_axe"), Key.key("iron_axe"), Key.key("netherite_axe"), Key.key("stone_axe"), Key.key("wooden_axe"))),
        Map.entry(Key.key("axolotl_food"), List.of(Key.key("tropical_fish_bucket"))),
        Map.entry(Key.key("bamboo_blocks"), List.of(Key.key("bamboo_block"), Key.key("stripped_bamboo_block"))),
        Map.entry(Key.key("banners"), List.of(Key.key("black_banner"), Key.key("blue_banner"), Key.key("brown_banner"), Key.key("cyan_banner"), Key.key("gray_banner"), Key.key("green_banner"), Key.key("light_blue_banner"), Key.key("light_gray_banner"), Key.key("lime_banner"), Key.key("magenta_banner"), Key.key("orange_banner"), Key.key("pink_banner"), Key.key("purple_banner"), Key.key("red_banner"), Key.key("white_banner"), Key.key("yellow_banner"))),
        Map.entry(Key.key("bars"), List.of(Key.key("copper_bars"), Key.key("exposed_copper_bars"), Key.key("iron_bars"), Key.key("oxidized_copper_bars"), Key.key("waxed_copper_bars"), Key.key("waxed_exposed_copper_bars"), Key.key("waxed_oxidized_copper_bars"), Key.key("waxed_weathered_copper_bars"), Key.key("weathered_copper_bars"))),
        Map.entry(Key.key("beacon_payment_items"), List.of(Key.key("diamond"), Key.key("emerald"), Key.key("gold_ingot"), Key.key("iron_ingot"), Key.key("netherite_ingot"))),
        Map.entry(Key.key("beds"), List.of(Key.key("black_bed"), Key.key("blue_bed"), Key.key("brown_bed"), Key.key("cyan_bed"), Key.key("gray_bed"), Key.key("green_bed"), Key.key("light_blue_bed"), Key.key("light_gray_bed"), Key.key("lime_bed"), Key.key("magenta_bed"), Key.key("orange_bed"), Key.key("pink_bed"), Key.key("purple_bed"), Key.key("red_bed"), Key.key("white_bed"), Key.key("yellow_bed"))),
        Map.entry(Key.key("bee_food"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("cactus_flower"), Key.key("cherry_leaves"), Key.key("chorus_flower"), Key.key("cornflower"), Key.key("dandelion"), Key.key("flowering_azalea"), Key.key("flowering_azalea_leaves"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_propagule"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("peony"), Key.key("pink_petals"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("spore_blossom"), Key.key("sunflower"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wildflowers"), Key.key("wither_rose"))),
        Map.entry(Key.key("birch_logs"), List.of(Key.key("birch_log"), Key.key("birch_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"))),
        Map.entry(Key.key("boats"), List.of(Key.key("acacia_boat"), Key.key("acacia_chest_boat"), Key.key("bamboo_chest_raft"), Key.key("bamboo_raft"), Key.key("birch_boat"), Key.key("birch_chest_boat"), Key.key("cherry_boat"), Key.key("cherry_chest_boat"), Key.key("dark_oak_boat"), Key.key("dark_oak_chest_boat"), Key.key("jungle_boat"), Key.key("jungle_chest_boat"), Key.key("mangrove_boat"), Key.key("mangrove_chest_boat"), Key.key("oak_boat"), Key.key("oak_chest_boat"), Key.key("pale_oak_boat"), Key.key("pale_oak_chest_boat"), Key.key("spruce_boat"), Key.key("spruce_chest_boat"))),
        Map.entry(Key.key("book_cloning_target"), List.of(Key.key("writable_book"))),
        Map.entry(Key.key("bookshelf_books"), List.of(Key.key("book"), Key.key("enchanted_book"), Key.key("knowledge_book"), Key.key("writable_book"), Key.key("written_book"))),
        Map.entry(Key.key("breaks_decorated_pots"), List.of(Key.key("copper_axe"), Key.key("copper_hoe"), Key.key("copper_pickaxe"), Key.key("copper_shovel"), Key.key("copper_sword"), Key.key("diamond_axe"), Key.key("diamond_hoe"), Key.key("diamond_pickaxe"), Key.key("diamond_shovel"), Key.key("diamond_sword"), Key.key("golden_axe"), Key.key("golden_hoe"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("golden_sword"), Key.key("iron_axe"), Key.key("iron_hoe"), Key.key("iron_pickaxe"), Key.key("iron_shovel"), Key.key("iron_sword"), Key.key("mace"), Key.key("netherite_axe"), Key.key("netherite_hoe"), Key.key("netherite_pickaxe"), Key.key("netherite_shovel"), Key.key("netherite_sword"), Key.key("stone_axe"), Key.key("stone_hoe"), Key.key("stone_pickaxe"), Key.key("stone_shovel"), Key.key("stone_sword"), Key.key("trident"), Key.key("wooden_axe"), Key.key("wooden_hoe"), Key.key("wooden_pickaxe"), Key.key("wooden_shovel"), Key.key("wooden_sword"))),
        Map.entry(Key.key("brewing_fuel"), List.of(Key.key("blaze_powder"))),
        Map.entry(Key.key("bundles"), List.of(Key.key("black_bundle"), Key.key("blue_bundle"), Key.key("brown_bundle"), Key.key("bundle"), Key.key("cyan_bundle"), Key.key("gray_bundle"), Key.key("green_bundle"), Key.key("light_blue_bundle"), Key.key("light_gray_bundle"), Key.key("lime_bundle"), Key.key("magenta_bundle"), Key.key("orange_bundle"), Key.key("pink_bundle"), Key.key("purple_bundle"), Key.key("red_bundle"), Key.key("white_bundle"), Key.key("yellow_bundle"))),
        Map.entry(Key.key("buttons"), List.of(Key.key("acacia_button"), Key.key("bamboo_button"), Key.key("birch_button"), Key.key("cherry_button"), Key.key("crimson_button"), Key.key("dark_oak_button"), Key.key("jungle_button"), Key.key("mangrove_button"), Key.key("oak_button"), Key.key("pale_oak_button"), Key.key("polished_blackstone_button"), Key.key("spruce_button"), Key.key("stone_button"), Key.key("warped_button"))),
        Map.entry(Key.key("camel_food"), List.of(Key.key("cactus"))),
        Map.entry(Key.key("camel_husk_food"), List.of(Key.key("rabbit_foot"))),
        Map.entry(Key.key("candles"), List.of(Key.key("black_candle"), Key.key("blue_candle"), Key.key("brown_candle"), Key.key("candle"), Key.key("cyan_candle"), Key.key("gray_candle"), Key.key("green_candle"), Key.key("light_blue_candle"), Key.key("light_gray_candle"), Key.key("lime_candle"), Key.key("magenta_candle"), Key.key("orange_candle"), Key.key("pink_candle"), Key.key("purple_candle"), Key.key("red_candle"), Key.key("white_candle"), Key.key("yellow_candle"))),
        Map.entry(Key.key("cat_collar_dyes"), List.of(Key.key("black_dye"), Key.key("blue_dye"), Key.key("brown_dye"), Key.key("cyan_dye"), Key.key("gray_dye"), Key.key("green_dye"), Key.key("light_blue_dye"), Key.key("light_gray_dye"), Key.key("lime_dye"), Key.key("magenta_dye"), Key.key("orange_dye"), Key.key("pink_dye"), Key.key("purple_dye"), Key.key("red_dye"), Key.key("white_dye"), Key.key("yellow_dye"))),
        Map.entry(Key.key("cat_food"), List.of(Key.key("cod"), Key.key("salmon"))),
        Map.entry(Key.key("cauldron_can_remove_dye"), List.of(Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_horse_armor"), Key.key("leather_leggings"), Key.key("wolf_armor"))),
        Map.entry(Key.key("chains"), List.of(Key.key("copper_chain"), Key.key("exposed_copper_chain"), Key.key("iron_chain"), Key.key("oxidized_copper_chain"), Key.key("waxed_copper_chain"), Key.key("waxed_exposed_copper_chain"), Key.key("waxed_oxidized_copper_chain"), Key.key("waxed_weathered_copper_chain"), Key.key("weathered_copper_chain"))),
        Map.entry(Key.key("cherry_logs"), List.of(Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"))),
        Map.entry(Key.key("chest_armor"), List.of(Key.key("chainmail_chestplate"), Key.key("copper_chestplate"), Key.key("diamond_chestplate"), Key.key("golden_chestplate"), Key.key("iron_chestplate"), Key.key("leather_chestplate"), Key.key("netherite_chestplate"))),
        Map.entry(Key.key("chest_boats"), List.of(Key.key("acacia_chest_boat"), Key.key("bamboo_chest_raft"), Key.key("birch_chest_boat"), Key.key("cherry_chest_boat"), Key.key("dark_oak_chest_boat"), Key.key("jungle_chest_boat"), Key.key("mangrove_chest_boat"), Key.key("oak_chest_boat"), Key.key("pale_oak_chest_boat"), Key.key("spruce_chest_boat"))),
        Map.entry(Key.key("chicken_food"), List.of(Key.key("beetroot_seeds"), Key.key("melon_seeds"), Key.key("pitcher_pod"), Key.key("pumpkin_seeds"), Key.key("torchflower_seeds"), Key.key("wheat_seeds"))),
        Map.entry(Key.key("cluster_max_harvestables"), List.of(Key.key("copper_pickaxe"), Key.key("diamond_pickaxe"), Key.key("golden_pickaxe"), Key.key("iron_pickaxe"), Key.key("netherite_pickaxe"), Key.key("stone_pickaxe"), Key.key("wooden_pickaxe"))),
        Map.entry(Key.key("coal_ores"), List.of(Key.key("coal_ore"), Key.key("deepslate_coal_ore"))),
        Map.entry(Key.key("coals"), List.of(Key.key("charcoal"), Key.key("coal"))),
        Map.entry(Key.key("compasses"), List.of(Key.key("compass"), Key.key("recovery_compass"))),
        Map.entry(Key.key("completes_find_tree_tutorial"), List.of(Key.key("acacia_leaves"), Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_leaves"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_leaves"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_leaves"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("nether_wart_block"), Key.key("oak_leaves"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_leaves"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_leaves"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("concrete"), List.of(Key.key("black_concrete"), Key.key("blue_concrete"), Key.key("brown_concrete"), Key.key("cyan_concrete"), Key.key("gray_concrete"), Key.key("green_concrete"), Key.key("light_blue_concrete"), Key.key("light_gray_concrete"), Key.key("lime_concrete"), Key.key("magenta_concrete"), Key.key("orange_concrete"), Key.key("pink_concrete"), Key.key("purple_concrete"), Key.key("red_concrete"), Key.key("white_concrete"), Key.key("yellow_concrete"))),
        Map.entry(Key.key("concrete_powders"), List.of(Key.key("black_concrete_powder"), Key.key("blue_concrete_powder"), Key.key("brown_concrete_powder"), Key.key("cyan_concrete_powder"), Key.key("gray_concrete_powder"), Key.key("green_concrete_powder"), Key.key("light_blue_concrete_powder"), Key.key("light_gray_concrete_powder"), Key.key("lime_concrete_powder"), Key.key("magenta_concrete_powder"), Key.key("orange_concrete_powder"), Key.key("pink_concrete_powder"), Key.key("purple_concrete_powder"), Key.key("red_concrete_powder"), Key.key("white_concrete_powder"), Key.key("yellow_concrete_powder"))),
        Map.entry(Key.key("copper"), List.of(Key.key("copper_block"), Key.key("exposed_copper"), Key.key("oxidized_copper"), Key.key("waxed_copper_block"), Key.key("waxed_exposed_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_weathered_copper"), Key.key("weathered_copper"))),
        Map.entry(Key.key("copper_chests"), List.of(Key.key("copper_chest"), Key.key("exposed_copper_chest"), Key.key("oxidized_copper_chest"), Key.key("waxed_copper_chest"), Key.key("waxed_exposed_copper_chest"), Key.key("waxed_oxidized_copper_chest"), Key.key("waxed_weathered_copper_chest"), Key.key("weathered_copper_chest"))),
        Map.entry(Key.key("copper_golem_statues"), List.of(Key.key("copper_golem_statue"), Key.key("exposed_copper_golem_statue"), Key.key("oxidized_copper_golem_statue"), Key.key("waxed_copper_golem_statue"), Key.key("waxed_exposed_copper_golem_statue"), Key.key("waxed_oxidized_copper_golem_statue"), Key.key("waxed_weathered_copper_golem_statue"), Key.key("weathered_copper_golem_statue"))),
        Map.entry(Key.key("copper_ores"), List.of(Key.key("copper_ore"), Key.key("deepslate_copper_ore"))),
        Map.entry(Key.key("copper_tool_materials"), List.of(Key.key("copper_ingot"))),
        Map.entry(Key.key("cow_food"), List.of(Key.key("wheat"))),
        Map.entry(Key.key("creeper_drop_music_discs"), List.of(Key.key("music_disc_11"), Key.key("music_disc_13"), Key.key("music_disc_blocks"), Key.key("music_disc_cat"), Key.key("music_disc_chirp"), Key.key("music_disc_far"), Key.key("music_disc_mall"), Key.key("music_disc_mellohi"), Key.key("music_disc_stal"), Key.key("music_disc_strad"), Key.key("music_disc_wait"), Key.key("music_disc_ward"))),
        Map.entry(Key.key("creeper_igniters"), List.of(Key.key("fire_charge"), Key.key("flint_and_steel"))),
        Map.entry(Key.key("crimson_stems"), List.of(Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"))),
        Map.entry(Key.key("dampens_vibrations"), List.of(Key.key("black_carpet"), Key.key("black_wool"), Key.key("blue_carpet"), Key.key("blue_wool"), Key.key("brown_carpet"), Key.key("brown_wool"), Key.key("cyan_carpet"), Key.key("cyan_wool"), Key.key("gray_carpet"), Key.key("gray_wool"), Key.key("green_carpet"), Key.key("green_wool"), Key.key("light_blue_carpet"), Key.key("light_blue_wool"), Key.key("light_gray_carpet"), Key.key("light_gray_wool"), Key.key("lime_carpet"), Key.key("lime_wool"), Key.key("magenta_carpet"), Key.key("magenta_wool"), Key.key("orange_carpet"), Key.key("orange_wool"), Key.key("pink_carpet"), Key.key("pink_wool"), Key.key("purple_carpet"), Key.key("purple_wool"), Key.key("red_carpet"), Key.key("red_wool"), Key.key("white_carpet"), Key.key("white_wool"), Key.key("yellow_carpet"), Key.key("yellow_wool"))),
        Map.entry(Key.key("dark_oak_logs"), List.of(Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"))),
        Map.entry(Key.key("decorated_pot_ingredients"), List.of(Key.key("angler_pottery_sherd"), Key.key("archer_pottery_sherd"), Key.key("arms_up_pottery_sherd"), Key.key("blade_pottery_sherd"), Key.key("brewer_pottery_sherd"), Key.key("brick"), Key.key("burn_pottery_sherd"), Key.key("danger_pottery_sherd"), Key.key("explorer_pottery_sherd"), Key.key("flow_pottery_sherd"), Key.key("friend_pottery_sherd"), Key.key("guster_pottery_sherd"), Key.key("heart_pottery_sherd"), Key.key("heartbreak_pottery_sherd"), Key.key("howl_pottery_sherd"), Key.key("miner_pottery_sherd"), Key.key("mourner_pottery_sherd"), Key.key("plenty_pottery_sherd"), Key.key("prize_pottery_sherd"), Key.key("scrape_pottery_sherd"), Key.key("sheaf_pottery_sherd"), Key.key("shelter_pottery_sherd"), Key.key("skull_pottery_sherd"), Key.key("snort_pottery_sherd"))),
        Map.entry(Key.key("decorated_pot_sherds"), List.of(Key.key("angler_pottery_sherd"), Key.key("archer_pottery_sherd"), Key.key("arms_up_pottery_sherd"), Key.key("blade_pottery_sherd"), Key.key("brewer_pottery_sherd"), Key.key("burn_pottery_sherd"), Key.key("danger_pottery_sherd"), Key.key("explorer_pottery_sherd"), Key.key("flow_pottery_sherd"), Key.key("friend_pottery_sherd"), Key.key("guster_pottery_sherd"), Key.key("heart_pottery_sherd"), Key.key("heartbreak_pottery_sherd"), Key.key("howl_pottery_sherd"), Key.key("miner_pottery_sherd"), Key.key("mourner_pottery_sherd"), Key.key("plenty_pottery_sherd"), Key.key("prize_pottery_sherd"), Key.key("scrape_pottery_sherd"), Key.key("sheaf_pottery_sherd"), Key.key("shelter_pottery_sherd"), Key.key("skull_pottery_sherd"), Key.key("snort_pottery_sherd"))),
        Map.entry(Key.key("diamond_ores"), List.of(Key.key("deepslate_diamond_ore"), Key.key("diamond_ore"))),
        Map.entry(Key.key("diamond_tool_materials"), List.of(Key.key("diamond"))),
        Map.entry(Key.key("dirt"), List.of(Key.key("coarse_dirt"), Key.key("dirt"), Key.key("rooted_dirt"))),
        Map.entry(Key.key("doors"), List.of(Key.key("acacia_door"), Key.key("bamboo_door"), Key.key("birch_door"), Key.key("cherry_door"), Key.key("copper_door"), Key.key("crimson_door"), Key.key("dark_oak_door"), Key.key("exposed_copper_door"), Key.key("iron_door"), Key.key("jungle_door"), Key.key("mangrove_door"), Key.key("oak_door"), Key.key("oxidized_copper_door"), Key.key("pale_oak_door"), Key.key("spruce_door"), Key.key("warped_door"), Key.key("waxed_copper_door"), Key.key("waxed_exposed_copper_door"), Key.key("waxed_oxidized_copper_door"), Key.key("waxed_weathered_copper_door"), Key.key("weathered_copper_door"))),
        Map.entry(Key.key("drowned_preferred_weapons"), List.of(Key.key("trident"))),
        Map.entry(Key.key("duplicates_allays"), List.of(Key.key("amethyst_shard"))),
        Map.entry(Key.key("dyes"), List.of(Key.key("black_dye"), Key.key("blue_dye"), Key.key("brown_dye"), Key.key("cyan_dye"), Key.key("gray_dye"), Key.key("green_dye"), Key.key("light_blue_dye"), Key.key("light_gray_dye"), Key.key("lime_dye"), Key.key("magenta_dye"), Key.key("orange_dye"), Key.key("pink_dye"), Key.key("purple_dye"), Key.key("red_dye"), Key.key("white_dye"), Key.key("yellow_dye"))),
        Map.entry(Key.key("eggs"), List.of(Key.key("blue_egg"), Key.key("brown_egg"), Key.key("egg"))),
        Map.entry(Key.key("emerald_ores"), List.of(Key.key("deepslate_emerald_ore"), Key.key("emerald_ore"))),
        Map.entry(Key.key("enchantable/armor"), List.of(Key.key("chainmail_boots"), Key.key("chainmail_chestplate"), Key.key("chainmail_helmet"), Key.key("chainmail_leggings"), Key.key("copper_boots"), Key.key("copper_chestplate"), Key.key("copper_helmet"), Key.key("copper_leggings"), Key.key("diamond_boots"), Key.key("diamond_chestplate"), Key.key("diamond_helmet"), Key.key("diamond_leggings"), Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_leggings"), Key.key("iron_boots"), Key.key("iron_chestplate"), Key.key("iron_helmet"), Key.key("iron_leggings"), Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_leggings"), Key.key("netherite_boots"), Key.key("netherite_chestplate"), Key.key("netherite_helmet"), Key.key("netherite_leggings"), Key.key("turtle_helmet"))),
        Map.entry(Key.key("enchantable/bow"), List.of(Key.key("bow"))),
        Map.entry(Key.key("enchantable/chest_armor"), List.of(Key.key("chainmail_chestplate"), Key.key("copper_chestplate"), Key.key("diamond_chestplate"), Key.key("golden_chestplate"), Key.key("iron_chestplate"), Key.key("leather_chestplate"), Key.key("netherite_chestplate"))),
        Map.entry(Key.key("enchantable/crossbow"), List.of(Key.key("crossbow"))),
        Map.entry(Key.key("enchantable/durability"), List.of(Key.key("bow"), Key.key("brush"), Key.key("carrot_on_a_stick"), Key.key("chainmail_boots"), Key.key("chainmail_chestplate"), Key.key("chainmail_helmet"), Key.key("chainmail_leggings"), Key.key("copper_axe"), Key.key("copper_boots"), Key.key("copper_chestplate"), Key.key("copper_helmet"), Key.key("copper_hoe"), Key.key("copper_leggings"), Key.key("copper_pickaxe"), Key.key("copper_shovel"), Key.key("copper_spear"), Key.key("copper_sword"), Key.key("crossbow"), Key.key("diamond_axe"), Key.key("diamond_boots"), Key.key("diamond_chestplate"), Key.key("diamond_helmet"), Key.key("diamond_hoe"), Key.key("diamond_leggings"), Key.key("diamond_pickaxe"), Key.key("diamond_shovel"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("elytra"), Key.key("fishing_rod"), Key.key("flint_and_steel"), Key.key("golden_axe"), Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_hoe"), Key.key("golden_leggings"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_axe"), Key.key("iron_boots"), Key.key("iron_chestplate"), Key.key("iron_helmet"), Key.key("iron_hoe"), Key.key("iron_leggings"), Key.key("iron_pickaxe"), Key.key("iron_shovel"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_leggings"), Key.key("mace"), Key.key("netherite_axe"), Key.key("netherite_boots"), Key.key("netherite_chestplate"), Key.key("netherite_helmet"), Key.key("netherite_hoe"), Key.key("netherite_leggings"), Key.key("netherite_pickaxe"), Key.key("netherite_shovel"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("shears"), Key.key("shield"), Key.key("stone_axe"), Key.key("stone_hoe"), Key.key("stone_pickaxe"), Key.key("stone_shovel"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("trident"), Key.key("turtle_helmet"), Key.key("warped_fungus_on_a_stick"), Key.key("wooden_axe"), Key.key("wooden_hoe"), Key.key("wooden_pickaxe"), Key.key("wooden_shovel"), Key.key("wooden_spear"), Key.key("wooden_sword"))),
        Map.entry(Key.key("enchantable/equippable"), List.of(Key.key("carved_pumpkin"), Key.key("chainmail_boots"), Key.key("chainmail_chestplate"), Key.key("chainmail_helmet"), Key.key("chainmail_leggings"), Key.key("copper_boots"), Key.key("copper_chestplate"), Key.key("copper_helmet"), Key.key("copper_leggings"), Key.key("creeper_head"), Key.key("diamond_boots"), Key.key("diamond_chestplate"), Key.key("diamond_helmet"), Key.key("diamond_leggings"), Key.key("dragon_head"), Key.key("elytra"), Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_leggings"), Key.key("iron_boots"), Key.key("iron_chestplate"), Key.key("iron_helmet"), Key.key("iron_leggings"), Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_leggings"), Key.key("netherite_boots"), Key.key("netherite_chestplate"), Key.key("netherite_helmet"), Key.key("netherite_leggings"), Key.key("piglin_head"), Key.key("player_head"), Key.key("skeleton_skull"), Key.key("turtle_helmet"), Key.key("wither_skeleton_skull"), Key.key("zombie_head"))),
        Map.entry(Key.key("enchantable/fire_aspect"), List.of(Key.key("copper_spear"), Key.key("copper_sword"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("mace"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("wooden_spear"), Key.key("wooden_sword"))),
        Map.entry(Key.key("enchantable/fishing"), List.of(Key.key("fishing_rod"))),
        Map.entry(Key.key("enchantable/foot_armor"), List.of(Key.key("chainmail_boots"), Key.key("copper_boots"), Key.key("diamond_boots"), Key.key("golden_boots"), Key.key("iron_boots"), Key.key("leather_boots"), Key.key("netherite_boots"))),
        Map.entry(Key.key("enchantable/head_armor"), List.of(Key.key("chainmail_helmet"), Key.key("copper_helmet"), Key.key("diamond_helmet"), Key.key("golden_helmet"), Key.key("iron_helmet"), Key.key("leather_helmet"), Key.key("netherite_helmet"), Key.key("turtle_helmet"))),
        Map.entry(Key.key("enchantable/leg_armor"), List.of(Key.key("chainmail_leggings"), Key.key("copper_leggings"), Key.key("diamond_leggings"), Key.key("golden_leggings"), Key.key("iron_leggings"), Key.key("leather_leggings"), Key.key("netherite_leggings"))),
        Map.entry(Key.key("enchantable/lunge"), List.of(Key.key("copper_spear"), Key.key("diamond_spear"), Key.key("golden_spear"), Key.key("iron_spear"), Key.key("netherite_spear"), Key.key("stone_spear"), Key.key("wooden_spear"))),
        Map.entry(Key.key("enchantable/mace"), List.of(Key.key("mace"))),
        Map.entry(Key.key("enchantable/melee_weapon"), List.of(Key.key("copper_spear"), Key.key("copper_sword"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("wooden_spear"), Key.key("wooden_sword"))),
        Map.entry(Key.key("enchantable/mining"), List.of(Key.key("copper_axe"), Key.key("copper_hoe"), Key.key("copper_pickaxe"), Key.key("copper_shovel"), Key.key("diamond_axe"), Key.key("diamond_hoe"), Key.key("diamond_pickaxe"), Key.key("diamond_shovel"), Key.key("golden_axe"), Key.key("golden_hoe"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("iron_axe"), Key.key("iron_hoe"), Key.key("iron_pickaxe"), Key.key("iron_shovel"), Key.key("netherite_axe"), Key.key("netherite_hoe"), Key.key("netherite_pickaxe"), Key.key("netherite_shovel"), Key.key("shears"), Key.key("stone_axe"), Key.key("stone_hoe"), Key.key("stone_pickaxe"), Key.key("stone_shovel"), Key.key("wooden_axe"), Key.key("wooden_hoe"), Key.key("wooden_pickaxe"), Key.key("wooden_shovel"))),
        Map.entry(Key.key("enchantable/mining_loot"), List.of(Key.key("copper_axe"), Key.key("copper_hoe"), Key.key("copper_pickaxe"), Key.key("copper_shovel"), Key.key("diamond_axe"), Key.key("diamond_hoe"), Key.key("diamond_pickaxe"), Key.key("diamond_shovel"), Key.key("golden_axe"), Key.key("golden_hoe"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("iron_axe"), Key.key("iron_hoe"), Key.key("iron_pickaxe"), Key.key("iron_shovel"), Key.key("netherite_axe"), Key.key("netherite_hoe"), Key.key("netherite_pickaxe"), Key.key("netherite_shovel"), Key.key("stone_axe"), Key.key("stone_hoe"), Key.key("stone_pickaxe"), Key.key("stone_shovel"), Key.key("wooden_axe"), Key.key("wooden_hoe"), Key.key("wooden_pickaxe"), Key.key("wooden_shovel"))),
        Map.entry(Key.key("enchantable/sharp_weapon"), List.of(Key.key("copper_axe"), Key.key("copper_spear"), Key.key("copper_sword"), Key.key("diamond_axe"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("golden_axe"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_axe"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("netherite_axe"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("stone_axe"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("wooden_axe"), Key.key("wooden_spear"), Key.key("wooden_sword"))),
        Map.entry(Key.key("enchantable/sweeping"), List.of(Key.key("copper_sword"), Key.key("diamond_sword"), Key.key("golden_sword"), Key.key("iron_sword"), Key.key("netherite_sword"), Key.key("stone_sword"), Key.key("wooden_sword"))),
        Map.entry(Key.key("enchantable/trident"), List.of(Key.key("trident"))),
        Map.entry(Key.key("enchantable/vanishing"), List.of(Key.key("bow"), Key.key("brush"), Key.key("carrot_on_a_stick"), Key.key("carved_pumpkin"), Key.key("chainmail_boots"), Key.key("chainmail_chestplate"), Key.key("chainmail_helmet"), Key.key("chainmail_leggings"), Key.key("compass"), Key.key("copper_axe"), Key.key("copper_boots"), Key.key("copper_chestplate"), Key.key("copper_helmet"), Key.key("copper_hoe"), Key.key("copper_leggings"), Key.key("copper_pickaxe"), Key.key("copper_shovel"), Key.key("copper_spear"), Key.key("copper_sword"), Key.key("creeper_head"), Key.key("crossbow"), Key.key("diamond_axe"), Key.key("diamond_boots"), Key.key("diamond_chestplate"), Key.key("diamond_helmet"), Key.key("diamond_hoe"), Key.key("diamond_leggings"), Key.key("diamond_pickaxe"), Key.key("diamond_shovel"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("dragon_head"), Key.key("elytra"), Key.key("fishing_rod"), Key.key("flint_and_steel"), Key.key("golden_axe"), Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_hoe"), Key.key("golden_leggings"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_axe"), Key.key("iron_boots"), Key.key("iron_chestplate"), Key.key("iron_helmet"), Key.key("iron_hoe"), Key.key("iron_leggings"), Key.key("iron_pickaxe"), Key.key("iron_shovel"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_leggings"), Key.key("mace"), Key.key("netherite_axe"), Key.key("netherite_boots"), Key.key("netherite_chestplate"), Key.key("netherite_helmet"), Key.key("netherite_hoe"), Key.key("netherite_leggings"), Key.key("netherite_pickaxe"), Key.key("netherite_shovel"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("piglin_head"), Key.key("player_head"), Key.key("shears"), Key.key("shield"), Key.key("skeleton_skull"), Key.key("stone_axe"), Key.key("stone_hoe"), Key.key("stone_pickaxe"), Key.key("stone_shovel"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("trident"), Key.key("turtle_helmet"), Key.key("warped_fungus_on_a_stick"), Key.key("wither_skeleton_skull"), Key.key("wooden_axe"), Key.key("wooden_hoe"), Key.key("wooden_pickaxe"), Key.key("wooden_shovel"), Key.key("wooden_spear"), Key.key("wooden_sword"), Key.key("zombie_head"))),
        Map.entry(Key.key("enchantable/weapon"), List.of(Key.key("copper_axe"), Key.key("copper_spear"), Key.key("copper_sword"), Key.key("diamond_axe"), Key.key("diamond_spear"), Key.key("diamond_sword"), Key.key("golden_axe"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("iron_axe"), Key.key("iron_spear"), Key.key("iron_sword"), Key.key("mace"), Key.key("netherite_axe"), Key.key("netherite_spear"), Key.key("netherite_sword"), Key.key("stone_axe"), Key.key("stone_spear"), Key.key("stone_sword"), Key.key("wooden_axe"), Key.key("wooden_spear"), Key.key("wooden_sword"))),
        Map.entry(Key.key("fence_gates"), List.of(Key.key("acacia_fence_gate"), Key.key("bamboo_fence_gate"), Key.key("birch_fence_gate"), Key.key("cherry_fence_gate"), Key.key("crimson_fence_gate"), Key.key("dark_oak_fence_gate"), Key.key("jungle_fence_gate"), Key.key("mangrove_fence_gate"), Key.key("oak_fence_gate"), Key.key("pale_oak_fence_gate"), Key.key("spruce_fence_gate"), Key.key("warped_fence_gate"))),
        Map.entry(Key.key("fences"), List.of(Key.key("acacia_fence"), Key.key("bamboo_fence"), Key.key("birch_fence"), Key.key("cherry_fence"), Key.key("crimson_fence"), Key.key("dark_oak_fence"), Key.key("jungle_fence"), Key.key("mangrove_fence"), Key.key("nether_brick_fence"), Key.key("oak_fence"), Key.key("pale_oak_fence"), Key.key("spruce_fence"), Key.key("warped_fence"))),
        Map.entry(Key.key("fishes"), List.of(Key.key("cod"), Key.key("cooked_cod"), Key.key("cooked_salmon"), Key.key("pufferfish"), Key.key("salmon"), Key.key("tropical_fish"))),
        Map.entry(Key.key("flowers"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("cactus_flower"), Key.key("cherry_leaves"), Key.key("chorus_flower"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("dandelion"), Key.key("flowering_azalea"), Key.key("flowering_azalea_leaves"), Key.key("golden_dandelion"), Key.key("lilac"), Key.key("lily_of_the_valley"), Key.key("mangrove_propagule"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("peony"), Key.key("pink_petals"), Key.key("pink_tulip"), Key.key("pitcher_plant"), Key.key("poppy"), Key.key("red_tulip"), Key.key("rose_bush"), Key.key("spore_blossom"), Key.key("sunflower"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wildflowers"), Key.key("wither_rose"))),
        Map.entry(Key.key("foot_armor"), List.of(Key.key("chainmail_boots"), Key.key("copper_boots"), Key.key("diamond_boots"), Key.key("golden_boots"), Key.key("iron_boots"), Key.key("leather_boots"), Key.key("netherite_boots"))),
        Map.entry(Key.key("fox_food"), List.of(Key.key("glow_berries"), Key.key("sweet_berries"))),
        Map.entry(Key.key("freeze_immune_wearables"), List.of(Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_horse_armor"), Key.key("leather_leggings"))),
        Map.entry(Key.key("frog_food"), List.of(Key.key("slime_ball"))),
        Map.entry(Key.key("furnace_minecart_fuel"), List.of(Key.key("charcoal"), Key.key("coal"))),
        Map.entry(Key.key("gaze_disguise_equipment"), List.of(Key.key("carved_pumpkin"))),
        Map.entry(Key.key("glazed_terracotta"), List.of(Key.key("black_glazed_terracotta"), Key.key("blue_glazed_terracotta"), Key.key("brown_glazed_terracotta"), Key.key("cyan_glazed_terracotta"), Key.key("gray_glazed_terracotta"), Key.key("green_glazed_terracotta"), Key.key("light_blue_glazed_terracotta"), Key.key("light_gray_glazed_terracotta"), Key.key("lime_glazed_terracotta"), Key.key("magenta_glazed_terracotta"), Key.key("orange_glazed_terracotta"), Key.key("pink_glazed_terracotta"), Key.key("purple_glazed_terracotta"), Key.key("red_glazed_terracotta"), Key.key("white_glazed_terracotta"), Key.key("yellow_glazed_terracotta"))),
        Map.entry(Key.key("goat_food"), List.of(Key.key("wheat"))),
        Map.entry(Key.key("gold_ores"), List.of(Key.key("deepslate_gold_ore"), Key.key("gold_ore"), Key.key("nether_gold_ore"))),
        Map.entry(Key.key("gold_tool_materials"), List.of(Key.key("gold_ingot"))),
        Map.entry(Key.key("grass_blocks"), List.of(Key.key("grass_block"), Key.key("mycelium"), Key.key("podzol"))),
        Map.entry(Key.key("hanging_signs"), List.of(Key.key("acacia_hanging_sign"), Key.key("bamboo_hanging_sign"), Key.key("birch_hanging_sign"), Key.key("cherry_hanging_sign"), Key.key("crimson_hanging_sign"), Key.key("dark_oak_hanging_sign"), Key.key("jungle_hanging_sign"), Key.key("mangrove_hanging_sign"), Key.key("oak_hanging_sign"), Key.key("pale_oak_hanging_sign"), Key.key("spruce_hanging_sign"), Key.key("warped_hanging_sign"))),
        Map.entry(Key.key("happy_ghast_food"), List.of(Key.key("snowball"))),
        Map.entry(Key.key("happy_ghast_tempt_items"), List.of(Key.key("black_harness"), Key.key("blue_harness"), Key.key("brown_harness"), Key.key("cyan_harness"), Key.key("gray_harness"), Key.key("green_harness"), Key.key("light_blue_harness"), Key.key("light_gray_harness"), Key.key("lime_harness"), Key.key("magenta_harness"), Key.key("orange_harness"), Key.key("pink_harness"), Key.key("purple_harness"), Key.key("red_harness"), Key.key("snowball"), Key.key("white_harness"), Key.key("yellow_harness"))),
        Map.entry(Key.key("harnesses"), List.of(Key.key("black_harness"), Key.key("blue_harness"), Key.key("brown_harness"), Key.key("cyan_harness"), Key.key("gray_harness"), Key.key("green_harness"), Key.key("light_blue_harness"), Key.key("light_gray_harness"), Key.key("lime_harness"), Key.key("magenta_harness"), Key.key("orange_harness"), Key.key("pink_harness"), Key.key("purple_harness"), Key.key("red_harness"), Key.key("white_harness"), Key.key("yellow_harness"))),
        Map.entry(Key.key("head_armor"), List.of(Key.key("chainmail_helmet"), Key.key("copper_helmet"), Key.key("diamond_helmet"), Key.key("golden_helmet"), Key.key("iron_helmet"), Key.key("leather_helmet"), Key.key("netherite_helmet"), Key.key("turtle_helmet"))),
        Map.entry(Key.key("hoes"), List.of(Key.key("copper_hoe"), Key.key("diamond_hoe"), Key.key("golden_hoe"), Key.key("iron_hoe"), Key.key("netherite_hoe"), Key.key("stone_hoe"), Key.key("wooden_hoe"))),
        Map.entry(Key.key("hoglin_food"), List.of(Key.key("crimson_fungus"))),
        Map.entry(Key.key("horse_food"), List.of(Key.key("apple"), Key.key("carrot"), Key.key("enchanted_golden_apple"), Key.key("golden_apple"), Key.key("golden_carrot"), Key.key("hay_block"), Key.key("sugar"), Key.key("wheat"))),
        Map.entry(Key.key("horse_tempt_items"), List.of(Key.key("enchanted_golden_apple"), Key.key("golden_apple"), Key.key("golden_carrot"))),
        Map.entry(Key.key("ignored_by_piglin_babies"), List.of(Key.key("leather"))),
        Map.entry(Key.key("iron_ores"), List.of(Key.key("deepslate_iron_ore"), Key.key("iron_ore"))),
        Map.entry(Key.key("iron_tool_materials"), List.of(Key.key("iron_ingot"))),
        Map.entry(Key.key("jungle_logs"), List.of(Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"))),
        Map.entry(Key.key("lanterns"), List.of(Key.key("copper_lantern"), Key.key("exposed_copper_lantern"), Key.key("lantern"), Key.key("oxidized_copper_lantern"), Key.key("soul_lantern"), Key.key("waxed_copper_lantern"), Key.key("waxed_exposed_copper_lantern"), Key.key("waxed_oxidized_copper_lantern"), Key.key("waxed_weathered_copper_lantern"), Key.key("weathered_copper_lantern"))),
        Map.entry(Key.key("lapis_ores"), List.of(Key.key("deepslate_lapis_ore"), Key.key("lapis_ore"))),
        Map.entry(Key.key("leaves"), List.of(Key.key("acacia_leaves"), Key.key("azalea_leaves"), Key.key("birch_leaves"), Key.key("cherry_leaves"), Key.key("dark_oak_leaves"), Key.key("flowering_azalea_leaves"), Key.key("jungle_leaves"), Key.key("mangrove_leaves"), Key.key("oak_leaves"), Key.key("pale_oak_leaves"), Key.key("spruce_leaves"))),
        Map.entry(Key.key("lectern_books"), List.of(Key.key("writable_book"), Key.key("written_book"))),
        Map.entry(Key.key("leg_armor"), List.of(Key.key("chainmail_leggings"), Key.key("copper_leggings"), Key.key("diamond_leggings"), Key.key("golden_leggings"), Key.key("iron_leggings"), Key.key("leather_leggings"), Key.key("netherite_leggings"))),
        Map.entry(Key.key("lightning_rods"), List.of(Key.key("exposed_lightning_rod"), Key.key("lightning_rod"), Key.key("oxidized_lightning_rod"), Key.key("waxed_exposed_lightning_rod"), Key.key("waxed_lightning_rod"), Key.key("waxed_oxidized_lightning_rod"), Key.key("waxed_weathered_lightning_rod"), Key.key("weathered_lightning_rod"))),
        Map.entry(Key.key("llama_food"), List.of(Key.key("hay_block"), Key.key("wheat"))),
        Map.entry(Key.key("llama_tempt_items"), List.of(Key.key("hay_block"))),
        Map.entry(Key.key("logs"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_stem"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("logs_that_burn"), List.of(Key.key("acacia_log"), Key.key("acacia_wood"), Key.key("birch_log"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_wood"), Key.key("dark_oak_log"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"))),
        Map.entry(Key.key("loom_dyes"), List.of(Key.key("black_dye"), Key.key("blue_dye"), Key.key("brown_dye"), Key.key("cyan_dye"), Key.key("gray_dye"), Key.key("green_dye"), Key.key("light_blue_dye"), Key.key("light_gray_dye"), Key.key("lime_dye"), Key.key("magenta_dye"), Key.key("orange_dye"), Key.key("pink_dye"), Key.key("purple_dye"), Key.key("red_dye"), Key.key("white_dye"), Key.key("yellow_dye"))),
        Map.entry(Key.key("loom_patterns"), List.of(Key.key("bordure_indented_banner_pattern"), Key.key("creeper_banner_pattern"), Key.key("field_masoned_banner_pattern"), Key.key("flow_banner_pattern"), Key.key("flower_banner_pattern"), Key.key("globe_banner_pattern"), Key.key("guster_banner_pattern"), Key.key("mojang_banner_pattern"), Key.key("piglin_banner_pattern"), Key.key("skull_banner_pattern"))),
        Map.entry(Key.key("mangrove_logs"), List.of(Key.key("mangrove_log"), Key.key("mangrove_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"))),
        Map.entry(Key.key("map_invisibility_equipment"), List.of(Key.key("carved_pumpkin"))),
        Map.entry(Key.key("meat"), List.of(Key.key("beef"), Key.key("chicken"), Key.key("cooked_beef"), Key.key("cooked_chicken"), Key.key("cooked_mutton"), Key.key("cooked_porkchop"), Key.key("cooked_rabbit"), Key.key("mutton"), Key.key("porkchop"), Key.key("rabbit"), Key.key("rotten_flesh"))),
        Map.entry(Key.key("metal_nuggets"), List.of(Key.key("copper_nugget"), Key.key("gold_nugget"), Key.key("iron_nugget"))),
        Map.entry(Key.key("moss_blocks"), List.of(Key.key("moss_block"), Key.key("pale_moss_block"))),
        Map.entry(Key.key("mud"), List.of(Key.key("mud"), Key.key("muddy_mangrove_roots"))),
        Map.entry(Key.key("nautilus_bucket_food"), List.of(Key.key("cod_bucket"), Key.key("pufferfish_bucket"), Key.key("salmon_bucket"), Key.key("tropical_fish_bucket"))),
        Map.entry(Key.key("nautilus_food"), List.of(Key.key("cod"), Key.key("cod_bucket"), Key.key("cooked_cod"), Key.key("cooked_salmon"), Key.key("pufferfish"), Key.key("pufferfish_bucket"), Key.key("salmon"), Key.key("salmon_bucket"), Key.key("tropical_fish"), Key.key("tropical_fish_bucket"))),
        Map.entry(Key.key("nautilus_taming_items"), List.of(Key.key("pufferfish"), Key.key("pufferfish_bucket"))),
        Map.entry(Key.key("netherite_tool_materials"), List.of(Key.key("netherite_ingot"))),
        Map.entry(Key.key("non_flammable_wood"), List.of(Key.key("crimson_button"), Key.key("crimson_door"), Key.key("crimson_fence"), Key.key("crimson_fence_gate"), Key.key("crimson_hanging_sign"), Key.key("crimson_hyphae"), Key.key("crimson_planks"), Key.key("crimson_pressure_plate"), Key.key("crimson_shelf"), Key.key("crimson_sign"), Key.key("crimson_slab"), Key.key("crimson_stairs"), Key.key("crimson_stem"), Key.key("crimson_trapdoor"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_button"), Key.key("warped_door"), Key.key("warped_fence"), Key.key("warped_fence_gate"), Key.key("warped_hanging_sign"), Key.key("warped_hyphae"), Key.key("warped_planks"), Key.key("warped_pressure_plate"), Key.key("warped_shelf"), Key.key("warped_sign"), Key.key("warped_slab"), Key.key("warped_stairs"), Key.key("warped_stem"), Key.key("warped_trapdoor"))),
        Map.entry(Key.key("noteblock_top_instruments"), List.of(Key.key("creeper_head"), Key.key("dragon_head"), Key.key("piglin_head"), Key.key("player_head"), Key.key("skeleton_skull"), Key.key("wither_skeleton_skull"), Key.key("zombie_head"))),
        Map.entry(Key.key("oak_logs"), List.of(Key.key("oak_log"), Key.key("oak_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"))),
        Map.entry(Key.key("ocelot_food"), List.of(Key.key("cod"), Key.key("salmon"))),
        Map.entry(Key.key("pale_oak_logs"), List.of(Key.key("pale_oak_log"), Key.key("pale_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"))),
        Map.entry(Key.key("panda_eats_from_ground"), List.of(Key.key("bamboo"), Key.key("cake"))),
        Map.entry(Key.key("panda_food"), List.of(Key.key("bamboo"))),
        Map.entry(Key.key("parrot_food"), List.of(Key.key("beetroot_seeds"), Key.key("melon_seeds"), Key.key("pitcher_pod"), Key.key("pumpkin_seeds"), Key.key("torchflower_seeds"), Key.key("wheat_seeds"))),
        Map.entry(Key.key("parrot_poisonous_food"), List.of(Key.key("cookie"))),
        Map.entry(Key.key("pickaxes"), List.of(Key.key("copper_pickaxe"), Key.key("diamond_pickaxe"), Key.key("golden_pickaxe"), Key.key("iron_pickaxe"), Key.key("netherite_pickaxe"), Key.key("stone_pickaxe"), Key.key("wooden_pickaxe"))),
        Map.entry(Key.key("pig_food"), List.of(Key.key("beetroot"), Key.key("carrot"), Key.key("potato"))),
        Map.entry(Key.key("piglin_food"), List.of(Key.key("cooked_porkchop"), Key.key("porkchop"))),
        Map.entry(Key.key("piglin_loved"), List.of(Key.key("bell"), Key.key("clock"), Key.key("deepslate_gold_ore"), Key.key("enchanted_golden_apple"), Key.key("gilded_blackstone"), Key.key("glistering_melon_slice"), Key.key("gold_block"), Key.key("gold_ingot"), Key.key("gold_ore"), Key.key("golden_apple"), Key.key("golden_axe"), Key.key("golden_boots"), Key.key("golden_carrot"), Key.key("golden_chestplate"), Key.key("golden_dandelion"), Key.key("golden_helmet"), Key.key("golden_hoe"), Key.key("golden_horse_armor"), Key.key("golden_leggings"), Key.key("golden_nautilus_armor"), Key.key("golden_pickaxe"), Key.key("golden_shovel"), Key.key("golden_spear"), Key.key("golden_sword"), Key.key("light_weighted_pressure_plate"), Key.key("nether_gold_ore"), Key.key("raw_gold"), Key.key("raw_gold_block"))),
        Map.entry(Key.key("piglin_preferred_weapons"), List.of(Key.key("crossbow"), Key.key("golden_spear"))),
        Map.entry(Key.key("piglin_repellents"), List.of(Key.key("soul_campfire"), Key.key("soul_lantern"), Key.key("soul_torch"))),
        Map.entry(Key.key("piglin_safe_armor"), List.of(Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_leggings"))),
        Map.entry(Key.key("pillager_preferred_weapons"), List.of(Key.key("crossbow"))),
        Map.entry(Key.key("planks"), List.of(Key.key("acacia_planks"), Key.key("bamboo_planks"), Key.key("birch_planks"), Key.key("cherry_planks"), Key.key("crimson_planks"), Key.key("dark_oak_planks"), Key.key("jungle_planks"), Key.key("mangrove_planks"), Key.key("oak_planks"), Key.key("pale_oak_planks"), Key.key("spruce_planks"), Key.key("warped_planks"))),
        Map.entry(Key.key("rabbit_food"), List.of(Key.key("carrot"), Key.key("dandelion"), Key.key("golden_carrot"))),
        Map.entry(Key.key("rails"), List.of(Key.key("activator_rail"), Key.key("detector_rail"), Key.key("powered_rail"), Key.key("rail"))),
        Map.entry(Key.key("redstone_ores"), List.of(Key.key("deepslate_redstone_ore"), Key.key("redstone_ore"))),
        Map.entry(Key.key("repairs_chain_armor"), List.of(Key.key("iron_ingot"))),
        Map.entry(Key.key("repairs_copper_armor"), List.of(Key.key("copper_ingot"))),
        Map.entry(Key.key("repairs_diamond_armor"), List.of(Key.key("diamond"))),
        Map.entry(Key.key("repairs_gold_armor"), List.of(Key.key("gold_ingot"))),
        Map.entry(Key.key("repairs_iron_armor"), List.of(Key.key("iron_ingot"))),
        Map.entry(Key.key("repairs_leather_armor"), List.of(Key.key("leather"))),
        Map.entry(Key.key("repairs_netherite_armor"), List.of(Key.key("netherite_ingot"))),
        Map.entry(Key.key("repairs_turtle_helmet"), List.of(Key.key("turtle_scute"))),
        Map.entry(Key.key("repairs_wolf_armor"), List.of(Key.key("armadillo_scute"))),
        Map.entry(Key.key("sand"), List.of(Key.key("red_sand"), Key.key("sand"), Key.key("suspicious_sand"))),
        Map.entry(Key.key("saplings"), List.of(Key.key("acacia_sapling"), Key.key("azalea"), Key.key("birch_sapling"), Key.key("cherry_sapling"), Key.key("dark_oak_sapling"), Key.key("flowering_azalea"), Key.key("jungle_sapling"), Key.key("mangrove_propagule"), Key.key("oak_sapling"), Key.key("pale_oak_sapling"), Key.key("spruce_sapling"))),
        Map.entry(Key.key("shearable_from_copper_golem"), List.of(Key.key("poppy"))),
        Map.entry(Key.key("sheep_food"), List.of(Key.key("wheat"))),
        Map.entry(Key.key("shovels"), List.of(Key.key("copper_shovel"), Key.key("diamond_shovel"), Key.key("golden_shovel"), Key.key("iron_shovel"), Key.key("netherite_shovel"), Key.key("stone_shovel"), Key.key("wooden_shovel"))),
        Map.entry(Key.key("shulker_boxes"), List.of(Key.key("black_shulker_box"), Key.key("blue_shulker_box"), Key.key("brown_shulker_box"), Key.key("cyan_shulker_box"), Key.key("gray_shulker_box"), Key.key("green_shulker_box"), Key.key("light_blue_shulker_box"), Key.key("light_gray_shulker_box"), Key.key("lime_shulker_box"), Key.key("magenta_shulker_box"), Key.key("orange_shulker_box"), Key.key("pink_shulker_box"), Key.key("purple_shulker_box"), Key.key("red_shulker_box"), Key.key("shulker_box"), Key.key("white_shulker_box"), Key.key("yellow_shulker_box"))),
        Map.entry(Key.key("signs"), List.of(Key.key("acacia_sign"), Key.key("bamboo_sign"), Key.key("birch_sign"), Key.key("cherry_sign"), Key.key("crimson_sign"), Key.key("dark_oak_sign"), Key.key("jungle_sign"), Key.key("mangrove_sign"), Key.key("oak_sign"), Key.key("pale_oak_sign"), Key.key("spruce_sign"), Key.key("warped_sign"))),
        Map.entry(Key.key("skeleton_preferred_weapons"), List.of(Key.key("bow"))),
        Map.entry(Key.key("skulls"), List.of(Key.key("creeper_head"), Key.key("dragon_head"), Key.key("piglin_head"), Key.key("player_head"), Key.key("skeleton_skull"), Key.key("wither_skeleton_skull"), Key.key("zombie_head"))),
        Map.entry(Key.key("slabs"), List.of(Key.key("acacia_slab"), Key.key("andesite_slab"), Key.key("bamboo_mosaic_slab"), Key.key("bamboo_slab"), Key.key("birch_slab"), Key.key("blackstone_slab"), Key.key("brick_slab"), Key.key("cherry_slab"), Key.key("cinnabar_brick_slab"), Key.key("cinnabar_slab"), Key.key("cobbled_deepslate_slab"), Key.key("cobblestone_slab"), Key.key("crimson_slab"), Key.key("cut_copper_slab"), Key.key("cut_red_sandstone_slab"), Key.key("cut_sandstone_slab"), Key.key("dark_oak_slab"), Key.key("dark_prismarine_slab"), Key.key("deepslate_brick_slab"), Key.key("deepslate_tile_slab"), Key.key("diorite_slab"), Key.key("end_stone_brick_slab"), Key.key("exposed_cut_copper_slab"), Key.key("granite_slab"), Key.key("jungle_slab"), Key.key("mangrove_slab"), Key.key("mossy_cobblestone_slab"), Key.key("mossy_stone_brick_slab"), Key.key("mud_brick_slab"), Key.key("nether_brick_slab"), Key.key("oak_slab"), Key.key("oxidized_cut_copper_slab"), Key.key("pale_oak_slab"), Key.key("petrified_oak_slab"), Key.key("polished_andesite_slab"), Key.key("polished_blackstone_brick_slab"), Key.key("polished_blackstone_slab"), Key.key("polished_cinnabar_slab"), Key.key("polished_deepslate_slab"), Key.key("polished_diorite_slab"), Key.key("polished_granite_slab"), Key.key("polished_sulfur_slab"), Key.key("polished_tuff_slab"), Key.key("prismarine_brick_slab"), Key.key("prismarine_slab"), Key.key("purpur_slab"), Key.key("quartz_slab"), Key.key("red_nether_brick_slab"), Key.key("red_sandstone_slab"), Key.key("resin_brick_slab"), Key.key("sandstone_slab"), Key.key("smooth_quartz_slab"), Key.key("smooth_red_sandstone_slab"), Key.key("smooth_sandstone_slab"), Key.key("smooth_stone_slab"), Key.key("spruce_slab"), Key.key("stone_brick_slab"), Key.key("stone_slab"), Key.key("sulfur_brick_slab"), Key.key("sulfur_slab"), Key.key("tuff_brick_slab"), Key.key("tuff_slab"), Key.key("warped_slab"), Key.key("waxed_cut_copper_slab"), Key.key("waxed_exposed_cut_copper_slab"), Key.key("waxed_oxidized_cut_copper_slab"), Key.key("waxed_weathered_cut_copper_slab"), Key.key("weathered_cut_copper_slab"))),
        Map.entry(Key.key("small_flowers"), List.of(Key.key("allium"), Key.key("azure_bluet"), Key.key("blue_orchid"), Key.key("closed_eyeblossom"), Key.key("cornflower"), Key.key("dandelion"), Key.key("golden_dandelion"), Key.key("lily_of_the_valley"), Key.key("open_eyeblossom"), Key.key("orange_tulip"), Key.key("oxeye_daisy"), Key.key("pink_tulip"), Key.key("poppy"), Key.key("red_tulip"), Key.key("torchflower"), Key.key("white_tulip"), Key.key("wither_rose"))),
        Map.entry(Key.key("smelts_to_glass"), List.of(Key.key("red_sand"), Key.key("sand"))),
        Map.entry(Key.key("sniffer_food"), List.of(Key.key("torchflower_seeds"))),
        Map.entry(Key.key("soul_fire_base_blocks"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("spears"), List.of(Key.key("copper_spear"), Key.key("diamond_spear"), Key.key("golden_spear"), Key.key("iron_spear"), Key.key("netherite_spear"), Key.key("stone_spear"), Key.key("wooden_spear"))),
        Map.entry(Key.key("spruce_logs"), List.of(Key.key("spruce_log"), Key.key("spruce_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"))),
        Map.entry(Key.key("stairs"), List.of(Key.key("acacia_stairs"), Key.key("andesite_stairs"), Key.key("bamboo_mosaic_stairs"), Key.key("bamboo_stairs"), Key.key("birch_stairs"), Key.key("blackstone_stairs"), Key.key("brick_stairs"), Key.key("cherry_stairs"), Key.key("cinnabar_brick_stairs"), Key.key("cinnabar_stairs"), Key.key("cobbled_deepslate_stairs"), Key.key("cobblestone_stairs"), Key.key("crimson_stairs"), Key.key("cut_copper_stairs"), Key.key("dark_oak_stairs"), Key.key("dark_prismarine_stairs"), Key.key("deepslate_brick_stairs"), Key.key("deepslate_tile_stairs"), Key.key("diorite_stairs"), Key.key("end_stone_brick_stairs"), Key.key("exposed_cut_copper_stairs"), Key.key("granite_stairs"), Key.key("jungle_stairs"), Key.key("mangrove_stairs"), Key.key("mossy_cobblestone_stairs"), Key.key("mossy_stone_brick_stairs"), Key.key("mud_brick_stairs"), Key.key("nether_brick_stairs"), Key.key("oak_stairs"), Key.key("oxidized_cut_copper_stairs"), Key.key("pale_oak_stairs"), Key.key("polished_andesite_stairs"), Key.key("polished_blackstone_brick_stairs"), Key.key("polished_blackstone_stairs"), Key.key("polished_cinnabar_stairs"), Key.key("polished_deepslate_stairs"), Key.key("polished_diorite_stairs"), Key.key("polished_granite_stairs"), Key.key("polished_sulfur_stairs"), Key.key("polished_tuff_stairs"), Key.key("prismarine_brick_stairs"), Key.key("prismarine_stairs"), Key.key("purpur_stairs"), Key.key("quartz_stairs"), Key.key("red_nether_brick_stairs"), Key.key("red_sandstone_stairs"), Key.key("resin_brick_stairs"), Key.key("sandstone_stairs"), Key.key("smooth_quartz_stairs"), Key.key("smooth_red_sandstone_stairs"), Key.key("smooth_sandstone_stairs"), Key.key("spruce_stairs"), Key.key("stone_brick_stairs"), Key.key("stone_stairs"), Key.key("sulfur_brick_stairs"), Key.key("sulfur_stairs"), Key.key("tuff_brick_stairs"), Key.key("tuff_stairs"), Key.key("warped_stairs"), Key.key("waxed_cut_copper_stairs"), Key.key("waxed_exposed_cut_copper_stairs"), Key.key("waxed_oxidized_cut_copper_stairs"), Key.key("waxed_weathered_cut_copper_stairs"), Key.key("weathered_cut_copper_stairs"))),
        Map.entry(Key.key("stone_bricks"), List.of(Key.key("chiseled_stone_bricks"), Key.key("cracked_stone_bricks"), Key.key("mossy_stone_bricks"), Key.key("stone_bricks"))),
        Map.entry(Key.key("stone_buttons"), List.of(Key.key("polished_blackstone_button"), Key.key("stone_button"))),
        Map.entry(Key.key("stone_crafting_materials"), List.of(Key.key("blackstone"), Key.key("cobbled_deepslate"), Key.key("cobblestone"))),
        Map.entry(Key.key("stone_tool_materials"), List.of(Key.key("blackstone"), Key.key("cobbled_deepslate"), Key.key("cobblestone"))),
        Map.entry(Key.key("strider_food"), List.of(Key.key("warped_fungus"))),
        Map.entry(Key.key("strider_tempt_items"), List.of(Key.key("warped_fungus"), Key.key("warped_fungus_on_a_stick"))),
        Map.entry(Key.key("sulfur_cube_archetype/bouncy"), List.of(Key.key("acacia_log"), Key.key("acacia_planks"), Key.key("acacia_wood"), Key.key("bamboo_block"), Key.key("bamboo_mosaic"), Key.key("bamboo_planks"), Key.key("birch_log"), Key.key("birch_planks"), Key.key("birch_wood"), Key.key("cherry_log"), Key.key("cherry_planks"), Key.key("cherry_wood"), Key.key("crimson_hyphae"), Key.key("crimson_planks"), Key.key("crimson_stem"), Key.key("dark_oak_log"), Key.key("dark_oak_planks"), Key.key("dark_oak_wood"), Key.key("jungle_log"), Key.key("jungle_planks"), Key.key("jungle_wood"), Key.key("mangrove_log"), Key.key("mangrove_planks"), Key.key("mangrove_wood"), Key.key("oak_log"), Key.key("oak_planks"), Key.key("oak_wood"), Key.key("pale_oak_log"), Key.key("pale_oak_planks"), Key.key("pale_oak_wood"), Key.key("spruce_log"), Key.key("spruce_planks"), Key.key("spruce_wood"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_bamboo_block"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_planks"), Key.key("warped_stem"))),
        Map.entry(Key.key("sulfur_cube_archetype/explosive"), List.of(Key.key("tnt"))),
        Map.entry(Key.key("sulfur_cube_archetype/fast_flat"), List.of(Key.key("brain_coral_block"), Key.key("bubble_coral_block"), Key.key("carved_pumpkin"), Key.key("chiseled_resin_bricks"), Key.key("dead_brain_coral_block"), Key.key("dead_bubble_coral_block"), Key.key("dead_fire_coral_block"), Key.key("dead_horn_coral_block"), Key.key("dead_tube_coral_block"), Key.key("dried_kelp_block"), Key.key("fire_coral_block"), Key.key("hay_block"), Key.key("horn_coral_block"), Key.key("jack_o_lantern"), Key.key("melon"), Key.key("moss_block"), Key.key("ochre_froglight"), Key.key("pale_moss_block"), Key.key("pearlescent_froglight"), Key.key("pumpkin"), Key.key("resin_block"), Key.key("resin_bricks"), Key.key("sponge"), Key.key("tube_coral_block"), Key.key("verdant_froglight"), Key.key("wet_sponge"))),
        Map.entry(Key.key("sulfur_cube_archetype/fast_sliding"), List.of(Key.key("blue_ice"), Key.key("packed_ice"), Key.key("snow_block"))),
        Map.entry(Key.key("sulfur_cube_archetype/high_resistance"), List.of(Key.key("soul_sand"), Key.key("soul_soil"))),
        Map.entry(Key.key("sulfur_cube_archetype/hot"), List.of(Key.key("magma_block"))),
        Map.entry(Key.key("sulfur_cube_archetype/light"), List.of(Key.key("black_wool"), Key.key("blue_wool"), Key.key("brown_wool"), Key.key("cyan_wool"), Key.key("gray_wool"), Key.key("green_wool"), Key.key("light_blue_wool"), Key.key("light_gray_wool"), Key.key("lime_wool"), Key.key("magenta_wool"), Key.key("orange_wool"), Key.key("pink_wool"), Key.key("purple_wool"), Key.key("red_wool"), Key.key("white_wool"), Key.key("yellow_wool"))),
        Map.entry(Key.key("sulfur_cube_archetype/regular"), List.of(Key.key("black_concrete_powder"), Key.key("blue_concrete_powder"), Key.key("bone_block"), Key.key("brown_concrete_powder"), Key.key("clay"), Key.key("coal_block"), Key.key("coarse_dirt"), Key.key("cyan_concrete_powder"), Key.key("dirt"), Key.key("grass_block"), Key.key("gray_concrete_powder"), Key.key("green_concrete_powder"), Key.key("light_blue_concrete_powder"), Key.key("light_gray_concrete_powder"), Key.key("lime_concrete_powder"), Key.key("magenta_concrete_powder"), Key.key("mud"), Key.key("muddy_mangrove_roots"), Key.key("orange_concrete_powder"), Key.key("packed_mud"), Key.key("pink_concrete_powder"), Key.key("podzol"), Key.key("purple_concrete_powder"), Key.key("red_concrete_powder"), Key.key("rooted_dirt"), Key.key("white_concrete_powder"), Key.key("yellow_concrete_powder"))),
        Map.entry(Key.key("sulfur_cube_archetype/slow_bouncy"), List.of(Key.key("amethyst_block"), Key.key("andesite"), Key.key("basalt"), Key.key("black_concrete"), Key.key("black_glazed_terracotta"), Key.key("black_terracotta"), Key.key("blackstone"), Key.key("blue_concrete"), Key.key("blue_glazed_terracotta"), Key.key("blue_terracotta"), Key.key("bricks"), Key.key("brown_concrete"), Key.key("brown_glazed_terracotta"), Key.key("brown_terracotta"), Key.key("calcite"), Key.key("chiseled_cinnabar"), Key.key("chiseled_deepslate"), Key.key("chiseled_nether_bricks"), Key.key("chiseled_polished_blackstone"), Key.key("chiseled_quartz_block"), Key.key("chiseled_red_sandstone"), Key.key("chiseled_sandstone"), Key.key("chiseled_stone_bricks"), Key.key("chiseled_sulfur"), Key.key("chiseled_tuff"), Key.key("chiseled_tuff_bricks"), Key.key("cinnabar"), Key.key("cinnabar_bricks"), Key.key("coal_ore"), Key.key("cobbled_deepslate"), Key.key("cobblestone"), Key.key("cracked_deepslate_bricks"), Key.key("cracked_deepslate_tiles"), Key.key("cracked_nether_bricks"), Key.key("cracked_polished_blackstone_bricks"), Key.key("cracked_stone_bricks"), Key.key("crimson_nylium"), Key.key("crying_obsidian"), Key.key("cut_red_sandstone"), Key.key("cut_sandstone"), Key.key("cyan_concrete"), Key.key("cyan_glazed_terracotta"), Key.key("cyan_terracotta"), Key.key("dark_prismarine"), Key.key("deepslate"), Key.key("deepslate_bricks"), Key.key("deepslate_coal_ore"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_lapis_ore"), Key.key("deepslate_redstone_ore"), Key.key("deepslate_tiles"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("diorite"), Key.key("dripstone_block"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("end_stone"), Key.key("end_stone_bricks"), Key.key("gilded_blackstone"), Key.key("glowstone"), Key.key("granite"), Key.key("gray_concrete"), Key.key("gray_glazed_terracotta"), Key.key("gray_terracotta"), Key.key("green_concrete"), Key.key("green_glazed_terracotta"), Key.key("green_terracotta"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("light_blue_concrete"), Key.key("light_blue_glazed_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_concrete"), Key.key("light_gray_glazed_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_concrete"), Key.key("lime_glazed_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_concrete"), Key.key("magenta_glazed_terracotta"), Key.key("magenta_terracotta"), Key.key("mossy_cobblestone"), Key.key("mossy_stone_bricks"), Key.key("mud_bricks"), Key.key("nether_bricks"), Key.key("nether_quartz_ore"), Key.key("netherrack"), Key.key("observer"), Key.key("obsidian"), Key.key("orange_concrete"), Key.key("orange_glazed_terracotta"), Key.key("orange_terracotta"), Key.key("pink_concrete"), Key.key("pink_glazed_terracotta"), Key.key("pink_terracotta"), Key.key("polished_andesite"), Key.key("polished_basalt"), Key.key("polished_blackstone"), Key.key("polished_blackstone_bricks"), Key.key("polished_cinnabar"), Key.key("polished_deepslate"), Key.key("polished_diorite"), Key.key("polished_granite"), Key.key("polished_sulfur"), Key.key("polished_tuff"), Key.key("prismarine"), Key.key("prismarine_bricks"), Key.key("purple_concrete"), Key.key("purple_glazed_terracotta"), Key.key("purple_terracotta"), Key.key("purpur_block"), Key.key("purpur_pillar"), Key.key("quartz_block"), Key.key("quartz_bricks"), Key.key("quartz_pillar"), Key.key("red_concrete"), Key.key("red_glazed_terracotta"), Key.key("red_nether_bricks"), Key.key("red_sandstone"), Key.key("red_terracotta"), Key.key("redstone_lamp"), Key.key("redstone_ore"), Key.key("sandstone"), Key.key("sea_lantern"), Key.key("smooth_basalt"), Key.key("smooth_quartz"), Key.key("smooth_red_sandstone"), Key.key("smooth_sandstone"), Key.key("smooth_stone"), Key.key("stone"), Key.key("stone_bricks"), Key.key("sulfur"), Key.key("sulfur_bricks"), Key.key("terracotta"), Key.key("tuff"), Key.key("tuff_bricks"), Key.key("warped_nylium"), Key.key("white_concrete"), Key.key("white_glazed_terracotta"), Key.key("white_terracotta"), Key.key("yellow_concrete"), Key.key("yellow_glazed_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("sulfur_cube_archetype/slow_flat"), List.of(Key.key("ancient_debris"), Key.key("chiseled_copper"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_ore"), Key.key("cut_copper"), Key.key("deepslate_copper_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_iron_ore"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bulb"), Key.key("exposed_cut_copper"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("iron_block"), Key.key("iron_ore"), Key.key("nether_gold_ore"), Key.key("netherite_block"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_cut_copper"), Key.key("raw_copper_block"), Key.key("raw_gold_block"), Key.key("raw_iron_block"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_cut_copper"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_cut_copper"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bulb"), Key.key("weathered_cut_copper"))),
        Map.entry(Key.key("sulfur_cube_archetype/slow_sliding"), List.of(Key.key("brown_mushroom_block"), Key.key("mushroom_stem"), Key.key("mycelium"), Key.key("nether_wart_block"), Key.key("red_mushroom_block"), Key.key("shroomlight"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("sulfur_cube_archetype/sticky"), List.of(Key.key("honeycomb_block"))),
        Map.entry(Key.key("sulfur_cube_food"), List.of(Key.key("slime_ball"))),
        Map.entry(Key.key("sulfur_cube_swallowable"), List.of(Key.key("acacia_log"), Key.key("acacia_planks"), Key.key("acacia_wood"), Key.key("amethyst_block"), Key.key("ancient_debris"), Key.key("andesite"), Key.key("bamboo_block"), Key.key("bamboo_mosaic"), Key.key("bamboo_planks"), Key.key("basalt"), Key.key("birch_log"), Key.key("birch_planks"), Key.key("birch_wood"), Key.key("black_concrete"), Key.key("black_concrete_powder"), Key.key("black_glazed_terracotta"), Key.key("black_terracotta"), Key.key("black_wool"), Key.key("blackstone"), Key.key("blue_concrete"), Key.key("blue_concrete_powder"), Key.key("blue_glazed_terracotta"), Key.key("blue_ice"), Key.key("blue_terracotta"), Key.key("blue_wool"), Key.key("bone_block"), Key.key("brain_coral_block"), Key.key("bricks"), Key.key("brown_concrete"), Key.key("brown_concrete_powder"), Key.key("brown_glazed_terracotta"), Key.key("brown_mushroom_block"), Key.key("brown_terracotta"), Key.key("brown_wool"), Key.key("bubble_coral_block"), Key.key("calcite"), Key.key("carved_pumpkin"), Key.key("cherry_log"), Key.key("cherry_planks"), Key.key("cherry_wood"), Key.key("chiseled_cinnabar"), Key.key("chiseled_copper"), Key.key("chiseled_deepslate"), Key.key("chiseled_nether_bricks"), Key.key("chiseled_polished_blackstone"), Key.key("chiseled_quartz_block"), Key.key("chiseled_red_sandstone"), Key.key("chiseled_resin_bricks"), Key.key("chiseled_sandstone"), Key.key("chiseled_stone_bricks"), Key.key("chiseled_sulfur"), Key.key("chiseled_tuff"), Key.key("chiseled_tuff_bricks"), Key.key("cinnabar"), Key.key("cinnabar_bricks"), Key.key("clay"), Key.key("coal_block"), Key.key("coal_ore"), Key.key("coarse_dirt"), Key.key("cobbled_deepslate"), Key.key("cobblestone"), Key.key("copper_block"), Key.key("copper_bulb"), Key.key("copper_ore"), Key.key("cracked_deepslate_bricks"), Key.key("cracked_deepslate_tiles"), Key.key("cracked_nether_bricks"), Key.key("cracked_polished_blackstone_bricks"), Key.key("cracked_stone_bricks"), Key.key("crimson_hyphae"), Key.key("crimson_nylium"), Key.key("crimson_planks"), Key.key("crimson_stem"), Key.key("crying_obsidian"), Key.key("cut_copper"), Key.key("cut_red_sandstone"), Key.key("cut_sandstone"), Key.key("cyan_concrete"), Key.key("cyan_concrete_powder"), Key.key("cyan_glazed_terracotta"), Key.key("cyan_terracotta"), Key.key("cyan_wool"), Key.key("dark_oak_log"), Key.key("dark_oak_planks"), Key.key("dark_oak_wood"), Key.key("dark_prismarine"), Key.key("dead_brain_coral_block"), Key.key("dead_bubble_coral_block"), Key.key("dead_fire_coral_block"), Key.key("dead_horn_coral_block"), Key.key("dead_tube_coral_block"), Key.key("deepslate"), Key.key("deepslate_bricks"), Key.key("deepslate_coal_ore"), Key.key("deepslate_copper_ore"), Key.key("deepslate_diamond_ore"), Key.key("deepslate_emerald_ore"), Key.key("deepslate_gold_ore"), Key.key("deepslate_iron_ore"), Key.key("deepslate_lapis_ore"), Key.key("deepslate_redstone_ore"), Key.key("deepslate_tiles"), Key.key("diamond_block"), Key.key("diamond_ore"), Key.key("diorite"), Key.key("dirt"), Key.key("dried_kelp_block"), Key.key("dripstone_block"), Key.key("emerald_block"), Key.key("emerald_ore"), Key.key("end_stone"), Key.key("end_stone_bricks"), Key.key("exposed_chiseled_copper"), Key.key("exposed_copper"), Key.key("exposed_copper_bulb"), Key.key("exposed_cut_copper"), Key.key("fire_coral_block"), Key.key("gilded_blackstone"), Key.key("glowstone"), Key.key("gold_block"), Key.key("gold_ore"), Key.key("granite"), Key.key("grass_block"), Key.key("gray_concrete"), Key.key("gray_concrete_powder"), Key.key("gray_glazed_terracotta"), Key.key("gray_terracotta"), Key.key("gray_wool"), Key.key("green_concrete"), Key.key("green_concrete_powder"), Key.key("green_glazed_terracotta"), Key.key("green_terracotta"), Key.key("green_wool"), Key.key("hay_block"), Key.key("honeycomb_block"), Key.key("horn_coral_block"), Key.key("iron_block"), Key.key("iron_ore"), Key.key("jack_o_lantern"), Key.key("jungle_log"), Key.key("jungle_planks"), Key.key("jungle_wood"), Key.key("lapis_block"), Key.key("lapis_ore"), Key.key("light_blue_concrete"), Key.key("light_blue_concrete_powder"), Key.key("light_blue_glazed_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_blue_wool"), Key.key("light_gray_concrete"), Key.key("light_gray_concrete_powder"), Key.key("light_gray_glazed_terracotta"), Key.key("light_gray_terracotta"), Key.key("light_gray_wool"), Key.key("lime_concrete"), Key.key("lime_concrete_powder"), Key.key("lime_glazed_terracotta"), Key.key("lime_terracotta"), Key.key("lime_wool"), Key.key("magenta_concrete"), Key.key("magenta_concrete_powder"), Key.key("magenta_glazed_terracotta"), Key.key("magenta_terracotta"), Key.key("magenta_wool"), Key.key("magma_block"), Key.key("mangrove_log"), Key.key("mangrove_planks"), Key.key("mangrove_wood"), Key.key("melon"), Key.key("moss_block"), Key.key("mossy_cobblestone"), Key.key("mossy_stone_bricks"), Key.key("mud"), Key.key("mud_bricks"), Key.key("muddy_mangrove_roots"), Key.key("mushroom_stem"), Key.key("mycelium"), Key.key("nether_bricks"), Key.key("nether_gold_ore"), Key.key("nether_quartz_ore"), Key.key("nether_wart_block"), Key.key("netherite_block"), Key.key("netherrack"), Key.key("oak_log"), Key.key("oak_planks"), Key.key("oak_wood"), Key.key("observer"), Key.key("obsidian"), Key.key("ochre_froglight"), Key.key("orange_concrete"), Key.key("orange_concrete_powder"), Key.key("orange_glazed_terracotta"), Key.key("orange_terracotta"), Key.key("orange_wool"), Key.key("oxidized_chiseled_copper"), Key.key("oxidized_copper"), Key.key("oxidized_copper_bulb"), Key.key("oxidized_cut_copper"), Key.key("packed_ice"), Key.key("packed_mud"), Key.key("pale_moss_block"), Key.key("pale_oak_log"), Key.key("pale_oak_planks"), Key.key("pale_oak_wood"), Key.key("pearlescent_froglight"), Key.key("pink_concrete"), Key.key("pink_concrete_powder"), Key.key("pink_glazed_terracotta"), Key.key("pink_terracotta"), Key.key("pink_wool"), Key.key("podzol"), Key.key("polished_andesite"), Key.key("polished_basalt"), Key.key("polished_blackstone"), Key.key("polished_blackstone_bricks"), Key.key("polished_cinnabar"), Key.key("polished_deepslate"), Key.key("polished_diorite"), Key.key("polished_granite"), Key.key("polished_sulfur"), Key.key("polished_tuff"), Key.key("prismarine"), Key.key("prismarine_bricks"), Key.key("pumpkin"), Key.key("purple_concrete"), Key.key("purple_concrete_powder"), Key.key("purple_glazed_terracotta"), Key.key("purple_terracotta"), Key.key("purple_wool"), Key.key("purpur_block"), Key.key("purpur_pillar"), Key.key("quartz_block"), Key.key("quartz_bricks"), Key.key("quartz_pillar"), Key.key("raw_copper_block"), Key.key("raw_gold_block"), Key.key("raw_iron_block"), Key.key("red_concrete"), Key.key("red_concrete_powder"), Key.key("red_glazed_terracotta"), Key.key("red_mushroom_block"), Key.key("red_nether_bricks"), Key.key("red_sandstone"), Key.key("red_terracotta"), Key.key("red_wool"), Key.key("redstone_lamp"), Key.key("redstone_ore"), Key.key("resin_block"), Key.key("resin_bricks"), Key.key("rooted_dirt"), Key.key("sandstone"), Key.key("sea_lantern"), Key.key("shroomlight"), Key.key("smooth_basalt"), Key.key("smooth_quartz"), Key.key("smooth_red_sandstone"), Key.key("smooth_sandstone"), Key.key("smooth_stone"), Key.key("snow_block"), Key.key("soul_sand"), Key.key("soul_soil"), Key.key("sponge"), Key.key("spruce_log"), Key.key("spruce_planks"), Key.key("spruce_wood"), Key.key("stone"), Key.key("stone_bricks"), Key.key("stripped_acacia_log"), Key.key("stripped_acacia_wood"), Key.key("stripped_bamboo_block"), Key.key("stripped_birch_log"), Key.key("stripped_birch_wood"), Key.key("stripped_cherry_log"), Key.key("stripped_cherry_wood"), Key.key("stripped_crimson_hyphae"), Key.key("stripped_crimson_stem"), Key.key("stripped_dark_oak_log"), Key.key("stripped_dark_oak_wood"), Key.key("stripped_jungle_log"), Key.key("stripped_jungle_wood"), Key.key("stripped_mangrove_log"), Key.key("stripped_mangrove_wood"), Key.key("stripped_oak_log"), Key.key("stripped_oak_wood"), Key.key("stripped_pale_oak_log"), Key.key("stripped_pale_oak_wood"), Key.key("stripped_spruce_log"), Key.key("stripped_spruce_wood"), Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("sulfur"), Key.key("sulfur_bricks"), Key.key("terracotta"), Key.key("tnt"), Key.key("tube_coral_block"), Key.key("tuff"), Key.key("tuff_bricks"), Key.key("verdant_froglight"), Key.key("warped_hyphae"), Key.key("warped_nylium"), Key.key("warped_planks"), Key.key("warped_stem"), Key.key("warped_wart_block"), Key.key("waxed_chiseled_copper"), Key.key("waxed_copper_block"), Key.key("waxed_copper_bulb"), Key.key("waxed_cut_copper"), Key.key("waxed_exposed_chiseled_copper"), Key.key("waxed_exposed_copper"), Key.key("waxed_exposed_copper_bulb"), Key.key("waxed_exposed_cut_copper"), Key.key("waxed_oxidized_chiseled_copper"), Key.key("waxed_oxidized_copper"), Key.key("waxed_oxidized_copper_bulb"), Key.key("waxed_oxidized_cut_copper"), Key.key("waxed_weathered_chiseled_copper"), Key.key("waxed_weathered_copper"), Key.key("waxed_weathered_copper_bulb"), Key.key("waxed_weathered_cut_copper"), Key.key("weathered_chiseled_copper"), Key.key("weathered_copper"), Key.key("weathered_copper_bulb"), Key.key("weathered_cut_copper"), Key.key("wet_sponge"), Key.key("white_concrete"), Key.key("white_concrete_powder"), Key.key("white_glazed_terracotta"), Key.key("white_terracotta"), Key.key("white_wool"), Key.key("yellow_concrete"), Key.key("yellow_concrete_powder"), Key.key("yellow_glazed_terracotta"), Key.key("yellow_terracotta"), Key.key("yellow_wool"))),
        Map.entry(Key.key("swords"), List.of(Key.key("copper_sword"), Key.key("diamond_sword"), Key.key("golden_sword"), Key.key("iron_sword"), Key.key("netherite_sword"), Key.key("stone_sword"), Key.key("wooden_sword"))),
        Map.entry(Key.key("terracotta"), List.of(Key.key("black_terracotta"), Key.key("blue_terracotta"), Key.key("brown_terracotta"), Key.key("cyan_terracotta"), Key.key("gray_terracotta"), Key.key("green_terracotta"), Key.key("light_blue_terracotta"), Key.key("light_gray_terracotta"), Key.key("lime_terracotta"), Key.key("magenta_terracotta"), Key.key("orange_terracotta"), Key.key("pink_terracotta"), Key.key("purple_terracotta"), Key.key("red_terracotta"), Key.key("terracotta"), Key.key("white_terracotta"), Key.key("yellow_terracotta"))),
        Map.entry(Key.key("trapdoors"), List.of(Key.key("acacia_trapdoor"), Key.key("bamboo_trapdoor"), Key.key("birch_trapdoor"), Key.key("cherry_trapdoor"), Key.key("copper_trapdoor"), Key.key("crimson_trapdoor"), Key.key("dark_oak_trapdoor"), Key.key("exposed_copper_trapdoor"), Key.key("iron_trapdoor"), Key.key("jungle_trapdoor"), Key.key("mangrove_trapdoor"), Key.key("oak_trapdoor"), Key.key("oxidized_copper_trapdoor"), Key.key("pale_oak_trapdoor"), Key.key("spruce_trapdoor"), Key.key("warped_trapdoor"), Key.key("waxed_copper_trapdoor"), Key.key("waxed_exposed_copper_trapdoor"), Key.key("waxed_oxidized_copper_trapdoor"), Key.key("waxed_weathered_copper_trapdoor"), Key.key("weathered_copper_trapdoor"))),
        Map.entry(Key.key("trim_materials"), List.of(Key.key("amethyst_shard"), Key.key("copper_ingot"), Key.key("diamond"), Key.key("emerald"), Key.key("gold_ingot"), Key.key("iron_ingot"), Key.key("lapis_lazuli"), Key.key("netherite_ingot"), Key.key("quartz"), Key.key("redstone"), Key.key("resin_brick"))),
        Map.entry(Key.key("trimmable_armor"), List.of(Key.key("chainmail_boots"), Key.key("chainmail_chestplate"), Key.key("chainmail_helmet"), Key.key("chainmail_leggings"), Key.key("copper_boots"), Key.key("copper_chestplate"), Key.key("copper_helmet"), Key.key("copper_leggings"), Key.key("diamond_boots"), Key.key("diamond_chestplate"), Key.key("diamond_helmet"), Key.key("diamond_leggings"), Key.key("golden_boots"), Key.key("golden_chestplate"), Key.key("golden_helmet"), Key.key("golden_leggings"), Key.key("iron_boots"), Key.key("iron_chestplate"), Key.key("iron_helmet"), Key.key("iron_leggings"), Key.key("leather_boots"), Key.key("leather_chestplate"), Key.key("leather_helmet"), Key.key("leather_leggings"), Key.key("netherite_boots"), Key.key("netherite_chestplate"), Key.key("netherite_helmet"), Key.key("netherite_leggings"), Key.key("turtle_helmet"))),
        Map.entry(Key.key("turtle_food"), List.of(Key.key("seagrass"))),
        Map.entry(Key.key("villager_picks_up"), List.of(Key.key("beetroot"), Key.key("beetroot_seeds"), Key.key("bread"), Key.key("carrot"), Key.key("pitcher_pod"), Key.key("potato"), Key.key("torchflower_seeds"), Key.key("wheat"), Key.key("wheat_seeds"))),
        Map.entry(Key.key("villager_plantable_seeds"), List.of(Key.key("beetroot_seeds"), Key.key("carrot"), Key.key("pitcher_pod"), Key.key("potato"), Key.key("torchflower_seeds"), Key.key("wheat_seeds"))),
        Map.entry(Key.key("walls"), List.of(Key.key("andesite_wall"), Key.key("blackstone_wall"), Key.key("brick_wall"), Key.key("cinnabar_brick_wall"), Key.key("cinnabar_wall"), Key.key("cobbled_deepslate_wall"), Key.key("cobblestone_wall"), Key.key("deepslate_brick_wall"), Key.key("deepslate_tile_wall"), Key.key("diorite_wall"), Key.key("end_stone_brick_wall"), Key.key("granite_wall"), Key.key("mossy_cobblestone_wall"), Key.key("mossy_stone_brick_wall"), Key.key("mud_brick_wall"), Key.key("nether_brick_wall"), Key.key("polished_blackstone_brick_wall"), Key.key("polished_blackstone_wall"), Key.key("polished_cinnabar_wall"), Key.key("polished_deepslate_wall"), Key.key("polished_sulfur_wall"), Key.key("polished_tuff_wall"), Key.key("prismarine_wall"), Key.key("red_nether_brick_wall"), Key.key("red_sandstone_wall"), Key.key("resin_brick_wall"), Key.key("sandstone_wall"), Key.key("stone_brick_wall"), Key.key("sulfur_brick_wall"), Key.key("sulfur_wall"), Key.key("tuff_brick_wall"), Key.key("tuff_wall"))),
        Map.entry(Key.key("warped_stems"), List.of(Key.key("stripped_warped_hyphae"), Key.key("stripped_warped_stem"), Key.key("warped_hyphae"), Key.key("warped_stem"))),
        Map.entry(Key.key("wart_blocks"), List.of(Key.key("nether_wart_block"), Key.key("warped_wart_block"))),
        Map.entry(Key.key("wither_skeleton_disliked_weapons"), List.of(Key.key("bow"), Key.key("crossbow"))),
        Map.entry(Key.key("wolf_collar_dyes"), List.of(Key.key("black_dye"), Key.key("blue_dye"), Key.key("brown_dye"), Key.key("cyan_dye"), Key.key("gray_dye"), Key.key("green_dye"), Key.key("light_blue_dye"), Key.key("light_gray_dye"), Key.key("lime_dye"), Key.key("magenta_dye"), Key.key("orange_dye"), Key.key("pink_dye"), Key.key("purple_dye"), Key.key("red_dye"), Key.key("white_dye"), Key.key("yellow_dye"))),
        Map.entry(Key.key("wolf_food"), List.of(Key.key("beef"), Key.key("chicken"), Key.key("cod"), Key.key("cooked_beef"), Key.key("cooked_chicken"), Key.key("cooked_cod"), Key.key("cooked_mutton"), Key.key("cooked_porkchop"), Key.key("cooked_rabbit"), Key.key("cooked_salmon"), Key.key("mutton"), Key.key("porkchop"), Key.key("pufferfish"), Key.key("rabbit"), Key.key("rabbit_stew"), Key.key("rotten_flesh"), Key.key("salmon"), Key.key("tropical_fish"))),
        Map.entry(Key.key("wooden_buttons"), List.of(Key.key("acacia_button"), Key.key("bamboo_button"), Key.key("birch_button"), Key.key("cherry_button"), Key.key("crimson_button"), Key.key("dark_oak_button"), Key.key("jungle_button"), Key.key("mangrove_button"), Key.key("oak_button"), Key.key("pale_oak_button"), Key.key("spruce_button"), Key.key("warped_button"))),
        Map.entry(Key.key("wooden_doors"), List.of(Key.key("acacia_door"), Key.key("bamboo_door"), Key.key("birch_door"), Key.key("cherry_door"), Key.key("crimson_door"), Key.key("dark_oak_door"), Key.key("jungle_door"), Key.key("mangrove_door"), Key.key("oak_door"), Key.key("pale_oak_door"), Key.key("spruce_door"), Key.key("warped_door"))),
        Map.entry(Key.key("wooden_fences"), List.of(Key.key("acacia_fence"), Key.key("bamboo_fence"), Key.key("birch_fence"), Key.key("cherry_fence"), Key.key("crimson_fence"), Key.key("dark_oak_fence"), Key.key("jungle_fence"), Key.key("mangrove_fence"), Key.key("oak_fence"), Key.key("pale_oak_fence"), Key.key("spruce_fence"), Key.key("warped_fence"))),
        Map.entry(Key.key("wooden_pressure_plates"), List.of(Key.key("acacia_pressure_plate"), Key.key("bamboo_pressure_plate"), Key.key("birch_pressure_plate"), Key.key("cherry_pressure_plate"), Key.key("crimson_pressure_plate"), Key.key("dark_oak_pressure_plate"), Key.key("jungle_pressure_plate"), Key.key("mangrove_pressure_plate"), Key.key("oak_pressure_plate"), Key.key("pale_oak_pressure_plate"), Key.key("spruce_pressure_plate"), Key.key("warped_pressure_plate"))),
        Map.entry(Key.key("wooden_shelves"), List.of(Key.key("acacia_shelf"), Key.key("bamboo_shelf"), Key.key("birch_shelf"), Key.key("cherry_shelf"), Key.key("crimson_shelf"), Key.key("dark_oak_shelf"), Key.key("jungle_shelf"), Key.key("mangrove_shelf"), Key.key("oak_shelf"), Key.key("pale_oak_shelf"), Key.key("spruce_shelf"), Key.key("warped_shelf"))),
        Map.entry(Key.key("wooden_slabs"), List.of(Key.key("acacia_slab"), Key.key("bamboo_slab"), Key.key("birch_slab"), Key.key("cherry_slab"), Key.key("crimson_slab"), Key.key("dark_oak_slab"), Key.key("jungle_slab"), Key.key("mangrove_slab"), Key.key("oak_slab"), Key.key("pale_oak_slab"), Key.key("spruce_slab"), Key.key("warped_slab"))),
        Map.entry(Key.key("wooden_stairs"), List.of(Key.key("acacia_stairs"), Key.key("bamboo_stairs"), Key.key("birch_stairs"), Key.key("cherry_stairs"), Key.key("crimson_stairs"), Key.key("dark_oak_stairs"), Key.key("jungle_stairs"), Key.key("mangrove_stairs"), Key.key("oak_stairs"), Key.key("pale_oak_stairs"), Key.key("spruce_stairs"), Key.key("warped_stairs"))),
        Map.entry(Key.key("wooden_tool_materials"), List.of(Key.key("acacia_planks"), Key.key("bamboo_planks"), Key.key("birch_planks"), Key.key("cherry_planks"), Key.key("crimson_planks"), Key.key("dark_oak_planks"), Key.key("jungle_planks"), Key.key("mangrove_planks"), Key.key("oak_planks"), Key.key("pale_oak_planks"), Key.key("spruce_planks"), Key.key("warped_planks"))),
        Map.entry(Key.key("wooden_trapdoors"), List.of(Key.key("acacia_trapdoor"), Key.key("bamboo_trapdoor"), Key.key("birch_trapdoor"), Key.key("cherry_trapdoor"), Key.key("crimson_trapdoor"), Key.key("dark_oak_trapdoor"), Key.key("jungle_trapdoor"), Key.key("mangrove_trapdoor"), Key.key("oak_trapdoor"), Key.key("pale_oak_trapdoor"), Key.key("spruce_trapdoor"), Key.key("warped_trapdoor"))),
        Map.entry(Key.key("wool"), List.of(Key.key("black_wool"), Key.key("blue_wool"), Key.key("brown_wool"), Key.key("cyan_wool"), Key.key("gray_wool"), Key.key("green_wool"), Key.key("light_blue_wool"), Key.key("light_gray_wool"), Key.key("lime_wool"), Key.key("magenta_wool"), Key.key("orange_wool"), Key.key("pink_wool"), Key.key("purple_wool"), Key.key("red_wool"), Key.key("white_wool"), Key.key("yellow_wool"))),
        Map.entry(Key.key("wool_carpets"), List.of(Key.key("black_carpet"), Key.key("blue_carpet"), Key.key("brown_carpet"), Key.key("cyan_carpet"), Key.key("gray_carpet"), Key.key("green_carpet"), Key.key("light_blue_carpet"), Key.key("light_gray_carpet"), Key.key("lime_carpet"), Key.key("magenta_carpet"), Key.key("orange_carpet"), Key.key("pink_carpet"), Key.key("purple_carpet"), Key.key("red_carpet"), Key.key("white_carpet"), Key.key("yellow_carpet"))),
        Map.entry(Key.key("zombie_horse_food"), List.of(Key.key("red_mushroom")))
    );

    private ItemKeys() {
        throw new UnsupportedOperationException("ItemKeys cannot be instantiated.");
    }

    private static TypedKey<Item> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.ITEM, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<Item>> values() {
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
