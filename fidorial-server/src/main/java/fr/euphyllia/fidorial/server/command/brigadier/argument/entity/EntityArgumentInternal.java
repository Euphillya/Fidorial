package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;

import java.util.function.Function;
import java.util.function.Predicate;

public final class EntityArgumentInternal {

    private static final Predicate<Entity> ALL = _ -> true;

    private EntityArgumentInternal() {
    }

    public static <T> ArgumentType<T> entity(final Function<EntitySelector, T> converter) {
        return entity(ALL, converter);
    }

    public static <T> ArgumentType<T> entities(final Function<EntitySelector, T> converter) {
        return entities(ALL, converter);
    }

    public static <T> ArgumentType<T> player(final Function<EntitySelector, T> converter) {
        return player(_ -> true, converter);
    }

    public static <T> ArgumentType<T> players(final Function<EntitySelector, T> converter) {
        return players(_ -> true, converter);
    }

    public static <T> ArgumentType<T> entity(final Predicate<Entity> filter, final Function<EntitySelector, T> converter) {
        return new EntityArgument<>(true, false, true, filter, converter);
    }

    public static <T> ArgumentType<T> entities(final Predicate<Entity> filter, final Function<EntitySelector, T> converter) {
        return new EntityArgument<>(false, false, true, filter, converter);
    }

    public static <T> ArgumentType<T> player(final Predicate<Player> filter, final Function<EntitySelector, T> converter) {
        return new EntityArgument<>(true, true, true, entity -> entity instanceof final Player player && filter.test(player), converter);
    }

    public static <T> ArgumentType<T> players(final Predicate<Player> filter, final Function<EntitySelector, T> converter) {
        return new EntityArgument<>(false, true, true, entity -> entity instanceof final Player player && filter.test(player), converter);
    }
}
