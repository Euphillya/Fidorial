package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.storage.LevelData;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.HashSet;
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

    private static final class Entry {
        final Key id;
        final BossBar bar;
        volatile boolean visible;
        volatile Set<UUID> players;
        BossBar.Listener listener;

        Entry(final Key id, final BossBar bar, final boolean visible, final Set<UUID> players) {
            this.id = id;
            this.bar = bar;
            this.visible = visible;
            this.players = players;
        }
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

        final Entry entry = new Entry(id, bar, visible, Set.copyOf(players));

        final BossBar.Listener listener = new BossBar.Listener() {
            @Override
            public void bossBarProgressChanged(final BossBar b, final float old, final float now) {
                persist(entry);
            }

            @Override
            public void bossBarNameChanged(final BossBar b, final Component old, final Component now) {
                persist(entry);
            }

            @Override
            public void bossBarColorChanged(final BossBar b, final BossBar.Color old, final BossBar.Color now) {
                persist(entry);
            }

            @Override
            public void bossBarOverlayChanged(final BossBar b, final BossBar.Overlay old, final BossBar.Overlay now) {
                persist(entry);
            }

            @Override
            public void bossBarFlagsChanged(final BossBar b, final Set<BossBar.Flag> added, final Set<BossBar.Flag> removed) {
                persist(entry);
            }
        };
        entry.listener = listener;

        bar.addListener(listener);
        registered.put(id, entry);
        persist(entry);

        if (visible) {
            for (final ServerPlayer player : onlinePlayers.get()) {
                if (entry.players.isEmpty() || entry.players.contains(player.uuid())) {
                    player.showBossBar(bar);
                }
            }
        }
    }

    public void unregister(final Key id) {
        final Entry entry = registered.remove(id);
        if (entry == null) return;

        entry.bar.removeListener(entry.listener);
        levelData.bossBars.remove(id);

        for (final ServerPlayer player : onlinePlayers.get()) {
            player.hideBossBar(entry.bar);
        }
    }

    public void setVisible(final Key id, final boolean visible) {
        final Entry entry = registered.get(id);
        if (entry == null) return;

        synchronized (entry) {
            if (entry.visible == visible) return;
            entry.visible = visible;
            persist(entry);

            for (final ServerPlayer player : onlinePlayers.get()) {
                if (entry.players.isEmpty() || entry.players.contains(player.uuid())) {
                    if (visible) {
                        player.showBossBar(entry.bar);
                    } else {
                        player.hideBossBar(entry.bar);
                    }
                }
            }
        }
    }

    public void setPlayers(final Key id, final Set<UUID> players) {
        final Entry entry = registered.get(id);
        if (entry == null) return;

        synchronized (entry) {
            applyPlayers(entry, players);
        }
    }

    public void addPlayer(final Key id, final UUID playerId) {
        final Entry entry = registered.get(id);
        if (entry == null) return;

        synchronized (entry) {
            if (entry.players.isEmpty() || entry.players.contains(playerId)) return;

            final Set<UUID> updated = new HashSet<>(entry.players);
            updated.add(playerId);
            applyPlayers(entry, updated);
        }
    }

    public void removePlayer(final Key id, final UUID playerId) {
        final Entry entry = registered.get(id);
        if (entry == null) return;

        synchronized (entry) {
            final Set<UUID> updated;

            if (entry.players.isEmpty()) {
                updated = new HashSet<>();
                for (final ServerPlayer player : onlinePlayers.get()) {
                    if (!player.uuid().equals(playerId)) {
                        updated.add(player.uuid());
                    }
                }
            } else {
                if (!entry.players.contains(playerId)) return;
                updated = new HashSet<>(entry.players);
                updated.remove(playerId);
            }

            applyPlayers(entry, updated);
        }
    }

    private void applyPlayers(final Entry entry, final Set<UUID> newPlayers) {
        final Set<UUID> old = entry.players;
        final Set<UUID> updated = Set.copyOf(newPlayers);

        entry.players = updated;
        persist(entry);

        if (!entry.visible) return;

        for (final ServerPlayer player : onlinePlayers.get()) {
            final boolean wasTargeted = old.isEmpty() || old.contains(player.uuid());
            final boolean isTargeted = updated.isEmpty() || updated.contains(player.uuid());

            if (wasTargeted && !isTargeted) {
                player.hideBossBar(entry.bar);
            } else if (!wasTargeted && isTargeted) {
                player.showBossBar(entry.bar);
            }
        }
    }

    public void close() {
        for (final Entry entry : registered.values()) {
            entry.bar.removeListener(entry.listener);
        }
        registered.clear();
    }

    public Optional<BossBar> get(final Key id) {
        final Entry entry = registered.get(id);
        return entry == null ? Optional.empty() : Optional.of(entry.bar);
    }

    public Optional<BossBarEntry> getEntry(final Key id) {
        final Entry entry = registered.get(id);
        return entry == null ? Optional.empty() : Optional.of(toPublicEntry(entry));
    }

    public Collection<BossBarEntry> entries() {
        return registered.values()
                .stream()
                .map(this::toPublicEntry)
                .toList();
    }

    private BossBarEntry toPublicEntry(final Entry entry) {
        return new BossBarEntry(entry.id, entry.bar, entry.visible, entry.players);
    }

    private void persist(final Entry entry) {
        levelData.bossBars.put(
                entry.id,
                new LevelData.BossBarData(
                        entry.bar.name(),
                        entry.bar.progress(),
                        entry.bar.color(),
                        entry.bar.overlay(),
                        entry.bar.flags(),
                        entry.visible,
                        entry.players
                )
        );
    }

    public void syncTo(final ServerPlayer player) {
        for (final Entry entry : registered.values()) {
            if (entry.visible && (entry.players.isEmpty() || entry.players.contains(player.uuid()))) {
                player.showBossBar(entry.bar);
            }
        }
    }

    public void loadFromLevelData() {
        for (final Map.Entry<Key, LevelData.BossBarData> entry : levelData.bossBars.entrySet()) {
            final LevelData.BossBarData data = entry.getValue();

            final BossBar bar = BossBar.bossBar(
                    data.name(), data.progress(), data.color(), data.overlay(), data.flags()
            );

            register(entry.getKey(), bar, data.visible(), data.players());
        }
    }
}
