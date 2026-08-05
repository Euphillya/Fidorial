package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import fr.fidorial.moderation.BanTarget;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static fr.fidorial.command.Commands.literal;

public final class BanListCommand {

    private static final String PERMISSION = "fidorial.command.banlist";

    private BanListCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        return literal("banlist")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .executes(context -> list(context, null))
                .then(literal("players").executes(context -> list(context, BanTarget.Profile.class)))
                .then(literal("ips").executes(context -> list(context, BanTarget.Address.class)))
                .build();
    }

    private static int list(
            final CommandContext<CommandSource> context,
            @Nullable final Class<? extends BanTarget> kind
    ) {
        final CommandSender sender = context.getSource().sender();
        final BanService bans = context.getSource().server().banList();

        final List<BanEntry> entries = (kind == null ? bans.bans() : bans.bans(kind)).toList();

        if (entries.isEmpty()) {
            sender.sendMessage(Component.translatable("commands.banlist.none"));
            return Command.SINGLE_SUCCESS;
        }

        sender.sendMessage(Component.translatable(
                "commands.banlist.header", Component.text(entries.size())));

        for (final BanEntry entry : entries) {
            sender.sendMessage(Component.translatable(
                    "commands.banlist.entry",
                    Component.text(entry.label()),
                    Component.text(entry.source() == null ? "-" : entry.source()),
                    BanCommand.expiryOf(entry),
                    BanCommand.reasonOf(entry)));
        }

        return Command.SINGLE_SUCCESS;
    }
}