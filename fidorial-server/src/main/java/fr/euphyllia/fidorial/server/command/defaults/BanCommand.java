package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import fr.fidorial.moderation.BanTarget;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.Collection;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class BanCommand {

    private static final String PERMISSION = "fidorial.command.ban";
    private static final FidorialServer server = FidorialServer.getInstance();

    private BanCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        final ArgumentType<PlayerProfileListResolver> playerArgument =
                ArgumentTypes.playerProfiles(player ->
                        !server.banList().isBanned(new BanTarget.Profile(player.uuid())));

        return literal("ban")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(argument("player", playerArgument)
                        .executes(context -> ban(context, null, null))
                        .then(argument("duration", ArgumentTypes.duration())
                                .executes(context -> ban(context, duration(context), null))
                                .then(argument("reason", ArgumentTypes.component())
                                        .executes(context -> ban(context, duration(context), reason(context)))))
                        .then(argument("reason", ArgumentTypes.component())
                                .executes(context -> ban(context, null, reason(context)))))
                .build();
    }

    private static Duration duration(final CommandContext<CommandSource> context) {
        return context.getArgument("duration", Duration.class);
    }

    private static Component reason(final CommandContext<CommandSource> context) {
        return context.getArgument("reason", Component.class);
    }

    private static int ban(
            final CommandContext<CommandSource> context,
            @Nullable final Duration duration,
            @Nullable final Component reason
    ) throws CommandSyntaxException {

        final CommandSource source = context.getSource();
        final BanService bans = server.banList();

        final Collection<PlayerProfile> targets =
                context.getArgument("player", PlayerProfileListResolver.class).resolve(source);

        final String issuer = source.sender().name();

        int banned = 0;

        for (final PlayerProfile target : targets) {
            final BanTarget banTarget = new BanTarget.Profile(target.uuid());

            final BanEntry entry = duration == null
                    ? BanEntry.permanent(banTarget, target.name(), reason, issuer)
                    : BanEntry.lasting(banTarget, target.name(), reason, issuer, duration);

            final boolean added = bans.ban(entry);

            kickBanned(entry);

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

    static Component expiryOf(final BanEntry entry) {
        return entry.permanent()
                ? Component.translatable("commands.ban.expires.never")
                : Component.text(entry.expiresLabel());
    }

    static Component reasonOf(final BanEntry entry) {
        return entry.describeReason().orElseGet(() -> Component.translatable("commands.ban.reason.none"));
    }

    private static int kickBanned(final BanEntry entry) {
        int kicked = 0;

        for (final ServerPlayer player : server.players()) {
            final boolean matches = switch (entry.target()) {
                case final BanTarget.Profile profile -> player.uuid().equals(profile.uuid());
                case final BanTarget.Address address -> player.address().filter(address.address()::equals).isPresent();
            };

            if (matches) {
                player.kick(server.banList().disconnectMessage(entry));
                kicked++;
            }
        }

        return kicked;
    }
}