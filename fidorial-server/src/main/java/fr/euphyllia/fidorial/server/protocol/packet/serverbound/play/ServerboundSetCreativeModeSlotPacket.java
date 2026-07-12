package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundSetCreativeModeSlotPacket(short slot, int itemId, int count)
        implements ServerboundPacket {

    public static ServerboundSetCreativeModeSlotPacket read(PacketBuffer buf) {
        short slot = buf.readShort();
        int count = buf.readVarInt();
        int itemId = -1;
        if (count > 0) {
            itemId = buf.readVarInt();
        }
        System.out.println("PACKET READ");
        return new ServerboundSetCreativeModeSlotPacket(slot, itemId, count);
    }

    @Override
    public void handle(PacketListener listener) {
        System.out.println("HANDLED");
        ((PlayPacketListener) listener).handleSetCreativeModeSlot(this);
    }
}
