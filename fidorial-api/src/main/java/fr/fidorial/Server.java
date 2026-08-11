package fr.fidorial;

import fr.fidorial.command.CommandRegistry;
import fr.fidorial.entity.OfflinePlayers;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventBus;
import fr.fidorial.moderation.BanManager;
import fr.fidorial.moderation.WhitelistManager;
import fr.fidorial.permission.PermissionRegistry;
import fr.fidorial.plugin.PluginManager;
import fr.fidorial.scheduler.RegionizedScheduler;
import fr.fidorial.service.ServiceRegistry;
import fr.fidorial.status.Favicon;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.World;
import fr.fidorial.world.WorldBuilder;
import fr.fidorial.world.generation.WorldGenerator;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Server extends ForwardingAudience {

    /**
     * Gets the server name.
     *
     * @return server name
     * @since 0.1.0
     */
    @Contract(pure = true)
    String getName();

    String minecraftVersion();

    int protocolVersion();

    RegionizedScheduler scheduler();

    CommandRegistry commands();

    EventBus events();

    /**
     * Gets the server's favicon shown in the status ping.
     *
     * @return the server favicon
     * @since 0.1.0
     */
    @Contract(pure = true)
    Optional<Favicon> favicon();

    /**
     * Sets the server's favicon shown in the status ping.
     *
     * @param favicon the server favicon
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    void favicon(Favicon favicon);

    /**
     * Gets the server description shown in the status ping.
     *
     * @return server description
     * @since 0.1.0
     */
    @Contract(pure = true)
    Component description();

    /**
     * Sets the server description shown in the status ping.
     *
     * @param description server description
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    void description(Component description);

    /**
     * Gets the maximum player count shown in the status ping.
     *
     * @return maximum player count
     * @since 0.1.0
     */
    @Contract(pure = true)
    int maxPlayers();

    /**
     * Sets the maximum player count shown in the status ping.
     *
     * @param maxPlayers maximum player count
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    void maxPlayers(int maxPlayers);

    /**
     * Gets the current online player count.
     *
     * @return online player count
     * @since 0.1.0
     */
    @Contract(pure = true)
    int playerCount();

    /**
     * Gets the service holding the players who are not allowed to connect.
     *
     * @return the ban service
     * @since 0.1.0
     */
    @Contract(pure = true)
    BanManager ban();

    /**
     * Gets the service holding the players allowed to connect while the whitelist is enforced.
     *
     * @return the whitelist service
     * @since 0.1.0
     */
    @Contract(pure = true)
    WhitelistManager whitelist();

    ServiceRegistry services();

    PluginManager plugins();

    /**
     * Gets the server-wide permission registry.
     *
     * @return the permission registry
     * @since 0.1.0
     */
    PermissionRegistry permissions();

    Collection<? extends World> worlds();

    Optional<? extends World> world(Key key);

    /**
     * Creates a new world from the given specification.
     *
     * <p>If a world is already registered under the spec's {@linkplain WorldBuilder#key() key}, that
     * existing world is returned unchanged and the rest of the spec (seed, generator) is ignored;
     * this call is therefore idempotent with respect to the world key. Otherwise a new world is
     * registered using the spec's {@linkplain WorldBuilder#generator() generator}, or the server's
     * built-in default generator when the spec supplies none.</p>
     *
     * <p>The returned world is immediately usable: it participates in ticking and its chunks are
     * generated on demand through the configured generator.</p>
     *
     * @param spec the description of the world to create
     * @return the newly created world, or the existing world sharing the same key
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    World createWorld(WorldBuilder spec);

    /**
     * @param key       the world key
     * @param generator the chunk generator to drive the new world
     * @return the newly created world, or the existing world sharing the same key
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    default World createWorld(final Key key, final WorldGenerator generator) {
        return createWorld(WorldBuilder.builder(key).generator(generator).build());
    }

    /**
     * Unloads the world identified by {@code key}, optionally saving it to disk first.
     *
     * <p>Unloading removes the world from {@link #worlds()} and stops it being ticked; entities it
     * held are released. The call is refused, returning {@code false} without side effects, when:</p>
     * <ul>
     *   <li>no world is registered under {@code key};</li>
     *   <li>the world is the server's primary world, which must always remain loaded; or</li>
     *   <li>players are still present in the world &mdash; relocate them with another world first.</li>
     * </ul>
     *
     * @param key  the key of the world to unload
     * @param save whether to save the world before unloading it
     * @return {@code true} if the world was unloaded, {@code false} if the call was refused
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    boolean unloadWorld(Key key, boolean save);

    Collection<? extends Player> onlinePlayers();

    Optional<? extends Player> player(UUID uuid);

    Optional<? extends Player> player(String name);

    @Contract(pure = true)
    OfflinePlayers offlinePlayers();

    boolean isRunning();

    void shutdown();

    TranslationStore translationStore();
}
