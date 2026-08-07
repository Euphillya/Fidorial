package fr.euphyllia.fidorial.server.command.brigadier.argument.bossbar;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.fidorial.command.argument.ArgumentTypes;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class BossBarColorArgument {

    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.enum.invalid", Component.text(String.valueOf(value)))));

    private BossBarColorArgument() {
    }

    public static ArgumentType<BossBar.Color> bossBarColor() {
        return ArgumentTypes.map(StringArgumentType.word(), BossBarColorArgument::parse, BossBarColorArgument::suggest, EXAMPLES);
    }

    private static final List<String> EXAMPLES = List.of("red", "blue");

    private static BossBar.Color parse(final String value) throws CommandSyntaxException {
        for (final BossBar.Color color : BossBar.Color.values()) {
            if (color.name().equalsIgnoreCase(value)) return color;
        }
        throw ERROR_INVALID_VALUE.create(value);
    }

    private static <S> CompletableFuture<Suggestions> suggest(final CommandContext<S> ctx, final SuggestionsBuilder builder) {
        for (final BossBar.Color color : BossBar.Color.values()) {
            builder.suggest(color.name().toLowerCase(Locale.ROOT));
        }
        return builder.buildFuture();
    }
}
