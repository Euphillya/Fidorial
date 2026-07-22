package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used when F3 + I is pressed while looking at a block.</p>
 *
 * <p><b>Packet ID:</b> Play = 2 (0x02)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Query_Block_Entity_Tag">Query Block Entity Tag</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Transaction ID</td><td>VarInt</td><td>An incremental ID so that the client can verify that the response matches.</td></tr>
 *     <tr><td>1</td><td>Location</td><td>Position</td><td>The location of the block to check.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundBlockEntityTagQueryPacket(int transactionId, BlockPos location) implements ServerboundPacket {

    public static ServerboundBlockEntityTagQueryPacket read(PacketBuffer buf) {
        int transactionId = 0;
        transactionId = buf.readVarInt();
        BlockPos location = null;
        location = buf.readPosition();
        return new ServerboundBlockEntityTagQueryPacket(transactionId, location);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
