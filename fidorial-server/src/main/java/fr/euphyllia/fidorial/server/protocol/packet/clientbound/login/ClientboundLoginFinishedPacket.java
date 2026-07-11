package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.auth.GameProfile;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

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
