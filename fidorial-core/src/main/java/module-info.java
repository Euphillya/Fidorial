import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.core {
    exports fr.euphyllia.fidorial.server.config to fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.context to fr.fidorial.entity, fr.fidorial.protocol, fr.fidorial.registry, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.events to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.moderation to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.permission to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.service to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.util to fr.fidorial.world;

    requires transitive fr.fidorial;
    requires it.unimi.dsi.fastutil;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
