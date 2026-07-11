package fr.euphyllia.fidorial.server.protocol.packet;

import fr.euphyllia.fidorial.server.network.PacketBuffer;

public interface ClientboundPacket {

    String name();

    void write(PacketBuffer buf);
}
