package fr.fidorial.registrygen.model;

import java.util.List;
import java.util.Optional;

public final class PacketCatalogs {

    private static final String PACKAGE = "fr.euphyllia.fidorial.server.network.protocol.catalog";

    public static final List<ProtocolIdTarget> ALL = List.of(
            target("play/clientbound", "PlayClientboundPackets"),
            target("play/serverbound", "PlayServerboundPackets"),
            target("configuration/clientbound", "ConfigurationClientboundPackets"),
            target("configuration/serverbound", "ConfigurationServerboundPackets"),
            target("handshake/serverbound", "HandshakeServerboundPackets"),
            target("login/clientbound", "LoginClientboundPackets"),
            target("login/serverbound", "LoginServerboundPackets"),
            target("status/clientbound", "StatusClientboundPackets"),
            target("status/serverbound", "StatusServerboundPackets")
    );

    private PacketCatalogs() {
        throw new UnsupportedOperationException();
    }

    private static ProtocolIdTarget target(final String stateAndBound, final String className) {
        return new ProtocolIdTarget(
                "packet:" + stateAndBound,
                PACKAGE,
                className,
                "",
                ProtocolIdValueKind.IDENTIFIER,
                "Packet identifiers for the {@code " + stateAndBound + "} state/direction.\n",
                "Generated from Mojang's packets report; do not edit.");
    }

    public static Optional<ProtocolIdTarget> byIdentifier(final String syntheticIdentifier) {
        return ALL.stream().filter(t -> t.registryIdentifier().equals(syntheticIdentifier)).findFirst();
    }
}
