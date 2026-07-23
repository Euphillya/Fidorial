package fr.euphyllia.fidorial.server.world.storage;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public record Dimension(
        String namespace,
        String path,
        @Nullable String legacyFolder
) {

    public static final Dimension OVERWORLD = new Dimension("minecraft", "overworld", null);
    public static final Dimension THE_NETHER = new Dimension("minecraft", "the_nether", "DIM-1");
    public static final Dimension THE_END = new Dimension("minecraft", "the_end", "DIM1");

    public static Dimension datapack(final String namespace, final String path) {
        return new Dimension(namespace, path, null);
    }

    public Key id() {
        return Key.key(namespace(), path());
    }
}
