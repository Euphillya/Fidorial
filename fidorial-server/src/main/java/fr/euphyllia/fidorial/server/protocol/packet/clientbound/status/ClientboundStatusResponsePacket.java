package fr.euphyllia.fidorial.server.protocol.packet.clientbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.StatusClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Status = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Status_Response">Status Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>JSON Response</td><td>String (32767)</td><td>See Java Edition protocol/Server List Ping#Status Response ; as with all strings, this is prefixed by its length as a VarInt .</td></tr>
 *   </tbody>
 * </table>
 */
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
