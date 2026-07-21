package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.BoolArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.DoubleArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.FloatArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.IntegerArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.LongArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.StringArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.KeyArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;

public final class ArgumentTypes {

    private ArgumentTypes() {
    }

    public static final BoolArgumentRegistrar BOOL = new BoolArgumentRegistrar();
    public static final FloatArgumentRegistrar FLOAT = new FloatArgumentRegistrar();
    public static final DoubleArgumentRegistrar DOUBLE = new DoubleArgumentRegistrar();
    public static final IntegerArgumentRegistrar INTEGER = new IntegerArgumentRegistrar();
    public static final LongArgumentRegistrar LONG = new LongArgumentRegistrar();
    public static final StringArgumentRegistrar STRING = new StringArgumentRegistrar();
    public static final KeyArgument.Info KEY = new KeyArgument.Info();
    public static final EntityArgument.Info ENTITY = new EntityArgument.Info();
    public static final PlayerProfileArgument.Info PLAYER_PROFILE = new PlayerProfileArgument.Info();
    public static final Vec3Argument.Info VEC3 = new Vec3Argument.Info();
    public static final GameModeArgument.Info GAME_MODE = new GameModeArgument.Info();
    public static final TimeArgument.Info TIME = new TimeArgument.Info();
    public static final ResourceArgument.Info<?> RESOURCE = new ResourceArgument.Info<>();
    public static final ResourceKeyArgument.Info<?> RESOURCE_KEY = new ResourceKeyArgument.Info<>();
    public static final UuidArgument.Info UUID = new UuidArgument.Info();

    static {
        register(BoolArgumentType.class, BOOL, 0);
        register(FloatArgumentType.class, FLOAT, 1);
        register(DoubleArgumentType.class, DOUBLE, 2);
        register(IntegerArgumentType.class, INTEGER, 3);
        register(LongArgumentType.class, LONG, 4);
        register(StringArgumentType.class, STRING, 5);
        register(KeyArgument.class, KEY, 36);
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
    private static void register(Class<? extends ArgumentType> clazz, ArgumentTypeRegistrar registrar, int networkId) {
        ArgumentTypeRegistry.register(clazz, registrar);
        NetworkArgumentIds.register(networkId, registrar);
    }

    public static void bootstrap() {
    }
}
