package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundSetCreativeModeSlotPacket(short slot, int itemId, int count)
        implements ServerboundPacket {

    public static ServerboundSetCreativeModeSlotPacket read(final PacketBuffer buf) {
        final short slot = buf.readShort();
        final int count = buf.readVarInt();
        int itemId = -1;
        if (count > 0) {
            itemId = buf.readVarInt();
        }
        return new ServerboundSetCreativeModeSlotPacket(slot, itemId, count);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleSetCreativeModeSlot(this);
    }
}
