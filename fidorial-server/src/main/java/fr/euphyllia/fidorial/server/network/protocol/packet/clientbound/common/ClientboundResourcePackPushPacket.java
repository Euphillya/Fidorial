package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Add_Resource_Pack
public record ClientboundResourcePackPushPacket(
        Key packetName,
        UUID id,
        String url,
        String hash,
        boolean forced,
        @Nullable Component promptMessage
) implements ClientboundPacket {

    @Override
    public Key name() {
        return packetName;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUuid(id);
        buf.writeString(url);
        buf.writeString(hash);
        buf.writeBoolean(forced);
        buf.writeBoolean(promptMessage != null);
        if (promptMessage != null) {
            buf.writeComponent(promptMessage);
        }
    }
}
