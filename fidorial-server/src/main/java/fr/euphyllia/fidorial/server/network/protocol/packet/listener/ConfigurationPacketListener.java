package fr.euphyllia.fidorial.server.network.protocol.packet.listener;

import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common.ServerboundCustomClickActionPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.fidorial.protocol.PacketListener;

public interface ConfigurationPacketListener extends PacketListener {
    void handleSelectKnownPacks(ServerboundSelectKnownPacksPacket packet);

    void handleFinishConfiguration(ServerboundFinishConfigurationPacket packet);

    void handleCustomClickAction(ServerboundCustomClickActionPacket packet);

    void handleClientInformation(ServerboundClientInformationPacket packet);
}
