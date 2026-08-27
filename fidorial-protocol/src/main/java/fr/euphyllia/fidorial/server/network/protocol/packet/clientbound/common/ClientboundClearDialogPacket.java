package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 *
 * @param name the packet identifier for the current connection phase
 * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Clear_Dialog">Clear Dialog</a>
 */
public record ClientboundClearDialogPacket(Key name) implements ClientboundPacket {

    public ClientboundClearDialogPacket {
        Objects.requireNonNull(name, "name");
    }

    @Override
    public void write(final PacketBuffer buf) {
        // no fields
    }
}
