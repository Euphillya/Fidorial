package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

import java.util.List;

public record ClientboundRegistryDataPacket(Key registryId, List<Key> entries)
        implements ClientboundPacket {

    @Override
    public Key name() {
        return ConfigurationClientboundPackets.REGISTRY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeKey(registryId);
        buf.writeVarInt(entries.size());
        for (final Key entry : entries) {
            buf.writeKey(entry.key());
            buf.writeBoolean(false);
            /*
            final Nbt data = entry.data();
            buf.writeBoolean(data != null);
            if (data != null) buf.writeNbt(data);
            */
        }
    }
}
