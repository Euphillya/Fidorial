package fr.fidorial.plugin;

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

    public PluginMeta {
        authors = authors == null ? List.of() : List.copyOf(authors);
        depends = depends == null ? List.of() : List.copyOf(depends);
        permissions = permissions == null ? Map.of() : Map.copyOf(permissions);
    }

    public PluginMeta(final String id, final String name, final String version, final String main, final List<String> authors, final List<String> depends) {
        this(id, name, version, main, authors, depends, Map.of());
    }

    public record PermissionEntry(String description, String regular, String operator) {

        public PermissionEntry {
            description = description == null ? "" : description;
            regular = regular == null ? "unset" : regular;
            operator = operator == null ? "allow" : operator;
        }
    }
}
