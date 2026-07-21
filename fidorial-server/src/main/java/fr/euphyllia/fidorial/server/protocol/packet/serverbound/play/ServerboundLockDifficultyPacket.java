package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Must have at least op level 2 to use.</p>
 * <p>Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.</p>
 *
 * <p><b>Packet ID:</b> Play = 29 (0x1D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Lock_Difficulty">Lock Difficulty</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Locked</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundLockDifficultyPacket(boolean locked) implements ServerboundPacket {

    public static ServerboundLockDifficultyPacket read(PacketBuffer buf) {
        boolean locked = false;
        locked = buf.readBoolean();
        return new ServerboundLockDifficultyPacket(locked);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
