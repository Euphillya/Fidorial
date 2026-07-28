package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundCustomClickActionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.fidorial.protocol.PacketListener;

public interface ConfigurationPacketListener extends PacketListener {
    void handleSelectKnownPacks(ServerboundSelectKnownPacksPacket packet);

    void handleFinishConfiguration(ServerboundFinishConfigurationPacket packet);

    void handleCustomClickAction(ServerboundCustomClickActionPacket packet);

    void handleClientInformation(ServerboundClientInformationPacket packet);
}
