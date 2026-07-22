package fr.euphyllia.fidorial.server.console.command.brigadier;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.context.StringRange;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.fidorial.command.CommandSource;
import org.jline.reader.CompletingParsedLine;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.SyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class FidorialConsoleParser implements Parser {

    private final CommandManager commandManager;
    private final Supplier<CommandSource> consoleSource;

    public FidorialConsoleParser(CommandManager commandManager, Supplier<CommandSource> consoleSource) {
        this.commandManager = commandManager;
        this.consoleSource = consoleSource;
    }

    @Override
    public ParsedLine parse(final String line, final int cursor, final ParseContext context) throws SyntaxError {
        final ParseResults<CommandSource> results =
                this.commandManager.dispatcher().parse(new StringReader(line), this.consoleSource.get());
        final ImmutableStringReader reader = results.getReader();
        final List<String> words = new ArrayList<>();
        CommandContextBuilder<CommandSource> currentContext = results.getContext();
        int currentWordIdx = -1;
        int wordIdx = -1;
        int inWordCursor = -1;

        if (currentContext.getRange().getLength() > 0) {
            do {
                for (final ParsedCommandNode<CommandSource> node : currentContext.getNodes()) {
                    final StringRange nodeRange = node.getRange();
                    String current = nodeRange.get(reader);
                    words.add(current);
                    currentWordIdx++;
                    if (wordIdx == -1 && nodeRange.getStart() <= cursor && nodeRange.getEnd() >= cursor) {
                        wordIdx = currentWordIdx;
                        inWordCursor = cursor - nodeRange.getStart();
                    }
                }
                currentContext = currentContext.getChild();
            } while (currentContext != null);
        }

        final String leftovers = reader.getRemaining();
        if (!leftovers.isEmpty() && leftovers.isBlank()) {
            currentWordIdx++;
            words.add("");
            if (wordIdx == -1) {
                wordIdx = currentWordIdx;
                inWordCursor = 0;
            }
        } else if (!leftovers.isEmpty()) {
            currentWordIdx++;
            words.add(leftovers);
            if (wordIdx == -1) {
                wordIdx = currentWordIdx;
                inWordCursor = cursor - reader.getCursor();
            }
        }

        if (wordIdx == -1) {
            currentWordIdx++;
            words.add("");
            wordIdx = currentWordIdx;
            inWordCursor = 0;
        }

        return new BrigadierParsedLine(words.get(wordIdx), inWordCursor, wordIdx, words, line, cursor);
    }

    record BrigadierParsedLine(String word, int wordCursor, int wordIndex, List<String> words, String line, int cursor)
            implements ParsedLine, CompletingParsedLine {

        @Override
        public CharSequence escape(CharSequence candidate, boolean complete) {
            return candidate;
        }

        @Override
        public int rawWordCursor() {
            return wordCursor;
        }

        @Override
        public int rawWordLength() {
            return word.length();
        }
    }
}
