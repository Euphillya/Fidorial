package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Response to the clientbound packet ( Ping ) with the same id.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 5 (0x05), Play = 45 (0x2D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Pong">Pong</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>ID</td><td>Int</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPongPacket(int id) implements ServerboundPacket {

    public static ServerboundPongPacket read(PacketBuffer buf) {
        int id = 0;
        id = buf.readInt();
        return new ServerboundPongPacket(id);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
