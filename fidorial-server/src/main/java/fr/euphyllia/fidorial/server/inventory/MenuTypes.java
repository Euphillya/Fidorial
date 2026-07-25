package fr.euphyllia.fidorial.server.inventory;

import fr.euphyllia.fidorial.server.registry.RegistryHolder;

/**
 * IDs from the {@code minecraft:menu} registry, used by {@code open_screen}.
 */
public final class MenuTypes {

    public static final String GENERIC_9X3 = "minecraft:generic_9x3";

    /**
     * Vanilla index of {@code generic_9x3}: the six {@code generic_9xN} entries open the registry
     * list, in the order 9x1, 9x2, 9x3, ...
     */
    private static final int GENERIC_9X3_FALLBACK = 2;

    private MenuTypes() {}

    public static int genericChest3Rows(final RegistryHolder frozen) {
        return resolve(frozen, GENERIC_9X3, GENERIC_9X3_FALLBACK);
    }

    private static int resolve(final RegistryHolder frozen, final String entry, final int fallback) {
        final int id = frozen.networkId("minecraft:menu", entry);
        return id >= 0 ? id : fallback;
    }
}