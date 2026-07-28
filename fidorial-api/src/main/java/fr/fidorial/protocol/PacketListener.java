package fr.fidorial.protocol;

public interface PacketListener {

    default void onEnter() {
    }

    default void onDisconnect() {
    }
}
