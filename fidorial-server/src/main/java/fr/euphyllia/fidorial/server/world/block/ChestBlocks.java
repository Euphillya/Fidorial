package fr.euphyllia.fidorial.server.world.block;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.world.BlockPos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class ChestBlocks {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ChestBlocks.class);

    public static boolean isBlockedAbove(final ServerWorld world, final BlockPos pos) {
        try {
            final var above = world.getBlock(pos.x(), pos.y() + 1, pos.z());
            return !above.isAir() && !above.isFluid();
        } catch (final Exception exception) {
            LOGGER.error("Failed to check if block is blocked above", exception);
            return false;
        }
    }
}
