package fr.euphyllia.fidorial.server.entity.player.profile;

import org.jspecify.annotations.Nullable;

import java.util.UUID;

/**
 * A single entry of the profile cache.
 *
 * @param uuid      the player identity
 * @param name      the most recently observed name, or {@code null} if only the identity is known
 * @param firstSeen the epoch milliseconds of the first observation, or {@code 0} if unknown
 * @param lastSeen  the epoch milliseconds of the most recent observation, against which expiry is
 *                  measured
 */
public record ProfileEntry(UUID uuid, @Nullable String name, long firstSeen, long lastSeen) {

    /**
     * Derives an entry carrying a new name, observed at the given time.
     *
     * @param newName the newly observed name
     * @param at      the epoch milliseconds of the observation
     * @return the derived entry, retaining the existing first observation if there is one
     */
    public ProfileEntry withName(final String newName, final long at) {
        return new ProfileEntry(uuid, newName, firstSeen == 0L ? at : firstSeen, at);
    }

    /**
     * Derives an entry observed at the given time, leaving the name unchanged.
     *
     * @param at the epoch milliseconds of the observation
     * @return the derived entry, retaining the existing first observation if there is one
     */
    public ProfileEntry touch(final long at) {
        return new ProfileEntry(uuid, name, firstSeen == 0L ? at : firstSeen, at);
    }
}
