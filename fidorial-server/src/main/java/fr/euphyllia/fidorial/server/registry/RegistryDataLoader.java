package fr.euphyllia.fidorial.server.registry;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.keys.BannerPatternKeys;
import fr.fidorial.registry.keys.BiomeKeys;
import fr.fidorial.registry.keys.CatSoundVariantKeys;
import fr.fidorial.registry.keys.CatVariantKeys;
import fr.fidorial.registry.keys.ChatTypeKeys;
import fr.fidorial.registry.keys.ChickenSoundVariantKeys;
import fr.fidorial.registry.keys.ChickenVariantKeys;
import fr.fidorial.registry.keys.CowSoundVariantKeys;
import fr.fidorial.registry.keys.CowVariantKeys;
import fr.fidorial.registry.keys.DamageTypeKeys;
import fr.fidorial.registry.keys.DialogKeys;
import fr.fidorial.registry.keys.DimensionTypeKeys;
import fr.fidorial.registry.keys.EnchantmentKeys;
import fr.fidorial.registry.keys.FrogVariantKeys;
import fr.fidorial.registry.keys.InstrumentKeys;
import fr.fidorial.registry.keys.ItemKeys;
import fr.fidorial.registry.keys.JukeboxSongKeys;
import fr.fidorial.registry.keys.PaintingVariantKeys;
import fr.fidorial.registry.keys.PigSoundVariantKeys;
import fr.fidorial.registry.keys.PigVariantKeys;
import fr.fidorial.registry.keys.TimelineKeys;
import fr.fidorial.registry.keys.TrimMaterialKeys;
import fr.fidorial.registry.keys.TrimPatternKeys;
import fr.fidorial.registry.keys.WolfSoundVariantKeys;
import fr.fidorial.registry.keys.WolfVariantKeys;
import fr.fidorial.registry.keys.WorldClockKeys;
import fr.fidorial.registry.keys.ZombieNautilusVariantKeys;
import net.kyori.adventure.key.Key;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class RegistryDataLoader {

    private final Map<Key, Registry> dynamic = new LinkedHashMap<>();
    private final Map<Key, Registry> frozen = new LinkedHashMap<>();

    private RegistryDataLoader() {
    }

    static RegistryDataLoader load() {

        final RegistryDataLoader loader = new RegistryDataLoader();

        /*
         * Frozen: hardcoded client-side registries. Order is the network ID -
         * comes straight from ItemKeys.values(), do not touch.
         */
        loader.putFrozen(RegistryKey.ITEM, ItemKeys.values(), ItemKeys.tags());

        /*
         * Dynamic: registries synced via the configuration-phase registry data
         * packets.
         */
        loader.putDynamic(RegistryKey.BANNER_PATTERN, BannerPatternKeys.values(), BannerPatternKeys.tags());
        loader.putDynamic(RegistryKey.BIOME, BiomeKeys.values(), BiomeKeys.tags());
        loader.putDynamic(RegistryKey.CAT_SOUND_VARIANT, CatSoundVariantKeys.values(), CatSoundVariantKeys.tags());
        loader.putDynamic(RegistryKey.CAT_VARIANT, CatVariantKeys.values(), CatVariantKeys.tags());
        loader.putDynamic(RegistryKey.CHAT_TYPE, ChatTypeKeys.values(), ChatTypeKeys.tags());
        loader.putDynamic(RegistryKey.CHICKEN_SOUND_VARIANT, ChickenSoundVariantKeys.values(), ChickenSoundVariantKeys.tags());
        loader.putDynamic(RegistryKey.CHICKEN_VARIANT, ChickenVariantKeys.values(), ChickenVariantKeys.tags());
        loader.putDynamic(RegistryKey.COW_SOUND_VARIANT, CowSoundVariantKeys.values(), CowSoundVariantKeys.tags());
        loader.putDynamic(RegistryKey.COW_VARIANT, CowVariantKeys.values(), CowVariantKeys.tags());
        loader.putDynamic(RegistryKey.DAMAGE_TYPE, DamageTypeKeys.values(), DamageTypeKeys.tags());
        loader.putDynamic(RegistryKey.DIALOG, DialogKeys.values(), DialogKeys.tags());
        loader.putDynamic(RegistryKey.DIMENSION_TYPE, DimensionTypeKeys.values(), DimensionTypeKeys.tags());
        loader.putDynamic(RegistryKey.ENCHANTMENT, EnchantmentKeys.values(), EnchantmentKeys.tags());
        loader.putDynamic(RegistryKey.FROG_VARIANT, FrogVariantKeys.values(), FrogVariantKeys.tags());
        loader.putDynamic(RegistryKey.INSTRUMENT, InstrumentKeys.values(), InstrumentKeys.tags());
        loader.putDynamic(RegistryKey.JUKEBOX_SONG, JukeboxSongKeys.values(), JukeboxSongKeys.tags());
        loader.putDynamic(RegistryKey.PAINTING_VARIANT, PaintingVariantKeys.values(), PaintingVariantKeys.tags());
        loader.putDynamic(RegistryKey.PIG_SOUND_VARIANT, PigSoundVariantKeys.values(), PigSoundVariantKeys.tags());
        loader.putDynamic(RegistryKey.PIG_VARIANT, PigVariantKeys.values(), PigVariantKeys.tags());
        loader.putDynamic(RegistryKey.TIMELINE, TimelineKeys.values(), TimelineKeys.tags());
        loader.putDynamic(RegistryKey.TRIM_MATERIAL, TrimMaterialKeys.values(), TrimMaterialKeys.tags());
        loader.putDynamic(RegistryKey.TRIM_PATTERN, TrimPatternKeys.values(), TrimPatternKeys.tags());
        loader.putDynamic(RegistryKey.WOLF_SOUND_VARIANT, WolfSoundVariantKeys.values(), WolfSoundVariantKeys.tags());
        loader.putDynamic(RegistryKey.WOLF_VARIANT, WolfVariantKeys.values(), WolfVariantKeys.tags());
        loader.putDynamic(RegistryKey.WORLD_CLOCK, WorldClockKeys.values(), WorldClockKeys.tags());
        loader.putDynamic(RegistryKey.ZOMBIE_NAUTILUS_VARIANT, ZombieNautilusVariantKeys.values(), ZombieNautilusVariantKeys.tags());

        return loader;
    }

    Map<Key, Registry> dynamic() {
        return dynamic;
    }

    Map<Key, Registry> frozen() {
        return frozen;
    }

    private <T> void putFrozen(final RegistryKey<T> registryKey, final Stream<TypedKey<T>> values, final Map<Key, List<Key>> tags) {
        frozen.put(registryKey.key(), toRegistry(registryKey, values, tags));
    }

    private <T> void putDynamic(final RegistryKey<T> registryKey, final Stream<TypedKey<T>> values, final Map<Key, List<Key>> tags) {
        dynamic.put(registryKey.key(), toRegistry(registryKey, values, tags));
    }

    private static <T> Registry toRegistry(final RegistryKey<T> registryKey, final Stream<TypedKey<T>> values, final Map<Key, List<Key>> tags) {
        final List<Key> entries = values.map(TypedKey::key).toList();
        return new Registry(registryKey.key(), entries, tags);
    }
}
