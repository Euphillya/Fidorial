package fr.euphyllia.fidorial.server.command.brigadier;

import com.mojang.brigadier.Message;
import fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper;
import fr.euphyllia.fidorial.server.adventure.brigadier.FidorialTranslatableMessage;
import fr.fidorial.command.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public final class MessageComponentSerializerImpl implements MessageComponentSerializer {

    @Override
    public @NotNull Component deserialize(@NotNull Message input) {
        return BrigadierAdventureHelper.convert(input);
    }

    @Override
    public @NotNull Message serialize(@NotNull Component component) {
        return new FidorialTranslatableMessage(component);
    }
}
