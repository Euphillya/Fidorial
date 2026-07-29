package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.StatusClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;

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
