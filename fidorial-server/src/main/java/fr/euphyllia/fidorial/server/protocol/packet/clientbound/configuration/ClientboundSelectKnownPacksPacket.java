package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundSelectKnownPacksPacket(String namespace, String id, String version)
        implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.SELECT_KNOWN_PACKS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(1);
        buf.writeString(namespace);
        buf.writeString(id);
        buf.writeString(version);
    }
}
