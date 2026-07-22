package fr.euphyllia.fidorial.server.adventure.brigadier;

import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * A custom wrapper around Brigadier's {@link Message}, used for converting brigadier exceptions and components into translation keys
 * and internal Brigadier types.
 * @param component the component
 */
public record FidorialTranslatableMessage(Component component) implements Message {

    public FidorialTranslatableMessage(String key, ComponentLike... args) {
        this(Component.translatable(key, args));
    }

    @Override
    public String getString() {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
