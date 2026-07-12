// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.ZombieNautilusVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:zombie_nautilus_variant} registry.
 */
public final class ZombieNautilusVariantKeys {

    private ZombieNautilusVariantKeys() {
    }

    public static final TypedKey<ZombieNautilusVariant> TEMPERATE = create("temperate");
    public static final TypedKey<ZombieNautilusVariant> WARM = create("warm");

    private static TypedKey<ZombieNautilusVariant> create(String value) {
        return TypedKey.create(RegistryKey.ZOMBIE_NAUTILUS_VARIANT, Key.minecraft(value));
    }
}
