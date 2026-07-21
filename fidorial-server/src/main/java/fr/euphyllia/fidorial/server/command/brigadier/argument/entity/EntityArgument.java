package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.EntitySelectorParser;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class EntityArgument
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

    public EntityArgument(
            boolean single,
            boolean playersOnly,
            Predicate<Entity> predicate
    ) {
        this.single = single;
        this.playersOnly = playersOnly;
        this.predicate = predicate;
    }


    public static EntityArgument entity() {
        return new EntityArgument(true, false, _ -> true);
    }


    public static EntityArgument entities() {
        return new EntityArgument(false, false, _ -> true);
    }


    public static EntityArgument player() {
        return new EntityArgument(true, true, Player.class::isInstance);
    }


    public static EntityArgument players() {
        return new EntityArgument(false, true, Player.class::isInstance);
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

    public static final class Info
            implements ArgumentTypeRegistrar<EntityArgument, Info.Spec> {


        @Override
        public void serialize(Spec spec, PacketBuffer buf) {
            int flags = 0;

            if(spec.single())
                flags |= 1;

            if(spec.playersOnly())
                flags |= 2;

            buf.writeByte(flags);
        }

        @Override
        public Spec deserialize(PacketBuffer buf) {
            int flags = buf.readByte();
            return new Spec(
                    (flags & 1) != 0,
                    (flags & 2) != 0
            );
        }

        @Override
        public void serializeJson(Spec spec, JsonObject json) {
            json.addProperty("single", spec.single());
            json.addProperty("players_only", spec.playersOnly());
        }

        @Override
        public Spec access(EntityArgument argument) {
            return new Spec(argument.single(), argument.playersOnly());
        }

        public record Spec(boolean single, boolean playersOnly) implements ArgumentTypeRegistrar.Spec<EntityArgument> {

            @Override
            public EntityArgument instantiate() {
                return new EntityArgument(
                        single,
                        playersOnly,
                        _ -> true
                );
            }


            @Override
            public ArgumentTypeRegistrar<EntityArgument, ?> type() {
                return new Info();
            }
        }
    }
}
