package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.codec.ResourcePackStatusCodec;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import net.kyori.adventure.resource.ResourcePackStatus;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Resource_Pack_Response
public record ServerboundResourcePackPacket(UUID id, ResourcePackStatus status) implements ServerboundPacket {

    public static ServerboundResourcePackPacket read(final PacketBuffer buf) {
        return new ServerboundResourcePackPacket(buf.readUuid(), ResourcePackStatusCodec.fromWireId(buf.readVarInt()));
    }

    @Override
    public void handle(final PacketListener listener) {
        ((PlayPacketListener) listener).handleResourcePackResponse(this);
    }
}
