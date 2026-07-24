package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.Player;
import fr.fidorial.entity.PlayerProfile;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;

public final class OpCommand {

    private OpCommand() {
    }

    public static CommandTree createOp() {
        return create("op", true);
    }

    public static CommandTree createDeop() {
        return create("deop", false);
    }

    private static CommandTree create(final String name, final boolean grant) {
        return new CommandTree(CommandTree.literal(name)
                .requires(source ->
                        source.sender().hasPermission(grant ? "fidorial.command.op" : "fidorial.command.deop"))
                .then(CommandTree.argument("player", ArgumentTypes.playerProfiles())
                        .suggests((ctx, builder) -> ArgumentTypes.playerProfiles()
                                .listSuggestions(ctx, builder)
                                .thenApply(suggestions -> new Suggestions(
                                        suggestions.getRange(),
                                        suggestions.getList().stream()
                                                .filter(suggestion -> ctx.getSource().server().onlinePlayers().stream()
                                                        .filter(player ->
                                                                player.name().equalsIgnoreCase(suggestion.getText()))
                                                        .anyMatch(player -> player.isOperator() != grant))
                                                .toList())))
                        .executes(context -> execute(context, grant))));
    }

    private static int execute(final CommandContext<CommandSource> context, final boolean grant) throws CommandSyntaxException {

        final PlayerProfileListResolver resolver = context.getArgument("player", PlayerProfileListResolver.class);
        final Collection<PlayerProfile> targets = resolver.resolve(context.getSource());

        for (final PlayerProfile targetProfile : targets) {
            final List<? extends Player> players = context.getSource().server().onlinePlayers().stream()
                    .filter(player -> player.uuid().equals(targetProfile.uuid()))
                    .toList();

            if (players.isEmpty()) {
                continue;
            }

            for (final Player target : players) {

                target.setOperator(grant);

                target.sendMessage(
                        Component.translatable(grant ? "command.op.granted.self" : "command.op.revoked.self"));

                context.getSource()
                        .sender()
                        .sendMessage(Component.translatable(
                                grant ? "command.op.granted.other" : "command.op.revoked.other",
                                Component.text(target.name())));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
