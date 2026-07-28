package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

public record ServerboundAcceptTeleportationPacket(int teleportId) implements ServerboundPacket {

    public static ServerboundAcceptTeleportationPacket read(final PacketBuffer buf) {
        return new ServerboundAcceptTeleportationPacket(buf.readVarInt());
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleAcceptTeleportation(this);
    }
}
