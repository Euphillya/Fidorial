package fr.fidorial.command.argument.resolvers;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fidorial.command.CommandSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * An {@link ArgumentResolver} capable of resolving
 * an angle value using a {@link CommandSource}.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface AngleResolver {

    /**
     * Resolves the argument with the given
     * command source.
     *
     * @param source source
     * @return the resolved angle in degrees.
     * @throws CommandSyntaxException if an error occurs while parsing
     */
    float resolve(CommandSource source) throws CommandSyntaxException;
}
