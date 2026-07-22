package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets">Java Edition protocol/Packets</a></p>
 */
public record ClientboundDebugEventPacket() implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DEBUG_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
    }
}
