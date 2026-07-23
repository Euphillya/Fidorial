package fr.euphyllia.fidorial.server.permission;

import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionDefault;
import fr.fidorial.plugin.PluginManager;

public class DefaultPermissions {

    public static final String ROOT = "fidorial";
    public static final String COMMAND_ROOT = ROOT + ".command";

    private DefaultPermissions() {
    }

    public static void registerCorePermissions(final PluginManager manager) {
        final Permission root = register(
                manager,
                new Permission(ROOT, "Provides access to all Fidorial features.", PermissionDefault.FALSE));
        final Permission commands = register(
                manager,
                new Permission(
                        COMMAND_ROOT, "Provides access to all Fidorial commands.", PermissionDefault.FALSE));
        commands.addParent(root, true);

        child(manager, commands, "tps", "Allows you to view the TPS", PermissionDefault.OP);
        child(manager, commands, "weather", "Allows you to change the weather.", PermissionDefault.OP);
        child(manager, commands, "time", "Allows you to change the time of a world.", PermissionDefault.OP);
        child(manager, commands, "gamemode", "Allows you to change the game mode.", PermissionDefault.OP);
        child(manager, commands, "op", "Allows promotion to operator status", PermissionDefault.OP);
        child(manager, commands, "deop", "Allows an operator to be removed.", PermissionDefault.OP);
        child(manager, commands, "stop", "Allows you to stop the server.", PermissionDefault.OP);
        final Permission selector = register(
                manager,
                new Permission(
                        "minecraft.command.selector",
                        "Allows the use of entity selectors (@a, @p, @e, @s)",
                        PermissionDefault.OP));

        root.recalculatePermissibles();
        commands.recalculatePermissibles();
        selector.recalculatePermissibles();
    }

    private static void child(
            final PluginManager manager,
            final Permission parent,
            final String name,
            final String description,
            final PermissionDefault def
    ) {
        final Permission perm = register(manager, new Permission(COMMAND_ROOT + "." + name, description, def));
        perm.addParent(parent, true);
    }

    private static Permission register(final PluginManager manager, final Permission perm) {
        final Permission existing = manager.getPermission(perm.getName()).orElse(null);
        if (existing != null) {
            return existing;
        }
        manager.addPermission(perm);
        return perm;
    }
}
