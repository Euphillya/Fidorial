package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.PigVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:pig_variant} registry.
 */
public final class PigVariantKeys {

    public static final TypedKey<PigVariant> COLD = create("cold");
    public static final TypedKey<PigVariant> TEMPERATE = create("temperate");
    public static final TypedKey<PigVariant> WARM = create("warm");

    private PigVariantKeys() {
    }

    private static TypedKey<PigVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.PIG_VARIANT, value);
    }
}
