package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.permission.*;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.translation.TranslationStore;
import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Locale;
import java.util.Set;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class ConsoleSender implements CommandSender, PermissibleBaseHolder {

    public static final ConsoleSender INSTANCE = new ConsoleSender();
    private static final ComponentLogger LOGGER = getLogger("Console");
    private static final ServerOperator CONSOLE_OP = new ServerOperator() {
        @Override
        public boolean isOp() {
            return true;
        }

        @Override
        public void setOp(boolean value) {
            throw new UnsupportedOperationException("Impossible de changer le statut op de la console");
        }
    };
    private Locale locale = TranslationStore.defaultLocale();
    private volatile PermissibleBase perm;

    private ConsoleSender() {
    }

    private Permissible perm() {
        PermissibleBase local = perm;
        if (local == null) {
            synchronized (this) {
                if (perm == null) {
                    FidorialServer server = FidorialServer.getInstance();
                    if (server == null || server.plugins() == null) {
                        return null;
                    }
                    perm = new PermissibleBase(CONSOLE_OP, this, server.plugins());
                }
                local = perm;
            }
        }
        return local;
    }

    @Override
    public String name() {
        return "Console";
    }

    @Override
    public void setLocale(final String language) {
        this.locale = Locale.forLanguageTag(language);
    }

    @Override
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale locale() {
        return this.locale;
    }

    // to revisit once we switch to a terminal lib like JNI with TerminalConsoleAppender for colors
    @Override
    public void sendMessage(final Component message) {
        LOGGER.info(TranslationStore.render(message, locale()));
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public PermissibleBase permissionBase() {
        perm();
        return perm;
    }

    private PermissionService service() {
        FidorialServer server = FidorialServer.getInstance();
        if (server == null || server.services() == null) {
            return null;
        }
        return server.services().find(PermissionService.class).orElse(null);
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {
        CONSOLE_OP.setOp(value);
    }

    @Override
    public boolean isPermissionSet(String name) {
        PermissionService service = service();
        if (service != null) {
            return service.isPermissionSet(this, name);
        }
        Permissible p = perm();
        return p != null && p.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        PermissionService service = service();
        if (service != null) {
            return service.isPermissionSet(this, permission);
        }
        Permissible p = perm();
        return p != null && p.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String name) {
        PermissionService service = service();
        if (service != null) {
            return service.hasPermission(this, name);
        }
        Permissible p = perm();
        return p == null || p.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        PermissionService service = service();
        if (service != null) {
            return service.hasPermission(this, permission);
        }
        Permissible p = perm();
        return p == null || p.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return require().addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return require().addAttachment(plugin, name, value);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        require().removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        PermissionService service = service();
        if (service != null) {
            service.recalculate(this);
            return;
        }
        Permissible p = perm();
        if (p != null) {
            p.recalculatePermissions();
        }
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        PermissionService service = service();
        if (service != null) {
            return service.effectivePermissions(this);
        }
        Permissible p = perm();
        return p == null ? Set.of() : p.getEffectivePermissions();
    }

    private Permissible require() {
        Permissible p = perm();
        if (p == null) {
            throw new IllegalStateException("Serveur non demarre : permissions indisponibles");
        }
        return p;
    }
}
