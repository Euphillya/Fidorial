package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundSetCarriedItemPacket(int slot) implements ServerboundPacket {

    public static ServerboundSetCarriedItemPacket read(final PacketBuffer buf) {
        return new ServerboundSetCarriedItemPacket(buf.readShort());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleSetCarriedItem(this);
    }
}
