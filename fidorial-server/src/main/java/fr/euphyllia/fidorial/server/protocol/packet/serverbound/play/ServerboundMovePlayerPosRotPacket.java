package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundMovePlayerPosRotPacket(double x, double y, double z,
                                                float yaw, float pitch, int flags)
        implements ServerboundPacket {

    public static ServerboundMovePlayerPosRotPacket read(PacketBuffer buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float yaw = buf.readFloat();
        float pitch = buf.readFloat();
        int flags = buf.readUByte();
        return new ServerboundMovePlayerPosRotPacket(x, y, z, yaw, pitch, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleMovePlayerPosRot(this);
    }
}
