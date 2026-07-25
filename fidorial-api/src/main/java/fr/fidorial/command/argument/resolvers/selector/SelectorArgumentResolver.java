package fr.fidorial.command.argument.resolvers.selector;

import fr.fidorial.command.argument.resolvers.ArgumentResolver;
import org.jetbrains.annotations.ApiStatus;

/**
 * An {@link ArgumentResolver} that's capable of resolving
 * a selector argument value using a {@link fr.fidorial.command.CommandSource}.
 *
 * @param <T> resolved type
 * @see <a href="https://minecraft.wiki/w/Target_selectors">Target Selectors</a>
 */
@ApiStatus.NonExtendable
public interface SelectorArgumentResolver<T> extends ArgumentResolver<T> {
}
