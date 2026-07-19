package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.fidorial.command.CommandSender;
import fr.fidorial.permission.PermissibleBase;
import fr.fidorial.permission.PermissibleBaseHolder;
import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionAttachment;
import fr.fidorial.permission.PermissionAttachmentInfo;
import fr.fidorial.permission.PermissionService;
import fr.fidorial.permission.ServerOperator;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.translation.TranslationStore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Set;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class ConsoleSender implements CommandSender, PermissibleBaseHolder {
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
    private Locale locale = Locale.US;
    private final PermissibleBase perm;

    public ConsoleSender(FidorialServer server) {
        this.perm = new PermissibleBase(CONSOLE_OP, this, server.plugins());
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
        return perm;
    }

    private @Nullable PermissionService service() {
        FidorialServer server = FidorialServer.getInstance();
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
        return perm.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        PermissionService service = service();
        if (service != null) {
            return service.isPermissionSet(this, permission);
        }
        return perm.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String name) {
        PermissionService service = service();
        if (service != null) {
            return service.hasPermission(this, name);
        }
        return perm.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        PermissionService service = service();
        if (service != null) {
            return service.hasPermission(this, permission);
        }
        return perm.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return perm.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return perm.addAttachment(plugin, name, value);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        perm.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        PermissionService service = service();
        if (service != null) {
            service.recalculate(this);
            return;
        }
        perm.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        PermissionService service = service();
        if (service != null) {
            return service.effectivePermissions(this);
        }
        return perm.getEffectivePermissions();
    }
}
