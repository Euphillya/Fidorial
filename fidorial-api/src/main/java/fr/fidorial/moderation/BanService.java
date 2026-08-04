package fr.fidorial.moderation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface BanService {

    /**
     * Gets the active ban on an identity.
     *
     * <p>An expired entry must not be returned; implementations are free to discard it.</p>
     *
     * @param uuid the player identity
     * @return the active ban, or empty when the player may connect
     * @since 0.1.0
     */
    @Contract(pure = true)
    Optional<BanEntry> find(final UUID uuid);

    /**
     * Checks whether an identity is currently banned.
     *
     * @param uuid the player identity
     * @return {@code true} when the player is banned
     * @since 0.1.0
     */
    @Contract(pure = true)
    default boolean isBanned(final UUID uuid) {
        return find(uuid).isPresent();
    }

    /**
     * Gets the active ban on a name.
     *
     * <p>An expired entry must not be returned; implementations are free to discard it.</p>
     *
     * @param name the player name
     * @return the active ban, or empty when the player may connect
     * @since 0.1.0
     */
    @Contract(pure = true)
    Optional<BanEntry> find(final String name);

    /**
     * Records a ban, replacing any existing entry for the same identity.
     *
     * @param entry the ban to record
     * @return {@code true} when the player was not already banned
     * @since 0.1.0
     */
    boolean ban(BanEntry entry);

    /**
     * Bans an identity permanently.
     *
     * @param uuid   the identity to ban
     * @param name   the name to record for display
     * @param reason why the player is banned, or {@code null}
     * @param source who is issuing the ban
     * @return {@code true} when the player was not already banned
     * @since 0.1.0
     */
    default boolean ban(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source
    ) {
        return ban(BanEntry.permanent(uuid, name, reason, source));
    }

    /**
     * Bans an identity until the given instant.
     *
     * @param uuid    the identity to ban
     * @param name    the name to record for display
     * @param reason  why the player is banned, or {@code null}
     * @param source  who is issuing the ban
     * @param expires when the ban lifts, or {@code null} for a permanent ban
     * @return {@code true} when the player was not already banned
     * @since 0.1.0
     */
    default boolean ban(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source,
            @Nullable final Instant expires
    ) {
        return ban(BanEntry.until(uuid, name, reason, source, expires));
    }

    /**
     * Bans an identity for the given amount of time, counted from now.
     *
     * @param uuid     the identity to ban
     * @param name     the name to record for display
     * @param reason   why the player is banned, or {@code null}
     * @param source   who is issuing the ban
     * @param duration how long the ban lasts
     * @return {@code true} when the player was not already banned
     * @since 0.1.0
     */
    default boolean ban(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source,
            final Duration duration
    ) {
        return ban(BanEntry.lasting(uuid, name, reason, source, duration));
    }

    /**
     * Lifts the ban on an identity.
     *
     * @param uuid the player identity
     * @return {@code true} when a ban was actually lifted
     * @since 0.1.0
     */
    boolean pardon(final UUID uuid);

    /**
     * Gets the active bans.
     *
     * @return the bans, most recently issued first; expired entries are excluded
     * @since 0.1.0
     */
    @Contract(pure = true)
    Stream<BanEntry> bans();

    /**
     * Gets how many players are currently banned.
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
    Component disconnectMessage(final BanEntry entry);
}
