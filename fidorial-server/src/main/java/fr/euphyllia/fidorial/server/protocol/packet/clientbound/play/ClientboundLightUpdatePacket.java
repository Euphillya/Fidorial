package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Updates light levels for a chunk.</p>
 * <p>See Light for information on how lighting works in Minecraft. A bit will never be set in both the block light mask and the empty block light mask, though it may be present in neither of them (if the block light does not need to be updated for the corresponding chunk section).</p>
 * <p>The same applies to the sky light mask and the empty sky light mask.</p>
 *
 * <p><b>Packet ID:</b> Play = 48 (0x30)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Light">Update Light</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Chunk X</td><td>VarInt</td><td>Chunk coordinate (block coordinate divided by 16, rounded down)</td></tr>
 *     <tr><td>1</td><td>Chunk Z</td><td>VarInt</td><td>Chunk coordinate (block coordinate divided by 16, rounded down)</td></tr>
 *     <tr><td>2</td><td>Data</td><td>Light Data</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundLightUpdatePacket(int chunkX, int chunkZ, Object data) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.LIGHT_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(chunkX);
        buf.writeVarInt(chunkZ);
        // TODO: write data (Light Data)
    }
}
