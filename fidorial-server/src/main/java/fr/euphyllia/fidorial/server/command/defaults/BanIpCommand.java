package fr.euphyllia.fidorial.server.command.defaults;

import com.google.common.net.InetAddresses;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.Player;
import fr.fidorial.moderation.BanEntry;
import fr.fidorial.moderation.BanService;
import fr.fidorial.moderation.BanTarget;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Optional;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public final class BanIpCommand {

    private static final String PERMISSION = "fidorial.command.banip";
    private static final FidorialServer server = FidorialServer.getInstance();

    private static final ArgumentType<PlayerProfileListResolver> ONLINE = ArgumentTypes.playerProfiles();

    private BanIpCommand() {
    }

    public static LiteralCommandNode<CommandSource> create() {
        return literal("ban-ip")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(argument("target", ArgumentTypes.word())
                        .suggests(ONLINE::listSuggestions)
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
    ) {
        final CommandSource source = context.getSource();
        final String target = context.getArgument("target", String.class);

        final Optional<InetAddress> address = resolve(source, target);

        if (address.isEmpty()) {
            source.sender()
                    .sendMessage(Component.translatable("commands.banip.invalid", Component.text(target)));
            return 0;
        }

        final BanService bans = server.banList();
        final BanTarget banned = new BanTarget.Address(address.get());
        final String issuer = source.sender().name();

        final BanEntry entry = duration == null
                ? BanEntry.permanent(banned, null, reason, issuer)
                : BanEntry.lasting(banned, null, reason, issuer, duration);

        final boolean added = bans.ban(entry);
        final int kicked = kickBanned(entry);

        source.sender()
                .sendMessage(Component.translatable(
                        added ? "commands.banip.success" : "commands.banip.updated",
                        Component.text(entry.label()),
                        Component.text(kicked),
                        BanCommand.expiryOf(entry),
                        BanCommand.reasonOf(entry)));

        return Command.SINGLE_SUCCESS;
    }

    private static Optional<InetAddress> resolve(final CommandSource source, final String target) {
        if (InetAddresses.isInetAddress(target)) {
            return Optional.of(InetAddresses.forString(target));
        }

        return server.player(target).map(Player::address);
    }

    private static int kickBanned(final BanEntry entry) {
        int kicked = 0;

        for (final ServerPlayer player : server.players()) {
            final boolean matches = switch (entry.target()) {
                case final BanTarget.Profile profile -> player.uuid().equals(profile.uuid());
                case final BanTarget.Address address -> player.address().equals(address.address());
            };

            if (matches) {
                player.kick(server.banList().disconnectMessage(entry));
                kicked++;
            }
        }

        return kicked;
    }
}
