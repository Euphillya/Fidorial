package fr.euphyllia.fidorial.server.adventure.brigadier;

import com.mojang.brigadier.Message;
import fr.fidorial.command.MessageComponentSerializer;
import net.kyori.adventure.text.Component;

public final class BrigadierAdventureHelper {

    public static final MessageComponentSerializer MSG_SERIALIZER = MessageComponentSerializer.message();

    private BrigadierAdventureHelper() {
    }

    public static Component convert(Message message) {
        if (message instanceof FidorialTranslatableMessage(Component component)) {
            return component;
        }

        return Component.text(message.getString());
    }
}
