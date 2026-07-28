package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.serverbound.handshake.ServerboundIntentionPacket;
import fr.fidorial.protocol.PacketListener;

public interface HandshakePacketListener extends PacketListener {
    void handleIntention(ServerboundIntentionPacket packet);
}
