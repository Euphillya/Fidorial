package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundGameEventPacket(int event, float value) implements ClientboundPacket {

    public static final int START_WAITING_FOR_CHUNKS = 13;

    @Override
    public String name() {
        return PlayClientboundPackets.GAME_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeByte(event).writeFloat(value);
    }
}
