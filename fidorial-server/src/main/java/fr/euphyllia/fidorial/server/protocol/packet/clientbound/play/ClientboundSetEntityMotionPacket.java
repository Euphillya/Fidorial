package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Entity_Velocity
 */
public record ClientboundSetEntityMotionPacket(int entityId,
                                               double velocityX, double velocityY, double velocityZ)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_MOTION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeLpVec3(velocityX, velocityY, velocityZ);
    }
}