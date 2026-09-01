package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.PigVariant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:pig_variant} registry.
 */
public final class PigVariantKeys {
    /**
     * Key for {@code minecraft:cold}.
     */
    public static final TypedKey<PigVariant> COLD = create("cold");

    /**
     * Key for {@code minecraft:temperate}.
     */
    public static final TypedKey<PigVariant> TEMPERATE = create("temperate");

    /**
     * Key for {@code minecraft:warm}.
     */
    public static final TypedKey<PigVariant> WARM = create("warm");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<PigVariant>> VALUES = List.of(
        COLD,
        TEMPERATE,
        WARM
    );

    private PigVariantKeys() {
        throw new UnsupportedOperationException("PigVariantKeys cannot be instantiated.");
    }

    private static TypedKey<PigVariant> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.PIG_VARIANT, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<PigVariant>> values() {
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
