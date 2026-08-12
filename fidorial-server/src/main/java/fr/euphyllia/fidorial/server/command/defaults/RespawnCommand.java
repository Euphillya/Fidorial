package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.List;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class RespawnCommand {

    private static final String PERMISSION = "fidorial.command.respawn";

    public static LiteralCommandNode<CommandSource> create() {
        return literal("respawn")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .executes(RespawnCommand::executeSelf)
                .then(argument("targets", ArgumentTypes.players()).executes(RespawnCommand::executeTargets))
                .build();
    }

    private static int executeSelf(final CommandContext<CommandSource> context) {
        if (!(context.getSource().sender() instanceof final Player sender)) {
            context.getSource().sender().sendMessage(Component.translatable("command.respawn.console"));
            return Command.SINGLE_SUCCESS;
        }
        return respawn(context, List.of(sender));
    }

    private static int executeTargets(final CommandContext<CommandSource> context) throws CommandSyntaxException {
        final List<Player> targets = context.getArgument("targets", PlayerSelectorArgumentResolver.class)
                .resolve(context.getSource());
        return respawn(context, targets);
    }

    private static int respawn(final CommandContext<CommandSource> context, final List<Player> targets) {
        for (final Player target : targets) {
            if (!target.respawn()) {
                context.getSource()
                        .sender()
                        .sendMessage(Component.translatable(
                                "command.respawn.alive", Component.text(target.name())));
                continue;
            }

            if (context.getSource().sender() != target) {
                context.getSource()
                        .sender()
                        .sendMessage(Component.translatable(
                                "command.respawn.done", Component.text(target.name())));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
