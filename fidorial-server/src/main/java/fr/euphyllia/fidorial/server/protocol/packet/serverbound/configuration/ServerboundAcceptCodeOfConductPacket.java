package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent when the client clicks on the "Acknowledge" field in the "Code of Conduct" view. Sent in response to the Code of Conduct packet.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 9 (0x09)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Accept_Code_of_Conduct">Accept Code of Conduct</a></p>
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
public record ServerboundAcceptCodeOfConductPacket(Object noFields) implements ServerboundPacket {

    public static ServerboundAcceptCodeOfConductPacket read(PacketBuffer buf) {
        Object noFields = null; // TODO: read noFields ()
        return new ServerboundAcceptCodeOfConductPacket(noFields);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
