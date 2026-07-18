package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CatSoundVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cat_sound_variant} registry.
 */
public final class CatSoundVariantKeys {

    public static final TypedKey<CatSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<CatSoundVariant> ROYAL = create("royal");

    private CatSoundVariantKeys() {
    }

    private static TypedKey<CatSoundVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.CAT_SOUND_VARIANT, value);
    }
}
