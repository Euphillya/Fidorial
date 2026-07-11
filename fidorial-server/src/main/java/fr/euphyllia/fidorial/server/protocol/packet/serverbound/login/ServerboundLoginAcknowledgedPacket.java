package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

public record ServerboundLoginAcknowledgedPacket() implements ServerboundPacket {

    public static ServerboundLoginAcknowledgedPacket read(PacketBuffer buf) {
        return new ServerboundLoginAcknowledgedPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleLoginAcknowledged(this);
    }
}
