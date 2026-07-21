import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.test {
    requires fr.fidorial;
    requires com.mojang.brigadier;
    requires net.kyori.adventure.api;
    requires net.kyori.adventure.key;
    requires net.kyori.adventure.text.logger.slf4j;
    requires net.kyori.adventure.text.minimessage;
    requires net.kyori.adventure.text.serializer.plain;
}
