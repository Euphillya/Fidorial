package fr.euphyllia.fidorial.server.command.brigadier.packet.util;

import fr.fidorial.command.CommandSender;
import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionAttachment;
import fr.fidorial.permission.PermissionAttachmentInfo;
import fr.fidorial.plugin.Plugin;

import java.util.Set;

public final class PermissionlessCommandSender implements CommandSender {

    static final PermissionlessCommandSender INSTANCE = new PermissionlessCommandSender();

    private PermissionlessCommandSender() {
    }

    @Override
    public boolean isPermissionSet(String name) {
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return false;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException("Cannot add permissions to PermissionlessSender");
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        throw new UnsupportedOperationException("Cannot add permissions to PermissionlessSender");
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        // no-op
    }

    @Override
    public void recalculatePermissions() {
        // no-op
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return Set.of();
    }

    @Override
    public String name() {
        return "Permissionless";
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot op PermissionlessSender");
    }
}
