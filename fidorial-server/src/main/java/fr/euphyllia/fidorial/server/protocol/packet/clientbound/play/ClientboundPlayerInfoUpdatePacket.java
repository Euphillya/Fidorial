package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.fidorial.entity.PlayerProfile;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundPlayerInfoUpdatePacket(PlayerProfile profile, int gameMode, int ping)
        implements ClientboundPacket {

    private static final int ACTIONS = 0x01 | 0x04 | 0x08 | 0x10; // add_player | game_mode | listed | latency

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_INFO_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeByte(ACTIONS);
        buf.writeVarInt(1);
        buf.writeUuid(profile.uuid());

        buf.writeString(profile.name());
        buf.writeVarInt(profile.properties().size());
        for (PlayerProfile.Property property : profile.properties()) {
            buf.writeString(property.name());
            buf.writeString(property.value());
            boolean signed = property.signature() != null;
            buf.writeBoolean(signed);
            if (signed) {
                buf.writeString(property.signature());
            }
        }

        buf.writeVarInt(gameMode);
        buf.writeBoolean(true);
        buf.writeVarInt(ping);
    }
}