package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Hurt_Animation
public record ClientboundHurtAnimationPacket(int entityId, float yaw) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.HURT_ANIMATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeFloat(yaw);
    }
}
