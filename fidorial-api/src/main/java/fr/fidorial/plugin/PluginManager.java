package fr.fidorial.plugin;

import fr.fidorial.permission.Permissible;
import fr.fidorial.permission.Permission;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PluginManager {

    Collection<PluginMeta> loaded();

    Optional<Plugin> plugin(String id);

    boolean isEnabled(String id);

    Permission getPermission(String name);

    void addPermission(Permission perm);

    void removePermission(Permission perm);

    void removePermission(String name);

    Set<Permission> getDefaultPermissions(boolean op);

    void recalculatePermissionDefaults(Permission perm);

    void subscribeToPermission(String permission, Permissible permissible);

    void unsubscribeFromPermission(String permission, Permissible permissible);

    Set<Permissible> getPermissionSubscriptions(String permission);

    void subscribeToDefaultPerms(boolean op, Permissible permissible);

    void unsubscribeFromDefaultPerms(boolean op, Permissible permissible);

    Set<Permissible> getDefaultPermSubscriptions(boolean op);

    Set<Permission> getPermissions();
}
