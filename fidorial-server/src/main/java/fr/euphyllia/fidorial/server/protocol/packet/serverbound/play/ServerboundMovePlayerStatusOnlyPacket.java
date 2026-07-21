package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet, as well as Set Player Position , Set Player Rotation , and Set Player Position and Rotation are called the “serverbound movement packets”. Vanilla clients will send Move Player Position once every 20 ticks, even for a stationary player. This packet is used to indicate whether the player is on ground (walking/swimming) or airborne (jumping/falling). When dropping from a sufficient height, fall damage is applied when this state goes from false to true. The amount of damage applied is based on the point where it last changed from true to false. Note that there are several movement related packets containing this state.</p>
 *
 * <p><b>Packet ID:</b> Play = 33 (0x21)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Player_Movement_Flags">Set Player Movement Flags</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Flags</td><td>Byte</td><td>Bit field: 0x01: on ground, 0x02: pushing against wall.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundMovePlayerStatusOnlyPacket(byte flags) implements ServerboundPacket {

    public static ServerboundMovePlayerStatusOnlyPacket read(PacketBuffer buf) {
        byte flags = (byte) 0;
        flags = buf.readByte();
        return new ServerboundMovePlayerStatusOnlyPacket(flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
