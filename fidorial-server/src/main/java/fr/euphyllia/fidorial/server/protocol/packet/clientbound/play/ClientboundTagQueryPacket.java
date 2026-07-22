package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent in response to Query Block Entity Tag or Query Entity Tag .</p>
 *
 * <p><b>Packet ID:</b> Play = 123 (0x7B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Tag_Query_Response">Tag Query Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Transaction ID</td><td>VarInt</td><td>Can be compared to the one sent in the original query packet.</td></tr>
 *     <tr><td>1</td><td>NBT</td><td>NBT</td><td>The NBT of the block or entity. May be a TAG_END (0), in which case no NBT is present.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTagQueryPacket(int transactionId, Object nbt) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TAG_QUERY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(transactionId);
        // TODO: write nbt (NBT)
    }
}
