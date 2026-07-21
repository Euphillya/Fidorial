package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sets attributes on the given entity. Modifier Data structure: The operation controls how the base value of the modifier is changed. All of the 0's are applied first, and then the 1's, and then the 2's.</p>
 *
 * <p><b>Packet ID:</b> Play = 131 (0x83)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Attributes">Update Attributes</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Property</td><td>Id</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Value</td><td>Double</td><td>See below.</td></tr>
 *     <tr><td>3</td><td>Modifiers</td><td>Prefixed Array of Modifier Data</td><td>See Attribute#Modifiers . Modifier Data defined below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundUpdateAttributesPacket(int entityId, Object property, double value,
                                                Object modifiers) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.UPDATE_ATTRIBUTES;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        // TODO: write property (Id)
        buf.writeDouble(value);
        // TODO: write modifiers (Prefixed Array of Modifier Data)
    }
}
