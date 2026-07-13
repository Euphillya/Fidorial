package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.Enchantment;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:enchantment} registry.
 */
public final class EnchantmentKeys {

    public static final TypedKey<Enchantment> AQUA_AFFINITY = create("aqua_affinity");
    public static final TypedKey<Enchantment> BANE_OF_ARTHROPODS = create("bane_of_arthropods");
    public static final TypedKey<Enchantment> BINDING_CURSE = create("binding_curse");
    public static final TypedKey<Enchantment> BLAST_PROTECTION = create("blast_protection");
    public static final TypedKey<Enchantment> BREACH = create("breach");
    public static final TypedKey<Enchantment> CHANNELING = create("channeling");
    public static final TypedKey<Enchantment> DENSITY = create("density");
    public static final TypedKey<Enchantment> DEPTH_STRIDER = create("depth_strider");
    public static final TypedKey<Enchantment> EFFICIENCY = create("efficiency");
    public static final TypedKey<Enchantment> FEATHER_FALLING = create("feather_falling");
    public static final TypedKey<Enchantment> FIRE_ASPECT = create("fire_aspect");
    public static final TypedKey<Enchantment> FIRE_PROTECTION = create("fire_protection");
    public static final TypedKey<Enchantment> FLAME = create("flame");
    public static final TypedKey<Enchantment> FORTUNE = create("fortune");
    public static final TypedKey<Enchantment> FROST_WALKER = create("frost_walker");
    public static final TypedKey<Enchantment> IMPALING = create("impaling");
    public static final TypedKey<Enchantment> INFINITY = create("infinity");
    public static final TypedKey<Enchantment> KNOCKBACK = create("knockback");
    public static final TypedKey<Enchantment> LOOTING = create("looting");
    public static final TypedKey<Enchantment> LOYALTY = create("loyalty");
    public static final TypedKey<Enchantment> LUCK_OF_THE_SEA = create("luck_of_the_sea");
    public static final TypedKey<Enchantment> LUNGE = create("lunge");
    public static final TypedKey<Enchantment> LURE = create("lure");
    public static final TypedKey<Enchantment> MENDING = create("mending");
    public static final TypedKey<Enchantment> MULTISHOT = create("multishot");
    public static final TypedKey<Enchantment> PIERCING = create("piercing");
    public static final TypedKey<Enchantment> POWER = create("power");
    public static final TypedKey<Enchantment> PROJECTILE_PROTECTION = create("projectile_protection");
    public static final TypedKey<Enchantment> PROTECTION = create("protection");
    public static final TypedKey<Enchantment> PUNCH = create("punch");
    public static final TypedKey<Enchantment> QUICK_CHARGE = create("quick_charge");
    public static final TypedKey<Enchantment> RESPIRATION = create("respiration");
    public static final TypedKey<Enchantment> RIPTIDE = create("riptide");
    public static final TypedKey<Enchantment> SHARPNESS = create("sharpness");
    public static final TypedKey<Enchantment> SILK_TOUCH = create("silk_touch");
    public static final TypedKey<Enchantment> SMITE = create("smite");
    public static final TypedKey<Enchantment> SOUL_SPEED = create("soul_speed");
    public static final TypedKey<Enchantment> SWEEPING_EDGE = create("sweeping_edge");
    public static final TypedKey<Enchantment> SWIFT_SNEAK = create("swift_sneak");
    public static final TypedKey<Enchantment> THORNS = create("thorns");
    public static final TypedKey<Enchantment> UNBREAKING = create("unbreaking");
    public static final TypedKey<Enchantment> VANISHING_CURSE = create("vanishing_curse");
    public static final TypedKey<Enchantment> WIND_BURST = create("wind_burst");

    private EnchantmentKeys() {
    }

    private static TypedKey<Enchantment> create(String value) {
        return TypedKey.create(RegistryKey.ENCHANTMENT, Key.minecraft(value));
    }
}
