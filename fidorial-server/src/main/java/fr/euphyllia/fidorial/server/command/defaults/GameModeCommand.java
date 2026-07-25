package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.List;

public final class GameModeCommand {

    private static Component describe(GameMode mode) {
        return switch (mode) {
            case SURVIVAL -> Component.translatable("gamemode.survival");
            case CREATIVE -> Component.translatable("gamemode.creative");
            case ADVENTURE -> Component.translatable("gamemode.adventure");
            case SPECTATOR -> Component.translatable("gamemode.spectator");
        };
    }

    public static CommandTree create() {
        var command = CommandTree.literal("gamemode")
                .requires(src -> src.sender().hasPermission("fidorial.command.gamemode"))
                .then(CommandTree.argument("gamemode", ArgumentTypes.gameMode())
                        .executes(GameModeCommand::executeSelf)
                        .then(CommandTree.argument("target", ArgumentTypes.players())
                                .executes(GameModeCommand::executeTarget)))
                .build();
        return new CommandTree(command);
    }

    private static int executeSelf(CommandContext<CommandSource> context) {
        if (!(context.getSource().sender() instanceof Player sender)) {
            context.getSource().sender().sendMessage(Component.translatable("command.gamemode.console"));
            return Command.SINGLE_SUCCESS;
        }

        return change(context, List.of(sender));
    }

    private static int executeTarget(CommandContext<CommandSource> context) throws CommandSyntaxException {

        var resolver = context.getArgument("target", PlayerSelectorArgumentResolver.class);

        List<Player> targets = resolver.resolve(context.getSource());

        return change(context, targets);
    }

    private static int change(CommandContext<CommandSource> context, List<Player> targets) {

        GameMode mode = context.getArgument("gamemode", GameMode.class);

        for (Player target : targets) {
            target.setGameMode(mode);

            target.sendMessage(Component.translatable("command.gamemode.changed.self", describe(mode)));

            if (context.getSource().sender() != target) {
                context.getSource()
                        .sender()
                        .sendMessage(Component.translatable(
                                "command.gamemode.changed.other", Component.text(target.name()), describe(mode)));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
