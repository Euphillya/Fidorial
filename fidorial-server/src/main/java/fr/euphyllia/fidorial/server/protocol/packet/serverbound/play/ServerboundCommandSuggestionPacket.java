package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundCommandSuggestionPacket(int id, String text) implements ServerboundPacket {

    private static final int MAX_LENGTH = 32500;

    public static ServerboundCommandSuggestionPacket read(PacketBuffer buf) {
        return new ServerboundCommandSuggestionPacket(
                buf.readVarInt(),
                buf.readString(MAX_LENGTH)
        );
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleCommandSuggestion(this);
    }
}
