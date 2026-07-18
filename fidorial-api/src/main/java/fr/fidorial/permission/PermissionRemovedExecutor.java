package fr.fidorial.permission;

@FunctionalInterface
public interface PermissionRemovedExecutor {

    void attachmentRemoved(PermissionAttachment attachment);
}
