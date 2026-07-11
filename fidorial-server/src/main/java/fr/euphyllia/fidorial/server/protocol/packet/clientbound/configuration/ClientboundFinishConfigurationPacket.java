package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundFinishConfigurationPacket() implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.FINISH_CONFIGURATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        // aucun champ
    }
}
