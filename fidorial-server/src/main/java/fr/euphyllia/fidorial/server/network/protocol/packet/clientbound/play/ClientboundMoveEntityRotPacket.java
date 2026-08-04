package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Entity_Rotation
public record ClientboundMoveEntityRotPacket(int entityId, float yaw, float pitch, boolean onGround)
        implements ClientboundPacket {

    @Override
    public Key name() {
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
