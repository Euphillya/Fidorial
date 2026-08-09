package fr.euphyllia.fidorial.server.console.command.brigadier;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.fidorial.command.CommandSource;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public final class FidorialCommandHighlighter implements Highlighter {

    private static final AttributedStyle LITERAL_STYLE = AttributedStyle.DEFAULT.bold();
    private static final AttributedStyle UNRECOGNIZED_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).underline();

    private final CommandManager commandManager;
    private final Supplier<CommandSource> sourceFactory;
    private final IntFunction<AttributedStyle> argumentPalette;

    public FidorialCommandHighlighter(final CommandManager commandManager, final Supplier<CommandSource> sourceFactory) {
        this(commandManager, sourceFactory, FidorialCommandHighlighter::defaultPalette);
    }

    public FidorialCommandHighlighter(
            final CommandManager commandManager,
            final Supplier<CommandSource> sourceFactory,
            final IntFunction<AttributedStyle> argumentPalette
    ) {
        this.commandManager = commandManager;
        this.sourceFactory = sourceFactory;
        this.argumentPalette = argumentPalette;
    }

    @Override
    public AttributedString highlight(final LineReader reader, final String buffer) {
        final ParseResults<CommandSource> parseResults = this.commandManager.parse(new StringReader(buffer), this.sourceFactory.get());
        final AttributedStringBuilder out = new AttributedStringBuilder();

        int cursor = 0;
        int argumentOrdinal = 0;
        for (final ParsedCommandNode<CommandSource> node : parseResults.getContext().getLastChild().getNodes()) {
            final int start = node.getRange().getStart();
            if (start >= buffer.length()) {
                break;
            }
            final int end = Math.min(node.getRange().getEnd(), buffer.length());

            appendGap(out, buffer, cursor, start);

            final boolean literal = node.getNode() instanceof LiteralCommandNode<?>;
            final AttributedStyle style = literal ? LITERAL_STYLE : this.argumentPalette.apply(argumentOrdinal);
            out.append(buffer.substring(start, end), style);
            if (!literal) {
                argumentOrdinal++;
            }

            cursor = end;
        }

        appendGap(out, buffer, cursor, buffer.length());
        return out.toAttributedString();
    }

    private static void appendGap(final AttributedStringBuilder out, final String buffer, final int from, final int to) {
        if (from >= to) {
            return;
        }
        final boolean isTrailingUnparsed = to == buffer.length();
        out.append(buffer.substring(from, to), isTrailingUnparsed ? UNRECOGNIZED_STYLE : AttributedStyle.DEFAULT);
    }

    private static AttributedStyle defaultPalette(final int ordinal) {
        final int[] colors = { AttributedStyle.CYAN, AttributedStyle.YELLOW, AttributedStyle.GREEN, AttributedStyle.MAGENTA, AttributedStyle.BLUE };
        return AttributedStyle.DEFAULT.foreground(colors[ordinal % colors.length]);
    }
}
