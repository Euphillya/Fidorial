package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>This packet is used for a number of actions and animations performed by blocks, usually non-persistent.</p>
 * <p>The client ignores the provided block type and instead uses the block state in their world. See Java Edition protocol/Block actions for a list of values.</p>
 *
 * <p><b>Packet ID:</b> Play = 7 (0x07)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Block_Action">Block Action</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block coordinates.</td></tr>
 *     <tr><td>1</td><td>Action ID (Byte 1)</td><td>Unsigned Byte</td><td>Varies depending on block — see Java Edition protocol/Block actions .</td></tr>
 *     <tr><td>2</td><td>Action Parameter (Byte 2)</td><td>Unsigned Byte</td><td>Varies depending on block — see Java Edition protocol/Block actions .</td></tr>
 *     <tr><td>3</td><td>Block Type</td><td>VarInt</td><td>ID in the minecraft:block registry. This value is unused by the vanilla client, as it will infer the type of block based on the given position.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBlockEventPacket(BlockPos location, int actionIdByte1, int actionParameterByte2,
                                          int blockType) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(location.x(), location.y(), location.z());
        buf.writeByte(actionIdByte1);
        buf.writeByte(actionParameterByte2);
        buf.writeVarInt(blockType);
    }
}
