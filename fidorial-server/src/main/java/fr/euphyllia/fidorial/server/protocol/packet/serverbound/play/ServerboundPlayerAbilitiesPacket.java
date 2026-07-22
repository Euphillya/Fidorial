package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>The vanilla client sends this packet when the player starts/stops flying with the Flags parameter changed accordingly.</p>
 *
 * <p><b>Packet ID:</b> Play = 40 (0x28)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Abilities_(serverbound)">Player Abilities (serverbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Flags</td><td>Byte</td><td>Bit mask. 0x02: is flying.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlayerAbilitiesPacket(byte flags) implements ServerboundPacket {

    public static ServerboundPlayerAbilitiesPacket read(PacketBuffer buf) {
        byte flags = (byte) 0;
        flags = buf.readByte();
        return new ServerboundPlayerAbilitiesPacket(flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
