package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.PigSoundVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:pig_sound_variant} registry.
 */
public final class PigSoundVariantKeys {

    public static final TypedKey<PigSoundVariant> BIG = create("big");
    public static final TypedKey<PigSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<PigSoundVariant> MINI = create("mini");

    private PigSoundVariantKeys() {
    }

    private static TypedKey<PigSoundVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.PIG_SOUND_VARIANT, value);
    }
}
