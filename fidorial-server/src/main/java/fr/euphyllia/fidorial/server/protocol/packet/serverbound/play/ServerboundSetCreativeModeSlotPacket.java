package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>While the user is in the standard inventory (i.e., not a crafting bench) in Creative mode, the player will send this packet. Clicking in the creative inventory menu is quite different from non-creative inventory management. Picking up an item with the mouse actually deletes the item from the server, and placing an item into a slot or dropping it out of the inventory actually tells the server to create the item from scratch. (This can be verified by clicking an item that you don't mind deleting, then severing the connection to the server; the item will be nowhere to be found when you log back in.) As a result of this implementation strategy, the "Destroy Item" slot is just a client-side implementation detail that means "I don't intend to recreate this item.". Additionally, the long listings of items (by category, etc.) are a client-side interface for choosing which item to create. Picking up an item from such listings sends no packets to the server; only when you put it somewhere does it tell the server to create the item in that location. This action can be described as "set inventory slot". Picking up an item sets the slot to item ID -1. Placing an item into an inventory slot sets the slot to the specified item. Dropping an item (by clicking outside the window) effectively sets slot -1 to the specified item, which causes the server to spawn the item entity, etc.. All other inventory slots are numbered the same as the non-creative inventory (including slots for the 2×2 crafting menu, even though they aren't visible in the vanilla client).</p>
 *
 * <p><b>Packet ID:</b> Play = 56 (0x38)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Creative_Mode_Slot">Set Creative Mode Slot</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot</td><td>Short</td><td>Inventory slot.</td></tr>
 *     <tr><td>1</td><td>Clicked Item</td><td>Slot</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetCreativeModeSlotPacket(short slot, int itemId, int count)
        implements ServerboundPacket {

    public static ServerboundSetCreativeModeSlotPacket read(PacketBuffer buf) {
        short slot = buf.readShort();
        int count = buf.readVarInt();
        int itemId = -1;
        if (count > 0) {
            itemId = buf.readVarInt();
        }
        System.out.println("PACKET READ");
        return new ServerboundSetCreativeModeSlotPacket(slot, itemId, count);
    }

    @Override
    public void handle(PacketListener listener) {
        System.out.println("HANDLED");
        ((PlayPacketListener) listener).handleSetCreativeModeSlot(this);
    }
}
