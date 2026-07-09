package fr.euphyllia.fidorial.server.protocol;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DynamicRegistries {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRegistries.class);
    private static final String RESOURCE = "/datapack_registries.json";
    private final Map<String, Registry> registries;

    private DynamicRegistries(Map<String, Registry> registries) {
        this.registries = registries;
    }

    public static DynamicRegistries load() {
        try (InputStream in = DynamicRegistries.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                LOGGER.warn("Ressource {} absente : lance "
                        + "tools/extract-datapack-registries.py <minecraft.jar>. "
                        + "Sans elle, le client refusera la connexion.", RESOURCE);
                return new DynamicRegistries(Map.of());
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();

            Map<String, Registry> result = new LinkedHashMap<>();
            for (Map.Entry<String, JsonElement> reg : root.entrySet()) {
                JsonObject obj = reg.getValue().getAsJsonObject();

                List<String> entries = new ArrayList<>();
                for (JsonElement e : obj.getAsJsonArray("entries")) {
                    entries.add(e.getAsString());
                }

                Map<String, List<String>> tags = new LinkedHashMap<>();
                JsonObject tagsObj = obj.getAsJsonObject("tags");
                if (tagsObj != null) {
                    for (Map.Entry<String, JsonElement> tag : tagsObj.entrySet()) {
                        List<String> values = new ArrayList<>();
                        for (JsonElement v : tag.getValue().getAsJsonArray()) {
                            values.add(v.getAsString());
                        }
                        tags.put(tag.getKey(), List.copyOf(values));
                    }
                }
                result.put(reg.getKey(), new Registry(List.copyOf(entries), tags));
            }
            int nt = result.values().stream().mapToInt(r -> r.tags().size()).sum();
            LOGGER.info("Registres dynamiques charges : {} registres, {} tags.",
                    result.size(), nt);
            return new DynamicRegistries(result);
        } catch (Exception e) {
            LOGGER.error("datapack_registries.json illisible", e);
            return new DynamicRegistries(Map.of());
        }
    }

    public boolean isEmpty() {
        return registries.isEmpty();
    }

    public Map<String, Registry> registries() {
        return registries;
    }

    public int networkId(String registry, String entry) {
        Registry reg = registries.get(registry);
        return reg == null ? -1 : reg.entries().indexOf(entry);
    }

    public record Registry(List<String> entries, Map<String, List<String>> tags) {
    }
}