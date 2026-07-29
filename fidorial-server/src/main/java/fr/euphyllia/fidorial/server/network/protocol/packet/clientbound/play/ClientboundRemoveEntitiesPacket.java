package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;

public record ClientboundRemoveEntitiesPacket(int... entityIds) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.REMOVE_ENTITIES;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityIds.length);
        for (int entityId : entityIds) {
            buf.writeVarInt(entityId);
        }
    }
}
