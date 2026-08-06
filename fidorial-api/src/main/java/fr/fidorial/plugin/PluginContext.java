package fr.fidorial.plugin;

import fr.fidorial.Server;
import fr.fidorial.event.EventBus;
import fr.fidorial.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Path;

public interface PluginContext {

    PluginMeta meta();

    Server server();

    EventBus events();

    ServiceRegistry services();

    ComponentLogger logger();

    Path dataFolder();

    /**
     * Opens a resource bundled inside this plugin's jar, relative to the jar root.
     *
     * @apiNote The caller owns the returned stream and is responsible for closing it, typically via try-with-resources
     * @param path path to the resource, relative to the jar root
     * @return an input stream for the resource, or {@code null} if no entry exists at that path
     * @since 0.1.0
     */
    @Nullable InputStream resource(String path);
}
