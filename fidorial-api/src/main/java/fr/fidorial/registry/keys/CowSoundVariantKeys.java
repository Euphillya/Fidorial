package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CowSoundVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cow_sound_variant} registry.
 */
public final class CowSoundVariantKeys {

    public static final TypedKey<CowSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<CowSoundVariant> MOODY = create("moody");

    private CowSoundVariantKeys() {
    }

    private static TypedKey<CowSoundVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.COW_SOUND_VARIANT, value);
    }
}
