package fr.euphyllia.fidorial.server.command.brigadier.argument.generic;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ForceServerSuggestions;
import net.kyori.adventure.text.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

/**
 * Reads a positive amount of time written as a chain of amount/unit pairs, such as
 * {@code 30m}, {@code 7d} or {@code 1w12h}.
 *
 * <p>The unit is always required: a bare number would be ambiguous between ticks, seconds and
 * minutes depending on who reads it.</p>
 */
public final class DurationArgument implements ArgumentType<Duration>, ForceServerSuggestions {

    /**
     * Anything longer than this is a permanent ban with extra steps, and keeps the expiry away
     * from the range where {@link Instant} arithmetic overflows.
     */
    public static final Duration MAXIMUM = Duration.ofDays(36_500);

    private static final Collection<String> EXAMPLES = List.of("30m", "12h", "7d", "1w12h");

    private static final List<String> COMMON = List.of("10m", "30m", "1h", "12h", "1d", "7d", "30d");

    private static final Pattern UNITS = Pattern.compile("(\\d{1,9})([wdhms])", Pattern.CASE_INSENSITIVE);

    private static final DynamicCommandExceptionType ERROR_INVALID =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.duration.invalid", Component.text(String.valueOf(value)))));

    private static final DynamicCommandExceptionType ERROR_TOO_LONG =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.duration.too_long", Component.text(String.valueOf(value)))));

    public static final SuggestionProvider<CommandSource> SUGGESTIONS = (_, builder) -> suggest(builder);

    public static DurationArgument duration() {
        return new DurationArgument();
    }

    public static Duration getDuration(final CommandContext<CommandSource> context, final String name) {
        return context.getArgument(name, Duration.class);
    }

    @Override
    public Duration parse(final StringReader reader) throws CommandSyntaxException {
        final int start = reader.getCursor();
        final String input = reader.readUnquotedString();

        final Matcher matcher = UNITS.matcher(input);

        Duration total = Duration.ZERO;
        int read = 0;

        while (matcher.find() && matcher.start() == read) {
            total = total.plus(unit(matcher.group(2)).multipliedBy(Long.parseLong(matcher.group(1))));
            read = matcher.end();
        }

        if (read != input.length() || total.isZero()) {
            reader.setCursor(start);
            throw ERROR_INVALID.createWithContext(reader, input);
        }

        if (total.compareTo(MAXIMUM) > 0) {
            reader.setCursor(start);
            throw ERROR_TOO_LONG.createWithContext(reader, input);
        }

        return total;
    }

    private static Duration unit(final String unit) {
        return switch (unit.toLowerCase(Locale.ROOT)) {
            case "w" -> Duration.ofDays(7);
            case "d" -> Duration.ofDays(1);
            case "h" -> Duration.ofHours(1);
            case "m" -> Duration.ofMinutes(1);
            default -> Duration.ofSeconds(1);
        };
    }

    private static CompletableFuture<Suggestions> suggest(final SuggestionsBuilder builder) {
        final String remaining = builder.getRemainingLowerCase();

        COMMON.stream()
                .filter(value -> value.startsWith(remaining))
                .forEach(builder::suggest);

        return builder.buildFuture();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            final CommandContext<S> context,
            final SuggestionsBuilder builder
    ) {
        return suggest(builder);
    }

    @Override
    public SuggestionProvider<CommandSource> suggestionProvider() {
        return SUGGESTIONS;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<DurationArgument, Info.Spec> {

        @Override
        public void serialize(final Spec spec, final PacketBuffer buf) {
            buf.writeVarInt(StringArgumentType.StringType.SINGLE_WORD.ordinal());
        }

        @Override
        public Spec deserialize(final PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(final Spec spec, final JsonObject json) {
        }

        @Override
        public Spec access(final DurationArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<DurationArgument> {

            @Override
            public DurationArgument instantiate() {
                return DurationArgument.duration();
            }

            @Override
            public ArgumentTypeRegistrar<DurationArgument, ?> type() {
                return new Info();
            }
        }
    }
}