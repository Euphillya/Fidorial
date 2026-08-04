package fr.euphyllia.fidorial.server.moderation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.fidorial.moderation.WhitelistEntry;
import fr.fidorial.moderation.WhitelistService;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class Whitelist implements WhitelistService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(Whitelist.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path file;
    private final Map<UUID, WhitelistEntry> entries = new ConcurrentHashMap<>();
    private volatile boolean enabled;

    public Whitelist(final Path file) {
        this.file = Objects.requireNonNull(file, "file");
    }

    public void load() {
        if (!Files.exists(file)) {
            return;
        }
        try (final Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            final Model model = GSON.fromJson(reader, Model.class);
            entries.clear();
            if (model != null) {
                this.enabled = model.enabled();
                if (model.entries() != null) {
                    for (final WhitelistEntry entry : model.entries()) {
                        if (entry != null && entry.uuid() != null) {
                            entries.put(entry.uuid(), entry);
                        }
                    }
                }
            }
            LOGGER.debug("Whitelist {} with {} entries", enabled ? "enabled" : "disabled", entries.size());
        } catch (final Exception e) {
            LOGGER.error("Unable to read {}", file, e);
        }
    }

    public synchronized void save() {
        try (final Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(new Model(enabled, new ArrayList<>(entries.values())), writer);
        } catch (final IOException e) {
            LOGGER.error("Unable to save {}", file, e);
        }
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public boolean enabled(final boolean enabled) {
        if (this.enabled == enabled) {
            return false;
        }
        this.enabled = enabled;
        save();
        return true;
    }

    @Override
    public boolean contains(final UUID uuid) {
        return entries.containsKey(uuid);
    }

    @Override
    public boolean add(final UUID uuid, @Nullable final String name) {
        Objects.requireNonNull(uuid, "uuid");
        if (entries.putIfAbsent(uuid, new WhitelistEntry(uuid, name)) != null) {
            return false;
        }
        save();
        return true;
    }

    @Override
    public boolean remove(final UUID uuid) {
        Objects.requireNonNull(uuid, "uuid");
        if (entries.remove(uuid) == null) {
            return false;
        }
        save();
        return true;
    }

    @Override
    public Optional<WhitelistEntry> find(final String name) {
        for (final WhitelistEntry entry : entries.values()) {
            if (entry.name() != null && entry.name().equalsIgnoreCase(name)) {
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    @Override
    public Stream<WhitelistEntry> entries() {
        return entries.values().stream()
                .sorted(Comparator.comparing(WhitelistEntry::label, String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public int totalEntries() {
        return entries.size();
    }

    private record Model(boolean enabled, @Nullable List<WhitelistEntry> entries) {
    }
}
