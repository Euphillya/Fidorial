package fr.fidorial.command.argument.resolvers;

import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.PlayerProfile;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

/**
 * An {@link ArgumentResolver} that's capable of resolving
 *  argument value using a {@link CommandSource}.
 */
@ApiStatus.NonExtendable
public interface PlayerProfileListResolver extends ArgumentResolver<Collection<PlayerProfile>> {
}
