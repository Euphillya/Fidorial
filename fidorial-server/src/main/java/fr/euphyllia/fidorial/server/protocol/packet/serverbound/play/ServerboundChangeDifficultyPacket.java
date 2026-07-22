package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Must have at least op level 2 to use.</p>
 * <p>Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.</p>
 *
 * <p><b>Packet ID:</b> Play = 4 (0x04)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Change_Difficulty_2">Change Difficulty</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>New difficulty</td><td>Unsigned Byte Enum</td><td>0: peaceful, 1: easy, 2: normal, 3: hard.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChangeDifficultyPacket(Object newDifficulty) implements ServerboundPacket {

    public static ServerboundChangeDifficultyPacket read(PacketBuffer buf) {
        Object newDifficulty = null; // TODO: read newDifficulty (Unsigned Byte Enum)
        return new ServerboundChangeDifficultyPacket(newDifficulty);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
