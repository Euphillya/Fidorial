package fr.euphyllia.fidorial.server.protocol.packet;

public interface PacketListener {

    default void onEnter() {
    }

    default void onDisconnect() {
    }
}
