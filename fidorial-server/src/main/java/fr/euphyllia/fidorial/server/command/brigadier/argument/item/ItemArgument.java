package fr.euphyllia.fidorial.server.command.brigadier.argument.item;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class ItemArgument implements ArgumentType<ItemArgument.ItemInput> {

    private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick");

    public static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM =
            new DynamicCommandExceptionType(id -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.item.id.invalid", Component.text(String.valueOf(id)))));

    public static ItemArgument item() {
        return new ItemArgument();
    }

    private boolean exists(Key key) {
        return ItemKeys.values().anyMatch(item -> item.key().equals(key));
    }

    @Override
    public ItemInput parse(StringReader reader) throws CommandSyntaxException {
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

        // TODO: ("[key=value,...]") is not parsed yet.
        if (reader.canRead() && reader.peek() == '[') {
            while (reader.canRead() && reader.peek() != ']') {
                reader.skip();
            }
            if (reader.canRead()) reader.skip();
        }

        return new ItemInput(key);
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

    public record ItemInput(Key id) {
        public fr.euphyllia.fidorial.server.entity.ItemStack createItemStack(int count) {
            return fr.euphyllia.fidorial.server.entity.ItemStack.of(id, count);
        }
    }

    public static final class Info implements ArgumentTypeRegistrar<ItemArgument, Info.Spec> {

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
        public Spec access(ItemArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<ItemArgument> {
            @Override
            public ItemArgument instantiate() {
                return ItemArgument.item();
            }

            @Override
            public ArgumentTypeRegistrar<ItemArgument, ?> type() {
                return new Info();
            }
        }
    }
}
