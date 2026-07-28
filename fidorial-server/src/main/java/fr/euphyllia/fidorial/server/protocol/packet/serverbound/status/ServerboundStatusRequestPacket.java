package fr.euphyllia.fidorial.server.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundStatusRequestPacket() implements ServerboundPacket {

    public static ServerboundStatusRequestPacket read(final PacketBuffer buf) {
        return new ServerboundStatusRequestPacket();
    }

    @Override
    public void handle(final PacketListener listener) {
        ((StatusPacketListener) listener).handleStatusRequest(this);
    }
}
