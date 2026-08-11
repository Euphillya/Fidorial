package fr.euphyllia.fidorial.server.command.brigadier.argument.custom;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.custom.ArgumentMapper;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public final class MappedArgumentType<N, T> implements ArgumentType<T>, ForceServerSuggestions {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(MappedArgumentType.class);

    private final ArgumentType<N> nativeType;
    private final ArgumentMapper<N, T> mapper;
    private final @Nullable SuggestionProvider<CommandSource> customSuggestions;
    private final @Nullable Collection<String> customExamples;

    public MappedArgumentType(final ArgumentType<N> nativeType, final ArgumentMapper<N, T> mapper) {
        this(nativeType, mapper, null, null);
    }

    public MappedArgumentType(
            final ArgumentType<N> nativeType,
            final ArgumentMapper<N, T> mapper,
            final @Nullable SuggestionProvider<CommandSource> customSuggestions
    ) {
        this(nativeType, mapper, customSuggestions, null);
    }

    public MappedArgumentType(
            final ArgumentType<N> nativeType,
            final ArgumentMapper<N, T> mapper,
            final @Nullable SuggestionProvider<CommandSource> customSuggestions,
            final @Nullable Collection<String> customExamples
    ) {
        this.nativeType = nativeType;
        this.mapper = mapper;
        this.customSuggestions = customSuggestions;
        this.customExamples = customExamples;
    }

    public ArgumentType<N> nativeType() { return nativeType; }

    @Override
    public T parse(final StringReader reader) throws CommandSyntaxException {
        return mapper.map(nativeType.parse(reader), reader);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (customSuggestions != null && context.getSource() instanceof CommandSource) {
            try {
                return customSuggestions.getSuggestions((CommandContext<CommandSource>) context, builder);
            } catch (final CommandSyntaxException e) {
                LOGGER.warn("Suggestion provider for mapped argument type '{}' threw while computing suggestions", nativeType.getClass().getSimpleName(), e);
                return Suggestions.empty();
            }
        }
        return nativeType.listSuggestions(context, builder);
    }

    @Override
    public Collection<String> getExamples() {
        return customExamples != null ? customExamples : nativeType.getExamples();
    }

    @Override
    public @Nullable SuggestionProvider<CommandSource> suggestionProvider() {
        if (customSuggestions != null) return customSuggestions;
        return nativeType instanceof final ForceServerSuggestions forced ? forced.suggestionProvider() : null;
    }
}
