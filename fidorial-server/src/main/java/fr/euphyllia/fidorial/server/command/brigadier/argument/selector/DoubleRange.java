package fr.euphyllia.fidorial.server.command.brigadier.argument.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.kyori.adventure.text.Component;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public record DoubleRange(Double min, Double max) {

    public static final SimpleCommandExceptionType ERROR_EMPTY = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.range.empty")));
    public static final SimpleCommandExceptionType ERROR_SWAPPED = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.range.swapped")));

    public static DoubleRange exact(double value) {
        return new DoubleRange(value, value);
    }

    public static DoubleRange fromReader(StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            throw ERROR_EMPTY.createWithContext(reader);
        }

        int start = reader.getCursor();
        Double min = readNumberOrNull(reader);
        Double max = min;

        if (reader.canRead(2) && reader.peek() == '.' && reader.peek(1) == '.') {
            reader.skip();
            reader.skip();
            max = readNumberOrNull(reader);

            if (min == null && max == null) {
                reader.setCursor(start);
                throw ERROR_EMPTY.createWithContext(reader);
            }
        } else if (min == null) {
            reader.setCursor(start);
            throw ERROR_EMPTY.createWithContext(reader);
        }

        if (min != null && max != null && min > max) {
            reader.setCursor(start);
            throw ERROR_SWAPPED.createWithContext(reader);
        }

        return new DoubleRange(min, max);
    }

    private static Double readNumberOrNull(StringReader reader) {
        int start = reader.getCursor();

        while (reader.canRead() && isAllowedNumber(reader)) {
            reader.skip();
        }

        String number = reader.getString().substring(start, reader.getCursor());
        if (number.isEmpty()) {
            return null;
        }

        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static boolean isAllowedNumber(StringReader reader) {
        char c = reader.peek();
        return (c >= '0' && c <= '9') || c == '-'
                || (c == '.' && !(reader.canRead(2) && reader.peek(1) == '.'));
    }

    public boolean matches(double value) {
        if (this.min != null && value < this.min) return false;
        return this.max == null || !(value > this.max);
    }

    public boolean matchesSqr(double valueSqr) {
        if (this.min != null && valueSqr < this.min * this.min) return false;
        return this.max == null || !(valueSqr > this.max * this.max);
    }
}
