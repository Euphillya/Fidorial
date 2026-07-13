package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundMovePlayerPosPacket(double x, double y, double z, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosPacket read(PacketBuffer buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        int flags = buf.readUByte();
        return new ServerboundMovePlayerPosPacket(x, y, z, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPos(this);
    }
}
