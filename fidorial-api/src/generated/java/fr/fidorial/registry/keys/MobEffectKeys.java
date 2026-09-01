package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.MobEffect;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:mob_effect} registry.
 */
public final class MobEffectKeys {
    /**
     * Key for {@code minecraft:absorption}.
     */
    public static final TypedKey<MobEffect> ABSORPTION = create("absorption");

    /**
     * Key for {@code minecraft:bad_omen}.
     */
    public static final TypedKey<MobEffect> BAD_OMEN = create("bad_omen");

    /**
     * Key for {@code minecraft:blindness}.
     */
    public static final TypedKey<MobEffect> BLINDNESS = create("blindness");

    /**
     * Key for {@code minecraft:breath_of_the_nautilus}.
     */
    public static final TypedKey<MobEffect> BREATH_OF_THE_NAUTILUS = create("breath_of_the_nautilus");

    /**
     * Key for {@code minecraft:conduit_power}.
     */
    public static final TypedKey<MobEffect> CONDUIT_POWER = create("conduit_power");

    /**
     * Key for {@code minecraft:darkness}.
     */
    public static final TypedKey<MobEffect> DARKNESS = create("darkness");

    /**
     * Key for {@code minecraft:dolphins_grace}.
     */
    public static final TypedKey<MobEffect> DOLPHINS_GRACE = create("dolphins_grace");

    /**
     * Key for {@code minecraft:fire_resistance}.
     */
    public static final TypedKey<MobEffect> FIRE_RESISTANCE = create("fire_resistance");

    /**
     * Key for {@code minecraft:glowing}.
     */
    public static final TypedKey<MobEffect> GLOWING = create("glowing");

    /**
     * Key for {@code minecraft:haste}.
     */
    public static final TypedKey<MobEffect> HASTE = create("haste");

    /**
     * Key for {@code minecraft:health_boost}.
     */
    public static final TypedKey<MobEffect> HEALTH_BOOST = create("health_boost");

    /**
     * Key for {@code minecraft:hero_of_the_village}.
     */
    public static final TypedKey<MobEffect> HERO_OF_THE_VILLAGE = create("hero_of_the_village");

    /**
     * Key for {@code minecraft:hunger}.
     */
    public static final TypedKey<MobEffect> HUNGER = create("hunger");

    /**
     * Key for {@code minecraft:infested}.
     */
    public static final TypedKey<MobEffect> INFESTED = create("infested");

    /**
     * Key for {@code minecraft:instant_damage}.
     */
    public static final TypedKey<MobEffect> INSTANT_DAMAGE = create("instant_damage");

    /**
     * Key for {@code minecraft:instant_health}.
     */
    public static final TypedKey<MobEffect> INSTANT_HEALTH = create("instant_health");

    /**
     * Key for {@code minecraft:invisibility}.
     */
    public static final TypedKey<MobEffect> INVISIBILITY = create("invisibility");

    /**
     * Key for {@code minecraft:jump_boost}.
     */
    public static final TypedKey<MobEffect> JUMP_BOOST = create("jump_boost");

    /**
     * Key for {@code minecraft:levitation}.
     */
    public static final TypedKey<MobEffect> LEVITATION = create("levitation");

    /**
     * Key for {@code minecraft:luck}.
     */
    public static final TypedKey<MobEffect> LUCK = create("luck");

    /**
     * Key for {@code minecraft:mining_fatigue}.
     */
    public static final TypedKey<MobEffect> MINING_FATIGUE = create("mining_fatigue");

    /**
     * Key for {@code minecraft:nausea}.
     */
    public static final TypedKey<MobEffect> NAUSEA = create("nausea");

    /**
     * Key for {@code minecraft:night_vision}.
     */
    public static final TypedKey<MobEffect> NIGHT_VISION = create("night_vision");

    /**
     * Key for {@code minecraft:oozing}.
     */
    public static final TypedKey<MobEffect> OOZING = create("oozing");

    /**
     * Key for {@code minecraft:poison}.
     */
    public static final TypedKey<MobEffect> POISON = create("poison");

    /**
     * Key for {@code minecraft:raid_omen}.
     */
    public static final TypedKey<MobEffect> RAID_OMEN = create("raid_omen");

    /**
     * Key for {@code minecraft:regeneration}.
     */
    public static final TypedKey<MobEffect> REGENERATION = create("regeneration");

    /**
     * Key for {@code minecraft:resistance}.
     */
    public static final TypedKey<MobEffect> RESISTANCE = create("resistance");

    /**
     * Key for {@code minecraft:saturation}.
     */
    public static final TypedKey<MobEffect> SATURATION = create("saturation");

    /**
     * Key for {@code minecraft:slowness}.
     */
    public static final TypedKey<MobEffect> SLOWNESS = create("slowness");

    /**
     * Key for {@code minecraft:slow_falling}.
     */
    public static final TypedKey<MobEffect> SLOW_FALLING = create("slow_falling");

    /**
     * Key for {@code minecraft:speed}.
     */
    public static final TypedKey<MobEffect> SPEED = create("speed");

    /**
     * Key for {@code minecraft:strength}.
     */
    public static final TypedKey<MobEffect> STRENGTH = create("strength");

    /**
     * Key for {@code minecraft:trial_omen}.
     */
    public static final TypedKey<MobEffect> TRIAL_OMEN = create("trial_omen");

    /**
     * Key for {@code minecraft:unluck}.
     */
    public static final TypedKey<MobEffect> UNLUCK = create("unluck");

    /**
     * Key for {@code minecraft:water_breathing}.
     */
    public static final TypedKey<MobEffect> WATER_BREATHING = create("water_breathing");

    /**
     * Key for {@code minecraft:weakness}.
     */
    public static final TypedKey<MobEffect> WEAKNESS = create("weakness");

    /**
     * Key for {@code minecraft:weaving}.
     */
    public static final TypedKey<MobEffect> WEAVING = create("weaving");

    /**
     * Key for {@code minecraft:wind_charged}.
     */
    public static final TypedKey<MobEffect> WIND_CHARGED = create("wind_charged");

    /**
     * Key for {@code minecraft:wither}.
     */
    public static final TypedKey<MobEffect> WITHER = create("wither");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<MobEffect>> VALUES = List.of(
        SPEED,
        SLOWNESS,
        HASTE,
        MINING_FATIGUE,
        STRENGTH,
        INSTANT_HEALTH,
        INSTANT_DAMAGE,
        JUMP_BOOST,
        NAUSEA,
        REGENERATION,
        RESISTANCE,
        FIRE_RESISTANCE,
        WATER_BREATHING,
        INVISIBILITY,
        BLINDNESS,
        NIGHT_VISION,
        HUNGER,
        WEAKNESS,
        POISON,
        WITHER,
        HEALTH_BOOST,
        ABSORPTION,
        SATURATION,
        GLOWING,
        LEVITATION,
        LUCK,
        UNLUCK,
        SLOW_FALLING,
        CONDUIT_POWER,
        DOLPHINS_GRACE,
        BAD_OMEN,
        HERO_OF_THE_VILLAGE,
        DARKNESS,
        TRIAL_OMEN,
        RAID_OMEN,
        WIND_CHARGED,
        WEAVING,
        OOZING,
        INFESTED,
        BREATH_OF_THE_NAUTILUS
    );

    private MobEffectKeys() {
        throw new UnsupportedOperationException("MobEffectKeys cannot be instantiated.");
    }

    private static TypedKey<MobEffect> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.MOB_EFFECT, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<MobEffect>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return Map.of();
    }
}
