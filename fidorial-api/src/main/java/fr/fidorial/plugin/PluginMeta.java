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
        Map<String, PermissionEntry> permissions
) {
    public PluginMeta(final String id, final String name, final String version, final String main, final List<String> authors, final List<String> depends) {
        this(id, name, version, main, authors, depends, Map.of());
    }

    public record PermissionEntry(String description, TriState regular, TriState operator) {
    }
}
