package fr.euphyllia.fidorial.api.permission;

import fr.euphyllia.fidorial.api.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PermissionAttachment {

    private final Plugin plugin;
    private final Permissible permissible;
    private final Map<String, Boolean> permissions = new LinkedHashMap<>();
    private PermissionRemovedExecutor removed;

    public PermissionAttachment(Plugin plugin, Permissible permissible) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.permissible = Objects.requireNonNull(permissible, "permissible");
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Permissible getPermissible() {
        return permissible;
    }

    public void setRemovalCallback(PermissionRemovedExecutor ex) {
        this.removed = ex;
    }

    public PermissionRemovedExecutor getRemovalCallback() {
        return removed;
    }

    public Map<String, Boolean> getPermissions() {
        return new LinkedHashMap<>(permissions);
    }

    public void setPermission(String name, boolean value) {
        permissions.put(name.toLowerCase(Locale.ROOT), value);
        permissible.recalculatePermissions();
    }

    public void setPermission(Permission perm, boolean value) {
        setPermission(perm.getName(), value);
    }

    public void unsetPermission(String name) {
        permissions.remove(name.toLowerCase(Locale.ROOT));
        permissible.recalculatePermissions();
    }

    public void unsetPermission(Permission perm) {
        unsetPermission(perm.getName());
    }

    public boolean remove() {
        try {
            permissible.removeAttachment(this);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
