package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Remove_Resource_Pack
public record ClientboundResourcePackPopPacket(Key packetName, @Nullable UUID id) implements ClientboundPacket {

    @Override
    public Key name() {
        return packetName;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeBoolean(id != null);
        if (id != null) {
            buf.writeUuid(id);
        }
    }
}
