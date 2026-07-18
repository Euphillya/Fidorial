package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

public record ServerboundCustomQueryAnswerPacket(int transactionId, boolean understood, byte[] payload)
        implements ServerboundPacket {

    public static ServerboundCustomQueryAnswerPacket read(PacketBuffer buf) {
        int transactionId = buf.readVarInt();
        boolean understood = buf.readBoolean();
        byte[] payload = understood ? buf.readRemainingBytes() : new byte[0];
        return new ServerboundCustomQueryAnswerPacket(transactionId, understood, payload);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleCustomQueryAnswer(this);
    }
}
