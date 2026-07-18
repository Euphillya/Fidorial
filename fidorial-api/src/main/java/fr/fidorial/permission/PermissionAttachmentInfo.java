package fr.fidorial.permission;

import java.util.Objects;

public record PermissionAttachmentInfo(Permissible permissible, String permission, PermissionAttachment attachment,
                                       boolean value) {

    public PermissionAttachmentInfo(Permissible permissible, String permission,
                                    PermissionAttachment attachment, boolean value) {
        this.permissible = Objects.requireNonNull(permissible, "permissible");
        this.permission = Objects.requireNonNull(permission, "permission");
        this.attachment = attachment;
        this.value = value;
    }
}
