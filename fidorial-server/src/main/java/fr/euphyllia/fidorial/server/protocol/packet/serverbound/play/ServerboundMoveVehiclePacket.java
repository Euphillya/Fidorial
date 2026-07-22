package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when a player moves in a client-side-controlled vehicle. Fields are the same as in Set Player Position and Rotation . Note that all fields use absolute positioning and do not allow for relative positioning.</p>
 *
 * <p><b>Packet ID:</b> Play = 34 (0x22)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Move_Vehicle_(serverbound)">Move Vehicle (serverbound)</a></p>
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
 *     <tr><td>5</td><td>On Ground</td><td>Boolean</td><td>(This value does not seem to exist)</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundMoveVehiclePacket(double x, double y, double z, float yaw, float pitch,
                                           boolean onGround) implements ServerboundPacket {

    public static ServerboundMoveVehiclePacket read(PacketBuffer buf) {
        double x = 0d;
        x = buf.readDouble();
        double y = 0d;
        y = buf.readDouble();
        double z = 0d;
        z = buf.readDouble();
        float yaw = 0f;
        yaw = buf.readFloat();
        float pitch = 0f;
        pitch = buf.readFloat();
        boolean onGround = false;
        onGround = buf.readBoolean();
        return new ServerboundMoveVehiclePacket(x, y, z, yaw, pitch, onGround);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
