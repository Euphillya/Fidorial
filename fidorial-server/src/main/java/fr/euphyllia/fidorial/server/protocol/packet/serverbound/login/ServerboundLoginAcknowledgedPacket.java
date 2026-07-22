package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

/**
 * <p>Acknowledgement to the Login Success packet sent by the server. This packet switches the connection state to configuration .</p>
 *
 * <p><b>Packet ID:</b> Login = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Login_Acknowledged">Login Acknowledged</a></p>
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
public record ServerboundLoginAcknowledgedPacket() implements ServerboundPacket {

    public static ServerboundLoginAcknowledgedPacket read(PacketBuffer buf) {
        return new ServerboundLoginAcknowledgedPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleLoginAcknowledged(this);
    }
}
