package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Bundle_Item_Selected">Bundle Item Selected</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot of Bundle</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Slot in Bundle</td><td>VarInt</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundBundleItemSelectedPacket(int slotOfBundle, int slotInBundle) implements ServerboundPacket {

    public static ServerboundBundleItemSelectedPacket read(PacketBuffer buf) {
        int slotOfBundle = 0;
        slotOfBundle = buf.readVarInt();
        int slotInBundle = 0;
        slotInBundle = buf.readVarInt();
        return new ServerboundBundleItemSelectedPacket(slotOfBundle, slotInBundle);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
