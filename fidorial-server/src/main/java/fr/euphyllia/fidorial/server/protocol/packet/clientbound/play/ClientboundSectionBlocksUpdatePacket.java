package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Fired whenever 2 or more blocks are changed within the same chunk on the same tick. Chunk section position is encoded: and decoded: Blocks are encoded: and decoded:</p>
 *
 * <p><b>Packet ID:</b> Play = 84 (0x54)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Section_Blocks">Update Section Blocks</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Chunk section position</td><td>Long</td><td>Chunk section coordinate (encoded chunk x and z with each 22 bits, and section y with 20 bits, from left to right).</td></tr>
 *     <tr><td>1</td><td>Blocks</td><td>Prefixed Array of VarLong</td><td>Each entry is composed of the block state id, shifted left by 12, and the relative block position in the chunk section (4 bits for x, z, and y, from left to right).</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSectionBlocksUpdatePacket(long chunkSectionPosition,
                                                   Object blocks) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SECTION_BLOCKS_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(chunkSectionPosition);
        // TODO: write blocks (Prefixed Array of VarLong)
    }
}
