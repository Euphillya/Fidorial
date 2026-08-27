package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundCommandSuggestionPacket(int id, String text) implements ServerboundPacket {

    private static final int MAX_LENGTH = 32500;

    public static ServerboundCommandSuggestionPacket read(final PacketBuffer buf) {
        return new ServerboundCommandSuggestionPacket(buf.readVarInt(), buf.readString(MAX_LENGTH));
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleCommandSuggestion(this);
    }
}
