package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundRotateHeadPacket(int entityId, float headYaw) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.ROTATE_HEAD;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeAngle(headYaw);
    }
}
