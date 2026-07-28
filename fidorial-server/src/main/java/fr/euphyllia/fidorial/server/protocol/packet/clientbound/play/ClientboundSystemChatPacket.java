package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;

public record ClientboundSystemChatPacket(Component message, boolean overlay, CommandSource source)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SYSTEM_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(source, message);
        buf.writeBoolean(overlay);
    }
}
