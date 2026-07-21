package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.auth.GameProfile;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Login = 2 (0x02)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Login_Success">Login Success</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Profile</td><td>Game Profile</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Session ID</td><td>UUID</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundLoginFinishedPacket(GameProfile profile) implements ClientboundPacket {

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_FINISHED;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeUuid(profile.uuid());
        buf.writeString(profile.name());
        buf.writeVarInt(profile.properties().size());
        for (GameProfile.Property prop : profile.properties()) {
            buf.writeString(prop.name());
            buf.writeString(prop.value());
            buf.writeBoolean(prop.signature() != null);
            if (prop.signature() != null) {
                buf.writeString(prop.signature());
            }
        }
        buf.writeUuid(profile.sessionId());
    }
}
