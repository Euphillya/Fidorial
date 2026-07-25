package fr.fidorial.plugin;

import fr.fidorial.permission.PermissionDefinition;
import fr.fidorial.permission.PermissionNode;
import net.kyori.adventure.util.TriState;
import org.eclipse.aether.graph.Exclusion;

import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PluginMeta {
    String id();

    String name();

    String version();

    String main();

    Set<Author> authors();

    Set<String> providedPlugins();

    Set<Dependency> dependencies();

    Set<PermissionEntry> permissions();

    interface Author {
        String name();

        Optional<URI> website();

        // any way of contact; discord, reddit, email…
        Map<String, String> contact();
    }

    interface PluginDependency extends Dependency {
        String id();

        // input: ~1.2.3 equivalent: >=1.2.3 <1.3.0
        // input: ^1.2.3 equivalent: >=1.2.3 <2.0.0
        String versionRange();

        boolean joinClasspath();

        RelativeLoadOrder load();

        enum RelativeLoadOrder {
            BEFORE,
            AFTER,
            UNDEFINED
        }
    }

    interface RemoteDependency extends Dependency {
        // multiple, to allow for backup mirrors
        Set<String> repositories();

        // all dependencies to exclude from this dependency
        Set<Exclusion> excludes();

        String groupId();

        String artifactId();

        String versionRange();
    }

    // jar in jar dependency
    interface JarDependency extends Dependency {
        Path file();
    }

    interface Dependency {
    }

    interface PermissionEntry {
        String permission();

        String description();

        Scope scope();

        Set<PermissionEntry> children();

        default PermissionDefinition definition() { // todo: make PermissionDefinition an interface and extend it?
            return new PermissionDefinition(PermissionNode.of(permission()), description(), switch (scope()) {
                case TRUE -> TriState.TRUE;
                case NOT_SET -> TriState.NOT_SET;
                default -> TriState.FALSE;
            }, switch (scope()) {
                case TRUE, OP -> TriState.TRUE;
                case NOT_SET -> TriState.NOT_SET;
                case FALSE -> TriState.FALSE;
            });
        }

        enum Scope {
            OP,
            TRUE,
            FALSE,
            NOT_SET
        }
    }
}
