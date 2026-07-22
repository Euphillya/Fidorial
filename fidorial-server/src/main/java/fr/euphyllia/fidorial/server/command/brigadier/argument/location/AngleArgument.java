package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.argument.resolvers.AngleResolver;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collection;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class AngleArgument implements ArgumentType<AngleResolver> {

    private static final Collection<String> EXAMPLES = Arrays.asList("0", "~", "~-5");

    public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(
            MSG_SERIALIZER.serialize(Component.translatable("argument.angle.incomplete")));
    public static final SimpleCommandExceptionType ERROR_INVALID_ANGLE =
            new SimpleCommandExceptionType(MSG_SERIALIZER.serialize(Component.translatable("argument.angle.invalid")));

    public static AngleArgument angle() {
        return new AngleArgument();
    }

    @Override
    public AngleResolver parse(StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            throw ERROR_NOT_COMPLETE.createWithContext(reader);
        }

        boolean relative = reader.peek() == '~';
        if (relative) {
            reader.skip();
        }

        float value = reader.canRead() && reader.peek() != ' ' ? reader.readFloat() : 0.0F;

        if (Float.isNaN(value) || Float.isInfinite(value)) {
            throw ERROR_INVALID_ANGLE.createWithContext(reader);
        }

        return source -> {
            float baseYaw = relative ? source.location().yaw() : 0.0F;
            float result = (relative ? baseYaw + value : value) % 360.0F;
            if (result >= 180.0F) {
                result -= 360.0F;
            }
            if (result < -180.0F) {
                result += 360.0F;
            }
            return result;
        };
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<AngleArgument, Info.Spec> {

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
        public Spec access(AngleArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<AngleArgument> {
            @Override
            public AngleArgument instantiate() {
                return AngleArgument.angle();
            }

            @Override
            public ArgumentTypeRegistrar<AngleArgument, ?> type() {
                return new Info();
            }
        }
    }
}
