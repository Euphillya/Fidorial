package fr.euphyllia.fidorial.server.console.command.brigadier;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
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
    private final Supplier<CommandSource> sourceFactory;

    public FidorialConsoleParser(final CommandManager commandManager, final Supplier<CommandSource> sourceFactory) {
        this.commandManager = commandManager;
        this.sourceFactory = sourceFactory;
    }

    @Override
    public ParsedLine parse(final String line, final int cursor, final ParseContext context) throws SyntaxError {
        final ParseResults<CommandSource> parseResults = this.commandManager.parse(new StringReader(line), this.sourceFactory.get());
        final List<Segment> segments = segmentLine(parseResults, line);
        return buildParsedLine(line, cursor, segments);
    }

    private static <S> List<Segment> segmentLine(final ParseResults<S> parseResults, final String line) {
        final List<Segment> segments = new ArrayList<>();

        CommandContextBuilder<S> frame = parseResults.getContext();
        while (frame != null && frame.getRange().getLength() > 0) {
            for (final ParsedCommandNode<S> node : frame.getNodes()) {
                final int start = node.getRange().getStart();
                final int end = node.getRange().getEnd();
                segments.add(new Segment(line.substring(start, end), start, end));
            }
            frame = frame.getChild();
        }

        final String remaining = parseResults.getReader().getRemaining();
        if (!remaining.isEmpty()) {
            final int start = parseResults.getReader().getCursor();
            final boolean pendingGap = remaining.isBlank();
            segments.add(new Segment(pendingGap ? "" : remaining, start, start + remaining.length()));
        }

        return segments;
    }

    private static ParsedLine buildParsedLine(final String line, final int cursor, final List<Segment> segments) {
        final List<String> words = new ArrayList<>(segments.size() + 1);
        for (final Segment segment : segments) {
            words.add(segment.text());
        }

        for (int i = 0; i < segments.size(); i++) {
            final Segment segment = segments.get(i);
            if (cursor >= segment.start() && cursor <= segment.end()) {
                final int offset = Math.min(cursor - segment.start(), segment.text().length());
                return new BrigadierParsedLine(segment.text(), offset, i, List.copyOf(words), line, cursor);
            }
        }

        words.add("");
        return new BrigadierParsedLine("", 0, words.size() - 1, List.copyOf(words), line, cursor);
    }

    private record Segment(String text, int start, int end) {
    }

    private record BrigadierParsedLine(
            String word,
            int wordCursor,
            int wordIndex,
            List<String> words,
            String line,
            int cursor
    ) implements ParsedLine, CompletingParsedLine {

        @Override
        public CharSequence escape(final CharSequence candidate, final boolean complete) {
            return candidate;
        }

        @Override
        public int rawWordCursor() {
            return this.wordCursor;
        }

        @Override
        public int rawWordLength() {
            return this.word.length();
        }
    }
}
