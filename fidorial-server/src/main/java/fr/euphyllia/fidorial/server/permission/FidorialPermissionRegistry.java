package fr.euphyllia.fidorial.server.permission;

import fr.fidorial.permission.PermissionDefinition;
import fr.fidorial.permission.PermissionNode;
import fr.fidorial.permission.PermissionRegistry;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default {@link PermissionRegistry}: a concurrent map of declarations plus a revision counter.
 */
public final class FidorialPermissionRegistry implements PermissionRegistry {

    private final Map<PermissionNode, PermissionDefinition> definitions = new ConcurrentHashMap<>();
    private final AtomicLong revision = new AtomicLong();

    @Override
    public Optional<PermissionDefinition> define(final PermissionDefinition definition) {
        Objects.requireNonNull(definition, "definition");
        final PermissionDefinition previous = definitions.put(definition.node(), definition);
        revision.incrementAndGet();
        return Optional.ofNullable(previous);
    }

    @Override
    public void defineAll(final Collection<PermissionDefinition> incoming) {
        Objects.requireNonNull(incoming, "definitions");
        if (incoming.isEmpty()) {
            return;
        }
        for (final PermissionDefinition definition : incoming) {
            definitions.put(definition.node(), definition);
        }
        revision.incrementAndGet();
    }

    @Override
    public boolean undefine(final PermissionNode node) {
        Objects.requireNonNull(node, "node");
        if (definitions.remove(node) == null) {
            return false;
        }
        revision.incrementAndGet();
        return true;
    }

    @Override
    public Optional<PermissionDefinition> definition(final PermissionNode node) {
        return Optional.ofNullable(definitions.get(node));
    }

    @Override
    public Collection<PermissionDefinition> definitions() {
        return List.copyOf(definitions.values());
    }

    @Override
    public long revision() {
        return revision.get();
    }

    /**
     * Removes every declaration whose node sits under {@code prefix}. Used when a plugin unloads to
     * drop the whole subtree it declared in one call.
     *
     * @param prefix the owning node, typically {@code "myplugin.*"}
     */
    public void undefineSubtree(final PermissionNode prefix) {
        final boolean changed = definitions.keySet().removeIf(prefix::covers);
        if (changed) {
            revision.incrementAndGet();
        }
    }
}
