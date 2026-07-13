package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.CatSoundVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cat_sound_variant} registry.
 */
public final class CatSoundVariantKeys {

    public static final TypedKey<CatSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<CatSoundVariant> ROYAL = create("royal");

    private CatSoundVariantKeys() {
    }

    private static TypedKey<CatSoundVariant> create(String value) {
        return TypedKey.create(RegistryKey.CAT_SOUND_VARIANT, Key.minecraft(value));
    }
}
