package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Acknowledges a user-initiated block change. After receiving this packet, the client will display the block state sent by the server instead of the one predicted by the client.</p>
 *
 * <p><b>Packet ID:</b> Play = 4 (0x04)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Acknowledge_Block_Change">Acknowledge Block Change</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Sequence ID</td><td>VarInt</td><td>Represents the sequence to acknowledge; this is used for properly syncing block changes to the client after interactions.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBlockChangedAckPacket(int sequence) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_CHANGED_ACK;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(sequence);
    }
}
