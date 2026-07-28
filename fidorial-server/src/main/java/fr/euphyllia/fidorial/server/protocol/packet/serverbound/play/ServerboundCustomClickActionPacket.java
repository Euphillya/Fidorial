package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.nbt.NetworkNbtHelper;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import net.kyori.adventure.key.Key;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Custom_Click_Action
public record ServerboundCustomClickActionPacket(Key id, Nbt payload) implements ServerboundPacket {

    private static final int MAX_PAYLOAD_BYTES = 1 << 15;

    public static ServerboundCustomClickActionPacket read(final PacketBuffer buf) {
        final Key id = Key.key(buf.readString(32767));
        final int payloadSize = buf.readVarInt();
        if (payloadSize < 0 || payloadSize > MAX_PAYLOAD_BYTES) {
            throw new IllegalStateException("Custom click payload too large: " + payloadSize + " bytes");
        }
        final int start = buf.nettyBuf().readerIndex();
        final Nbt payload = NetworkNbtHelper.readNbt(buf.nettyBuf(), payloadSize);
        final int consumed = buf.nettyBuf().readerIndex() - start;
        if (consumed != payloadSize) {
            throw new IllegalStateException(
                    "Custom click payload size mismatch: announced " + payloadSize + " but read " + consumed);
        }
        return new ServerboundCustomClickActionPacket(id, payload);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleCustomClickAction(this);
    }
}
