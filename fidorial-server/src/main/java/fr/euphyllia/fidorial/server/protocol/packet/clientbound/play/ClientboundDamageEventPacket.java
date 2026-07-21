package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 25 (0x19)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Damage_Event">Damage Event</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>The ID of the entity taking damage</td></tr>
 *     <tr><td>1</td><td>Source Type ID</td><td>VarInt</td><td>The type of damage in the minecraft:damage_type registry, defined by the Registry Data packet.</td></tr>
 *     <tr><td>2</td><td>Source Cause ID</td><td>VarInt</td><td>The ID + 1 of the entity responsible for the damage, if present. If not present, the value is 0</td></tr>
 *     <tr><td>3</td><td>Source Direct ID</td><td>VarInt</td><td>The ID + 1 of the entity that directly dealt the damage, if present. If not present, the value is 0. If this field is present: and damage was dealt indirectly, such as by the use of a projectile, this field will contain the ID of such projectile; and damage was dealt directly, such as by manually attacking, this field will contain the same value as Source Cause ID.</td></tr>
 *     <tr><td>4</td><td>Source Position</td><td>X</td><td>Prefixed Optional</td></tr>
 *     <tr><td>5</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundDamageEventPacket(int entityId, int sourceTypeId, int sourceCauseId, int sourceDirectId,
                                           Object sourcePosition, double y, double z) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DAMAGE_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeVarInt(sourceTypeId);
        buf.writeVarInt(sourceCauseId);
        buf.writeVarInt(sourceDirectId);
        // TODO: write sourcePosition (X)
        buf.writeDouble(y);
        buf.writeDouble(z);
    }
}
