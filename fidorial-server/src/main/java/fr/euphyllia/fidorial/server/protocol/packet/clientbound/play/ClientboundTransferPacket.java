package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Notifies the client that it should transfer to the given server. The client will close its connection to the current server, open a connection to the specified address and send a Handshake with intent set to 3 (Transfer). If the server chooses to accept the transfer, the usual login process will follow. Cookies previously stored are preserved between server transfers.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 11 (0x0B), Play = 129 (0x81)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Transfer">Transfer</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Host</td><td>String (32767)</td><td>The hostname or IP of the server.</td></tr>
 *     <tr><td>1</td><td>Port</td><td>VarInt</td><td>The port of the server.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTransferPacket(String host, int port) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TRANSFER;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(host);
        buf.writeVarInt(port);
    }
}
