package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.*;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;

import java.util.HashMap;
import java.util.Map;

public final class ArgumentTypeRegistry {

    private ArgumentTypeRegistry() {}

    private static final Map<Class<?>, Integer> IDS =
            new HashMap<>();


    public static final int BOOL = register(
            BoolArgumentType.class,
            0
    );

    public static final int INTEGER = register(
            IntegerArgumentType.class,
            3
    );


    public static final int STRING = register(
            StringArgumentType.class,
            5
    );


    public static final int ENTITY = register(
            EntityArgumentType.class,
            6
    );

    public static final int PLAYER_PROFILE = register(
            PlayerProfileArgument.class,
            7
    );

    public static final int VEC3 = register(
            Vec3Argument.class,
            10
    );

    public static final int GAME_MODE = register(
            GameModeArgument.class,
            42
    );

    public static final int TIME = register(
            TimeArgument.class,
            43
    );

    public static final int RESOURCE_KEY = register(
            ResourceKeyArgument.class,
            47
    );

    public static final int UUID = register(
            UuidArgument.class,
            56
    );


    private static int register(
            Class<?> type,
            int id
    ) {
        IDS.put(type, id);
        return id;
    }


    public static int getId(
            ArgumentType<?> argument
    ) {

        Integer id = IDS.get(argument.getClass());

        if (id == null) {
            throw new IllegalArgumentException(
                    "Unknown argument type: "
                            + argument.getClass()
            );
        }

        return id;
    }
}
