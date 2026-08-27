package fr.euphyllia.fidorial.server.registry;

import com.google.gson.stream.JsonReader;
import net.kyori.adventure.key.Key;

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

    private final Map<Key, Registry> dynamic = new LinkedHashMap<>();
    private final Map<Key, Registry> frozen = new LinkedHashMap<>();

    private RegistryDataLoader() {
    }

    static RegistryDataLoader load() {
        final RegistryDataLoader loader = new RegistryDataLoader();
        try (final InputStream raw = RegistryDataLoader.class.getResourceAsStream(RESOURCE)) {
            if (raw == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            loader.read(new GZIPInputStream(raw));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to load " + RESOURCE, exception);
        }
        return loader;
    }

    Map<Key, Registry> dynamic() {
        return dynamic;
    }

    Map<Key, Registry> frozen() {
        return frozen;
    }

    private void read(final InputStream input) throws IOException {
        try (final JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
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

    private void readGroup(final JsonReader reader, final Map<Key, Registry> target) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            final Key name = Key.key(reader.nextName());
            target.put(name, readRegistry(name, reader));
        }
        reader.endObject();
    }

    private Registry readRegistry(final Key name, final JsonReader reader) throws IOException {
        List<Key> entries = List.of();
        Map<Key, List<Key>> tags = Map.of();

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "entries" -> entries = readKeys(reader);
                case "tags" -> {
                    tags = new LinkedHashMap<>();
                    reader.beginObject();
                    while (reader.hasNext()) {
                        tags.put(Key.key(reader.nextName()), readKeys(reader));
                    }
                    reader.endObject();
                }
                default -> reader.skipValue();
            }
        }
        reader.endObject();
        return new Registry(name, entries, tags);
    }

    private List<Key> readKeys(final JsonReader reader) throws IOException {
        final List<Key> values = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            values.add(Key.key(reader.nextString()));
        }
        reader.endArray();
        return values;
    }
}
