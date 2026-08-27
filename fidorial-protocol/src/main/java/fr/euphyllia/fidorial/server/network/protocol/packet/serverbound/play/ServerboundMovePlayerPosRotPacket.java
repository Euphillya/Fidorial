package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundMovePlayerPosRotPacket(double x, double y, double z,
                                                float yaw, float pitch, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosRotPacket read(final PacketBuffer buf) {
        final double x = buf.readDouble();
        final double y = buf.readDouble();
        final double z = buf.readDouble();
        final float yaw = buf.readFloat();
        final float pitch = buf.readFloat();
        final int flags = buf.readUByte();
        return new ServerboundMovePlayerPosRotPacket(x, y, z, yaw, pitch, flags);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPosRot(this);
    }
}
