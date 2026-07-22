package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>Sets the block entity associated with the block at the given location.</p>
 *
 * <p><b>Packet ID:</b> Play = 6 (0x06)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Block_Entity_Data">Block Entity Data</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Type</td><td>VarInt</td><td>ID in the minecraft:block_entity_type registry</td></tr>
 *     <tr><td>2</td><td>NBT Data</td><td>NBT</td><td>Data to set.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBlockEntityDataPacket(BlockPos location, int type,
                                               Object nbtData) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_ENTITY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(location.x(), location.y(), location.z());
        buf.writeVarInt(type);
        // TODO: write nbtData (NBT)
    }
}
