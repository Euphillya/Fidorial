package fr.euphyllia.fidorial.server.command.brigadier.argument.player;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.MessageComponentSerializer;
import fr.fidorial.entity.GameMode;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class GameModeArgument implements ArgumentType<GameMode> {

    private static final Collection<String> EXAMPLES = Arrays.stream(GameMode.values())
            .map(GameMode::name)
            .map(String::toLowerCase)
            .toList();

    private static final DynamicCommandExceptionType ERROR_INVALID =
            new DynamicCommandExceptionType(
                    value -> MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.gamemode.invalid",
                                    Component.text(value.toString())
                            )
                    )
            );

    public static GameModeArgument gameMode() {
        return new GameModeArgument();
    }

    public GameModeArgument() {
    }

    @Override
    public GameMode parse(StringReader reader) throws CommandSyntaxException {
        String input = reader.readUnquotedString();

        GameMode mode = GameMode.byName(input);
        if (mode == null) {
            throw ERROR_INVALID.createWithContext(reader, input);
        }

        return mode;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            CommandContext<S> context,
            SuggestionsBuilder builder
    ) {
        String remaining = builder.getRemaining().toLowerCase();

        for (GameMode mode : GameMode.values()) {
            String name = mode.name().toLowerCase();
            if (name.startsWith(remaining)) {
                builder.suggest(name);
            }
        }

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<GameModeArgument, Info.Spec> {

        @Override
        public void serialize(Spec spec, PacketBuffer buf) {}

        @Override
        public Spec deserialize(PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(Spec spec, JsonObject json) {}

        @Override
        public Spec access(GameModeArgument argument) {
            return new Spec();
        }


        public record Spec() implements ArgumentTypeRegistrar.Spec<GameModeArgument> {
            @Override
            public GameModeArgument instantiate() {
                return new GameModeArgument();
            }


            @Override
            public ArgumentTypeRegistrar<GameModeArgument, ?> type() {
                return new Info();
            }
        }
    }
}
