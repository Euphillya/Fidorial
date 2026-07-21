package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;

public final class ArgumentTypes {

    private ArgumentTypes() {
    }

    //public static final BoolArgument.Info BOOL = new BoolArgument.Info();
    //public static final IntegerArgument.Info INTEGER = new IntegerArgument.Info();
    //public static final StringArgument.Info STRING = new StringArgument.Info();
    public static final EntityArgument.Info ENTITY = new EntityArgument.Info();
    public static final PlayerProfileArgument.Info PLAYER_PROFILE = new PlayerProfileArgument.Info();
    public static final Vec3Argument.Info VEC3 = new Vec3Argument.Info();
    public static final GameModeArgument.Info GAME_MODE = new GameModeArgument.Info();
    public static final TimeArgument.Info TIME = new TimeArgument.Info();
    public static final ResourceArgument.Info<?> RESOURCE = new ResourceArgument.Info<>();
    public static final ResourceKeyArgument.Info<?> RESOURCE_KEY = new ResourceKeyArgument.Info<>();
    public static final UuidArgument.Info UUID = new UuidArgument.Info();

    static {
        //register(BoolArgumentType.class, BOOL, 0);
        //register(IntegerArgumentType.class, INTEGER, 3);
        //register(StringArgumentType.class, STRING, 5);
        register(EntityArgument.class, ENTITY, 6);
        register(PlayerProfileArgument.class, PLAYER_PROFILE, 7);
        register(Vec3Argument.class, VEC3, 10);
        register(GameModeArgument.class, GAME_MODE, 42);
        register(TimeArgument.class, TIME, 43);
        register(ResourceArgument.class, RESOURCE, 46);
        register(ResourceKeyArgument.class, RESOURCE_KEY, 47);
        register(UuidArgument.class, UUID, 56);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void register(
            Class<? extends ArgumentType> clazz,
            ArgumentTypeRegistrar registrar,
            int networkId
    ) {
        ArgumentTypeRegistry.register(clazz, registrar);
        NetworkArgumentIds.register(networkId, registrar);
    }

    public static void bootstrap() {}
}
