package fr.euphyllia.fidorial.server.command.brigadier.argument.location;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public record LocalCoord(double value) {

    static LocalCoord parse(final StringReader reader)
            throws CommandSyntaxException {

        reader.expect('^');

        if (!reader.canRead() || reader.peek() == ' ') {
            return new LocalCoord(0);
        }

        return new LocalCoord(reader.readDouble());
    }
}
