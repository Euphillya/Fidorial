package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.ZombieNautilusVariant;
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
