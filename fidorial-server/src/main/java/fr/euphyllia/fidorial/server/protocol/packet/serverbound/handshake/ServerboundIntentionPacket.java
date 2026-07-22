package fr.euphyllia.fidorial.server.protocol.packet.serverbound.handshake;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.HandshakePacketListener;

/**
 * <p>This packet causes the server to switch into the target state. It should be sent right after opening the TCP connection to prevent the server from disconnecting.</p>
 *
 * <p><b>Packet ID:</b> Handshaking = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Handshake">Handshake</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Protocol Version</td><td>VarInt</td><td>See protocol version numbers (currently 776 in Minecraft 26.2).</td></tr>
 *     <tr><td>1</td><td>Server Address</td><td>String (255)</td><td>Hostname or IP, e.g. localhost or 127.0.0.1, that was used to connect. The vanilla server does not use this information. This is the name obtained after SRV record resolution, except in 1.17 (and no older or newer version) and during server list ping ( MC-278651 ), where it is the host portion of the address specified by the user directly. In 1.17.1 and later if a literal IP address is specified by the user, reverse DNS lookup is attempted, and the result is used as the value of this field if successful.</td></tr>
 *     <tr><td>2</td><td>Server Port</td><td>Unsigned Short</td><td>Default is 25565. The vanilla server does not use this information.</td></tr>
 *     <tr><td>3</td><td>Intent</td><td>VarInt Enum</td><td>1 for Status , 2 for Login , 3 for Transfer . Intents 2 and 3 both transition to the Login state, but 3 indicates that the client is connecting due to a Transfer packet received from another server. If the server is not expecting transfers, it may choose to reject the connection by replying with a Disconnect (login) packet.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundIntentionPacket(int protocolVersion, String hostname, int port, int nextState)
        implements ServerboundPacket {

    public static ServerboundIntentionPacket read(PacketBuffer buf) {
        int protocolVersion = buf.readVarInt();
        String hostname = buf.readString(255);
        int port = buf.readUShort();
        int nextState = buf.readVarInt();
        return new ServerboundIntentionPacket(protocolVersion, hostname, port, nextState);
    }

    @Override
    public void handle(PacketListener listener) {
        ((HandshakePacketListener) listener).handleIntention(this);
    }
}
