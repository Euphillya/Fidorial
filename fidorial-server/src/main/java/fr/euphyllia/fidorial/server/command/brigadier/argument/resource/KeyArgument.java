package fr.euphyllia.fidorial.server.command.brigadier.argument.resource;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class KeyArgument implements ArgumentType<Key> {

    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");

    public static final SimpleCommandExceptionType ERROR_INVALID_KEY =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.translatable("argument.id.invalid")));

    public static KeyArgument key() {
        return new KeyArgument();
    }

    @Override
    public Key parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();

        while (reader.canRead() && isAllowedInKey(reader.peek())) {
            reader.skip();
        }

        String input = reader.getString().substring(start, reader.getCursor());

        if (!Key.parseable(input)) {
            reader.setCursor(start);
            throw ERROR_INVALID_KEY.createWithContext(reader);
        }

        return Key.key(input);
    }

    private boolean isAllowedInKey(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == ':' || c == '/';
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<KeyArgument, Info.Spec> {

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
        public Spec access(KeyArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<KeyArgument> {
            @Override
            public KeyArgument instantiate() {
                return KeyArgument.key();
            }

            @Override
            public ArgumentTypeRegistrar<KeyArgument, ?> type() {
                return new Info();
            }
        }
    }
}
