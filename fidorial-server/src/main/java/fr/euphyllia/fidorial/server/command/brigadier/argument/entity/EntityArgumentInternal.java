package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;

import java.util.function.Predicate;

public final class EntityArgumentInternal {

    private static final Predicate<Entity> ALL = _ -> true;

    private EntityArgumentInternal() {
    }

    public static ArgumentType<EntitySelector> player() {
        return player(_ -> true);
    }

    public static ArgumentType<EntitySelector> players() {
        return players(_ -> true);
    }

    public static ArgumentType<EntitySelector> entity() {
        return entity(ALL);
    }

    public static ArgumentType<EntitySelector> entities() {
        return entities(ALL);
    }

    public static ArgumentType<EntitySelector> player(Predicate<Player> filter) {
        return new EntityArgument(
                true,
                true,
                entity -> entity instanceof Player player && filter.test(player)
        );
    }

    public static ArgumentType<EntitySelector> players(Predicate<Player> filter) {
        return new EntityArgument(
                false,
                true,
                entity -> entity instanceof Player player && filter.test(player)
        );
    }

    public static ArgumentType<EntitySelector> entity(Predicate<Entity> filter) {
        return new EntityArgument(
                true,
                false,
                filter
        );
    }

    public static ArgumentType<EntitySelector> entities(Predicate<Entity> filter) {
        return new EntityArgument(
                false,
                false,
                filter
        );
    }
}
