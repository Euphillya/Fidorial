package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent by the server after login to specify the coordinates of the spawn point (the point at which players spawn at, and which the compass points to). It can be sent at any time to update the point compasses point at. Before receiving this packet, the client uses the default position 8, 64, 8, and angle 0.0.</p>
 *
 * <p><b>Packet ID:</b> Play = 97 (0x61)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Default_Spawn_Position">Set Default Spawn Position</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Dimension Name</td><td>Identifier</td><td>Name of spawn dimension.</td></tr>
 *     <tr><td>1</td><td>Location</td><td>Position</td><td>Spawn location.</td></tr>
 *     <tr><td>2</td><td>Yaw</td><td>Float</td><td>Yaw after respawning.</td></tr>
 *     <tr><td>3</td><td>Pitch</td><td>Float</td><td>Pitch after respawning.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetDefaultSpawnPositionPacket(String dimensionName, BlockPos location, float yaw,
                                                       float pitch) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_DEFAULT_SPAWN_POSITION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(dimensionName);
        buf.writePosition(location.x(), location.y(), location.z());
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
    }
}
