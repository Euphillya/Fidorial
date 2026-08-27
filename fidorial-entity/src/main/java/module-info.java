import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.entity {
    exports fr.euphyllia.fidorial.server.combat to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.ai to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.mob to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.player to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.player.profile to fr.fidorial.command, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.player.storage to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.entity.storage to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.inventory to fr.fidorial.command, fr.fidorial.server;

    requires fr.fidorial.codecs;
    requires fr.fidorial.core;
    requires fr.fidorial.protocol;
    requires fr.fidorial.registry;
    requires fr.fidorial.storage;
    requires fr.fidorial.world;
    requires transitive fr.fidorial;
    requires fr.fidorial.auth;
    requires io.papermc.adventurex.nbt.dfu;
    requires it.unimi.dsi.fastutil;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
