package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.world.BlockPos;

public record ServerboundPlayerActionPacket(int status, BlockPos position, int face, int sequence)
        implements ServerboundPacket {

    public static final int START_DESTROY_BLOCK = 0;
    public static final int ABORT_DESTROY_BLOCK = 1;
    public static final int FINISH_DESTROY_BLOCK = 2;

    public static ServerboundPlayerActionPacket read(PacketBuffer buf) {
        int status = buf.readVarInt();
        BlockPos position = buf.readPosition();
        int face = buf.readUByte();
        int sequence = buf.readVarInt();
        return new ServerboundPlayerActionPacket(status, position, face, sequence);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerAction(this);
    }
}
