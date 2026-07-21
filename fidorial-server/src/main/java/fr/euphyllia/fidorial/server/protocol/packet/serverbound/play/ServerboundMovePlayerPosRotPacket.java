package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>A combination of Set Player Rotation and Set Player Position .</p>
 *
 * <p><b>Packet ID:</b> Play = 31 (0x1F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Player_Position_and_Rotation">Set Player Position and Rotation</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>X</td><td>Double</td><td>Absolute position.</td></tr>
 *     <tr><td>1</td><td>Feet Y</td><td>Double</td><td>Absolute feet position, normally Head Y - 1.62.</td></tr>
 *     <tr><td>2</td><td>Z</td><td>Double</td><td>Absolute position.</td></tr>
 *     <tr><td>3</td><td>Yaw</td><td>Float</td><td>Absolute rotation on the X Axis, in degrees.</td></tr>
 *     <tr><td>4</td><td>Pitch</td><td>Float</td><td>Absolute rotation on the Y Axis, in degrees.</td></tr>
 *     <tr><td>5</td><td>Flags</td><td>Byte</td><td>Bit field: 0x01: on ground, 0x02: pushing against wall.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundMovePlayerPosRotPacket(double x, double y, double z,
                                                float yaw, float pitch, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosRotPacket read(PacketBuffer buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float yaw = buf.readFloat();
        float pitch = buf.readFloat();
        int flags = buf.readUByte();
        return new ServerboundMovePlayerPosRotPacket(x, y, z, yaw, pitch, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPosRot(this);
    }
}
