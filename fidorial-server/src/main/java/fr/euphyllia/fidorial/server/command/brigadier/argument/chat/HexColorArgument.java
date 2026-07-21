package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class HexColorArgument implements ArgumentType<Integer> {

    private static final Collection<String> EXAMPLES = Arrays.asList("F00", "FF0000");

    public static final DynamicCommandExceptionType ERROR_INVALID_HEX =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.hexcolor.invalid", Component.text(String.valueOf(value)))));

    public static HexColorArgument hexColor() {
        return new HexColorArgument();
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        String colorString = reader.readUnquotedString();

        return switch (colorString.length()) {
            case 3 ->
                rgb(
                        duplicate(hexDigit(colorString, 0)),
                        duplicate(hexDigit(colorString, 1)),
                        duplicate(hexDigit(colorString, 2)));
            case 6 ->
                rgb(
                        Integer.parseInt(colorString.substring(0, 2), 16),
                        Integer.parseInt(colorString.substring(2, 4), 16),
                        Integer.parseInt(colorString.substring(4, 6), 16));
            default -> throw ERROR_INVALID_HEX.createWithContext(reader, colorString);
        };
    }

    private static int hexDigit(String s, int index) {
        return Integer.parseInt(s.substring(index, index + 1), 16);
    }

    private static int duplicate(int digit) {
        return digit * 17;
    }

    private static int rgb(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            com.mojang.brigadier.context.CommandContext<S> context,
            SuggestionsBuilder builder
    ) {
        for (String example : EXAMPLES) {
            builder.suggest(example);
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<HexColorArgument, Info.Spec> {

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
        public Spec access(HexColorArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<HexColorArgument> {
            @Override
            public HexColorArgument instantiate() {
                return HexColorArgument.hexColor();
            }

            @Override
            public ArgumentTypeRegistrar<HexColorArgument, ?> type() {
                return new Info();
            }
        }
    }
}
