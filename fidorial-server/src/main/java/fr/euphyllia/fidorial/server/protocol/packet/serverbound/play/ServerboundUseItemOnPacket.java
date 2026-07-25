package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.world.BlockPos;

public record ServerboundUseItemOnPacket(
        int hand,
        BlockPos target,
        int face,
        float cursorX,
        float cursorY,
        float cursorZ,
        boolean insideBlock,
        int sequence)
        implements ServerboundPacket {

    public static ServerboundUseItemOnPacket read(PacketBuffer buf) {
        int hand = buf.readVarInt();
        BlockPos target = buf.readPosition();
        int face = buf.readVarInt();
        float cursorX = buf.readFloat();
        float cursorY = buf.readFloat();
        float cursorZ = buf.readFloat();
        boolean insideBlock = buf.readBoolean();

        if (buf.readableBytes() > 0) {
            buf.readBoolean();
        }
        int sequence = buf.readableBytes() > 0 ? buf.readVarInt() : 0;
        return new ServerboundUseItemOnPacket(hand, target, face, cursorX, cursorY, cursorZ, insideBlock, sequence);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleUseItemOn(this);
    }
}
