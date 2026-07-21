package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class NamedColorArgument implements ArgumentType<NamedTextColor> {

    private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");

    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.color.invalid", Component.text(String.valueOf(value)))));

    public static NamedColorArgument namedColor() {
        return new NamedColorArgument();
    }

    @Override
    public NamedTextColor parse(StringReader reader) throws CommandSyntaxException {
        String id = reader.readUnquotedString();
        NamedTextColor result = NamedTextColor.NAMES.value(id);
        if (result == null) {
            throw ERROR_INVALID_VALUE.createWithContext(reader, id);
        }
        return result;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (NamedTextColor color : NamedTextColor.NAMES.values()) {
            builder.suggest(NamedTextColor.NAMES.key(color));
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<NamedColorArgument, Info.Spec> {

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
        public Spec access(NamedColorArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<NamedColorArgument> {
            @Override
            public NamedColorArgument instantiate() {
                return NamedColorArgument.namedColor();
            }

            @Override
            public ArgumentTypeRegistrar<NamedColorArgument, ?> type() {
                return new Info();
            }
        }
    }
}
