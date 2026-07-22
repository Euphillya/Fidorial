package fr.euphyllia.fidorial.server.command.brigadier.argument.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.EntitySelectorOptions;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.InvertableSetOptionState;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options.SetOnceOptionState;
import fr.fidorial.entity.Entity;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class EntitySelectorParser {

    public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_OPTIONS = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.unterminated")));
    public static final DynamicCommandExceptionType ERROR_EXPECTED_OPTION_VALUE = new DynamicCommandExceptionType(
            name -> MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.valueless")
                    .append(Component.text(name.toString()))));
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_OPTION = new DynamicCommandExceptionType(
            name -> MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.unknown")
                    .append(Component.text(name.toString()))));
    public static final DynamicCommandExceptionType ERROR_INAPPLICABLE_OPTION = new DynamicCommandExceptionType(
            name -> MSG_SERIALIZER.serialize(Component.translatable("argument.entity.options.inapplicable")
                    .append(Component.text(name.toString()))));

    private static final SimpleCommandExceptionType INVALID =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.translatable("argument.entity.invalid")));

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

    private Double x;
    private Double y;
    private Double z;
    private Double deltaX;
    private Double deltaY;
    private Double deltaZ;

    private DoubleRange distance;

    private String targetName;
    private UUID targetUuid;

    private EntitySelector.SortType sort = EntitySelector.SortType.ARBITRARY;

    private final List<Predicate<Entity>> predicates = new ArrayList<>();

    private final InvertableSetOptionState nameOption = new InvertableSetOptionState();
    private final SetOnceOptionState limitedOption = new SetOnceOptionState();
    private final SetOnceOptionState sortedOption = new SetOnceOptionState();
    private final InvertableSetOptionState gamemodeOption = new InvertableSetOptionState();
    private final InvertableSetOptionState typeOption = new InvertableSetOptionState();

    public EntitySelectorParser(StringReader reader) {
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

            int start = reader.getCursor();
            String key = reader.readString();

            EntitySelectorOptions.Modifier modifier = EntitySelectorOptions.get(this, key, start);

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

        String value = reader.readString();
        if (value.isEmpty()) {
            throw INVALID.create();
        }

        try {
            targetUuid = UUID.fromString(value);
            includesEntities = true;
        } catch (IllegalArgumentException ex) {
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

    public void addPredicate(Predicate<Entity> predicate) {
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

    public void setSuggestions(SuggestionProviderLike provider) {
        this.suggestions = provider::apply;
    }

    @FunctionalInterface
    public interface SuggestionProviderLike {
        CompletableFuture<Suggestions> apply(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names);
    }

    public DoubleRange getDistance() {
        return distance;
    }

    public void setDistance(DoubleRange distance) {
        this.distance = distance;
    }

    public Double getX() { return x; }
    public Double getY() { return y; }
    public Double getZ() { return z; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }

    public Double getDeltaX() { return deltaX; }
    public Double getDeltaY() { return deltaY; }
    public Double getDeltaZ() { return deltaZ; }
    public void setDeltaX(double deltaX) { this.deltaX = deltaX; }
    public void setDeltaY(double deltaY) { this.deltaY = deltaY; }
    public void setDeltaZ(double deltaZ) { this.deltaZ = deltaZ; }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public void setIncludesEntities(boolean includesEntities) {
        this.includesEntities = includesEntities;
    }

    public EntitySelector.SortType getOrder() {
        return sort;
    }

    public void setOrder(EntitySelector.SortType sort) {
        this.sort = sort;
    }

    public InvertableSetOptionState nameOption() { return nameOption; }
    public SetOnceOptionState limitedOption() { return limitedOption; }
    public SetOnceOptionState sortedOption() { return sortedOption; }
    public InvertableSetOptionState gamemodeOption() { return gamemodeOption; }
    public InvertableSetOptionState typeOption() { return typeOption; }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        return suggestions.apply(builder.createOffset(reader.getCursor()), names);
    }

    private CompletableFuture<Suggestions> suggestNameOrSelector(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        names.accept(builder);
        builder.suggest("@p"); builder.suggest("@a"); builder.suggest("@r"); builder.suggest("@s"); builder.suggest("@e"); builder.suggest("@n");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        SuggestionsBuilder sub = builder.createOffset(builder.getStart() - 1);
        sub.suggest("@p"); sub.suggest("@a"); sub.suggest("@r"); sub.suggest("@s"); sub.suggest("@e"); sub.suggest("@n");
        builder.add(sub);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpenOptions(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        builder.suggest("[");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKey(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        EntitySelectorOptions.suggestNames(this, builder);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        builder.suggest("]");
        return suggestOptionsKey(builder, names);
    }

    private CompletableFuture<Suggestions> suggestOptionsNextOrClose(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        builder.suggest(","); builder.suggest("]");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        SuggestionsBuilder sub = builder.createOffset(startPosition);
        names.accept(sub);
        return builder.add(sub).buildFuture();
    }
}
