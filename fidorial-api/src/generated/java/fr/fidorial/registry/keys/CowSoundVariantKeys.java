package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CowSoundVariant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:cow_sound_variant} registry.
 */
public final class CowSoundVariantKeys {
    /**
     * Key for {@code minecraft:classic}.
     */
    public static final TypedKey<CowSoundVariant> CLASSIC = create("classic");

    /**
     * Key for {@code minecraft:moody}.
     */
    public static final TypedKey<CowSoundVariant> MOODY = create("moody");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<CowSoundVariant>> VALUES = List.of(
        CLASSIC,
        MOODY
    );

    private CowSoundVariantKeys() {
        throw new UnsupportedOperationException("CowSoundVariantKeys cannot be instantiated.");
    }

    private static TypedKey<CowSoundVariant> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.COW_SOUND_VARIANT, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<CowSoundVariant>> values() {
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
