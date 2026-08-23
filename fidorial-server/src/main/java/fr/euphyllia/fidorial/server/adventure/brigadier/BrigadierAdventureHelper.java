package fr.euphyllia.fidorial.server.adventure.brigadier;

import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;

public final class BrigadierAdventureHelper {

    private BrigadierAdventureHelper() {
    }

    public static Component convert(final Message message, final boolean isConsole) {
        if (message instanceof FidorialTranslatableMessage(final Component component)) {
            if (!isConsole) {
                return component;
            }

            if (component instanceof final TranslatableComponent translatable) {
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
