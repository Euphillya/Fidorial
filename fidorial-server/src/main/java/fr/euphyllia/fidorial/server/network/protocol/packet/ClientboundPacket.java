package fr.euphyllia.fidorial.server.network.protocol.packet;

import fr.euphyllia.fidorial.server.network.PacketBuffer;

public interface ClientboundPacket {

    String name();

    void write(PacketBuffer buf);
}
