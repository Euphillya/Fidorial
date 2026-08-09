package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import net.kyori.adventure.resource.ResourcePackStatus;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Resource_Pack_Response
public record ServerboundResourcePackPacket(UUID id, ResourcePackStatus status) implements ServerboundPacket {

    // look at Result box under the packet
    private static final ResourcePackStatus[] BY_WIRE_ID = {
            ResourcePackStatus.SUCCESSFULLY_LOADED,
            ResourcePackStatus.DECLINED,
            ResourcePackStatus.FAILED_DOWNLOAD,
            ResourcePackStatus.ACCEPTED,
            ResourcePackStatus.DOWNLOADED,
            ResourcePackStatus.INVALID_URL,
            ResourcePackStatus.FAILED_RELOAD,
            ResourcePackStatus.DISCARDED
    };

    public static ServerboundResourcePackPacket read(final PacketBuffer buf) {
        final UUID id = buf.readUuid();
        final int wireId = buf.readVarInt();
        final ResourcePackStatus status =
                (wireId >= 0 && wireId < BY_WIRE_ID.length) ? BY_WIRE_ID[wireId] : ResourcePackStatus.DISCARDED;
        return new ServerboundResourcePackPacket(id, status);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleResourcePackResponse(this);
    }
}
