package fr.euphyllia.fidorial.server.world.light;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;

public class BlockLightProperties {

    public static final int OPAQUE = 15;

    /**
     *
     * @param state
     * @return the opacity (0-15). {@link #OPAQUE} for a solid cube that blocks light.
     */
    public static int opacity(final BlockState state) {
        if (state.isAir()) {
            return 0;
        }
        return OPAQUE;
    }

    public static boolean occludes(final BlockState state) {
        return opacity(state) >= OPAQUE;
    }

    /**
     * Light from a block
     *
     * @param state
     * @return
     */
    public static int emission(final BlockState state) {
        return 0;
    }

    private static boolean isTransparentShape(final String name) {
        return false;
    }
}
