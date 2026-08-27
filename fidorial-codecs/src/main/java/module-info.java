import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.codecs {
    exports fr.euphyllia.fidorial.server.codecs to fr.fidorial.entity;
    exports fr.euphyllia.fidorial.server.codecs.adventure to fr.fidorial.protocol, fr.fidorial.server;
    exports fr.euphyllia.fidorial.server.codecs.dialog to fr.fidorial.protocol, fr.fidorial.registry;
    exports fr.euphyllia.fidorial.server.codecs.world to fr.fidorial.registry;

    requires com.google.gson;
    requires transitive fr.fidorial;
    requires io.papermc.adventurex.nbt.dfu;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
