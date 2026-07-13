// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.FrogVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:frog_variant} registry.
 */
public final class FrogVariantKeys {

    public static final TypedKey<FrogVariant> COLD = create("cold");
    public static final TypedKey<FrogVariant> TEMPERATE = create("temperate");
    public static final TypedKey<FrogVariant> WARM = create("warm");
    private FrogVariantKeys() {
    }

    private static TypedKey<FrogVariant> create(String value) {
        return TypedKey.create(RegistryKey.FROG_VARIANT, Key.minecraft(value));
    }
}
