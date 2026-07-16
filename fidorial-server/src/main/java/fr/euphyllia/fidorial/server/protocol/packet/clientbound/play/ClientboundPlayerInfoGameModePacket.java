package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.UUID;

public record ClientboundPlayerInfoGameModePacket(UUID uuid, int gameMode)
        implements ClientboundPacket {

    private static final int ACTIONS = 0x04;

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_INFO_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeByte(ACTIONS);
        buf.writeVarInt(1);
        buf.writeUuid(uuid);
        buf.writeVarInt(gameMode);
    }
}
