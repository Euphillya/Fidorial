package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.PigSoundVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:pig_sound_variant} registry.
 */
public final class PigSoundVariantKeys {

    public static final TypedKey<PigSoundVariant> BIG = create("big");
    public static final TypedKey<PigSoundVariant> CLASSIC = create("classic");
    public static final TypedKey<PigSoundVariant> MINI = create("mini");

    private PigSoundVariantKeys() {
    }

    private static TypedKey<PigSoundVariant> create(String value) {
        return TypedKey.create(RegistryKey.PIG_SOUND_VARIANT, Key.minecraft(value));
    }
}
