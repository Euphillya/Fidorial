package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Notifies the server that the chunk batch has been received by the client. The server uses the value sent in this packet to adjust the number of chunks to be sent in a batch. The vanilla server will stop sending further chunk data until the client acknowledges the sent chunk batch. After the first acknowledgement, the server adjusts this number to allow up to 10 unacknowledged batches.</p>
 *
 * <p><b>Packet ID:</b> Play = 11 (0x0B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chunk_Batch_Received">Chunk Batch Received</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Chunks per tick</td><td>Float</td><td>Desired chunks per tick.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChunkBatchReceivedPacket(float chunksPerTick) implements ServerboundPacket {

    public static ServerboundChunkBatchReceivedPacket read(PacketBuffer buf) {
        float chunksPerTick = 0f;
        chunksPerTick = buf.readFloat();
        return new ServerboundChunkBatchReceivedPacket(chunksPerTick);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
