package fr.euphyllia.fidorial.server.entity;

import fr.fidorial.entity.EntityType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class EntityTypes {

    private static final Map<Key, EntityType> BY_KEY = new ConcurrentHashMap<>();
    private static final Map<Key, Integer> NETWORK_IDS = new ConcurrentHashMap<>();

    public static final EntityType ACACIA_BOAT = vanilla("acacia_boat", EntityType.Category.MISC, 0);
    public static final EntityType ACACIA_CHEST_BOAT = vanilla("acacia_chest_boat", EntityType.Category.MISC, 1);
    public static final EntityType ALLAY = vanilla("allay", EntityType.Category.CREATURE, 2);
    public static final EntityType AREA_EFFECT_CLOUD = vanilla("area_effect_cloud", EntityType.Category.MISC, 3);
    public static final EntityType ARMADILLO = vanilla("armadillo", EntityType.Category.CREATURE, 4);
    public static final EntityType ARMOR_STAND = vanilla("armor_stand", EntityType.Category.MISC, 5);
    public static final EntityType ARROW = vanilla("arrow", EntityType.Category.MISC, 6);
    public static final EntityType AXOLOTL = vanilla("axolotl", EntityType.Category.WATER_CREATURE, 7);
    public static final EntityType BAMBOO_CHEST_RAFT = vanilla("bamboo_chest_raft", EntityType.Category.MISC, 8);
    public static final EntityType BAMBOO_RAFT = vanilla("bamboo_raft", EntityType.Category.MISC, 9);
    public static final EntityType BAT = vanilla("bat", EntityType.Category.AMBIENT, 10);
    public static final EntityType BEE = vanilla("bee", EntityType.Category.CREATURE, 11);
    public static final EntityType BIRCH_BOAT = vanilla("birch_boat", EntityType.Category.MISC, 12);
    public static final EntityType BIRCH_CHEST_BOAT = vanilla("birch_chest_boat", EntityType.Category.MISC, 13);
    public static final EntityType BLAZE = vanilla("blaze", EntityType.Category.MONSTER, 14);
    public static final EntityType BLOCK_DISPLAY = vanilla("block_display", EntityType.Category.MISC, 15);
    public static final EntityType BOGGED = vanilla("bogged", EntityType.Category.MONSTER, 16);
    public static final EntityType BREEZE = vanilla("breeze", EntityType.Category.MONSTER, 17);
    public static final EntityType BREEZE_WIND_CHARGE = vanilla("breeze_wind_charge", EntityType.Category.MISC, 18);
    public static final EntityType CAMEL = vanilla("camel", EntityType.Category.CREATURE, 19);
    public static final EntityType CAMEL_HUSK = vanilla("camel_husk", EntityType.Category.MONSTER, 20);
    public static final EntityType CAT = vanilla("cat", EntityType.Category.CREATURE, 21);
    public static final EntityType CAVE_SPIDER = vanilla("cave_spider", EntityType.Category.MONSTER, 22);
    public static final EntityType CHERRY_BOAT = vanilla("cherry_boat", EntityType.Category.MISC, 23);
    public static final EntityType CHERRY_CHEST_BOAT = vanilla("cherry_chest_boat", EntityType.Category.MISC, 24);
    public static final EntityType CHEST_MINECART = vanilla("chest_minecart", EntityType.Category.MISC, 25);
    public static final EntityType CHICKEN = vanilla("chicken", EntityType.Category.CREATURE, 26);
    public static final EntityType COD = vanilla("cod", EntityType.Category.WATER_CREATURE, 27);
    public static final EntityType COPPER_GOLEM = vanilla("copper_golem", EntityType.Category.MISC, 28);
    public static final EntityType COMMAND_BLOCK_MINECART = vanilla("command_block_minecart", EntityType.Category.MISC, 29);
    public static final EntityType COW = vanilla("cow", EntityType.Category.CREATURE, 30);
    public static final EntityType CREAKING = vanilla("creaking", EntityType.Category.MONSTER, 31);
    public static final EntityType CREEPER = vanilla("creeper", EntityType.Category.MONSTER, 32);
    public static final EntityType DARK_OAK_BOAT = vanilla("dark_oak_boat", EntityType.Category.MISC, 33);
    public static final EntityType DARK_OAK_CHEST_BOAT = vanilla("dark_oak_chest_boat", EntityType.Category.MISC, 34);
    public static final EntityType DOLPHIN = vanilla("dolphin", EntityType.Category.WATER_CREATURE, 35);
    public static final EntityType DONKEY = vanilla("donkey", EntityType.Category.CREATURE, 36);
    public static final EntityType DRAGON_FIREBALL = vanilla("dragon_fireball", EntityType.Category.MISC, 37);
    public static final EntityType DROWNED = vanilla("drowned", EntityType.Category.MONSTER, 38);
    public static final EntityType EGG = vanilla("egg", EntityType.Category.MISC, 39);
    public static final EntityType ELDER_GUARDIAN = vanilla("elder_guardian", EntityType.Category.MONSTER, 40);
    public static final EntityType ENDERMAN = vanilla("enderman", EntityType.Category.MONSTER, 41);
    public static final EntityType ENDERMITE = vanilla("endermite", EntityType.Category.MONSTER, 42);
    public static final EntityType ENDER_DRAGON = vanilla("ender_dragon", EntityType.Category.MONSTER, 43);
    public static final EntityType ENDER_PEARL = vanilla("ender_pearl", EntityType.Category.MISC, 44);
    public static final EntityType END_CRYSTAL = vanilla("end_crystal", EntityType.Category.MISC, 45);
    public static final EntityType EVOKER = vanilla("evoker", EntityType.Category.MONSTER, 46);
    public static final EntityType EVOKER_FANGS = vanilla("evoker_fangs", EntityType.Category.MISC, 47);
    public static final EntityType EXPERIENCE_BOTTLE = vanilla("experience_bottle", EntityType.Category.MISC, 48);
    public static final EntityType EXPERIENCE_ORB = vanilla("experience_orb", EntityType.Category.MISC, 49);
    public static final EntityType EYE_OF_ENDER = vanilla("eye_of_ender", EntityType.Category.MISC, 50);
    public static final EntityType FALLING_BLOCK = vanilla("falling_block", EntityType.Category.MISC, 51);
    public static final EntityType FIREBALL = vanilla("fireball", EntityType.Category.MISC, 52);
    public static final EntityType FIREWORK_ROCKET = vanilla("firework_rocket", EntityType.Category.MISC, 53);
    public static final EntityType FOX = vanilla("fox", EntityType.Category.CREATURE, 54);
    public static final EntityType FROG = vanilla("frog", EntityType.Category.CREATURE, 55);
    public static final EntityType FURNACE_MINECART = vanilla("furnace_minecart", EntityType.Category.MISC, 56);
    public static final EntityType GHAST = vanilla("ghast", EntityType.Category.MONSTER, 57);
    public static final EntityType HAPPY_GHAST = vanilla("happy_ghast", EntityType.Category.CREATURE, 58);
    public static final EntityType GIANT = vanilla("giant", EntityType.Category.MONSTER, 59);
    public static final EntityType GLOW_ITEM_FRAME = vanilla("glow_item_frame", EntityType.Category.MISC, 60);
    public static final EntityType GLOW_SQUID = vanilla("glow_squid", EntityType.Category.WATER_CREATURE, 61);
    public static final EntityType GOAT = vanilla("goat", EntityType.Category.CREATURE, 62);
    public static final EntityType GUARDIAN = vanilla("guardian", EntityType.Category.MONSTER, 63);
    public static final EntityType HOGLIN = vanilla("hoglin", EntityType.Category.MONSTER, 64);
    public static final EntityType HOPPER_MINECART = vanilla("hopper_minecart", EntityType.Category.MISC, 65);
    public static final EntityType HORSE = vanilla("horse", EntityType.Category.CREATURE, 66);
    public static final EntityType HUSK = vanilla("husk", EntityType.Category.MONSTER, 67);
    public static final EntityType ILLUSIONER = vanilla("illusioner", EntityType.Category.MONSTER, 68);
    public static final EntityType INTERACTION = vanilla("interaction", EntityType.Category.MISC, 69);
    public static final EntityType IRON_GOLEM = vanilla("iron_golem", EntityType.Category.MISC, 70);
    public static final EntityType ITEM = vanilla("item", EntityType.Category.MISC, 71);
    public static final EntityType ITEM_DISPLAY = vanilla("item_display", EntityType.Category.MISC, 72);
    public static final EntityType ITEM_FRAME = vanilla("item_frame", EntityType.Category.MISC, 73);
    public static final EntityType JUNGLE_BOAT = vanilla("jungle_boat", EntityType.Category.MISC, 74);
    public static final EntityType JUNGLE_CHEST_BOAT = vanilla("jungle_chest_boat", EntityType.Category.MISC, 75);
    public static final EntityType LEASH_KNOT = vanilla("leash_knot", EntityType.Category.MISC, 76);
    public static final EntityType LIGHTNING_BOLT = vanilla("lightning_bolt", EntityType.Category.MISC, 77);
    public static final EntityType LLAMA = vanilla("llama", EntityType.Category.CREATURE, 78);
    public static final EntityType LLAMA_SPIT = vanilla("llama_spit", EntityType.Category.MISC, 79);
    public static final EntityType MAGMA_CUBE = vanilla("magma_cube", EntityType.Category.MONSTER, 80);
    public static final EntityType MANGROVE_BOAT = vanilla("mangrove_boat", EntityType.Category.MISC, 81);
    public static final EntityType MANGROVE_CHEST_BOAT = vanilla("mangrove_chest_boat", EntityType.Category.MISC, 82);
    public static final EntityType MANNEQUIN = vanilla("mannequin", EntityType.Category.MISC, 83);
    public static final EntityType MARKER = vanilla("marker", EntityType.Category.MISC, 84);
    public static final EntityType MINECART = vanilla("minecart", EntityType.Category.MISC, 85);
    public static final EntityType MOOSHROOM = vanilla("mooshroom", EntityType.Category.CREATURE, 86);
    public static final EntityType MULE = vanilla("mule", EntityType.Category.CREATURE, 87);
    public static final EntityType NAUTILUS = vanilla("nautilus", EntityType.Category.WATER_CREATURE, 88);
    public static final EntityType OAK_BOAT = vanilla("oak_boat", EntityType.Category.MISC, 89);
    public static final EntityType OAK_CHEST_BOAT = vanilla("oak_chest_boat", EntityType.Category.MISC, 90);
    public static final EntityType OCELOT = vanilla("ocelot", EntityType.Category.CREATURE, 91);
    public static final EntityType OMINOUS_ITEM_SPAWNER = vanilla("ominous_item_spawner", EntityType.Category.MISC, 92);
    public static final EntityType PAINTING = vanilla("painting", EntityType.Category.MISC, 93);
    public static final EntityType PALE_OAK_BOAT = vanilla("pale_oak_boat", EntityType.Category.MISC, 94);
    public static final EntityType PALE_OAK_CHEST_BOAT = vanilla("pale_oak_chest_boat", EntityType.Category.MISC, 95);
    public static final EntityType PANDA = vanilla("panda", EntityType.Category.CREATURE, 96);
    public static final EntityType PARCHED = vanilla("parched", EntityType.Category.MONSTER, 97);
    public static final EntityType PARROT = vanilla("parrot", EntityType.Category.CREATURE, 98);
    public static final EntityType PHANTOM = vanilla("phantom", EntityType.Category.MONSTER, 99);
    public static final EntityType PIG = vanilla("pig", EntityType.Category.CREATURE, 100);
    public static final EntityType PIGLIN = vanilla("piglin", EntityType.Category.MONSTER, 101);
    public static final EntityType PIGLIN_BRUTE = vanilla("piglin_brute", EntityType.Category.MONSTER, 102);
    public static final EntityType PILLAGER = vanilla("pillager", EntityType.Category.MONSTER, 103);
    public static final EntityType POLAR_BEAR = vanilla("polar_bear", EntityType.Category.CREATURE, 104);
    public static final EntityType SPLASH_POTION = vanilla("splash_potion", EntityType.Category.MISC, 105);
    public static final EntityType LINGERING_POTION = vanilla("lingering_potion", EntityType.Category.MISC, 106);
    public static final EntityType PUFFERFISH = vanilla("pufferfish", EntityType.Category.WATER_CREATURE, 107);
    public static final EntityType RABBIT = vanilla("rabbit", EntityType.Category.CREATURE, 108);
    public static final EntityType RAVAGER = vanilla("ravager", EntityType.Category.MONSTER, 109);
    public static final EntityType SALMON = vanilla("salmon", EntityType.Category.WATER_CREATURE, 110);
    public static final EntityType SHEEP = vanilla("sheep", EntityType.Category.CREATURE, 111);
    public static final EntityType SHULKER = vanilla("shulker", EntityType.Category.MONSTER, 112);
    public static final EntityType SHULKER_BULLET = vanilla("shulker_bullet", EntityType.Category.MISC, 113);
    public static final EntityType SILVERFISH = vanilla("silverfish", EntityType.Category.MONSTER, 114);
    public static final EntityType SKELETON = vanilla("skeleton", EntityType.Category.MONSTER, 115);
    public static final EntityType SKELETON_HORSE = vanilla("skeleton_horse", EntityType.Category.CREATURE, 116);
    public static final EntityType SLIME = vanilla("slime", EntityType.Category.MONSTER, 117);
    public static final EntityType SMALL_FIREBALL = vanilla("small_fireball", EntityType.Category.MISC, 118);
    public static final EntityType SNIFFER = vanilla("sniffer", EntityType.Category.CREATURE, 119);
    public static final EntityType SNOWBALL = vanilla("snowball", EntityType.Category.MISC, 120);
    public static final EntityType SNOW_GOLEM = vanilla("snow_golem", EntityType.Category.MISC, 121);
    public static final EntityType SPAWNER_MINECART = vanilla("spawner_minecart", EntityType.Category.MISC, 122);
    public static final EntityType SPECTRAL_ARROW = vanilla("spectral_arrow", EntityType.Category.MISC, 123);
    public static final EntityType SPIDER = vanilla("spider", EntityType.Category.MONSTER, 124);
    public static final EntityType SPRUCE_BOAT = vanilla("spruce_boat", EntityType.Category.MISC, 125);
    public static final EntityType SPRUCE_CHEST_BOAT = vanilla("spruce_chest_boat", EntityType.Category.MISC, 126);
    public static final EntityType SQUID = vanilla("squid", EntityType.Category.WATER_CREATURE, 127);
    public static final EntityType STRAY = vanilla("stray", EntityType.Category.MONSTER, 128);
    public static final EntityType STRIDER = vanilla("strider", EntityType.Category.CREATURE, 129);
    public static final EntityType SULFUR_CUBE = vanilla("sulfur_cube", EntityType.Category.MONSTER, 130);
    public static final EntityType TADPOLE = vanilla("tadpole", EntityType.Category.WATER_CREATURE, 131);
    public static final EntityType TEXT_DISPLAY = vanilla("text_display", EntityType.Category.MISC, 132);
    public static final EntityType TNT = vanilla("tnt", EntityType.Category.MISC, 133);
    public static final EntityType TNT_MINECART = vanilla("tnt_minecart", EntityType.Category.MISC, 134);
    public static final EntityType TRADER_LLAMA = vanilla("trader_llama", EntityType.Category.CREATURE, 135);
    public static final EntityType TRIDENT = vanilla("trident", EntityType.Category.MISC, 136);
    public static final EntityType TROPICAL_FISH = vanilla("tropical_fish", EntityType.Category.WATER_CREATURE, 137);
    public static final EntityType TURTLE = vanilla("turtle", EntityType.Category.CREATURE, 138);
    public static final EntityType VEX = vanilla("vex", EntityType.Category.MONSTER, 139);
    public static final EntityType VILLAGER = vanilla("villager", EntityType.Category.CREATURE, 140);
    public static final EntityType VINDICATOR = vanilla("vindicator", EntityType.Category.MONSTER, 141);
    public static final EntityType WANDERING_TRADER = vanilla("wandering_trader", EntityType.Category.CREATURE, 142);
    public static final EntityType WARDEN = vanilla("warden", EntityType.Category.MONSTER, 143);
    public static final EntityType WIND_CHARGE = vanilla("wind_charge", EntityType.Category.MISC, 144);
    public static final EntityType WITCH = vanilla("witch", EntityType.Category.MONSTER, 145);
    public static final EntityType WITHER = vanilla("wither", EntityType.Category.MONSTER, 146);
    public static final EntityType WITHER_SKELETON = vanilla("wither_skeleton", EntityType.Category.MONSTER, 147);
    public static final EntityType WITHER_SKULL = vanilla("wither_skull", EntityType.Category.MISC, 148);
    public static final EntityType WOLF = vanilla("wolf", EntityType.Category.CREATURE, 149);
    public static final EntityType ZOGLIN = vanilla("zoglin", EntityType.Category.MONSTER, 150);
    public static final EntityType ZOMBIE = vanilla("zombie", EntityType.Category.MONSTER, 151);
    public static final EntityType ZOMBIE_HORSE = vanilla("zombie_horse", EntityType.Category.CREATURE, 152);
    public static final EntityType ZOMBIE_NAUTILUS = vanilla("zombie_nautilus", EntityType.Category.MONSTER, 153);
    public static final EntityType ZOMBIE_VILLAGER = vanilla("zombie_villager", EntityType.Category.MONSTER, 154);
    public static final EntityType ZOMBIFIED_PIGLIN = vanilla("zombified_piglin", EntityType.Category.MONSTER, 155);
    public static final EntityType PLAYER = vanilla("player", EntityType.Category.PLAYER, 156);
    public static final EntityType FISHING_BOBBER = vanilla("fishing_bobber", EntityType.Category.MISC, 157);

    private EntityTypes() {
    }

    private static EntityType vanilla(@KeyPattern String name, EntityType.Category category, int networkId) {
        Key key = Key.key(name);
        EntityType type = new EntityType(
                key,
                category,
                0.6f,
                1.8f
        );
        register(type);
        NETWORK_IDS.put(key, networkId);
        return type;
    }

    public static EntityType register(EntityType type) {
        EntityType previous = BY_KEY.putIfAbsent(type.key(), type);
        if (previous != null) {
            throw new IllegalStateException("Entity type already registered : " + type.key());
        }
        return type;
    }

    public static EntityType get(Key key) {
        return BY_KEY.get(key);
    }

    public static Iterable<EntityType> values() {
        return BY_KEY.values();
    }

    public static int networkId(EntityType type) {
        Integer id = NETWORK_IDS.get(type.key());
        if (id == null) {
            throw new IllegalStateException("No network ID for the entity type " + type.key());
        }
        return id;
    }

    public static boolean hasNetworkId(EntityType type) {
        return NETWORK_IDS.containsKey(type.key());
    }
}