package fr.euphyllia.fidorial.server.command.brigadier;

import com.mojang.brigadier.Message;
import fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper;
import fr.euphyllia.fidorial.server.adventure.brigadier.FidorialTranslatableMessage;
import fr.fidorial.command.MessageComponentSerializer;
import net.kyori.adventure.text.Component;

public final class MessageComponentSerializerImpl implements MessageComponentSerializer {

    @Override
    public Component deserialize(Message input) {
        return BrigadierAdventureHelper.convert(input, false);
    }

    @Override
    public Message serialize(Component component) {
        return new FidorialTranslatableMessage(component);
    }
}
