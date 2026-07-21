package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Entity statuses generally trigger an animation for an entity.</p>
 * <p>The available statuses vary by the entity's type (and are available to subclasses of that type as well).</p>
 *
 * <p><b>Packet ID:</b> Play = 34 (0x22)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Entity_Event">Entity Event</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>Int</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Entity Status</td><td>Byte Enum</td><td>See Java Edition protocol/Entity statuses for a list of which statuses are valid for each type of entity.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundEntityEventPacket(int entityId, Object entityStatus) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.ENTITY_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(entityId);
        // TODO: write entityStatus (Byte Enum)
    }
}
