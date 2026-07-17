package fr.euphyllia.fidorial.server.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.chat.MiniText;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

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
        Component motdComponent = MiniText.miniMessage().deserialize(FidorialServer.getInstance().config().motd());
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

    public static JsonElement nbtToJsonElement(Nbt nbt) {
        if (nbt instanceof NbtString(String value)) {
            return new JsonPrimitive(value);
        }
        if (nbt instanceof NbtCompound comp) {
            JsonObject obj = new JsonObject();
            for (String key : comp.tags().keySet()) {
                obj.add(key, nbtToJsonElement(comp.get(key)));
            }
            return obj;
        }
        if (nbt instanceof NbtList list) {
            JsonArray arr = new JsonArray();
            for (int i = 0; i < list.size(); i++) {
                arr.add(nbtToJsonElement(list.get(i)));
            }
            return arr;
        }
        return new JsonPrimitive(nbt.toString());
    }

    public static JsonElement componentToJsonElement(Component component) {
        return GsonComponentSerializer.gson()
                .serializeToTree(component);
    }
}
