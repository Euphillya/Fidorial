package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>Updates the player's XYZ position on the server. If the player is in a vehicle, the position is ignored (but in case of Set Player Position and Rotation , the rotation is still used as normal). No validation steps other than value range clamping are performed in this case. If the player is sleeping, the position (or rotation) is not changed, and a Synchronize Player Position is sent if the received position deviated from the server's view by more than a meter. The vanilla server silently clamps the x and z coordinates between -30,000,000 and 30,000,000, and the y coordinate between -20,000,000 and 20,000,000. A similar condition has historically caused a kick for "Illegal position"; this is no longer the case. However, infinite or NaN coordinates (or angles) still result in a kick for multiplayer.disconnect.invalid_player_movement . As of 1.20.6, checking for moving too fast is achieved like this (sic): If the player is moving too fast, it is logged that "&lt;player&gt; moved too quickly! " followed by the change in x, y, and z, and the player is teleported back to their current (before this packet) server-side position. Checking for block collisions is achieved like this: Checking for illegal flight is achieved like this:</p>
 *
 * <p><b>Packet ID:</b> Play = 30 (0x1E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Player_Position">Set Player Position</a></p>
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
 *     <tr><td>3</td><td>Flags</td><td>Byte</td><td>Bit field: 0x01: on ground, 0x02: pushing against wall.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundMovePlayerPosPacket(double x, double y, double z, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosPacket read(PacketBuffer buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        int flags = buf.readUByte();
        return new ServerboundMovePlayerPosPacket(x, y, z, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPos(this);
    }
}
