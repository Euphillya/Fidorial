package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundChatCommandPacket(String command) implements ServerboundPacket {

    private static final int MAX_LENGTH = 32767;

    public static ServerboundChatCommandPacket read(final PacketBuffer buf) {
        return new ServerboundChatCommandPacket(buf.readString(MAX_LENGTH));
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleChatCommand(this);
    }
}
