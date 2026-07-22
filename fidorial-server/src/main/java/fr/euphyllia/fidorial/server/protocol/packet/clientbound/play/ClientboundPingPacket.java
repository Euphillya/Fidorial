package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Packet is not used by the vanilla server. When sent to the client, the client responds with a Pong packet with the same ID.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 5 (0x05), Play = 61 (0x3D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Ping">Ping</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>ID</td><td>Int</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPingPacket(int id) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PING;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(id);
    }
}
