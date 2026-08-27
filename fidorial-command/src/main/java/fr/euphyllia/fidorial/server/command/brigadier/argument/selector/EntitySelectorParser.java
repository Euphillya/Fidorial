package fr.euphyllia.fidorial.server.command.brigadier.argument.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.EntitySelectorOptions;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.SelectorSetState;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.SingleUseOption;
import fr.euphyllia.fidorial.server.command.brigadier.argument.util.ExceptionFactory;
import fr.fidorial.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class EntitySelectorParser {

    public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_OPTIONS = ExceptionFactory.simple("argument.entity.options.unterminated");
    public static final DynamicCommandExceptionType ERROR_EXPECTED_OPTION_VALUE = ExceptionFactory.dynamic("argument.entity.options.valueless");
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_OPTION = ExceptionFactory.dynamic("argument.entity.options.unknown");
    public static final DynamicCommandExceptionType ERROR_INAPPLICABLE_OPTION = ExceptionFactory.dynamic("argument.entity.options.inapplicable");
    private static final SimpleCommandExceptionType INVALID = ExceptionFactory.simple("argument.entity.invalid");

    @FunctionalInterface
    private interface SuggestionProvider {
        CompletableFuture<Suggestions> apply(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names);
        SuggestionProvider NONE = (b, n) -> b.buildFuture();
    }

    private final StringReader reader;
    private int startPosition;
    private SuggestionProvider suggestions = SuggestionProvider.NONE;

    private int maxResults = 1;
    private boolean includesEntities;
    private boolean selfSelector;
    private boolean usesSelector;

    private @Nullable Double x;
    private @Nullable Double y;
    private @Nullable Double z;

    private @Nullable Double deltaX;
    private @Nullable Double deltaY;
    private @Nullable Double deltaZ;

    private @Nullable DoubleRange distance;

    private @Nullable String targetName;
    private @Nullable UUID targetUuid;

    private EntitySelector.SortType sort = EntitySelector.SortType.ARBITRARY;

    private final List<Predicate<Entity>> predicates = new ArrayList<>();

    private final SelectorSetState nameOption = new SelectorSetState();
    private final SingleUseOption limitedOption = new SingleUseOption();
    private final SingleUseOption sortedOption = new SingleUseOption();
    private final SelectorSetState gamemodeOption = new SelectorSetState();
    private final SelectorSetState typeOption = new SelectorSetState();

    public EntitySelectorParser(final StringReader reader) {
        this.reader = reader;
        EntitySelectorOptions.bootStrap();
    }

    public EntitySelector parse() throws CommandSyntaxException {
        startPosition = reader.getCursor();
        suggestions = this::suggestNameOrSelector;

        if (reader.canRead() && reader.peek() == '@') {
            reader.skip();
            parseSelector();
        } else {
            parseNameOrUuid();
        }

        return buildSelector();
    }

    private void parseSelector() throws CommandSyntaxException {
        usesSelector = true;
        suggestions = this::suggestSelector;

        if (!reader.canRead()) {
            throw INVALID.create();
        }

        switch (reader.read()) {
            case 's' -> { selfSelector = true; includesEntities = false; maxResults = 1; }
            case 'p' -> { includesEntities = false; maxResults = 1; sort = EntitySelector.SortType.NEAREST; }
            case 'a' -> { includesEntities = false; maxResults = Integer.MAX_VALUE; }
            case 'e' -> { includesEntities = true; maxResults = Integer.MAX_VALUE; }
            case 'r' -> { includesEntities = false; maxResults = 1; sort = EntitySelector.SortType.RANDOM; }
            case 'n' -> { includesEntities = true; maxResults = 1; sort = EntitySelector.SortType.NEAREST; }
            default -> throw INVALID.create();
        }

        suggestions = this::suggestOpenOptions;

        if (reader.canRead() && reader.peek() == '[') {
            reader.skip();
            suggestions = this::suggestOptionsKeyOrClose;
            parseArguments();
        }
    }

    private void parseArguments() throws CommandSyntaxException {
        suggestions = this::suggestOptionsKey;
        reader.skipWhitespace();

        while (reader.canRead() && reader.peek() != ']') {
            reader.skipWhitespace();

            final int start = reader.getCursor();
            final String key = reader.readString();

            final EntitySelectorOptions.Modifier modifier = EntitySelectorOptions.get(this, key, start);

            reader.skipWhitespace();

            if (!reader.canRead() || reader.peek() != '=') {
                reader.setCursor(start);
                throw ERROR_EXPECTED_OPTION_VALUE.createWithContext(reader, key);
            }

            reader.skip();
            reader.skipWhitespace();
            suggestions = SuggestionProvider.NONE;

            modifier.handle(this);

            reader.skipWhitespace();
            suggestions = this::suggestOptionsNextOrClose;

            if (reader.canRead()) {
                if (reader.peek() != ',') {
                    if (reader.peek() != ']') {
                        throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(reader);
                    }
                    break;
                }
                reader.skip();
                suggestions = this::suggestOptionsKey;
            }
        }

        if (reader.canRead()) {
            reader.skip();
            suggestions = SuggestionProvider.NONE;
        } else {
            throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(reader);
        }
    }

    private void parseNameOrUuid() throws CommandSyntaxException {
        suggestions = this::suggestName;

        final String value = reader.readString();
        if (value.isEmpty()) {
            throw INVALID.create();
        }

        try {
            targetUuid = UUID.fromString(value);
            includesEntities = true;
        } catch (final IllegalArgumentException ex) {
            targetName = value;
            includesEntities = false;
        }

        maxResults = 1;
    }

    private EntitySelector buildSelector() {
        return new EntitySelector(
                maxResults,
                includesEntities,
                selfSelector,
                usesSelector,
                predicates,
                x, y, z,
                distance,
                deltaX, deltaY, deltaZ,
                sort,
                targetName,
                targetUuid);
    }

    public StringReader getReader() {
        return reader;
    }

    public boolean isCurrentEntity() {
        return selfSelector;
    }

    public void addPredicate(final Predicate<Entity> predicate) {
        predicates.add(predicate);
    }

    public boolean shouldInvertValue() {
        reader.skipWhitespace();
        if (reader.canRead() && reader.peek() == '!') {
            reader.skip();
            reader.skipWhitespace();
            return true;
        }
        return false;
    }

    public void setSuggestions(final SuggestionProviderLike provider) {
        this.suggestions = provider::apply;
    }

    @FunctionalInterface
    public interface SuggestionProviderLike {
        CompletableFuture<Suggestions> apply(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names);
    }

    public @Nullable DoubleRange getDistance() {
        return distance;
    }

    public void setDistance(final DoubleRange distance) {
        this.distance = distance;
    }

    public @Nullable Double getX() { return x; }
    public @Nullable Double getY() { return y; }
    public @Nullable Double getZ() { return z; }
    public void setX(final double x) { this.x = x; }
    public void setY(final double y) { this.y = y; }
    public void setZ(final double z) { this.z = z; }

    public @Nullable Double getDeltaX() { return deltaX; }
    public @Nullable Double getDeltaY() { return deltaY; }
    public @Nullable Double getDeltaZ() { return deltaZ; }
    public void setDeltaX(final double deltaX) { this.deltaX = deltaX; }
    public void setDeltaY(final double deltaY) { this.deltaY = deltaY; }
    public void setDeltaZ(final double deltaZ) { this.deltaZ = deltaZ; }

    public void setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
    }

    public void setIncludesEntities(final boolean includesEntities) {
        this.includesEntities = includesEntities;
    }

    public EntitySelector.SortType getOrder() {
        return sort;
    }

    public void setOrder(final EntitySelector.SortType sort) {
        this.sort = sort;
    }

    public SelectorSetState nameOption() { return nameOption; }
    public SingleUseOption limitedOption() { return limitedOption; }
    public SingleUseOption sortedOption() { return sortedOption; }
    public SelectorSetState gamemodeOption() { return gamemodeOption; }
    public SelectorSetState typeOption() { return typeOption; }

    public CompletableFuture<Suggestions> fillSuggestions(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        return suggestions.apply(builder.createOffset(reader.getCursor()), names);
    }

    private CompletableFuture<Suggestions> suggestNameOrSelector(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        names.accept(builder);
        builder.suggest("@p"); builder.suggest("@a"); builder.suggest("@r"); builder.suggest("@s"); builder.suggest("@e"); builder.suggest("@n");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelector(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        final SuggestionsBuilder sub = builder.createOffset(builder.getStart() - 1);
        sub.suggest("@p"); sub.suggest("@a"); sub.suggest("@r"); sub.suggest("@s"); sub.suggest("@e"); sub.suggest("@n");
        builder.add(sub);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpenOptions(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        builder.suggest("[");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKey(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        EntitySelectorOptions.suggestNames(this, builder);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        builder.suggest("]");
        return suggestOptionsKey(builder, names);
    }

    private CompletableFuture<Suggestions> suggestOptionsNextOrClose(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        builder.suggest(","); builder.suggest("]");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestName(final SuggestionsBuilder builder, final Consumer<SuggestionsBuilder> names) {
        final SuggestionsBuilder sub = builder.createOffset(startPosition);
        names.accept(sub);
        return builder.add(sub).buildFuture();
    }
}
