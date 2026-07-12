package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundSetCarriedItemPacket(int slot) implements ServerboundPacket {

    public static ServerboundSetCarriedItemPacket read(PacketBuffer buf) {
        return new ServerboundSetCarriedItemPacket(buf.readShort());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleSetCarriedItem(this);
    }
}
