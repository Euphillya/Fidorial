package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.CowVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cow_variant} registry.
 */
public final class CowVariantKeys {

    public static final TypedKey<CowVariant> COLD = create("cold");
    public static final TypedKey<CowVariant> TEMPERATE = create("temperate");
    public static final TypedKey<CowVariant> WARM = create("warm");

    private CowVariantKeys() {
    }

    private static TypedKey<CowVariant> create(String value) {
        return TypedKey.create(RegistryKey.COW_VARIANT, Key.minecraft(value));
    }
}
