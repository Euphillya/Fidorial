package fr.fidorial.event.player;

import fr.fidorial.entity.Player;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

/**
 * Fired when a dead player clicks respawn, before they are placed back in the world.
 *
 * @since 0.1.0
 */
public final class PlayerRespawnEvent implements PlayerEvent {

    private final Player player;
    private World world;
    private Location location;

    public PlayerRespawnEvent(final Player player, final World world, final Location location) {
        this.player = player;
        this.world = world;
        this.location = location;
    }

    @Override
    public Player player() {
        return player;
    }

    /**
     * @return the world the player is about to respawn in
     */
    public World world() {
        return world;
    }

    /**
     * @return the position the player is about to respawn at
     */
    public Location location() {
        return location;
    }

    /**
     * @param world    the world to respawn in instead
     * @param location the position to respawn at instead
     */
    public void setRespawnLocation(final World world, final Location location) {
        this.world = world;
        this.location = location;
    }

    /**
     * @param location the position to respawn at instead, in the same world
     */
    public void setRespawnLocation(final Location location) {
        this.location = location;
    }
}
