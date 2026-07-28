package fr.euphyllia.fidorial.server.command.brigadier.argument.range;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public interface RangeArgument {

    static RangeArgument.Ints<MinMaxBounds.Ints> intRange() {
        return intRange(Function.identity());
    }

    static <R> RangeArgument.Ints<R> intRange(final Function<MinMaxBounds.Ints, R> converter) {
        return new RangeArgument.Ints<>(converter);
    }

    static RangeArgument.Floats<MinMaxBounds.Doubles> floatRange() {
        return floatRange(Function.identity());
    }

    static <R> RangeArgument.Floats<R> floatRange(final Function<MinMaxBounds.Doubles, R> converter) {
        return new RangeArgument.Floats<>(converter);
    }

    final class Floats<R> implements ArgumentType<R> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        private final Function<MinMaxBounds.Doubles, R> converter;

        public Floats(final Function<MinMaxBounds.Doubles, R> converter) {
            this.converter = converter;
        }

        public static MinMaxBounds.Doubles getRange(final CommandContext<CommandSource> context, final String name) {
            return context.getArgument(name, MinMaxBounds.Doubles.class);
        }

        @Override
        public R parse(final StringReader reader) throws CommandSyntaxException {
            return converter.apply(MinMaxBounds.Doubles.fromReader(reader));
        }

        @Override
        public Collection<String> getExamples() {
            return EXAMPLES;
        }

        public static final class Info implements ArgumentTypeRegistrar<Floats<?>, Info.Spec> {

            @Override
            public void serialize(final Spec spec, final PacketBuffer buf) {
            }

            @Override
            public Spec deserialize(final PacketBuffer buf) {
                return new Spec();
            }

            @Override
            public void serializeJson(final Spec spec, final JsonObject json) {
            }

            @Override
            public Spec access(final Floats<?> argument) {
                return new Spec();
            }

            public record Spec() implements ArgumentTypeRegistrar.Spec<Floats<?>> {

                @Override
                public Floats<?> instantiate() {
                    return new Floats<>(Function.identity());
                }

                @Override
                public ArgumentTypeRegistrar<Floats<?>, ?> type() {
                    return new Info();
                }
            }
        }
    }

    final class Ints<R> implements ArgumentType<R> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        private final Function<MinMaxBounds.Ints, R> converter;

        public Ints(final Function<MinMaxBounds.Ints, R> converter) {
            this.converter = converter;
        }

        public static MinMaxBounds.Ints getRange(final CommandContext<CommandSource> context, final String name) {
            return context.getArgument(name, MinMaxBounds.Ints.class);
        }

        @Override
        public R parse(final StringReader reader) throws CommandSyntaxException {
            return converter.apply(MinMaxBounds.Ints.fromReader(reader));
        }

        @Override
        public Collection<String> getExamples() {
            return EXAMPLES;
        }

        public static final class Info implements ArgumentTypeRegistrar<Ints<?>, Info.Spec> {

            @Override
            public void serialize(final Spec spec, final PacketBuffer buf) {
            }

            @Override
            public Spec deserialize(final PacketBuffer buf) {
                return new Spec();
            }

            @Override
            public void serializeJson(final Spec spec, final JsonObject json) {
            }

            @Override
            public Spec access(final Ints<?> argument) {
                return new Spec();
            }

            public record Spec() implements ArgumentTypeRegistrar.Spec<Ints<?>> {

                @Override
                public Ints<?> instantiate() {
                    return new Ints<>(Function.identity());
                }

                @Override
                public ArgumentTypeRegistrar<Ints<?>, ?> type() {
                    return new Info();
                }
            }
        }
    }
}
