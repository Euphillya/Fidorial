package fr.euphyllia.fidorial.server.registry.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;

import java.util.*;

public final class SnapshotRegistryLoader extends JsonRegistryLoader {

    @Override
    protected String resource() {
        return "/registries.json";
    }

    @Override
    protected String missingResourceMessage() {
        return "Ressource /registries.json absente : lance tools/extract-protocol.sh <server.jar> "
                + "pour extraire les registres synchronises du datapack. La phase "
                + "Configuration ne pourra pas envoyer les registres.";
    }

    @Override
    protected RegistryHolder parse(JsonObject root) {
        Map<String, Registry> result = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> reg : root.entrySet()) {
            JsonObject registry = reg.getValue().getAsJsonObject();
            JsonObject entries = registry.getAsJsonObject("entries");

            List<Map.Entry<String, JsonElement>> sorted = new ArrayList<>(entries.entrySet());
            sorted.sort(Comparator.comparingInt(e ->
                    e.getValue().getAsJsonObject().get("protocol_id").getAsInt()));

            List<String> entryList = new ArrayList<>(sorted.size());
            for (Map.Entry<String, JsonElement> entry : sorted) {
                entryList.add(entry.getKey());
            }
            result.put(reg.getKey(), Registry.of(reg.getKey(), entryList));
        }

        RegistryHolder holder = RegistryHolder.of(result);
        logger.info("Registres synchronises charges : {} registres.", holder.size());
        return holder;
    }
}
