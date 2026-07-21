package fr.euphyllia.fidorial.server.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.fidorial.status.Favicon;
import fr.fidorial.status.ServerStatus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.Base64;

public final class StatusResponseBuilder {
    private StatusResponseBuilder() {
    }

    public static String build(final ServerStatus status) {
        final JsonObject version = new JsonObject();
        version.addProperty("name", status.version().name());
        version.addProperty("protocol", status.version().protocolVersion());

        final JsonArray sample = new JsonArray();
        status.samplePlayers().forEach(samplePlayer -> {
            final JsonObject player = new JsonObject();
            player.addProperty("name", samplePlayer.name());
            player.addProperty("id", samplePlayer.id().toString());
            sample.add(player);
        });

        final JsonObject players = new JsonObject();
        players.addProperty("max", status.maxPlayers());
        players.addProperty("online", status.players());
        if (!sample.isEmpty()) players.add("sample", sample);

        JsonObject description = new JsonObject();
        final JsonElement jsonElement = componentToJsonElement(status.description());
        if (jsonElement.isJsonObject()) {
            description = jsonElement.getAsJsonObject();
        } else {
            description.addProperty("text", jsonElement.getAsString());
        }

        final JsonObject root = new JsonObject();
        root.add("version", version);
        root.add("players", players);
        root.add("description", description);

        status.favicon()
                .map(Favicon::data)
                .map(Base64.getEncoder()::encodeToString)
                .map(data -> "data:image/png;base64," + data)
                .ifPresent(favicon -> root.addProperty("favicon", favicon));

        root.addProperty("enforcesSecureChat", status.enforceSecureChat());
        return root.toString();
    }

    public static JsonElement componentToJsonElement(final Component component) {
        return GsonComponentSerializer.gson()
                .serializeToTree(component);
    }
}
