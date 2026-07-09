package fr.euphyllia.fidorial.server.protocol;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class RegistrySnapshot {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrySnapshot.class);
    private static final String RESOURCE = "/registries.json";

    private final Map<String, List<String>> registries;

    private RegistrySnapshot(Map<String, List<String>> registries) {
        this.registries = registries;
    }

    public static RegistrySnapshot load() {
        try (InputStream in = RegistrySnapshot.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                LOGGER.warn("Ressource {} absente : lance tools/extract-protocol.sh <server.jar> "
                        + "pour extraire les registres synchronises du datapack. La phase "
                        + "Configuration ne pourra pas envoyer les registres.", RESOURCE);
                return new RegistrySnapshot(Map.of());
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();

            Map<String, List<String>> result = new LinkedHashMap<>();
            for (Map.Entry<String, JsonElement> reg : root.entrySet()) {
                JsonObject registry = reg.getValue().getAsJsonObject();
                JsonObject entries = registry.getAsJsonObject("entries");

                List<Map.Entry<String, JsonElement>> sorted =
                        new ArrayList<>(entries.entrySet());

                sorted.sort(Comparator.comparingInt(e ->
                        e.getValue()
                                .getAsJsonObject()
                                .get("protocol_id")
                                .getAsInt()));

                List<String> entryList = new ArrayList<>(sorted.size());

                for (Map.Entry<String, JsonElement> entry : sorted) {
                    entryList.add(entry.getKey());
                }

                result.put(reg.getKey(), entryList);
            }
            LOGGER.info("Registres synchronises charges : {} registres.", result.size());
            return new RegistrySnapshot(result);
        } catch (Exception e) {
            LOGGER.error("registries.json illisible", e);
            return new RegistrySnapshot(Map.of());
        }
    }

    public boolean isEmpty() {
        return registries.isEmpty();
    }

    public Map<String, List<String>> registries() {
        return registries;
    }

    public int networkId(String registry, String entry) {
        List<String> entries = registries.get(registry);
        return entries == null ? -1 : entries.indexOf(entry);
    }
}
