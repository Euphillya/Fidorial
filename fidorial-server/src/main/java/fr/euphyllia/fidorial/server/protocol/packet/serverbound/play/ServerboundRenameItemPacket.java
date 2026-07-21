package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent as a player is renaming an item in an anvil (each keypress in the anvil UI sends a new Rename Item packet). If the new name is empty, then the item loses its custom name (this is different from setting the custom name to the normal name of the item). The item name may be no longer than 50 characters, and if it is longer than that, then the rename is silently ignored.</p>
 *
 * <p><b>Packet ID:</b> Play = 48 (0x30)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Rename_Item">Rename Item</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Item name</td><td>String (32767)</td><td>The new name of the item.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundRenameItemPacket(String itemName) implements ServerboundPacket {

    public static ServerboundRenameItemPacket read(PacketBuffer buf) {
        String itemName = null;
        itemName = buf.readString(32767);
        return new ServerboundRenameItemPacket(itemName);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
