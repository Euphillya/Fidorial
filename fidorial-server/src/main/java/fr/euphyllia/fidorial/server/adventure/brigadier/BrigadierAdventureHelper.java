package fr.euphyllia.fidorial.server.adventure.brigadier;

import com.mojang.brigadier.Message;
import fr.fidorial.command.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public final class BrigadierAdventureHelper {

    public static final MessageComponentSerializer MSG_SERIALIZER = MessageComponentSerializer.message();

    private BrigadierAdventureHelper() {
    }

    public static Component convert(Message message, boolean isConsole) {
        if (message instanceof FidorialTranslatableMessage(Component component)) {
            String rawComponent = PlainTextComponentSerializer.plainText().serialize(component);
            if (isConsole && rawComponent.startsWith("console.")) {
                return component;
            } else if (isConsole) {
                return Component.translatable("console." + rawComponent);
            } else {
                return component;
            }
        }

        return isConsole ? Component.text("console." + message.getString()) : Component.text(message.getString());
    }
}
