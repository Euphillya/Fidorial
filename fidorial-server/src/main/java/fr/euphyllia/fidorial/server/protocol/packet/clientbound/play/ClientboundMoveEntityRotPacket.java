package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Entity_Rotation
public record ClientboundMoveEntityRotPacket(int entityId, float yaw, float pitch, boolean onGround)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MOVE_ENTITY_ROT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeAngle(yaw);
        buf.writeAngle(pitch);
        buf.writeBoolean(onGround);
    }
}
