package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundPlayerAbilitiesPacket(boolean flying)
        implements ServerboundPacket {

    public static final byte FLAG_FLYING = 0x02;

    public static ServerboundPlayerAbilitiesPacket read(PacketBuffer buf) {
        byte flags = buf.readByte();
        return new ServerboundPlayerAbilitiesPacket(
                (flags & FLAG_FLYING) != 0
        );
    }

    public boolean isFlying() {
        return flying;
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerAbilities(this);
    }
}
