package fr.euphyllia.fidorial.server.command.brigadier.argument.range;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;

import java.util.Arrays;
import java.util.Collection;

public interface RangeArgument<T extends MinMaxBounds<?>> extends com.mojang.brigadier.arguments.ArgumentType<T> {

    static RangeArgument.Ints intRange() {
        return new RangeArgument.Ints();
    }

    static RangeArgument.Floats floatRange() {
        return new RangeArgument.Floats();
    }

    class Floats implements RangeArgument<MinMaxBounds.Doubles> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        public static MinMaxBounds.Doubles getRange(CommandContext<CommandSource> context, String name) {
            return context.getArgument(name, MinMaxBounds.Doubles.class);
        }

        @Override
        public MinMaxBounds.Doubles parse(StringReader reader) throws CommandSyntaxException {
            return MinMaxBounds.Doubles.fromReader(reader);
        }

        @Override
        public Collection<String> getExamples() {
            return EXAMPLES;
        }

        public static final class Info implements ArgumentTypeRegistrar<RangeArgument.Floats, Info.Spec> {

            @Override
            public void serialize(Spec spec, PacketBuffer buf) {
            }

            @Override
            public Spec deserialize(PacketBuffer buf) {
                return new Spec();
            }

            @Override
            public void serializeJson(Spec spec, JsonObject json) {
            }

            @Override
            public Spec access(RangeArgument.Floats argument) {
                return new Spec();
            }

            public record Spec() implements ArgumentTypeRegistrar.Spec<RangeArgument.Floats> {

                @Override
                public RangeArgument.Floats instantiate() {
                    return new RangeArgument.Floats();
                }

                @Override
                public ArgumentTypeRegistrar<RangeArgument.Floats, ?> type() {
                    return new Info();
                }
            }
        }
    }

    class Ints implements RangeArgument<MinMaxBounds.Ints> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        public static MinMaxBounds.Ints getRange(CommandContext<CommandSource> context, String name) {
            return context.getArgument(name, MinMaxBounds.Ints.class);
        }

        @Override
        public MinMaxBounds.Ints parse(StringReader reader) throws CommandSyntaxException {
            return MinMaxBounds.Ints.fromReader(reader);
        }

        @Override
        public Collection<String> getExamples() {
            return EXAMPLES;
        }

        public static final class Info implements ArgumentTypeRegistrar<RangeArgument.Ints, Info.Spec> {

            @Override
            public void serialize(Spec spec, PacketBuffer buf) {
            }

            @Override
            public Spec deserialize(PacketBuffer buf) {
                return new Spec();
            }

            @Override
            public void serializeJson(Spec spec, JsonObject json) {
            }

            @Override
            public Spec access(RangeArgument.Ints argument) {
                return new Spec();
            }

            public record Spec() implements ArgumentTypeRegistrar.Spec<RangeArgument.Ints> {

                @Override
                public RangeArgument.Ints instantiate() {
                    return new RangeArgument.Ints();
                }

                @Override
                public ArgumentTypeRegistrar<RangeArgument.Ints, ?> type() {
                    return new Info();
                }
            }
        }
    }
}
