package fr.euphyllia.fidorial.server.network.protocol.packet.listener;

import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.status.ServerboundPingRequestPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.status.ServerboundStatusRequestPacket;
import fr.fidorial.protocol.PacketListener;

public interface StatusPacketListener extends PacketListener {
    void handleStatusRequest(ServerboundStatusRequestPacket packet);

    void handlePingRequest(ServerboundPingRequestPacket packet);
}
