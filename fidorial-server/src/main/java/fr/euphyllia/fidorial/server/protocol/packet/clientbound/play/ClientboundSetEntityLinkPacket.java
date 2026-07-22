package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is sent when an entity has been leashed to another entity.</p>
 *
 * <p><b>Packet ID:</b> Play = 100 (0x64)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Link_Entities">Link Entities</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Attached Entity ID</td><td>Int</td><td>Attached entity's EID.</td></tr>
 *     <tr><td>1</td><td>Holding Entity ID</td><td>Int</td><td>ID of the entity holding the lead. Set to -1 to detach.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetEntityLinkPacket(int attachedEntityId, int holdingEntityId) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_LINK;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(attachedEntityId);
        buf.writeInt(holdingEntityId);
    }
}
