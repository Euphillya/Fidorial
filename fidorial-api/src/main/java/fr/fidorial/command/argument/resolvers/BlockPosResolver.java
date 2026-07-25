package fr.fidorial.command.argument.resolvers;

import fr.fidorial.command.CommandSender;
import fr.fidorial.world.BlockPos;
import org.jetbrains.annotations.ApiStatus;

/**
 * An {@link ArgumentResolver} that's capable of resolving
 * a block position argument value using a {@link CommandSender}.
 */
@ApiStatus.NonExtendable
public interface BlockPosResolver extends ArgumentResolver<BlockPos> {
}
