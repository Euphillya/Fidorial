package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>The server will frequently send out a keep-alive (see Keep Alive (clientbound) ), each containing a random ID. The client must respond with the same packet.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 4 (0x04), Play = 28 (0x1C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Keep_Alive_(serverbound)">Keep Alive (serverbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Keep Alive ID</td><td>Long</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundKeepAlivePacket(long id) implements ServerboundPacket {

    public static ServerboundKeepAlivePacket read(PacketBuffer buf) {
        return new ServerboundKeepAlivePacket(buf.readLong());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleKeepAlive(this);
    }
}
