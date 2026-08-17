package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import fr.fidorial.Server;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.entity.Player;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class PregenCommand {

    private static TestPlugin plugin;

    public PregenCommand(final TestPlugin plugin) {
        PregenCommand.plugin = plugin;
    }

    public LiteralCommandNode<CommandSource> create() {
        return literal("pregen")
                .then(literal("start")
                        .requires(_ -> !isTaskRunning())
                        .then(argument("radius", ArgumentTypes.integer(1, Integer.MAX_VALUE))
                                .executes(PregenCommand::startDefault)
                                .then(argument("centerX", IntegerArgumentType.integer())
                                        .then(argument("centerZ", IntegerArgumentType.integer())
                                                .executes(PregenCommand::startCentered)))))
                .then(literal("stop")
                        .executes(PregenCommand::stopCommand)
                        .requires(_ -> isTaskRunning()))
                .then(literal("status").executes(PregenCommand::statusCommand)).build();
    }

    // helper for requires predicate
    private static boolean isTaskRunning() {
        try {
            return plugin.getTask().isRunning();
        } catch (final NullPointerException e) {
            return false;
        }
    }

    private static int startDefault(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        final int radius = IntegerArgumentType.getInteger(ctx, "radius");

        int cx = 0;
        int cz = 0;
        World world = null;

        if (sender instanceof final Player player) {
            final var chunk = player.chunk();
            cx = chunk.x();
            cz = chunk.z();
            world = player.world();
        }

        if (world == null) {
            world = plugin.server().world(Key.key("overworld")).get();
//            plugin.msg(sender, "<red>Aucun monde cible.</red>");
//            return Command.SINGLE_SUCCESS;
        }

        final PregenTask task = new PregenTask(
                world,
                plugin.logger,
                cx,
                cz,
                radius,
                message -> {
                    plugin.logger.info("[Pregen] {}", message);
                    plugin.msg(sender, "<gray>[Pregen]</gray> " + message);
                },
                PregenCommand::resendCommands,
                PregenCommand::resendCommands);

        plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int startCentered(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final int radius = IntegerArgumentType.getInteger(ctx, "radius");
        final int centerX = IntegerArgumentType.getInteger(ctx, "centerX");
        final int centerZ = IntegerArgumentType.getInteger(ctx, "centerZ");

        World world = null;

        if (sender instanceof final Player player) {
            world = player.world();
        }

        if (world == null) {
            plugin.msg(sender, "<red>Aucun monde cible.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final int total = (2 * radius + 1) * (2 * radius + 1);

        plugin.msg(sender, "Pre-generation de " + total + " chunks (rayon " + radius + ")...");

        final PregenTask task = new PregenTask(
                world,
                plugin.logger,
                centerX,
                centerZ,
                radius,
                message -> {
                    plugin.logger.info("[Pregen] {}", message);
                    plugin.msg(sender, "<gray>[Pregen]</gray> " + message);
                },
                PregenCommand::resendCommands,
                PregenCommand::resendCommands);

        plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int stopCommand(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final PregenTask task = plugin.getTask();

        task.cancel();
        plugin.msg(sender, "Arret de la pre-generation demande.");

        return Command.SINGLE_SUCCESS;
    }

    private static int statusCommand(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final PregenTask task = plugin.getTask();

        if (!task.isRunning()) {
            plugin.msg(sender, "Aucune pre-generation en cours.");
            return Command.SINGLE_SUCCESS;
        }

        plugin.msg(sender, "Pre-generation : " + task.status());

        return Command.SINGLE_SUCCESS;
    }

    public static void resendCommands() {
        final Server server = plugin.server();
        for (final Player player : server.onlinePlayers()) {
            player.refreshCommands();
        }
    }
}
