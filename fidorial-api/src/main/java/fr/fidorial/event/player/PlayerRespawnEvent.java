package fr.fidorial.event.player;

import fr.fidorial.entity.Player;
import fr.fidorial.world.Location;

/**
 * @since 0.1.0
 */
public final class PlayerRespawnEvent implements PlayerEvent {

    private final Player player;

    private Location respawnLocation;

    public PlayerRespawnEvent(final Player player, final Location respawnLocation) {
        this.player = player;
        this.respawnLocation = respawnLocation;
    }

    /**
     * @return the player coming back to life
     */
    @Override
    public Player player() {
        return player;
    }

    /**
     * @return where the player is about to reappear
     */
    public Location respawnLocation() {
        return respawnLocation;
    }

    /**
     * @param respawnLocation where the player should reappear instead
     */
    public void setRespawnLocation(final Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }
}