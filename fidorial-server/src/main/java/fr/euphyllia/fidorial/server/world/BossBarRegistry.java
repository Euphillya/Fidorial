package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.storage.LevelData;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class BossBarRegistry {

    public record BossBarEntry(
            Key id,
            BossBar bar,
            boolean visible,
            Set<UUID> players
    ) {
    }

    private record Entry(
            Key id,
            BossBar bar,
            boolean visible,
            Set<UUID> players,
            BossBar.Listener listener
    ) {
    }

    private final Map<Key, Entry> registered = new ConcurrentHashMap<>();

    private final LevelData levelData;
    private final Supplier<Iterable<ServerPlayer>> onlinePlayers;

    public BossBarRegistry(
            final LevelData levelData,
            final Supplier<Iterable<ServerPlayer>> onlinePlayers
    ) {
        this.levelData = levelData;
        this.onlinePlayers = onlinePlayers;
    }

    public void register(
            final Key id,
            final BossBar bar,
            final boolean visible,
            final Set<UUID> players
    ) {
        if (registered.containsKey(id)) {
            unregister(id);
        }

        final Set<UUID> immutablePlayers = Set.copyOf(players);

        final BossBar.Listener listener = new BossBar.Listener() {
            @Override
            public void bossBarProgressChanged(
                    final BossBar b,
                    final float oldProgress,
                    final float newProgress
            ) {
                persist(id, b, visible, immutablePlayers);
            }

            @Override
            public void bossBarNameChanged(
                    final BossBar b,
                    final Component oldName,
                    final Component newName
            ) {
                persist(id, b, visible, immutablePlayers);
            }

            @Override
            public void bossBarColorChanged(
                    final BossBar b,
                    final BossBar.Color oldColor,
                    final BossBar.Color newColor
            ) {
                persist(id, b, visible, immutablePlayers);
            }

            @Override
            public void bossBarOverlayChanged(
                    final BossBar b,
                    final BossBar.Overlay oldOverlay,
                    final BossBar.Overlay newOverlay
            ) {
                persist(id, b, visible, immutablePlayers);
            }

            @Override
            public void bossBarFlagsChanged(
                    final BossBar b,
                    final Set<BossBar.Flag> added,
                    final Set<BossBar.Flag> removed
            ) {
                persist(id, b, visible, immutablePlayers);
            }
        };

        bar.addListener(listener);

        registered.put(
                id,
                new Entry(
                        id,
                        bar,
                        visible,
                        immutablePlayers,
                        listener
                )
        );

        persist(id, bar, visible, immutablePlayers);

        if (visible) {
            for (final ServerPlayer player : onlinePlayers.get()) {
                if (immutablePlayers.isEmpty() || immutablePlayers.contains(player.uuid())) {
                    player.showBossBar(bar);
                }
            }
        }
    }

    public void unregister(final Key id) {
        final Entry entry = registered.remove(id);

        if (entry == null) {
            return;
        }

        entry.bar().removeListener(entry.listener());

        levelData.bossBars.remove(id);

        for (final ServerPlayer player : onlinePlayers.get()) {
            player.hideBossBar(entry.bar());
        }
    }

    public void close() {
        for (final Entry entry : registered.values()) {
            entry.bar().removeListener(entry.listener());
        }

        registered.clear();
    }

    public Optional<BossBar> get(final Key id) {
        final Entry entry = registered.get(id);

        return entry == null
                ? Optional.empty()
                : Optional.of(entry.bar());
    }

    public Optional<BossBarEntry> getEntry(final Key id) {
        final Entry entry = registered.get(id);

        if (entry == null) {
            return Optional.empty();
        }

        return Optional.of(toPublicEntry(entry));
    }

    public Collection<BossBarEntry> entries() {
        return registered.values()
                .stream()
                .map(this::toPublicEntry)
                .toList();
    }

    private BossBarEntry toPublicEntry(final Entry entry) {
        return new BossBarEntry(
                entry.id(),
                entry.bar(),
                entry.visible(),
                entry.players()
        );
    }

    private void persist(
            final Key id,
            final BossBar bar,
            final boolean visible,
            final Set<UUID> players
    ) {
        levelData.bossBars.put(
                id,
                new LevelData.BossBarData(
                        bar.name(),
                        bar.progress(),
                        bar.color(),
                        bar.overlay(),
                        bar.flags(),
                        visible,
                        players
                )
        );
    }

    public void syncTo(final ServerPlayer player) {
        for (final Entry entry : registered.values()) {
            if (entry.visible()
                    && (entry.players().isEmpty()
                    || entry.players().contains(player.uuid()))) {

                player.showBossBar(entry.bar());
            }
        }
    }

    public void loadFromLevelData() {
        for (final Map.Entry<Key, LevelData.BossBarData> entry : levelData.bossBars.entrySet()) {
            final LevelData.BossBarData data = entry.getValue();

            final BossBar bar = BossBar.bossBar(
                    data.name(),
                    data.progress(),
                    data.color(),
                    data.overlay(),
                    data.flags()
            );

            register(
                    entry.getKey(),
                    bar,
                    data.visible(),
                    data.players()
            );
        }
    }
}
