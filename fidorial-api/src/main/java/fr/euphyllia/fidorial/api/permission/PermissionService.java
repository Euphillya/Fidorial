package fr.euphyllia.fidorial.api.permission;

import java.util.Set;

public interface PermissionService {

    String name();

    boolean hasPermission(Permissible permissible, String permission);

    default boolean hasPermission(Permissible permissible, Permission permission) {
        return hasPermission(permissible, permission.getName());
    }

    boolean isPermissionSet(Permissible permissible, String permission);

    default boolean isPermissionSet(Permissible permissible, Permission permission) {
        return isPermissionSet(permissible, permission.getName());
    }

    Set<PermissionAttachmentInfo> effectivePermissions(Permissible permissible);

    default void recalculate(Permissible permissible) {
    }
}
