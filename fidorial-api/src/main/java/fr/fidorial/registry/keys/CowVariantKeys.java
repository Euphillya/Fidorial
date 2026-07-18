package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CowVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cow_variant} registry.
 */
public final class CowVariantKeys {

    public static final TypedKey<CowVariant> COLD = create("cold");
    public static final TypedKey<CowVariant> TEMPERATE = create("temperate");
    public static final TypedKey<CowVariant> WARM = create("warm");

    private CowVariantKeys() {
    }

    private static TypedKey<CowVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.COW_VARIANT, value);
    }
}
