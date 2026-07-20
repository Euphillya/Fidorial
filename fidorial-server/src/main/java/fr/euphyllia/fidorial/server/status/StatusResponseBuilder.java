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

    public static String build(ServerStatus status) {
        JsonObject version = new JsonObject();
        version.addProperty("name", status.version().name());
        version.addProperty("protocol", status.version().protocolVersion());

        JsonArray sample = new JsonArray();
        status.samplePlayers().forEach(samplePlayer -> {
            JsonObject player = new JsonObject();
            player.addProperty("name", samplePlayer.name());
            player.addProperty("id", samplePlayer.id().toString());
            sample.add(player);
        });

        JsonObject players = new JsonObject();
        players.addProperty("max", status.maxPlayers());
        players.addProperty("online", status.players());
        if (!sample.isEmpty()) players.add("sample", sample);

        JsonObject description = new JsonObject();
        JsonElement jsonElement = componentToJsonElement(status.description());
        if (jsonElement.isJsonObject()) {
            description = jsonElement.getAsJsonObject();
        } else {
            description.addProperty("text", jsonElement.getAsString());
        }

        JsonObject root = new JsonObject();
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

    public static JsonElement componentToJsonElement(Component component) {
        return GsonComponentSerializer.gson()
                .serializeToTree(component);
    }
}
