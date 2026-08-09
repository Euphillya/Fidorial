package fr.euphyllia.fidorial.server.network.protocol.catalog;

import java.util.Map;
import net.kyori.adventure.key.Key;

/**
 * Network IDs for the {@code status/clientbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public interface StatusClientboundPacketIds {
    /**
     * Returned by {@link #id(Key)} when the identifier is unknown.
     */
    int UNKNOWN = -1;

    /**
     * {@code minecraft:pong_response}
     */
    int PONG_RESPONSE_ID = 1;

    /**
     * {@code minecraft:status_response}
     */
    int STATUS_RESPONSE_ID = 0;

    /**
     * Immutable identifier to protocol ID lookup table.
     */
    Map<Key, Integer> BY_IDENTIFIER = Map.ofEntries(
        Map.entry(Key.key("pong_response"), PONG_RESPONSE_ID),
        Map.entry(Key.key("status_response"), STATUS_RESPONSE_ID)
    );

    /**
     * Resolves the protocol ID for a namespaced identifier.
     *
     * @param identifier namespaced identifier, e.g. {@code Key.key("minecraft", "chest")}
     * @return the protocol ID, or {@link #UNKNOWN} when the identifier is unknown
     */
    static int id(final Key identifier) {
        return BY_IDENTIFIER.getOrDefault(identifier, UNKNOWN);
    }
}
