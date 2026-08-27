package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public final class CommonPositionSuggestions {

    private CommonPositionSuggestions() {
    }

    public static CompletableFuture<Suggestions> suggest(
            final SuggestionsBuilder builder,
            final String x,
            final String y,
            final String z
    ) {
        final String remainder = builder.getRemaining();

        if (remainder.isEmpty()) {
            builder.suggest(x);
            builder.suggest(x + " " + y);
            builder.suggest(x + " " + y + " " + z);

            builder.suggest("~");
            builder.suggest("~ ~");
            builder.suggest("~ ~ ~");
        } else {
            final String[] fields = remainder.split(" ", -1);

            if (fields.length == 1) {
                builder.suggest(fields[0] + " " + y);
                builder.suggest(fields[0] + " " + y + " " + z);
            } else if (fields.length == 2) {
                builder.suggest(fields[0] + " " + fields[1] + " " + z);
            }
        }

        return builder.buildFuture();
    }

    private static void suggest(final SuggestionsBuilder builder, final String value) {
        if (value.startsWith(builder.getRemaining())) {
            builder.suggest(value);
        }
    }
}
