package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

public record ServerboundAcceptTeleportationPacket(int teleportId) implements ServerboundPacket {

    public static ServerboundAcceptTeleportationPacket read(PacketBuffer buf) {
        return new ServerboundAcceptTeleportationPacket(buf.readVarInt());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleAcceptTeleportation(this);
    }
}
