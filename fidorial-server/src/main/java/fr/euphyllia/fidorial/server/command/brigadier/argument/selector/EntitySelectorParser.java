package fr.euphyllia.fidorial.server.command.brigadier.argument.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
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
    private int startPosition;
    private SuggestionProvider suggestions = SuggestionProvider.NONE;

    @FunctionalInterface
    private interface SuggestionProvider {
        CompletableFuture<Suggestions> apply(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names);

        SuggestionProvider NONE = (b, n) -> b.buildFuture();
    }

    private static final SimpleCommandExceptionType INVALID =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.translatable("argument.entity.invalid")));

    private final StringReader reader;

    private int maxResults = 1;
    private boolean includesEntities;
    private boolean selfSelector;
    private boolean usesSelector;

    private double x;
    private double y;
    private double z;

    private Double minDistance;
    private Double maxDistance;

    private Integer dx;
    private Integer dy;
    private Integer dz;

    private String targetName;
    private UUID targetUuid;

    private EntitySelector.SortType sort = EntitySelector.SortType.ARBITRARY;

    private final List<Predicate<Entity>> predicates = new ArrayList<>();

    public EntitySelectorParser(StringReader reader) {
        this.reader = reader;
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
            case 's' -> {
                selfSelector = true;
                includesEntities = false;
                maxResults = 1;
            }

            case 'p' -> {
                includesEntities = false;
                maxResults = 1;
                sort = EntitySelector.SortType.NEAREST;
            }

            case 'a' -> {
                includesEntities = false;
                maxResults = Integer.MAX_VALUE;
            }

            case 'e' -> {
                includesEntities = true;
                maxResults = Integer.MAX_VALUE;
            }

            case 'r' -> {
                includesEntities = false;
                maxResults = 1;
                sort = EntitySelector.SortType.RANDOM;
            }

            case 'n' -> {
                includesEntities = true;
                maxResults = 1;
                sort = EntitySelector.SortType.NEAREST;
            }

            default -> throw INVALID.create();
        }

        // suggestions = this::suggestOpenOptions;

        if (reader.canRead() && reader.peek() == '[') {
            reader.skip();
            // suggestions = this::suggestOptionsKeyOrClose;
            parseArguments();
        }
    }

    private void parseArguments() throws CommandSyntaxException {

        // suggestions = this::suggestOptionsKey;

        while (reader.canRead() && reader.peek() != ']') {
            reader.skipWhitespace();

            int start = reader.getCursor();
            String key = reader.readString();

            // Option option = EntitySelectorOptions.get(key);

            reader.skipWhitespace();

            if (!reader.canRead() || reader.peek() != '=') {
                reader.setCursor(start);
                throw ERROR_EXPECTED_OPTION_VALUE.createWithContext(reader, key);
            }

            reader.skip();
            reader.skipWhitespace();

            suggestions = SuggestionProvider.NONE;

            // option.parse(this); // reads directly from the StringReader

            reader.skipWhitespace();

            // suggestions = this::suggestOptionsNextOrClose;

            if (reader.canRead()) {
                if (reader.peek() == ',') {
                    reader.skip();
                    // suggestions = this::suggestOptionsKey;
                } else if (reader.peek() != ']') {
                    throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(reader);
                }
            }
        }
    }

    private void apply(String key, String value) throws CommandSyntaxException {

        switch (key) {
            case "limit" -> maxResults = Integer.parseInt(value);

            case "sort" -> sort = EntitySelector.SortType.valueOf(value.toUpperCase());

            case "x" -> x = Double.parseDouble(value);

            case "y" -> y = Double.parseDouble(value);

            case "z" -> z = Double.parseDouble(value);

            case "distance" -> {
                String[] split = value.split("\\.\\.");

                if (split.length == 1) {
                    minDistance = Double.parseDouble(split[0]);
                    maxDistance = minDistance;
                } else {
                    minDistance = split[0].isEmpty() ? null : Double.parseDouble(split[0]);

                    maxDistance = split[1].isEmpty() ? null : Double.parseDouble(split[1]);
                }
            }

            case "dx" -> dx = Integer.parseInt(value);

            case "dy" -> dy = Integer.parseInt(value);

            case "dz" -> dz = Integer.parseInt(value);

            case "type" -> {
                boolean inverted = value.startsWith("!");

                String type = inverted ? value.substring(1) : value;

                predicates.add(entity -> {
                    boolean result = entity.type().key().value().equals(type);

                    return inverted != result;
                });
            }

            case "name" -> {
                boolean inverted = value.startsWith("!");

                String name = inverted ? value.substring(1) : value;

                predicates.add(entity -> {
                    boolean result =
                            entity instanceof Player player && player.name().equalsIgnoreCase(name);

                    return inverted != result;
                });
            }

            case "gamemode" -> {
                boolean inverted = value.startsWith("!");

                String mode = inverted ? value.substring(1) : value;

                predicates.add(entity -> {
                    if (!(entity instanceof Player player)) return false;

                    boolean result = player.gameMode().name().equalsIgnoreCase(mode);

                    return inverted != result;
                });
            }

            case "tag" -> {
                boolean inverted = value.startsWith("!");

                String tag = inverted ? value.substring(1) : value;

                predicates.add(_ -> {
                    boolean result = false; // we dont have tags yet

                    return inverted != result;
                });
            }

            default -> throw INVALID.create();
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
                x,
                y,
                z,
                minDistance,
                maxDistance,
                dx,
                dy,
                dz,
                sort,
                targetName,
                targetUuid);
    }

    public CompletableFuture<Suggestions> fillSuggestions(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        return suggestions.apply(builder.createOffset(reader.getCursor()), names);
    }

    private CompletableFuture<Suggestions> suggestNameOrSelector(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        names.accept(builder);

        builder.suggest("@p");
        builder.suggest("@a");
        builder.suggest("@r");
        builder.suggest("@s");
        builder.suggest("@e");
        builder.suggest("@n");

        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelector(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        SuggestionsBuilder sub = builder.createOffset(builder.getStart() - 1);

        sub.suggest("@p");
        sub.suggest("@a");
        sub.suggest("@r");
        sub.suggest("@s");
        sub.suggest("@e");

        builder.add(sub);

        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpenOptions(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        builder.suggest("[");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKey(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        /*
        builder.suggest("limit=");
        builder.suggest("sort=");
        builder.suggest("distance=");
        builder.suggest("type=");
        builder.suggest("gamemode=");
        builder.suggest("name=");
        builder.suggest("tag=");
        builder.suggest("x=");
        builder.suggest("y=");
        builder.suggest("z=");
        builder.suggest("dx=");
        builder.suggest("dy=");
        builder.suggest("dz=");
        */
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        builder.suggest("]");
        return suggestOptionsKey(builder, names);
    }

    private CompletableFuture<Suggestions> suggestOptionsNextOrClose(
            SuggestionsBuilder builder,
            Consumer<SuggestionsBuilder> names
    ) {
        builder.suggest(",");
        builder.suggest("]");
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> names) {
        SuggestionsBuilder sub = builder.createOffset(startPosition);
        names.accept(sub);
        return builder.add(sub).buildFuture();
    }

    private String read(char... end) {

        int start = reader.getCursor();

        while (reader.canRead()) {
            char c = reader.peek();

            if (Character.isWhitespace(c)) {
                break;
            }

            for (char e : end) {
                if (c == e) {
                    return reader.getString().substring(start, reader.getCursor());
                }
            }

            reader.skip();
        }

        return reader.getString().substring(start);
    }
}
