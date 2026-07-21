package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Updates the direction the player is looking in. Yaw is measured in degrees and does not follow classical trigonometry rules. The unit circle of yaw on the XZ-plane starts at (0, 1) and turns counterclockwise, with 90 at (-1, 0), 180 at (0,-1) and 270 at (1, 0). Additionally, yaw is not clamped to between 0 and 360 degrees; any number is valid, including negative numbers and numbers greater than 360. Pitch is measured in degrees, where 0 is looking straight ahead, -90 is looking straight up, and 90 is looking straight down. The yaw and pitch of the player (in degrees), standing at point (x0, y0, z0) and looking towards point (x, y, z) can be calculated with: You can get a unit vector from a given yaw/pitch via:</p>
 *
 * <p><b>Packet ID:</b> Play = 32 (0x20)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Player_Rotation">Set Player Rotation</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Yaw</td><td>Float</td><td>Absolute rotation on the X Axis, in degrees.</td></tr>
 *     <tr><td>1</td><td>Pitch</td><td>Float</td><td>Absolute rotation on the Y Axis, in degrees.</td></tr>
 *     <tr><td>2</td><td>Flags</td><td>Byte</td><td>Bit field: 0x01: on ground, 0x02: pushing against wall.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundMovePlayerRotPacket(float yaw, float pitch, byte flags) implements ServerboundPacket {

    public static ServerboundMovePlayerRotPacket read(PacketBuffer buf) {
        float yaw = 0f;
        yaw = buf.readFloat();
        float pitch = 0f;
        pitch = buf.readFloat();
        byte flags = (byte) 0;
        flags = buf.readByte();
        return new ServerboundMovePlayerRotPacket(yaw, pitch, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
