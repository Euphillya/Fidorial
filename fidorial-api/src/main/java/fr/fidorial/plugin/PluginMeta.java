package fr.fidorial.plugin;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public record PluginMeta(
        String id,
        String name,
        String version,
        String main,
        List<String> authors,
        List<String> depends,
        Map<String, Map<String, Object>> permissions,
        @Nullable String defaultPermission
) {

    public PluginMeta {
        authors = List.copyOf(authors);
        depends = List.copyOf(depends);
        permissions = Map.copyOf(permissions);
    }

    public PluginMeta(String id, String name, String version, String main, List<String> authors, List<String> depends) {
        this(id, name, version, main, authors, depends, Map.of(), null);
    }
}
