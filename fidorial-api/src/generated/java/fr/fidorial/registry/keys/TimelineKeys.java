package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Timeline;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:timeline} registry.
 */
public final class TimelineKeys {
    /**
     * Key for {@code minecraft:day}.
     */
    public static final TypedKey<Timeline> DAY = create("day");

    /**
     * Key for {@code minecraft:early_game}.
     */
    public static final TypedKey<Timeline> EARLY_GAME = create("early_game");

    /**
     * Key for {@code minecraft:moon}.
     */
    public static final TypedKey<Timeline> MOON = create("moon");

    /**
     * Key for {@code minecraft:villager_schedule}.
     */
    public static final TypedKey<Timeline> VILLAGER_SCHEDULE = create("villager_schedule");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<Timeline>> VALUES = List.of(
        DAY,
        EARLY_GAME,
        MOON,
        VILLAGER_SCHEDULE
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("in_end"), List.of(Key.key("villager_schedule"))),
        Map.entry(Key.key("in_nether"), List.of(Key.key("villager_schedule"))),
        Map.entry(Key.key("in_overworld"), List.of(Key.key("day"), Key.key("early_game"), Key.key("moon"), Key.key("villager_schedule"))),
        Map.entry(Key.key("universal"), List.of(Key.key("villager_schedule")))
    );

    private TimelineKeys() {
        throw new UnsupportedOperationException("TimelineKeys cannot be instantiated.");
    }

    private static TypedKey<Timeline> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.TIMELINE, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<Timeline>> values() {
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
