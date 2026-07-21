package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sets the contents of a player inventory slot directly, bypassing the usual container window mechanism. This packet is used by the vanilla server only when placing items in temporary slots back into the inventory upon closing a container window, and likely exists to work around implementation issues specific to vanilla. It is prone to race conditions because it doesn't include a State ID, and there is generally no reason not to use Set Container Content or Set Container Slot instead.</p>
 *
 * <p><b>Packet ID:</b> Play = 108 (0x6C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Player_Inventory_Slot">Set Player Inventory Slot</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot</td><td>VarInt</td><td>Index of the slot to be modified in the player inventory. Not a container window slot index, to the survival inventory or any other window—there is no crafting grid, and the slot order is different.</td></tr>
 *     <tr><td>1</td><td>Slot Data</td><td>Slot</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetPlayerInventoryPacket(int slot, Object slotData) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_PLAYER_INVENTORY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(slot);
        // TODO: write slotData (Slot)
    }
}
