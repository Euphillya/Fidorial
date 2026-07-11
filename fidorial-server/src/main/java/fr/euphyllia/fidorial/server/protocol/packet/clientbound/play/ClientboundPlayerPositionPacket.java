package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundPlayerPositionPacket(int teleportId, double x, double y, double z)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_POSITION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(teleportId);
        buf.writeDouble(x).writeDouble(y).writeDouble(z);
        buf.writeDouble(0).writeDouble(0).writeDouble(0);   // velocite
        buf.writeFloat(0f).writeFloat(0f);                  // yaw / pitch
        buf.writeInt(0);                                    // flags (tout absolu)
    }
}
