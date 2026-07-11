package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.handshake.ServerboundIntentionPacket;

public interface HandshakePacketListener extends PacketListener {
    void handleIntention(ServerboundIntentionPacket packet);
}
