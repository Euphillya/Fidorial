package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundKeepAlivePacket(long id) implements ServerboundPacket {

    public static ServerboundKeepAlivePacket read(final PacketBuffer buf) {
        return new ServerboundKeepAlivePacket(buf.readLong());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleKeepAlive(this);
    }
}
