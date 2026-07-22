package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Replaces or sets the inventory item that's being dragged with the mouse.</p>
 *
 * <p><b>Packet ID:</b> Play = 96 (0x60)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Cursor_Item">Set Cursor Item</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Carried item</td><td>Slot</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetCursorItemPacket(Object carriedItem) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_CURSOR_ITEM;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write carriedItem (Slot)
    }
}
