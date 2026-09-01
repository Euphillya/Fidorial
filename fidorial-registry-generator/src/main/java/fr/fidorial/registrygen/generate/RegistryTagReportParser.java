package fr.fidorial.registrygen.generate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.fidorial.registrygen.model.RegistryTagDefinition;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Parses Mojang's vanilla tag files and resolves them into a flat
 * identifier &rarr; member-entries mapping per registry.
 *
 * @since 0.1.0
 */
public final class RegistryTagReportParser {

    private static final String TAG_REFERENCE_PREFIX = "#";
    private static final String JSON_EXTENSION = ".json";

    /**
     * Parses and resolves every tag file for a single registry.
     *
     * @param vanillaDataDirectory  root of the extracted vanilla data (the {@code data}
     *                              directory produced by the {@code --server} data
     *                              generator flag), or {@code null}/non-existent to skip
     *                              tag generation entirely for this registry
     * @param registryPath          the registry's path, e.g. {@code "item"} or {@code "worldgen/biome"}
     * @param knownEntryIdentifiers every valid namespaced identifier in this registry;
     *                              used to silently drop optional-and-missing tag
     *                              entries and to fail loudly on required-and-missing ones
     * @return resolved tags, sorted by identifier; empty when there is no tag directory
     * for this registry
     * @throws IOException if a tag file exists but cannot be read or parsed
     */
    public List<RegistryTagDefinition> parse(final Path vanillaDataDirectory,
                                             final String registryPath,
                                             final Set<String> knownEntryIdentifiers) throws IOException {

        Objects.requireNonNull(registryPath, "registryPath");
        Objects.requireNonNull(knownEntryIdentifiers, "knownEntryIdentifiers");

        if (vanillaDataDirectory == null || !Files.isDirectory(vanillaDataDirectory)) {
            return List.of();
        }

        final Path tagDirectory = vanillaDataDirectory.resolve("minecraft").resolve("tags").resolve(registryPath);

        if (!Files.isDirectory(tagDirectory)) {
            return List.of();
        }

        final Map<String, List<RawEntry>> rawTags = readRawTags(tagDirectory);

        final List<RegistryTagDefinition> resolved = new ArrayList<>();
        for (final String tagIdentifier : rawTags.keySet()) {
            final List<String> entries = resolve(tagIdentifier, rawTags, knownEntryIdentifiers, new LinkedHashSet<>());
            resolved.add(new RegistryTagDefinition(tagIdentifier, entries));
        }

        resolved.sort(Comparator.comparing(RegistryTagDefinition::identifier));
        return List.copyOf(resolved);
    }

    private Map<String, List<RawEntry>> readRawTags(final Path tagDirectory) throws IOException {

        final Map<String, List<RawEntry>> rawTags = new LinkedHashMap<>();

        try (final Stream<Path> walk = Files.walk(tagDirectory)) {

            final List<Path> files = walk
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(JSON_EXTENSION))
                    .sorted()
                    .toList();

            for (final Path file : files) {

                final String relative = tagDirectory.relativize(file).toString().replace('\\', '/');
                final String tagPath = relative.substring(0, relative.length() - JSON_EXTENSION.length());
                final String tagIdentifier = "minecraft:" + tagPath;

                rawTags.put(tagIdentifier, readEntries(file, tagIdentifier));
            }
        }

        return rawTags;
    }

    private List<RawEntry> readEntries(final Path file, final String tagIdentifier) throws IOException {

        final JsonElement root;
        try (final Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            root = JsonParser.parseReader(reader);
        } catch (final RuntimeException exception) {
            throw new IOException("Failed to parse tag file for '" + tagIdentifier + "'.", exception);
        }

        if (!root.isJsonObject()) {
            throw new IOException("Tag file for '" + tagIdentifier + "' must be a JSON object.");
        }

        final JsonElement valuesElement = root.getAsJsonObject().get("values");
        if (valuesElement == null || !valuesElement.isJsonArray()) {
            throw new IOException("Tag file for '" + tagIdentifier + "' does not contain a 'values' array.");
        }

        final List<RawEntry> entries = new ArrayList<>();
        for (final JsonElement element : valuesElement.getAsJsonArray()) {
            entries.add(readEntry(element, tagIdentifier));
        }
        return entries;
    }

    private RawEntry readEntry(final JsonElement element, final String tagIdentifier) throws IOException {

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return rawEntryOf(element.getAsString(), true);
        }

        if (element.isJsonObject()) {

            final JsonObject object = element.getAsJsonObject();
            final JsonElement idElement = object.get("id");

            if (idElement == null || !idElement.isJsonPrimitive() || !idElement.getAsJsonPrimitive().isString()) {
                throw new IOException("Tag file for '" + tagIdentifier + "' contains an entry without a string 'id'.");
            }

            final boolean required = !object.has("required") || object.get("required").getAsBoolean();

            return rawEntryOf(idElement.getAsString(), required);
        }

        throw new IOException("Tag file for '" + tagIdentifier + "' contains an unsupported entry.");
    }

    private static RawEntry rawEntryOf(final String value, final boolean required) {

        final boolean isTag = value.startsWith(TAG_REFERENCE_PREFIX);
        final String identifier = isTag ? value.substring(TAG_REFERENCE_PREFIX.length()) : value;

        return new RawEntry(normalize(identifier), isTag, required);
    }

    private static String normalize(final String identifier) {
        return identifier.indexOf(':') < 0 ? "minecraft:" + identifier : identifier;
    }

    private List<String> resolve(final String tagIdentifier,
                                 final Map<String, List<RawEntry>> rawTags,
                                 final Set<String> knownEntryIdentifiers,
                                 final Set<String> visiting) {

        if (!visiting.add(tagIdentifier)) {
            throw new IllegalStateException("Cyclic tag reference detected involving '" + tagIdentifier + "'.");
        }

        final LinkedHashSet<String> resolved = new LinkedHashSet<>();
        final List<RawEntry> rawEntries = rawTags.get(tagIdentifier);

        if (rawEntries != null) {
            for (final RawEntry entry : rawEntries) {

                if (entry.isTag()) {

                    if (!rawTags.containsKey(entry.identifier())) {
                        if (entry.required()) {
                            throw new IllegalStateException("Tag '" + tagIdentifier + "' references unknown tag '" + entry.identifier() + "'.");
                        }
                        continue;
                    }

                    resolved.addAll(resolve(entry.identifier(), rawTags, knownEntryIdentifiers, visiting));
                    continue;
                }

                if (knownEntryIdentifiers.contains(entry.identifier())) {
                    resolved.add(entry.identifier());
                } else if (entry.required()) {
                    throw new IllegalStateException("Tag '" + tagIdentifier + "' references unknown entry '" + entry.identifier() + "'.");
                }
                // optional and missing from this registry: silently skipped, matching vanilla semantics.
            }
        }

        visiting.remove(tagIdentifier);

        final List<String> sorted = new ArrayList<>(resolved);
        sorted.sort(Comparator.naturalOrder());
        return List.copyOf(sorted);
    }

    private record RawEntry(String identifier, boolean isTag, boolean required) {
    }
}
