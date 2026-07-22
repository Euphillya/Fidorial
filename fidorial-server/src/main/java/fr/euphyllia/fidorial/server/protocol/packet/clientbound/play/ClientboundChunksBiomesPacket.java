package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Note: The order of X and Z is inverted, because the client reads them as one big-endian Long , with Z being the upper 32 bits.</p>
 *
 * <p><b>Packet ID:</b> Play = 13 (0x0D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chunk_Biomes">Chunk Biomes</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Chunk biome data</td><td>Chunk Z</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Chunk X</td><td>Int</td><td>Chunk coordinate (block coordinate divided by 16, rounded down)</td></tr>
 *     <tr><td>2</td><td>Data</td><td>Prefixed Array of Byte</td><td>Chunk data structure , with sections containing only the Biomes field</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundChunksBiomesPacket(Object chunkBiomeData, int chunkX,
                                            Object data) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CHUNKS_BIOMES;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write chunkBiomeData (Chunk Z)
        buf.writeInt(chunkX);
        // TODO: write data (Prefixed Array of Byte)
    }
}
