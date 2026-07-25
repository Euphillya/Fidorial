package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.argument.resolvers.BlockPosResolver;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;

import java.util.Arrays;
import java.util.Collection;

public final class BlockPositionArgument implements ArgumentType<BlockPosResolver> {

    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "~1 ~ ~-5");

    public static BlockPositionArgument blockPosition() {
        return new BlockPositionArgument();
    }

    @Override
    public BlockPosResolver parse(StringReader reader) throws CommandSyntaxException {
        Coordinate x = Coordinate.parse(reader);
        reader.expect(' ');
        Coordinate y = Coordinate.parse(reader);
        reader.expect(' ');
        Coordinate z = Coordinate.parse(reader);

        return source -> {
            Location origin = source.location();

            double px = x.resolve(origin.x());
            double py = y.resolve(origin.y());
            double pz = z.resolve(origin.z());

            return new BlockPos((int) Math.floor(px), (int) Math.floor(py), (int) Math.floor(pz));
        };
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private record Coordinate(double value, boolean relative) {

        static Coordinate parse(StringReader reader) throws CommandSyntaxException {
            boolean relative = false;

            if (reader.canRead() && reader.peek() == '~') {
                relative = true;
                reader.skip();

                if (!reader.canRead() || reader.peek() == ' ') {
                    return new Coordinate(0, true);
                }
            }

            double value = reader.readDouble();
            return new Coordinate(value, relative);
        }

        double resolve(double origin) {
            return relative ? origin + value : value;
        }
    }

    public static final class Info implements ArgumentTypeRegistrar<BlockPositionArgument, Info.Spec> {

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
        public Spec access(BlockPositionArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<BlockPositionArgument> {
            @Override
            public BlockPositionArgument instantiate() {
                return BlockPositionArgument.blockPosition();
            }

            @Override
            public ArgumentTypeRegistrar<BlockPositionArgument, ?> type() {
                return new Info();
            }
        }
    }
}
