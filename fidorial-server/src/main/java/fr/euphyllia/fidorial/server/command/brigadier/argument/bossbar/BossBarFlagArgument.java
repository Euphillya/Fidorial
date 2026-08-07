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

public final class BossBarFlagArgument {

    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.enum.invalid", Component.text(String.valueOf(value)))));

    private BossBarFlagArgument() {
    }

    public static ArgumentType<BossBar.Flag> bossBarFlag() {
        return ArgumentTypes.map(StringArgumentType.word(), BossBarFlagArgument::parse, BossBarFlagArgument::suggest, EXAMPLES);
    }

    private static final List<String> EXAMPLES = List.of("progress", "notched_10");

    private static BossBar.Flag parse(final String value) throws CommandSyntaxException {
        for (final BossBar.Flag flag : BossBar.Flag.values()) {
            if (flag.name().equalsIgnoreCase(value)) return flag;
        }
        throw ERROR_INVALID_VALUE.create(value);
    }

    private static <S> CompletableFuture<Suggestions> suggest(final CommandContext<S> ctx, final SuggestionsBuilder builder) {
        for (final BossBar.Flag flag : BossBar.Flag.values()) {
            builder.suggest(flag.name().toLowerCase(Locale.ROOT));
        }
        return builder.buildFuture();
    }
}
