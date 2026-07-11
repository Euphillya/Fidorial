package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;


public record ServerboundSelectKnownPacksPacket() implements ServerboundPacket {

    public static ServerboundSelectKnownPacksPacket read(PacketBuffer buf) {
        return new ServerboundSelectKnownPacksPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleSelectKnownPacks(this);
    }
}
