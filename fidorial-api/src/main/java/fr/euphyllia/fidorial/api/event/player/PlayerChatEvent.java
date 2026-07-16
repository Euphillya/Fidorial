package fr.euphyllia.fidorial.api.event.player;

import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.event.Cancellable;

public class PlayerChatEvent implements PlayerEvent, Cancellable {

    private final Player player;
    private String message;
    private boolean cancelled;

    public PlayerChatEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public Player player() {
        return player;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
