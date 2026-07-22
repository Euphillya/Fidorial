package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Marks the end of a chunk batch. The vanilla client marks the time it receives this packet and calculates the elapsed duration since the beginning of the chunk batch . The client uses this duration and the batch size received in this packet to estimate the number of milliseconds elapsed per chunk received. This value is then used to calculate the desired number of chunks per tick through the formula 25 / millisPerChunk , which is reported to the server through Chunk Batch Received . This likely uses 25 instead of the normal tick duration of 50 so chunk processing will only use half of the client's and network's bandwidth. The vanilla client uses the samples from the latest 15 batches to estimate the milliseconds per chunk number.</p>
 *
 * <p><b>Packet ID:</b> Play = 11 (0x0B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chunk_Batch_Finished">Chunk Batch Finished</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Batch size</td><td>VarInt</td><td>Number of chunks.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundChunkBatchFinishedPacket(int batchSize) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CHUNK_BATCH_FINISHED;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(batchSize);
    }
}
