package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent whenever an entity should change animation. Animation can be one of the following values:</p>
 *
 * <p><b>Packet ID:</b> Play = 2 (0x02)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Entity_Animation">Entity Animation</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>Player ID.</td></tr>
 *     <tr><td>1</td><td>Animation</td><td>Unsigned Byte</td><td>Animation ID (see below).</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundAnimatePacket(int entityId, int animation) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.ANIMATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeByte(animation);
    }
}
