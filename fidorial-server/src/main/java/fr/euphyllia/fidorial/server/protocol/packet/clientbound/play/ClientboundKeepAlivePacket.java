package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Keep_Alive_(clientbound)
public record ClientboundKeepAlivePacket(long id) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.KEEP_ALIVE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(id);
    }
}
