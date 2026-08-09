package fr.fidorial.registrygen.model;

import java.util.ArrayList;
import java.util.List;

public final class PacketCatalogs {

    private static final String PACKAGE = "fr.euphyllia.fidorial.server.network.protocol.catalog";

    public static final List<ProtocolIdTarget> ALL = List.of(
            keys("play/clientbound", "PlayClientboundPackets"),
            ids("play/clientbound", "PlayClientboundPacketIds"),
            keys("play/serverbound", "PlayServerboundPackets"),
            ids("play/serverbound", "PlayServerboundPacketIds"),
            keys("configuration/clientbound", "ConfigurationClientboundPackets"),
            ids("configuration/clientbound", "ConfigurationClientboundPacketIds"),
            keys("configuration/serverbound", "ConfigurationServerboundPackets"),
            ids("configuration/serverbound", "ConfigurationServerboundPacketIds"),
            keys("handshake/serverbound", "HandshakeServerboundPackets"),
            ids("handshake/serverbound", "HandshakeServerboundPacketIds"),
            keys("login/clientbound", "LoginClientboundPackets"),
            ids("login/clientbound", "LoginClientboundPacketIds"),
            keys("login/serverbound", "LoginServerboundPackets"),
            ids("login/serverbound", "LoginServerboundPacketIds"),
            keys("status/clientbound", "StatusClientboundPackets"),
            ids("status/clientbound", "StatusClientboundPacketIds"),
            keys("status/serverbound", "StatusServerboundPackets"),
            ids("status/serverbound", "StatusServerboundPacketIds")
    );

    private PacketCatalogs() {
        throw new UnsupportedOperationException();
    }

    private static ProtocolIdTarget keys(final String stateAndBound, final String className) {
        return new ProtocolIdTarget(
                "packet:" + stateAndBound,
                PACKAGE,
                className,
                "",
                ProtocolIdValueKind.IDENTIFIER,
                "Packet identifiers for the {@code " + stateAndBound + "} state/direction.\n",
                "Generated from Mojang's packets report; do not edit.");
    }

    private static ProtocolIdTarget ids(final String stateAndBound, final String className) {
        return new ProtocolIdTarget(
                "packet:" + stateAndBound,
                PACKAGE,
                className,
                "_ID",
                ProtocolIdValueKind.PROTOCOL_ID,
                "Network IDs for the {@code " + stateAndBound + "} state/direction.\n",
                "Generated from Mojang's packets report; do not edit.");
    }

    public static List<ProtocolIdTarget> byIdentifier(final String syntheticIdentifier) {

        final List<ProtocolIdTarget> targets = new ArrayList<>();

        for (final ProtocolIdTarget target : ALL) {
            if (target.registryIdentifier().equals(syntheticIdentifier)) {
                targets.add(target);
            }
        }

        return List.copyOf(targets);
    }
}
