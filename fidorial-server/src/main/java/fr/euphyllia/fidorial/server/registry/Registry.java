package fr.euphyllia.fidorial.server.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record Registry(String name, List<String> entries, Map<String, List<String>> tags) {

    public Registry {
        entries = List.copyOf(entries);
        tags = Collections.unmodifiableMap(new LinkedHashMap<>(tags));
    }

    public static Registry of(String name, List<String> entries) {
        return new Registry(name, entries, Map.of());
    }

    public int networkId(String entry) {
        return entries.indexOf(entry);
    }

    public boolean contains(String entry) {
        return entries.contains(entry);
    }

    public boolean hasTags() {
        return !tags.isEmpty();
    }
}
