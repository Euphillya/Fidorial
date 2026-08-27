package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.LoginPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundKeyPacket(byte[] encryptedSecret, byte[] encryptedToken) implements ServerboundPacket {

    public static ServerboundKeyPacket read(final PacketBuffer buf) {
        final byte[] encryptedSecret = buf.readByteArray(256);
        final byte[] encryptedToken = buf.readByteArray(256);
        return new ServerboundKeyPacket(encryptedSecret, encryptedToken);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((LoginPacketListener) listener).handleKey(this);
    }
}
