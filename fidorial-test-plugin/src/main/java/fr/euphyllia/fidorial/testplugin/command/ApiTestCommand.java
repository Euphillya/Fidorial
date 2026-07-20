package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;import com.mojang.brigadier.context.CommandContext;
import fr.euphyllia.fidorial.testplugin.CounterService;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
import fr.fidorial.scheduler.RegionTps;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.World;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class ApiTestCommand {

    private final TestPlugin plugin;

    public ApiTestCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }

    public CommandTree create() {
        var command = CommandTree.literal("apitest")
                .then(literal("info").executes(ctx -> info(plugin, ctx)))
                .then(literal("tps").executes(ctx -> tps(plugin, ctx)))
                .then(literal("worlds").executes(ctx -> worlds(plugin, ctx)))
                .then(literal("players").executes(ctx -> players(plugin, ctx)))
                .then(literal("service").executes(ctx -> service(plugin, ctx)))
                .then(literal("schedule").executes(ctx -> schedule(plugin, ctx)))
                .then(literal("perms").executes(ApiTestCommand::perms))
                .build();

        return new CommandTree(command);
    }

    private static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return CommandTree.literal(name);
    }

    private static int info(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        msg(sender,
                "[TestPlugin] MC " + plugin.server().minecraftVersion()
                        + " | protocole " + plugin.server().protocolVersion()
                        + " | running=" + plugin.server().isRunning()
                        + " | plugins=" + plugin.server().plugins().loaded().size()
                        + " | events=" + plugin.eventCount()
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int tps(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        List<? extends RegionTps> snapshots = plugin.server()
                .scheduler()
                .tpsSnapshots();

        if (snapshots.isEmpty()) {
            msg(sender, "[TestPlugin] Aucune region active.");
            return Command.SINGLE_SUCCESS;
        }

        for (RegionTps tps : snapshots) {
            msg(sender, String.format(
                    Locale.ROOT,
                    "[TestPlugin] %s section(%d,%d) tps=%.1f mspt=%.2f queued=%d",
                    tps.world(),
                    tps.sectionX(),
                    tps.sectionZ(),
                    tps.tps(),
                    tps.msptAvg(),
                    tps.queuedTasks()
            ));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int worlds(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        String worlds = plugin.server().worlds().stream()
                .map(w -> w.key().toString())
                .collect(Collectors.joining(", "));

        msg(sender,
                "[TestPlugin] "
                        + plugin.server().worlds().size()
                        + " monde(s): "
                        + worlds
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int players(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        var players = plugin.server().onlinePlayers();

        msg(sender,
                "[TestPlugin] "
                        + players.size()
                        + " joueur(s): "
                        + players.stream()
                        .map(Player::name)
                        .collect(Collectors.joining(", "))
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int service(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        var service = plugin.server()
                .services()
                .find(CounterService.class);

        if (service.isEmpty()) {
            msg(sender, "<red>CounterService introuvable.</red>");
            return Command.SINGLE_SUCCESS;
        }

        msg(sender,
                "[TestPlugin] compteur = "
                        + service.get().increment()
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int schedule(TestPlugin plugin, CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        World world = plugin.server()
                .worlds()
                .stream()
                .findFirst()
                .orElse(null);

        if (world == null) {
            msg(sender, "[TestPlugin] Aucun monde.");
            return Command.SINGLE_SUCCESS;
        }

        plugin.server().scheduler().executeDelayed(
                world.key().value(),
                new ChunkPos(0, 0),
                () -> msg(sender, "[TestPlugin] Scheduler OK"),
                40L
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int perms(CommandContext<CommandSource> ctx) {
        CommandSender sender = ctx.getSource().sender();

        msg(sender,
                sender.name()
                        + " | console=" + sender.isConsole()
                        + " | testplugin.use=" + sender.hasPermission("testplugin.use")
                        + " | testplugin.admin=" + sender.hasPermission("testplugin.admin")
        );

        return Command.SINGLE_SUCCESS;
    }

    private static void msg(CommandSender sender, String message) {
        sender.sendMessage(Component.text(message));
    }
}
