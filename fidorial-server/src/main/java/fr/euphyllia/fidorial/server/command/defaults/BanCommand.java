package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.Collection;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

/**
 * /ban &lt;player&gt; [&lt;duration&gt;] [&lt;reason...&gt;]
 *
 * <p>Without a duration the ban is permanent. The duration is read before the reason, so a reason
 * that starts with something like {@code 7d} is taken as the duration; the feedback always states
 * when the ban lifts, so that is visible straight away.</p>
 */
public final class BanCommand {

    private static final String PERMISSION = "fidorial.command.ban";

    private BanCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        final ArgumentType<PlayerProfileListResolver> playerArgument = ArgumentTypes.playerProfiles();

        return literal("ban")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(argument("player", playerArgument)
                        .suggests(playerArgument::listSuggestions)
                        .executes(context -> ban(context, null, null))
                        .then(argument("duration", ArgumentTypes.duration())
                                .executes(context -> ban(context, duration(context), null))
                                .then(argument("reason", ArgumentTypes.greedyString())
                                        .executes(context -> ban(context, duration(context), reason(context)))))
                        .then(argument("reason", ArgumentTypes.greedyString())
                                .executes(context -> ban(context, null, reason(context)))))
                .build();
    }

    private static Duration duration(final CommandContext<CommandSource> context) {
        return context.getArgument("duration", Duration.class);
    }

    private static String reason(final CommandContext<CommandSource> context) {
        return context.getArgument("reason", String.class);
    }

    private static int ban(
            final CommandContext<CommandSource> context,
            @Nullable final Duration duration,
            @Nullable final String reason
    ) throws CommandSyntaxException {

        final CommandSource source = context.getSource();
        final BanService bans = source.server().banList();

        final Collection<PlayerProfile> targets =
                context.getArgument("player", PlayerProfileListResolver.class).resolve(source);

        final Component message = reason == null ? null : Component.text(reason);
        final String issuer = source.sender().name();

        int banned = 0;

        for (final PlayerProfile target : targets) {
            final BanEntry entry = duration == null
                    ? BanEntry.permanent(target.uuid(), target.name(), message, issuer)
                    : BanEntry.lasting(target.uuid(), target.name(), message, issuer, duration);

            final boolean added = bans.ban(entry);

            source.server()
                    .player(target.uuid())
                    .ifPresent(player -> player.kick(bans.disconnectMessage(entry)));

            source.sender()
                    .sendMessage(Component.translatable(
                            added ? "commands.ban.success" : "commands.ban.updated",
                            Component.text(entry.label()),
                            expiryOf(entry),
                            reasonOf(entry)));

            banned++;
        }

        return banned > 0 ? Command.SINGLE_SUCCESS : 0;
    }

    /**
     * Describes when a ban lifts, in a form suitable for chat.
     *
     * @param entry the ban
     * @return the expiry date, or a label saying the ban never lifts
     */
    static Component expiryOf(final BanEntry entry) {
        return entry.permanent()
                ? Component.translatable("commands.ban.expires.never")
                : Component.text(entry.expiresLabel());
    }

    /**
     * Describes why a player was banned, in a form suitable for chat.
     *
     * @param entry the ban
     * @return the reason, or a label saying none was given
     */
    static Component reasonOf(final BanEntry entry) {
        return entry.describeReason().orElseGet(() -> Component.translatable("commands.ban.reason.none"));
    }
}