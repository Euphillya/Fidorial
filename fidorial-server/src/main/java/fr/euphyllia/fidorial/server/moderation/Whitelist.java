package fr.euphyllia.fidorial.server.moderation;

import fr.fidorial.moderation.WhitelistEntry;
import fr.fidorial.moderation.WhitelistService;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class Whitelist implements WhitelistService {
    @Override
    public boolean enabled() {
        return false;
    }

    @Override
    public boolean enabled(final boolean enabled) {
        return false;
    }

    @Override
    public boolean contains(final UUID uuid) {
        return false;
    }

    @Override
    public boolean add(final UUID uuid, @Nullable final String name) {
        return false;
    }

    @Override
    public boolean remove(final UUID uuid) {
        return false;
    }

    @Override
    public Optional<WhitelistEntry> find(final String name) {
        return Optional.empty();
    }

    @Override
    public Collection<WhitelistEntry> entries() {
        return List.of();
    }
}
