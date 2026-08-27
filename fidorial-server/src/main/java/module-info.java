import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.server {
    requires fr.fidorial.codecs;
    requires fr.fidorial.command;
    requires fr.fidorial.core;
    requires fr.fidorial.entity;
    requires fr.fidorial.protocol;
    requires fr.fidorial.registry;
    requires fr.fidorial.storage;
    requires fr.fidorial.world;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.google.common;
    requires com.google.gson;
    requires dev.faststats;
    requires transitive fr.fidorial;
    requires fr.fidorial.auth;
    requires io.github.classgraph;
    requires io.netty.buffer;
    requires io.papermc.adventurex.nbt.dfu;
    requires it.unimi.dsi.fastutil;
    requires java.logging;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.logger.slf4j;
    requires net.kyori.adventure.text.minimessage;
    requires net.kyori.adventure.text.serializer.ansi;
    requires net.kyori.adventure.text.serializer.gson;
    requires net.kyori.adventure.text.serializer.plain;
    requires org.jline.reader;
    requires org.jline.terminal;
    requires org.slf4j;
    requires com.mojang.brigadier;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
