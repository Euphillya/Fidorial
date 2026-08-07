package fr.fidorial.command.argument.resolvers.selector;

import fr.fidorial.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * A {@link SelectorArgumentResolver} that resolves to the {@link Entity}s
 * matched by an entity selector.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public non-sealed interface EntitySelectorArgumentResolver extends SelectorArgumentResolver<List<Entity>> {
}
