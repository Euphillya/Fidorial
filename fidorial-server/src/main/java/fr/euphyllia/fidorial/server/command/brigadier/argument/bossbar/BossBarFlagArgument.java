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

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class BossBarFlagArgument implements ArgumentType<BossBar.Flag>, ForceServerSuggestions {

    private static final List<String> EXAMPLES = List.of("darken_screen", "play_boss_music");

    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable(
                            "argument.enum.invalid",
                            Component.text(String.valueOf(value)))));

    private final SuggestionProvider<CommandSource> suggestions;

    public BossBarFlagArgument() {
        this.suggestions = this::listSuggestions;
    }

    public static BossBarFlagArgument bossBarFlag() {
        return new BossBarFlagArgument();
    }

    @Override
    public BossBar.Flag parse(final StringReader reader) throws CommandSyntaxException {
        final String value = reader.readUnquotedString();

        for (final BossBar.Flag flag : BossBar.Flag.values()) {
            if (flag.name().equalsIgnoreCase(value)) {
                return flag;
            }
        }
        throw ERROR_INVALID_VALUE.createWithContext(reader, value);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            final CommandContext<S> context,
            final SuggestionsBuilder builder
    ) {
        for (final BossBar.Flag flag : BossBar.Flag.values()) {
            builder.suggest(flag.name().toLowerCase(Locale.ROOT));
        }
        return builder.buildFuture();
    }

    @Override
    public List<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public SuggestionProvider<CommandSource> suggestionProvider() {
        return suggestions;
    }

    public static final class Info implements ArgumentTypeRegistrar<BossBarFlagArgument, Info.Spec> {

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
        public Spec access(final BossBarFlagArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<BossBarFlagArgument> {

            @Override
            public BossBarFlagArgument instantiate() {
                return BossBarFlagArgument.bossBarFlag();
            }

            @Override
            public ArgumentTypeRegistrar<BossBarFlagArgument, ?> type() {
                return new Info();
            }
        }
    }
}
