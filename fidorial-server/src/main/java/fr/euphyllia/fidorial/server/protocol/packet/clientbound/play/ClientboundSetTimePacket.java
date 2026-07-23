package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.List;

public record ClientboundSetTimePacket(long worldAge, List<Clock> clocks) implements ClientboundPacket {

    public ClientboundSetTimePacket {
        clocks = List.copyOf(clocks);
    }

    public static ClientboundSetTimePacket single(
            final long worldAge,
            final int clockId,
            final long time,
            final float fractionalTime,
            final float rate
    ) {
        return new ClientboundSetTimePacket(worldAge, List.of(new Clock(clockId, time, fractionalTime, rate)));
    }

    @Override
    public String name() {
        return PlayClientboundPackets.SET_TIME;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeLong(worldAge);
        buf.writeVarInt(clocks.size());
        for (final Clock clock : clocks) {
            buf.writeVarInt(clock.clockId());
            buf.writeVarLong(clock.time());
            buf.writeFloat(clock.fractionalTime());
            buf.writeFloat(clock.rate());
        }
    }

    public record Clock(int clockId, long time, float fractionalTime, float rate) {
    }
}
