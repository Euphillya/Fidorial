package fr.euphyllia.fidorial.server.network.protocol.catalog;

import net.kyori.adventure.key.Key;

/**
 * Packet identifiers for the {@code status/serverbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class StatusServerboundPackets {
    /**
     * {@code minecraft:ping_request}
     */
    public static final Key PING_REQUEST = Key.key("ping_request");

    /**
     * {@code minecraft:status_request}
     */
    public static final Key STATUS_REQUEST = Key.key("status_request");

    private StatusServerboundPackets() {
    }
}
