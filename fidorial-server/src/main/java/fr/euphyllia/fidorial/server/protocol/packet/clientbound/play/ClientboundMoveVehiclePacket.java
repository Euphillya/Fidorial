package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>If the player is riding a client-side-controlled vehicle, teleports the vehicle to the specified position. Sent by the vanilla server in response to serverbound Move Vehicle (serverbound) packets that fail the movement speed check. Note that all fields use absolute positioning and do not allow for relative positioning.</p>
 *
 * <p><b>Packet ID:</b> Play = 57 (0x39)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Move_Vehicle_(clientbound)">Move Vehicle (clientbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>X</td><td>Double</td><td>Absolute position (X coordinate).</td></tr>
 *     <tr><td>1</td><td>Y</td><td>Double</td><td>Absolute position (Y coordinate).</td></tr>
 *     <tr><td>2</td><td>Z</td><td>Double</td><td>Absolute position (Z coordinate).</td></tr>
 *     <tr><td>3</td><td>Yaw</td><td>Float</td><td>Absolute rotation on the vertical axis, in degrees.</td></tr>
 *     <tr><td>4</td><td>Pitch</td><td>Float</td><td>Absolute rotation on the horizontal axis, in degrees.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundMoveVehiclePacket(double x, double y, double z, float yaw,
                                           float pitch) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MOVE_VEHICLE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
    }
}
