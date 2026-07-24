package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.entity.Player;
import fr.fidorial.entity.PlayerProfile;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;

import static fr.fidorial.command.CommandTree.argument;
import static fr.fidorial.command.CommandTree.literal;

public final class OpCommand {

    private OpCommand() {
    }

    public static LiteralCommandNode<CommandSource> createOp() {
        return create("op", true);
    }

    public static LiteralCommandNode<CommandSource> createDeop() {
        return create("deop", false);
    }

    private static LiteralCommandNode<CommandSource> create(String name, boolean grant) {
        return literal(name)
                .requires(source ->
                        source.sender().hasPermission(grant ? "fidorial.command.op" : "fidorial.command.deop"))
                .then(argument("player", ArgumentTypes.playerProfiles())
                        .suggests((ctx, builder) -> ArgumentTypes.playerProfiles()
                                .listSuggestions(ctx, builder)
                                .thenApply(suggestions -> new Suggestions(
                                        suggestions.getRange(),
                                        suggestions.getList().stream()
                                                .filter(suggestion -> ctx.getSource().server().onlinePlayers().stream()
                                                        .filter(player ->
                                                                player.name().equalsIgnoreCase(suggestion.getText()))
                                                        .anyMatch(player -> player.isOp() != grant))
                                                .toList())))
                        .executes(context -> execute(context, grant))).build();
    }

    private static int execute(CommandContext<CommandSource> context, boolean grant) throws CommandSyntaxException {

        PlayerProfileListResolver resolver = context.getArgument("player", PlayerProfileListResolver.class);
        Collection<PlayerProfile> targets = resolver.resolve(context.getSource());

        for (PlayerProfile targetProfile : targets) {
            final List<? extends Player> players = context.getSource().server().onlinePlayers().stream()
                    .filter(player -> player.uuid().equals(targetProfile.uuid()))
                    .toList();

            if (players.isEmpty()) {
                continue;
            }

            for (Player target : players) {

                target.setOp(grant);

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
