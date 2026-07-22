package fr.euphyllia.fidorial.server.permission;

import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionDefault;
import fr.fidorial.plugin.PluginManager;

public class DefaultPermissions {

    public static final String ROOT = "fidorial";
    public static final String COMMAND_ROOT = ROOT + ".command";

    private DefaultPermissions() {
    }

    public static void registerCorePermissions(PluginManager manager) {
        Permission root = register(
                manager,
                new Permission(ROOT, "Donne acces a toutes les fonctionnalites de Fidorial", PermissionDefault.FALSE));
        Permission commands = register(
                manager,
                new Permission(
                        COMMAND_ROOT, "Donne acces a toutes les commandes de Fidorial", PermissionDefault.FALSE));
        commands.addParent(root, true);

        child(manager, commands, "tps", "Permet de consulter les TPS", PermissionDefault.OP);
        child(manager, commands, "weather", "Permet de changer la meteo", PermissionDefault.OP);
        child(manager, commands, "gamemode", "Permet de changer de mode de jeu", PermissionDefault.OP);
        child(manager, commands, "op", "Permet de promouvoir un operateur", PermissionDefault.OP);
        child(manager, commands, "deop", "Permet de retrograder un operateur", PermissionDefault.OP);
        child(manager, commands, "stop", "Idk what french says", PermissionDefault.OP);
        Permission selector = register(
                manager,
                new Permission(
                        "minecraft.command.selector",
                        "Permet d'utiliser les selecteurs d'entites (@a, @p, @e, @s)",
                        PermissionDefault.OP));

        root.recalculatePermissibles();
        commands.recalculatePermissibles();
        selector.recalculatePermissibles();
    }

    private static void child(
            PluginManager manager,
            Permission parent,
            String name,
            String description,
            PermissionDefault def
    ) {
        Permission perm = register(manager, new Permission(COMMAND_ROOT + "." + name, description, def));
        perm.addParent(parent, true);
    }

    private static Permission register(PluginManager manager, Permission perm) {
        Permission existing = manager.getPermission(perm.getName()).orElse(null);
        if (existing != null) {
            return existing;
        }
        manager.addPermission(perm);
        return perm;
    }
}
