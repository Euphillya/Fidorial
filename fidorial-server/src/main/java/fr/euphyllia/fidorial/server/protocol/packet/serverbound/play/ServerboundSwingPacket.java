package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent when the player's arm swings.</p>
 *
 * <p><b>Packet ID:</b> Play = 63 (0x3F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Swing_Arm">Swing Arm</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Hand</td><td>VarInt Enum</td><td>Hand used for the animation. 0: main hand, 1: off hand.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSwingPacket(Object hand) implements ServerboundPacket {

    public static ServerboundSwingPacket read(PacketBuffer buf) {
        Object hand = null; // TODO: read hand (VarInt Enum)
        return new ServerboundSwingPacket(hand);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
