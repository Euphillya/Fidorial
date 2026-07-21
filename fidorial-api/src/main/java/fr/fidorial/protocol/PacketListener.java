package fr.fidorial.protocol;

@FunctionalInterface
public interface PacketListener {

    /**
     * Called when a watched packet is intercepted.
     *
     * @param event the event describing the packet; mutable and cancellable
     */
    void onPacket(PacketEvent event);
}
