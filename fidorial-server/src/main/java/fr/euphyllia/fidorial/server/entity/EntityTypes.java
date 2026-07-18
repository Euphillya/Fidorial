package fr.euphyllia.fidorial.server.entity;

import com.google.gson.stream.JsonReader;
import fr.euphyllia.fidorial.api.entity.EntityType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

@SuppressWarnings("PatternValidation")
public final class EntityTypes {

    private static final String RESOURCE = "/data/entity_types.json.gz";

    private static final Map<Key, EntityType> BY_KEY;
    private static final Map<Key, Integer> NETWORK_IDS;

    public static EntityType ACACIA_BOAT;
    public static EntityType ACACIA_CHEST_BOAT;
    public static EntityType ALLAY;
    public static EntityType AREA_EFFECT_CLOUD;
    public static EntityType ARMADILLO;
    public static EntityType ARMOR_STAND;
    public static EntityType ARROW;
    public static EntityType AXOLOTL;
    public static EntityType BAMBOO_CHEST_RAFT;
    public static EntityType BAMBOO_RAFT;
    public static EntityType BAT;
    public static EntityType BEE;
    public static EntityType BIRCH_BOAT;
    public static EntityType BIRCH_CHEST_BOAT;
    public static EntityType BLAZE;
    public static EntityType BLOCK_DISPLAY;
    public static EntityType BOGGED;
    public static EntityType BREEZE;
    public static EntityType BREEZE_WIND_CHARGE;
    public static EntityType CAMEL;
    public static EntityType CAMEL_HUSK;
    public static EntityType CAT;
    public static EntityType CAVE_SPIDER;
    public static EntityType CHERRY_BOAT;
    public static EntityType CHERRY_CHEST_BOAT;
    public static EntityType CHEST_MINECART;
    public static EntityType CHICKEN;
    public static EntityType COD;
    public static EntityType COPPER_GOLEM;
    public static EntityType COMMAND_BLOCK_MINECART;
    public static EntityType COW;
    public static EntityType CREAKING;
    public static EntityType CREEPER;
    public static EntityType DARK_OAK_BOAT;
    public static EntityType DARK_OAK_CHEST_BOAT;
    public static EntityType DOLPHIN;
    public static EntityType DONKEY;
    public static EntityType DRAGON_FIREBALL;
    public static EntityType DROWNED;
    public static EntityType EGG;
    public static EntityType ELDER_GUARDIAN;
    public static EntityType ENDERMAN;
    public static EntityType ENDERMITE;
    public static EntityType ENDER_DRAGON;
    public static EntityType ENDER_PEARL;
    public static EntityType END_CRYSTAL;
    public static EntityType EVOKER;
    public static EntityType EVOKER_FANGS;
    public static EntityType EXPERIENCE_BOTTLE;
    public static EntityType EXPERIENCE_ORB;
    public static EntityType EYE_OF_ENDER;
    public static EntityType FALLING_BLOCK;
    public static EntityType FIREBALL;
    public static EntityType FIREWORK_ROCKET;
    public static EntityType FOX;
    public static EntityType FROG;
    public static EntityType FURNACE_MINECART;
    public static EntityType GHAST;
    public static EntityType HAPPY_GHAST;
    public static EntityType GIANT;
    public static EntityType GLOW_ITEM_FRAME;
    public static EntityType GLOW_SQUID;
    public static EntityType GOAT;
    public static EntityType GUARDIAN;
    public static EntityType HOGLIN;
    public static EntityType HOPPER_MINECART;
    public static EntityType HORSE;
    public static EntityType HUSK;
    public static EntityType ILLUSIONER;
    public static EntityType INTERACTION;
    public static EntityType IRON_GOLEM;
    public static EntityType ITEM;
    public static EntityType ITEM_DISPLAY;
    public static EntityType ITEM_FRAME;
    public static EntityType JUNGLE_BOAT;
    public static EntityType JUNGLE_CHEST_BOAT;
    public static EntityType LEASH_KNOT;
    public static EntityType LIGHTNING_BOLT;
    public static EntityType LLAMA;
    public static EntityType LLAMA_SPIT;
    public static EntityType MAGMA_CUBE;
    public static EntityType MANGROVE_BOAT;
    public static EntityType MANGROVE_CHEST_BOAT;
    public static EntityType MANNEQUIN;
    public static EntityType MARKER;
    public static EntityType MINECART;
    public static EntityType MOOSHROOM;
    public static EntityType MULE;
    public static EntityType NAUTILUS;
    public static EntityType OAK_BOAT;
    public static EntityType OAK_CHEST_BOAT;
    public static EntityType OCELOT;
    public static EntityType OMINOUS_ITEM_SPAWNER;
    public static EntityType PAINTING;
    public static EntityType PALE_OAK_BOAT;
    public static EntityType PALE_OAK_CHEST_BOAT;
    public static EntityType PANDA;
    public static EntityType PARCHED;
    public static EntityType PARROT;
    public static EntityType PHANTOM;
    public static EntityType PIG;
    public static EntityType PIGLIN;
    public static EntityType PIGLIN_BRUTE;
    public static EntityType PILLAGER;
    public static EntityType POLAR_BEAR;
    public static EntityType SPLASH_POTION;
    public static EntityType LINGERING_POTION;
    public static EntityType PUFFERFISH;
    public static EntityType RABBIT;
    public static EntityType RAVAGER;
    public static EntityType SALMON;
    public static EntityType SHEEP;
    public static EntityType SHULKER;
    public static EntityType SHULKER_BULLET;
    public static EntityType SILVERFISH;
    public static EntityType SKELETON;
    public static EntityType SKELETON_HORSE;
    public static EntityType SLIME;
    public static EntityType SMALL_FIREBALL;
    public static EntityType SNIFFER;
    public static EntityType SNOWBALL;
    public static EntityType SNOW_GOLEM;
    public static EntityType SPAWNER_MINECART;
    public static EntityType SPECTRAL_ARROW;
    public static EntityType SPIDER;
    public static EntityType SPRUCE_BOAT;
    public static EntityType SPRUCE_CHEST_BOAT;
    public static EntityType SQUID;
    public static EntityType STRAY;
    public static EntityType STRIDER;
    public static EntityType SULFUR_CUBE;
    public static EntityType TADPOLE;
    public static EntityType TEXT_DISPLAY;
    public static EntityType TNT;
    public static EntityType TNT_MINECART;
    public static EntityType TRADER_LLAMA;
    public static EntityType TRIDENT;
    public static EntityType TROPICAL_FISH;
    public static EntityType TURTLE;
    public static EntityType VEX;
    public static EntityType VILLAGER;
    public static EntityType VINDICATOR;
    public static EntityType WANDERING_TRADER;
    public static EntityType WARDEN;
    public static EntityType WIND_CHARGE;
    public static EntityType WITCH;
    public static EntityType WITHER;
    public static EntityType WITHER_SKELETON;
    public static EntityType WITHER_SKULL;
    public static EntityType WOLF;
    public static EntityType ZOGLIN;
    public static EntityType ZOMBIE;
    public static EntityType ZOMBIE_HORSE;
    public static EntityType ZOMBIE_NAUTILUS;
    public static EntityType ZOMBIE_VILLAGER;
    public static EntityType ZOMBIFIED_PIGLIN;
    public static EntityType PLAYER;
    public static EntityType FISHING_BOBBER;

    static {
        Map<Key, EntityType> tempTypes = new ConcurrentHashMap<>();
        Map<Key, Integer> tempNetworkIds = new ConcurrentHashMap<>();

        try (InputStream raw = EntityTypes.class.getResourceAsStream(RESOURCE)) {
            if (raw == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            try (JsonReader reader = new JsonReader(new InputStreamReader(
                    new GZIPInputStream(raw), StandardCharsets.UTF_8))) {
                reader.beginObject();
                while (reader.hasNext()) {
                    Key key = Key.key(reader.nextName());
                    int networkId = reader.nextInt();

                    EntityType type = new EntityType(
                            key,
                            EntityType.Category.MISC, // Todo : Need replace
                            0.6f,
                            1.8f
                    );
                    tempTypes.put(key, type);
                    tempNetworkIds.put(key, networkId);
                }
                reader.endObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading entity types", e);
        }

        BY_KEY = Collections.unmodifiableMap(tempTypes);
        NETWORK_IDS = Collections.unmodifiableMap(tempNetworkIds);

        initializeConstants();
    }

    private EntityTypes() {
    }

    private static void initializeConstants() {
        ACACIA_BOAT = vanilla("acacia_boat");
        ACACIA_CHEST_BOAT = vanilla("acacia_chest_boat");
        ALLAY = vanilla("allay");
        AREA_EFFECT_CLOUD = vanilla("area_effect_cloud");
        ARMADILLO = vanilla("armadillo");
        ARMOR_STAND = vanilla("armor_stand");
        ARROW = vanilla("arrow");
        AXOLOTL = vanilla("axolotl");
        BAMBOO_CHEST_RAFT = vanilla("bamboo_chest_raft");
        BAMBOO_RAFT = vanilla("bamboo_raft");
        BAT = vanilla("bat");
        BEE = vanilla("bee");
        BIRCH_BOAT = vanilla("birch_boat");
        BIRCH_CHEST_BOAT = vanilla("birch_chest_boat");
        BLAZE = vanilla("blaze");
        BLOCK_DISPLAY = vanilla("block_display");
        BOGGED = vanilla("bogged");
        BREEZE = vanilla("breeze");
        BREEZE_WIND_CHARGE = vanilla("breeze_wind_charge");
        CAMEL = vanilla("camel");
        CAMEL_HUSK = vanilla("camel_husk");
        CAT = vanilla("cat");
        CAVE_SPIDER = vanilla("cave_spider");
        CHERRY_BOAT = vanilla("cherry_boat");
        CHERRY_CHEST_BOAT = vanilla("cherry_chest_boat");
        CHEST_MINECART = vanilla("chest_minecart");
        CHICKEN = vanilla("chicken");
        COD = vanilla("cod");
        COPPER_GOLEM = vanilla("copper_golem");
        COMMAND_BLOCK_MINECART = vanilla("command_block_minecart");
        COW = vanilla("cow");
        CREAKING = vanilla("creaking");
        CREEPER = vanilla("creeper");
        DARK_OAK_BOAT = vanilla("dark_oak_boat");
        DARK_OAK_CHEST_BOAT = vanilla("dark_oak_chest_boat");
        DOLPHIN = vanilla("dolphin");
        DONKEY = vanilla("donkey");
        DRAGON_FIREBALL = vanilla("dragon_fireball");
        DROWNED = vanilla("drowned");
        EGG = vanilla("egg");
        ELDER_GUARDIAN = vanilla("elder_guardian");
        ENDERMAN = vanilla("enderman");
        ENDERMITE = vanilla("endermite");
        ENDER_DRAGON = vanilla("ender_dragon");
        ENDER_PEARL = vanilla("ender_pearl");
        END_CRYSTAL = vanilla("end_crystal");
        EVOKER = vanilla("evoker");
        EVOKER_FANGS = vanilla("evoker_fangs");
        EXPERIENCE_BOTTLE = vanilla("experience_bottle");
        EXPERIENCE_ORB = vanilla("experience_orb");
        EYE_OF_ENDER = vanilla("eye_of_ender");
        FALLING_BLOCK = vanilla("falling_block");
        FIREBALL = vanilla("fireball");
        FIREWORK_ROCKET = vanilla("firework_rocket");
        FOX = vanilla("fox");
        FROG = vanilla("frog");
        FURNACE_MINECART = vanilla("furnace_minecart");
        GHAST = vanilla("ghast");
        HAPPY_GHAST = vanilla("happy_ghast");
        GIANT = vanilla("giant");
        GLOW_ITEM_FRAME = vanilla("glow_item_frame");
        GLOW_SQUID = vanilla("glow_squid");
        GOAT = vanilla("goat");
        GUARDIAN = vanilla("guardian");
        HOGLIN = vanilla("hoglin");
        HOPPER_MINECART = vanilla("hopper_minecart");
        HORSE = vanilla("horse");
        HUSK = vanilla("husk");
        ILLUSIONER = vanilla("illusioner");
        INTERACTION = vanilla("interaction");
        IRON_GOLEM = vanilla("iron_golem");
        ITEM = vanilla("item");
        ITEM_DISPLAY = vanilla("item_display");
        ITEM_FRAME = vanilla("item_frame");
        JUNGLE_BOAT = vanilla("jungle_boat");
        JUNGLE_CHEST_BOAT = vanilla("jungle_chest_boat");
        LEASH_KNOT = vanilla("leash_knot");
        LIGHTNING_BOLT = vanilla("lightning_bolt");
        LLAMA = vanilla("llama");
        LLAMA_SPIT = vanilla("llama_spit");
        MAGMA_CUBE = vanilla("magma_cube");
        MANGROVE_BOAT = vanilla("mangrove_boat");
        MANGROVE_CHEST_BOAT = vanilla("mangrove_chest_boat");
        MANNEQUIN = vanilla("mannequin");
        MARKER = vanilla("marker");
        MINECART = vanilla("minecart");
        MOOSHROOM = vanilla("mooshroom");
        MULE = vanilla("mule");
        NAUTILUS = vanilla("nautilus");
        OAK_BOAT = vanilla("oak_boat");
        OAK_CHEST_BOAT = vanilla("oak_chest_boat");
        OCELOT = vanilla("ocelot");
        OMINOUS_ITEM_SPAWNER = vanilla("ominous_item_spawner");
        PAINTING = vanilla("painting");
        PALE_OAK_BOAT = vanilla("pale_oak_boat");
        PALE_OAK_CHEST_BOAT = vanilla("pale_oak_chest_boat");
        PANDA = vanilla("panda");
        PARCHED = vanilla("parched");
        PARROT = vanilla("parrot");
        PHANTOM = vanilla("phantom");
        PIG = vanilla("pig");
        PIGLIN = vanilla("piglin");
        PIGLIN_BRUTE = vanilla("piglin_brute");
        PILLAGER = vanilla("pillager");
        POLAR_BEAR = vanilla("polar_bear");
        SPLASH_POTION = vanilla("splash_potion");
        LINGERING_POTION = vanilla("lingering_potion");
        PUFFERFISH = vanilla("pufferfish");
        RABBIT = vanilla("rabbit");
        RAVAGER = vanilla("ravager");
        SALMON = vanilla("salmon");
        SHEEP = vanilla("sheep");
        SHULKER = vanilla("shulker");
        SHULKER_BULLET = vanilla("shulker_bullet");
        SILVERFISH = vanilla("silverfish");
        SKELETON = vanilla("skeleton");
        SKELETON_HORSE = vanilla("skeleton_horse");
        SLIME = vanilla("slime");
        SMALL_FIREBALL = vanilla("small_fireball");
        SNIFFER = vanilla("sniffer");
        SNOWBALL = vanilla("snowball");
        SNOW_GOLEM = vanilla("snow_golem");
        SPAWNER_MINECART = vanilla("spawner_minecart");
        SPECTRAL_ARROW = vanilla("spectral_arrow");
        SPIDER = vanilla("spider");
        SPRUCE_BOAT = vanilla("spruce_boat");
        SPRUCE_CHEST_BOAT = vanilla("spruce_chest_boat");
        SQUID = vanilla("squid");
        STRAY = vanilla("stray");
        STRIDER = vanilla("strider");
        SULFUR_CUBE = vanilla("sulfur_cube");
        TADPOLE = vanilla("tadpole");
        TEXT_DISPLAY = vanilla("text_display");
        TNT = vanilla("tnt");
        TNT_MINECART = vanilla("tnt_minecart");
        TRADER_LLAMA = vanilla("trader_llama");
        TRIDENT = vanilla("trident");
        TROPICAL_FISH = vanilla("tropical_fish");
        TURTLE = vanilla("turtle");
        VEX = vanilla("vex");
        VILLAGER = vanilla("villager");
        VINDICATOR = vanilla("vindicator");
        WANDERING_TRADER = vanilla("wandering_trader");
        WARDEN = vanilla("warden");
        WIND_CHARGE = vanilla("wind_charge");
        WITCH = vanilla("witch");
        WITHER = vanilla("wither");
        WITHER_SKELETON = vanilla("wither_skeleton");
        WITHER_SKULL = vanilla("wither_skull");
        WOLF = vanilla("wolf");
        ZOGLIN = vanilla("zoglin");
        ZOMBIE = vanilla("zombie");
        ZOMBIE_HORSE = vanilla("zombie_horse");
        ZOMBIE_NAUTILUS = vanilla("zombie_nautilus");
        ZOMBIE_VILLAGER = vanilla("zombie_villager");
        ZOMBIFIED_PIGLIN = vanilla("zombified_piglin");
        PLAYER = vanilla("player");
        FISHING_BOBBER = vanilla("fishing_bobber");
    }

    private static EntityType vanilla(@KeyPattern String name) {
        EntityType type = BY_KEY.get(Key.key(name));
        if (type == null) {
            throw new IllegalStateException("Vanilla type absent from " + RESOURCE + " : " + name);
        }
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