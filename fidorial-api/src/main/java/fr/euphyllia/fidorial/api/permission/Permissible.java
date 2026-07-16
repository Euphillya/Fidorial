package fr.euphyllia.fidorial.api.permission;

import fr.euphyllia.fidorial.api.plugin.Plugin;

import java.util.Set;

public interface Permissible extends ServerOperator {

    boolean isPermissionSet(String name);

    boolean isPermissionSet(Permission perm);

    boolean hasPermission(String name);

    boolean hasPermission(Permission perm);

    PermissionAttachment addAttachment(Plugin plugin);

    PermissionAttachment addAttachment(Plugin plugin, String name, boolean value);

    void removeAttachment(PermissionAttachment attachment);

    void recalculatePermissions();

    Set<PermissionAttachmentInfo> getEffectivePermissions();

}
