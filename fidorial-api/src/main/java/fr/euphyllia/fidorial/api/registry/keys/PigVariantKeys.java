// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.PigVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:pig_variant} registry.
 */
public final class PigVariantKeys {

    public static final TypedKey<PigVariant> COLD = create("cold");
    public static final TypedKey<PigVariant> TEMPERATE = create("temperate");
    public static final TypedKey<PigVariant> WARM = create("warm");
    private PigVariantKeys() {
    }

    private static TypedKey<PigVariant> create(String value) {
        return TypedKey.create(RegistryKey.PIG_VARIANT, Key.minecraft(value));
    }
}
