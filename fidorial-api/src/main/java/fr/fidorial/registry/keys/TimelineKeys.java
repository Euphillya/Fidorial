package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Timeline;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:timeline} registry.
 */
public final class TimelineKeys {

    public static final TypedKey<Timeline> DAY = create("day");
    public static final TypedKey<Timeline> EARLY_GAME = create("early_game");
    public static final TypedKey<Timeline> MOON = create("moon");
    public static final TypedKey<Timeline> VILLAGER_SCHEDULE = create("villager_schedule");

    private TimelineKeys() {
    }

    private static TypedKey<Timeline> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.TIMELINE, value);
    }
}
