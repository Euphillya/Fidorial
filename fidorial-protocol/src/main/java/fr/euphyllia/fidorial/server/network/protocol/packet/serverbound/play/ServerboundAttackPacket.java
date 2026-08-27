package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

/**
 * Sent when the player left-clicks an entity.
 *
 * <p><a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Attack">Attack</a></p>
 */
public record ServerboundAttackPacket(int entityId) implements ServerboundPacket {

    public static ServerboundAttackPacket read(final PacketBuffer buf) {
        return new ServerboundAttackPacket(buf.readVarInt());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleAttack(this);
    }
}
