package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundKeepAlivePacket(long id) implements ServerboundPacket {

    public static ServerboundKeepAlivePacket read(PacketBuffer buf) {
        return new ServerboundKeepAlivePacket(buf.readLong());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleKeepAlive(this);
    }
}
