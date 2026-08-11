package fr.fidorial.command.argument.custom;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

/**
 * Converts a value parsed by a native argument type into a custom result type.
 *
 * @param <N> native value type
 * @param <T> mapped result type
 * @since 0.1.0
 */
@FunctionalInterface
public interface ArgumentMapper<N, T> {
    T map(N nativeValue, StringReader reader) throws CommandSyntaxException;
}
