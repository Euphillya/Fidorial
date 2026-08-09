package fr.euphyllia.fidorial.server.command;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.builtin.exceptions.TranslatableExceptions;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypes;
import fr.euphyllia.fidorial.server.command.defaults.BanCommand;
import fr.euphyllia.fidorial.server.command.defaults.BanIpCommand;
import fr.euphyllia.fidorial.server.command.defaults.BanListCommand;
import fr.euphyllia.fidorial.server.command.defaults.BossBarCommand;
import fr.euphyllia.fidorial.server.command.defaults.GameModeCommand;
import fr.euphyllia.fidorial.server.command.defaults.OpCommand;
import fr.euphyllia.fidorial.server.command.defaults.PardonCommand;
import fr.euphyllia.fidorial.server.command.defaults.PardonIpCommand;
import fr.euphyllia.fidorial.server.command.defaults.StopCommand;
import fr.euphyllia.fidorial.server.command.defaults.SummonCommand;
import fr.euphyllia.fidorial.server.command.defaults.TimeCommand;
import fr.euphyllia.fidorial.server.command.defaults.TpsCommand;
import fr.euphyllia.fidorial.server.command.defaults.WeatherCommand;
import fr.euphyllia.fidorial.server.command.defaults.WhitelistCommand;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundCommandsPacket;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.convert;
import static fr.euphyllia.fidorial.server.command.brigadier.builtin.exceptions.TranslatableExceptions.DISPATCHER_UNKNOWN_ARGUMENT;
import static fr.euphyllia.fidorial.server.command.brigadier.builtin.exceptions.TranslatableExceptions.DISPATCHER_UNKNOWN_COMMAND;

public final class CommandManager implements CommandRegistry {
    private final @GuardedBy("lock") CommandDispatcher<CommandSource> dispatcher;
    private final ReadWriteLock lock;
    private final Map<String, RegisteredCommand> commands = new ConcurrentHashMap<>();
    private final ExecutorService commandExecutor =
            Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("fidorial-command-worker-", 0).factory());

    public CommandManager() {
        this.lock = new ReentrantReadWriteLock();
        this.dispatcher = new CommandDispatcher<>();

        CommandSyntaxException.BUILT_IN_EXCEPTIONS = new TranslatableExceptions();
        ArgumentTypes.bootstrap();

        registerDefaults();
    }

    private void registerDefaults() {
        register(WeatherCommand.create(), Set.of("w"));
        register(StopCommand.create(), Set.of("s"));
        register(OpCommand.createOp());
        register(OpCommand.createDeop());
        register(SummonCommand.create());
        register(GameModeCommand.create(), Set.of("gm"));
        register(TpsCommand.create());
        register(TimeCommand.create());
        register(BossBarCommand.create());
        register(BanCommand.create());
        register(PardonCommand.create(), Set.of("unban"));
        register(BanListCommand.create());
        register(WhitelistCommand.create());
        register(BanIpCommand.create(), Set.of("banip"));
        register(PardonIpCommand.create(), Set.of("unban-ip", "pardonip"));
    }

    @Override
    public void register(final LiteralCommandNode<CommandSource> command, final Set<String> aliases) {
        lock.writeLock().lock();
        try {
            final RegisteredCommand registered = new RegisteredCommand(command);

            // Always register the primary name as well if no aliases were provided
            final Set<String> names = new HashSet<>(aliases);
            names.add(command.getName());

            for (final String alias : names) {
                final String key = alias.toLowerCase(Locale.ROOT);

                commands.put(key, registered);

                final CommandNode<CommandSource> node =
                        alias.equalsIgnoreCase(command.getName())
                                ? command
                                : cloneLiteral(alias, command);

                dispatcher.getRoot().addChild(node);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static LiteralCommandNode<CommandSource> cloneLiteral(
            final String name,
            final LiteralCommandNode<CommandSource> original
    ) {
        final LiteralArgumentBuilder<CommandSource> builder =
                LiteralArgumentBuilder.<CommandSource>literal(name).requires(original.getRequirement());

        if (original.getCommand() != null) {
            builder.executes(original.getCommand());
        }

        for (final CommandNode<CommandSource> child : original.getChildren()) {
            builder.then(cloneNode(child));
        }

        return builder.build();
    }

    private static <S> CommandNode<S> cloneNode(final CommandNode<S> node) {
        final ArgumentBuilder<S, ?> builder = node.createBuilder();

        for (final CommandNode<S> child : node.getChildren()) {
            builder.then(cloneNode(child));
        }

        return builder.build();
    }

    @Override
    public void unregister(final String alias) {
        Preconditions.checkNotNull(alias, "alias");
        lock.writeLock().lock();
        try {
            commands.remove(alias.toLowerCase(Locale.ROOT));
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public CompletableFuture<Boolean> dispatchAsync(final CommandSource source, final String cmdLine) {
        return CompletableFuture.supplyAsync(() -> {
            final ParseResults<CommandSource> parse;

            lock.readLock().lock();
            try {
                parse = dispatcher.parse(cmdLine, source);
            } finally {
                lock.readLock().unlock();
            }

            final CommandSyntaxException exception = getParseException(parse);
            final boolean isConsole = source.sender() instanceof ConsoleSender;

            if (exception != null) {
                source.sender()
                        .sendMessage(convert(
                                exception.getRawMessage(),
                                isConsole)
                                .color(NamedTextColor.RED));
                sendContext(source, exception, cmdLine, isConsole);
                return false;
            }

            try {
                // dirty cuz vanilla brigadier doesnt allow child removal wtf
                // also we do this here so real invalid entries fail in the console
                String root = cmdLine.strip();

                final int space = root.indexOf(' ');
                if (space != -1) {
                    root = root.substring(0, space);
                }

                root = root.toLowerCase(Locale.ROOT);

                if (!commands.containsKey(root)) {
                    final CommandSyntaxException e = unknownCommand(parse);
                    source.sender()
                            .sendMessage(
                                    convert(e.getRawMessage(), isConsole)
                                            .color(NamedTextColor.RED));
                    sendContext(source, e, cmdLine, isConsole);
                    return false;
                }

                final int result = dispatcher.execute(parse);
                return result == Command.SINGLE_SUCCESS;
            } catch (final CommandSyntaxException e) {
                source.sender()
                        .sendMessage(convert(e.getRawMessage(), isConsole)
                                .color(NamedTextColor.RED));
                return false;
            }
        }, commandExecutor).exceptionally(ex -> {
            FidorialServer.LOGGER.error("Encountered an exception while executing command: \"/{}\"", cmdLine, ex);
            return false;
        });
    }

    private static boolean hasExecutableNode(final ParseResults<CommandSource> parse) {
        return parse.getContext().getNodes().stream()
                .anyMatch(node -> node.getNode().getCommand() != null);
    }

    private static @Nullable CommandSyntaxException getParseException(final ParseResults<CommandSource> parse) {
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

    private static CommandSyntaxException unknownCommand(final ParseResults<CommandSource> parse) {
        return DISPATCHER_UNKNOWN_COMMAND.createWithContext(parse.getReader());
    }

    private void sendContext(
            final CommandSource source,
            final CommandSyntaxException exception,
            final String command,
            final boolean isConsole
    ) {

        if (exception.getInput() == null || exception.getCursor() < 0) {
            return;
        }

        final int cursor = Math.min(exception.getInput().length(), exception.getCursor());

        Component context =
                Component.empty().color(NamedTextColor.GRAY).clickEvent(ClickEvent.suggestCommand("/" + command));

        if (cursor > 10) {
            context = context.append(Component.text("..."));
        }

        final int start = Math.max(0, cursor - 10);

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
    public CompletableFuture<Suggestions> offerSuggestions(final CommandSource source, final String cmdLine) {
        return CompletableFuture.supplyAsync(() -> {
            lock.readLock().lock();
            try {
                final ParseResults<CommandSource> parse = dispatcher.parse(cmdLine, source);
                return dispatcher.getCompletionSuggestions(parse).join();
            } finally {
                lock.readLock().unlock();
            }
        }, commandExecutor);
    }

    @Override
    public boolean hasCommand(final String alias) {
        return commands.containsKey(alias.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean hasCommand(final String alias, final CommandSource source) {
        lock.readLock().lock();
        try {
            final CommandNode<CommandSource> node = dispatcher.getRoot().getChild(alias.toLowerCase(Locale.ROOT));
            return node != null && node.canUse(source);
        } finally {
            lock.readLock().unlock();
        }
    }

    public ParseResults<CommandSource> parse(
            final StringReader reader,
            final CommandSource source
    ) {
        lock.readLock().lock();
        try {
            return dispatcher.parse(reader, source);
        } finally {
            lock.readLock().unlock();
        }
    }

    public ParseResults<CommandSource> parse(
            final String input,
            final CommandSource source
    ) {
        lock.readLock().lock();
        try {
            return dispatcher.parse(input, source);
        } finally {
            lock.readLock().unlock();
        }
    }

    public CompletableFuture<Suggestions> completionSuggestions(
            final ParseResults<CommandSource> parse,
            final int cursor
    ) {
        lock.readLock().lock();
        try {
            return dispatcher.getCompletionSuggestions(parse, cursor);
        } finally {
            lock.readLock().unlock();
        }
    }

    public ClientboundCommandsPacket createCommandsPacket(final ServerPlayer player) {
        lock.readLock().lock();
        try {
            return new ClientboundCommandsPacket(dispatcher, player);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void shutdown() {
        commandExecutor.shutdown();
        try {
            if (!commandExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                commandExecutor.shutdownNow();
            }
        } catch (final InterruptedException e) {
            commandExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public record RegisteredCommand(LiteralCommandNode<CommandSource> node) {
    }

    public RegisteredCommand command(final String alias) {
        return commands.get(alias.toLowerCase(Locale.ROOT));
    }
}
