package fr.euphyllia.fidorial.api.event.player;

import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.event.Cancellable;
import fr.euphyllia.fidorial.api.world.Location;

public final class PlayerMoveEvent implements PlayerEvent, Cancellable {

    private final Player player;
    private final Location from;
    private final Location to;
    private boolean cancelled;

    public PlayerMoveEvent(Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @Override
    public Player player() {
        return player;
    }

    public Location from() {
        return from;
    }

    public Location to() {
        return to;
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
