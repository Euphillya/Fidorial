package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundSetEntityMotionPacket(int entityId,
                                               double velocityX, double velocityY, double velocityZ)
        implements ClientboundPacket {

    private static final double MAX = 3.9;

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_MOTION;
    }

    private static short quantize(double v) {
        return (short) (Math.clamp(v, -MAX, MAX) * 8000.0);
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeShort(quantize(velocityX));
        buf.writeShort(quantize(velocityY));
        buf.writeShort(quantize(velocityZ));
    }
}
