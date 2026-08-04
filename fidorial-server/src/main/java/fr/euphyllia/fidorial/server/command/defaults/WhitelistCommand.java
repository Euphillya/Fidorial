package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.moderation.WhitelistEntry;
import fr.fidorial.moderation.WhitelistService;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

/**
 * /whitelist on|off|add &lt;player&gt;|remove &lt;player&gt;|list|reload
 */
public final class WhitelistCommand {

    private static final String PERMISSION = "fidorial.command.whitelist";

    private static final SuggestionProvider<CommandSource> LISTED = (context, builder) -> {
        final String remaining = builder.getRemainingLowerCase();

        context.getSource().server().whitelist().entries()
                .map(WhitelistEntry::label)
                .filter(label -> label.toLowerCase(Locale.ROOT).startsWith(remaining))
                .forEach(builder::suggest);

        return builder.buildFuture();
    };

    private WhitelistCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        final ArgumentType<PlayerProfileListResolver> playerArgument = ArgumentTypes.playerProfiles();

        return literal("whitelist")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(literal("on").executes(context -> enforce(context, true)))
                .then(literal("off").executes(context -> enforce(context, false)))
                .then(literal("add")
                        .then(argument("player", playerArgument)
                                .suggests(playerArgument::listSuggestions)
                                .executes(WhitelistCommand::add)))
                .then(literal("remove")
                        .then(argument("target", ArgumentTypes.word())
                                .suggests(LISTED)
                                .executes(WhitelistCommand::remove)))
                .then(literal("list").executes(WhitelistCommand::list))
                .then(literal("reload").executes(WhitelistCommand::reload))
                .build();
    }

    private static int enforce(final CommandContext<CommandSource> context, final boolean enabled) {
        final CommandSender sender = context.getSource().sender();
        final WhitelistService whitelist = context.getSource().server().whitelist();

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
        final WhitelistService whitelist = source.server().whitelist();

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

    private static int remove(final CommandContext<CommandSource> context) {
        final CommandSource source = context.getSource();
        final CommandSender sender = source.sender();
        final WhitelistService whitelist = source.server().whitelist();

        final String target = context.getArgument("target", String.class);
        final Optional<WhitelistEntry> entry = find(whitelist, target);

        if (entry.isEmpty() || !whitelist.remove(entry.get().uuid())) {
            sender.sendMessage(Component.translatable(
                    "commands.whitelist.remove.failed", Component.text(target)));
            return 0;
        }

        sender.sendMessage(Component.translatable(
                "commands.whitelist.remove.success", Component.text(entry.get().label())));

        kickDisallowed(sender);

        return Command.SINGLE_SUCCESS;
    }

    private static int list(final CommandContext<CommandSource> context) {
        final CommandSender sender = context.getSource().sender();
        final WhitelistService whitelist = context.getSource().server().whitelist();

        final List<WhitelistEntry> entries = whitelist.entries().toList();

        if (entries.isEmpty()) {
            sender.sendMessage(Component.translatable("commands.whitelist.none"));
            return Command.SINGLE_SUCCESS;
        }

        sender.sendMessage(Component.translatable(
                "commands.whitelist.list",
                Component.text(entries.size()),
                Component.text(entries.stream().map(WhitelistEntry::label).reduce((a, b) -> a + ", " + b).orElse(""))));

        return Command.SINGLE_SUCCESS;
    }

    private static int reload(final CommandContext<CommandSource> context) {
        FidorialServer.getInstance().whitelist().load();

        context.getSource().sender().sendMessage(Component.translatable("commands.whitelist.reloaded"));

        kickDisallowed(context.getSource().sender());

        return Command.SINGLE_SUCCESS;
    }

    private static void kickDisallowed(final CommandSender sender) {
        final int kicked = FidorialServer.getInstance().enforceWhitelist();

        if (kicked > 0) {
            sender.sendMessage(Component.translatable(
                    "commands.whitelist.kicked", Component.text(kicked)));
        }
    }

    private static Optional<WhitelistEntry> find(final WhitelistService whitelist, final String target) {
        final Optional<WhitelistEntry> byName = whitelist.find(target);

        if (byName.isPresent()) {
            return byName;
        }

        try {
            final UUID uuid = UUID.fromString(target);

            return whitelist.entries()
                    .filter(entry -> entry.uuid().equals(uuid))
                    .findFirst();
        } catch (final IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }
}