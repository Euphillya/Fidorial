package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Action ID values:</p>
 *
 * <p><b>Packet ID:</b> Play = 12 (0x0C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Client_Status">Client Status</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Action ID</td><td>VarInt Enum</td><td>See below</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundClientCommandPacket(Object actionId) implements ServerboundPacket {

    public static ServerboundClientCommandPacket read(PacketBuffer buf) {
        Object actionId = null; // TODO: read actionId (VarInt Enum)
        return new ServerboundClientCommandPacket(actionId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
