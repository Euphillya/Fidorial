package fr.euphyllia.fidorial.server.protocol.packet;

public interface ServerboundPacket {

    void handle(PacketListener listener);
}
