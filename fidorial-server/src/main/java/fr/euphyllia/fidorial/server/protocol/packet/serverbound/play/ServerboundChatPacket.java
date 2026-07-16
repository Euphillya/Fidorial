package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundChatPacket(String message) implements ServerboundPacket {

    private static final int MAX_LENGTH = 256;

    public static ServerboundChatPacket read(PacketBuffer buf) {
        return new ServerboundChatPacket(buf.readString(MAX_LENGTH));
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleChat(this);
    }
}
