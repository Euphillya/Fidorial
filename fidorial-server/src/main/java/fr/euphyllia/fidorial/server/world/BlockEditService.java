package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.BlockPos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;

public final class BlockEditService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(BlockEditService.class);

    private final BlockStateRegistry blockRegistry;
    private final BlockChangeBroadcaster broadcaster;
    private final FluidNotifier fluidNotifier;

    public BlockEditService(
            BlockStateRegistry blockRegistry,
            BlockChangeBroadcaster broadcaster,
            FluidNotifier fluidNotifier
    ) {
        this.blockRegistry = blockRegistry;
        this.broadcaster = broadcaster;
        this.fluidNotifier = fluidNotifier;
    }

    public boolean set(ServerWorld world, BlockPos pos, BlockState state) {
        try {
            if (!world.setBlock(pos.x(), pos.y(), pos.z(), state)) {
                return false;
            }
        } catch (IOException e) {
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
        void notifyBlockChanged(String world, int x, int y, int z);
    }
}
