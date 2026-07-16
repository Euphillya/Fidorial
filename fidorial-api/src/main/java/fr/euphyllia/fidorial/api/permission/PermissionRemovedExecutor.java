package fr.euphyllia.fidorial.api.permission;

@FunctionalInterface
public interface PermissionRemovedExecutor {

    void attachmentRemoved(PermissionAttachment attachment);
}
