package fr.fidorial.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.LiteralCommandNode;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * Handles the registration and execution of commands.
 */
public interface CommandRegistry {

    /**
     * Registers a command from the given builder.
     *
     * @param command the command builder to register
     */
    default void register(LiteralArgumentBuilder<CommandSource> command) {
        register(command.build());
    }

    /**
     * Registers a command node without aliases.
     *
     * @param command the command node to register
     */
    default void register(LiteralCommandNode<CommandSource> command) {
        register(command, Set.of());
    }

    /**
     * Registers a command from the given builder with the specified aliases.
     *
     * @param command the command builder to register
     * @param aliases additional aliases that should point to this command
     */
    default void register(LiteralArgumentBuilder<CommandSource> command, Set<String> aliases) {
        register(command.build(), aliases);
    }

    /**
     * Registers a command node with the specified aliases.
     *
     * <p>Aliases are additional names that can be used to execute the command
     * besides its primary literal name.</p>
     *
     * @param command the command node to register
     * @param aliases additional aliases that should point to this command
     */
    void register(LiteralCommandNode<CommandSource> command, Set<String> aliases);

    /**
     * Unregisters the specified command alias from the manager, if registered.
     *
     * @param alias the command alias to unregister
     */
    void unregister(String alias);

    /**
     * Attempts to asynchronously execute a command from the given {@code cmdLine}.
     *
     * @param source  the source to execute the command for
     * @param cmdLine the command to run
     * @return a future that may be completed with the result of the command execution.
     * Can be completed exceptionally if an exception is thrown during execution.
     */
    CompletableFuture<Boolean> dispatchAsync(CommandSource source, String cmdLine);

    /**
     * Asynchronously collects suggestions to fill in the given command {@code cmdLine}.
     * Returns only the raw completion suggestions without tooltips.
     *
     * @param source  the source to execute the command for
     * @param cmdLine the partially completed command
     * @return a {@link CompletableFuture} eventually completed with a {@link List}, possibly empty
     */
    CompletableFuture<List<String>> offerSuggestions(CommandSource source, String cmdLine);

    /**
     * Asynchronously collects suggestions to fill in the given command {@code cmdLine}.
     * Returns the brigadier {@link Suggestions} with tooltips for each result.
     *
     * @param source  the source to execute the command for
     * @param cmdLine the partially completed command
     * @return a {@link CompletableFuture} eventually completed with {@link Suggestions}, possibly
     * empty
     */
    CompletableFuture<Suggestions> offerBrigadierSuggestions(CommandSource source, String cmdLine);

    /**
     * Returns whether the given alias is registered on this manager.
     *
     * @param alias the command alias to check
     * @return true if the alias is registered; false otherwise
     */
    boolean hasCommand(String alias);

    /**
     * Returns whether the given alias is registered on this manager
     * and can be used by the given {@link CommandSource}.
     * See {@link com.mojang.brigadier.builder.ArgumentBuilder#requires(Predicate)}
     *
     * @param alias  the command alias to check
     * @param source the command source
     * @return true if the alias is registered and usable; false otherwise
     */
    boolean hasCommand(String alias, CommandSource source);
}
