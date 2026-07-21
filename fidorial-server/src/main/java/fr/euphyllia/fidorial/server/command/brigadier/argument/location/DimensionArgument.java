package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class DimensionArgument implements ArgumentType<Key> {

    private static final Collection<String> EXAMPLES = Arrays.asList("minecraft:overworld", "minecraft:the_nether");

    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
            new DynamicCommandExceptionType(value -> MSG_SERIALIZER.serialize(
                    Component.translatable("argument.dimension.invalid", Component.text(value.toString()))));

    public static DimensionArgument dimension() {
        return new DimensionArgument();
    }

    @Override
    public Key parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();

        while (reader.canRead() && isAllowedInKey(reader.peek())) {
            reader.skip();
        }

        String input = reader.getString().substring(start, reader.getCursor());
        String full = input.contains(":") ? input : "minecraft:" + input;

        if (!Key.parseable(full)) {
            reader.setCursor(start);
            throw ERROR_INVALID_VALUE.create(full);
        }

        return Key.key(full);
    }

    private boolean isAllowedInKey(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == ':' || c == '/';
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (World world : FidorialServer.getInstance().worldManager().worlds()) {
            builder.suggest(world.key().asString());
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<DimensionArgument, Info.Spec> {

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
        public Spec access(DimensionArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<DimensionArgument> {
            @Override
            public DimensionArgument instantiate() {
                return DimensionArgument.dimension();
            }

            @Override
            public ArgumentTypeRegistrar<DimensionArgument, ?> type() {
                return new Info();
            }
        }
    }
}
