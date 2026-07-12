package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundBlockChangedAckPacket(int sequence) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_CHANGED_ACK;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(sequence);
    }
}
