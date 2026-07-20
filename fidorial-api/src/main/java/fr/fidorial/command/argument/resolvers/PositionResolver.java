package fr.fidorial.command.argument.resolvers;

import fr.fidorial.command.CommandSource;
import fr.fidorial.world.Location;

public interface PositionResolver {

    Location resolve(CommandSource source);
}
