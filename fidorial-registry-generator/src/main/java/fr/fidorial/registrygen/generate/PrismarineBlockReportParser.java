package fr.fidorial.registrygen.generate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.fidorial.registrygen.model.PrismarineBlockLightPropertiesDefinition;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parses PrismarineJS's {@code minecraft-data} {@code blocks.json} report.
 * Currently only parses the block name, light emission and opacity
 *
 * @since 0.1.0
 */
public final class PrismarineBlockReportParser {

    /**
     * Parses a Prismarine {@code blocks.json} file into a name-keyed lighting lookup.
     *
     * @param blocksJson path to Prismarine's {@code blocks.json}
     * @return lighting definitions keyed by plain block name
     * @throws IOException if the file cannot be read or isn't the expected array shape
     */
    public Map<String, PrismarineBlockLightPropertiesDefinition> parse(final Path blocksJson) throws IOException {

        final JsonArray root;
        try (final Reader reader = Files.newBufferedReader(blocksJson, StandardCharsets.UTF_8)) {
            root = JsonParser.parseReader(reader).getAsJsonArray();
        }

        final Map<String, PrismarineBlockLightPropertiesDefinition> lighting = new LinkedHashMap<>();

        for (final JsonElement element : root) {

            final JsonObject blockObject = element.getAsJsonObject();
            final String name = blockObject.get("name").getAsString();

            final int emitLight = blockObject.has("emitLight") ? blockObject.get("emitLight").getAsInt() : 0;
            final int filterLight = blockObject.has("filterLight") ? blockObject.get("filterLight").getAsInt() : 0;

            lighting.put(name, new PrismarineBlockLightPropertiesDefinition(name, emitLight, filterLight));
        }

        return Map.copyOf(lighting);
    }
}
