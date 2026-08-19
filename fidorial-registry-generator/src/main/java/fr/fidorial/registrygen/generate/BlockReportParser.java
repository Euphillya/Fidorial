package fr.fidorial.registrygen.generate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.fidorial.registrygen.model.BlockPropertyDefinition;
import fr.fidorial.registrygen.model.BlockReportDefinition;
import fr.fidorial.registrygen.model.BlockStateEntry;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BlockReportParser {

    private static final Gson GSON = new Gson();

    public List<BlockReportDefinition> parse(final Path blocksJson) throws IOException {

        final JsonObject root;
        try (Reader reader = Files.newBufferedReader(blocksJson, StandardCharsets.UTF_8)) {
            root = GSON.fromJson(reader, JsonObject.class);
        }

        final List<BlockReportDefinition> blocks = new ArrayList<>();
        for (final Map.Entry<String, JsonElement> entry : root.entrySet()) {
            blocks.add(parseBlock(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
        return List.copyOf(blocks);
    }

    private BlockReportDefinition parseBlock(final String identifier, final JsonObject blockObject) {

        final List<BlockPropertyDefinition> properties = new ArrayList<>();
        if (blockObject.has("properties")) {
            for (final Map.Entry<String, JsonElement> propEntry : blockObject.getAsJsonObject("properties").entrySet()) {
                final List<String> values = new ArrayList<>();
                for (final JsonElement value : propEntry.getValue().getAsJsonArray()) {
                    values.add(value.getAsString());
                }
                properties.add(new BlockPropertyDefinition(propEntry.getKey(), List.copyOf(values)));
            }
        }

        final List<BlockStateEntry> states = new ArrayList<>();
        for (final JsonElement stateElement : blockObject.getAsJsonArray("states")) {
            final JsonObject stateObject = stateElement.getAsJsonObject();

            final Map<String, String> stateProperties = new LinkedHashMap<>();
            if (stateObject.has("properties")) {
                for (final Map.Entry<String, JsonElement> propEntry : stateObject.getAsJsonObject("properties").entrySet()) {
                    stateProperties.put(propEntry.getKey(), propEntry.getValue().getAsString());
                }
            }

            final boolean isDefault = stateObject.has("default") && stateObject.get("default").getAsBoolean();
            states.add(new BlockStateEntry(stateObject.get("id").getAsInt(), Map.copyOf(stateProperties), isDefault));
        }

        return new BlockReportDefinition(identifier, List.copyOf(properties), List.copyOf(states));
    }
}
