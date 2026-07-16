package fr.euphyllia.fidorial.api.plugin;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record PluginMeta(String id,
                         String name,
                         String version,
                         String main,
                         List<String> authors,
                         List<String> depends,
                         Map<String, Map<String, Object>> permissions,
                         String defaultPermission) {

    public PluginMeta {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(main, "main");
        name = name == null ? id : name;
        version = version == null ? "0.0.0" : version;
        authors = authors == null ? List.of() : List.copyOf(authors);
        depends = depends == null ? List.of() : List.copyOf(depends);
        permissions = permissions == null ? Map.of() : Map.copyOf(permissions);
    }

    public PluginMeta(String id, String name, String version, String main,
                      List<String> authors, List<String> depends) {
        this(id, name, version, main, authors, depends, null, null);
    }
}
