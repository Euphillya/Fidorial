package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundEntityPositionSyncPacket(int entityId,
                                                  double x, double y, double z,
                                                  double velocityX, double velocityY, double velocityZ,
                                                  float yaw, float pitch, boolean onGround)
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
