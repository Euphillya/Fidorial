package fr.euphyllia.fidorial.server.protocol.packet.clientbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.StatusClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundStatusResponsePacket(String json) implements ClientboundPacket {

    @Override
    public String name() {
        return StatusClientboundPackets.STATUS_RESPONSE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(json);
    }
}
