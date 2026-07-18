package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import net.kyori.adventure.text.Component;

import java.util.BitSet;

public record ServerboundChatPacket(
        Component message,
        long timestamp,
        long salt,
        byte[] signature,
        int messageCount,
        BitSet acknowledged,
        byte checksum
) implements ServerboundPacket {

    private static final int MAX_LENGTH = 256;

    public static ServerboundChatPacket read(PacketBuffer buf) {
        Component message = buf.readComponent(MAX_LENGTH);
        long timestamp = buf.readLong();
        long salt = buf.readLong();
        byte[] sig = buf.readOptionalByteArray(MAX_LENGTH);
        int count = buf.readVarInt();
        BitSet acknowledged = buf.readFixedBitSet(20);
        byte checksum = buf.readByte();
        return new ServerboundChatPacket(
                message,
                timestamp,
                salt,
                sig,
                count,
                acknowledged,
                checksum
        );
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleChat(this);
    }
}
