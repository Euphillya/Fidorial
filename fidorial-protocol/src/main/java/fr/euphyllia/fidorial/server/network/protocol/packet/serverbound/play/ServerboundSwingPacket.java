package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

/**
 * Sent every time the player swings an arm, whether anything was hit.
 *
 * <p><a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Swing_Arm">Swing Arm</a></p>
 */
public record ServerboundSwingPacket(int hand) implements ServerboundPacket {

    public static ServerboundSwingPacket read(final PacketBuffer buf) {
        return new ServerboundSwingPacket(buf.readVarInt());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleSwing(this);
    }
}
