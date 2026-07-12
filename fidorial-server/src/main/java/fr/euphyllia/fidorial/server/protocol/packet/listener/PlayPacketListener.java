package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerActionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCarriedItemPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCreativeModeSlotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundUseItemOnPacket;

public interface PlayPacketListener extends PacketListener {
    void handlePlayerLoaded(ServerboundPlayerLoadedPacket packet);

    void handleAcceptTeleportation(ServerboundAcceptTeleportationPacket packet);

    void handleKeepAlive(ServerboundKeepAlivePacket packet);

    void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket packet);

    void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet);

    void handleUseItemOn(ServerboundUseItemOnPacket packet);

    void handlePlayerAction(ServerboundPlayerActionPacket packet);
}
