package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.DamageType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:damage_type} registry.
 */
public final class DamageTypeKeys {
    /**
     * Key for {@code minecraft:arrow}.
     */
    public static final TypedKey<DamageType> ARROW = create("arrow");

    /**
     * Key for {@code minecraft:bad_respawn_point}.
     */
    public static final TypedKey<DamageType> BAD_RESPAWN_POINT = create("bad_respawn_point");

    /**
     * Key for {@code minecraft:cactus}.
     */
    public static final TypedKey<DamageType> CACTUS = create("cactus");

    /**
     * Key for {@code minecraft:campfire}.
     */
    public static final TypedKey<DamageType> CAMPFIRE = create("campfire");

    /**
     * Key for {@code minecraft:cramming}.
     */
    public static final TypedKey<DamageType> CRAMMING = create("cramming");

    /**
     * Key for {@code minecraft:dragon_breath}.
     */
    public static final TypedKey<DamageType> DRAGON_BREATH = create("dragon_breath");

    /**
     * Key for {@code minecraft:drown}.
     */
    public static final TypedKey<DamageType> DROWN = create("drown");

    /**
     * Key for {@code minecraft:dry_out}.
     */
    public static final TypedKey<DamageType> DRY_OUT = create("dry_out");

    /**
     * Key for {@code minecraft:ender_pearl}.
     */
    public static final TypedKey<DamageType> ENDER_PEARL = create("ender_pearl");

    /**
     * Key for {@code minecraft:explosion}.
     */
    public static final TypedKey<DamageType> EXPLOSION = create("explosion");

    /**
     * Key for {@code minecraft:fall}.
     */
    public static final TypedKey<DamageType> FALL = create("fall");

    /**
     * Key for {@code minecraft:falling_anvil}.
     */
    public static final TypedKey<DamageType> FALLING_ANVIL = create("falling_anvil");

    /**
     * Key for {@code minecraft:falling_block}.
     */
    public static final TypedKey<DamageType> FALLING_BLOCK = create("falling_block");

    /**
     * Key for {@code minecraft:falling_stalactite}.
     */
    public static final TypedKey<DamageType> FALLING_STALACTITE = create("falling_stalactite");

    /**
     * Key for {@code minecraft:fireball}.
     */
    public static final TypedKey<DamageType> FIREBALL = create("fireball");

    /**
     * Key for {@code minecraft:fireworks}.
     */
    public static final TypedKey<DamageType> FIREWORKS = create("fireworks");

    /**
     * Key for {@code minecraft:fly_into_wall}.
     */
    public static final TypedKey<DamageType> FLY_INTO_WALL = create("fly_into_wall");

    /**
     * Key for {@code minecraft:freeze}.
     */
    public static final TypedKey<DamageType> FREEZE = create("freeze");

    /**
     * Key for {@code minecraft:generic}.
     */
    public static final TypedKey<DamageType> GENERIC = create("generic");

    /**
     * Key for {@code minecraft:generic_kill}.
     */
    public static final TypedKey<DamageType> GENERIC_KILL = create("generic_kill");

    /**
     * Key for {@code minecraft:hot_floor}.
     */
    public static final TypedKey<DamageType> HOT_FLOOR = create("hot_floor");

    /**
     * Key for {@code minecraft:indirect_magic}.
     */
    public static final TypedKey<DamageType> INDIRECT_MAGIC = create("indirect_magic");

    /**
     * Key for {@code minecraft:in_fire}.
     */
    public static final TypedKey<DamageType> IN_FIRE = create("in_fire");

    /**
     * Key for {@code minecraft:in_wall}.
     */
    public static final TypedKey<DamageType> IN_WALL = create("in_wall");

    /**
     * Key for {@code minecraft:lava}.
     */
    public static final TypedKey<DamageType> LAVA = create("lava");

    /**
     * Key for {@code minecraft:lightning_bolt}.
     */
    public static final TypedKey<DamageType> LIGHTNING_BOLT = create("lightning_bolt");

    /**
     * Key for {@code minecraft:mace_smash}.
     */
    public static final TypedKey<DamageType> MACE_SMASH = create("mace_smash");

    /**
     * Key for {@code minecraft:magic}.
     */
    public static final TypedKey<DamageType> MAGIC = create("magic");

    /**
     * Key for {@code minecraft:mob_attack}.
     */
    public static final TypedKey<DamageType> MOB_ATTACK = create("mob_attack");

    /**
     * Key for {@code minecraft:mob_attack_no_aggro}.
     */
    public static final TypedKey<DamageType> MOB_ATTACK_NO_AGGRO = create("mob_attack_no_aggro");

    /**
     * Key for {@code minecraft:mob_projectile}.
     */
    public static final TypedKey<DamageType> MOB_PROJECTILE = create("mob_projectile");

    /**
     * Key for {@code minecraft:on_fire}.
     */
    public static final TypedKey<DamageType> ON_FIRE = create("on_fire");

    /**
     * Key for {@code minecraft:outside_border}.
     */
    public static final TypedKey<DamageType> OUTSIDE_BORDER = create("outside_border");

    /**
     * Key for {@code minecraft:out_of_world}.
     */
    public static final TypedKey<DamageType> OUT_OF_WORLD = create("out_of_world");

    /**
     * Key for {@code minecraft:player_attack}.
     */
    public static final TypedKey<DamageType> PLAYER_ATTACK = create("player_attack");

    /**
     * Key for {@code minecraft:player_explosion}.
     */
    public static final TypedKey<DamageType> PLAYER_EXPLOSION = create("player_explosion");

    /**
     * Key for {@code minecraft:sonic_boom}.
     */
    public static final TypedKey<DamageType> SONIC_BOOM = create("sonic_boom");

    /**
     * Key for {@code minecraft:spear}.
     */
    public static final TypedKey<DamageType> SPEAR = create("spear");

    /**
     * Key for {@code minecraft:spit}.
     */
    public static final TypedKey<DamageType> SPIT = create("spit");

    /**
     * Key for {@code minecraft:stalagmite}.
     */
    public static final TypedKey<DamageType> STALAGMITE = create("stalagmite");

    /**
     * Key for {@code minecraft:starve}.
     */
    public static final TypedKey<DamageType> STARVE = create("starve");

    /**
     * Key for {@code minecraft:sting}.
     */
    public static final TypedKey<DamageType> STING = create("sting");

    /**
     * Key for {@code minecraft:sulfur_cube_hot}.
     */
    public static final TypedKey<DamageType> SULFUR_CUBE_HOT = create("sulfur_cube_hot");

    /**
     * Key for {@code minecraft:sweet_berry_bush}.
     */
    public static final TypedKey<DamageType> SWEET_BERRY_BUSH = create("sweet_berry_bush");

    /**
     * Key for {@code minecraft:thorns}.
     */
    public static final TypedKey<DamageType> THORNS = create("thorns");

    /**
     * Key for {@code minecraft:thrown}.
     */
    public static final TypedKey<DamageType> THROWN = create("thrown");

    /**
     * Key for {@code minecraft:trident}.
     */
    public static final TypedKey<DamageType> TRIDENT = create("trident");

    /**
     * Key for {@code minecraft:unattributed_fireball}.
     */
    public static final TypedKey<DamageType> UNATTRIBUTED_FIREBALL = create("unattributed_fireball");

    /**
     * Key for {@code minecraft:wind_charge}.
     */
    public static final TypedKey<DamageType> WIND_CHARGE = create("wind_charge");

    /**
     * Key for {@code minecraft:wither}.
     */
    public static final TypedKey<DamageType> WITHER = create("wither");

    /**
     * Key for {@code minecraft:wither_skull}.
     */
    public static final TypedKey<DamageType> WITHER_SKULL = create("wither_skull");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<DamageType>> VALUES = List.of(
        ARROW,
        BAD_RESPAWN_POINT,
        CACTUS,
        CAMPFIRE,
        CRAMMING,
        DRAGON_BREATH,
        DROWN,
        DRY_OUT,
        ENDER_PEARL,
        EXPLOSION,
        FALL,
        FALLING_ANVIL,
        FALLING_BLOCK,
        FALLING_STALACTITE,
        FIREBALL,
        FIREWORKS,
        FLY_INTO_WALL,
        FREEZE,
        GENERIC,
        GENERIC_KILL,
        HOT_FLOOR,
        IN_FIRE,
        IN_WALL,
        INDIRECT_MAGIC,
        LAVA,
        LIGHTNING_BOLT,
        MACE_SMASH,
        MAGIC,
        MOB_ATTACK,
        MOB_ATTACK_NO_AGGRO,
        MOB_PROJECTILE,
        ON_FIRE,
        OUT_OF_WORLD,
        OUTSIDE_BORDER,
        PLAYER_ATTACK,
        PLAYER_EXPLOSION,
        SONIC_BOOM,
        SPEAR,
        SPIT,
        STALAGMITE,
        STARVE,
        STING,
        SULFUR_CUBE_HOT,
        SWEET_BERRY_BUSH,
        THORNS,
        THROWN,
        TRIDENT,
        UNATTRIBUTED_FIREBALL,
        WIND_CHARGE,
        WITHER,
        WITHER_SKULL
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("always_hurts_ender_dragons"), List.of(Key.key("bad_respawn_point"), Key.key("explosion"), Key.key("fireworks"), Key.key("player_explosion"))),
        Map.entry(Key.key("always_kills_armor_stands"), List.of(Key.key("arrow"), Key.key("fireball"), Key.key("trident"), Key.key("wind_charge"), Key.key("wither_skull"))),
        Map.entry(Key.key("always_most_significant_fall"), List.of(Key.key("out_of_world"))),
        Map.entry(Key.key("always_triggers_silverfish"), List.of(Key.key("magic"))),
        Map.entry(Key.key("avoids_guardian_thorns"), List.of(Key.key("bad_respawn_point"), Key.key("explosion"), Key.key("fireworks"), Key.key("magic"), Key.key("player_explosion"), Key.key("thorns"))),
        Map.entry(Key.key("burn_from_stepping"), List.of(Key.key("campfire"), Key.key("hot_floor"), Key.key("sulfur_cube_hot"))),
        Map.entry(Key.key("burns_armor_stands"), List.of(Key.key("on_fire"))),
        Map.entry(Key.key("bypasses_armor"), List.of(Key.key("cramming"), Key.key("dragon_breath"), Key.key("drown"), Key.key("ender_pearl"), Key.key("fall"), Key.key("fly_into_wall"), Key.key("freeze"), Key.key("generic"), Key.key("generic_kill"), Key.key("in_wall"), Key.key("indirect_magic"), Key.key("magic"), Key.key("on_fire"), Key.key("out_of_world"), Key.key("outside_border"), Key.key("sonic_boom"), Key.key("stalagmite"), Key.key("starve"), Key.key("wither"))),
        Map.entry(Key.key("bypasses_effects"), List.of(Key.key("starve"))),
        Map.entry(Key.key("bypasses_enchantments"), List.of(Key.key("sonic_boom"))),
        Map.entry(Key.key("bypasses_invulnerability"), List.of(Key.key("generic_kill"), Key.key("out_of_world"))),
        Map.entry(Key.key("bypasses_resistance"), List.of(Key.key("generic_kill"), Key.key("out_of_world"))),
        Map.entry(Key.key("bypasses_shield"), List.of(Key.key("cactus"), Key.key("campfire"), Key.key("cramming"), Key.key("dragon_breath"), Key.key("drown"), Key.key("dry_out"), Key.key("ender_pearl"), Key.key("fall"), Key.key("falling_anvil"), Key.key("falling_stalactite"), Key.key("fly_into_wall"), Key.key("freeze"), Key.key("generic"), Key.key("generic_kill"), Key.key("hot_floor"), Key.key("in_fire"), Key.key("in_wall"), Key.key("indirect_magic"), Key.key("lava"), Key.key("lightning_bolt"), Key.key("magic"), Key.key("on_fire"), Key.key("out_of_world"), Key.key("outside_border"), Key.key("sonic_boom"), Key.key("stalagmite"), Key.key("starve"), Key.key("sulfur_cube_hot"), Key.key("sweet_berry_bush"), Key.key("wither"))),
        Map.entry(Key.key("bypasses_wolf_armor"), List.of(Key.key("cramming"), Key.key("drown"), Key.key("dry_out"), Key.key("freeze"), Key.key("generic_kill"), Key.key("in_wall"), Key.key("indirect_magic"), Key.key("magic"), Key.key("out_of_world"), Key.key("outside_border"), Key.key("starve"), Key.key("thorns"), Key.key("wither"))),
        Map.entry(Key.key("can_break_armor_stand"), List.of(Key.key("mace_smash"), Key.key("player_attack"), Key.key("player_explosion"), Key.key("spear"))),
        Map.entry(Key.key("damages_helmet"), List.of(Key.key("falling_anvil"), Key.key("falling_block"), Key.key("falling_stalactite"))),
        Map.entry(Key.key("ignites_armor_stands"), List.of(Key.key("campfire"), Key.key("in_fire"))),
        Map.entry(Key.key("is_drowning"), List.of(Key.key("drown"))),
        Map.entry(Key.key("is_explosion"), List.of(Key.key("bad_respawn_point"), Key.key("explosion"), Key.key("fireworks"), Key.key("player_explosion"))),
        Map.entry(Key.key("is_fall"), List.of(Key.key("ender_pearl"), Key.key("fall"), Key.key("stalagmite"))),
        Map.entry(Key.key("is_fire"), List.of(Key.key("campfire"), Key.key("fireball"), Key.key("hot_floor"), Key.key("in_fire"), Key.key("lava"), Key.key("on_fire"), Key.key("sulfur_cube_hot"), Key.key("unattributed_fireball"))),
        Map.entry(Key.key("is_freezing"), List.of(Key.key("freeze"))),
        Map.entry(Key.key("is_lightning"), List.of(Key.key("lightning_bolt"))),
        Map.entry(Key.key("is_player_attack"), List.of(Key.key("mace_smash"), Key.key("player_attack"), Key.key("spear"))),
        Map.entry(Key.key("is_projectile"), List.of(Key.key("arrow"), Key.key("fireball"), Key.key("mob_projectile"), Key.key("thrown"), Key.key("trident"), Key.key("unattributed_fireball"), Key.key("wind_charge"), Key.key("wither_skull"))),
        Map.entry(Key.key("mace_smash"), List.of(Key.key("mace_smash"))),
        Map.entry(Key.key("no_anger"), List.of(Key.key("mob_attack_no_aggro"))),
        Map.entry(Key.key("no_impact"), List.of(Key.key("drown"))),
        Map.entry(Key.key("no_knockback"), List.of(Key.key("bad_respawn_point"), Key.key("cactus"), Key.key("campfire"), Key.key("cramming"), Key.key("dragon_breath"), Key.key("drown"), Key.key("dry_out"), Key.key("ender_pearl"), Key.key("explosion"), Key.key("fall"), Key.key("fly_into_wall"), Key.key("freeze"), Key.key("generic"), Key.key("generic_kill"), Key.key("hot_floor"), Key.key("in_fire"), Key.key("in_wall"), Key.key("lava"), Key.key("lightning_bolt"), Key.key("magic"), Key.key("on_fire"), Key.key("out_of_world"), Key.key("outside_border"), Key.key("player_explosion"), Key.key("spear"), Key.key("stalagmite"), Key.key("starve"), Key.key("sulfur_cube_hot"), Key.key("sweet_berry_bush"), Key.key("wither"))),
        Map.entry(Key.key("panic_causes"), List.of(Key.key("arrow"), Key.key("cactus"), Key.key("dragon_breath"), Key.key("explosion"), Key.key("fireball"), Key.key("fireworks"), Key.key("freeze"), Key.key("hot_floor"), Key.key("in_fire"), Key.key("indirect_magic"), Key.key("lava"), Key.key("lightning_bolt"), Key.key("mace_smash"), Key.key("magic"), Key.key("mob_attack"), Key.key("mob_projectile"), Key.key("on_fire"), Key.key("player_attack"), Key.key("player_explosion"), Key.key("sonic_boom"), Key.key("spear"), Key.key("sting"), Key.key("sulfur_cube_hot"), Key.key("thrown"), Key.key("trident"), Key.key("unattributed_fireball"), Key.key("wind_charge"), Key.key("wither"), Key.key("wither_skull"))),
        Map.entry(Key.key("panic_environmental_causes"), List.of(Key.key("cactus"), Key.key("freeze"), Key.key("hot_floor"), Key.key("in_fire"), Key.key("lava"), Key.key("lightning_bolt"), Key.key("on_fire"), Key.key("sulfur_cube_hot"))),
        Map.entry(Key.key("sulfur_cube_with_block_immune_to"), List.of(Key.key("arrow"), Key.key("bad_respawn_point"), Key.key("cactus"), Key.key("dry_out"), Key.key("explosion"), Key.key("fall"), Key.key("falling_anvil"), Key.key("falling_block"), Key.key("falling_stalactite"), Key.key("fireworks"), Key.key("freeze"), Key.key("hot_floor"), Key.key("mace_smash"), Key.key("mob_attack"), Key.key("mob_attack_no_aggro"), Key.key("mob_projectile"), Key.key("player_attack"), Key.key("player_explosion"), Key.key("spear"), Key.key("spit"), Key.key("stalagmite"), Key.key("sting"), Key.key("sulfur_cube_hot"), Key.key("sweet_berry_bush"), Key.key("thrown"), Key.key("trident"), Key.key("wind_charge"))),
        Map.entry(Key.key("witch_resistant_to"), List.of(Key.key("indirect_magic"), Key.key("magic"), Key.key("sonic_boom"), Key.key("thorns"))),
        Map.entry(Key.key("wither_immune_to"), List.of(Key.key("drown")))
    );

    private DamageTypeKeys() {
        throw new UnsupportedOperationException("DamageTypeKeys cannot be instantiated.");
    }

    private static TypedKey<DamageType> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.DAMAGE_TYPE, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<DamageType>> values() {
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
