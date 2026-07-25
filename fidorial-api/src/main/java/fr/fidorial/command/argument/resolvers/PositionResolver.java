package fr.fidorial.command.argument.resolvers;

import fr.fidorial.command.CommandSource;
import fr.fidorial.world.Location;
import org.jetbrains.annotations.ApiStatus;

/**
 * An {@link ArgumentResolver} that's capable of resolving
 *  argument value using a {@link CommandSource}.
 */
@ApiStatus.NonExtendable
public interface PositionResolver extends ArgumentResolver<Location> {

    Location resolve(CommandSource source);
}
