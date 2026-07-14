package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundChatCommandPacket(String command) implements ServerboundPacket {

    private static final int MAX_LENGTH = 32767;

    public static ServerboundChatCommandPacket read(PacketBuffer buf) {
        return new ServerboundChatCommandPacket(buf.readString(MAX_LENGTH));
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleChatCommand(this);
    }
}
