package fr.fidorial.registry;

import fr.fidorial.entity.EntityType;
import fr.fidorial.registry.data.Attribute;
import fr.fidorial.registry.data.BannerPattern;
import fr.fidorial.registry.data.Biome;
import fr.fidorial.registry.data.CatSoundVariant;
import fr.fidorial.registry.data.CatVariant;
import fr.fidorial.registry.data.ChatType;
import fr.fidorial.registry.data.ChickenSoundVariant;
import fr.fidorial.registry.data.ChickenVariant;
import fr.fidorial.registry.data.CowSoundVariant;
import fr.fidorial.registry.data.CowVariant;
import fr.fidorial.registry.data.DamageType;
import fr.fidorial.registry.data.Dialog;
import fr.fidorial.registry.data.DimensionType;
import fr.fidorial.registry.data.Enchantment;
import fr.fidorial.registry.data.FrogVariant;
import fr.fidorial.registry.data.Instrument;
import fr.fidorial.registry.data.Item;
import fr.fidorial.registry.data.JukeboxSong;
import fr.fidorial.registry.data.PaintingVariant;
import fr.fidorial.registry.data.PigSoundVariant;
import fr.fidorial.registry.data.PigVariant;
import fr.fidorial.registry.data.Timeline;
import fr.fidorial.registry.data.TrimMaterial;
import fr.fidorial.registry.data.TrimPattern;
import fr.fidorial.registry.data.WolfSoundVariant;
import fr.fidorial.registry.data.WolfVariant;
import fr.fidorial.registry.data.WorldClock;
import fr.fidorial.registry.data.ZombieNautilusVariant;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import java.util.Objects;

public record RegistryKey<T>(Key key) {

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
    public static final RegistryKey<EntityType> ENTITY_TYPE = of("entity_type");

    public RegistryKey {
        Objects.requireNonNull(key, "key");
    }

    public static <T> RegistryKey<T> of(@KeyPattern String path) {
        return new RegistryKey<>(Key.key(path));
    }

    public static <T> RegistryKey<T> of(Key key) {
        return new RegistryKey<>(key);
    }

    @Override
    public String toString() {
        return "RegistryKey[" + key + "]";
    }
}
