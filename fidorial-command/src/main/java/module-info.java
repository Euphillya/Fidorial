import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.command {
    exports fr.euphyllia.fidorial.server.command to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.command.brigadier to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.command.brigadier.argument.entity to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.command.brigadier.argument.nbt to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.command.brigadier.packet to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.command.defaults to fr.fidorial.server;

    requires fr.fidorial.core;
    requires fr.fidorial.entity;
    requires fr.fidorial.protocol;
    requires fr.fidorial.registry;
    requires fr.fidorial.world;
    requires com.google.common;
    requires com.google.gson;
    requires transitive fr.fidorial;
    requires it.unimi.dsi.fastutil;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;
    requires net.kyori.adventure.text.serializer.gson;
    requires com.mojang.brigadier;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
