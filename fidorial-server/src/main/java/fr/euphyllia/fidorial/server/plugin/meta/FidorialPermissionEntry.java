package fr.euphyllia.fidorial.server.plugin.meta;

import fr.fidorial.permission.PermissionNode;
import fr.fidorial.plugin.PluginMeta;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public final class FidorialPermissionEntry implements PluginMeta.PermissionEntry {
    private final PermissionNode permission;
    private final @Nullable String description;
    private final Scope scope;
    private final Set<PluginMeta.PermissionEntry> children;

    public FidorialPermissionEntry(
            PermissionNode permission,
            @Nullable String description,
            Scope scope,
            Set<PluginMeta.PermissionEntry> children
    ) {
        this.permission = permission;
        this.description = description;
        this.scope = scope;
        this.children = Set.copyOf(children);
    }

    @Override
    public PermissionNode permission() {
        return permission;
    }

    @Override
    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    @Override
    public Scope scope() {
        return scope;
    }

    @Override
    public @Unmodifiable Set<PluginMeta.PermissionEntry> children() {
        return children;
    }
}
