package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

/**
 * <p>See protocol encryption for details.</p>
 *
 * <p><b>Packet ID:</b> Login = 1 (0x01)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Encryption_Response">Encryption Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Shared Secret</td><td>Prefixed Array of Byte</td><td>Shared Secret value, encrypted with the server's public key.</td></tr>
 *     <tr><td>1</td><td>Verify Token</td><td>Prefixed Array of Byte</td><td>Verify Token value, encrypted with the same public key as the shared secret.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundKeyPacket(byte[] encryptedSecret, byte[] encryptedToken) implements ServerboundPacket {

    public static ServerboundKeyPacket read(PacketBuffer buf) {
        byte[] encryptedSecret = buf.readByteArray(256);
        byte[] encryptedToken = buf.readByteArray(256);
        return new ServerboundKeyPacket(encryptedSecret, encryptedToken);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleKey(this);
    }
}
