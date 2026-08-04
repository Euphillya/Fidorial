package fr.euphyllia.fidorial.server.moderation;

import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class BanList implements BanService {
    @Override
    public Optional<BanEntry> find(final UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean ban(final BanEntry entry) {
        return false;
    }

    @Override
    public boolean pardon(final UUID uuid) {
        return false;
    }

    @Override
    public Collection<BanEntry> bans() {
        return List.of();
    }
}
