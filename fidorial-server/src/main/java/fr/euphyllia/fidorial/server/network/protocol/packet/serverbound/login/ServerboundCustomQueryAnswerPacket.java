package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.LoginPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundCustomQueryAnswerPacket(int transactionId, boolean understood, byte[] payload)
        implements ServerboundPacket {

    public static ServerboundCustomQueryAnswerPacket read(final PacketBuffer buf) {
        final int transactionId = buf.readVarInt();
        final boolean understood = buf.readBoolean();
        final byte[] payload = understood ? buf.readRemainingBytes() : new byte[0];
        return new ServerboundCustomQueryAnswerPacket(transactionId, understood, payload);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((LoginPacketListener) listener).handleCustomQueryAnswer(this);
    }
}
