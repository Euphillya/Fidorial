package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundMoveEntityPosRotPacket(int entityId, short deltaX, short deltaY, short deltaZ,
                                                float yaw, float pitch, boolean onGround)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MOVE_ENTITY_POS_ROT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeShort(deltaX);
        buf.writeShort(deltaY);
        buf.writeShort(deltaZ);
        buf.writeAngle(yaw);
        buf.writeAngle(pitch);
        buf.writeBoolean(onGround);
    }
}
