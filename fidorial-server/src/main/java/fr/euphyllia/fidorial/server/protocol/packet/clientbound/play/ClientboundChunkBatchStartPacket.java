package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Marks the start of a chunk batch. The vanilla client marks and stores the time it receives this packet.</p>
 *
 * <p><b>Packet ID:</b> Play = 12 (0x0C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chunk_Batch_Start">Chunk Batch Start</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>no fields</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundChunkBatchStartPacket(Object noFields) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CHUNK_BATCH_START;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write noFields ()
    }
}
