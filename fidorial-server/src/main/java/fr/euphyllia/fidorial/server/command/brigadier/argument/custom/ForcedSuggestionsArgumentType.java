package fr.euphyllia.fidorial.server.command.brigadier.argument.custom;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;

import java.util.concurrent.CompletableFuture;

public final class ForcedSuggestionsArgumentType<T> implements ArgumentType<T>, ForceServerSuggestions {

    private final ArgumentType<T> delegate;
    private final SuggestionProvider<CommandSource> suggestions;

    public ForcedSuggestionsArgumentType(final ArgumentType<T> delegate) {
        this.delegate = delegate;
        this.suggestions = this::listSuggestions;
    }

    @Override
    public T parse(final StringReader reader) throws CommandSyntaxException {
        return delegate.parse(reader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        return delegate.listSuggestions(context, builder);
    }

    @Override
    public SuggestionProvider<CommandSource> suggestionProvider() {
        return suggestions;
    }

    public ArgumentType<T> delegate() {
        return delegate;
    }

    public static final class Info implements ArgumentTypeRegistrar<ForcedSuggestionsArgumentType<?>, Info.Spec> {

        @Override
        public void serialize(final Spec spec, final PacketBuffer buf) {
            buf.writeVarInt(StringArgumentType.StringType.SINGLE_WORD.ordinal());
        }

        @Override
        public Spec deserialize(final PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(final Spec spec, final JsonObject json) {
        }

        @Override
        public Spec access(final ForcedSuggestionsArgumentType<?> argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<ForcedSuggestionsArgumentType<?>> {

            @Override
            public ForcedSuggestionsArgumentType<?> instantiate() {
                throw new UnsupportedOperationException(
                        "ForcedSuggestionsArgumentType cannot be reconstructed from network data");
            }

            @Override
            public ArgumentTypeRegistrar<ForcedSuggestionsArgumentType<?>, ?> type() {
                return new Info();
            }
        }
    }
}
