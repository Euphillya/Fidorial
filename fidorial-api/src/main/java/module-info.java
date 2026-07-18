module fr.euphyllia.fidorial {
    exports fr.euphyllia.fidorial.api.attribute;
    exports fr.euphyllia.fidorial.api.command;
    exports fr.euphyllia.fidorial.api.entity.ai;
    exports fr.euphyllia.fidorial.api.entity;
    exports fr.euphyllia.fidorial.api.event.player;
    exports fr.euphyllia.fidorial.api.event.server;
    exports fr.euphyllia.fidorial.api.event;
    exports fr.euphyllia.fidorial.api.inventory;
    exports fr.euphyllia.fidorial.api.permission;
    exports fr.euphyllia.fidorial.api.plugin;
    exports fr.euphyllia.fidorial.api.registry.data;
    exports fr.euphyllia.fidorial.api.registry.keys;
    exports fr.euphyllia.fidorial.api.registry;
    exports fr.euphyllia.fidorial.api.scheduler;
    exports fr.euphyllia.fidorial.api.service;
    exports fr.euphyllia.fidorial.api.storage.player;
    exports fr.euphyllia.fidorial.api.translation;
    exports fr.euphyllia.fidorial.api.world.block.data.type;
    exports fr.euphyllia.fidorial.api.world.block.data;
    exports fr.euphyllia.fidorial.api.world.block;
    exports fr.euphyllia.fidorial.api.world.fluid;
    exports fr.euphyllia.fidorial.api.world.generation;
    exports fr.euphyllia.fidorial.api.world.weather;
    exports fr.euphyllia.fidorial.api.world;
    exports fr.euphyllia.fidorial.api;

    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.text.logger.slf4j;

    requires static org.jetbrains.annotations;
}