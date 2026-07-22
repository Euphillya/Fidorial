package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 89 (0x59)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Border_Lerp_Size">Set Border Lerp Size</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Old Diameter</td><td>Double</td><td>Current length of a single side of the world border, in meters.</td></tr>
 *     <tr><td>1</td><td>New Diameter</td><td>Double</td><td>Target length of a single side of the world border, in meters.</td></tr>
 *     <tr><td>2</td><td>Speed</td><td>VarLong</td><td>Number of real-time milli seconds until New Diameter is reached. It appears that vanilla server does not sync world border speed to game ticks, so it gets out of sync with server lag. If the world border is not moving, this is set to 0.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetBorderLerpSizePacket(double oldDiameter, double newDiameter,
                                                 Object speed) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_BORDER_LERP_SIZE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(oldDiameter);
        buf.writeDouble(newDiameter);
        // TODO: write speed (VarLong)
    }
}
