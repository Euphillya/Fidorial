package fr.fidorial.moderation;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * An identity allowed to connect while the whitelist is enforced.
 *
 * @param uuid the allowed identity
 * @param name the name the identity was last known by, for display only; entries are matched on
 *             {@link #uuid()}, so a player keeps their access after a rename
 * @since 0.1.0
 */
public record WhitelistEntry(UUID uuid, @Nullable String name) {

    @Contract(pure = true)
    public WhitelistEntry {
        Objects.requireNonNull(uuid, "uuid");
    }

    /**
     * Gets a name suitable for display, falling back to the identity when no name is recorded.
     *
     * @return the recorded name, or the string form of {@link #uuid()}
     * @since 0.1.0
     */
    @Contract(pure = true)
    public String label() {
        return name != null ? name : uuid.toString();
    }
}
