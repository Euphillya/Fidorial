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

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class FidorialCommandHighlighter implements Highlighter {

    private static final int[] COLORS = {
            AttributedStyle.CYAN, AttributedStyle.YELLOW, AttributedStyle.GREEN,
            AttributedStyle.MAGENTA, AttributedStyle.BLUE
    };

    private final CommandManager commandManager;
    private final Supplier<CommandSource> consoleSource;

    public FidorialCommandHighlighter(CommandManager commandManager, Supplier<CommandSource> consoleSource) {
        this.commandManager = commandManager;
        this.consoleSource = consoleSource;
    }

    @Override
    public AttributedString highlight(final LineReader reader, final String buffer) {
        final AttributedStringBuilder builder = new AttributedStringBuilder();
        final ParseResults<CommandSource> results =
                this.commandManager.dispatcher().parse(new StringReader(buffer), this.consoleSource.get());

        int pos = 0;
        int component = -1;

        for (final ParsedCommandNode<CommandSource> node : results.getContext().getLastChild().getNodes()) {
            if (node.getRange().getStart() >= buffer.length()) {
                break;
            }

            final int start = node.getRange().getStart();
            final int end = Math.min(node.getRange().getEnd(), buffer.length());

            builder.append(buffer.substring(pos, start), AttributedStyle.DEFAULT);

            if (node.getNode() instanceof LiteralCommandNode) {
                builder.append(buffer.substring(start, end), AttributedStyle.DEFAULT);
            } else {
                if (++component >= COLORS.length) {
                    component = 0;
                }
                builder.append(buffer.substring(start, end), AttributedStyle.DEFAULT.foreground(COLORS[component]));
            }

            pos = end;
        }

        if (pos < buffer.length()) {
            builder.append(buffer.substring(pos), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
        }

        return builder.toAttributedString();
    }

    @Override
    public void setErrorPattern(final Pattern errorPattern) {
    }

    @Override
    public void setErrorIndex(final int errorIndex) {
    }
}
