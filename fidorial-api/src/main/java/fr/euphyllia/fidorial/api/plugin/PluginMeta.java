package fr.euphyllia.fidorial.api.plugin;

import java.util.List;
import java.util.Objects;

public record PluginMeta(String id,
                         String name,
                         String version,
                         String main,
                         List<String> authors,
                         List<String> depends) {

    public PluginMeta {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(main, "main");
        name = name == null ? id : name;
        version = version == null ? "0.0.0" : version;
        authors = authors == null ? List.of() : List.copyOf(authors);
        depends = depends == null ? List.of() : List.copyOf(depends);
    }
}
