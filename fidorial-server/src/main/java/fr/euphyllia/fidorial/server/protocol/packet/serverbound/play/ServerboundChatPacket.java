package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import net.kyori.adventure.text.Component;

public record ServerboundChatPacket(Component message) implements ServerboundPacket {

    private static final int MAX_LENGTH = 65536;

    public static ServerboundChatPacket read(PacketBuffer buf) {
        return new ServerboundChatPacket(buf.readComponent(MAX_LENGTH));
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleChat(this);
    }
}
