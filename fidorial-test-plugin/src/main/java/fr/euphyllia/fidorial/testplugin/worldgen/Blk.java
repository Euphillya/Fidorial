package fr.euphyllia.fidorial.testplugin.worldgen;

import fr.fidorial.registry.keys.BlockTypeKeys;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class Blk {

    private static final List<Key> KEYS = new ArrayList<>();
    private static final List<Map<String, String>> PROPERTIES = new ArrayList<>();

    public static final short AIR = register(BlockTypeKeys.AIR.key());
    public static final short BEDROCK = register(BlockTypeKeys.BEDROCK.key());
    public static final short STONE = register(BlockTypeKeys.STONE.key());
    public static final short DEEPSLATE = register(BlockTypeKeys.DEEPSLATE.key());
    public static final short GRANITE = register(BlockTypeKeys.GRANITE.key());
    public static final short DIORITE = register(BlockTypeKeys.DIORITE.key());
    public static final short ANDESITE = register(BlockTypeKeys.ANDESITE.key());
    public static final short TUFF = register(BlockTypeKeys.TUFF.key());
    public static final short CALCITE = register(BlockTypeKeys.CALCITE.key());
    public static final short DRIPSTONE_BLOCK = register(BlockTypeKeys.DRIPSTONE_BLOCK.key());
    public static final short POINTED_DRIPSTONE = register(BlockTypeKeys.POINTED_DRIPSTONE.key());
    public static final short OBSIDIAN = register(BlockTypeKeys.OBSIDIAN.key());
    public static final short MAGMA_BLOCK = register(BlockTypeKeys.MAGMA_BLOCK.key());

    public static final short DIRT = register(BlockTypeKeys.DIRT.key());
    public static final short COARSE_DIRT = register(BlockTypeKeys.COARSE_DIRT.key());
    public static final short ROOTED_DIRT = register(BlockTypeKeys.ROOTED_DIRT.key());
    public static final short GRASS_BLOCK = register(BlockTypeKeys.GRASS_BLOCK.key());
    public static final short PODZOL = register(BlockTypeKeys.PODZOL.key());
    public static final short MYCELIUM = register(BlockTypeKeys.MYCELIUM.key());
    public static final short MUD = register(BlockTypeKeys.MUD.key());
    public static final short PACKED_MUD = register(BlockTypeKeys.PACKED_MUD.key());
    public static final short CLAY = register(BlockTypeKeys.CLAY.key());
    public static final short SAND = register(BlockTypeKeys.SAND.key());
    public static final short RED_SAND = register(BlockTypeKeys.RED_SAND.key());
    public static final short SANDSTONE = register(BlockTypeKeys.SANDSTONE.key());
    public static final short RED_SANDSTONE = register(BlockTypeKeys.RED_SANDSTONE.key());
    public static final short GRAVEL = register(BlockTypeKeys.GRAVEL.key());
    public static final short MOSS_BLOCK = register(BlockTypeKeys.MOSS_BLOCK.key());
    public static final short MOSS_CARPET = register(BlockTypeKeys.MOSS_CARPET.key());

    public static final short WATER = register(BlockTypeKeys.WATER.key());
    public static final short LAVA = register(BlockTypeKeys.LAVA.key());
    public static final short ICE = register(BlockTypeKeys.ICE.key());
    public static final short PACKED_ICE = register(BlockTypeKeys.PACKED_ICE.key());
    public static final short BLUE_ICE = register(BlockTypeKeys.BLUE_ICE.key());
    public static final short SNOW_BLOCK = register(BlockTypeKeys.SNOW_BLOCK.key());
    public static final short SNOW = register(BlockTypeKeys.SNOW.key());
    public static final short POWDER_SNOW = register(BlockTypeKeys.POWDER_SNOW.key());

    public static final short TERRACOTTA = register(BlockTypeKeys.TERRACOTTA.key());
    public static final short WHITE_TERRACOTTA = register(BlockTypeKeys.WHITE_TERRACOTTA.key());
    public static final short ORANGE_TERRACOTTA = register(BlockTypeKeys.ORANGE_TERRACOTTA.key());
    public static final short YELLOW_TERRACOTTA = register(BlockTypeKeys.YELLOW_TERRACOTTA.key());
    public static final short BROWN_TERRACOTTA = register(BlockTypeKeys.BROWN_TERRACOTTA.key());
    public static final short RED_TERRACOTTA = register(BlockTypeKeys.RED_TERRACOTTA.key());
    public static final short LIGHT_GRAY_TERRACOTTA = register(BlockTypeKeys.LIGHT_GRAY_TERRACOTTA.key());

    public static final short COAL_ORE = register(BlockTypeKeys.COAL_ORE.key());
    public static final short DEEPSLATE_COAL_ORE = register(BlockTypeKeys.DEEPSLATE_COAL_ORE.key());
    public static final short IRON_ORE = register(BlockTypeKeys.IRON_ORE.key());
    public static final short DEEPSLATE_IRON_ORE = register(BlockTypeKeys.DEEPSLATE_IRON_ORE.key());
    public static final short COPPER_ORE = register(BlockTypeKeys.COPPER_ORE.key());
    public static final short DEEPSLATE_COPPER_ORE = register(BlockTypeKeys.DEEPSLATE_COPPER_ORE.key());
    public static final short GOLD_ORE = register(BlockTypeKeys.GOLD_ORE.key());
    public static final short DEEPSLATE_GOLD_ORE = register(BlockTypeKeys.DEEPSLATE_GOLD_ORE.key());
    public static final short REDSTONE_ORE = register(BlockTypeKeys.REDSTONE_ORE.key());
    public static final short DEEPSLATE_REDSTONE_ORE = register(BlockTypeKeys.DEEPSLATE_REDSTONE_ORE.key());
    public static final short LAPIS_ORE = register(BlockTypeKeys.LAPIS_ORE.key());
    public static final short DEEPSLATE_LAPIS_ORE = register(BlockTypeKeys.DEEPSLATE_LAPIS_ORE.key());
    public static final short DIAMOND_ORE = register(BlockTypeKeys.DIAMOND_ORE.key());
    public static final short DEEPSLATE_DIAMOND_ORE = register(BlockTypeKeys.DEEPSLATE_DIAMOND_ORE.key());
    public static final short EMERALD_ORE = register(BlockTypeKeys.EMERALD_ORE.key());
    public static final short DEEPSLATE_EMERALD_ORE = register(BlockTypeKeys.DEEPSLATE_EMERALD_ORE.key());

    public static final short OAK_LOG = register(BlockTypeKeys.OAK_LOG.key());
    public static final short OAK_LEAVES = register(BlockTypeKeys.OAK_LEAVES.key());
    public static final short BIRCH_LOG = register(BlockTypeKeys.BIRCH_LOG.key());
    public static final short BIRCH_LEAVES = register(BlockTypeKeys.BIRCH_LEAVES.key());
    public static final short SPRUCE_LOG = register(BlockTypeKeys.SPRUCE_LOG.key());
    public static final short SPRUCE_LEAVES = register(BlockTypeKeys.SPRUCE_LEAVES.key());
    public static final short JUNGLE_LOG = register(BlockTypeKeys.JUNGLE_LOG.key());
    public static final short JUNGLE_LEAVES = register(BlockTypeKeys.JUNGLE_LEAVES.key());
    public static final short ACACIA_LOG = register(BlockTypeKeys.ACACIA_LOG.key());
    public static final short ACACIA_LEAVES = register(BlockTypeKeys.ACACIA_LEAVES.key());
    public static final short DARK_OAK_LOG = register(BlockTypeKeys.DARK_OAK_LOG.key());
    public static final short DARK_OAK_LEAVES = register(BlockTypeKeys.DARK_OAK_LEAVES.key());
    public static final short CHERRY_LOG = register(BlockTypeKeys.CHERRY_LOG.key());
    public static final short CHERRY_LEAVES = register(BlockTypeKeys.CHERRY_LEAVES.key());
    public static final short MANGROVE_LOG = register(BlockTypeKeys.MANGROVE_LOG.key());
    public static final short MANGROVE_LEAVES = register(BlockTypeKeys.MANGROVE_LEAVES.key());
    public static final short PALE_OAK_LOG = register(BlockTypeKeys.PALE_OAK_LOG.key());
    public static final short PALE_OAK_LEAVES = register(BlockTypeKeys.PALE_OAK_LEAVES.key());

    public static final short SHORT_GRASS = register(BlockTypeKeys.SHORT_GRASS.key());
    public static final short TALL_GRASS = register(BlockTypeKeys.TALL_GRASS.key());
    public static final short FERN = register(BlockTypeKeys.FERN.key());
    public static final short LARGE_FERN = register(BlockTypeKeys.LARGE_FERN.key());
    public static final short DEAD_BUSH = register(BlockTypeKeys.DEAD_BUSH.key());
    public static final short DANDELION = register(BlockTypeKeys.DANDELION.key());
    public static final short POPPY = register(BlockTypeKeys.POPPY.key());
    public static final short BLUE_ORCHID = register(BlockTypeKeys.BLUE_ORCHID.key());
    public static final short ALLIUM = register(BlockTypeKeys.ALLIUM.key());
    public static final short AZURE_BLUET = register(BlockTypeKeys.AZURE_BLUET.key());
    public static final short OXEYE_DAISY = register(BlockTypeKeys.OXEYE_DAISY.key());
    public static final short CORNFLOWER = register(BlockTypeKeys.CORNFLOWER.key());
    public static final short LILY_OF_THE_VALLEY = register(BlockTypeKeys.LILY_OF_THE_VALLEY.key());
    public static final short RED_TULIP = register(BlockTypeKeys.RED_TULIP.key());
    public static final short ORANGE_TULIP = register(BlockTypeKeys.ORANGE_TULIP.key());
    public static final short WHITE_TULIP = register(BlockTypeKeys.WHITE_TULIP.key());
    public static final short PINK_TULIP = register(BlockTypeKeys.PINK_TULIP.key());
    public static final short SUNFLOWER = register(BlockTypeKeys.SUNFLOWER.key());
    public static final short BROWN_MUSHROOM = register(BlockTypeKeys.BROWN_MUSHROOM.key());
    public static final short RED_MUSHROOM = register(BlockTypeKeys.RED_MUSHROOM.key());
    public static final short MUSHROOM_STEM = register(BlockTypeKeys.MUSHROOM_STEM.key());
    public static final short RED_MUSHROOM_BLOCK = register(BlockTypeKeys.RED_MUSHROOM_BLOCK.key());
    public static final short BROWN_MUSHROOM_BLOCK = register(BlockTypeKeys.BROWN_MUSHROOM_BLOCK.key());
    public static final short CACTUS = register(BlockTypeKeys.CACTUS.key());
    public static final short SUGAR_CANE = register(BlockTypeKeys.SUGAR_CANE.key());
    public static final short PUMPKIN = register(BlockTypeKeys.PUMPKIN.key());
    public static final short MELON = register(BlockTypeKeys.MELON.key());
    public static final short LILY_PAD = register(BlockTypeKeys.LILY_PAD.key());
    public static final short VINE = register(BlockTypeKeys.VINE.key());
    public static final short BAMBOO = register(BlockTypeKeys.BAMBOO.key());
    public static final short SWEET_BERRY_BUSH = register(BlockTypeKeys.SWEET_BERRY_BUSH.key());
    public static final short GLOW_LICHEN = register(BlockTypeKeys.GLOW_LICHEN.key());
    public static final short SEAGRASS = register(BlockTypeKeys.SEAGRASS.key());
    public static final short KELP = register(BlockTypeKeys.KELP.key());
    public static final short KELP_PLANT = register(BlockTypeKeys.KELP_PLANT.key());
    public static final short SEA_PICKLE = register(BlockTypeKeys.SEA_PICKLE.key());
    public static final short TALL_SEAGRASS = register(BlockTypeKeys.TALL_SEAGRASS.key());
    public static final short TUBE_CORAL_BLOCK = register(BlockTypeKeys.TUBE_CORAL_BLOCK.key());
    public static final short BRAIN_CORAL_BLOCK = register(BlockTypeKeys.BRAIN_CORAL_BLOCK.key());
    public static final short HORN_CORAL_BLOCK = register(BlockTypeKeys.HORN_CORAL_BLOCK.key());
    public static final short FIRE_CORAL_BLOCK = register(BlockTypeKeys.FIRE_CORAL_BLOCK.key());
    public static final short BUBBLE_CORAL_BLOCK = register(BlockTypeKeys.BUBBLE_CORAL_BLOCK.key());

    public static final short AZALEA = register(BlockTypeKeys.AZALEA.key());
    public static final short FLOWERING_AZALEA = register(BlockTypeKeys.FLOWERING_AZALEA.key());
    public static final short HANGING_ROOTS = register(BlockTypeKeys.HANGING_ROOTS.key());
    public static final short CAVE_VINES = register(BlockTypeKeys.CAVE_VINES.key());
    public static final short SMALL_DRIPLEAF = register(BlockTypeKeys.SMALL_DRIPLEAF.key());
    public static final short SPORE_BLOSSOM = register(BlockTypeKeys.SPORE_BLOSSOM.key());
    public static final short SCULK = register(BlockTypeKeys.SCULK.key());
    public static final short SCULK_VEIN = register(BlockTypeKeys.SCULK_VEIN.key());
    public static final short AMETHYST_BLOCK = register(BlockTypeKeys.AMETHYST_BLOCK.key());
    public static final short BUDDING_AMETHYST = register(BlockTypeKeys.BUDDING_AMETHYST.key());
    public static final short COBWEB = register(BlockTypeKeys.COBWEB.key());

    public static final short TALL_GRASS_UPPER = variant(BlockTypeKeys.TALL_GRASS.key(), "half", "upper");
    public static final short LARGE_FERN_UPPER = variant(BlockTypeKeys.LARGE_FERN.key(), "half", "upper");
    public static final short SUNFLOWER_UPPER = variant(BlockTypeKeys.SUNFLOWER.key(), "half", "upper");
    public static final short LILAC = register(BlockTypeKeys.LILAC.key());
    public static final short LILAC_UPPER = variant(BlockTypeKeys.LILAC.key(), "half", "upper");
    public static final short ROSE_BUSH = register(BlockTypeKeys.ROSE_BUSH.key());
    public static final short ROSE_BUSH_UPPER = variant(BlockTypeKeys.ROSE_BUSH.key(), "half", "upper");
    public static final short PEONY = register(BlockTypeKeys.PEONY.key());
    public static final short PEONY_UPPER = variant(BlockTypeKeys.PEONY.key(), "half", "upper");
    public static final short TALL_SEAGRASS_UPPER = variant(BlockTypeKeys.TALL_SEAGRASS.key(), "half", "upper");

    public static final short SNOW_2 = variant(BlockTypeKeys.SNOW.key(), "layers", "2");
    public static final short SNOW_3 = variant(BlockTypeKeys.SNOW.key(), "layers", "3");

    public static final short DRIPSTONE_DOWN = variant(
            BlockTypeKeys.POINTED_DRIPSTONE.key(), Map.of("vertical_direction", "down", "thickness", "tip"));
    public static final short DRIPSTONE_DOWN_MIDDLE = variant(
            BlockTypeKeys.POINTED_DRIPSTONE.key(), Map.of("vertical_direction", "down", "thickness", "frustum"));
    public static final short DRIPSTONE_UP = variant(
            BlockTypeKeys.POINTED_DRIPSTONE.key(), Map.of("vertical_direction", "up", "thickness", "tip"));
    public static final short DRIPSTONE_UP_MIDDLE = variant(
            BlockTypeKeys.POINTED_DRIPSTONE.key(), Map.of("vertical_direction", "up", "thickness", "frustum"));

    public static final short CAVE_VINES_PLANT = register(BlockTypeKeys.CAVE_VINES_PLANT.key());
    public static final short CAVE_VINES_BERRIES = variant(BlockTypeKeys.CAVE_VINES.key(), "berries", "true");

    public static final short BAMBOO_SMALL_LEAVES = variant(BlockTypeKeys.BAMBOO.key(), "leaves", "small");
    public static final short BAMBOO_LARGE_LEAVES = variant(BlockTypeKeys.BAMBOO.key(), "leaves", "large");

    public static final short VINE_NORTH = variant(BlockTypeKeys.VINE.key(), "north", "true");
    public static final short VINE_SOUTH = variant(BlockTypeKeys.VINE.key(), "south", "true");
    public static final short VINE_EAST = variant(BlockTypeKeys.VINE.key(), "east", "true");
    public static final short VINE_WEST = variant(BlockTypeKeys.VINE.key(), "west", "true");
    public static final short VINE_UP = variant(BlockTypeKeys.VINE.key(), "up", "true");
    public static final short GLOW_LICHEN_DOWN = variant(BlockTypeKeys.GLOW_LICHEN.key(), "down", "true");

    private static final Key[] TABLE = KEYS.toArray(new Key[0]);
    private static final Map<String, String>[] PROPERTY_TABLE = toArray(PROPERTIES);

    private Blk() {
        throw new UnsupportedOperationException("Blk cannot be instantiated.");
    }

    private static short register(final Key key) {
        return variant(key, Map.of());
    }

    private static short variant(final Key key, final String property, final String value) {
        return variant(key, Map.of(property, value));
    }

    private static short variant(final Key key, final Map<String, String> properties) {
        KEYS.add(key);
        PROPERTIES.add(properties);
        return (short) (KEYS.size() - 1);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String>[] toArray(final List<Map<String, String>> values) {
        return values.toArray(new Map[0]);
    }

    public static Key key(final short id) {
        return TABLE[id];
    }

    public static Map<String, String> properties(final short id) {
        return PROPERTY_TABLE[id];
    }

    public static int size() {
        return TABLE.length;
    }

    public static boolean isOreReplaceable(final short id) {
        return id == STONE
                || id == DEEPSLATE
                || id == GRANITE
                || id == DIORITE
                || id == ANDESITE
                || id == TUFF;
    }

    public static boolean isReplaceableByFeature(final short id) {
        return id == AIR
                || id == SHORT_GRASS
                || id == TALL_GRASS
                || id == FERN
                || id == LARGE_FERN
                || id == DEAD_BUSH
                || id == SNOW
                || id == VINE
                || id == GLOW_LICHEN;
    }

    public static boolean isSoil(final short id) {
        return id == GRASS_BLOCK
                || id == DIRT
                || id == COARSE_DIRT
                || id == ROOTED_DIRT
                || id == PODZOL
                || id == MYCELIUM
                || id == MOSS_BLOCK
                || id == MUD;
    }

    public static boolean isSolid(final short id) {
        return id != AIR && id != WATER && id != LAVA;
    }
}
