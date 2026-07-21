package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

import java.util.UUID;

/**
 * <p><b>Packet ID:</b> Play = 10 (0x0A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Session">Player Session</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Session Id</td><td>UUID</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Public Key</td><td>Expires At</td><td>Long</td></tr>
 *     <tr><td>2</td><td>Public Key</td><td>Prefixed Array (512) of Byte</td><td>A byte array of an X.509-encoded public key.</td></tr>
 *     <tr><td>3</td><td>Key Signature</td><td>Prefixed Array (4096) of Byte</td><td>The signature consists of the player UUID, the key expiration timestamp, and the public key data. These values are hashed using SHA-1 and signed using Mojang's private RSA key.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChatSessionUpdatePacket(UUID sessionId, Object publicKey, Object publicKey2,
                                                 Object keySignature) implements ServerboundPacket {

    public static ServerboundChatSessionUpdatePacket read(PacketBuffer buf) {
        UUID sessionId = null;
        sessionId = buf.readUuid();
        Object publicKey = null; // TODO: read publicKey (Expires At)
        Object publicKey2 = null; // TODO: read publicKey2 (Prefixed Array of Byte)
        Object keySignature = null; // TODO: read keySignature (Prefixed Array of Byte)
        return new ServerboundChatSessionUpdatePacket(sessionId, publicKey, publicKey2, keySignature);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
