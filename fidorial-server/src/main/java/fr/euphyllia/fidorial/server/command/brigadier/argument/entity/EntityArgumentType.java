package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.EntitySelectorParser;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class EntityArgumentType
        implements ArgumentType<EntitySelector> {

    private static final Collection<String> EXAMPLES = List.of(
            "Player",
            "0123",
            "@e",
            "@e[type=zombie]",
            "dd12be42-52a9-4a91-a8a1-11c01849e498"
    );


    public static final SimpleCommandExceptionType
            ERROR_NOT_SINGLE_ENTITY =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.entity.toomany"
                            )
                    )
            );


    public static final SimpleCommandExceptionType
            ERROR_NOT_SINGLE_PLAYER =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.player.toomany"
                            )
                    )
            );


    public static final SimpleCommandExceptionType
            ERROR_ONLY_PLAYERS_ALLOWED =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.player.entities"
                            )
                    )
            );


    public static final SimpleCommandExceptionType
            NO_ENTITIES_FOUND =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.entity.notfound.entity"
                            )
                    )
            );


    public static final SimpleCommandExceptionType
            NO_PLAYERS_FOUND =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.entity.notfound.player"
                            )
                    )
            );

    public static final SimpleCommandExceptionType
            SELECTORS_NOT_PERMITTED =
            new SimpleCommandExceptionType(
                    MSG_SERIALIZER.serialize(
                            Component.translatable(
                                    "argument.entity.selector.not_allowed"
                            )
                    )
            );


    private final boolean single;
    private final boolean playersOnly;

    private final Predicate<Entity> predicate;

    public EntityArgumentType(
            boolean single,
            boolean playersOnly,
            Predicate<Entity> predicate
    ) {
        this.single = single;
        this.playersOnly = playersOnly;
        this.predicate = predicate;
    }


    public static EntityArgumentType entity() {
        return new EntityArgumentType(true, false, _ -> true);
    }


    public static EntityArgumentType entities() {
        return new EntityArgumentType(false, false, _ -> true);
    }


    public static EntityArgumentType player() {
        return new EntityArgumentType(true, true, Player.class::isInstance);
    }


    public static EntityArgumentType players() {
        return new EntityArgumentType(false, true, Player.class::isInstance);
    }


    public boolean single() {
        return single;
    }


    public boolean playersOnly() {
        return playersOnly;
    }


    @Override
    public EntitySelector parse(
            StringReader reader
    ) throws CommandSyntaxException {

        int start = reader.getCursor();

        EntitySelector selector =
                new EntitySelectorParser(reader).parse();


        if (selector.maxResults() > 1 && single) {

            reader.setCursor(start);

            if (playersOnly) {
                throw ERROR_NOT_SINGLE_PLAYER
                        .createWithContext(reader);
            }

            throw ERROR_NOT_SINGLE_ENTITY
                    .createWithContext(reader);
        }


        if (selector.includesEntities()
                && playersOnly
                && !selector.selfSelector()) {

            reader.setCursor(start);

            throw ERROR_ONLY_PLAYERS_ALLOWED
                    .createWithContext(reader);
        }


        return selector;
    }


    public static Entity getEntity(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        return context
                .getArgument(
                        name,
                        EntitySelector.class
                )
                .findSingleEntity(
                        context.getSource()
                );
    }


    public static Collection<? extends Entity> getEntities(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        Collection<? extends Entity> entities =
                getOptionalEntities(context, name);

        if (entities.isEmpty()) {
            throw NO_ENTITIES_FOUND.create();
        }

        return entities;
    }


    public static Collection<? extends Entity> getOptionalEntities(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        return context
                .getArgument(
                        name,
                        EntitySelector.class
                )
                .findEntities(
                        context.getSource()
                );
    }


    public static Player getPlayer(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        return context
                .getArgument(
                        name,
                        EntitySelector.class
                )
                .findSinglePlayer(
                        context.getSource()
                );
    }


    public static Collection<Player> getOptionalPlayers(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        return context
                .getArgument(
                        name,
                        EntitySelector.class
                )
                .findPlayers(
                        context.getSource()
                );
    }


    public static Collection<Player> getPlayers(
            CommandContext<CommandSource> context,
            String name
    ) throws CommandSyntaxException {

        Collection<Player> players =
                getOptionalPlayers(context, name);

        if (players.isEmpty()) {
            throw NO_PLAYERS_FOUND.create();
        }

        return players;
    }


    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            CommandContext<S> context,
            SuggestionsBuilder builder
    ) {
        return Suggestions.empty();
    }


    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
