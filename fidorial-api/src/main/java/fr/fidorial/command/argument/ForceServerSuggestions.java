package fr.fidorial.command.argument;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import fr.fidorial.command.CommandSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Marks an ArgumentType that may have no vanilla client-side parser equivalent,
 * so the client must be told to ask the server for suggestions
 * (minecraft:ask_server), regardless of whether .suggests(...) was
 * attached at command-registration time.
 */
@ApiStatus.Internal
@ApiStatus.NonExtendable
public interface ForceServerSuggestions {

    /**
     * Whether this argument instance needs server-side suggestions.
     * Defaults to {@code true} for types that unconditionally have no vanilla
     * equivalent.
     */
    default boolean shouldForceServerSuggestions() {
        return true;
    }

    SuggestionProvider<CommandSource> suggestionProvider();
}
