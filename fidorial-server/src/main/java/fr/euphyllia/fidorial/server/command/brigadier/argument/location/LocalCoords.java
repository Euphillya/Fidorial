package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.world.CoordMath;
import fr.fidorial.command.CommandSource;
import fr.fidorial.world.Location;

public record LocalCoords(LocalCoord x, LocalCoord y, LocalCoord z) {

    public static LocalCoords parse(StringReader reader)
            throws CommandSyntaxException {

        LocalCoord x = LocalCoord.parse(reader);
        reader.expect(' ');
        LocalCoord y = LocalCoord.parse(reader);
        reader.expect(' ');
        LocalCoord z = LocalCoord.parse(reader);

        return new LocalCoords(x, y, z);
    }

    public Location resolve(CommandSource source) {
        return CoordMath.applyLocalCoords(
                source.location(),
                x.value(),
                y.value(),
                z.value()
        );
    }
}
