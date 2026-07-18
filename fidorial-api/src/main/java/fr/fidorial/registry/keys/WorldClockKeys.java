package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.WorldClock;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:world_clock} registry.
 */
public final class WorldClockKeys {

    public static final TypedKey<WorldClock> OVERWORLD = create("overworld");
    public static final TypedKey<WorldClock> THE_END = create("the_end");

    private WorldClockKeys() {
    }

    private static TypedKey<WorldClock> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.WORLD_CLOCK, value);
    }
}
