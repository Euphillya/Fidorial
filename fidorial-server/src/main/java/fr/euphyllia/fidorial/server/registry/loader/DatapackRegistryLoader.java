package fr.euphyllia.fidorial.server.registry.loader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Todo : plus tard tout sera géneré
public final class DatapackRegistryLoader extends JsonRegistryLoader {

    @Override
    protected String resource() {
        return "/datapack_registries.json";
    }

    @Override
    protected String missingResourceMessage() {
        return "Ressource /datapack_registries.json absente : lance "
                + "tools/extract-datapack-registries.py <minecraft.jar>. "
                + "Sans elle, le client refusera la connexion.";
    }

    @Override
    protected RegistryHolder parse(JsonObject root) {
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
                    tags.put(tag.getKey(), values);
                }
            }
            result.put(reg.getKey(), new Registry(reg.getKey(), entries, tags));
        }

        RegistryHolder holder = RegistryHolder.of(result);
        logger.info("Registres dynamiques charges : {} registres, {} tags.",
                holder.size(), holder.tagCount());
        return holder;
    }
}
