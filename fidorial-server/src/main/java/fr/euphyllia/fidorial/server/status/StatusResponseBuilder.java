package fr.euphyllia.fidorial.server.status;

import com.google.gson.JsonObject;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;

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
        description.addProperty("text", "Fidorial | serveur regionalise from scratch");

        JsonObject root = new JsonObject();
        root.add("version", version);
        root.add("players", players);
        root.add("description", description);
        root.addProperty("enforcesSecureChat", false);
        return root.toString();
    }
}
