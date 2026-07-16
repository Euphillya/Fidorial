package fr.euphyllia.fidorial.api.permission;

import java.util.Objects;

public class PermissionAttachmentInfo {

    private final Permissible permissible;
    private final String permission;
    private final PermissionAttachment attachment;
    private final boolean value;

    public PermissionAttachmentInfo(Permissible permissible, String permission,
                                    PermissionAttachment attachment, boolean value) {
        this.permissible = Objects.requireNonNull(permissible, "permissible");
        this.permission = Objects.requireNonNull(permission, "permission");
        this.attachment = attachment;
        this.value = value;
    }

    public Permissible getPermissible() {
        return permissible;
    }

    public String getPermission() {
        return permission;
    }

    public PermissionAttachment getAttachment() {
        return attachment;
    }

    public boolean getValue() {
        return value;
    }
}
