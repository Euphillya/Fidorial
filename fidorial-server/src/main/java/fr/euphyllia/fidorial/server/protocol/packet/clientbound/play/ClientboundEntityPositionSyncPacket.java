package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This packet is sent by the server when an entity moves more than 8 blocks.</p>
 *
 * <p><b>Packet ID:</b> Play = 35 (0x23)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Teleport_Entity">Teleport Entity</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Velocity X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>5</td><td>Velocity Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Velocity Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Yaw</td><td>Float</td><td>Rotation on the X axis, in degrees.</td></tr>
 *     <tr><td>8</td><td>Pitch</td><td>Float</td><td>Rotation on the Y axis, in degrees.</td></tr>
 *     <tr><td>9</td><td>On Ground</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundEntityPositionSyncPacket(
        int entityId,
        double x,
        double y,
        double z,
        double velocityX,
        double velocityY,
        double velocityZ,
        float yaw,
        float pitch,
        boolean onGround)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.ENTITY_POSITION_SYNC;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(velocityX);
        buf.writeDouble(velocityY);
        buf.writeDouble(velocityZ);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(onGround);
    }
}
