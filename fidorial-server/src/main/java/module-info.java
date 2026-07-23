import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.server {
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.google.common;
    requires com.google.gson;
    requires dev.faststats.config;
    requires dev.faststats;
    requires fr.fidorial.auth;
    requires fr.fidorial;
    requires com.mojang.brigadier;
    requires io.netty.buffer;
    requires io.netty.codec;
    requires io.netty.transport.unix.common;
    requires io.netty.transport;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.text.logger.slf4j;
    requires net.kyori.adventure.text.minimessage;
    requires net.kyori.adventure.text.serializer.ansi;
    requires net.kyori.adventure.text.serializer.gson;
    requires org.slf4j;
    requires org.jline.reader;
    requires org.jline.terminal;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
    requires com.google.errorprone.annotations;
    requires net.kyori.adventure.text.serializer.plain;
    requires io.netty.transport.classes.epoll;
    requires io.netty.transport.classes.kqueue;
    requires io.netty.transport.classes.io_uring;
    requires java.management;
}
