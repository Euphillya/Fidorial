package fr.euphyllia.fidorial.server.plugin.meta;

import com.google.common.base.Preconditions;
import fr.fidorial.plugin.PluginMeta;
import net.kyori.adventure.key.KeyPattern;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class FidorialPluginMeta implements PluginMeta {
    private final @KeyPattern.Namespace String id;
    private final String name;
    private final Class<?> main;
    private final String version;
    private final String apiVersion;
    private final @Nullable String description;
    private final @Nullable String license;
    private final @Nullable URI url;
    private final Set<String> providedPlugins;
    private final Set<Author> authors;
    private final Set<Dependency> dependencies;
    private final Set<PermissionEntry> permissions;

    private FidorialPluginMeta(@Subst("regex") Builder builder) {
        Preconditions.checkArgument(builder.id != null, "'id' must be defined");
        Preconditions.checkArgument(builder.main != null, "'main' must be defined");
        Preconditions.checkArgument(builder.version != null, "'version' must be defined");
        Preconditions.checkArgument(
                builder.id.matches(KeyPattern.NAMESPACE_PATTERN),
                "'id' must match namespace pattern (%s)",
                KeyPattern.NAMESPACE_PATTERN
        );
        this.id = builder.id;
        this.name = builder.name != null ? builder.name : builder.id;
        this.main = builder.main;
        this.version = builder.version;
        this.apiVersion = builder.apiVersion != null ? builder.apiVersion : "*";
        this.description = builder.description;
        this.license = builder.license;
        this.url = builder.url;
        this.providedPlugins = Set.copyOf(builder.providedPlugins);
        this.authors = Set.copyOf(builder.authors);
        this.dependencies = Set.copyOf(builder.dependencies);
        this.permissions = Set.copyOf(builder.permissions);
    }

    @Override
    public @KeyPattern.Namespace String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public Class<?> main() {
        return main;
    }

    @Override
    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    @Override
    public Optional<String> license() {
        return Optional.ofNullable(license);
    }

    @Override
    public Optional<URI> url() {
        return Optional.ofNullable(url);
    }

    @Override
    public String apiVersion() {
        return apiVersion;
    }

    @Override
    public @Unmodifiable Set<Author> authors() {
        return authors;
    }

    @Override
    public @Unmodifiable Set<String> providedPlugins() {
        return providedPlugins;
    }

    @Override
    public @Unmodifiable Set<Dependency> dependencies() {
        return dependencies;
    }

    @Override
    public @Unmodifiable Set<PermissionEntry> permissions() {
        return permissions;
    }

    public static final class Builder {
        private @Nullable String id;
        private @Nullable String license;
        private @Nullable String name;
        private @Nullable String version;
        private @Nullable String description;
        private @Nullable URI url;
        private @Nullable Class<?> main;
        private @Nullable String apiVersion;
        private final Set<String> providedPlugins = new HashSet<>();
        private final Set<Author> authors = new HashSet<>();
        private final Set<Dependency> dependencies = new HashSet<>();
        private final Set<PermissionEntry> permissions = new HashSet<>();

        public Builder id(@Nullable String id) {
            this.id = id;
            return this;
        }

        public Builder description(@Nullable String description) {
            this.description = description;
            return this;
        }

        public Builder license(@Nullable String license) {
            this.license = license;
            return this;
        }

        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        public Builder version(@Nullable String version) {
            this.version = version;
            return this;
        }

        public Builder mainClass(@Nullable Class<?> main) {
            this.main = main;
            return this;
        }

        public Builder url(@Nullable URI url) {
            this.url = url;
            return this;
        }

        public Builder apiVersion(@Nullable String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public Builder providedPlugins(Set<String> providedPlugins) {
            this.providedPlugins.addAll(providedPlugins);
            return this;
        }

        public Builder dependencies(Set<Dependency> dependencies) {
            this.dependencies.addAll(dependencies);
            return this;
        }

        public Builder authors(Set<Author> authors) {
            this.authors.addAll(authors);
            return this;
        }

        public Builder permissions(Set<PermissionEntry> permissions) {
            this.permissions.addAll(permissions);
            return this;
        }

        public FidorialPluginMeta build() {
            return new FidorialPluginMeta(this);
        }
    }
}
