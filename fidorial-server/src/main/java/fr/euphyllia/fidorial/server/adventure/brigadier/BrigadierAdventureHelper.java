package fr.euphyllia.fidorial.server.adventure.brigadier;

import com.mojang.brigadier.Message;
import fr.fidorial.command.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;

public final class BrigadierAdventureHelper {

    public static final MessageComponentSerializer MSG_SERIALIZER = MessageComponentSerializer.message();

    private BrigadierAdventureHelper() {
    }

    public static Component convert(Message message, boolean isConsole) {
        if (message instanceof FidorialTranslatableMessage(Component component)) {
            if (!isConsole) {
                return component;
            }

            if (component instanceof TranslatableComponent translatable) {
                return Component.translatable(
                        "console." + translatable.key(),
                        translatable.arguments().toArray(ComponentLike[]::new)
                );
            }

            return component;
        }

        return isConsole ? Component.translatable("console." + message.getString()) : Component.translatable(message.getString());
    }
}
