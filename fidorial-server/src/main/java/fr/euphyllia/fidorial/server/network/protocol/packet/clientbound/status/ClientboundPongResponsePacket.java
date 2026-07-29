package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.StatusClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;

public record ClientboundPongResponsePacket(long payload) implements ClientboundPacket {

    @Override
    public String name() {
        return StatusClientboundPackets.PONG_RESPONSE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(payload);
    }
}
