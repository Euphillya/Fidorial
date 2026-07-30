package fr.euphyllia.fidorial.auth;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class MojangProfileRepository {

    private static final URI BULK_NAMES = URI.create("https://api.mojang.com/profiles/minecraft");
    private static final String SINGLE_NAME = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String PROFILE_BY_ID = "https://sessionserver.mojang.com/session/minecraft/profile/%s";
    private static final int BATCH_SIZE = 10;

    private final HttpClient http;
    private final Duration timeout;

    public MojangProfileRepository() {
        this(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build(), Duration.ofSeconds(10));
    }

    /**
     * Creates a repository using the given HTTP client.
     *
     * @param http    the client used to issue requests
     * @param timeout the timeout applied to each request
     */
    public MojangProfileRepository(final HttpClient http, final Duration timeout) {
        this.http = http;
        this.timeout = timeout;
    }

    private static UUID undash(final String id) {
        return UUID.fromString(id.replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

    private static String dashless(final UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    /**
     * Resolves a single name.
     *
     * @param name the player name
     * @return a future completing with the profile, or with an empty result if the name is unused
     * or the API cannot be reached
     */
    public CompletableFuture<Optional<GameProfile>> byName(final String name) {
        final HttpRequest request = HttpRequest.newBuilder(
                        URI.create(SINGLE_NAME.formatted(URLEncoder.encode(name, StandardCharsets.UTF_8))))
                .timeout(timeout)
                .GET()
                .build();

        return http.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200 || response.body().isBlank()) {
                        return Optional.<GameProfile>empty();
                    }
                    final JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                    return Optional.of(toProfile(json));
                })
                .exceptionally(_ -> Optional.empty());
    }

    /**
     * Resolves several names, issuing one request per batch accepted by the API.
     *
     * @param names the player names; duplicates and case differences are collapsed
     * @return a future completing with the resolved profiles, keyed by lowercase name; names that
     * do not resolve are absent from the result
     */
    public CompletableFuture<Map<String, GameProfile>> byNames(final Collection<String> names) {
        final Set<String> distinct = new LinkedHashSet<>();
        for (final String name : names) {
            distinct.add(name.toLowerCase(Locale.ROOT));
        }
        if (distinct.isEmpty()) {
            return CompletableFuture.completedFuture(Map.of());
        }

        final List<String> ordered = new ArrayList<>(distinct);
        final List<CompletableFuture<Map<String, GameProfile>>> batches = new ArrayList<>();

        for (int from = 0; from < ordered.size(); from += BATCH_SIZE) {
            batches.add(batch(ordered.subList(from, Math.min(from + BATCH_SIZE, ordered.size()))));
        }

        return CompletableFuture.allOf(batches.toArray(CompletableFuture[]::new))
                .thenApply(_ -> {
                    final Map<String, GameProfile> merged = new HashMap<>();
                    for (final CompletableFuture<Map<String, GameProfile>> batch : batches) {
                        merged.putAll(batch.join());
                    }
                    return Map.copyOf(merged);
                });
    }

    private CompletableFuture<Map<String, GameProfile>> batch(final List<String> names) {
        final JsonArray payload = new JsonArray();
        names.forEach(payload::add);

        final HttpRequest request = HttpRequest.newBuilder(BULK_NAMES)
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                .build();

        return http.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200 || response.body().isBlank()) {
                        return Map.<String, GameProfile>of();
                    }
                    final Map<String, GameProfile> found = new HashMap<>();
                    for (final JsonElement element : JsonParser.parseString(response.body()).getAsJsonArray()) {
                        final GameProfile profile = toProfile(element.getAsJsonObject());
                        found.put(profile.name().toLowerCase(Locale.ROOT), profile);
                    }
                    return found;
                })
                .exceptionally(_ -> Map.of());
    }

    /**
     * Reads the full profile of an identity, including its signed properties.
     *
     * @param uuid the player identity
     * @return a future completing with the profile, or with an empty result if the identity is
     * unknown or the API cannot be reached
     */
    public CompletableFuture<Optional<GameProfile>> byId(final UUID uuid) {
        final HttpRequest request = HttpRequest.newBuilder(URI.create(PROFILE_BY_ID.formatted(dashless(uuid))))
                .timeout(timeout)
                .GET()
                .build();

        return http.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200 || response.body().isBlank()) {
                        return Optional.<GameProfile>empty();
                    }
                    final JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                    return Optional.of(toProfile(json));
                })
                .exceptionally(_ -> Optional.empty());
    }

    private static GameProfile toProfile(final JsonObject json) {
        final List<GameProfile.Property> properties = new ArrayList<>();
        if (json.has("properties")) {
            for (final JsonElement element : json.getAsJsonArray("properties")) {
                final JsonObject property = element.getAsJsonObject();
                properties.add(new GameProfile.Property(
                        property.get("name").getAsString(),
                        property.get("value").getAsString(),
                        property.has("signature") ? property.get("signature").getAsString() : null));
            }
        }
        return new GameProfile(
                undash(json.get("id").getAsString()),
                json.get("name").getAsString(),
                UUID.randomUUID(),
                List.copyOf(properties));
    }
}
