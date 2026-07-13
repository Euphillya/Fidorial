package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.DamageType;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:damage_type} registry.
 */
public final class DamageTypeKeys {

    public static final TypedKey<DamageType> ARROW = create("arrow");
    public static final TypedKey<DamageType> BAD_RESPAWN_POINT = create("bad_respawn_point");
    public static final TypedKey<DamageType> CACTUS = create("cactus");
    public static final TypedKey<DamageType> CAMPFIRE = create("campfire");
    public static final TypedKey<DamageType> CRAMMING = create("cramming");
    public static final TypedKey<DamageType> DRAGON_BREATH = create("dragon_breath");
    public static final TypedKey<DamageType> DROWN = create("drown");
    public static final TypedKey<DamageType> DRY_OUT = create("dry_out");
    public static final TypedKey<DamageType> ENDER_PEARL = create("ender_pearl");
    public static final TypedKey<DamageType> EXPLOSION = create("explosion");
    public static final TypedKey<DamageType> FALL = create("fall");
    public static final TypedKey<DamageType> FALLING_ANVIL = create("falling_anvil");
    public static final TypedKey<DamageType> FALLING_BLOCK = create("falling_block");
    public static final TypedKey<DamageType> FALLING_STALACTITE = create("falling_stalactite");
    public static final TypedKey<DamageType> FIREBALL = create("fireball");
    public static final TypedKey<DamageType> FIREWORKS = create("fireworks");
    public static final TypedKey<DamageType> FLY_INTO_WALL = create("fly_into_wall");
    public static final TypedKey<DamageType> FREEZE = create("freeze");
    public static final TypedKey<DamageType> GENERIC = create("generic");
    public static final TypedKey<DamageType> GENERIC_KILL = create("generic_kill");
    public static final TypedKey<DamageType> HOT_FLOOR = create("hot_floor");
    public static final TypedKey<DamageType> IN_FIRE = create("in_fire");
    public static final TypedKey<DamageType> IN_WALL = create("in_wall");
    public static final TypedKey<DamageType> INDIRECT_MAGIC = create("indirect_magic");
    public static final TypedKey<DamageType> LAVA = create("lava");
    public static final TypedKey<DamageType> LIGHTNING_BOLT = create("lightning_bolt");
    public static final TypedKey<DamageType> MACE_SMASH = create("mace_smash");
    public static final TypedKey<DamageType> MAGIC = create("magic");
    public static final TypedKey<DamageType> MOB_ATTACK = create("mob_attack");
    public static final TypedKey<DamageType> MOB_ATTACK_NO_AGGRO = create("mob_attack_no_aggro");
    public static final TypedKey<DamageType> MOB_PROJECTILE = create("mob_projectile");
    public static final TypedKey<DamageType> ON_FIRE = create("on_fire");
    public static final TypedKey<DamageType> OUT_OF_WORLD = create("out_of_world");
    public static final TypedKey<DamageType> OUTSIDE_BORDER = create("outside_border");
    public static final TypedKey<DamageType> PLAYER_ATTACK = create("player_attack");
    public static final TypedKey<DamageType> PLAYER_EXPLOSION = create("player_explosion");
    public static final TypedKey<DamageType> SONIC_BOOM = create("sonic_boom");
    public static final TypedKey<DamageType> SPEAR = create("spear");
    public static final TypedKey<DamageType> SPIT = create("spit");
    public static final TypedKey<DamageType> STALAGMITE = create("stalagmite");
    public static final TypedKey<DamageType> STARVE = create("starve");
    public static final TypedKey<DamageType> STING = create("sting");
    public static final TypedKey<DamageType> SULFUR_CUBE_HOT = create("sulfur_cube_hot");
    public static final TypedKey<DamageType> SWEET_BERRY_BUSH = create("sweet_berry_bush");
    public static final TypedKey<DamageType> THORNS = create("thorns");
    public static final TypedKey<DamageType> THROWN = create("thrown");
    public static final TypedKey<DamageType> TRIDENT = create("trident");
    public static final TypedKey<DamageType> UNATTRIBUTED_FIREBALL = create("unattributed_fireball");
    public static final TypedKey<DamageType> WIND_CHARGE = create("wind_charge");
    public static final TypedKey<DamageType> WITHER = create("wither");
    public static final TypedKey<DamageType> WITHER_SKULL = create("wither_skull");

    private DamageTypeKeys() {
    }

    private static TypedKey<DamageType> create(String value) {
        return TypedKey.create(RegistryKey.DAMAGE_TYPE, Key.minecraft(value));
    }
}
