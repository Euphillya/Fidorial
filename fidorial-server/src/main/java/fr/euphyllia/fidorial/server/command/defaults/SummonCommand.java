package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PositionResolver;
import fr.fidorial.command.argument.resolvers.selector.EntitySelectorArgumentResolver;
import fr.fidorial.entity.EntityType;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.world.Location;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.mob.Mobs;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Locale;

import static fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument.ERROR_UNKNOWN_RESOURCE;
import static fr.fidorial.registry.RegistryKey.ENTITY_TYPE;

public final class SummonCommand {
    public static CommandTree create() {
        return new CommandTree(
                CommandTree.literal("summon")
                        .requires(source ->
                                source.sender().hasPermission("fidorial.command.summon")
                        )
                        .then(CommandTree.argument("entity", ArgumentTypes.resource(ENTITY_TYPE))
                                //.suggests((ctx, builder) -> ArgumentTypes.resourceKey(ENTITY_TYPE).listSuggestions(ctx, builder))
                                .executes(SummonCommand::executeSelf)
                                .then(CommandTree.argument("position", ArgumentTypes.position())
                                        .suggests((ctx, builder) -> ArgumentTypes.position().listSuggestions(ctx, builder))
                                        .executes(SummonCommand::executeCoordinates)
                                )
                        )
        );
    }

    private static int executeSelf(CommandContext<CommandSource> context) {

        if (!(context.getSource().sender() instanceof ServerPlayer player)) {
            context.getSource().sender().sendMessage(
                    Component.translatable("command.summon.console")
            );
            return Command.SINGLE_SUCCESS;
        }

        return summon(
                context,
                player.world() instanceof ServerWorld world
                        ? world
                        : FidorialServer.getInstance().worldManager().overworld(),
                player.location()
        );
    }


    private static int executeCoordinates(CommandContext<CommandSource> context) {
        Location location =
                context.getArgument("position", PositionResolver.class)
                        .resolve(context.getSource());

        ServerWorld world = context.getSource().sender() instanceof ServerPlayer player
                && player.world() instanceof ServerWorld serverWorld
                ? serverWorld
                : FidorialServer.getInstance().worldManager().overworld();

        return summon(context, world, location);
    }


    private static int summon(
            CommandContext<CommandSource> context,
            ServerWorld world,
            Location location
    ) {
        EntityType entity = context.getArgument("entity", EntityType.class);

        if (!Mobs.isMob(entity)) {
            context.getSource().sender().sendMessage(
                    Component.translatable(
                            "command.summon.notmob",
                            Component.text(entity.key().asString())
                    )
            );
            return Command.SINGLE_SUCCESS;
        }

        FidorialServer server = FidorialServer.getInstance();

        Mob mob = Mobs.create(
                entity,
                server.entityIds().allocate(),
                world,
                location
        );

        server.spawnEntity(mob);

        context.getSource().sender().sendMessage(
                Component.translatable(
                        "command.summon.done",
                        Component.text(entity.key().value()),
                        Component.text(mob.entityId())
                )
        );

        return Command.SINGLE_SUCCESS;
    }
}
