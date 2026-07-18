package fr.euphyllia.fidorial.server.plugin;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.event.EventBus;
import fr.euphyllia.fidorial.api.plugin.PluginContext;
import fr.euphyllia.fidorial.api.plugin.PluginMeta;
import fr.euphyllia.fidorial.api.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

final class SimplePluginContext implements PluginContext {

    private final PluginMeta meta;
    private final Server server;
    private final EventBus events;
    private final ServiceRegistry services;
    private final Path dataFolder;
    private final ComponentLogger logger;

    SimplePluginContext(PluginMeta meta, Server server, EventBus events,
                        ServiceRegistry services, Path dataFolder) {
        this.meta = meta;
        this.server = server;
        this.events = events;
        this.services = services;
        this.dataFolder = dataFolder;
        this.logger = getLogger("plugin/" + meta.id());
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
    public EventBus events() {
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
        } catch (IOException e) {
            throw new UncheckedIOException("Dossier de donnees de " + meta.id() + " impossible a creer", e);
        }
        return dataFolder;
    }
}
