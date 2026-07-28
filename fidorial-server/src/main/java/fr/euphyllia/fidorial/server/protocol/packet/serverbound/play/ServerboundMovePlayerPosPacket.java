package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundMovePlayerPosPacket(double x, double y, double z, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosPacket read(final PacketBuffer buf) {
        final double x = buf.readDouble();
        final double y = buf.readDouble();
        final double z = buf.readDouble();
        final int flags = buf.readUByte();
        return new ServerboundMovePlayerPosPacket(x, y, z, flags);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPos(this);
    }
}
