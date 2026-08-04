package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

/**
 * Sent when the player clicks respawn on the death screen, or opens the statistics menu.
 *
 * <p><a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Client_Status">Client Status</a></p>
 */
public record ServerboundClientCommandPacket(int action) implements ServerboundPacket {

    public static final int PERFORM_RESPAWN = 0;
    public static final int REQUEST_STATS = 1;
    public static final int REQUEST_GAMERULE_VALUES = 2;

    public static ServerboundClientCommandPacket read(final PacketBuffer buf) {
        return new ServerboundClientCommandPacket(buf.readVarInt());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleClientCommand(this);
    }
}
