package fr.fidorial.command.argument.resolvers.selector;

import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.resolvers.ArgumentResolver;

/**
 * An {@link ArgumentResolver} that's capable of resolving
 * a selector argument value using a {@link CommandSource}.
 *
 * @param <T> resolved type
 * @see <a href="https://minecraft.wiki/w/Target_selectors">Target Selectors</a>
 * @since 0.1.0
 */
public sealed interface SelectorArgumentResolver<T> extends ArgumentResolver<T> permits EntitySelectorArgumentResolver, PlayerSelectorArgumentResolver {
}
