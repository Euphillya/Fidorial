package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.StatusPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundPingRequestPacket(long payload) implements ServerboundPacket {

    public static ServerboundPingRequestPacket read(final PacketBuffer buf) {
        return new ServerboundPingRequestPacket(buf.readLong());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((StatusPacketListener) listener).handlePingRequest(this);
    }
}
