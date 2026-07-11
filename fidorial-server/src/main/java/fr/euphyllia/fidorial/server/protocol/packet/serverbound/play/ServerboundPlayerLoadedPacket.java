package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundPlayerLoadedPacket() implements ServerboundPacket {

    public static ServerboundPlayerLoadedPacket read(PacketBuffer buf) {
        return new ServerboundPlayerLoadedPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerLoaded(this);
    }
}
