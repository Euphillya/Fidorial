package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;

public record ClientboundEntityEventPacket(int entityId, byte eventId) implements ClientboundPacket {
    @Override
    public String name() {
        return PlayClientboundPackets.ENTITY_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(entityId);
        buf.writeByte(eventId);
    }
}
