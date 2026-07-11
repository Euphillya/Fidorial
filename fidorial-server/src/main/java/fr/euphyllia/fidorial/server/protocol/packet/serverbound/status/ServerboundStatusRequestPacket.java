package fr.euphyllia.fidorial.server.protocol.packet.serverbound.status;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;

public record ServerboundStatusRequestPacket() implements ServerboundPacket {

    public static ServerboundStatusRequestPacket read(PacketBuffer buf) {
        return new ServerboundStatusRequestPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((StatusPacketListener) listener).handleStatusRequest(this);
    }
}
