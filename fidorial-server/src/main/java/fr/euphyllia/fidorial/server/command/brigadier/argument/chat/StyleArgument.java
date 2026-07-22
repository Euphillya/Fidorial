package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class StyleArgument implements ArgumentType<Style> {

    private static final Collection<String> EXAMPLES = List.of("{\"bold\":true}", "{\"color\":\"red\"}", "{}");
    private static final Set<String> CONTENT_KEYS = Set.of("text", "translate", "selector", "score", "keybind", "nbt");

    public static final DynamicCommandExceptionType ERROR_INVALID_STYLE = new DynamicCommandExceptionType(
            message -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.style.invalid")
                            .append(Component.text(message.toString()))));

    private StyleArgument() {
    }

    public static StyleArgument style() {
        return new StyleArgument();
    }

    @Override
    public Style parse(StringReader reader) throws CommandSyntaxException {
        String raw = AdventureJsonReader.readRawJson(reader, ERROR_INVALID_STYLE);

        JsonElement element;
        try {
            element = JsonParser.parseString(raw);
        } catch (JsonParseException ex) {
            throw ERROR_INVALID_STYLE.createWithContext(reader, raw);
        }

        if (!(element instanceof JsonObject object)) {
            throw ERROR_INVALID_STYLE.createWithContext(reader, raw);
        }

        boolean hasContent = CONTENT_KEYS.stream().anyMatch(object::has);
        if (!hasContent) {
            object.addProperty("text", "");
        }

        try {
            Component component = GsonComponentSerializer.gson().deserializeFromTree(object);
            return component.style();
        } catch (JsonParseException | IllegalArgumentException ex) {
            throw ERROR_INVALID_STYLE.createWithContext(reader, raw);
        }
    }

    public static Style getStyle(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, Style.class);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<StyleArgument, Info.Spec> {

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
        public Spec access(StyleArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<StyleArgument> {
            @Override
            public StyleArgument instantiate() {
                return new StyleArgument();
            }

            @Override
            public ArgumentTypeRegistrar<StyleArgument, ?> type() {
                return new Info();
            }
        }
    }
}
