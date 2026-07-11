package fr.euphyllia.fidorial.server.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;

public record ServerboundPingRequestPacket(long payload) implements ServerboundPacket {

    public static ServerboundPingRequestPacket read(PacketBuffer buf) {
        return new ServerboundPingRequestPacket(buf.readLong());
    }

    @Override
    public void handle(PacketListener listener) {
        ((StatusPacketListener) listener).handlePingRequest(this);
    }
}
