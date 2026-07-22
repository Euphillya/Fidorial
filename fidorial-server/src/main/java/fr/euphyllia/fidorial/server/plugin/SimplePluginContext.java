package fr.euphyllia.fidorial.server.plugin;

import fr.fidorial.Server;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginContext;
import fr.fidorial.plugin.PluginEventBus;
import fr.fidorial.plugin.PluginMeta;
import fr.fidorial.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class SimplePluginContext implements PluginContext {

    private final PluginMeta meta;
    private final Server server;
    private final PluginEventBus events;
    private final ServiceRegistry services;
    private final Path dataFolder;
    private final ComponentLogger logger;

    SimplePluginContext(
            final PluginMeta meta,
            final Server server,
            final Plugin plugin,
            final ServiceRegistry services,
            final Path dataFolder
    ) {
        this.meta = meta;
        this.server = server;
        this.events = new SimplePluginEventBus(server.events(), plugin);
        this.services = services;
        this.dataFolder = dataFolder;
        this.logger = ComponentLogger.logger("plugin/" + meta.id());
    }

    @Override
    public PluginMeta meta() {
        return meta;
    }

    @Override
    public Server server() {
        return server;
    }

    @Override
    public PluginEventBus events() {
        return events;
    }

    @Override
    public ServiceRegistry services() {
        return services;
    }

    @Override
    public ComponentLogger logger() {
        return logger;
    }

    @Override
    public Path dataFolder() {
        try {
            Files.createDirectories(dataFolder);
        } catch (final IOException e) {
            throw new UncheckedIOException("Dossier de donnees de " + meta.id() + " impossible a creer", e);
        }
        return dataFolder;
    }
}
