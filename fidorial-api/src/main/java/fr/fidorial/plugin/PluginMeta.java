package fr.fidorial.plugin;

import net.kyori.adventure.util.TriState;

import java.util.List;
import java.util.Map;

public record PluginMeta(
        String id,
        String name,
        String version,
        String main,
        List<String> authors,
        List<String> depends,
        Map<String, PermissionEntry> permissions,
        List<String> repositories
) {

    public PluginMeta(final String id, final String name, final String version, final String main, final List<String> authors, final List<String> depends) {
        this(id, name, version, main, authors, depends, Map.of(), List.of());
    }

    public PluginMeta(final String id, final String name, final String version, final String main, final List<String> authors, final List<String> depends, final Map<String, PermissionEntry> permissions) {
        this(id, name, version, main, authors, depends, permissions, List.of());
    }

    @Override
    public List<String> repositories() {
        return repositories == null ? List.of() : repositories;
    }

    public record PermissionEntry(String description, TriState regular, TriState operator) {
    }
}
