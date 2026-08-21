package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.MobFactories;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.entity.EntityType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public class MobTypeArgument {

    public static final DynamicCommandExceptionType ERROR_UNKNOWN_ENTITY_TYPE =
            new DynamicCommandExceptionType(id -> MSG_SERIALIZER.serialize(
                    Component.translatable("command.summon.unknown", Component.text(String.valueOf(id)))));

    private MobTypeArgument() {
    }

    public static ArgumentType<EntityType> mobType() {
        return ArgumentTypes.map(ArgumentTypes.key(), MobTypeArgument::resolve, MobTypeArgument::suggest);
    }

    private static EntityType resolve(final Key key, final StringReader reader) throws CommandSyntaxException {
        final EntityType type = EntityTypes.get(key);

        if (type == null) {
            throw ERROR_UNKNOWN_ENTITY_TYPE.createWithContext(reader, key.asString());
        }

        return type;
    }

    private static CompletableFuture<Suggestions> suggest(
            final CommandContext<CommandSource> context,
            final SuggestionsBuilder builder
    ) {
        final String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        final String minecraftPrefix = Key.MINECRAFT_NAMESPACE + Key.DEFAULT_SEPARATOR;
        final boolean remainingHasNamespace = remaining.indexOf(Key.DEFAULT_SEPARATOR) >= 0;

        for (final Key key : MobFactories.keys()) {
            final String full = key.asString();
            final String path = full.startsWith(minecraftPrefix) ? full.substring(minecraftPrefix.length()) : full;

            if (remainingHasNamespace ? full.contains(remaining) : path.contains(remaining)) {
                builder.suggest(full);
            }
        }

        return builder.buildFuture();
    }
}
