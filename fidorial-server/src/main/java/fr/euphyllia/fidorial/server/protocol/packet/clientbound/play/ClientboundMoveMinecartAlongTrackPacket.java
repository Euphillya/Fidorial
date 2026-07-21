package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 55 (0x37)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Move_Minecart_Along_Track">Move Minecart Along Track</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Steps</td><td>X</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Velocity X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>5</td><td>Velocity Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Velocity Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Yaw</td><td>Angle</td><td>&nbsp;</td></tr>
 *     <tr><td>8</td><td>Pitch</td><td>Angle</td><td>&nbsp;</td></tr>
 *     <tr><td>9</td><td>Weight</td><td>Float</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundMoveMinecartAlongTrackPacket(int entityId, Object steps, double y, double z, double velocityX,
                                                      double velocityY, double velocityZ, Object yaw, Object pitch,
                                                      float weight) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MOVE_MINECART_ALONG_TRACK;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        // TODO: write steps (X)
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(velocityX);
        buf.writeDouble(velocityY);
        buf.writeDouble(velocityZ);
        // TODO: write yaw (Angle)
        // TODO: write pitch (Angle)
        buf.writeFloat(weight);
    }
}
