package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent to change the player's slot selection.</p>
 *
 * <p><b>Packet ID:</b> Play = 105 (0x69)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Held_Item_(clientbound)">Set Held Item (clientbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot</td><td>VarInt</td><td>The slot which the player has selected (0–8).</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetHeldSlotPacket(int slot) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_HELD_SLOT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(slot);
    }
}
