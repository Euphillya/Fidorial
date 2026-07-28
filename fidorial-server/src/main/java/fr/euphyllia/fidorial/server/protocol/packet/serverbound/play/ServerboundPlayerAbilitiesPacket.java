package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundPlayerAbilitiesPacket(boolean flying) implements ServerboundPacket {

    public static final byte FLAG_FLYING = 0x02;

    public static ServerboundPlayerAbilitiesPacket read(final PacketBuffer buf) {
        final byte flags = buf.readByte();
        return new ServerboundPlayerAbilitiesPacket((flags & FLAG_FLYING) != 0);
    }

    public boolean isFlying() {
        return flying;
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerAbilities(this);
    }
}
