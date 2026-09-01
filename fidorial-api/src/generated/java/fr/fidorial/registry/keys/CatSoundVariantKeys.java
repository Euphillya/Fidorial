package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CatSoundVariant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:cat_sound_variant} registry.
 */
public final class CatSoundVariantKeys {
    /**
     * Key for {@code minecraft:classic}.
     */
    public static final TypedKey<CatSoundVariant> CLASSIC = create("classic");

    /**
     * Key for {@code minecraft:royal}.
     */
    public static final TypedKey<CatSoundVariant> ROYAL = create("royal");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<CatSoundVariant>> VALUES = List.of(
        CLASSIC,
        ROYAL
    );

    private CatSoundVariantKeys() {
        throw new UnsupportedOperationException("CatSoundVariantKeys cannot be instantiated.");
    }

    private static TypedKey<CatSoundVariant> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.CAT_SOUND_VARIANT, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<CatSoundVariant>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return Map.of();
    }
}
