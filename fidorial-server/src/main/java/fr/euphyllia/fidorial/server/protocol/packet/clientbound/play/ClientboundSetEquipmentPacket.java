package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Equipment slot can be one of the following:</p>
 *
 * <p><b>Packet ID:</b> Play = 102 (0x66)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Equipment">Set Equipment</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>Entity's ID.</td></tr>
 *     <tr><td>1</td><td>Equipment</td><td>Slot</td><td>Array</td></tr>
 *     <tr><td>2</td><td>Item</td><td>Slot</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetEquipmentPacket(int entityId, Object equipment, Object item) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_EQUIPMENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        // TODO: write equipment (Slot)
        // TODO: write item (Slot)
    }
}
