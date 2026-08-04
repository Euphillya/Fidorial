package fr.fidorial.moderation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a ban entry for a player.
 *
 * @param uuid    the unique identifier of the player
 * @param name    the name of the player (nullable)
 * @param reason  the reason for the ban (nullable)
 * @param source  the source of the ban (nullable)
 * @param created the timestamp when the ban was created
 * @param expires the timestamp when the ban expires (nullable)
 */
public record BanEntry(
        UUID uuid,
        @Nullable String name,
        @Nullable Component reason,
        @Nullable String source,
        Instant created,
        @Nullable Instant expires
) {

    /**
     * The date format used for displaying timestamps in the ban entry.
     */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT).withZone(ZoneId.systemDefault());


    public BanEntry {
        Objects.requireNonNull(uuid, "uuid");
        Objects.requireNonNull(created, "created");
    }

    /**
     * Creates a ban that never lifts.
     *
     * @param uuid   the identity to ban
     * @param name   the name to record for display
     * @param reason why the player is banned, or {@code null}
     * @param source who is issuing the ban
     * @return the ban
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static BanEntry permanent(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source
    ) {
        return until(uuid, name, reason, source, null);
    }

    /**
     * Creates a ban lifting at the given instant.
     *
     * @param uuid    the identity to ban
     * @param name    the name to record for display
     * @param reason  why the player is banned, or {@code null}
     * @param source  who is issuing the ban
     * @param expires when the ban lifts, or {@code null} for a permanent ban
     * @return the ban
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static BanEntry until(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source,
            @Nullable final Instant expires
    ) {
        return new BanEntry(uuid, name, reason, source, Instant.now(), expires);
    }

    /**
     * Creates a ban lasting the given amount of time, counted from now.
     *
     * @param uuid     the identity to ban
     * @param name     the name to record for display
     * @param reason   why the player is banned, or {@code null}
     * @param source   who is issuing the ban
     * @param duration how long the ban lasts
     * @return the ban
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static BanEntry lasting(
            final UUID uuid,
            @Nullable final String name,
            @Nullable final Component reason,
            @Nullable final String source,
            final Duration duration
    ) {
        Objects.requireNonNull(duration, "duration");

        final Instant created = Instant.now();

        return new BanEntry(uuid, name, reason, source, created, created.plus(duration));
    }

    /**
     * Checks whether this ban never lifts on its own.
     *
     * @return {@code true} when the ban has no expiry
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean permanent() {
        return expires == null;
    }

    /**
     * Checks whether the expiry has already passed.
     *
     * <p>An expired entry is not an active ban. A {@link BanService} is expected to stop
     * reporting it, and may discard it.</p>
     *
     * @return {@code true} when the ban has run out
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean expired() {
        return expires != null && expires.isBefore(Instant.now());
    }

    /**
     * Gets how long is left before the ban lifts.
     *
     * @return the remaining time, or empty for a permanent ban; {@link Duration#ZERO} once the
     * expiry has passed
     * @since 0.1.0
     */
    @Contract(pure = true)
    public Optional<Duration> remaining() {
        if (expires == null) {
            return Optional.empty();
        }

        final Duration left = Duration.between(Instant.now(), expires);

        return Optional.of(left.isNegative() ? Duration.ZERO : left);
    }

    /**
     * Gets why the player was banned.
     *
     * @return the reason, or empty when none was given
     * @since 0.1.0
     */
    @Contract(pure = true)
    public Optional<Component> describeReason() {
        return Optional.ofNullable(reason);
    }

    /**
     * Gets the label for the player.
     *
     * @return the label, or the UUID as a string if no name is available
     * @since 0.1.0
     */
    @Contract(pure = true)
    public String label() {
        return name != null ? name : uuid.toString();
    }

    /**
     * Gets when the ban was issued, as a readable date.
     *
     * @return the creation date
     * @since 0.1.0
     */
    @Contract(pure = true)
    public String createdLabel() {
        return DATE_FORMAT.format(created);
    }

    /**
     * Gets when the ban lifts, as a readable date.
     *
     * @return the expiry date, or an empty string for a permanent ban
     * @since 0.1.0
     */
    @Contract(pure = true)
    public String expiresLabel() {
        return expires == null ? "" : DATE_FORMAT.format(expires);
    }
}
