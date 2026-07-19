package fr.euphyllia.fidorial.server.registry;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RegistryHolder {

    private static final RegistryHolder EMPTY = new RegistryHolder(Map.of());

    private final Map<String, Registry> registries;

    private RegistryHolder(Map<String, Registry> registries) {
        this.registries = registries;
    }

    public static RegistryHolder of(Map<String, Registry> registries) {
        return new RegistryHolder(Collections.unmodifiableMap(new LinkedHashMap<>(registries)));
    }

    public static RegistryHolder empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return registries.isEmpty();
    }

    public int size() {
        return registries.size();
    }

    public Collection<Registry> all() {
        return registries.values();
    }

    public @Nullable Registry get(String name) {
        return registries.get(name);
    }

    public int networkId(String registry, String entry) {
        Registry reg = registries.get(registry);
        return reg == null ? -1 : reg.networkId(entry);
    }

    public int tagCount() {
        return registries.values().stream().mapToInt(r -> r.tags().size()).sum();
    }
}
