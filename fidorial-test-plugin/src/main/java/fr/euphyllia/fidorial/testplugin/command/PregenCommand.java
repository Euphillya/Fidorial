package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import fr.fidorial.Server;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.entity.Player;
import fr.fidorial.world.World;
import net.kyori.adventure.text.Component;

public final class PregenCommand {

    private static TestPlugin plugin;

    public PregenCommand(TestPlugin plugin) {
        PregenCommand.plugin = plugin;
    }

    public CommandTree create() {
        var command = CommandTree.literal("pregen")
                .then(CommandTree.literal("start")
                        .requires(_ -> !isTaskRunning())
                        .then(CommandTree.argument("radius", ArgumentTypes.integer(1, Integer.MAX_VALUE))
                                .executes(PregenCommand::startDefault)
                                .then(CommandTree.argument("centerX", IntegerArgumentType.integer())
                                        .then(CommandTree.argument("centerZ", IntegerArgumentType.integer())
                                                .executes(PregenCommand::startCentered)))))
                .then(CommandTree.literal("stop")
                        .executes(PregenCommand::stopCommand)
                        .requires(_ -> isTaskRunning()))
                .then(CommandTree.literal("status").executes(PregenCommand::statusCommand));

        return new CommandTree(command);
    }

    // helper for requires predicate
    private static boolean isTaskRunning() {
        try {
            return plugin.getTask().isRunning();
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static int startDefault(CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();
        int radius = IntegerArgumentType.getInteger(ctx, "radius");

        int cx = 0;
        int cz = 0;
        World world = null;

        if (sender instanceof Player player) {
            var chunk = player.chunk();
            cx = chunk.x();
            cz = chunk.z();
            world = player.world();
        }

        if (world == null) {
            msg(sender, "<red>Aucun monde cible.</red>");
            return Command.SINGLE_SUCCESS;
        }

        PregenTask task = new PregenTask(
                world,
                PregenCommand.plugin.logger,
                cx,
                cz,
                radius,
                message -> {
                    PregenCommand.plugin.logger.info("[Pregen] {}", message);
                    msg(sender, "<gray>[Pregen]</gray> " + message);
                },
                PregenCommand::resendCommands,
                PregenCommand::resendCommands);

        PregenCommand.plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int startCentered(CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        int radius = IntegerArgumentType.getInteger(ctx, "radius");
        int centerX = IntegerArgumentType.getInteger(ctx, "centerX");
        int centerZ = IntegerArgumentType.getInteger(ctx, "centerZ");

        World world = null;

        if (sender instanceof Player player) {
            world = player.world();
        }

        if (world == null) {
            msg(sender, "<red>Aucun monde cible.</red>");
            return Command.SINGLE_SUCCESS;
        }

        int total = (2 * radius + 1) * (2 * radius + 1);

        msg(sender, "Pre-generation de " + total + " chunks (rayon " + radius + ")...");

        PregenTask task = new PregenTask(
                world,
                PregenCommand.plugin.logger,
                centerX,
                centerZ,
                radius,
                message -> {
                    PregenCommand.plugin.logger.info("[Pregen] {}", message);
                    msg(sender, "<gray>[Pregen]</gray> " + message);
                },
                PregenCommand::resendCommands,
                PregenCommand::resendCommands);

        PregenCommand.plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int stopCommand(CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        PregenTask task = PregenCommand.plugin.getTask();

        task.cancel();
        msg(sender, "Arret de la pre-generation demande.");

        return Command.SINGLE_SUCCESS;
    }

    private static int statusCommand(CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        PregenTask task = PregenCommand.plugin.getTask();

        if (!task.isRunning()) {
            msg(sender, "Aucune pre-generation en cours.");
            return Command.SINGLE_SUCCESS;
        }

        msg(sender, "Pre-generation : " + task.status());

        return Command.SINGLE_SUCCESS;
    }

    private static void msg(CommandSender sender, String message) {
        sender.sendMessage(Component.text(message));
    }

    public static void resendCommands() {
        Server server = plugin.server();
        for (Player player : server.onlinePlayers()) {
            player.refreshCommands();
        }
    }
}
