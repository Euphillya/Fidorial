package fr.euphyllia.fidorial.server.console.command.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestion;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class FidorialCommandCompleter implements Completer {

    private final CommandManager commandManager;
    private final Supplier<CommandSource> consoleSource;

    public FidorialCommandCompleter(CommandManager commandManager, Supplier<CommandSource> consoleSource) {
        this.commandManager = commandManager;
        this.consoleSource = consoleSource;
    }

    @Override
    public void complete(final LineReader reader, final ParsedLine line, final List<Candidate> candidates) {
        final CommandDispatcher<CommandSource> dispatcher = this.commandManager.dispatcher();
        final ParseResults<CommandSource> results =
                dispatcher.parse(new StringReader(line.line()), this.consoleSource.get());

        List<Suggestion> suggestions = CompletableFuture
                .supplyAsync(() -> dispatcher.getCompletionSuggestions(results, line.cursor()))
                .thenCompose(Function.identity())
                .join()
                .getList();

        int suggestionStart = results.getContext().findSuggestionContext(line.cursor()).startPos;

        for (Suggestion suggestion : suggestions) {
            if (suggestion.getText().isEmpty()) continue;
            candidates.add(toCandidate(suggestion, line.line(), suggestionStart));
        }
    }

    private static Candidate toCandidate(Suggestion suggestion, String fullLine, int suggestionStart) {
        String value = fullLine.substring(suggestionStart, suggestion.getRange().getStart()) + suggestion.getText();

        @Nullable String tooltip = null;
        Message rawTooltip = suggestion.getTooltip();
        if (suggestion.getTooltip() != null) {
            Component component = MSG_SERIALIZER.deserialize(rawTooltip);
            tooltip = PlainTextComponentSerializer.plainText().serialize(component);
        }

        return new Candidate(value, value, null, tooltip, null, null, false);
    }
}
