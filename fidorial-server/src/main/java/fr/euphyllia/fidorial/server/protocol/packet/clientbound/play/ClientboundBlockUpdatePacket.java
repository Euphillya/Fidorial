package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>Fired whenever a block is changed within the render distance.</p>
 *
 * <p><b>Packet ID:</b> Play = 8 (0x08)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Block_Update">Block Update</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block Coordinates.</td></tr>
 *     <tr><td>1</td><td>Block ID</td><td>VarInt</td><td>The new block state ID for the block as given in the global block state palette .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBlockUpdatePacket(BlockPos pos, int blockStateId)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(pos.x(), pos.y(), pos.z());
        buf.writeVarInt(blockStateId);
    }
}
