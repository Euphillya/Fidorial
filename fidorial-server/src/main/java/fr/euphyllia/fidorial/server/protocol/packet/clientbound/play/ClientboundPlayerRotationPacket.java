package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 73 (0x49)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Rotation">Player Rotation</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Yaw</td><td>Float</td><td>Rotation on the X axis, in degrees.</td></tr>
 *     <tr><td>1</td><td>Relative Yaw</td><td>Boolean</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Pitch</td><td>Float</td><td>Rotation on the Y axis, in degrees.</td></tr>
 *     <tr><td>3</td><td>Relative Pitch</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerRotationPacket(float yaw, boolean relativeYaw, float pitch,
                                              boolean relativePitch) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_ROTATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeFloat(yaw);
        buf.writeBoolean(relativeYaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(relativePitch);
    }
}
