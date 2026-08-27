import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.protocol {
    exports fr.euphyllia.fidorial.server.network to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.network.protocol to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.catalog to fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet to fr.fidorial.entity, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common to fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.login to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play to fr.fidorial.command, fr.fidorial.entity, fr.fidorial.server, fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.status to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.listener to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.handshake to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.login to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.play to fr.fidorial.entity, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.status to fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.network.nbt to fr.fidorial.server;

    requires fr.fidorial.codecs;
    requires fr.fidorial.core;
    requires fr.fidorial.registry;
    requires com.google.common;
    requires transitive fr.fidorial;
    requires fr.fidorial.auth;
    requires io.netty.buffer;
    requires io.netty.codec;
    requires io.netty.transport;
    requires io.netty.transport.classes.epoll;
    requires io.netty.transport.classes.kqueue;
    requires io.papermc.adventurex.nbt.dfu;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;
    requires net.kyori.adventure.text.serializer.gson;
    requires com.mojang.brigadier;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
