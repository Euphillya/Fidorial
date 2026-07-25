package fr.euphyllia.fidorial.server.command.brigadier.argument.item;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.entity.ItemStack;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class ItemPredicateArgument implements ArgumentType<Predicate<ItemStack>> {

    private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick");

    public static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM =
            new DynamicCommandExceptionType(id -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.item.id.invalid", Component.text(String.valueOf(id)))));

    public static final SimpleCommandExceptionType ERROR_TAGS_UNSUPPORTED =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.text("Item tags are not yet supported")));

    public static ItemPredicateArgument itemPredicate() {
        return new ItemPredicateArgument();
    }

    private boolean exists(Key key) {
        return ItemKeys.values().anyMatch(item -> item.key().equals(key));
    }

    @Override
    public Predicate<ItemStack> parse(StringReader reader) throws CommandSyntaxException {
        if (reader.canRead() && reader.peek() == '#') {
            // TBD
            throw ERROR_TAGS_UNSUPPORTED.createWithContext(reader);
        }

        int start = reader.getCursor();

        while (reader.canRead() && isAllowedInKey(reader.peek())) {
            reader.skip();
        }

        String input = reader.getString().substring(start, reader.getCursor());
        Key key = input.contains(":") ? Key.key(input) : Key.key("minecraft", input);

        if (!exists(key)) {
            reader.setCursor(start);
            throw ERROR_UNKNOWN_ITEM.createWithContext(reader, key.asString());
        }

        // TODO: ("[key=value,...]") are not parsed yet.
        if (reader.canRead() && reader.peek() == '[') {
            while (reader.canRead() && reader.peek() != ']') {
                reader.skip();
            }
            if (reader.canRead()) reader.skip();
        }

        return stack -> !stack.isEmpty() && stack.id().equals(key);
    }

    private boolean isAllowedInKey(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == ':' || c == '/';
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        ItemKeys.values()
                .map(TypedKey::key)
                .map(Key::asString)
                .filter(id -> id.contains(remaining))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<ItemPredicateArgument, Info.Spec> {

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
        public Spec access(ItemPredicateArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<ItemPredicateArgument> {
            @Override
            public ItemPredicateArgument instantiate() {
                return ItemPredicateArgument.itemPredicate();
            }

            @Override
            public ArgumentTypeRegistrar<ItemPredicateArgument, ?> type() {
                return new Info();
            }
        }
    }
}
