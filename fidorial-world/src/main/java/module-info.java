import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.world {
    exports fr.euphyllia.fidorial.server.schedulers to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.block to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.block.blockentity to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.chunk to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.entity to fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.fluid to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.light to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.time to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.world.weather to fr.fidorial.command, fr.fidorial.server;

    requires fr.fidorial.core;
    requires fr.fidorial.protocol;
    requires fr.fidorial.registry;
    requires fr.fidorial.storage;
    requires transitive fr.fidorial;
    requires io.netty.buffer;
    requires it.unimi.dsi.fastutil;
    requires java.management;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
