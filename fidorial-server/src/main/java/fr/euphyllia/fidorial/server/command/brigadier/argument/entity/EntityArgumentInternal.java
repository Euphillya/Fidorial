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

    public static <T> ArgumentType<T> entity(Function<EntitySelector, T> converter) {
        return entity(ALL, converter);
    }

    public static <T> ArgumentType<T> entities(Function<EntitySelector, T> converter) {
        return entities(ALL, converter);
    }

    public static <T> ArgumentType<T> player(Function<EntitySelector, T> converter) {
        return player((Predicate<Player>) _ -> true, converter);
    }

    public static <T> ArgumentType<T> players(Function<EntitySelector, T> converter) {
        return players((Predicate<Player>) _ -> true, converter);
    }

    public static <T> ArgumentType<T> entity(Predicate<Entity> filter, Function<EntitySelector, T> converter) {
        return new EntityArgument<>(true, false, filter, converter);
    }

    public static <T> ArgumentType<T> entities(Predicate<Entity> filter, Function<EntitySelector, T> converter) {
        return new EntityArgument<>(false, false, filter, converter);
    }

    public static <T> ArgumentType<T> player(Predicate<Player> filter, Function<EntitySelector, T> converter) {
        return new EntityArgument<>(true, true, entity -> entity instanceof Player player && filter.test(player), converter);
    }

    public static <T> ArgumentType<T> players(Predicate<Player> filter, Function<EntitySelector, T> converter) {
        return new EntityArgument<>(false, true, entity -> entity instanceof Player player && filter.test(player), converter);
    }
}
