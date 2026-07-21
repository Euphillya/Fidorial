package fr.euphyllia.fidorial.server.protocol.packet.clientbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.StatusClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Status = 1 (0x01)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Pong_Response_(status)">Pong Response (status)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Timestamp</td><td>Long</td><td>Should match the one sent by the client.</td></tr>
 *   </tbody>
 * </table>
 */
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
