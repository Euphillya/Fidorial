package fr.euphyllia.fidorial.api.plugin;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.event.EventBus;
import fr.euphyllia.fidorial.api.service.ServiceRegistry;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface PluginContext {

    PluginMeta meta();

    Server server();

    EventBus events();

    ServiceRegistry services();

    Logger logger();

    Path dataFolder();
}
