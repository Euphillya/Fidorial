package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Dialog;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:dialog} registry.
 */
public final class DialogKeys {
    /**
     * Key for {@code minecraft:custom_options}.
     */
    public static final TypedKey<Dialog> CUSTOM_OPTIONS = create("custom_options");

    /**
     * Key for {@code minecraft:quick_actions}.
     */
    public static final TypedKey<Dialog> QUICK_ACTIONS = create("quick_actions");

    /**
     * Key for {@code minecraft:server_links}.
     */
    public static final TypedKey<Dialog> SERVER_LINKS = create("server_links");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<Dialog>> VALUES = List.of(
        CUSTOM_OPTIONS,
        QUICK_ACTIONS,
        SERVER_LINKS
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("pause_screen_additions"), List.of()),
        Map.entry(Key.key("quick_actions"), List.of())
    );

    private DialogKeys() {
        throw new UnsupportedOperationException("DialogKeys cannot be instantiated.");
    }

    private static TypedKey<Dialog> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.DIALOG, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<Dialog>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return TAGS;
    }
}
