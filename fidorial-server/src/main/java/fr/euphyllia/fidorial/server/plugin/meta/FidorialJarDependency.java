package fr.euphyllia.fidorial.server.plugin.meta;

import fr.fidorial.plugin.PluginMeta;

import java.nio.file.Path;

public record FidorialJarDependency(Path file) implements PluginMeta.JarDependency {
}
