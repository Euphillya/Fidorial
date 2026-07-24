package fr.euphyllia.fidorial.server.command.brigadier.argument;

import com.mojang.brigadier.arguments.ArgumentType;

/**
 * Implemented by the anonymous {@link ArgumentType} instances in
 * {@link ArgumentProviderImpl} that just convert the result of internal argument type into an API type.
 */
public interface WrappedArgumentType<P> extends ArgumentType<P> {

    ArgumentType<?> internalArgumentType();
}
