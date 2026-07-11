package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

public record ServerboundKeyPacket(byte[] encryptedSecret, byte[] encryptedToken) implements ServerboundPacket {

    public static ServerboundKeyPacket read(PacketBuffer buf) {
        byte[] encryptedSecret = buf.readByteArray(256);
        byte[] encryptedToken = buf.readByteArray(256);
        return new ServerboundKeyPacket(encryptedSecret, encryptedToken);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleKey(this);
    }
}
