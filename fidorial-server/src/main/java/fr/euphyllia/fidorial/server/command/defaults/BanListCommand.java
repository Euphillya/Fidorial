package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import net.kyori.adventure.text.Component;

import java.util.List;

import static fr.fidorial.command.Commands.literal;

/**
 * /banlist
 */
public final class BanListCommand {

    private static final String PERMISSION = "fidorial.command.banlist";

    private BanListCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        return literal("banlist")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .executes(BanListCommand::list)
                .build();
    }

    private static int list(final CommandContext<CommandSource> context) {
        final CommandSender sender = context.getSource().sender();
        final BanService bans = context.getSource().server().banList();

        final List<BanEntry> entries = bans.bans().toList();

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