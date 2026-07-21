package fr.euphyllia.fidorial.server.permission;

import fr.fidorial.Server;
import fr.fidorial.permission.Permissible;
import fr.fidorial.permission.PermissibleBase;
import fr.fidorial.permission.PermissibleBaseHolder;
import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionAttachmentInfo;
import fr.fidorial.permission.PermissionDefault;
import fr.fidorial.permission.PermissionService;
import fr.fidorial.plugin.PluginManager;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public final class FidorialPermissionService implements PermissionService {

    private final Server server;

    public FidorialPermissionService(Server server) {
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    public String name() {
        return "fidorial";
    }

    @Override
    public boolean hasPermission(Permissible permissible, String permission) {
        PermissibleBase base = base(permissible);
        if (base != null) {
            return base.hasPermission(permission);
        }
        return defaultValue(permission, permissible.isOp());
    }

    @Override
    public boolean isPermissionSet(Permissible permissible, String permission) {
        PermissibleBase base = base(permissible);
        return base != null && base.isPermissionSet(permission);
    }

    @Override
    public Set<PermissionAttachmentInfo> effectivePermissions(Permissible permissible) {
        PermissibleBase base = base(permissible);
        return base == null ? Set.of() : base.getEffectivePermissions();
    }

    @Override
    public void recalculate(Permissible permissible) {
        PermissibleBase base = base(permissible);
        if (base != null) {
            base.recalculatePermissions();
        }
    }

    private @Nullable PermissibleBase base(Permissible permissible) {
        return permissible instanceof PermissibleBaseHolder holder ? holder.permissionBase() : null;
    }

    private boolean defaultValue(String permission, boolean op) {
        PluginManager plugins = server.plugins();
        Permission perm = plugins.getPermission(permission.toLowerCase(Locale.ROOT));
        PermissionDefault def = perm == null ? Permission.DEFAULT_PERMISSION : perm.getDefault();
        return def.getValue(op);
    }
}
