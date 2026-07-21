package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

final class AdventureJsonReader {

    private AdventureJsonReader() {
    }

    static String readRawJson(StringReader reader, DynamicCommandExceptionType errorType) throws CommandSyntaxException {
        reader.skipWhitespace();

        if (!reader.canRead()) {
            throw errorType.createWithContext(reader, "");
        }

        int start = reader.getCursor();
        char first = reader.peek();

        if (first == '"' || first == '\'') {
            return readQuotedString(reader, first);
        } else if (first == '{' || first == '[') {
            return readBalanced(reader, first, errorType, start);
        } else {
            throw errorType.createWithContext(reader, String.valueOf(first));
        }
    }

    private static String readQuotedString(StringReader reader, char quote) {
        reader.skip();
        StringBuilder raw = new StringBuilder();
        boolean escaped = false;

        while (reader.canRead()) {
            char c = reader.read();
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
            StringReader reader, char open, DynamicCommandExceptionType errorType, int start
    ) throws CommandSyntaxException {
        char close = open == '{' ? '}' : ']';
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        StringBuilder raw = new StringBuilder();

        while (reader.canRead()) {
            char c = reader.read();
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
