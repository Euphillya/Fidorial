package fr.euphyllia.fidorial.server.world.block;

import com.google.gson.stream.JsonReader;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockProperty;
import fr.fidorial.world.block.BlockRegistry;
import fr.fidorial.world.block.BlockType;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

/**
 * @deprecated do not build anything new on top of it.
 */
@Deprecated(forRemoval = true)
public final class VanillaBlockRegistry implements BlockRegistry {

    private static final String RESOURCE = "/data/blocks.json.gz";

    private final Map<Key, BlockType> types = new ConcurrentHashMap<>();
    private final Map<Integer, BlockData> byNetworkId = new ConcurrentHashMap<>();

    public VanillaBlockRegistry() {
        try (final InputStream raw = VanillaBlockRegistry.class.getResourceAsStream(RESOURCE)) {
            if (raw == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            load(new GZIPInputStream(raw));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to load " + RESOURCE, exception);
        }
    }

    private static int ordinalOf(final List<BlockProperty> properties, final Map<String, String> values) {
        int ordinal = 0;
        for (final BlockProperty property : properties) {
            ordinal = ordinal * property.values().size() + property.indexOf(values.get(property.name()));
        }
        return ordinal;
    }

    @SuppressWarnings("PatternValidation")
    private void load(final InputStream input) throws IOException {
        try (final JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            reader.beginObject();
            while (reader.hasNext()) {
                register(readBlock(Key.key(reader.nextName()), reader));
            }
            reader.endObject();
        }
    }

    private BlockType readBlock(final Key key, final JsonReader reader) throws IOException {
        List<BlockProperty> properties = List.of();
        List<ParsedState> states = List.of();

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "properties" -> properties = readProperties(reader);
                case "states" -> states = readStates(reader);
                default -> reader.skipValue(); // "definition" et champs futurs
            }
        }
        reader.endObject();

        final int[] stateIds = new int[states.size()];
        int defaultOrdinal = 0;
        for (final ParsedState state : states) {
            final int ordinal = ordinalOf(properties, state.values);
            stateIds[ordinal] = state.id;
            if (state.isDefault) {
                defaultOrdinal = ordinal;
            }
        }
        return BlockType.of(key, properties, stateIds, defaultOrdinal);
    }

    private List<BlockProperty> readProperties(final JsonReader reader) throws IOException {
        final List<BlockProperty> properties = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            final List<String> values = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()) {
                values.add(reader.nextString());
            }
            reader.endArray();
            properties.add(new BlockProperty(name, values));
        }
        reader.endObject();
        return properties;
    }

    private List<ParsedState> readStates(final JsonReader reader) throws IOException {
        final List<ParsedState> states = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            int id = -1;
            boolean isDefault = false;
            Map<String, String> values = Collections.emptyMap();
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "id" -> id = reader.nextInt();
                    case "default" -> isDefault = reader.nextBoolean();
                    case "properties" -> {
                        values = new LinkedHashMap<>();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            values.put(reader.nextName(), reader.nextString());
                        }
                        reader.endObject();
                    }
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            states.add(new ParsedState(id, isDefault, values));
        }
        reader.endArray();
        return states;
    }

    @Override
    public Optional<BlockType> type(final Key key) {
        return Optional.ofNullable(types.get(key));
    }

    @Override
    public @Nullable BlockData fromNetworkId(final int networkId) {
        return byNetworkId.get(networkId);
    }

    @Override
    public void register(final BlockType type) {
        types.put(type.key(), type);
        for (int ordinal = 0; ordinal < type.stateCount(); ordinal++) {
            final BlockData data = type.stateAt(ordinal);
            byNetworkId.putIfAbsent(data.networkId(), data);
        }
    }

    @Override
    public Collection<BlockType> types() {
        return Collections.unmodifiableCollection(types.values());
    }

    private record ParsedState(int id, boolean isDefault, Map<String, String> values) {
    }
}
