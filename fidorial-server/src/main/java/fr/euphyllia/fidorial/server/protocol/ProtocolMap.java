package fr.euphyllia.fidorial.server.protocol;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class ProtocolMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolMap.class);
    private static final String RESOURCE = "/protocol/26.2.json";
    private final Map<ConnectionState, Map<Boolean, Direction>> table = new EnumMap<>(ConnectionState.class);
    private final boolean available;
    private ProtocolMap(boolean available) {
        this.available = available;
        seedFixedStates();
    }

    public static ProtocolMap load() {
        try (InputStream in = ProtocolMap.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                LOGGER.warn("Ressource {} absente : lance tools/extract-protocol.sh <server.jar> "
                        + "pour generer la table. Les phases Configuration/Play sont desactivees.", RESOURCE);
                return new ProtocolMap(false);
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();

            ProtocolMap map = new ProtocolMap(true);
            for (ConnectionState state : new ConnectionState[]{
                    ConnectionState.CONFIGURATION, ConnectionState.PLAY}) {
                JsonObject stateJson = root.getAsJsonObject(state.name().toLowerCase());
                map.put(state, false, parse(stateJson, "serverbound"));
                map.put(state, true, parse(stateJson, "clientbound"));
            }
            LOGGER.info("Table de protocole 26.2 chargee (Configuration + Play).");
            return map;
        } catch (Exception e) {
            LOGGER.error("Table de protocole illisible", e);
            return new ProtocolMap(false);
        }
    }

    private static Map<String, Integer> parse(JsonObject stateJson, String direction) {
        if (stateJson == null || !stateJson.has(direction)) return Map.of();
        Map<String, Integer> byName = new HashMap<>();
        for (var entry : stateJson.getAsJsonObject(direction).entrySet()) {
            byName.put(entry.getKey(), entry.getValue().getAsInt());
        }
        return byName;
    }

    private void seedFixedStates() {
        put(ConnectionState.HANDSHAKE, false, Map.of(
                "minecraft:intention", 0));

        put(ConnectionState.STATUS, true, Map.of(
                "minecraft:status_response", 0,
                "minecraft:pong_response", 1));
        put(ConnectionState.STATUS, false, Map.of(
                "minecraft:status_request", 0,
                "minecraft:ping_request", 1));

        put(ConnectionState.LOGIN, true, Map.of(
                "minecraft:login_disconnect", 0,
                "minecraft:hello", 1,
                "minecraft:login_finished", 2,
                "minecraft:login_compression", 3,
                "minecraft:custom_query", 4,
                "minecraft:cookie_request", 5));
        put(ConnectionState.LOGIN, false, Map.of(
                "minecraft:hello", 0,
                "minecraft:key", 1,
                "minecraft:custom_query_answer", 2,
                "minecraft:login_acknowledged", 3,
                "minecraft:cookie_response", 4));
    }

    private void put(ConnectionState state, boolean clientbound, Map<String, Integer> byName) {
        table.computeIfAbsent(state, s -> new HashMap<>()).put(clientbound, Direction.of(byName));
    }

    public boolean isAvailable() {
        return available;
    }

    private Direction dir(ConnectionState state, boolean clientbound) {
        return table.getOrDefault(state, Map.of()).getOrDefault(clientbound, Direction.empty());
    }

    public int clientboundId(ConnectionState state, String name) {
        Integer id = dir(state, true).byName().get(name);
        if (id == null) throw new IllegalStateException(
                "Paquet clientbound inconnu dans la table : " + state + "/" + name);
        return id;
    }

    public String serverboundName(ConnectionState state, int id) {
        return dir(state, false).byId().get(id);
    }

    public int serverboundId(ConnectionState state, String name) {
        Integer id = dir(state, false).byName().get(name);
        if (id == null) throw new IllegalStateException(
                "Paquet serverbound inconnu dans la table : " + state + "/" + name);
        return id;
    }

    private record Direction(Map<String, Integer> byName, Map<Integer, String> byId) {
        static Direction empty() {
            return new Direction(Map.of(), Map.of());
        }

        static Direction of(Map<String, Integer> byName) {
            Map<Integer, String> byId = new HashMap<>();
            byName.forEach((n, i) -> byId.put(i, n));
            return new Direction(Map.copyOf(byName), Map.copyOf(byId));
        }
    }
}
