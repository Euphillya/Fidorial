package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundLoginAcknowledgedPacket() implements ServerboundPacket {

    public static ServerboundLoginAcknowledgedPacket read(final PacketBuffer buf) {
        return new ServerboundLoginAcknowledgedPacket();
    }

    @Override
    public void handle(final PacketListener listener) {
        ((LoginPacketListener) listener).handleLoginAcknowledged(this);
    }
}
