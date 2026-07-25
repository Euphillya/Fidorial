package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.euphyllia.fidorial.testplugin.CounterService;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.entity.Player;
import fr.fidorial.scheduler.RegionTps;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static fr.fidorial.command.CommandTree.argument;
import static fr.fidorial.command.CommandTree.literal;

public final class ApiTestCommand {

    private final TestPlugin plugin;

    public ApiTestCommand(final TestPlugin plugin) {
        this.plugin = plugin;
    }

    public CommandTree create() {
        final var command = literal("apitest")
                .then(literal("info").executes(ctx -> info(plugin, ctx)))
                .then(literal("tps").executes(ctx -> tps(plugin, ctx)))
                .then(literal("worlds").executes(ctx -> worlds(plugin, ctx)))
                .then(literal("players").executes(ctx -> players(plugin, ctx)))
                .then(literal("service").executes(ctx -> service(plugin, ctx)))
                .then(literal("schedule").executes(ctx -> schedule(plugin, ctx)))
                .then(literal("perms").executes(ApiTestCommand::perms))
                .then(literal("sound")
                        .executes(ApiTestCommand::soundDemo)
                        .then(argument("key", ArgumentTypes.key())
                                .executes(ApiTestCommand::soundDefault)
                                .then(argument("volume", ArgumentTypes.floatArg())
                                        .executes(ApiTestCommand::soundVolume)
                                        .then(argument("pitch", ArgumentTypes.floatArg())
                                                .executes(ApiTestCommand::soundPitch)))))
                .then(literal("stopsound")
                        .executes(ApiTestCommand::stopAllSound)
                        .then(argument("key", ArgumentTypes.key()).executes(ApiTestCommand::stopSound)))
                .build();

        return new CommandTree(command);
    }

    private static int soundDefault(final CommandContext<CommandSource> ctx) {
        return playSound(ctx, 1.0f, 1.0f);
    }

    private static int soundVolume(final CommandContext<CommandSource> ctx) {
        final Float volume = ctx.getArgument("volume", Float.class);

        return playSound(ctx, volume, 1.0f);
    }

    private static int soundPitch(final CommandContext<CommandSource> ctx) {
        final Float volume = ctx.getArgument("volume", Float.class);
        final Float pitch = ctx.getArgument("pitch", Float.class);

        return playSound(ctx, volume, pitch);
    }

    private static int playSound(final CommandContext<CommandSource> ctx, final float volume, final float pitch) {
        final CommandSender sender = ctx.getSource().sender();

        if (!(sender instanceof final Player player)) {
            msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final Key key = ctx.getArgument("key", Key.class);

        player.playSound(Sound.sound(key, Sound.Source.MASTER, volume, pitch));

        msg(player, "[TestPlugin] Played sound " + key + " (volume=" + volume + ", pitch=" + pitch + ")");

        return Command.SINGLE_SUCCESS;
    }

    private static int soundDemo(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        if (!(sender instanceof final Player player)) {
            msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        player.playSound(Sound.sound(Key.key("minecraft", "entity.player.levelup"), Sound.Source.PLAYER, 1.0f, 1.0f));

        player.playSound(
                Sound.sound(Key.key("minecraft", "entity.experience_orb.pickup"), Sound.Source.MASTER, 0.8f, 1.4f),
                Sound.Emitter.self());

        player.playSound(
                Sound.sound(Key.key("minecraft", "block.bell.use"), Sound.Source.BLOCK, 1.0f, 0.8f), 0.0, 64.0, 0.0);

        msg(player, "[TestPlugin] Sound demo executed.");

        return Command.SINGLE_SUCCESS;
    }

    private static int stopSound(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        if (!(sender instanceof final Player player)) {
            msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final Key key = ctx.getArgument("key", Key.class);

        player.stopSound(SoundStop.named(key));

        msg(player, "[TestPlugin] Stopped sound " + key);

        return Command.SINGLE_SUCCESS;
    }

    private static int stopAllSound(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        if (!(sender instanceof final Player player)) {
            msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        player.stopSound(SoundStop.all());

        msg(player, "[TestPlugin] Stopped all sounds.");

        return Command.SINGLE_SUCCESS;
    }

    private static int info(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        msg(
                sender,
                "[TestPlugin] MC " + plugin.server().minecraftVersion()
                        + " | protocole " + plugin.server().protocolVersion()
                        + " | running=" + plugin.server().isRunning()
                        + " | plugins=" + plugin.server().plugins().loaded().size()
                        + " | events=" + plugin.eventCount());

        return Command.SINGLE_SUCCESS;
    }

    private static int tps(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final List<? extends RegionTps> snapshots = plugin.server().scheduler().tpsSnapshots();

        if (snapshots.isEmpty()) {
            msg(sender, "[TestPlugin] Aucune region active.");
            return Command.SINGLE_SUCCESS;
        }

        for (final RegionTps tps : snapshots) {
            msg(
                    sender,
                    String.format(
                            Locale.ROOT,
                            "[TestPlugin] %s section(%d,%d) tps=%.1f mspt=%.2f queued=%d",
                            tps.world(),
                            tps.sectionX(),
                            tps.sectionZ(),
                            tps.tps(),
                            tps.msptAvg(),
                            tps.queuedTasks()));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int worlds(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final String worlds =
                plugin.server().worlds().stream().map(w -> w.key().toString()).collect(Collectors.joining(", "));

        msg(sender, "[TestPlugin] " + plugin.server().worlds().size() + " monde(s): " + worlds);

        return Command.SINGLE_SUCCESS;
    }

    private static int players(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final var players = plugin.server().onlinePlayers();

        msg(
                sender,
                "[TestPlugin] "
                        + players.size()
                        + " joueur(s): "
                        + players.stream().map(Player::name).collect(Collectors.joining(", ")));

        return Command.SINGLE_SUCCESS;
    }

    private static int service(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final var service = plugin.server().services().find(CounterService.class);

        if (service.isEmpty()) {
            msg(sender, "<red>CounterService introuvable.</red>");
            return Command.SINGLE_SUCCESS;
        }

        msg(sender, "[TestPlugin] compteur = " + service.get().increment());

        return Command.SINGLE_SUCCESS;
    }

    private static int schedule(final TestPlugin plugin, final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        final World world = plugin.server().worlds().stream().findFirst().orElse(null);

        if (world == null) {
            msg(sender, "[TestPlugin] Aucun monde.");
            return Command.SINGLE_SUCCESS;
        }

        plugin.server()
                .scheduler()
                .executeDelayed(
                        world.key(), new ChunkPos(0, 0), () -> msg(sender, "[TestPlugin] Scheduler OK"), 40L);

        return Command.SINGLE_SUCCESS;
    }

    private static int perms(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();

        msg(
                sender,
                sender.name()
                        + " | console=" + sender.name().equals("Console")
                        + " | testplugin.use=" + sender.hasPermission("testplugin.use")
                        + " | testplugin.admin=" + sender.hasPermission("testplugin.admin"));

        return Command.SINGLE_SUCCESS;
    }

    private static void msg(final CommandSender sender, final String message) {
        sender.sendMessage(Component.text(message));
    }
}
