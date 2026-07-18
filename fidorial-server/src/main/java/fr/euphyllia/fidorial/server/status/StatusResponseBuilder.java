package fr.euphyllia.fidorial.server.status;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.MINI_MESSAGE;

public final class StatusResponseBuilder {

    private StatusResponseBuilder() {
    }

    public static String build(int clientProtocol) {
        int reported = ProtocolConstants.PROTOCOL_VERSION > 0
                ? ProtocolConstants.PROTOCOL_VERSION
                : clientProtocol;

        JsonObject version = new JsonObject();
        version.addProperty("name", "Fidorial " + ProtocolConstants.MINECRAFT_VERSION);
        version.addProperty("protocol", reported);

        JsonObject players = new JsonObject();
        players.addProperty("max", 100);
        players.addProperty("online", FidorialServer.getInstance().playerCount());

        JsonObject description = new JsonObject();
        Component motdComponent = MINI_MESSAGE.deserialize(FidorialServer.getInstance().config().motd());
        JsonElement jsonElement = componentToJsonElement(motdComponent);
        if (jsonElement.isJsonObject()) {
            description = jsonElement.getAsJsonObject();
        } else {
            description.addProperty("text", jsonElement.getAsString());
        }

        JsonObject root = new JsonObject();
        root.add("version", version);
        root.add("players", players);
        root.add("description", description);
        root.addProperty("enforcesSecureChat", false);
        return root.toString();
    }

    public static JsonElement componentToJsonElement(Component component) {
        return GsonComponentSerializer.gson()
                .serializeToTree(component);
    }
}
