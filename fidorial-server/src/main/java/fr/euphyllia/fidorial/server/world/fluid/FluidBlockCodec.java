package fr.euphyllia.fidorial.server.world.fluid;

import fr.euphyllia.fidorial.api.world.fluid.FluidState;
import fr.euphyllia.fidorial.api.world.fluid.FluidType;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;

import java.util.Map;

public class FluidBlockCodec {

    private static final String LEVEL = "level";
    private static final int FALLING_OFFSET = 8;

    private FluidBlockCodec() {
    }

    public static FluidState fromBlock(BlockState block) {
        if (block == null) {
            return FluidState.empty();
        }
        FluidType type = FluidType.byBlockKey(block.name());
        if (type == null) {
            return FluidState.empty();
        }
        String raw = block.properties().get(LEVEL);
        int level = 0;
        if (raw != null) {
            try {
                level = Integer.parseInt(raw);
            } catch (NumberFormatException ignored) {
            }
        }
        if (level >= FALLING_OFFSET) {
            return FluidState.fallingFluid(type);
        }
        return new FluidState(type, level, false);
    }

    public static BlockState toBlock(FluidState state) {
        if (state.isEmpty()) {
            return BlockState.AIR;
        }
        int level = state.falling() ? FALLING_OFFSET : clamp(state.level());
        return new BlockState(state.type().blockKey().asString(),
                Map.of(LEVEL, String.valueOf(level)));
    }

    private static int clamp(int level) {
        return Math.clamp(level, 0, 7);
    }
}
