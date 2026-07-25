package fr.fidorial.plugin;

import fr.fidorial.permission.PermissionDefinition;
import fr.fidorial.permission.PermissionNode;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.util.TriState;
import org.eclipse.aether.graph.Exclusion;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Describes a plugin as declared by its {@code plugin-meta.json}.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface PluginMeta {
    /**
     * Returns the stable plugin identifier.
     * <p>
     * The identifier is used for dependency resolution and plugin lookups.
     * It must match the namespace pattern: lowercase, alphanumerics, underscores, hyphens, and periods.
     *
     * @return the plugin identifier
     * @see KeyPattern#NAMESPACE_PATTERN
     */
    @KeyPattern.Namespace
    @Contract(pure = true)
    String id();

    /**
     * Returns the human-readable plugin name.
     *
     * @return the display name
     */
    @Contract(pure = true)
    default String name() {
        return id();
    }

    /**
     * Returns the plugin version.
     *
     * @return the plugin version
     */
    @Contract(pure = true)
    String version();

    /**
     * Returns the plugin entry point class.
     *
     * @return the plugin main class
     */
    @Contract(pure = true)
    Class<?> main();

    /**
     * Returns the plugin description, if declared.
     *
     * @return the description, or empty
     */
    default Optional<String> description() {
        return Optional.empty();
    }

    /**
     * Returns the plugin license, if declared.
     *
     * @return the license identifier or name, or empty
     */
    @Contract(pure = true)
    default Optional<String> license() {
        return Optional.empty();
    }

    /**
     * Returns the plugin website or project URL, if declared.
     *
     * @return the plugin URL, or empty
     */
    @Contract(pure = true)
    default Optional<URI> url() {
        return Optional.empty();
    }

    /**
     * Returns the compatible Fidorial API version range.
     * <p>
     * Version ranges may use npm-style shorthand.
     * For example, {@code ~1.2.3} is equivalent to {@code >=1.2.3 <1.3.0},
     * and {@code ^1.2.3} is equivalent to {@code >=1.2.3 <2.0.0}.
     *
     * @return the compatible API version range
     */
    @Contract(pure = true)
    default String apiVersion() {
        return "*";
    }

    /**
     * Returns the declared plugin authors.
     *
     * @return the plugin authors
     */
    @Unmodifiable
    @Contract(pure = true)
    default Set<Author> authors() {
        return Set.of();
    }

    /**
     * Returns plugin identifiers provided by this plugin.
     * <p>
     * Provided plugins can be used to satisfy dependencies on another plugin identifier, such as
     * when this plugin is a compatible replacement, aggregate, or fork.
     *
     * @return provided plugin identifiers
     */
    @Unmodifiable
    @Contract(pure = true)
    default Set<String> providedPlugins() {
        return Set.of();
    }

    /**
     * Returns dependencies required or consumed by this plugin.
     *
     * @return plugin dependencies
     */
    @Unmodifiable
    @Contract(pure = true)
    default Set<Dependency> dependencies() {
        return Set.of();
    }

    /**
     * Returns permissions declared by this plugin.
     *
     * @return declared permissions
     */
    @Unmodifiable
    @Contract(pure = true)
    default Set<PermissionEntry> permissions() {
        return Set.of();
    }

    /**
     * Describes a plugin author.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface Author {
        /**
         * Returns the author's name.
         *
         * @return the author name
         */
        String name();

        /**
         * Returns the author's website, if declared.
         *
         * @return the website, or empty
         */
        default Optional<URI> website() {
            return Optional.empty();
        }

        /**
         * Returns declared contact methods for this author.
         * <p>
         * Keys identify the contact method, such as {@code email}, {@code discord}, or
         * {@code reddit}; values contain the corresponding address or handle.
         *
         * @return contact methods
         */
        @Unmodifiable
        @Contract(pure = true)
        default Map<String, String> contact() {
            return Map.of();
        }
    }

    /**
     * Describes a dependency on another Fidorial plugin.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface PluginDependency extends Dependency {
        /**
         * Returns the required plugin identifier.
         *
         * @return the plugin identifier
         */
        @KeyPattern.Namespace
        @Contract(pure = true)
        String id();

        /**
         * Returns the accepted version range for the dependency.
         * <p>
         * Version ranges may use npm-style shorthand.
         * For example, {@code ~1.2.3} is equivalent to {@code >=1.2.3 <1.3.0},
         * and {@code ^1.2.3} is equivalent to {@code >=1.2.3 <2.0.0}.
         *
         * @return the accepted dependency version range
         */
        @Contract(pure = true)
        default String versionRange() {
            return "*";
        }

        /**
         * Tests whether this dependency is required for the plugin to load.
         *
         * @return {@code true} if the plugin requires this dependency
         */
        @Contract(pure = true)
        default boolean required() {
            return true;
        }

        /**
         * Whether the dependency should be joined to this plugin's classpath.
         *
         * @return {@code true} if the dependency should be joined to the classpath
         */
        @Contract(pure = true)
        default boolean joinClasspath() {
            return true;
        }

        /**
         * Returns the requested load order relative to the dependency.
         *
         * @return the relative load order
         */
        @Contract(pure = true)
        default RelativeLoadOrder load() {
            return RelativeLoadOrder.UNDEFINED;
        }

        /**
         * Relative load order for plugin dependencies.
         */
        enum RelativeLoadOrder {
            /**
             * Load this plugin before the dependency.
             */
            BEFORE,
            /**
             * Load this plugin after the dependency.
             */
            AFTER,
            /**
             * No relative load order is required.
             */
            UNDEFINED
        }
    }

    /**
     * Describes an artifact dependency resolved from remote Maven repositories.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface RemoteDependency extends Dependency {
        /**
         * Returns Maven repositories that can provide this dependency.
         * <p>
         * Multiple repositories may be declared to allow fallback mirrors.
         *
         * @return repository URIs
         */
        @Unmodifiable
        @Contract(pure = true)
        Set<URI> repositories();

        /**
         * Returns transitive dependencies excluded from this dependency.
         *
         * @return dependency exclusions
         */
        @Unmodifiable
        @Contract(pure = true)
        Set<Exclusion> excludes();

        /**
         * Returns the Maven group id.
         *
         * @return the group id
         */
        @Contract(pure = true)
        String groupId();

        /**
         * Returns the Maven artifact id.
         *
         * @return the artifact id
         */
        @Contract(pure = true)
        String artifactId();

        /**
         * Returns the accepted artifact version range.
         * <p>
         * Version ranges may use npm-style shorthand.
         * For example, {@code ~1.2.3} is equivalent to {@code >=1.2.3 <1.3.0},
         * and {@code ^1.2.3} is equivalent to {@code >=1.2.3 <2.0.0}.
         *
         * @return the accepted artifact version range
         */
        @Contract(pure = true)
        String versionRange();
    }

    /**
     * Describes a dependency bundled inside the plugin artifact.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface JarDependency extends Dependency {
        /**
         * Returns the path to the bundled dependency file.
         *
         * @return the dependency file path
         */
        @Contract(pure = true)
        Path file();
    }

    /**
     * Marker interface for plugin dependency declarations.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface Dependency {
    }

    /**
     * Describes a permission.
     *
     * @since 0.1.0
     */
    @ApiStatus.NonExtendable
    interface PermissionEntry {
        /**
         * Returns the permission node.
         *
         * @return the permission node
         */
        @Contract(pure = true)
        PermissionNode permission();

        /**
         * Returns the permission description.
         *
         * @return the description
         */
        @Contract(pure = true)
        String description();

        /**
         * Returns the default permission scope.
         *
         * @return the default scope
         */
        @Contract(pure = true)
        Scope scope();

        /**
         * Returns child permissions implied by this permission.
         *
         * @return child permissions
         */
        @Unmodifiable
        @Contract(pure = true)
        Set<PermissionEntry> children();

        /**
         * Converts this descriptor entry into a permission definition.
         *
         * @return the permission definition
         */
        @Contract(value = " -> new", pure = true)
        default PermissionDefinition definition() { // todo: make PermissionDefinition an interface and extend it?
            return new PermissionDefinition(permission(), description(), switch (scope()) {
                case TRUE -> TriState.TRUE;
                case NOT_SET -> TriState.NOT_SET;
                default -> TriState.FALSE;
            }, switch (scope()) {
                case TRUE, OP -> TriState.TRUE;
                case NOT_SET -> TriState.NOT_SET;
                case FALSE -> TriState.FALSE;
            });
        }

        /**
         * Default assignment scope for a declared permission.
         */
        enum Scope {
            /**
             * Grant the permission to operators by default.
             */
            OP,
            /**
             * Grant the permission to everyone by default.
             */
            TRUE,
            /**
             * Deny the permission to everyone by default.
             */
            FALSE,
            /**
             * Do not set an explicit default value.
             */
            NOT_SET
        }
    }
}
