package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.*;

public interface PlayPacketListener extends PacketListener {
    void handlePlayerLoaded(ServerboundPlayerLoadedPacket packet);

    void handleAcceptTeleportation(ServerboundAcceptTeleportationPacket packet);

    void handleKeepAlive(ServerboundKeepAlivePacket packet);

    void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket packet);

    void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet);

    void handleUseItemOn(ServerboundUseItemOnPacket packet);

    void handlePlayerAction(ServerboundPlayerActionPacket packet);

    void handleMovePlayerPos(ServerboundMovePlayerPosPacket packet);

    void handleMovePlayerPosRot(ServerboundMovePlayerPosRotPacket packet);

    void handleClientInformation(ServerboundClientInformationPacket packet);

    void handleChatCommand(ServerboundChatCommandPacket packet);
}
