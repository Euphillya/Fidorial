package fr.fidorial.command.argument.resolvers;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fidorial.command.CommandSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * An {@link ArgumentResolver} is capable of resolving
 * an argument value using a {@link CommandSource}.
 *
 * @param <T> resolved type
 */
@ApiStatus.NonExtendable
public interface ArgumentResolver<T> {

    /**
     * Resolves the argument with the given
     * command source.
     * @param source source
     * @return resolved
     */
    T resolve(CommandSource source) throws CommandSyntaxException;
}
