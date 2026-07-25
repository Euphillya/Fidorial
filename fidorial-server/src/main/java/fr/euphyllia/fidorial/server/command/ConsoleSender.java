package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.ServerConfig;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.permission.PermissionResolver;
import fr.fidorial.permission.PermissionState;
import fr.fidorial.permission.PermissionStateHolder;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.Location;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class ConsoleSender implements CommandSender, PermissionStateHolder, CommandSource {

    public static final ComponentLogger LOGGER = ComponentLogger.logger("Console");

    private final PermissionState permissions;
    private Locale locale = Locale.US;

    public ConsoleSender(final FidorialServer server) {
        this.permissions = new PermissionState(
                this,
                server.permissions(),
                () -> server.services()
                        .find(PermissionResolver.class)
                        .map(List::of)
                        .orElseGet(List::of));
    }

    public void setLocale(final String language) {
        this.locale = Locale.forLanguageTag(language);
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public Locale locale() {
        return this.locale;
    }

    @Override
    public void sendMessage(final Component message) {
        LOGGER.info(TranslationStore.render(message, locale()));
    }

    @Override
    public String name() {
        return "Console";
    }

    @Override
    public PermissionState permissions() {
        return permissions;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public void setOperator(final boolean operator) {
        throw new UnsupportedOperationException("The console is always an operator");
    }

    @Override
    public Location location() {
        // provide the location as default spawn
        final ServerConfig config = FidorialServer.getInstance().config();
        final double x = config.spawnX();
        final double y = config.spawnY();
        final double z = config.spawnZ();
        return new Location(x, y, z, 0, 0);
    }

    @Override
    public CommandSender sender() {
        return this;
    }

    @Override
    public @Nullable Entity executor() {
        return null;
    }

    @Override
    public FidorialServer server() {
        return FidorialServer.getInstance();
    }
}
