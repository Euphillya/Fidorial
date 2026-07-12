// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry;

import fr.euphyllia.fidorial.api.registry.data.*;

import java.util.Objects;

/**
 * Identifies a Minecraft registry (e.g. {@code minecraft:worldgen/biome}).
 * The type parameter {@code T} is a phantom marker binding a registry to the
 * kind of value it holds, so a {@link TypedKey} cannot be pointed at the wrong
 * registry at compile time.
 *
 */
public record RegistryKey<T>(Key key) {

    public RegistryKey {
        Objects.requireNonNull(key, "key");
    }

    private static <T> RegistryKey<T> of(String path) {
        return new RegistryKey<>(Key.minecraft(path));
    }

    @Override
    public String toString() {
        return "RegistryKey[" + key + "]";
    }

    public static final RegistryKey<Attribute> ATTRIBUTE = of("attribute");
    public static final RegistryKey<BannerPattern> BANNER_PATTERN = of("banner_pattern");
    public static final RegistryKey<Biome> BIOME = of("worldgen/biome");
    public static final RegistryKey<CatSoundVariant> CAT_SOUND_VARIANT = of("cat_sound_variant");
    public static final RegistryKey<CatVariant> CAT_VARIANT = of("cat_variant");
    public static final RegistryKey<ChatType> CHAT_TYPE = of("chat_type");
    public static final RegistryKey<ChickenSoundVariant> CHICKEN_SOUND_VARIANT = of("chicken_sound_variant");
    public static final RegistryKey<ChickenVariant> CHICKEN_VARIANT = of("chicken_variant");
    public static final RegistryKey<CowSoundVariant> COW_SOUND_VARIANT = of("cow_sound_variant");
    public static final RegistryKey<CowVariant> COW_VARIANT = of("cow_variant");
    public static final RegistryKey<DamageType> DAMAGE_TYPE = of("damage_type");
    public static final RegistryKey<Dialog> DIALOG = of("dialog");
    public static final RegistryKey<DimensionType> DIMENSION_TYPE = of("dimension_type");
    public static final RegistryKey<Enchantment> ENCHANTMENT = of("enchantment");
    public static final RegistryKey<FrogVariant> FROG_VARIANT = of("frog_variant");
    public static final RegistryKey<Instrument> INSTRUMENT = of("instrument");
    public static final RegistryKey<Item> ITEM = of("item");
    public static final RegistryKey<JukeboxSong> JUKEBOX_SONG = of("jukebox_song");
    public static final RegistryKey<PaintingVariant> PAINTING_VARIANT = of("painting_variant");
    public static final RegistryKey<PigSoundVariant> PIG_SOUND_VARIANT = of("pig_sound_variant");
    public static final RegistryKey<PigVariant> PIG_VARIANT = of("pig_variant");
    public static final RegistryKey<Timeline> TIMELINE = of("timeline");
    public static final RegistryKey<TrimMaterial> TRIM_MATERIAL = of("trim_material");
    public static final RegistryKey<TrimPattern> TRIM_PATTERN = of("trim_pattern");
    public static final RegistryKey<WolfSoundVariant> WOLF_SOUND_VARIANT = of("wolf_sound_variant");
    public static final RegistryKey<WolfVariant> WOLF_VARIANT = of("wolf_variant");
    public static final RegistryKey<WorldClock> WORLD_CLOCK = of("world_clock");
    public static final RegistryKey<ZombieNautilusVariant> ZOMBIE_NAUTILUS_VARIANT = of("zombie_nautilus_variant");
}
