package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.BlockPos;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;

public final class BlockEditService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(BlockEditService.class);

    private final BlockStateRegistry blockRegistry;
    private final BlockChangeBroadcaster broadcaster;
    private final FluidNotifier fluidNotifier;

    public BlockEditService(
            final BlockStateRegistry blockRegistry,
            final BlockChangeBroadcaster broadcaster,
            final FluidNotifier fluidNotifier
    ) {
        this.blockRegistry = blockRegistry;
        this.broadcaster = broadcaster;
        this.fluidNotifier = fluidNotifier;
    }

    public boolean set(final ServerWorld world, final BlockPos pos, final BlockState state) {
        try {
            if (!world.setBlock(pos.x(), pos.y(), pos.z(), state)) {
                return false;
            }
        } catch (final IOException e) {
            LOGGER.error("Changement de bloc impossible en {},{},{}", pos.x(), pos.y(), pos.z(), e);
            return false;
        }
        broadcaster.broadcast(pos, blockRegistry.networkId(state));
        fluidNotifier.notifyBlockChanged(world.dimension().id(), pos.x(), pos.y(), pos.z());
        return true;
    }

    @FunctionalInterface
    public interface BlockChangeBroadcaster {
        void broadcast(BlockPos pos, int stateId);
    }

    @FunctionalInterface
    public interface FluidNotifier {
        void notifyBlockChanged(Key world, int x, int y, int z);
    }
}
