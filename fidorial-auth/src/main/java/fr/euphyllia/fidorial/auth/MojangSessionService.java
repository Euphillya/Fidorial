package fr.euphyllia.fidorial.auth;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public final class MojangSessionService {

    private static final String HAS_JOINED_URL =
            "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s";

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static UUID undash(String id) {
        return UUID.fromString(id.replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

    public Optional<GameProfile> hasJoined(String username, String serverHash)
            throws IOException, InterruptedException {
        String url = HAS_JOINED_URL.formatted(
                URLEncoder.encode(username, StandardCharsets.UTF_8), serverHash);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 || response.body().isBlank()) {
            return Optional.empty(); // 204 = session invalide
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        UUID uuid = undash(json.get("id").getAsString());
        String name = json.get("name").getAsString();

        List<GameProfile.Property> props = new ArrayList<>();
        if (json.has("properties")) {
            JsonArray array = json.getAsJsonArray("properties");
            for (JsonElement el : array) {
                JsonObject p = el.getAsJsonObject();
                props.add(new GameProfile.Property(
                        p.get("name").getAsString(),
                        p.get("value").getAsString(),
                        p.has("signature") ? p.get("signature").getAsString() : null));
            }
        }
        return Optional.of(new GameProfile(
                uuid,
                name,
                UUID.randomUUID(),
                List.copyOf(props)
        ));
    }
}
