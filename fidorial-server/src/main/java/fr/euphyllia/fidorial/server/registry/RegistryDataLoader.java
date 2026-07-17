package fr.euphyllia.fidorial.server.registry;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RegistryDataLoader {

    private static final String RESOURCE = "/data/registries.json.gz";

    private final Map<String, Registry> dynamic = new LinkedHashMap<>();
    private final Map<String, Registry> frozen = new LinkedHashMap<>();

    private RegistryDataLoader() {
    }

    static RegistryDataLoader load() {
        RegistryDataLoader loader = new RegistryDataLoader();
        try (InputStream raw = RegistryDataLoader.class.getResourceAsStream(RESOURCE)) {
            if (raw == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            loader.read(new GZIPInputStream(raw));
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to load " + RESOURCE, exception);
        }
        return loader;
    }

    Map<String, Registry> dynamic() {
        return dynamic;
    }

    Map<String, Registry> frozen() {
        return frozen;
    }

    private void read(InputStream input) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "dynamic" -> readGroup(reader, dynamic);
                    case "frozen" -> readGroup(reader, frozen);
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
        }
    }

    private void readGroup(JsonReader reader, Map<String, Registry> target) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            target.put(name, readRegistry(name, reader));
        }
        reader.endObject();
    }

    private Registry readRegistry(String name, JsonReader reader) throws IOException {
        List<String> entries = List.of();
        Map<String, List<String>> tags = Map.of();

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "entries" -> entries = readStrings(reader);
                case "tags" -> {
                    tags = new LinkedHashMap<>();
                    reader.beginObject();
                    while (reader.hasNext()) {
                        tags.put(reader.nextName(), readStrings(reader));
                    }
                    reader.endObject();
                }
                default -> reader.skipValue();
            }
        }
        reader.endObject();
        return new Registry(name, entries, tags);
    }

    private List<String> readStrings(JsonReader reader) throws IOException {
        List<String> values = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            values.add(reader.nextString());
        }
        reader.endArray();
        return values;
    }
}
