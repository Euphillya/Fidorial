package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>Sent when the player changes the slot selection.</p>
 *
 * <p><b>Packet ID:</b> Play = 53 (0x35)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Held_Item_(serverbound)">Set Held Item (serverbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot</td><td>Short</td><td>The slot which the player has selected (0–8).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetCarriedItemPacket(int slot) implements ServerboundPacket {

    public static ServerboundSetCarriedItemPacket read(PacketBuffer buf) {
        return new ServerboundSetCarriedItemPacket(buf.readShort());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleSetCarriedItem(this);
    }
}
