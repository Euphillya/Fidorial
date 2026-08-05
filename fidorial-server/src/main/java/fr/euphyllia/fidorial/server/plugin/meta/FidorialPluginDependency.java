package fr.euphyllia.fidorial.server.plugin.meta;

import fr.fidorial.plugin.PluginMeta;
import net.kyori.adventure.key.KeyPattern;

public record FidorialPluginDependency(
        @KeyPattern.Namespace String id,
        String versionRange,
        boolean required,
        boolean joinClasspath,
        RelativeLoadOrder load
) implements PluginMeta.PluginDependency {
}
