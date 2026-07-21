package fr.euphyllia.fidorial.server.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;

/**
 * <p><b>Packet ID:</b> Status = 1 (0x01)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Ping_Request_(status)">Ping Request (status)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Timestamp</td><td>Long</td><td>May be any number, but vanilla clients will always use the timestamp in milliseconds.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPingRequestPacket(long payload) implements ServerboundPacket {

    public static ServerboundPingRequestPacket read(PacketBuffer buf) {
        return new ServerboundPingRequestPacket(buf.readLong());
    }

    @Override
    public void handle(PacketListener listener) {
        ((StatusPacketListener) listener).handlePingRequest(this);
    }
}
