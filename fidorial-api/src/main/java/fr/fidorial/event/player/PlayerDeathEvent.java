package fr.fidorial.event.player;

import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

/**
 * @since 0.1.0
 */
public final class PlayerDeathEvent implements PlayerEvent {

    private final Player player;
    private final @Nullable Entity killer;

    private @Nullable Component deathMessage;

    public PlayerDeathEvent(final Player player, final @Nullable Entity killer, final @Nullable Component deathMessage) {
        this.player = player;
        this.killer = killer;
        this.deathMessage = deathMessage;
    }

    /**
     * @return the player who died
     */
    @Override
    public Player player() {
        return player;
    }

    /**
     * @return the entity credited with the kill, or {@code null} for environmental deaths
     */
    public @Nullable Entity killer() {
        return killer;
    }

    /**
     * @return the message broadcast to the server, or {@code null} if the death stays silent
     */
    public @Nullable Component deathMessage() {
        return deathMessage;
    }

    /**
     * @param deathMessage the message to broadcast instead, or {@code null} to broadcast nothing
     */
    public void setDeathMessage(final @Nullable Component deathMessage) {
        this.deathMessage = deathMessage;
    }
}