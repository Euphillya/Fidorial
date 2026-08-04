package fr.euphyllia.fidorial.server.world.storage;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public record Dimension(
        String namespace,
        String path,
        @Nullable String legacyFolder,
        Key id
) {

    public static final Dimension OVERWORLD = new Dimension(Key.MINECRAFT_NAMESPACE, "overworld", null);
    public static final Dimension THE_NETHER = new Dimension(Key.MINECRAFT_NAMESPACE, "the_nether", "DIM-1");
    public static final Dimension THE_END = new Dimension(Key.MINECRAFT_NAMESPACE, "the_end", "DIM1");

    public Dimension(final String namespace, final String path, final @Nullable String legacyFolder) {
        this(namespace, path, legacyFolder, Key.key(namespace, path));
    }

    public static Dimension datapack(final String namespace, final String path) {
        return new Dimension(namespace, path, null);
    }
}
