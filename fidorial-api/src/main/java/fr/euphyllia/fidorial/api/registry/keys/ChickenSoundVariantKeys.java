// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.ChickenSoundVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:chicken_sound_variant} registry.
 */
public final class ChickenSoundVariantKeys {

    public static final TypedKey<ChickenSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<ChickenSoundVariant> PICKY = create("picky");
    private ChickenSoundVariantKeys() {
    }

    private static TypedKey<ChickenSoundVariant> create(String value) {
        return TypedKey.create(RegistryKey.CHICKEN_SOUND_VARIANT, Key.minecraft(value));
    }
}
