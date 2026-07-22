package fr.fidorial.plugin;

import fr.fidorial.Server;
import fr.fidorial.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.nio.file.Path;

public interface PluginContext {
    PluginMeta meta();

    Server server();

    PluginEventBus events();

    ServiceRegistry services();

    ComponentLogger logger();

    Path dataFolder();
}
