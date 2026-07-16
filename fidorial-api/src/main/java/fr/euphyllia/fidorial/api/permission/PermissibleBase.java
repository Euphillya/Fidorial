package fr.euphyllia.fidorial.api.permission;

import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PermissibleBase implements Permissible {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissibleBase.class);

    private final ServerOperator opable;
    private final Permissible parent;
    private final PluginManager pluginManager;
    private final List<PermissionAttachment> attachments = new LinkedList<>();
    private final Map<String, PermissionAttachmentInfo> permissions = new HashMap<>();

    public PermissibleBase(ServerOperator opable, PluginManager pluginManager) {
        this(opable, null, pluginManager);
    }

    public PermissibleBase(ServerOperator opable, Permissible parent, PluginManager pluginManager) {
        this.opable = opable;
        this.parent = parent == null ? this : parent;
        this.pluginManager = Objects.requireNonNull(pluginManager, "pluginManager");
        recalculatePermissions();
    }

    @Override
    public boolean isOp() {
        return opable != null && opable.isOp();
    }

    @Override
    public void setOp(boolean value) {
        if (opable == null) {
            throw new UnsupportedOperationException("Impossible de changer le statut op de cet objet");
        }
        opable.setOp(value);
        recalculatePermissions();
    }

    @Override
    public synchronized boolean isPermissionSet(String name) {
        Objects.requireNonNull(name, "name");
        return permissions.containsKey(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        Objects.requireNonNull(perm, "perm");
        return isPermissionSet(perm.getName());
    }

    @Override
    public synchronized boolean hasPermission(String name) {
        Objects.requireNonNull(name, "name");
        String lname = name.toLowerCase(Locale.ROOT);
        PermissionAttachmentInfo info = permissions.get(lname);
        if (info != null) {
            return info.getValue();
        }
        Permission perm = pluginManager.getPermission(lname);
        PermissionDefault def = perm == null ? Permission.DEFAULT_PERMISSION : perm.getDefault();
        return def.getValue(isOp());
    }

    @Override
    public synchronized boolean hasPermission(Permission perm) {
        Objects.requireNonNull(perm, "perm");
        String lname = perm.getName().toLowerCase(Locale.ROOT);
        PermissionAttachmentInfo info = permissions.get(lname);
        if (info != null) {
            return info.getValue();
        }
        return perm.getDefault().getValue(isOp());
    }

    @Override
    public synchronized PermissionAttachment addAttachment(Plugin plugin) {
        checkPlugin(plugin);
        PermissionAttachment attachment = new PermissionAttachment(plugin, parent);
        attachments.add(attachment);
        recalculatePermissions();
        return attachment;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        Objects.requireNonNull(name, "name");
        PermissionAttachment attachment = addAttachment(plugin);
        attachment.setPermission(name, value);
        return attachment;
    }

    @Override
    public synchronized void removeAttachment(PermissionAttachment attachment) {
        Objects.requireNonNull(attachment, "attachment");
        if (!attachments.remove(attachment)) {
            throw new IllegalArgumentException("L'attachement n'appartient pas a ce permissible");
        }
        PermissionRemovedExecutor ex = attachment.getRemovalCallback();
        if (ex != null) {
            try {
                ex.attachmentRemoved(attachment);
            } catch (Throwable t) {
                LOGGER.error("Erreur dans le callback de retrait d'attachement", t);
            }
        }
        recalculatePermissions();
    }

    @Override
    public synchronized void recalculatePermissions() {
        clearPermissions();
        Set<Permission> defaults = pluginManager.getDefaultPermissions(isOp());
        pluginManager.subscribeToDefaultPerms(isOp(), parent);

        for (Permission perm : defaults) {
            String name = perm.getName().toLowerCase(Locale.ROOT);
            permissions.put(name, new PermissionAttachmentInfo(parent, name, null, true));
            pluginManager.subscribeToPermission(name, parent);
            calculateChildPermissions(perm.getChildren(), false, null);
        }

        for (PermissionAttachment attachment : attachments) {
            calculateChildPermissions(attachment.getPermissions(), false, attachment);
        }
    }

    public synchronized void clearPermissions() {
        for (String name : new HashSet<>(permissions.keySet())) {
            pluginManager.unsubscribeFromPermission(name, parent);
        }
        pluginManager.unsubscribeFromDefaultPerms(false, parent);
        pluginManager.unsubscribeFromDefaultPerms(true, parent);
        permissions.clear();
    }

    @Override
    public synchronized Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return new HashSet<>(permissions.values());
    }

    private void calculateChildPermissions(Map<String, Boolean> children, boolean invert,
                                           PermissionAttachment attachment) {
        for (Map.Entry<String, Boolean> entry : children.entrySet()) {
            String name = entry.getKey();
            String lname = name.toLowerCase(Locale.ROOT);
            boolean value = entry.getValue() ^ invert;

            permissions.put(lname, new PermissionAttachmentInfo(parent, lname, attachment, value));
            pluginManager.subscribeToPermission(lname, parent);

            Permission perm = pluginManager.getPermission(lname);
            if (perm != null) {
                calculateChildPermissions(perm.getChildren(), !value, attachment);
            }
        }
    }

    private void checkPlugin(Plugin plugin) {
        Objects.requireNonNull(plugin, "plugin");
    }
}
