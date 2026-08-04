package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import net.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

/**
 * /pardon &lt;player&gt;
 *
 * <p>The target is looked up in the ban list rather than in the profile cache, so a player who was
 * banned before the server ever saw them can still be pardoned.</p>
 */
public final class PardonCommand {

    private static final String PERMISSION = "fidorial.command.pardon";

    private static final SuggestionProvider<CommandSource> BANNED = (context, builder) -> {
        final String remaining = builder.getRemainingLowerCase();

        context.getSource().server().banList().bans()
                .map(BanEntry::label)
                .filter(label -> label.toLowerCase(Locale.ROOT).startsWith(remaining))
                .forEach(builder::suggest);

        return builder.buildFuture();
    };

    private PardonCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        return literal("pardon")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(argument("target", ArgumentTypes.word())
                        .suggests(BANNED)
                        .executes(PardonCommand::pardon))
                .build();
    }

    private static int pardon(final CommandContext<CommandSource> context) {
        final CommandSource source = context.getSource();
        final BanService bans = source.server().banList();
        final String target = context.getArgument("target", String.class);

        final Optional<BanEntry> entry = find(bans, target);

        if (entry.isEmpty()) {
            source.sender()
                    .sendMessage(Component.translatable("commands.pardon.failed", Component.text(target)));
            return 0;
        }

        bans.pardon(entry.get().uuid());

        source.sender()
                .sendMessage(Component.translatable(
                        "commands.pardon.success", Component.text(entry.get().label())));

        return Command.SINGLE_SUCCESS;
    }

    private static Optional<BanEntry> find(final BanService bans, final String target) {
        final Optional<BanEntry> byName = bans.find(target);

        if (byName.isPresent()) {
            return byName;
        }

        try {
            return bans.find(UUID.fromString(target));
        } catch (final IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }
}