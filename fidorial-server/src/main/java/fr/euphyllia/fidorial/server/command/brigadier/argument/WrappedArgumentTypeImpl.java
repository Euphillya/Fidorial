package fr.euphyllia.fidorial.server.command.brigadier.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public abstract class WrappedArgumentTypeImpl<P, V> implements WrappedArgumentType<P> {

    protected final ArgumentType<V> internalArgumentType;

    protected WrappedArgumentTypeImpl(final ArgumentType<V> internalArgumentType) {
        this.internalArgumentType = internalArgumentType;
    }

    @Override
    public final ArgumentType<?> internalArgumentType() {
        return this.internalArgumentType;
    }

    @Override
    public final P parse(final StringReader reader) throws CommandSyntaxException {
        return this.convert(this.internalArgumentType.parse(reader));
    }

    @Override
    public final <S> P parse(final StringReader reader, final S source) throws CommandSyntaxException {
        return this.convert(this.internalArgumentType.parse(reader, source));
    }

    @Override
    public final <S> CompletableFuture<Suggestions> listSuggestions(
            final CommandContext<S> context,
            final SuggestionsBuilder builder
    ) {
        return this.internalArgumentType.listSuggestions(context, builder);
    }

    @Override
    public final Collection<String> getExamples() {
        return this.internalArgumentType.getExamples();
    }

    protected abstract P convert(V value) throws CommandSyntaxException;
}
