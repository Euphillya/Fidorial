package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.moderation.WhitelistService;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class WhitelistCommand {

    private static final String PERMISSION = "fidorial.command.whitelist";
    private static final FidorialServer server = FidorialServer.getInstance();

    private WhitelistCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        final ArgumentType<PlayerProfileListResolver> unlistedArgument =
                ArgumentTypes.playerProfiles(player -> !server.whitelist().contains(player.uuid()));

        final ArgumentType<PlayerProfileListResolver> listedArgument =
                ArgumentTypes.playerProfiles(player -> server.whitelist().contains(player.uuid()));

        return literal("whitelist")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(literal("on").executes(context -> enforce(context, true)))
                .then(literal("off").executes(context -> enforce(context, false)))
                .then(literal("add")
                        .then(argument("player", unlistedArgument)
                                .executes(WhitelistCommand::add)))
                .then(literal("remove")
                        .then(argument("player", listedArgument)
                                .executes(WhitelistCommand::remove)))
                .then(literal("list").executes(WhitelistCommand::list))
                .then(literal("reload").executes(WhitelistCommand::reload))
                .build();
    }

    private static int enforce(final CommandContext<CommandSource> context, final boolean enabled) {
        final CommandSender sender = context.getSource().sender();
        final WhitelistService whitelist = server.whitelist();

        if (!whitelist.enabled(enabled)) {
            sender.sendMessage(Component.translatable(
                    enabled ? "commands.whitelist.alreadyOn" : "commands.whitelist.alreadyOff"));
            return 0;
        }

        sender.sendMessage(Component.translatable(
                enabled ? "commands.whitelist.enabled" : "commands.whitelist.disabled"));

        kickDisallowed(sender);

        return Command.SINGLE_SUCCESS;
    }

    private static int add(final CommandContext<CommandSource> context) throws CommandSyntaxException {
        final CommandSource source = context.getSource();
        final CommandSender sender = source.sender();
        final WhitelistService whitelist = server.whitelist();

        final Collection<PlayerProfile> targets =
                context.getArgument("player", PlayerProfileListResolver.class).resolve(source);

        int added = 0;

        for (final PlayerProfile target : targets) {
            final Component name = Component.text(target.name());

            if (!whitelist.add(target.uuid(), target.name())) {
                sender.sendMessage(Component.translatable("commands.whitelist.add.failed", name));
                continue;
            }

            sender.sendMessage(Component.translatable("commands.whitelist.add.success", name));
            added++;
        }

        return added > 0 ? Command.SINGLE_SUCCESS : 0;
    }

    private static int remove(final CommandContext<CommandSource> context) throws CommandSyntaxException {
        final CommandSource source = context.getSource();
        final CommandSender sender = source.sender();
        final WhitelistService whitelist = server.whitelist();

        final Collection<PlayerProfile> targets =
                context.getArgument("player", PlayerProfileListResolver.class).resolve(source);

        int removed = 0;

        for (final PlayerProfile target : targets) {
            final Component name = Component.text(target.name());

            if (!whitelist.remove(target.uuid())) {
                sender.sendMessage(Component.translatable("commands.whitelist.remove.failed", name));
                continue;
            }

            sender.sendMessage(Component.translatable("commands.whitelist.remove.success", name));
            removed++;
        }

        if (removed > 0) {
            kickDisallowed(sender);
        }

        return removed > 0 ? Command.SINGLE_SUCCESS : 0;
    }

    private static int list(final CommandContext<CommandSource> context) {
        final CommandSender sender = context.getSource().sender();
        final WhitelistService whitelist = server.whitelist();

        final List<PlayerProfile> entries = whitelist.entries().toList();

        if (entries.isEmpty()) {
            sender.sendMessage(Component.translatable("commands.whitelist.none"));
            return Command.SINGLE_SUCCESS;
        }

        sender.sendMessage(Component.translatable(
                "commands.whitelist.list",
                Component.text(entries.size()),
                Component.text(entries.stream().map(PlayerProfile::name).reduce((a, b) -> a + ", " + b).orElse(""))));

        return Command.SINGLE_SUCCESS;
    }

    private static int reload(final CommandContext<CommandSource> context) {
        server.whitelist().load();

        context.getSource().sender().sendMessage(Component.translatable("commands.whitelist.reloaded"));

        kickDisallowed(context.getSource().sender());

        return Command.SINGLE_SUCCESS;
    }

    private static void kickDisallowed(final CommandSender sender) {
        final int kicked = enforceWhitelist();

        if (kicked > 0) {
            sender.sendMessage(Component.translatable(
                    "commands.whitelist.kicked", Component.text(kicked)));
        }
    }

    private static int enforceWhitelist() {
        if (!server.whitelist().enabled()) {
            return 0;
        }

        int kicked = 0;

        for (final ServerPlayer player : server.players()) {
            if (server.whitelist().contains(player.uuid()) || server.operators().isOp(player.uuid())) {
                continue;
            }

            player.kick(Component.translatable("multiplayer.disconnect.not_whitelisted"));
            kicked++;
        }

        return kicked;
    }
}