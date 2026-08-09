package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public record ClientboundDisconnectPacket(Component reason) implements ClientboundPacket {

    @Override
    public Key name() {
        return PlayClientboundPackets.DISCONNECT;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeComponent(reason);
    }
}
