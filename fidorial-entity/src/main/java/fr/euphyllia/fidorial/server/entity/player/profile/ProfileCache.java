package fr.euphyllia.fidorial.server.entity.player.profile;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ProfileCache implements Closeable {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ProfileCache.class);

    private static final long TOUCH_INTERVAL_MILLIS = Duration.ofMinutes(10).toMillis();

    private final ProfileJournal journal;
    private final Map<UUID, ProfileEntry> byId = new ConcurrentHashMap<>();
    private final Map<String, UUID> byName = new ConcurrentHashMap<>();
    private final long ttlMillis;
    private final int maxEntries;

    /**
     * Creates a cache over the given journal file. Nothing is read until {@link #load()} is called.
     *
     * @param file       the journal file
     * @param ttl        how long an entry is retained after it was last observed
     * @param maxEntries the maximum number of entries to retain
     */
    public ProfileCache(final Path file, final Duration ttl, final int maxEntries) {
        this.journal = new ProfileJournal(file);
        this.ttlMillis = ttl.toMillis();
        this.maxEntries = maxEntries;
    }

    private static String key(final String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    /**
     * Replays the journal into memory and opens it for appending.
     *
     * @throws IOException if the journal cannot be read or opened
     */
    public void load() throws IOException {
        final long cutoff = System.currentTimeMillis() - ttlMillis;

        journal.open((entry, removed) -> {
            if (removed) {
                final ProfileEntry gone = byId.remove(entry.uuid());
                if (gone != null && gone.name() != null) {
                    byName.remove(key(gone.name()), gone.uuid());
                }
                return;
            }
            if (entry.lastSeen() < cutoff) {
                return;
            }
            index(entry);
        });

        LOGGER.info("Profile cache loaded: {} identities", byId.size());

        if (journal.shouldCompact(byId.size())) {
            journal.compact(List.copyOf(byId.values()));
        }
    }

    private void index(final ProfileEntry entry) {
        final ProfileEntry previous = byId.put(entry.uuid(), entry);
        if (previous != null && previous.name() != null
                && !previous.name().equalsIgnoreCase(entry.name() == null ? "" : entry.name())) {
            byName.remove(key(previous.name()), entry.uuid());
        }
        if (entry.name() != null) {
            byName.put(key(entry.name()), entry.uuid());
        }
    }

    /**
     * Gets the entry held for an identity.
     *
     * @param uuid the player identity
     * @return the entry, or empty if the identity is unknown or its entry has expired
     */
    public Optional<ProfileEntry> byId(final UUID uuid) {
        return Optional.ofNullable(byId.get(uuid)).filter(this::alive);
    }

    /**
     * Gets the entry held for a name, ignoring case.
     *
     * @param name the player name
     * @return the entry, or empty if the name is unknown or its entry has expired
     */
    public Optional<ProfileEntry> byName(final String name) {
        final UUID uuid = byName.get(key(name));
        return uuid == null ? Optional.empty() : byId(uuid);
    }

    private boolean alive(final ProfileEntry entry) {
        return entry.lastSeen() >= System.currentTimeMillis() - ttlMillis;
    }

    /**
     * Records an observation of an identity under a name.
     *
     * @param uuid the player identity
     * @param name the observed name
     * @return the resulting entry
     */
    public ProfileEntry remember(final UUID uuid, final String name) {
        final long now = System.currentTimeMillis();

        final ProfileEntry previous = byId.get(uuid);
        final ProfileEntry updated = previous == null
                ? new ProfileEntry(uuid, name, now, now)
                : previous.withName(name, now);

        final boolean nameChanged = previous == null
                || previous.name() == null
                || !previous.name().equals(name);
        final boolean stale = previous == null || now - previous.lastSeen() > TOUCH_INTERVAL_MILLIS;

        index(updated);

        if (nameChanged || stale) {
            try {
                journal.append(updated, false);
            } catch (final IOException e) {
                LOGGER.error("Unable to persist the profile of {} ({})", name, uuid, e);
            }
        }

        if (byId.size() > maxEntries) {
            evictOldest();
        }
        return updated;
    }

    /**
     * Removes an identity from the cache and records the removal in the journal.
     *
     * @param uuid the player identity
     */
    public void forget(final UUID uuid) {
        final ProfileEntry gone = byId.remove(uuid);
        if (gone == null) {
            return;
        }
        if (gone.name() != null) {
            byName.remove(key(gone.name()), uuid);
        }
        try {
            journal.append(gone, true);
        } catch (final IOException e) {
            LOGGER.error("Unable to persist the removal of {}", uuid, e);
        }
    }

    /**
     * Gets every unexpired entry.
     *
     * @return a snapshot of the retained entries
     */
    public List<ProfileEntry> entries() {
        final List<ProfileEntry> out = new ArrayList<>(byId.size());
        for (final ProfileEntry entry : byId.values()) {
            if (alive(entry)) {
                out.add(entry);
            }
        }
        return out;
    }

    /**
     * Gets the number of entries currently held, expired entries included until the next
     * {@link #maintain()}.
     *
     * @return the entry count
     */
    public int size() {
        return byId.size();
    }

    /**
     * Discards expired entries and either compacts or flushes the journal.
     */
    public void maintain() {
        final long cutoff = System.currentTimeMillis() - ttlMillis;
        byId.values().removeIf(entry -> {
            if (entry.lastSeen() >= cutoff) {
                return false;
            }
            if (entry.name() != null) {
                byName.remove(key(entry.name()), entry.uuid());
            }
            return true;
        });

        try {
            if (journal.shouldCompact(byId.size())) {
                journal.compact(entries());
            } else {
                journal.flush();
            }
        } catch (final IOException e) {
            LOGGER.error("Profile cache maintenance failed", e);
        }
    }

    private void evictOldest() {
        final int target = Math.max(1, (maxEntries * 9) / 10);
        final List<ProfileEntry> ordered = new ArrayList<>(byId.values());
        ordered.sort(Comparator.comparingLong(ProfileEntry::lastSeen));

        int removed = 0;
        for (final ProfileEntry entry : ordered) {
            if (byId.size() <= target) {
                break;
            }
            forget(entry.uuid());
            removed++;
        }
        LOGGER.debug("Profile cache full: {} least recent identities evicted", removed);
    }

    /**
     * Flushes and closes the journal.
     *
     * @throws IOException if the final flush fails
     */
    @Override
    public void close() throws IOException {
        journal.flush();
        journal.close();
    }
}
