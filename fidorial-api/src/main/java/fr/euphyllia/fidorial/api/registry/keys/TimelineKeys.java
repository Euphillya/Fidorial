// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.Timeline;

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

    private static TypedKey<Timeline> create(String value) {
        return TypedKey.create(RegistryKey.TIMELINE, Key.minecraft(value));
    }
}
