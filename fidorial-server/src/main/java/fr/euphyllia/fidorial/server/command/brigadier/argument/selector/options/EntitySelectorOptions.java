package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.DoubleRange;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.EntitySelectorParser;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class EntitySelectorOptions {

    private static final Map<String, Option> OPTIONS = new HashMap<>();
    private static final Predicate<EntitySelectorParser> ALWAYS_AVAILABLE = p -> true;

    public static final SimpleCommandExceptionType ERROR_RANGE_NEGATIVE = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.distance.negative")));
    public static final SimpleCommandExceptionType ERROR_LIMIT_TOO_SMALL = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.limit.toosmall")));
    public static final DynamicCommandExceptionType ERROR_SORT_UNKNOWN = new DynamicCommandExceptionType(
            name -> MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.sort.irreversible")
                    .append(Component.text(name.toString()))));

    private EntitySelectorOptions() {
    }

    private static void register(String name, Modifier modifier, Predicate<EntitySelectorParser> canUse) {
        OPTIONS.put(name, new Option(modifier, canUse));
    }

    public static synchronized void bootStrap() {
        if (!OPTIONS.isEmpty()) return;

        register("name", parser -> {
            boolean inverted = parser.shouldInvertValue();
            String name = parser.getReader().readString();
            var state = parser.nameOption();
            if (!state.canParseElement(inverted)) {
                throw parser.getReader().canRead()
                        ? EntitySelectorParser.ERROR_INAPPLICABLE_OPTION.createWithContext(parser.getReader(), "name")
                        : EntitySelectorParser.ERROR_INAPPLICABLE_OPTION.create("name");
            }
            state.markParsedElement(inverted);
            parser.addPredicate(e -> (e instanceof Player p && p.name().equalsIgnoreCase(name)) != inverted);
        }, s -> s.nameOption().canParseAny());

        register("distance", parser -> {
            int start = parser.getReader().getCursor();
            DoubleRange range = DoubleRange.fromReader(parser.getReader());
            if ((range.min() != null && range.min() < 0.0) || (range.max() != null && range.max() < 0.0)) {
                parser.getReader().setCursor(start);
                throw ERROR_RANGE_NEGATIVE.createWithContext(parser.getReader());
            }
            parser.setDistance(range);
        }, s -> s.getDistance() == null);

        register("x", parser -> parser.setX(parser.getReader().readDouble()), s -> s.getX() == null);
        register("y", parser -> parser.setY(parser.getReader().readDouble()), s -> s.getY() == null);
        register("z", parser -> parser.setZ(parser.getReader().readDouble()), s -> s.getZ() == null);
        register("dx", parser -> parser.setDeltaX(parser.getReader().readDouble()), s -> s.getDeltaX() == null);
        register("dy", parser -> parser.setDeltaY(parser.getReader().readDouble()), s -> s.getDeltaY() == null);
        register("dz", parser -> parser.setDeltaZ(parser.getReader().readDouble()), s -> s.getDeltaZ() == null);

        register("limit", parser -> {
            int start = parser.getReader().getCursor();
            int count = parser.getReader().readInt();
            if (count < 1) {
                parser.getReader().setCursor(start);
                throw ERROR_LIMIT_TOO_SMALL.createWithContext(parser.getReader());
            }
            parser.setMaxResults(count);
            parser.limitedOption().markParsed();
        }, s -> !s.isCurrentEntity() && s.limitedOption().canParse());

        register("sort", parser -> {
            int start = parser.getReader().getCursor();
            String name = parser.getReader().readUnquotedString();
            parser.setSuggestions((b, n) -> {
                b.suggest("nearest"); b.suggest("furthest"); b.suggest("random"); b.suggest("arbitrary");
                return b.buildFuture();
            });
            parser.setOrder(switch (name) {
                case "nearest" -> EntitySelector.SortType.NEAREST;
                case "furthest" -> EntitySelector.SortType.FURTHEST;
                case "random" -> EntitySelector.SortType.RANDOM;
                case "arbitrary" -> EntitySelector.SortType.ARBITRARY;
                default -> {
                    parser.getReader().setCursor(start);
                    throw ERROR_SORT_UNKNOWN.createWithContext(parser.getReader(), name);
                }
            });
            parser.sortedOption().markParsed();
        }, s -> !s.isCurrentEntity() && s.sortedOption().canParse());

        register("gamemode", parser -> {
            var state = parser.gamemodeOption();
            boolean inverted = parser.shouldInvertValue();
            if (!state.canParseElement(inverted)) {
                throw EntitySelectorParser.ERROR_INAPPLICABLE_OPTION.createWithContext(parser.getReader(), "gamemode");
            }
            String name = parser.getReader().readUnquotedString().toLowerCase(Locale.ROOT);
            parser.setIncludesEntities(false);
            parser.addPredicate(e -> (e instanceof Player p && p.gameMode().name().equalsIgnoreCase(name)) != inverted);
            state.markParsedElement(inverted);
        }, s -> s.gamemodeOption().canParseAny());

        register("type", parser -> {
            var state = parser.typeOption();
            boolean inverted = parser.shouldInvertValue();
            if (!state.canParseElement(inverted)) {
                throw EntitySelectorParser.ERROR_INAPPLICABLE_OPTION.createWithContext(parser.getReader(), "type");
            }
            String type = parser.getReader().readUnquotedString();
            parser.addPredicate(e -> e.type().key().value().equals(type) != inverted);
            state.markParsedElement(inverted);
        }, s -> s.typeOption().canParseAny());
    }

    public static Modifier get(EntitySelectorParser parser, String key, int start) throws CommandSyntaxException {
        Option option = OPTIONS.get(key);
        if (option == null) {
            throw EntitySelectorParser.ERROR_UNKNOWN_OPTION.createWithContext(rewind(parser, start), key);
        }
        if (!option.canUse.test(parser)) {
            throw EntitySelectorParser.ERROR_INAPPLICABLE_OPTION.createWithContext(rewind(parser, start), key);
        }
        return option.modifier;
    }

    private static StringReader rewind(EntitySelectorParser parser, int start) {
        parser.getReader().setCursor(start);
        return parser.getReader();
    }

    public static void suggestNames(EntitySelectorParser parser, SuggestionsBuilder builder) {
        String prefix = builder.getRemaining().toLowerCase(Locale.ROOT);
        for (Map.Entry<String, Option> entry : OPTIONS.entrySet()) {
            if (entry.getValue().canUse.test(parser) && entry.getKey().toLowerCase(Locale.ROOT).startsWith(prefix)) {
                builder.suggest(entry.getKey() + "=");
            }
        }
    }

    @FunctionalInterface
    public interface Modifier {
        void handle(EntitySelectorParser parser) throws CommandSyntaxException;
    }

    private record Option(Modifier modifier, Predicate<EntitySelectorParser> canUse) {
    }
}
