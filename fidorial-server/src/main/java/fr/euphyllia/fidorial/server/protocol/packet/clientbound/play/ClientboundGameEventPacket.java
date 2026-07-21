package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Game_Event
public record ClientboundGameEventPacket(int event, float value) implements ClientboundPacket {

    public static final int END_RAINING = 1;
    public static final int BEGIN_RAINING = 2;
    public static final int CHANGE_GAME_MODE = 3;
    public static final int RAIN_LEVEL_CHANGE = 7;
    public static final int THUNDER_LEVEL_CHANGE = 8;
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
