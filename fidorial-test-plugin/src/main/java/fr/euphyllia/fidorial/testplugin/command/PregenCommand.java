package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import net.kyori.adventure.text.Component;

public final class PregenCommand {

    private final TestPlugin plugin;

    public PregenCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }

    public CommandTree create() {
        var command = CommandTree.literal("pregen")
                .then(CommandTree.literal("start")
                        .then(CommandTree.argument("radius", IntegerArgumentType.integer(1))
                                .executes(ctx -> startDefault(plugin, ctx))
                                .then(CommandTree.argument("centerX", IntegerArgumentType.integer())
                                        .then(CommandTree.argument("centerZ", IntegerArgumentType.integer())
                                                .executes(ctx -> startCentered(plugin, ctx))
                                        )
                                )
                        )
                )
                .then(CommandTree.literal("stop")
                        .executes(ctx -> stopCommand(plugin, ctx))
                )
                .then(CommandTree.literal("status")
                        .executes(ctx -> statusCommand(plugin, ctx))
                );

        return new CommandTree(command);
    }

    private static int startDefault(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();
        int radius = IntegerArgumentType.getInteger(ctx, "radius");

        PregenTask running = plugin.getTask();

        if (running != null && running.isRunning()) {
            msg(sender, "<red>Une pre-generation est deja en cours :</red> " + running.status());
            return Command.SINGLE_SUCCESS;
        }

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
                plugin.logger,
                cx,
                cz,
                radius,
                message -> {
                    plugin.logger.info("[Pregen] {}", message);
                    msg(sender, "<gray>[Pregen]</gray> " + message);
                }
        );

        plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int startCentered(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        int radius = IntegerArgumentType.getInteger(ctx, "radius");
        int centerX = IntegerArgumentType.getInteger(ctx, "centerX");
        int centerZ = IntegerArgumentType.getInteger(ctx, "centerZ");

        PregenTask running = plugin.getTask();

        if (running != null && running.isRunning()) {
            msg(sender, "<red>Une pre-generation est deja en cours :</red> " + running.status());
            return Command.SINGLE_SUCCESS;
        }

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
                plugin.logger,
                centerX,
                centerZ,
                radius,
                message -> {
                    plugin.logger.info("[Pregen] {}", message);
                    msg(sender, "<gray>[Pregen]</gray> " + message);
                }
        );

        plugin.setTask(task);
        task.start();

        return Command.SINGLE_SUCCESS;
    }

    private static int stopCommand(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        PregenTask task = plugin.getTask();

        if (task == null || !task.isRunning()) {
            msg(sender, "<red>Aucune pre-generation en cours.</red>");
            return Command.SINGLE_SUCCESS;
        }

        task.cancel();
        msg(sender, "Arret de la pre-generation demande.");

        return Command.SINGLE_SUCCESS;
    }

    private static int statusCommand(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        PregenTask task = plugin.getTask();

        if (task == null || !task.isRunning()) {
            msg(sender, "Aucune pre-generation en cours.");
            return Command.SINGLE_SUCCESS;
        }

        msg(sender, "Pre-generation : " + task.status());

        return Command.SINGLE_SUCCESS;
    }

    private static void msg(CommandSender sender, String message) {
        sender.sendMessage(Component.text(message));
    }
}
