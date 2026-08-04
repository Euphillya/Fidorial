package fr.euphyllia.fidorial.server.moderation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BanList implements BanService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(BanList.class);

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(
                    Instant.class,
                    (JsonSerializer<Instant>) (source, _, _) -> new JsonPrimitive(source.toString()))
            .registerTypeAdapter(
                    Instant.class,
                    (JsonDeserializer<Instant>) (json, _, _) -> Instant.parse(json.getAsString()))
            .create();

    private final Path file;
    private final Map<UUID, BanEntry> bans = new ConcurrentHashMap<>();

    public BanList(final Path file) {
        this.file = Objects.requireNonNull(file, "file");
    }

    public void load() {
        if (!Files.exists(file)) {
            return;
        }
        try (final Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            final List<BanEntry> entries = GSON.fromJson(reader, new TypeToken<List<BanEntry>>() {
            }.getType());
            bans.clear();
            if (entries != null) {
                for (final BanEntry entry : entries) {
                    if (entry != null && entry.uuid() != null && !entry.expired()) {
                        bans.put(entry.uuid(), entry);
                    }
                }
            }
            LOGGER.debug("There are currently {} banned player(s)", bans.size());
        } catch (final Exception e) {
            LOGGER.error("Unable to read {}", file, e);
        }
    }

    public synchronized void save() {
        try (final Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(new ArrayList<>(bans.values()), writer);
        } catch (final IOException e) {
            LOGGER.error("Unable to save {}", file, e);
        }
    }

    @Override
    public Optional<BanEntry> find(final UUID uuid) {
        final BanEntry entry = bans.get(uuid);
        if (entry == null) {
            return Optional.empty();
        }
        if (entry.expired()) {
            if (bans.remove(uuid, entry)) {
                save();
            }
            return Optional.empty();
        }
        return Optional.of(entry);
    }

    @Override
    public Optional<BanEntry> find(final String name) {
        return bans.values().stream()
                .filter(entry -> !entry.expired())
                .filter(entry -> entry.name() != null && entry.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean ban(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");
        final boolean isNew = find(entry.uuid()).isEmpty();
        bans.put(entry.uuid(), entry);
        save();
        return isNew;
    }

    @Override
    public boolean pardon(final UUID uuid) {
        Objects.requireNonNull(uuid, "uuid");
        if (bans.remove(uuid) == null) {
            return false;
        }
        save();
        return true;
    }

    @Override
    public Collection<BanEntry> bans() {
        return List.copyOf(bans.values());
    }

    @Override
    public Component disconnectMessage(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");

        Component message = entry.reason() == null
                ? Component.translatable("multiplayer.disconnect.banned")
                : Component.translatable("multiplayer.disconnect.banned.reason", entry.reason());

        if (!entry.permanent()) {
            message = message.appendNewline()
                    .append(Component.translatable(
                            "multiplayer.disconnect.banned.expiration",
                            Component.text(entry.expiresLabel())));
        }
        return message;
    }

    @Override
    public int size() {
        int count = 0;
        for (final BanEntry entry : bans.values()) {
            if (!entry.expired()) {
                count++;
            }
        }
        return count;
    }
}
