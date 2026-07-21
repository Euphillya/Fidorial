package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used for pick block functionality (middle click) on blocks to retrieve items from the inventory in survival or creative mode or create them in creative mode.</p>
 * <p>See Controls#Pick Block for more information.</p>
 *
 * <p><b>Packet ID:</b> Play = 36 (0x24)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Pick_Item_From_Block">Pick Item From Block</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>The location of the block.</td></tr>
 *     <tr><td>1</td><td>Include Data</td><td>Boolean</td><td>Used to tell the server to include block data in the new stack, works only if in creative mode.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPickItemFromBlockPacket(BlockPos location, boolean includeData) implements ServerboundPacket {

    public static ServerboundPickItemFromBlockPacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        boolean includeData = false;
        includeData = buf.readBoolean();
        return new ServerboundPickItemFromBlockPacket(location, includeData);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
