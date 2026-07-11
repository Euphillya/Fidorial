package fr.euphyllia.fidorial.server.protocol.packet.listener;

import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundKeyPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundLoginAcknowledgedPacket;

public interface LoginPacketListener extends PacketListener {
    void handleHello(ServerboundHelloPacket packet);

    void handleKey(ServerboundKeyPacket packet);

    void handleLoginAcknowledged(ServerboundLoginAcknowledgedPacket packet);
}
