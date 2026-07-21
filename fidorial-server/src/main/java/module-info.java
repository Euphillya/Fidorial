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

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}