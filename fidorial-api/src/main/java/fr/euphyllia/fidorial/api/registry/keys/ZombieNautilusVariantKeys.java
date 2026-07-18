package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.ZombieNautilusVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:zombie_nautilus_variant} registry.
 */
public final class ZombieNautilusVariantKeys {

    public static final TypedKey<ZombieNautilusVariant> TEMPERATE = create("temperate");
    public static final TypedKey<ZombieNautilusVariant> WARM = create("warm");

    private ZombieNautilusVariantKeys() {
    }

    private static TypedKey<ZombieNautilusVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.ZOMBIE_NAUTILUS_VARIANT, value);
    }
}
