package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

import java.util.UUID;

public record ServerboundHelloPacket(String username, UUID profileId) implements ServerboundPacket {

    public static ServerboundHelloPacket read(PacketBuffer buf) {
        String username = buf.readString(16);
        UUID profileId = buf.readUuid();
        return new ServerboundHelloPacket(username, profileId);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleHello(this);
    }
}
