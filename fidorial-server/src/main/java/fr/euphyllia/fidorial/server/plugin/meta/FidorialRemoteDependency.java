package fr.euphyllia.fidorial.server.plugin.meta;

import fr.fidorial.plugin.PluginMeta;
import org.eclipse.aether.graph.Exclusion;

import java.net.URI;
import java.util.Set;

public record FidorialRemoteDependency(
        Set<URI> repositories,
        Set<Exclusion> excludes,
        String groupId,
        String artifactId,
        String versionRange
) implements PluginMeta.RemoteDependency {
    public FidorialRemoteDependency {
        repositories = Set.copyOf(repositories);
        excludes = Set.copyOf(excludes);
    }
}
