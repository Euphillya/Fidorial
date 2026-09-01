package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Enchantment;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:enchantment} registry.
 */
public final class EnchantmentKeys {
    /**
     * Key for {@code minecraft:aqua_affinity}.
     */
    public static final TypedKey<Enchantment> AQUA_AFFINITY = create("aqua_affinity");

    /**
     * Key for {@code minecraft:bane_of_arthropods}.
     */
    public static final TypedKey<Enchantment> BANE_OF_ARTHROPODS = create("bane_of_arthropods");

    /**
     * Key for {@code minecraft:binding_curse}.
     */
    public static final TypedKey<Enchantment> BINDING_CURSE = create("binding_curse");

    /**
     * Key for {@code minecraft:blast_protection}.
     */
    public static final TypedKey<Enchantment> BLAST_PROTECTION = create("blast_protection");

    /**
     * Key for {@code minecraft:breach}.
     */
    public static final TypedKey<Enchantment> BREACH = create("breach");

    /**
     * Key for {@code minecraft:channeling}.
     */
    public static final TypedKey<Enchantment> CHANNELING = create("channeling");

    /**
     * Key for {@code minecraft:density}.
     */
    public static final TypedKey<Enchantment> DENSITY = create("density");

    /**
     * Key for {@code minecraft:depth_strider}.
     */
    public static final TypedKey<Enchantment> DEPTH_STRIDER = create("depth_strider");

    /**
     * Key for {@code minecraft:efficiency}.
     */
    public static final TypedKey<Enchantment> EFFICIENCY = create("efficiency");

    /**
     * Key for {@code minecraft:feather_falling}.
     */
    public static final TypedKey<Enchantment> FEATHER_FALLING = create("feather_falling");

    /**
     * Key for {@code minecraft:fire_aspect}.
     */
    public static final TypedKey<Enchantment> FIRE_ASPECT = create("fire_aspect");

    /**
     * Key for {@code minecraft:fire_protection}.
     */
    public static final TypedKey<Enchantment> FIRE_PROTECTION = create("fire_protection");

    /**
     * Key for {@code minecraft:flame}.
     */
    public static final TypedKey<Enchantment> FLAME = create("flame");

    /**
     * Key for {@code minecraft:fortune}.
     */
    public static final TypedKey<Enchantment> FORTUNE = create("fortune");

    /**
     * Key for {@code minecraft:frost_walker}.
     */
    public static final TypedKey<Enchantment> FROST_WALKER = create("frost_walker");

    /**
     * Key for {@code minecraft:impaling}.
     */
    public static final TypedKey<Enchantment> IMPALING = create("impaling");

    /**
     * Key for {@code minecraft:infinity}.
     */
    public static final TypedKey<Enchantment> INFINITY = create("infinity");

    /**
     * Key for {@code minecraft:knockback}.
     */
    public static final TypedKey<Enchantment> KNOCKBACK = create("knockback");

    /**
     * Key for {@code minecraft:looting}.
     */
    public static final TypedKey<Enchantment> LOOTING = create("looting");

    /**
     * Key for {@code minecraft:loyalty}.
     */
    public static final TypedKey<Enchantment> LOYALTY = create("loyalty");

    /**
     * Key for {@code minecraft:luck_of_the_sea}.
     */
    public static final TypedKey<Enchantment> LUCK_OF_THE_SEA = create("luck_of_the_sea");

    /**
     * Key for {@code minecraft:lunge}.
     */
    public static final TypedKey<Enchantment> LUNGE = create("lunge");

    /**
     * Key for {@code minecraft:lure}.
     */
    public static final TypedKey<Enchantment> LURE = create("lure");

    /**
     * Key for {@code minecraft:mending}.
     */
    public static final TypedKey<Enchantment> MENDING = create("mending");

    /**
     * Key for {@code minecraft:multishot}.
     */
    public static final TypedKey<Enchantment> MULTISHOT = create("multishot");

    /**
     * Key for {@code minecraft:piercing}.
     */
    public static final TypedKey<Enchantment> PIERCING = create("piercing");

    /**
     * Key for {@code minecraft:power}.
     */
    public static final TypedKey<Enchantment> POWER = create("power");

    /**
     * Key for {@code minecraft:projectile_protection}.
     */
    public static final TypedKey<Enchantment> PROJECTILE_PROTECTION = create("projectile_protection");

    /**
     * Key for {@code minecraft:protection}.
     */
    public static final TypedKey<Enchantment> PROTECTION = create("protection");

    /**
     * Key for {@code minecraft:punch}.
     */
    public static final TypedKey<Enchantment> PUNCH = create("punch");

    /**
     * Key for {@code minecraft:quick_charge}.
     */
    public static final TypedKey<Enchantment> QUICK_CHARGE = create("quick_charge");

    /**
     * Key for {@code minecraft:respiration}.
     */
    public static final TypedKey<Enchantment> RESPIRATION = create("respiration");

    /**
     * Key for {@code minecraft:riptide}.
     */
    public static final TypedKey<Enchantment> RIPTIDE = create("riptide");

    /**
     * Key for {@code minecraft:sharpness}.
     */
    public static final TypedKey<Enchantment> SHARPNESS = create("sharpness");

    /**
     * Key for {@code minecraft:silk_touch}.
     */
    public static final TypedKey<Enchantment> SILK_TOUCH = create("silk_touch");

    /**
     * Key for {@code minecraft:smite}.
     */
    public static final TypedKey<Enchantment> SMITE = create("smite");

    /**
     * Key for {@code minecraft:soul_speed}.
     */
    public static final TypedKey<Enchantment> SOUL_SPEED = create("soul_speed");

    /**
     * Key for {@code minecraft:sweeping_edge}.
     */
    public static final TypedKey<Enchantment> SWEEPING_EDGE = create("sweeping_edge");

    /**
     * Key for {@code minecraft:swift_sneak}.
     */
    public static final TypedKey<Enchantment> SWIFT_SNEAK = create("swift_sneak");

    /**
     * Key for {@code minecraft:thorns}.
     */
    public static final TypedKey<Enchantment> THORNS = create("thorns");

    /**
     * Key for {@code minecraft:unbreaking}.
     */
    public static final TypedKey<Enchantment> UNBREAKING = create("unbreaking");

    /**
     * Key for {@code minecraft:vanishing_curse}.
     */
    public static final TypedKey<Enchantment> VANISHING_CURSE = create("vanishing_curse");

    /**
     * Key for {@code minecraft:wind_burst}.
     */
    public static final TypedKey<Enchantment> WIND_BURST = create("wind_burst");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<Enchantment>> VALUES = List.of(
        AQUA_AFFINITY,
        BANE_OF_ARTHROPODS,
        BINDING_CURSE,
        BLAST_PROTECTION,
        BREACH,
        CHANNELING,
        DENSITY,
        DEPTH_STRIDER,
        EFFICIENCY,
        FEATHER_FALLING,
        FIRE_ASPECT,
        FIRE_PROTECTION,
        FLAME,
        FORTUNE,
        FROST_WALKER,
        IMPALING,
        INFINITY,
        KNOCKBACK,
        LOOTING,
        LOYALTY,
        LUCK_OF_THE_SEA,
        LUNGE,
        LURE,
        MENDING,
        MULTISHOT,
        PIERCING,
        POWER,
        PROJECTILE_PROTECTION,
        PROTECTION,
        PUNCH,
        QUICK_CHARGE,
        RESPIRATION,
        RIPTIDE,
        SHARPNESS,
        SILK_TOUCH,
        SMITE,
        SOUL_SPEED,
        SWEEPING_EDGE,
        SWIFT_SNEAK,
        THORNS,
        UNBREAKING,
        VANISHING_CURSE,
        WIND_BURST
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("curse"), List.of(Key.key("binding_curse"), Key.key("vanishing_curse"))),
        Map.entry(Key.key("double_trade_price"), List.of(Key.key("binding_curse"), Key.key("frost_walker"), Key.key("mending"), Key.key("soul_speed"), Key.key("swift_sneak"), Key.key("vanishing_curse"), Key.key("wind_burst"))),
        Map.entry(Key.key("exclusive_set/armor"), List.of(Key.key("blast_protection"), Key.key("fire_protection"), Key.key("projectile_protection"), Key.key("protection"))),
        Map.entry(Key.key("exclusive_set/boots"), List.of(Key.key("depth_strider"), Key.key("frost_walker"))),
        Map.entry(Key.key("exclusive_set/bow"), List.of(Key.key("infinity"), Key.key("mending"))),
        Map.entry(Key.key("exclusive_set/crossbow"), List.of(Key.key("multishot"), Key.key("piercing"))),
        Map.entry(Key.key("exclusive_set/damage"), List.of(Key.key("bane_of_arthropods"), Key.key("breach"), Key.key("density"), Key.key("impaling"), Key.key("sharpness"), Key.key("smite"))),
        Map.entry(Key.key("exclusive_set/mining"), List.of(Key.key("fortune"), Key.key("silk_touch"))),
        Map.entry(Key.key("exclusive_set/riptide"), List.of(Key.key("channeling"), Key.key("loyalty"))),
        Map.entry(Key.key("in_enchanting_table"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"))),
        Map.entry(Key.key("non_treasure"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"))),
        Map.entry(Key.key("on_mob_spawn_equipment"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"))),
        Map.entry(Key.key("on_random_loot"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("binding_curse"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("frost_walker"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("mending"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"), Key.key("vanishing_curse"))),
        Map.entry(Key.key("on_traded_equipment"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"))),
        Map.entry(Key.key("prevents_bee_spawns_when_mining"), List.of(Key.key("silk_touch"))),
        Map.entry(Key.key("prevents_decorated_pot_shattering"), List.of(Key.key("silk_touch"))),
        Map.entry(Key.key("prevents_ice_melting"), List.of(Key.key("silk_touch"))),
        Map.entry(Key.key("prevents_infested_spawns"), List.of(Key.key("silk_touch"))),
        Map.entry(Key.key("smelts_loot"), List.of(Key.key("fire_aspect"))),
        Map.entry(Key.key("tooltip_order"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("binding_curse"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("frost_walker"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("mending"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("soul_speed"), Key.key("sweeping_edge"), Key.key("swift_sneak"), Key.key("thorns"), Key.key("unbreaking"), Key.key("vanishing_curse"), Key.key("wind_burst"))),
        Map.entry(Key.key("tradeable"), List.of(Key.key("aqua_affinity"), Key.key("bane_of_arthropods"), Key.key("binding_curse"), Key.key("blast_protection"), Key.key("breach"), Key.key("channeling"), Key.key("density"), Key.key("depth_strider"), Key.key("efficiency"), Key.key("feather_falling"), Key.key("fire_aspect"), Key.key("fire_protection"), Key.key("flame"), Key.key("fortune"), Key.key("frost_walker"), Key.key("impaling"), Key.key("infinity"), Key.key("knockback"), Key.key("looting"), Key.key("loyalty"), Key.key("luck_of_the_sea"), Key.key("lunge"), Key.key("lure"), Key.key("mending"), Key.key("multishot"), Key.key("piercing"), Key.key("power"), Key.key("projectile_protection"), Key.key("protection"), Key.key("punch"), Key.key("quick_charge"), Key.key("respiration"), Key.key("riptide"), Key.key("sharpness"), Key.key("silk_touch"), Key.key("smite"), Key.key("sweeping_edge"), Key.key("thorns"), Key.key("unbreaking"), Key.key("vanishing_curse"))),
        Map.entry(Key.key("treasure"), List.of(Key.key("binding_curse"), Key.key("frost_walker"), Key.key("mending"), Key.key("soul_speed"), Key.key("swift_sneak"), Key.key("vanishing_curse"), Key.key("wind_burst")))
    );

    private EnchantmentKeys() {
        throw new UnsupportedOperationException("EnchantmentKeys cannot be instantiated.");
    }

    private static TypedKey<Enchantment> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.ENCHANTMENT, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<Enchantment>> values() {
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
