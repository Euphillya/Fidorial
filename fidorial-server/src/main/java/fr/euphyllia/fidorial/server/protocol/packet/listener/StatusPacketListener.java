package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundPingRequestPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundStatusRequestPacket;

public interface StatusPacketListener extends PacketListener {
    void handleStatusRequest(ServerboundStatusRequestPacket packet);

    void handlePingRequest(ServerboundPingRequestPacket packet);
}
