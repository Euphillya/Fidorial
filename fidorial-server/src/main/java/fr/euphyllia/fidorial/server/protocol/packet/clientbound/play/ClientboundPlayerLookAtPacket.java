package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Used to rotate the client player to face the given location or entity (for /teleport [&lt;targets&gt;] &lt;x&gt; &lt;y&gt; &lt;z&gt; facing ). If the entity given by entity ID cannot be found, this packet should be treated as if is entity was false.</p>
 *
 * <p><b>Packet ID:</b> Play = 71 (0x47)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Look_At">Look At</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Feet/eyes</td><td>VarInt Enum</td><td>Values are feet=0, eyes=1.  If set to eyes, aims using the head position; otherwise, aims using the feet position.</td></tr>
 *     <tr><td>1</td><td>Target x</td><td>Double</td><td>x coordinate of the point to face towards.</td></tr>
 *     <tr><td>2</td><td>Target y</td><td>Double</td><td>y coordinate of the point to face towards.</td></tr>
 *     <tr><td>3</td><td>Target z</td><td>Double</td><td>z coordinate of the point to face towards.</td></tr>
 *     <tr><td>4</td><td>Is entity</td><td>Boolean</td><td>If true, additional information about an entity is provided.</td></tr>
 *     <tr><td>5</td><td>Entity ID</td><td>Optional VarInt</td><td>Only if is entity is true — the entity to face towards.</td></tr>
 *     <tr><td>6</td><td>Entity feet/eyes</td><td>Optional VarInt Enum</td><td>Whether to look at the entity's eyes or feet.  Same values and meanings as before, just for the entity's head/feet.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerLookAtPacket(Object feetEyes, double targetX, double targetY, double targetZ,
                                            boolean isEntity, Object entityId,
                                            Object entityFeetEyes) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_LOOK_AT;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write feetEyes (VarInt Enum)
        buf.writeDouble(targetX);
        buf.writeDouble(targetY);
        buf.writeDouble(targetZ);
        buf.writeBoolean(isEntity);
        // TODO: write entityId (Optional VarInt)
        // TODO: write entityFeetEyes (Optional VarInt Enum)
    }
}
