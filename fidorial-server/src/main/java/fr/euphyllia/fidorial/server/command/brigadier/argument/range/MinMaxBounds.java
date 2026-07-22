package fr.euphyllia.fidorial.server.command.brigadier.argument.range;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public interface MinMaxBounds<T extends Number & Comparable<T>> {

    SimpleCommandExceptionType ERROR_EMPTY = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.range.empty")));
    SimpleCommandExceptionType ERROR_SWAPPED = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.range.swapped")));

    Bounds<T> bounds();

    default Optional<T> min() {
        return this.bounds().min();
    }

    default Optional<T> max() {
        return this.bounds().max();
    }

    default boolean isAny() {
        return this.bounds().isAny();
    }

    record Bounds<T extends Number & Comparable<T>>(Optional<T> min, Optional<T> max) {

        public boolean isAny() {
            return this.min.isEmpty() && this.max.isEmpty();
        }

        public boolean areSwapped() {
            return this.min.isPresent() && this.max.isPresent() && this.min.get().compareTo(this.max.get()) > 0;
        }

        public Optional<T> asPoint() {
            return this.min.equals(this.max) ? this.min : Optional.empty();
        }

        public static <T extends Number & Comparable<T>> Bounds<T> any() {
            return new Bounds<T>(Optional.<T>empty(), Optional.<T>empty()); // <T> is needed for javac even tho idea doesnt flag this
        }

        public static <T extends Number & Comparable<T>> Bounds<T> exactly(T value) {
            Optional<T> wrapped = Optional.of(value);
            return new Bounds<>(wrapped, wrapped);
        }

        public static <T extends Number & Comparable<T>> Bounds<T> between(T min, T max) {
            return new Bounds<>(Optional.of(min), Optional.of(max));
        }

        public static <T extends Number & Comparable<T>> Bounds<T> atLeast(T value) {
            return new Bounds<>(Optional.of(value), Optional.empty());
        }

        public static <T extends Number & Comparable<T>> Bounds<T> atMost(T value) {
            return new Bounds<>(Optional.empty(), Optional.of(value));
        }

        public <U extends Number & Comparable<U>> Bounds<U> map(Function<T, U> mapper) {
            return new Bounds<>(this.min.map(mapper), this.max.map(mapper));
        }

        public static <T extends Number & Comparable<T>> Bounds<T> fromReader(
                StringReader reader,
                Function<String, T> converter,
                Supplier<DynamicCommandExceptionType> parseExc
        ) throws CommandSyntaxException {

            if (!reader.canRead()) {
                throw ERROR_EMPTY.createWithContext(reader);
            }

            int start = reader.getCursor();

            try {
                Optional<T> min = readNumber(reader, converter, parseExc);
                Optional<T> max;

                if (reader.canRead(2) && reader.peek() == '.' && reader.peek(1) == '.') {
                    reader.skip();
                    reader.skip();
                    max = readNumber(reader, converter, parseExc);
                } else {
                    max = min;
                }

                if (min.isEmpty() && max.isEmpty()) {
                    throw ERROR_EMPTY.createWithContext(reader);
                }

                return new Bounds<>(min, max);
            } catch (CommandSyntaxException e) {
                reader.setCursor(start);
                throw e;
            }
        }

        private static <T extends Number> Optional<T> readNumber(
                StringReader reader,
                Function<String, T> converter,
                Supplier<DynamicCommandExceptionType> parseExc
        ) throws CommandSyntaxException {

            int start = reader.getCursor();

            while (reader.canRead() && isAllowedInputChar(reader)) {
                reader.skip();
            }

            String number = reader.getString().substring(start, reader.getCursor());
            if (number.isEmpty()) {
                return Optional.empty();
            }

            try {
                return Optional.of(converter.apply(number));
            } catch (NumberFormatException ex) {
                throw parseExc.get().createWithContext(reader, number);
            }
        }

        private static boolean isAllowedInputChar(StringReader reader) {
            char c = reader.peek();
            return (c >= '0' && c <= '9') || c == '-'
                    || (c == '.' && (!reader.canRead(2) || reader.peek(1) != '.'));
        }
    }

    record Doubles(Bounds<Double> bounds, Bounds<Double> boundsSqr) implements MinMaxBounds<Double> {

        public static final Doubles ANY = new Doubles(Bounds.any());

        private Doubles(Bounds<Double> bounds) {
            this(bounds, bounds.map(v -> v * v));
        }

        public static Doubles exactly(double value) {
            return new Doubles(Bounds.exactly(value));
        }

        public static Doubles between(double min, double max) {
            return new Doubles(Bounds.between(min, max));
        }

        public static Doubles atLeast(double value) {
            return new Doubles(Bounds.atLeast(value));
        }

        public static Doubles atMost(double value) {
            return new Doubles(Bounds.atMost(value));
        }

        public boolean matches(double value) {
            return this.bounds.min().map(min -> min <= value).orElse(true)
                    && this.bounds.max().map(max -> max >= value).orElse(true);
        }

        public boolean matchesSqr(double valueSqr) {
            return this.boundsSqr.min().map(min -> min <= valueSqr).orElse(true)
                    && this.boundsSqr.max().map(max -> max >= valueSqr).orElse(true);
        }

        public static Doubles fromReader(StringReader reader) throws CommandSyntaxException {
            int start = reader.getCursor();
            Bounds<Double> bounds = Bounds.fromReader(
                    reader, Double::parseDouble, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidDouble);

            if (bounds.areSwapped()) {
                reader.setCursor(start);
                throw ERROR_SWAPPED.createWithContext(reader);
            }

            return new Doubles(bounds);
        }
    }

    record Ints(Bounds<Integer> bounds, Bounds<Long> boundsSqr) implements MinMaxBounds<Integer> {

        public static final Ints ANY = new Ints(Bounds.any());

        private Ints(Bounds<Integer> bounds) {
            this(bounds, bounds.map(v -> (long) v * (long) v));
        }

        public static Ints exactly(int value) {
            return new Ints(Bounds.exactly(value));
        }

        public static Ints between(int min, int max) {
            return new Ints(Bounds.between(min, max));
        }

        public static Ints atLeast(int value) {
            return new Ints(Bounds.atLeast(value));
        }

        public static Ints atMost(int value) {
            return new Ints(Bounds.atMost(value));
        }

        public boolean matches(int value) {
            return this.bounds.min().map(min -> min <= value).orElse(true)
                    && this.bounds.max().map(max -> max >= value).orElse(true);
        }

        public boolean matchesSqr(long valueSqr) {
            return this.boundsSqr.min().map(min -> min <= valueSqr).orElse(true)
                    && this.boundsSqr.max().map(max -> max >= valueSqr).orElse(true);
        }

        public static Ints fromReader(StringReader reader) throws CommandSyntaxException {
            int start = reader.getCursor();
            Bounds<Integer> bounds = Bounds.fromReader(
                    reader, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt);

            if (bounds.areSwapped()) {
                reader.setCursor(start);
                throw ERROR_SWAPPED.createWithContext(reader);
            }

            return new Ints(bounds);
        }
    }
}
