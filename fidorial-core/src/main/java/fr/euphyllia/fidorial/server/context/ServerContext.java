package fr.euphyllia.fidorial.server.context;

import fr.euphyllia.fidorial.server.config.ServerConfig;
import fr.euphyllia.fidorial.server.moderation.CodeOfConductManager;
import fr.euphyllia.fidorial.server.permission.OperatorList;
import fr.fidorial.event.EventBus;
import fr.fidorial.moderation.BanManager;
import fr.fidorial.moderation.WhitelistManager;
import fr.fidorial.permission.PermissionRegistry;
import fr.fidorial.service.ServiceRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public interface ServerContext {

    String brandName();

    ServerConfig config();

    ComponentLogger logger();

    EventBus events();

    ServiceRegistry services();

    PermissionRegistry permissions();

    OperatorList operators();

    BanManager ban();

    WhitelistManager whitelist();

    CodeOfConductManager codeOfConduct();

    ComponentResolution components();

    Iterable<? extends Audience> audiences();

    void shutdown();

    static ServerContext get() {
        return ServerContextHolder.require(ServerContext.class);
    }

    static <T extends ServerContext> T get(final Class<T> type) {
        return ServerContextHolder.require(type);
    }

    static void install(final ServerContext context) {
        ServerContextHolder.install(context);
    }

    static void uninstall() {
        ServerContextHolder.uninstall();
    }
}
