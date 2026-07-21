package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>The server simulation distance is used by the vanilla client only to determine whether the death animation of an entity should progress. If the integer chessboard distance between the chunk the local player is in (not the chunk cache center) and the chunk the entity is in is greater than the simulation distance, the animation will be paused.</p>
 *
 * <p><b>Packet ID:</b> Play = 111 (0x6F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Simulation_Distance">Set Simulation Distance</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Simulation Distance</td><td>VarInt</td><td>Server simulation distance.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetSimulationDistancePacket(int simulationDistance) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_SIMULATION_DISTANCE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(simulationDistance);
    }
}
