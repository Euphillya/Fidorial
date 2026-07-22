package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

import java.util.UUID;

/**
 * <p><b>Packet ID:</b> Login = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Login_Start">Login Start</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Name</td><td>String (16)</td><td>Player's Username.</td></tr>
 *     <tr><td>1</td><td>Player UUID</td><td>UUID</td><td>The UUID of the player logging in. Unused by the vanilla server.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundHelloPacket(String username, UUID profileId) implements ServerboundPacket {

    public static ServerboundHelloPacket read(PacketBuffer buf) {
        String username = buf.readString(16);
        UUID profileId = buf.readUuid();
        return new ServerboundHelloPacket(username, profileId);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleHello(this);
    }
}
