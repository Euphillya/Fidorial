package fr.euphyllia.fidorial.server.command.brigadier.argument.bossbar;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ForceServerSuggestions;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class BossBarColorArgument implements ArgumentType<BossBar.Color>, ForceServerSuggestions {

    private static final Collection<String> EXAMPLES = Arrays.asList("red", "blue");

    public static final SuggestionProvider<CommandSource> SUGGESTIONS = (_, builder) -> {
        for (final BossBar.Color color : BossBar.Color.values()) {
            builder.suggest(color.name().toLowerCase(Locale.ROOT));
        }
        return builder.buildFuture();
    };

    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.enum.invalid",
                            Component.text(String.valueOf(value)))));

    public static BossBarColorArgument bossBarColor() {
        return new BossBarColorArgument();
    }

    @Override
    public BossBar.Color parse(final StringReader reader) throws CommandSyntaxException {
        final String value = reader.readUnquotedString();

        for (final BossBar.Color color : BossBar.Color.values()) {
            if (color.name().equalsIgnoreCase(value)) {
                return color;
            }
        }

        throw ERROR_INVALID_VALUE.createWithContext(reader, value);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            final CommandContext<S> context,
            final SuggestionsBuilder builder
    ) {
        for (final BossBar.Color color : BossBar.Color.values()) {
            builder.suggest(color.name().toLowerCase(Locale.ROOT));
        }
        return builder.buildFuture();
    }


    @Override
    public SuggestionProvider<CommandSource> suggestionProvider() {
        return SUGGESTIONS;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<BossBarColorArgument, Info.Spec> {

        @Override
        public void serialize(final Spec spec, final PacketBuffer buf) {
            buf.writeVarInt(StringArgumentType.StringType.SINGLE_WORD.ordinal());
        }

        @Override
        public Spec deserialize(final PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(final Spec spec, final JsonObject json) {
        }

        @Override
        public Spec access(final BossBarColorArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<BossBarColorArgument> {

            @Override
            public BossBarColorArgument instantiate() {
                return BossBarColorArgument.bossBarColor();
            }

            @Override
            public ArgumentTypeRegistrar<BossBarColorArgument, ?> type() {
                return new Info();
            }
        }
    }
}
