package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;

public interface ConfigurationPacketListener extends PacketListener {
    void handleSelectKnownPacks(ServerboundSelectKnownPacksPacket packet);

    void handleFinishConfiguration(ServerboundFinishConfigurationPacket packet);
}
