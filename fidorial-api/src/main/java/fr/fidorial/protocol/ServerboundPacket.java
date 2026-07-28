package fr.fidorial.protocol;

public interface ServerboundPacket {

    void handle(PacketListener listener);
}
