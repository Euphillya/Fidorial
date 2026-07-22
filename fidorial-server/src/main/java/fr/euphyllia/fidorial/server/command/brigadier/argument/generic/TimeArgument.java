package fr.euphyllia.fidorial.server.command.brigadier.argument.generic;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public record TimeArgument(int minimum) implements ArgumentType<Integer> {

    private static final Collection<String> EXAMPLES = Arrays.asList("0d", "0s", "0t", "0");

    private static final SimpleCommandExceptionType ERROR_INVALID_UNIT = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.time.invalid_unit")));

    private static final Dynamic2CommandExceptionType ERROR_TICK_COUNT_TOO_LOW =
            new Dynamic2CommandExceptionType((value, limit) -> MSG_SERIALIZER.serialize(Component.translatable(
                    "argument.time.tick_count_too_low",
                    Component.text(limit.toString()),
                    Component.text(value.toString()))));

    private static final Map<String, Integer> UNITS = new LinkedHashMap<>();

    static {
        UNITS.put("d", 24000);
        UNITS.put("s", 20);
        UNITS.put("t", 1);
        UNITS.put("", 1);
    }

    public static TimeArgument time() {
        return new TimeArgument(0);
    }

    public static TimeArgument time(int minimum) {
        return new TimeArgument(minimum);
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {

        float value = reader.readFloat();

        String unit = reader.readUnquotedString();

        int factor = UNITS.getOrDefault(unit, 0);

        if (factor == 0) {
            throw ERROR_INVALID_UNIT.createWithContext(reader);
        }

        int ticks = Math.round(value * factor);

        if (ticks < minimum) {
            throw ERROR_TICK_COUNT_TOO_LOW.createWithContext(reader, ticks, minimum);
        }

        return ticks;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader reader = new StringReader(builder.getRemaining());

        try {
            reader.readFloat();
        } catch (CommandSyntaxException exception) {
            return builder.buildFuture();
        }

        SuggestionsBuilder offset = builder.createOffset(builder.getStart() + reader.getCursor());

        for (String unit : UNITS.keySet()) {
            offset.suggest(unit);
        }

        return offset.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<TimeArgument, Info.Spec> {

        @Override
        public void serialize(Spec spec, PacketBuffer buf) {
            buf.writeInt(spec.minimum());
        }

        @Override
        public Spec deserialize(PacketBuffer buf) {
            return new Spec(buf.readInt());
        }

        @Override
        public void serializeJson(Spec spec, JsonObject json) {
            json.addProperty("min", spec.minimum());
        }

        @Override
        public Spec access(TimeArgument argument) {
            return new Spec(argument.minimum());
        }

        public record Spec(int minimum) implements ArgumentTypeRegistrar.Spec<TimeArgument> {

            @Override
            public TimeArgument instantiate() {
                return TimeArgument.time(minimum);
            }

            @Override
            public ArgumentTypeRegistrar<TimeArgument, ?> type() {
                return new Info();
            }
        }
    }
}
