package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 107 (0x6B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Passengers">Set Passengers</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>Vehicle's EID.</td></tr>
 *     <tr><td>1</td><td>Passengers</td><td>Prefixed Array of VarInt</td><td>EIDs of entity's passengers.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetPassengersPacket(int entityId, Object passengers) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_PASSENGERS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        // TODO: write passengers (Prefixed Array of VarInt)
    }
}
