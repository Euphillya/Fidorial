package fr.euphyllia.fidorial.server.network.protocol.packet;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import net.kyori.adventure.key.Key;

public interface ClientboundPacket {

    Key name();

    void write(PacketBuffer buf);
}
