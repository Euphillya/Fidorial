package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent when a player right-clicks with a signed book. This tells the client to open the book GUI.</p>
 *
 * <p><b>Packet ID:</b> Play = 58 (0x3A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Open_Book">Open Book</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Hand</td><td>VarInt Enum</td><td>0: Main hand, 1: Off hand .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundOpenBookPacket(Object hand) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.OPEN_BOOK;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write hand (VarInt Enum)
    }
}
