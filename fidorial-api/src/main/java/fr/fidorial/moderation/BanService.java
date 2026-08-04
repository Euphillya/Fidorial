package fr.fidorial.moderation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
    Optional<BanEntry> find(UUID uuid);

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
     * Lifts the ban on an identity.
     *
     * @param uuid the player identity
     * @return {@code true} when a ban was actually lifted
     * @since 0.1.0
     */
    boolean pardon(UUID uuid);

    /**
     * Gets the active bans.
     *
     * @return the bans, most recently issued first; expired entries are excluded
     * @since 0.1.0
     */
    @Contract(pure = true)
    Collection<BanEntry> bans();

    /**
     * Gets how many players are currently banned.
     *
     * @return the number of active bans
     * @since 0.1.0
     */
    @Contract(pure = true)
    default int size() {
        return bans().size();
    }

    /**
     * Builds the message shown on the disconnect screen of a banned player.
     *
     * @param entry the ban
     * @return the disconnect message
     * @since 0.1.0
     */
    @Contract(pure = true)
    Component disconnectMessage(final BanEntry entry);
}
