package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.ChickenSoundVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:chicken_sound_variant} registry.
 */
public final class ChickenSoundVariantKeys {

    public static final TypedKey<ChickenSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<ChickenSoundVariant> PICKY = create("picky");

    private ChickenSoundVariantKeys() {
    }

    private static TypedKey<ChickenSoundVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.CHICKEN_SOUND_VARIANT, value);
    }
}
