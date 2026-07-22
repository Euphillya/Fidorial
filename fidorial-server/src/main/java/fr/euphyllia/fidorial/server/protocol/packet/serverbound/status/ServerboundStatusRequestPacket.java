package fr.euphyllia.fidorial.server.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;

/**
 * <p>The status can only be requested once, immediately after the handshake, before any ping. The server won't respond otherwise.</p>
 *
 * <p><b>Packet ID:</b> Status = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Status_Request">Status Request</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>no fields</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundStatusRequestPacket() implements ServerboundPacket {

    public static ServerboundStatusRequestPacket read(PacketBuffer buf) {
        return new ServerboundStatusRequestPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((StatusPacketListener) listener).handleStatusRequest(this);
    }
}
