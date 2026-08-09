package fr.fidorial.moderation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A service that manages bans.
 *
 * @since 0.1.0
 */
public interface BanService {

    /**
     * Gets the active ban on a target.
     *
     * <p>An expired entry must not be returned; implementations are free to discard it.</p>
     *
     * @param target the ban target
     * @param <T>    what the ban applies to
     * @return the active ban, or empty when nothing bans the target
     * @since 0.1.0
     */
    @Contract(pure = true)
    <T extends BanTarget> Optional<BanEntry<T>> find(final T target);

    /**
     * Gets the active ban recorded under a name, matched case-insensitively.
     *
     * <p>Only entries carrying a recorded name can match, so this never resolves an address.</p>
     *
     * @param name the player name
     * @return the active ban, or empty
     * @since 0.1.0
     */
    @Contract(pure = true)
    Optional<BanEntry<?>> findByName(final String name);

    /**
     * Gets whatever bans a connecting player, whether that is their identity or the address they
     * connect from.
     *
     * @param uuid    the player identity
     * @param address the address the client connects from, or {@code null} when unknown
     * @return the active ban, or empty when the player may connect
     * @since 0.1.0
     */
    @Contract(pure = true)
    default Optional<BanEntry<?>> findAny(final UUID uuid, @Nullable final InetAddress address) {
        final Optional<BanEntry<BanTarget.Profile>> byProfile = find(new BanTarget.Profile(uuid));

        if (byProfile.isPresent() || address == null) {
            return byProfile.map(entry -> entry);
        }

        return find(new BanTarget.Address(address)).map(entry -> entry);
    }

    /**
     * Checks whether a target is currently banned.
     *
     * @param target the ban target
     * @return {@code true} when the target is banned
     * @since 0.1.0
     */
    @Contract(pure = true)
    default boolean isBanned(final BanTarget target) {
        return find(target).isPresent();
    }

    /**
     * Records a ban, replacing any existing entry for the same target.
     *
     * @param entry the ban to record
     * @return {@code true} when the target was not already banned
     * @since 0.1.0
     */
    boolean ban(BanEntry<?> entry);

    /**
     * Lifts the ban on a target.
     *
     * @param target the ban target
     * @return {@code true} when a ban was actually lifted
     * @since 0.1.0
     */
    boolean pardon(final BanTarget target);

    /**
     * Gets the active bans.
     *
     * @return the bans, most recently issued first; expired entries are excluded
     * @since 0.1.0
     */
    @Contract(pure = true)
    Stream<BanEntry<?>> bans();

    /**
     * Gets the active bans of the given kind.
     *
     * @param kind the entry type to keep, such as {@link BanEntry.Address}
     * @param <E>  the entry type
     * @return the bans, most recently issued first; expired entries are excluded
     * @since 0.1.0
     */
    @Contract(pure = true)
    default <E extends BanEntry<?>> Stream<E> bans(final Class<E> kind) {
        return bans().filter(kind::isInstance).map(kind::cast);
    }

    /**
     * Gets how many targets are currently banned.
     *
     * @return the number of active bans
     * @since 0.1.0
     */
    @Contract(pure = true)
    int totalBans();

    /**
     * Builds the message shown on the disconnect screen of a banned player.
     *
     * <p>This is the single place the message is produced: callers must not assemble their own,
     * so that an implementation replacing this one is actually the message players see.</p>
     *
     * @param entry the ban
     * @return the disconnect message
     * @since 0.1.0
     */
    @Contract(pure = true)
    Component disconnectMessage(final BanEntry<?> entry);
}
