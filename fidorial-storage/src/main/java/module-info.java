import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.storage {
    exports fr.euphyllia.fidorial.server.world.anvil to fr.fidorial.world;
    exports fr.euphyllia.fidorial.server.world.storage to fr.fidorial.entity, fr.fidorial.server, fr.fidorial.world;

    requires transitive fr.fidorial;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.nbt;
    requires net.kyori.adventure.text.serializer.gson;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}
