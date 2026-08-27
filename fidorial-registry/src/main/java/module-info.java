import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.registry {
    exports fr.euphyllia.fidorial.server.registry to fr.fidorial.entity, fr.fidorial.protocol, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.registry.biome to fr.fidorial.protocol, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.registry.data to fr.fidorial.command, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.registry.dialog to fr.fidorial.protocol, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.registry.dimension to fr.fidorial.protocol, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.registry.entity to fr.fidorial.entity, fr.fidorial.protocol;

    requires fr.fidorial.codecs;
    requires fr.fidorial.core;
    requires com.google.gson;
    requires transitive fr.fidorial;
    requires it.unimi.dsi.fastutil;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
