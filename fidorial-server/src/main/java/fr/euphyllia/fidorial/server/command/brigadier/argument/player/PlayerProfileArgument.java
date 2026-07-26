package fr.euphyllia.fidorial.server.command.brigadier.argument.player;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.EntitySelectorParser;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
import fr.fidorial.entity.PlayerProfileMeta;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public class PlayerProfileArgument<T> implements ArgumentType<T> {

    public static final SimpleCommandExceptionType ERROR_UNKNOWN_PLAYER =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.translatable("argument.player.unknown")));

    private static final Collection<String> EXAMPLES =
            List.of("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@a");

    private final Function<Result, T> converter;

    private PlayerProfileArgument(Function<Result, T> converter) {
        this.converter = converter;
    }

    public static PlayerProfileArgument<Result> playerProfile() {
        return playerProfile(Function.identity());
    }

    public static <T> PlayerProfileArgument<T> playerProfile(Function<Result, T> converter) {
        return new PlayerProfileArgument<>(converter);
    }

    public static Collection<PlayerProfileMeta> getPlayerProfiles(CommandContext<CommandSource> context, String name)
            throws CommandSyntaxException {

        return context.getArgument(name, Result.class).getNames(context.getSource());
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {

        Result result;

        if (reader.canRead() && reader.peek() == '@') {
            EntitySelector selector = new EntitySelectorParser(reader).parse();

            if (selector.includesEntities()) {
                throw EntityArgument.ERROR_ONLY_PLAYERS_ALLOWED.create();
            }

            result = new SelectorResult(selector);
        } else {

            int start = reader.getCursor();

            while (reader.canRead() && reader.peek() != ' ') {
                reader.skip();
            }

            String name = reader.getString().substring(start, reader.getCursor());

            result = source -> {
                FidorialServer server = (FidorialServer) source.server();

                Optional<? extends Player> player = server.player(name);

                if (player.isEmpty()) {
                    throw ERROR_UNKNOWN_PLAYER.create();
                }

                return List.of(new PlayerProfileMeta(player.get().profile()));
            };
        }

        return converter.apply(result);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (!(context.getSource() instanceof CommandSource source)) {
            return Suggestions.empty();
        }

        source.server().onlinePlayers().forEach(player -> builder.suggest(player.name()));

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @FunctionalInterface
    public interface Result {

        Collection<PlayerProfileMeta> getNames(CommandSource source) throws CommandSyntaxException;
    }

    public static class SelectorResult implements Result {

        private final EntitySelector selector;

        public SelectorResult(EntitySelector selector) {
            this.selector = selector;
        }

        @Override
        public Collection<PlayerProfileMeta> getNames(CommandSource source) throws CommandSyntaxException {

            List<Player> players = selector.findPlayers(source);

            if (players.isEmpty()) {
                throw EntityArgument.NO_PLAYERS_FOUND.create();
            }

            return players.stream()
                    .map(Player::profile)
                    .map(PlayerProfileMeta::new)
                    .toList();
        }
    }

    public static final class Info implements ArgumentTypeRegistrar<PlayerProfileArgument<?>, Info.Spec> {

        @Override
        public void serialize(Spec spec, PacketBuffer buf) {
        }

        @Override
        public Spec deserialize(PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(Spec spec, JsonObject json) {
        }

        @Override
        public Spec access(PlayerProfileArgument<?> argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<PlayerProfileArgument<?>> {

            @Override
            public PlayerProfileArgument<?> instantiate() {
                return PlayerProfileArgument.playerProfile();
            }

            @Override
            public ArgumentTypeRegistrar<PlayerProfileArgument<?>, ?> type() {
                return new Info();
            }
        }
    }
}
