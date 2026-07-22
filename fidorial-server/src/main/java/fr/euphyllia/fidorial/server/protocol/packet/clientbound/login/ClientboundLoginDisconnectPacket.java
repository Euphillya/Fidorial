package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Login = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Disconnect_(login)">Disconnect (login)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Reason</td><td>JSON Text Component</td><td>The reason why the player was disconnected.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundLoginDisconnectPacket(String reasonJson) implements ClientboundPacket {

    public static ClientboundLoginDisconnectPacket ofText(String text) {
        return new ClientboundLoginDisconnectPacket("{\"text\":\"" + text + "\"}");
    }

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_DISCONNECT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(reasonJson);
    }
}
