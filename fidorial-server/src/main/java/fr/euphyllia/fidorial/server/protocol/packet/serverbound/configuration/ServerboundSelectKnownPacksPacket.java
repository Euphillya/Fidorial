package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;


public record ServerboundSelectKnownPacksPacket() implements ServerboundPacket {

    public static ServerboundSelectKnownPacksPacket read(final PacketBuffer buf) {
        return new ServerboundSelectKnownPacksPacket();
    }

    @Override
    public void handle(final PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleSelectKnownPacks(this);
    }
}
