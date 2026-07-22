package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent whenever the player presses or releases certain keys. The flags correspond directly to the states of their corresponding keys—the Sprint flag does not depend on whether the player is actually able to sprint at the moment, etc. Used by the vanilla server for minecart controls, player inputs in the entity_properties predicate , and sneaking (sprinting is still controlled by Player Command ). The flags are as follows:</p>
 *
 * <p><b>Packet ID:</b> Play = 43 (0x2B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Input">Player Input</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Flags</td><td>Unsigned Byte</td><td>Bit mask; see below</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlayerInputPacket(int flags) implements ServerboundPacket {

    public static ServerboundPlayerInputPacket read(PacketBuffer buf) {
        int flags = 0;
        flags = buf.readUByte();
        return new ServerboundPlayerInputPacket(flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
