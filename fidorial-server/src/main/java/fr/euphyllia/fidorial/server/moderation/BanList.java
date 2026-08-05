package fr.euphyllia.fidorial.server.moderation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import fr.fidorial.moderation.BanTarget;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public final class BanList implements BanService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(BanList.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path profileFile;
    private final Path addressFile;
    private final Map<BanTarget, BanEntry> bans = new ConcurrentHashMap<>();

    public BanList(final Path profileFile, final Path addressFile) {
        this.profileFile = Objects.requireNonNull(profileFile, "profileFile");
        this.addressFile = Objects.requireNonNull(addressFile, "addressFile");
    }

    public void load() {
        bans.clear();

        read(profileFile, BanList::readProfile);
        read(addressFile, BanList::readAddress);

        LOGGER.debug("There are currently {} active ban(s)", bans.size());
    }

    public synchronized void save() {
        write(profileFile, BanTarget.Profile.class, BanList::writeProfile);
        write(addressFile, BanTarget.Address.class, BanList::writeAddress);
    }

    public int purgeExpired() {
        int removed = 0;

        for (final BanEntry entry : bans.values()) {
            if (entry.expired() && bans.remove(entry.target(), entry)) {
                removed++;
            }
        }

        if (removed > 0) {
            save();
        }

        return removed;
    }

    @Override
    public Optional<BanEntry> find(final BanTarget target) {
        final BanEntry entry = bans.get(target);

        if (entry == null) {
            return Optional.empty();
        }

        if (entry.expired()) {
            if (bans.remove(target, entry)) {
                save();
            }
            return Optional.empty();
        }

        return Optional.of(entry);
    }

    @Override
    public Optional<BanEntry> findByName(final String name) {
        return bans.values().stream()
                .filter(entry -> !entry.expired())
                .filter(entry -> entry.name() != null && entry.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean ban(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");

        final boolean isNew = find(entry.target()).isEmpty();

        bans.put(entry.target(), entry);
        save();

        return isNew;
    }

    @Override
    public boolean pardon(final BanTarget target) {
        Objects.requireNonNull(target, "target");

        if (bans.remove(target) == null) {
            return false;
        }

        save();

        return true;
    }

    @Override
    public Stream<BanEntry> bans() {
        return bans.values().stream()
                .filter(entry -> !entry.expired())
                .sorted(Comparator.comparing(BanEntry::created).reversed());
    }

    @Override
    public Component disconnectMessage(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");

        final String key = switch (entry.target()) {
            case BanTarget.Profile _ -> "multiplayer.disconnect.banned";
            case BanTarget.Address _ -> "multiplayer.disconnect.banned_ip";
        };

        final Component reason = entry.reason() == null
                ? Component.translatable(key)
                : Component.translatable(key + ".reason", entry.reason());

        return entry.permanent()
                ? reason
                : reason.appendNewline()
                  .append(Component.translatable(
                          key + ".expiration",
                          Component.text(entry.expiresLabel())));
    }

    @Override
    public int totalBans() {
        int count = 0;

        for (final BanEntry entry : bans.values()) {
            if (!entry.expired()) {
                count++;
            }
        }

        return count;
    }

    private void read(final Path file, final Function<JsonObject, BanEntry> reader) {
        if (!Files.exists(file)) {
            return;
        }

        try (final Reader in = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            final JsonArray array = GSON.fromJson(in, JsonArray.class);

            if (array == null) {
                return;
            }

            for (final JsonElement element : array) {
                try {
                    final BanEntry entry = reader.apply(element.getAsJsonObject());

                    if (!entry.expired()) {
                        bans.put(entry.target(), entry);
                    }
                } catch (final RuntimeException e) {
                    LOGGER.warn("Skipping malformed entry in {}", file, e);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Unable to read {}", file, e);
        }
    }

    private void write(
            final Path file,
            final Class<? extends BanTarget> kind,
            final Function<BanEntry, JsonObject> writer
    ) {
        final List<BanEntry> entries = bans.values().stream()
                .filter(entry -> kind.isInstance(entry.target()))
                .toList();

        final JsonArray array = new JsonArray();
        entries.forEach(entry -> array.add(writer.apply(entry)));

        try (final Writer out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(array, out);
        } catch (final IOException e) {
            LOGGER.error("Unable to save {}", file, e);
        }
    }

    private static BanEntry readProfile(final JsonObject json) {
        return new BanEntry(
                new BanTarget.Profile(UUID.fromString(json.get("uuid").getAsString())),
                string(json, "name"),
                reason(json),
                string(json, "source"),
                Instant.parse(json.get("created").getAsString()),
                instant(json, "expires"));
    }

    private static BanEntry readAddress(final JsonObject json) {
        return new BanEntry(
                BanTarget.Address.of(json.get("ip").getAsString()),
                null,
                reason(json),
                string(json, "source"),
                Instant.parse(json.get("created").getAsString()),
                instant(json, "expires"));
    }

    private static JsonObject writeProfile(final BanEntry entry) {
        final JsonObject json = new JsonObject();

        json.addProperty("uuid", ((BanTarget.Profile) entry.target()).uuid().toString());
        json.addProperty("name", entry.name());

        return common(json, entry);
    }

    private static JsonObject writeAddress(final BanEntry entry) {
        final JsonObject json = new JsonObject();

        json.addProperty("ip", entry.target().label());

        return common(json, entry);
    }

    private static JsonObject common(final JsonObject json, final BanEntry entry) {
        if (entry.reason() != null) {
            json.add("reason", GsonComponentSerializer.gson().serializeToTree(entry.reason()));
        }

        json.addProperty("source", entry.source());
        json.addProperty("created", entry.created().toString());

        if (entry.expires() != null) {
            json.addProperty("expires", entry.expires().toString());
        }

        return json;
    }

    private static @Nullable String string(final JsonObject json, final String member) {
        final JsonElement element = json.get(member);

        return element == null || element.isJsonNull() ? null : element.getAsString();
    }

    private static @Nullable Instant instant(final JsonObject json, final String member) {
        final String value = string(json, member);

        return value == null ? null : Instant.parse(value);
    }

    private static @Nullable Component reason(final JsonObject json) {
        final JsonElement element = json.get("reason");

        if (element == null || element.isJsonNull()) {
            return null;
        }

        return GsonComponentSerializer.gson().deserializeFromTree(element);
    }
}
