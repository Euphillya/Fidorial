package fr.fidorial.command;

import com.mojang.brigadier.suggestion.Suggestions;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.List;

/**
 * Handles the registration and execution of commands.
 */
public interface CommandRegistry {

    /**
     * Returns a builder to create a {@link CommandMeta} with
     * the given alias.
     *
     * @param alias the first command alias
     * @return a {@link CommandMeta} builder
     */
    CommandMeta.Builder metaBuilder(String alias);

    /**
     * Returns a builder to create a {@link CommandMeta} for
     * the given Brigadier command.
     *
     * @param command the command
     * @return a {@link CommandMeta} builder
     */
    CommandMeta.Builder metaBuilder(CommandTree command);

    /**
     * Registers the specified command with the given metadata.
     *
     * @param meta    the command metadata
     * @param command the command to register
     * @throws IllegalArgumentException if one of the given aliases is already registered, or
     *                                  the given command does not implement a registrable {@link CommandTree} subinterface
     * @see CommandTree for a list of registrable Command subinterfaces
     */
    void register(CommandMeta meta, CommandTree command);

    /**
     * Unregisters the specified command alias from the manager, if registered.
     *
     * @param alias the command alias to unregister
     */
    void unregister(String alias);

    /**
     * Unregisters the specified command from the manager, if registered.
     *
     * @param meta the command to unregister
     */
    void unregister(CommandMeta meta);

    /**
     * Retrieves the {@link CommandMeta} from the specified command alias, if registered.
     *
     * @param alias the command alias to lookup
     * @return an {@link CommandMeta} of the alias
     */
    @Nullable CommandMeta commandMeta(String alias);

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
     * Returns an immutable collection of the case-insensitive aliases registered
     * on this manager.
     *
     * @return the registered aliases
     */
    Collection<String> aliases();

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