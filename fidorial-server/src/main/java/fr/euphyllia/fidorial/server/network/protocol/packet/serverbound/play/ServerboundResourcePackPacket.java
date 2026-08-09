package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import net.kyori.adventure.resource.ResourcePackStatus;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Resource_Pack_Response
public record ServerboundResourcePackPacket(UUID id, ResourcePackStatus status) implements ServerboundPacket {

    // look at Result box under the packet
    private static final ResourcePackStatus[] BY_WIRE_ID = {
            ResourcePackStatus.SUCCESSFULLY_LOADED, // 0
            ResourcePackStatus.DECLINED,             // 1
            ResourcePackStatus.FAILED_DOWNLOAD,      // 2
            ResourcePackStatus.ACCEPTED,             // 3
            ResourcePackStatus.DOWNLOADED,           // 4
            ResourcePackStatus.INVALID_URL,          // 5
            ResourcePackStatus.FAILED_RELOAD,        // 6
            ResourcePackStatus.DISCARDED             // 7
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
        ((PlayPacketListener) listener).handleResourcePackResponse(this);
    }
}
