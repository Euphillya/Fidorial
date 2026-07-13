package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.WorldClock;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:world_clock} registry.
 */
public final class WorldClockKeys {

    public static final TypedKey<WorldClock> OVERWORLD = create("overworld");
    public static final TypedKey<WorldClock> THE_END = create("the_end");

    private WorldClockKeys() {
    }

    private static TypedKey<WorldClock> create(String value) {
        return TypedKey.create(RegistryKey.WORLD_CLOCK, Key.minecraft(value));
    }
}
