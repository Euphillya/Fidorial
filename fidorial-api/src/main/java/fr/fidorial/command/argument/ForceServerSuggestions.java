package fr.fidorial.command.argument;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import fr.fidorial.command.CommandSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Marks an ArgumentType that has no vanilla client-side parser equivalent,
 * so the client must always be told to ask the server for suggestions
 * (minecraft:ask_server), regardless of whether .suggests(...) was
 * attached at command-registration time.
 */
@ApiStatus.Internal
public interface ForceServerSuggestions {
    SuggestionProvider<CommandSource> suggestionProvider();
}
