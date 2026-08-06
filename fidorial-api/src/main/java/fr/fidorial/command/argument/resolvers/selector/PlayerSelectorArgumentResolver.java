package fr.fidorial.command.argument.resolvers.selector;

import fr.fidorial.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * A {@link SelectorArgumentResolver} that resolves to the {@link Player}s
 * matched by a player selector.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public non-sealed interface PlayerSelectorArgumentResolver extends SelectorArgumentResolver<List<Player>> {
}
