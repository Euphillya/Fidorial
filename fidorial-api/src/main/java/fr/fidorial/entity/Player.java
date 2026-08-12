package fr.fidorial.entity;

import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.permission.PermissionHolder;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContentsLike;
import org.jspecify.annotations.Nullable;

import java.net.InetAddress;
import java.util.UUID;

public interface Player extends LivingEntity, PermissionHolder, CommandSource, CommandSender, Identified, BossBarViewer, ObjectContentsLike {

    void refreshCommands();

    PlayerProfile profile();

    /**
     * Gets the offline handle for this player's identity.
     *
     * @return the handle for this player's identity
     * @since 0.1.0
     */
    default OfflinePlayer offline() {
        return server().offlinePlayers().of(this);
    }

    @Override
    default Identity identity() {
        return Identity.identity(this.uuid());
    }

    @Override
    default UUID uuid() {
        return profile().uuid();
    }

    @Override
    default String name() {
        return profile().name();
    }

    /**
     * Gets the address this player is connected from.
     *
     * @return the client address
     * @throws IllegalStateException if the connection has no resolvable IP address
     *
     * @since 0.1.0
     */
    InetAddress address();

    void kick(Component reason);

    PlayerInventory inventory();

    EnderChestInventory enderChest();

    GameMode gameMode();

    void setGameMode(GameMode gameMode);

    /**
     * @return {@code true} while this player is dead and still on the respawn screen
     * @since 0.1.0
     */
    boolean isAwaitingRespawn();

    /**
     * Respawns this player, exactly as if they had clicked the button on the death screen.
     *
     * <p>The respawn is queued on the region owning the player and happens on the next tick, so it
     * is safe to call from a {@code PlayerDeathEvent} listener to skip the death screen entirely.
     * The player comes back at their {@linkplain #respawnPoint() respawn point}, or at the world
     * spawn when they have none, and a {@code PlayerRespawnEvent} is fired as usual.</p>
     *
     * @return {@code true} if the respawn was queued, {@code false} when the player is not waiting
     * to respawn or has left the server
     * @since 0.1.0
     */
    boolean respawn();

    /**
     * @return where this player respawns, or {@code null} when they use the world spawn
     * @since 0.1.0
     */
    @Nullable RespawnPoint respawnPoint();

    /**
     * Sets where this player respawns. The point is saved with the rest of their data and restored
     * on their next login.
     *
     * @param point the point to respawn at, or {@code null} to fall back to the world spawn
     * @since 0.1.0
     */
    void setRespawnPoint(@Nullable RespawnPoint point);

    /**
     * @param world    the world to respawn in
     * @param location the position to respawn at
     * @since 0.1.0
     */
    default void setRespawnPoint(final World world, final Location location) {
        setRespawnPoint(new RespawnPoint(world, location));
    }

    /**
     * @param location the position to respawn at, in the player's current world
     * @since 0.1.0
     */
    default void setRespawnPoint(final Location location) {
        setRespawnPoint(new RespawnPoint(world(), location));
    }
}
