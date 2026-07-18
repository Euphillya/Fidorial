package fr.euphyllia.fidorial.server.entity;

import com.google.gson.stream.JsonReader;
import fr.fidorial.entity.EntityType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

public final class EntityTypes {

    private static final String RESOURCE = "/data/entity_types.json.gz";

    private static final Map<Key, EntityType> BY_KEY = new ConcurrentHashMap<>();
    private static final Map<Key, Integer> NETWORK_IDS = new ConcurrentHashMap<>();

    static {
        loadVanillaTypes();
    }

    public static final EntityType ACACIA_BOAT = vanilla("acacia_boat");
    public static final EntityType ACACIA_CHEST_BOAT = vanilla("acacia_chest_boat");
    public static final EntityType ALLAY = vanilla("allay");
    public static final EntityType AREA_EFFECT_CLOUD = vanilla("area_effect_cloud");
    public static final EntityType ARMADILLO = vanilla("armadillo");
    public static final EntityType ARMOR_STAND = vanilla("armor_stand");
    public static final EntityType ARROW = vanilla("arrow");
    public static final EntityType AXOLOTL = vanilla("axolotl");
    public static final EntityType BAMBOO_CHEST_RAFT = vanilla("bamboo_chest_raft");
    public static final EntityType BAMBOO_RAFT = vanilla("bamboo_raft");
    public static final EntityType BAT = vanilla("bat");
    public static final EntityType BEE = vanilla("bee");
    public static final EntityType BIRCH_BOAT = vanilla("birch_boat");
    public static final EntityType BIRCH_CHEST_BOAT = vanilla("birch_chest_boat");
    public static final EntityType BLAZE = vanilla("blaze");
    public static final EntityType BLOCK_DISPLAY = vanilla("block_display");
    public static final EntityType BOGGED = vanilla("bogged");
    public static final EntityType BREEZE = vanilla("breeze");
    public static final EntityType BREEZE_WIND_CHARGE = vanilla("breeze_wind_charge");
    public static final EntityType CAMEL = vanilla("camel");
    public static final EntityType CAMEL_HUSK = vanilla("camel_husk");
    public static final EntityType CAT = vanilla("cat");
    public static final EntityType CAVE_SPIDER = vanilla("cave_spider");
    public static final EntityType CHERRY_BOAT = vanilla("cherry_boat");
    public static final EntityType CHERRY_CHEST_BOAT = vanilla("cherry_chest_boat");
    public static final EntityType CHEST_MINECART = vanilla("chest_minecart");
    public static final EntityType CHICKEN = vanilla("chicken");
    public static final EntityType COD = vanilla("cod");
    public static final EntityType COPPER_GOLEM = vanilla("copper_golem");
    public static final EntityType COMMAND_BLOCK_MINECART = vanilla("command_block_minecart");
    public static final EntityType COW = vanilla("cow");
    public static final EntityType CREAKING = vanilla("creaking");
    public static final EntityType CREEPER = vanilla("creeper");
    public static final EntityType DARK_OAK_BOAT = vanilla("dark_oak_boat");
    public static final EntityType DARK_OAK_CHEST_BOAT = vanilla("dark_oak_chest_boat");
    public static final EntityType DOLPHIN = vanilla("dolphin");
    public static final EntityType DONKEY = vanilla("donkey");
    public static final EntityType DRAGON_FIREBALL = vanilla("dragon_fireball");
    public static final EntityType DROWNED = vanilla("drowned");
    public static final EntityType EGG = vanilla("egg");
    public static final EntityType ELDER_GUARDIAN = vanilla("elder_guardian");
    public static final EntityType ENDERMAN = vanilla("enderman");
    public static final EntityType ENDERMITE = vanilla("endermite");
    public static final EntityType ENDER_DRAGON = vanilla("ender_dragon");
    public static final EntityType ENDER_PEARL = vanilla("ender_pearl");
    public static final EntityType END_CRYSTAL = vanilla("end_crystal");
    public static final EntityType EVOKER = vanilla("evoker");
    public static final EntityType EVOKER_FANGS = vanilla("evoker_fangs");
    public static final EntityType EXPERIENCE_BOTTLE = vanilla("experience_bottle");
    public static final EntityType EXPERIENCE_ORB = vanilla("experience_orb");
    public static final EntityType EYE_OF_ENDER = vanilla("eye_of_ender");
    public static final EntityType FALLING_BLOCK = vanilla("falling_block");
    public static final EntityType FIREBALL = vanilla("fireball");
    public static final EntityType FIREWORK_ROCKET = vanilla("firework_rocket");
    public static final EntityType FOX = vanilla("fox");
    public static final EntityType FROG = vanilla("frog");
    public static final EntityType FURNACE_MINECART = vanilla("furnace_minecart");
    public static final EntityType GHAST = vanilla("ghast");
    public static final EntityType HAPPY_GHAST = vanilla("happy_ghast");
    public static final EntityType GIANT = vanilla("giant");
    public static final EntityType GLOW_ITEM_FRAME = vanilla("glow_item_frame");
    public static final EntityType GLOW_SQUID = vanilla("glow_squid");
    public static final EntityType GOAT = vanilla("goat");
    public static final EntityType GUARDIAN = vanilla("guardian");
    public static final EntityType HOGLIN = vanilla("hoglin");
    public static final EntityType HOPPER_MINECART = vanilla("hopper_minecart");
    public static final EntityType HORSE = vanilla("horse");
    public static final EntityType HUSK = vanilla("husk");
    public static final EntityType ILLUSIONER = vanilla("illusioner");
    public static final EntityType INTERACTION = vanilla("interaction");
    public static final EntityType IRON_GOLEM = vanilla("iron_golem");
    public static final EntityType ITEM = vanilla("item");
    public static final EntityType ITEM_DISPLAY = vanilla("item_display");
    public static final EntityType ITEM_FRAME = vanilla("item_frame");
    public static final EntityType JUNGLE_BOAT = vanilla("jungle_boat");
    public static final EntityType JUNGLE_CHEST_BOAT = vanilla("jungle_chest_boat");
    public static final EntityType LEASH_KNOT = vanilla("leash_knot");
    public static final EntityType LIGHTNING_BOLT = vanilla("lightning_bolt");
    public static final EntityType LLAMA = vanilla("llama");
    public static final EntityType LLAMA_SPIT = vanilla("llama_spit");
    public static final EntityType MAGMA_CUBE = vanilla("magma_cube");
    public static final EntityType MANGROVE_BOAT = vanilla("mangrove_boat");
    public static final EntityType MANGROVE_CHEST_BOAT = vanilla("mangrove_chest_boat");
    public static final EntityType MANNEQUIN = vanilla("mannequin");
    public static final EntityType MARKER = vanilla("marker");
    public static final EntityType MINECART = vanilla("minecart");
    public static final EntityType MOOSHROOM = vanilla("mooshroom");
    public static final EntityType MULE = vanilla("mule");
    public static final EntityType NAUTILUS = vanilla("nautilus");
    public static final EntityType OAK_BOAT = vanilla("oak_boat");
    public static final EntityType OAK_CHEST_BOAT = vanilla("oak_chest_boat");
    public static final EntityType OCELOT = vanilla("ocelot");
    public static final EntityType OMINOUS_ITEM_SPAWNER = vanilla("ominous_item_spawner");
    public static final EntityType PAINTING = vanilla("painting");
    public static final EntityType PALE_OAK_BOAT = vanilla("pale_oak_boat");
    public static final EntityType PALE_OAK_CHEST_BOAT = vanilla("pale_oak_chest_boat");
    public static final EntityType PANDA = vanilla("panda");
    public static final EntityType PARCHED = vanilla("parched");
    public static final EntityType PARROT = vanilla("parrot");
    public static final EntityType PHANTOM = vanilla("phantom");
    public static final EntityType PIG = vanilla("pig");
    public static final EntityType PIGLIN = vanilla("piglin");
    public static final EntityType PIGLIN_BRUTE = vanilla("piglin_brute");
    public static final EntityType PILLAGER = vanilla("pillager");
    public static final EntityType POLAR_BEAR = vanilla("polar_bear");
    public static final EntityType SPLASH_POTION = vanilla("splash_potion");
    public static final EntityType LINGERING_POTION = vanilla("lingering_potion");
    public static final EntityType PUFFERFISH = vanilla("pufferfish");
    public static final EntityType RABBIT = vanilla("rabbit");
    public static final EntityType RAVAGER = vanilla("ravager");
    public static final EntityType SALMON = vanilla("salmon");
    public static final EntityType SHEEP = vanilla("sheep");
    public static final EntityType SHULKER = vanilla("shulker");
    public static final EntityType SHULKER_BULLET = vanilla("shulker_bullet");
    public static final EntityType SILVERFISH = vanilla("silverfish");
    public static final EntityType SKELETON = vanilla("skeleton");
    public static final EntityType SKELETON_HORSE = vanilla("skeleton_horse");
    public static final EntityType SLIME = vanilla("slime");
    public static final EntityType SMALL_FIREBALL = vanilla("small_fireball");
    public static final EntityType SNIFFER = vanilla("sniffer");
    public static final EntityType SNOWBALL = vanilla("snowball");
    public static final EntityType SNOW_GOLEM = vanilla("snow_golem");
    public static final EntityType SPAWNER_MINECART = vanilla("spawner_minecart");
    public static final EntityType SPECTRAL_ARROW = vanilla("spectral_arrow");
    public static final EntityType SPIDER = vanilla("spider");
    public static final EntityType SPRUCE_BOAT = vanilla("spruce_boat");
    public static final EntityType SPRUCE_CHEST_BOAT = vanilla("spruce_chest_boat");
    public static final EntityType SQUID = vanilla("squid");
    public static final EntityType STRAY = vanilla("stray");
    public static final EntityType STRIDER = vanilla("strider");
    public static final EntityType SULFUR_CUBE = vanilla("sulfur_cube");
    public static final EntityType TADPOLE = vanilla("tadpole");
    public static final EntityType TEXT_DISPLAY = vanilla("text_display");
    public static final EntityType TNT = vanilla("tnt");
    public static final EntityType TNT_MINECART = vanilla("tnt_minecart");
    public static final EntityType TRADER_LLAMA = vanilla("trader_llama");
    public static final EntityType TRIDENT = vanilla("trident");
    public static final EntityType TROPICAL_FISH = vanilla("tropical_fish");
    public static final EntityType TURTLE = vanilla("turtle");
    public static final EntityType VEX = vanilla("vex");
    public static final EntityType VILLAGER = vanilla("villager");
    public static final EntityType VINDICATOR = vanilla("vindicator");
    public static final EntityType WANDERING_TRADER = vanilla("wandering_trader");
    public static final EntityType WARDEN = vanilla("warden");
    public static final EntityType WIND_CHARGE = vanilla("wind_charge");
    public static final EntityType WITCH = vanilla("witch");
    public static final EntityType WITHER = vanilla("wither");
    public static final EntityType WITHER_SKELETON = vanilla("wither_skeleton");
    public static final EntityType WITHER_SKULL = vanilla("wither_skull");
    public static final EntityType WOLF = vanilla("wolf");
    public static final EntityType ZOGLIN = vanilla("zoglin");
    public static final EntityType ZOMBIE = vanilla("zombie");
    public static final EntityType ZOMBIE_HORSE = vanilla("zombie_horse");
    public static final EntityType ZOMBIE_NAUTILUS = vanilla("zombie_nautilus");
    public static final EntityType ZOMBIE_VILLAGER = vanilla("zombie_villager");
    public static final EntityType ZOMBIFIED_PIGLIN = vanilla("zombified_piglin");
    public static final EntityType PLAYER = vanilla("player");
    public static final EntityType FISHING_BOBBER = vanilla("fishing_bobber");

    private EntityTypes() {
    }

    @SuppressWarnings("PatternValidation")
    private static void loadVanillaTypes() {
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
                    register(type);
                    NETWORK_IDS.put(key, networkId);
                }
                reader.endObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading entity types", e);
        }
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
