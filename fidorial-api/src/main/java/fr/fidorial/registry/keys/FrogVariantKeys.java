package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.FrogVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:frog_variant} registry.
 */
public final class FrogVariantKeys {

    public static final TypedKey<FrogVariant> COLD = create("cold");
    public static final TypedKey<FrogVariant> TEMPERATE = create("temperate");
    public static final TypedKey<FrogVariant> WARM = create("warm");

    private FrogVariantKeys() {
    }

    private static TypedKey<FrogVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.FROG_VARIANT, value);
    }
}
