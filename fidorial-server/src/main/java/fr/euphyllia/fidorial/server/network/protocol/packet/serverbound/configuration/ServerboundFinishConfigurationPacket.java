package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundFinishConfigurationPacket() implements ServerboundPacket {

    public static ServerboundFinishConfigurationPacket read(final PacketBuffer buf) {
        return new ServerboundFinishConfigurationPacket();
    }

    @Override
    public void handle(final PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleFinishConfiguration(this);
    }
}
