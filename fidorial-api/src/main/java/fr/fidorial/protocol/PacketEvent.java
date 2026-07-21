package fr.fidorial.protocol;

import fr.fidorial.entity.Player;
import fr.fidorial.event.Cancellable;
import org.jspecify.annotations.Nullable;


public final class PacketEvent implements Cancellable {

    private final @Nullable Player player;
    private final PacketDirection direction;
    private final PacketContainer packet;
    private boolean cancelled;

    public PacketEvent(@Nullable Player player, PacketDirection direction, PacketContainer packet) {
        this.player = player;
        this.direction = direction;
        this.packet = packet;
    }

    /**
     * @return the player involved, or {@code null} if the connection has none yet.
     */
    public @Nullable Player player() {
        return player;
    }

    /**
     * @return the travel direction of the packet.
     */
    public PacketDirection direction() {
        return direction;
    }

    /**
     * @return the packet type (shortcut for {@code packet().type()}).
     */
    public PacketType type() {
        return packet.type();
    }

    /**
     * @return the mutable container of the packet's fields.
     */
    public PacketContainer packet() {
        return packet;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


}
