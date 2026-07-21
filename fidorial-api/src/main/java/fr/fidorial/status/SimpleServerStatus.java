package fr.fidorial.status;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

final class SimpleServerStatus implements ServerStatus {
    private final @Nullable Favicon favicon;
    private final Component description;
    private final List<SamplePlayer> samplePlayers;
    private final Version version;
    private final int maxPlayers;
    private final int players;

    SimpleServerStatus(
            final Component description,
            final Version version,
            @Nullable final Favicon favicon,
            final List<SamplePlayer> samplePlayers,
            final int maxPlayers,
            final int players
    ) {
        this.description = description;
        this.version = version;
        this.favicon = favicon;
        this.samplePlayers = samplePlayers;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }

    @Override
    public Component description() {
        return description;
    }

    @Override
    public Version version() {
        return version;
    }

    @Override
    public Optional<Favicon> favicon() {
        return Optional.ofNullable(favicon);
    }

    @Override
    public int maxPlayers() {
        return maxPlayers;
    }

    @Override
    public int players() {
        return players;
    }

    @Override
    public @Unmodifiable List<SamplePlayer> samplePlayers() {
        return samplePlayers;
    }

    @Override
    public ServerStatus.Builder toBuilder() {
        return new Builder()
                .description(description)
                .favicon(favicon)
                .maxPlayers(maxPlayers)
                .players(players)
                .samplePlayers(samplePlayers)
                .version(version);
    }

    static final class Builder implements ServerStatus.Builder {
        private final List<SamplePlayer> samplePlayers = new ArrayList<>();

        private @Nullable Favicon favicon = null;
        private Component description = Component.empty();
        private Version version = new Version("", -1);
        private int maxPlayers = 0;
        private int players = 0;

        @Override
        public ServerStatus.Builder description(final Component description) {
            this.description = description;
            return this;
        }

        @Override
        public ServerStatus.Builder version(final Version version) {
            this.version = version;
            return this;
        }

        @Override
        public ServerStatus.Builder favicon(final Favicon favicon) {
            this.favicon = favicon;
            return this;
        }

        @Override
        public ServerStatus.Builder maxPlayers(final int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }

        @Override
        public ServerStatus.Builder players(final int players) {
            this.players = players;
            return this;
        }

        @Override
        public ServerStatus.Builder samplePlayers(final List<SamplePlayer> samplePlayers) {
            this.samplePlayers.clear();
            this.samplePlayers.addAll(samplePlayers);
            return this;
        }

        @Override
        public ServerStatus.Builder samplePlayer(final SamplePlayer samplePlayer) {
            this.samplePlayers.add(samplePlayer);
            return this;
        }

        @Override
        public ServerStatus build() {
            return new SimpleServerStatus(description, version, favicon, samplePlayers, maxPlayers, players);
        }
    }
}
