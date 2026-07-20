package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.resolvers.PositionResolver;
import fr.fidorial.world.Location;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class Vec3Argument implements ArgumentType<PositionResolver> {

    private static final Collection<String> EXAMPLES = List.of(
            "0 0 0",
            "~ ~ ~",
            "~1 ~ ~-5"
    );

    private final boolean centerCorrect;

    public Vec3Argument(boolean centerCorrect) {
        this.centerCorrect = centerCorrect;
    }

    public static Vec3Argument vec3() {
        return new Vec3Argument(true);
    }

    public static Location getPosition(
            CommandContext<CommandSource> context,
            String name
    ) {
        return context.getArgument(name, PositionResolver.class)
                .resolve(context.getSource());
    }


    @Override
    public PositionResolver parse(StringReader reader)
            throws CommandSyntaxException {

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

            if (centerCorrect && !x.relative() && !y.relative() && !z.relative()) {
                px += 0.5;
                pz += 0.5;
            }

            return new Location(
                    px,
                    py,
                    pz,
                    origin.yaw(),
                    origin.pitch()
            );
        };
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            CommandContext<S> context,
            SuggestionsBuilder builder
    ) {
        if (context.getSource() instanceof CommandSource source
                && source.sender() instanceof ServerPlayer player) {

            String remaining = builder.getRemaining();

            int spaces = (int) remaining.chars()
                    .filter(c -> c == ' ')
                    .count();

            Location loc = source.location();

            boolean relative = player.isFlying();

            SuggestionsBuilder coordinateBuilder = offsetToCurrentCoordinate(builder);

            switch (spaces) {
                case 0 -> {
                    if (relative) {
                        coordinateBuilder.suggest("~ ~ ~");
                    } else {
                        coordinateBuilder.suggest(String.format(
                                "%.2f %.2f %.2f",
                                loc.x(),
                                loc.y(),
                                loc.z()
                        ));
                    }
                }

                case 1 -> {
                    if (relative) {
                        coordinateBuilder.suggest("~ ~");
                    } else {
                        coordinateBuilder.suggest(String.format(
                                "%.2f %.2f",
                                loc.y(),
                                loc.z()
                        ));
                    }
                }

                case 2 -> {
                    if (relative) {
                        coordinateBuilder.suggest("~");
                    } else {
                        coordinateBuilder.suggest(String.format(
                                "%.2f",
                                loc.z()
                        ));
                    }
                }
            }

            return coordinateBuilder.buildFuture();
        }

        return builder.buildFuture();
    }

    private SuggestionsBuilder offsetToCurrentCoordinate(SuggestionsBuilder builder) {
        String input = builder.getInput();
        int start = builder.getStart();

        for (int i = input.length() - 1; i >= start; i--) {
            if (input.charAt(i) == ' ') {
                return builder.createOffset(i + 1);
            }
        }

        return builder;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }


    private record Coordinate(
            double value,
            boolean relative
    ) {

        static Coordinate parse(StringReader reader)
                throws CommandSyntaxException {

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
}
