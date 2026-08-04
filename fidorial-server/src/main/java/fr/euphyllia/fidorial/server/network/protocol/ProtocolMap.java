package fr.euphyllia.fidorial.server.network.protocol;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class ProtocolMap {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ProtocolMap.class);
    private static final String RESOURCE = "/protocol/26.2.json";
    private final Map<ConnectionState, Map<Boolean, Direction>> table = new EnumMap<>(ConnectionState.class);
    private final boolean available;

    private ProtocolMap(final boolean available) {
        this.available = available;
        seedFixedStates();
    }

    public static ProtocolMap load() {
        try (final InputStream in = ProtocolMap.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                LOGGER.warn(
                        "Resource {} missing",
                        RESOURCE);
                return new ProtocolMap(false);
            }
            final JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject();

            final ProtocolMap map = new ProtocolMap(true);
            for (final ConnectionState state : new ConnectionState[]{ConnectionState.CONFIGURATION, ConnectionState.PLAY}) {
                final JsonObject stateJson = root.getAsJsonObject(state.name().toLowerCase());
                map.put(state, false, parse(stateJson, "serverbound"));
                map.put(state, true, parse(stateJson, "clientbound"));
            }
            LOGGER.info("Loaded 26.2 protocol table (Configuration + Play).");
            return map;
        } catch (final Exception e) {
            LOGGER.error("Illegible protocol table", e);
            return new ProtocolMap(false);
        }
    }

    private static Map<Key, Integer> parse(@Nullable final JsonObject stateJson, final String direction) {
        if (stateJson == null || !stateJson.has(direction)) return Map.of();
        final Map<Key, Integer> byName = new HashMap<>();
        for (final var entry : stateJson.getAsJsonObject(direction).entrySet()) {
            byName.put(Key.key(entry.getKey()), entry.getValue().getAsInt());
        }
        return byName;
    }

    private void seedFixedStates() {
        put(ConnectionState.HANDSHAKE, false, Map.of(Key.key("intention"), 0));

        put(
                ConnectionState.STATUS,
                true,
                Map.of(
                        Key.key("status_response"), 0,
                        Key.key("pong_response"), 1));
        put(
                ConnectionState.STATUS,
                false,
                Map.of(
                        Key.key("status_request"), 0,
                        Key.key("ping_request"), 1));

        put(
                ConnectionState.LOGIN,
                true,
                Map.of(
                        Key.key("login_disconnect"), 0,
                        Key.key("hello"), 1,
                        Key.key("login_finished"), 2,
                        Key.key("login_compression"), 3,
                        Key.key("custom_query"), 4,
                        Key.key("cookie_request"), 5));
        put(
                ConnectionState.LOGIN,
                false,
                Map.of(
                        Key.key("hello"), 0,
                        Key.key("key"), 1,
                        Key.key("custom_query_answer"), 2,
                        Key.key("login_acknowledged"), 3,
                        Key.key("cookie_response"), 4));
    }

    private void put(final ConnectionState state, final boolean clientbound, final Map<Key, Integer> byName) {
        table.computeIfAbsent(state, s -> new HashMap<>()).put(clientbound, Direction.of(byName));
    }

    public boolean isAvailable() {
        return available;
    }

    private Direction dir(final ConnectionState state, final boolean clientbound) {
        return table.getOrDefault(state, Map.of()).getOrDefault(clientbound, Direction.empty());
    }

    public int clientboundId(final ConnectionState state, final Key name) {
        final Integer id = dir(state, true).byName().get(name);
        if (id == null)
            throw new IllegalStateException("Paquet clientbound inconnu dans la table : " + state + "/" + name);
        return id;
    }

    public @Nullable Key serverboundName(final ConnectionState state, final int id) {
        return dir(state, false).byId().get(id);
    }

    public int serverboundId(final ConnectionState state, final Key name) {
        final Integer id = dir(state, false).byName().get(name);
        if (id == null)
            throw new IllegalStateException("Paquet serverbound inconnu dans la table : " + state + "/" + name);
        return id;
    }

    private record Direction(Map<Key, Integer> byName, Map<Integer, Key> byId) {
        static Direction empty() {
            return new Direction(Map.of(), Map.of());
        }

        static Direction of(final Map<Key, Integer> byName) {
            final Map<Integer, Key> byId = new HashMap<>();
            byName.forEach((n, i) -> byId.put(i, n));
            return new Direction(Map.copyOf(byName), Map.copyOf(byId));
        }
    }
}
