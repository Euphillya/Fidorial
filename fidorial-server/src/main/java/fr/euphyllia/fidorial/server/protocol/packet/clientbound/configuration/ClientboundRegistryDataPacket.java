package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.List;

public record ClientboundRegistryDataPacket(String registryId, List<String> entries)
        implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.REGISTRY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(registryId);
        buf.writeVarInt(entries.size());
        for (String entry : entries) {
            buf.writeIdentifier(entry);
            buf.writeBoolean(false);
        }
    }
}
