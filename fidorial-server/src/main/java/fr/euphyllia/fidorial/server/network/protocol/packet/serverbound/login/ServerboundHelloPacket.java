package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.LoginPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

import java.util.UUID;

public record ServerboundHelloPacket(String username, UUID profileId) implements ServerboundPacket {

    public static ServerboundHelloPacket read(final PacketBuffer buf) {
        final String username = buf.readString(16);
        final UUID profileId = buf.readUuid();
        return new ServerboundHelloPacket(username, profileId);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((LoginPacketListener) listener).handleHello(this);
    }
}
