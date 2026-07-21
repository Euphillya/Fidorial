import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial {
    exports fr.fidorial.attribute;
    exports fr.fidorial.command;
    exports fr.fidorial.entity.ai;
    exports fr.fidorial.entity;
    exports fr.fidorial.event.player;
    exports fr.fidorial.event.server;
    exports fr.fidorial.event;
    exports fr.fidorial.inventory;
    exports fr.fidorial.permission;
    exports fr.fidorial.plugin;
    exports fr.fidorial.registry.data;
    exports fr.fidorial.registry.keys;
    exports fr.fidorial.registry;
    exports fr.fidorial.scheduler;
    exports fr.fidorial.service;
    exports fr.fidorial.storage.player;
    exports fr.fidorial.translation;
    exports fr.fidorial.world.block.data.type;
    exports fr.fidorial.world.block.data;
    exports fr.fidorial.world.block;
    exports fr.fidorial.world.fluid;
    exports fr.fidorial.world.generation;
    exports fr.fidorial.world.weather;
    exports fr.fidorial.world;
    exports fr.fidorial;
    exports fr.fidorial.sound;

    requires com.google.common;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}