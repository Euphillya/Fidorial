package fr.euphyllia.fidorial.server.world.time;

import fr.euphyllia.fidorial.server.world.storage.Dimension;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.keys.WorldClockKeys;
import net.kyori.adventure.key.Key;

public class WorldClocks {

    public static final String REGISTRY = RegistryKey.WORLD_CLOCK.key().asString();

    private WorldClocks() {
    }

    public static Key forDimension(final Dimension dimension) {
        if (Dimension.THE_END.id().equals(dimension.id())) {
            return WorldClockKeys.THE_END.key();
        }
        return WorldClockKeys.OVERWORLD.key();
    }

}
