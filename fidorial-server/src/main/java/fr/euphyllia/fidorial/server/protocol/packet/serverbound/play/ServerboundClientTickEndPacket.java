package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 13 (0x0D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Client_Tick_End">Client Tick End</a></p>
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
public record ServerboundClientTickEndPacket(Object noFields) implements ServerboundPacket {

    public static ServerboundClientTickEndPacket read(PacketBuffer buf) {
        Object noFields = null; // TODO: read noFields ()
        return new ServerboundClientTickEndPacket(noFields);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
