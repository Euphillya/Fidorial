package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Clear the client's current title information, with the option to also reset it.</p>
 *
 * <p><b>Packet ID:</b> Play = 14 (0x0E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Clear_Titles">Clear Titles</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Reset</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundClearTitlesPacket(boolean reset) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CLEAR_TITLES;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeBoolean(reset);
    }
}
