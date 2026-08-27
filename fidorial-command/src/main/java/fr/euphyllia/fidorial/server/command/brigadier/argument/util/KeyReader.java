package fr.euphyllia.fidorial.server.command.brigadier.argument.util;

import com.mojang.brigadier.StringReader;
import net.kyori.adventure.key.Key;

public final class KeyReader {

    private KeyReader() {
    }

    public record ParsedKey(String value, boolean hasNamespace) {
    }

    public static ParsedKey readKeyStringDetailed(final StringReader reader) {
        final int start = reader.getCursor();
        boolean sawSeparator = false;

        while (reader.canRead()) {
            final char c = reader.peek();

            if (c == Key.DEFAULT_SEPARATOR) {
                if (sawSeparator) {
                    break;
                }
                sawSeparator = true;
                reader.skip();
                continue;
            }

            if (sawSeparator ? Key.allowedInValue(c) : Key.allowedInNamespace(c)) {
                reader.skip();
            } else {
                break;
            }
        }

        final String value = reader.getString().substring(start, reader.getCursor());
        return new ParsedKey(value, sawSeparator);
    }

    public static String readKeyString(final StringReader reader) {
        return readKeyStringDetailed(reader).value();
    }
}
