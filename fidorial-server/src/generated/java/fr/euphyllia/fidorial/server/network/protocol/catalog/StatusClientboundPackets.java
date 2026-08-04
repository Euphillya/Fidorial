package fr.euphyllia.fidorial.server.network.protocol.catalog;

import net.kyori.adventure.key.Key;

/**
 * Packet identifiers for the {@code status/clientbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class StatusClientboundPackets {
    /**
     * {@code minecraft:pong_response}
     */
    public static final Key PONG_RESPONSE = Key.key("pong_response");

    /**
     * {@code minecraft:status_response}
     */
    public static final Key STATUS_RESPONSE = Key.key("status_response");

    private StatusClientboundPackets() {
    }
}
