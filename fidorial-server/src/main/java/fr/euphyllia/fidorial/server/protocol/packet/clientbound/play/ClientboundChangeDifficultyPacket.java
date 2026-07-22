package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Changes the difficulty setting in the client's option menu</p>
 *
 * <p><b>Packet ID:</b> Play = 10 (0x0A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Change_Difficulty">Change Difficulty</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Difficulty</td><td>Unsigned Byte Enum</td><td>0: peaceful, 1: easy, 2: normal, 3: hard.</td></tr>
 *     <tr><td>1</td><td>Difficulty locked?</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundChangeDifficultyPacket(Object difficulty,
                                                boolean difficultyLocked) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CHANGE_DIFFICULTY;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write difficulty (Unsigned Byte Enum)
        buf.writeBoolean(difficultyLocked);
    }
}
