package fr.euphyllia.fidorial.server.command;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.command.brigadier.InternalCommandMeta;
import fr.euphyllia.fidorial.server.command.defaults.GameModeCommand;
import fr.euphyllia.fidorial.server.command.defaults.OpCommand;
import fr.euphyllia.fidorial.server.command.defaults.StopCommand;
import fr.euphyllia.fidorial.server.command.defaults.SummonCommand;
import fr.euphyllia.fidorial.server.command.defaults.TpsCommand;
import fr.euphyllia.fidorial.server.command.defaults.WeatherCommand;
import fr.fidorial.command.CommandMeta;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.convert;
import static fr.euphyllia.fidorial.server.command.brigadier.argument.builtin.TranslatableExceptions.DISPATCHER_UNKNOWN_ARGUMENT;
import static fr.euphyllia.fidorial.server.command.brigadier.argument.builtin.TranslatableExceptions.DISPATCHER_UNKNOWN_COMMAND;

public final class CommandManager implements CommandRegistry {
    private final @GuardedBy("lock") CommandDispatcher<CommandSource> dispatcher;
    private final ReadWriteLock lock;
    private final Map<String, CommandMeta> metaByAlias = new ConcurrentHashMap<>();
    private final Map<String, RegisteredCommand> commands = new ConcurrentHashMap<>();

    public CommandManager() {
        this.lock = new ReentrantReadWriteLock();
        this.dispatcher = new CommandDispatcher<>();

        registerDefaults();
    }

    private void registerDefaults() {
        register(
                metaBuilder("weather")
                        .aliases("w")
                        .description(Component.translatable("command.weather.description"))
                        .usage(Component.text("/weather <clear|rain|thunder>"))
                        .build(),
                WeatherCommand.create());
        register(metaBuilder("stop").aliases("s").build(), StopCommand.create());
        register(metaBuilder("op").build(), OpCommand.createOp());
        register(metaBuilder("deop").build(), OpCommand.createDeop());
        register(metaBuilder("summon").build(), SummonCommand.create());
        register(metaBuilder("gamemode").aliases("gm").build(), GameModeCommand.create());
        register(metaBuilder("tps").build(), TpsCommand.create());
    }

    @Override
    public CommandMeta.Builder metaBuilder(String alias) {
        Preconditions.checkNotNull(alias, "alias");
        return new InternalCommandMeta.Builder(alias);
    }

    @Override
    public CommandMeta.Builder metaBuilder(CommandTree command) {
        Preconditions.checkNotNull(command, "command");
        return new InternalCommandMeta.Builder(command.node().getName());
    }

    @Override
    public void register(CommandMeta meta, CommandTree command) {
        lock.writeLock().lock();
        try {
            RegisteredCommand registered = new RegisteredCommand(command, meta);

            for (String alias : meta.aliases()) {
                metaByAlias.put(alias.toLowerCase(Locale.ROOT), meta);
                commands.put(alias.toLowerCase(Locale.ROOT), registered);

                CommandNode<CommandSource> node;

                if (alias.equalsIgnoreCase(command.node().getName())) {
                    node = command.node();
                } else {
                    node = cloneLiteral(alias, command.node());
                }
                dispatcher.getRoot().addChild(node);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static LiteralCommandNode<CommandSource> cloneLiteral(
            String name,
            LiteralCommandNode<CommandSource> original
    ) {
        LiteralArgumentBuilder<CommandSource> builder =
                LiteralArgumentBuilder.<CommandSource>literal(name).requires(original.getRequirement());

        if (original.getCommand() != null) {
            builder.executes(original.getCommand());
        }

        for (CommandNode<CommandSource> child : original.getChildren()) {
            builder.then(cloneNode(child));
        }

        return builder.build();
    }

    private static <S> CommandNode<S> cloneNode(CommandNode<S> node) {
        ArgumentBuilder<S, ?> builder = node.createBuilder();

        for (CommandNode<S> child : node.getChildren()) {
            builder.then(cloneNode(child));
        }

        return builder.build();
    }

    @Override
    public void unregister(String alias) {
        Preconditions.checkNotNull(alias, "alias");
        lock.writeLock().lock();
        try {
            metaByAlias.remove(alias.toLowerCase(Locale.ROOT));
            commands.remove(alias.toLowerCase(Locale.ROOT));
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void unregister(CommandMeta meta) {
        Preconditions.checkNotNull(meta, "meta");
        lock.writeLock().lock();
        try {
            for (String alias : meta.aliases()) {
                metaByAlias.remove(alias.toLowerCase(Locale.ROOT));
                commands.remove(alias.toLowerCase(Locale.ROOT));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public CommandMeta commandMeta(String alias) {
        return metaByAlias.get(alias.toLowerCase(Locale.ROOT));
    }

    @Override
    public CompletableFuture<Boolean> dispatchAsync(CommandSource source, String cmdLine) {
        return CompletableFuture.supplyAsync(() -> {
            ParseResults<CommandSource> parse = dispatcher.parse(cmdLine, source);

            CommandSyntaxException exception = getParseException(parse);

            if (exception != null) {
                source.sender()
                        .sendMessage(convert(
                                        exception.getRawMessage(),
                                        source.sender().isConsole())
                                .color(NamedTextColor.RED));
                sendContext(source, exception, cmdLine, source.sender().isConsole());
                return false;
            }

            try {
                // dirty cuz vanilla brigadier doesnt allow child removal wtf
                // also we do this here so real invalid entries fail in the console
                String root = cmdLine.strip();

                int space = root.indexOf(' ');
                if (space != -1) {
                    root = root.substring(0, space);
                }

                root = root.toLowerCase(Locale.ROOT);

                if (!metaByAlias.containsKey(root)) {
                    CommandSyntaxException e = unknownCommand(parse);
                    source.sender()
                            .sendMessage(
                                    convert(e.getRawMessage(), source.sender().isConsole())
                                            .color(NamedTextColor.RED));
                    sendContext(source, e, cmdLine, source.sender().isConsole());
                    return false;
                }

                int result = dispatcher.execute(parse);
                return result == 1;
            } catch (CommandSyntaxException e) {
                source.sender()
                        .sendMessage(convert(e.getRawMessage(), source.sender().isConsole())
                                .color(NamedTextColor.RED));
                return false;
            }
        });
    }

    private static boolean hasExecutableNode(ParseResults<CommandSource> parse) {
        return parse.getContext().getNodes().stream()
                .anyMatch(node -> node.getNode().getCommand() != null);
    }

    private static @Nullable CommandSyntaxException getParseException(ParseResults<CommandSource> parse) {
        if (!parse.getExceptions().isEmpty()) {
            return parse.getExceptions().values().iterator().next();
        }

        if (parse.getContext().getNodes().isEmpty()) {
            return unknownCommand(parse);
        }

        if (parse.getReader().canRead()) {
            return DISPATCHER_UNKNOWN_ARGUMENT.createWithContext(parse.getReader());
        }

        if (!hasExecutableNode(parse)) {
            return DISPATCHER_UNKNOWN_COMMAND.createWithContext(parse.getReader());
        }

        return null;
    }

    private static CommandSyntaxException unknownCommand(ParseResults<CommandSource> parse) {
        return DISPATCHER_UNKNOWN_COMMAND.createWithContext(parse.getReader());
    }

    private void sendContext(
            CommandSource source,
            CommandSyntaxException exception,
            String command,
            boolean isConsole
    ) {

        if (exception.getInput() == null || exception.getCursor() < 0) {
            return;
        }

        int cursor = Math.min(exception.getInput().length(), exception.getCursor());

        Component context =
                Component.empty().color(NamedTextColor.GRAY).clickEvent(ClickEvent.suggestCommand("/" + command));

        if (cursor > 10) {
            context = context.append(Component.text("..."));
        }

        int start = Math.max(0, cursor - 10);

        context = context.append(Component.text(exception.getInput().substring(start, cursor)));

        if (cursor < exception.getInput().length()) {
            context = context.append(Component.text(exception.getInput().substring(cursor))
                    .color(NamedTextColor.RED)
                    .decorate(TextDecoration.UNDERLINED));
        }

        context = context.append(
                isConsole
                        ? Component.translatable("console.command.context.here")
                        : Component.translatable("command.context.here")
                                .color(NamedTextColor.RED)
                                .decorate(TextDecoration.ITALIC));

        source.sender().sendMessage(context);
    }

    @Override
    public CompletableFuture<List<String>> offerSuggestions(CommandSource source, String cmdLine) {
        return offerBrigadierSuggestions(source, cmdLine)
                .thenApply(suggestions -> Lists.transform(
                        suggestions.getList(), suggestion -> suggestion != null ? suggestion.getText() : null));
    }

    @Override
    public CompletableFuture<Suggestions> offerBrigadierSuggestions(CommandSource source, String cmdLine) {
        ParseResults<CommandSource> parse = dispatcher.parse(cmdLine, source);
        return dispatcher.getCompletionSuggestions(parse);
    }

    @Override
    public boolean hasCommand(String alias) {
        return metaByAlias.containsKey(alias.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean hasCommand(String alias, CommandSource source) {
        CommandNode<CommandSource> node = dispatcher.getRoot().getChild(alias.toLowerCase(Locale.ROOT));
        return node != null && node.canUse(source);
    }

    @Override
    public Collection<String> aliases() {
        return Collections.unmodifiableSet(metaByAlias.keySet());
    }

    public CommandDispatcher<CommandSource> dispatcher() {
        return dispatcher;
    }

    public record RegisteredCommand(CommandTree tree, CommandMeta meta) {
    }
}
