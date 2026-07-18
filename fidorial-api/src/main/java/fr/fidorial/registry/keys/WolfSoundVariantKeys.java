package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.WolfSoundVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:wolf_sound_variant} registry.
 */
public final class WolfSoundVariantKeys {

    public static final TypedKey<WolfSoundVariant> ANGRY = create("angry");
    public static final TypedKey<WolfSoundVariant> BIG = create("big");
    public static final TypedKey<WolfSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<WolfSoundVariant> CUTE = create("cute");
    public static final TypedKey<WolfSoundVariant> GRUMPY = create("grumpy");
    public static final TypedKey<WolfSoundVariant> PUGLIN = create("puglin");
    public static final TypedKey<WolfSoundVariant> SAD = create("sad");

    private WolfSoundVariantKeys() {
    }

    private static TypedKey<WolfSoundVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.WOLF_SOUND_VARIANT, value);
    }
}
