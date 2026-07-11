package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;

public interface PlayPacketListener extends PacketListener {
    void handlePlayerLoaded(ServerboundPlayerLoadedPacket packet);

    void handleAcceptTeleportation(ServerboundAcceptTeleportationPacket packet);

    void handleKeepAlive(ServerboundKeepAlivePacket packet);
}
