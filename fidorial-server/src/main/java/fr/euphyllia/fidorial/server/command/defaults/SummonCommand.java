package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.MobTypeArgument;
import fr.euphyllia.fidorial.server.entity.mob.AbstractMob;
import fr.euphyllia.fidorial.server.entity.mob.MobFactories;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PositionResolver;
import fr.fidorial.entity.EntityType;
import fr.fidorial.world.Location;
import net.kyori.adventure.text.Component;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class SummonCommand {
    public static LiteralCommandNode<CommandSource> create() {
        return literal("summon")
                .requires(source -> source.sender().hasPermission("fidorial.command.summon"))
                .then(argument("entity", MobTypeArgument.mobType())
                        .executes(SummonCommand::executeSelf)
                        .then(argument("position", ArgumentTypes.position())
                                .executes(SummonCommand::executeCoordinates))).build();
    }

    private static int executeSelf(final CommandContext<CommandSource> context) {

        if (!(context.getSource().sender() instanceof final ServerPlayer player)) {
            context.getSource().sender().sendMessage(Component.translatable("command.summon.console"));
            return Command.SINGLE_SUCCESS;
        }

        return summon(
                context,
                player.world() instanceof final ServerWorld world
                        ? world
                        : FidorialServer.getInstance().worldManager().overworld(),
                player.location());
    }

    private static int executeCoordinates(final CommandContext<CommandSource> context) {
        final Location location = context.getArgument("position", PositionResolver.class).resolve(context.getSource());

        final ServerWorld world = context.getSource().sender() instanceof final ServerPlayer player
                && player.world() instanceof final ServerWorld serverWorld
                ? serverWorld
                : FidorialServer.getInstance().worldManager().overworld();

        return summon(context, world, location);
    }

    private static int summon(final CommandContext<CommandSource> context, final ServerWorld world, final Location location) {
        final EntityType entity = context.getArgument("entity", EntityType.class);

        if (!MobFactories.isMob(entity)) {
            context.getSource()
                    .sender()
                    .sendMessage(Component.translatable(
                            "command.summon.notmob", Component.text(entity.key().asString())));
            return Command.SINGLE_SUCCESS;
        }

        final FidorialServer server = FidorialServer.getInstance();

        final AbstractMob mob = MobFactories.create(entity, server.entityIds().allocate(), world, location);

        server.spawnEntity(mob);

        context.getSource()
                .sender()
                .sendMessage(Component.translatable(
                        "command.summon.done", Component.text(entity.key().value()), Component.text(mob.entityId())));

        return Command.SINGLE_SUCCESS;
    }
}