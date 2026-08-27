package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

final class AdventureJsonReader {

    private AdventureJsonReader() {
    }

    static String readRawJson(final StringReader reader, final DynamicCommandExceptionType errorType) throws CommandSyntaxException {
        reader.skipWhitespace();

        if (!reader.canRead()) {
            throw errorType.createWithContext(reader, "");
        }

        final int start = reader.getCursor();
        final char first = reader.peek();

        if (first == '"' || first == '\'') {
            return readQuotedString(reader, first);
        } else if (first == '{' || first == '[') {
            return readBalanced(reader, first, errorType, start);
        } else if (StringReader.isAllowedInUnquotedString(first)) {
            return readUnquotedAsJsonString(reader);
        } else {
            throw errorType.createWithContext(reader, String.valueOf(first));
        }
    }

    private static String readUnquotedAsJsonString(final StringReader reader) {
        final String word = reader.readUnquotedString();
        final StringBuilder escaped = new StringBuilder(word.length() + 2);
        escaped.append('"');
        for (int i = 0; i < word.length(); i++) {
            final char c = word.charAt(i);
            if (c == '"' || c == '\\') {
                escaped.append('\\');
            }
            escaped.append(c);
        }
        escaped.append('"');
        return escaped.toString();
    }

    private static String readQuotedString(final StringReader reader, final char quote) {
        reader.skip();
        final StringBuilder raw = new StringBuilder();
        boolean escaped = false;

        while (reader.canRead()) {
            final char c = reader.read();
            if (escaped) {
                raw.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
                raw.append(c);
            } else if (c == quote) {
                break;
            } else {
                raw.append(c);
            }
        }

        String content = raw.toString();
        if (quote == '\'') {
            content = content.replace("\\'", "'").replace("\"", "\\\"");
        }

        return "\"" + content + "\"";
    }

    private static String readBalanced(
            final StringReader reader, final char open, final DynamicCommandExceptionType errorType, final int start
    ) throws CommandSyntaxException {
        final char close = open == '{' ? '}' : ']';
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        final StringBuilder raw = new StringBuilder();

        while (reader.canRead()) {
            final char c = reader.read();
            raw.append(c);

            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (c == '\\') {
                    escaped = true;
                } else if (c == '"') {
                    inString = false;
                }
                continue;
            }

            if (c == '"') {
                inString = true;
            } else if (c == open) {
                depth++;
            } else if (c == close) {
                depth--;
                if (depth == 0) {
                    return raw.toString();
                }
            }
        }

        reader.setCursor(start);
        throw errorType.createWithContext(reader, raw.toString());
    }
}
