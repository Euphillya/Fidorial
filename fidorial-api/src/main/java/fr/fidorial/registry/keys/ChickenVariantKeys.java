package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.ChickenVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:chicken_variant} registry.
 */
public final class ChickenVariantKeys {

    public static final TypedKey<ChickenVariant> COLD = create("cold");
    public static final TypedKey<ChickenVariant> TEMPERATE = create("temperate");
    public static final TypedKey<ChickenVariant> WARM = create("warm");

    private ChickenVariantKeys() {
    }

    private static TypedKey<ChickenVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.CHICKEN_VARIANT, value);
    }
}
