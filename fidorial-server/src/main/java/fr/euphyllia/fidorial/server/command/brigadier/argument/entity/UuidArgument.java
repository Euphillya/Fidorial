package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class UuidArgument implements ArgumentType<UUID> {

    private static final Collection<String> EXAMPLES = List.of(
            "dd12be42-52a9-4a91-a8a1-11c01849e498"
    );

    private static final Pattern ALLOWED_CHARACTERS =
            Pattern.compile("^([-A-Fa-f0-9]+)");

    private static final SimpleCommandExceptionType ERROR_INVALID_UUID =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable("argument.uuid.invalid")
                    )
            );

    public static UuidArgument uuid() {
        return new UuidArgument();
    }

    public static UUID getUuid(
            CommandContext<CommandSource> context,
            String name
    ) {
        return context.getArgument(name, UUID.class);
    }

    @Override
    public UUID parse(
            StringReader reader
    ) throws CommandSyntaxException {

        String remaining = reader.getRemaining();
        Matcher matcher = ALLOWED_CHARACTERS.matcher(remaining);

        if (matcher.find()) {
            String value = matcher.group(1);

            try {
                UUID uuid = UUID.fromString(value);
                reader.setCursor(reader.getCursor() + value.length());
                return uuid;
            } catch (IllegalArgumentException _) {}
        }

        throw ERROR_INVALID_UUID.createWithContext(reader);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
