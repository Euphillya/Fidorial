package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.TrimPattern;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:trim_pattern} registry.
 */
public final class TrimPatternKeys {

    public static final TypedKey<TrimPattern> BOLT = create("bolt");
    public static final TypedKey<TrimPattern> COAST = create("coast");
    public static final TypedKey<TrimPattern> DUNE = create("dune");
    public static final TypedKey<TrimPattern> EYE = create("eye");
    public static final TypedKey<TrimPattern> FLOW = create("flow");
    public static final TypedKey<TrimPattern> HOST = create("host");
    public static final TypedKey<TrimPattern> RAISER = create("raiser");
    public static final TypedKey<TrimPattern> RIB = create("rib");
    public static final TypedKey<TrimPattern> SENTRY = create("sentry");
    public static final TypedKey<TrimPattern> SHAPER = create("shaper");
    public static final TypedKey<TrimPattern> SILENCE = create("silence");
    public static final TypedKey<TrimPattern> SNOUT = create("snout");
    public static final TypedKey<TrimPattern> SPIRE = create("spire");
    public static final TypedKey<TrimPattern> TIDE = create("tide");
    public static final TypedKey<TrimPattern> VEX = create("vex");
    public static final TypedKey<TrimPattern> WARD = create("ward");
    public static final TypedKey<TrimPattern> WAYFINDER = create("wayfinder");
    public static final TypedKey<TrimPattern> WILD = create("wild");

    private TrimPatternKeys() {
    }

    private static TypedKey<TrimPattern> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.TRIM_PATTERN, value);
    }
}
