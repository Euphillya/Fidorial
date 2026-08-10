package fr.euphyllia.fidorial.server.moderation;

import com.google.common.net.InetAddresses;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class BanList implements BanService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(BanList.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path profileFile;
    private final Path addressFile;
    private final Map<UUID, BanEntry.Profile> profiles = new ConcurrentHashMap<>();
    private final Map<InetAddress, BanEntry.Address> addresses = new ConcurrentHashMap<>();

    public BanList(final Path profileFile, final Path addressFile) {
        this.profileFile = Objects.requireNonNull(profileFile, "profileFile");
        this.addressFile = Objects.requireNonNull(addressFile, "addressFile");
    }

    public void load() {
        profiles.clear();
        addresses.clear();

        read(profileFile, BanList::readProfile, entry -> profiles.put(entry.uuid(), entry));
        read(addressFile, BanList::readAddress, entry -> addresses.put(entry.address(), entry));

        LOGGER.debug("There are currently {} active ban(s)", profiles.size() + addresses.size());
    }

    public synchronized void save() {
        write(profileFile, profiles.values(), BanList::writeProfile);
        write(addressFile, addresses.values(), BanList::writeAddress);
    }

    public int purgeExpired() {
        final int removed = purgeExpired(profiles) + purgeExpired(addresses);

        if (removed > 0) {
            save();
        }

        return removed;
    }

    @Override
    public Optional<BanEntry.Profile> find(final UUID uuid) {
        return lookup(profiles, uuid);
    }

    @Override
    public Optional<BanEntry.Address> find(final InetAddress address) {
        return lookup(addresses, address);
    }

    @Override
    public Optional<BanEntry> findByName(final String name) {
        return active()
                .filter(entry -> entry.name() != null && entry.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean ban(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");

        final boolean isNew = switch (entry) {
            case final BanEntry.Profile profile -> store(profiles, profile.uuid(), profile);
            case final BanEntry.Address address -> store(addresses, address.address(), address);
        };

        save();

        return isNew;
    }

    @Override
    public boolean pardon(final UUID uuid) {
        return lift(profiles, Objects.requireNonNull(uuid, "uuid"));
    }

    @Override
    public boolean pardon(final InetAddress address) {
        return lift(addresses, Objects.requireNonNull(address, "address"));
    }

    @Override
    public Stream<BanEntry> bans() {
        return active().sorted(Comparator.comparing(BanEntry::created).reversed());
    }

    @Override
    public Component disconnectMessage(final BanEntry entry) {
        Objects.requireNonNull(entry, "entry");

        final String key = switch (entry) {
            case BanEntry.Profile _ -> "multiplayer.disconnect.banned";
            case BanEntry.Address _ -> "multiplayer.disconnect.banned_ip";
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
        return (int) active().count();
    }

    private Stream<BanEntry> active() {
        return Stream.<BanEntry>concat(profiles.values().stream(), addresses.values().stream())
                .filter(entry -> !entry.expired());
    }

    private <K, E extends BanEntry> Optional<E> lookup(final Map<K, E> bans, final K key) {
        final E entry = bans.get(key);

        if (entry == null) {
            return Optional.empty();
        }

        if (entry.expired()) {
            if (bans.remove(key, entry)) {
                save();
            }
            return Optional.empty();
        }

        return Optional.of(entry);
    }

    private <K, E extends BanEntry> boolean store(final Map<K, E> bans, final K key, final E entry) {
        final E previous = bans.put(key, entry);

        return previous == null || previous.expired();
    }

    private <K, E extends BanEntry> boolean lift(final Map<K, E> bans, final K key) {
        if (bans.remove(key) == null) {
            return false;
        }

        save();

        return true;
    }

    private static <K, E extends BanEntry> int purgeExpired(final Map<K, E> bans) {
        int removed = 0;

        for (final Map.Entry<K, E> ban : bans.entrySet()) {
            if (ban.getValue().expired() && bans.remove(ban.getKey(), ban.getValue())) {
                removed++;
            }
        }

        return removed;
    }

    private static <E extends BanEntry> void read(
            final Path file,
            final Function<JsonObject, E> reader,
            final Consumer<E> sink
    ) {
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
                    final E entry = reader.apply(element.getAsJsonObject());

                    if (!entry.expired()) {
                        sink.accept(entry);
                    }
                } catch (final RuntimeException e) {
                    LOGGER.warn("Skipping malformed entry in {}", file, e);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Unable to read {}", file, e);
        }
    }

    private static <E extends BanEntry> void write(
            final Path file,
            final Collection<E> entries,
            final Function<E, JsonObject> writer
    ) {
        final JsonArray array = new JsonArray();
        entries.forEach(entry -> array.add(writer.apply(entry)));

        try (final Writer out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(array, out);
        } catch (final IOException e) {
            LOGGER.error("Unable to save {}", file, e);
        }
    }

    private static BanEntry.Profile readProfile(final JsonObject json) {
        return new BanEntry.Profile(
                UUID.fromString(json.get("uuid").getAsString()),
                string(json, "name"),
                reason(json),
                uuid(json, "source"),
                Instant.parse(json.get("created").getAsString()),
                instant(json, "expires"));
    }

    private static BanEntry.Address readAddress(final JsonObject json) {
        return new BanEntry.Address(
                InetAddresses.forString(json.get("ip").getAsString()),
                string(json, "name"),
                reason(json),
                uuid(json, "source"),
                Instant.parse(json.get("created").getAsString()),
                instant(json, "expires"));
    }

    private static JsonObject writeProfile(final BanEntry.Profile entry) {
        final JsonObject json = new JsonObject();

        json.addProperty("uuid", entry.uuid().toString());
        json.addProperty("name", entry.name());

        return common(json, entry);
    }

    private static JsonObject writeAddress(final BanEntry.Address entry) {
        final JsonObject json = new JsonObject();

        json.addProperty("ip", entry.address().getHostAddress());
        json.addProperty("name", entry.name());

        return common(json, entry);
    }

    private static JsonObject common(final JsonObject json, final BanEntry entry) {
        if (entry.reason() != null) {
            json.add("reason", GsonComponentSerializer.gson().serializeToTree(entry.reason()));
        }

        json.addProperty("source", entry.source() == null ? null : entry.source().toString());
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

    private static @Nullable UUID uuid(final JsonObject json, final String member) {
        final String value = string(json, member);

        return value == null ? null : UUID.fromString(value);
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