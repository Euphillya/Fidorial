package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.chat.ComponentArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.chat.HexColorArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.chat.NamedColorArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.chat.StyleArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.item.ItemArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.item.ItemPredicateArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.AngleArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.BlockPositionArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.DimensionArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.BoolArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.DoubleArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.FloatArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.IntegerArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.LongArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.primitive.StringArgumentRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.argument.range.RangeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.KeyArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;
import fr.euphyllia.fidorial.server.registry.data.ArgumentTypeIds;

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
    public static final BlockPositionArgument.Info BLOCK_POS = new BlockPositionArgument.Info();
    public static final Vec3Argument.Info VEC3 = new Vec3Argument.Info();
    public static final ItemArgument.Info ITEM_STACK = new ItemArgument.Info();
    public static final ItemPredicateArgument.Info ITEM_PREDICATE = new ItemPredicateArgument.Info();
    public static final NamedColorArgument.Info TEAM_COLOR = new NamedColorArgument.Info();
    public static final HexColorArgument.Info HEX_COLOR = new HexColorArgument.Info();
    public static final ComponentArgument.Info COMPONENT = new ComponentArgument.Info();
    public static final StyleArgument.Info STYLE = new StyleArgument.Info();
    public static final AngleArgument.Info ANGLE = new AngleArgument.Info();
    public static final RangeArgument.Ints.Info INT_RANGE = new RangeArgument.Ints.Info();
    public static final RangeArgument.Floats.Info FLOAT_RANGE = new RangeArgument.Floats.Info();
    public static final DimensionArgument.Info DIMENSION = new DimensionArgument.Info();
    public static final GameModeArgument.Info GAME_MODE = new GameModeArgument.Info();
    public static final TimeArgument.Info TIME = new TimeArgument.Info();
    public static final ResourceArgument.Info<?> RESOURCE = new ResourceArgument.Info<>();
    public static final ResourceKeyArgument.Info<?> RESOURCE_KEY = new ResourceKeyArgument.Info<>();
    public static final UuidArgument.Info UUID = new UuidArgument.Info();

    static {
        register(BoolArgumentType.class, BOOL, ArgumentTypeIds.BOOL_ARGUMENT_ID);
        register(FloatArgumentType.class, FLOAT, ArgumentTypeIds.FLOAT_ARGUMENT_ID);
        register(DoubleArgumentType.class, DOUBLE, ArgumentTypeIds.DOUBLE_ARGUMENT_ID);
        register(IntegerArgumentType.class, INTEGER, ArgumentTypeIds.INTEGER_ARGUMENT_ID);
        register(LongArgumentType.class, LONG, ArgumentTypeIds.LONG_ARGUMENT_ID);
        register(StringArgumentType.class, STRING, ArgumentTypeIds.STRING_ARGUMENT_ID);
        register(EntityArgument.class, ENTITY, ArgumentTypeIds.ENTITY_ARGUMENT_ID);
        register(PlayerProfileArgument.class, PLAYER_PROFILE, ArgumentTypeIds.GAME_PROFILE_ARGUMENT_ID);
        register(BlockPositionArgument.class, BLOCK_POS, ArgumentTypeIds.BLOCK_POS_ARGUMENT_ID);
        register(Vec3Argument.class, VEC3, ArgumentTypeIds.VEC3_ARGUMENT_ID);
        register(ItemArgument.class, ITEM_STACK, ArgumentTypeIds.ITEM_STACK_ARGUMENT_ID);
        register(ItemPredicateArgument.class, ITEM_PREDICATE, ArgumentTypeIds.ITEM_PREDICATE_ARGUMENT_ID);
        register(NamedColorArgument.class, TEAM_COLOR, ArgumentTypeIds.TEAM_COLOR_ARGUMENT_ID);
        register(HexColorArgument.class, HEX_COLOR, ArgumentTypeIds.HEX_COLOR_ARGUMENT_ID);
        register(ComponentArgument.class, COMPONENT, ArgumentTypeIds.COMPONENT_ARGUMENT_ID);
        register(StyleArgument.class, STYLE, ArgumentTypeIds.STYLE_ARGUMENT_ID);
        register(AngleArgument.class, ANGLE, ArgumentTypeIds.ANGLE_ARGUMENT_ID);
        register(KeyArgument.class, KEY, ArgumentTypeIds.RESOURCE_LOCATION_ARGUMENT_ID);
        register(RangeArgument.Ints.class, INT_RANGE, ArgumentTypeIds.INT_RANGE_ARGUMENT_ID);
        register(RangeArgument.Floats.class, FLOAT_RANGE, ArgumentTypeIds.FLOAT_RANGE_ARGUMENT_ID);
        register(DimensionArgument.class, DIMENSION, ArgumentTypeIds.DIMENSION_ARGUMENT_ID);
        register(GameModeArgument.class, GAME_MODE, ArgumentTypeIds.GAMEMODE_ARGUMENT_ID);
        register(TimeArgument.class, TIME, ArgumentTypeIds.TIME_ARGUMENT_ID);
        register(ResourceArgument.class, RESOURCE, ArgumentTypeIds.RESOURCE_ARGUMENT_ID);
        register(ResourceKeyArgument.class, RESOURCE_KEY, ArgumentTypeIds.RESOURCE_KEY_ARGUMENT_ID);
        register(UuidArgument.class, UUID, ArgumentTypeIds.UUID_ARGUMENT_ID);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void register(Class<? extends ArgumentType> clazz, ArgumentTypeRegistrar registrar, int networkId) {
        ArgumentTypeRegistry.register(clazz, registrar);
        NetworkArgumentIds.register(networkId, registrar);
    }

    public static void bootstrap() {
    }
}
