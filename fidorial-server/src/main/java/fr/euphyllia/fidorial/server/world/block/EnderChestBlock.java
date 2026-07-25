package fr.euphyllia.fidorial.server.world.block;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;

import java.io.IOException;
import java.util.Map;

/**
 * Vanilla rules for the {@code minecraft:ender_chest} block.
 *
 * <p>The block carries two properties: {@code facing} (north, south, west, east) and
 * {@code waterlogged}. Its contents are not stored here but in the player's data, which avoids the
 * need for a block-entity system.
 *
 * <p>https://minecraft.wiki/w/Ender_Chest
 */
public final class EnderChestBlock {

    public static final String ID = "minecraft:ender_chest";

    /** Light level emitted by the block. */
    public static final int LIGHT_LEVEL = 7;

    /** Number of obsidian dropped when the block is broken without Silk Touch. */
    public static final int OBSIDIAN_DROPS = 8;

    private EnderChestBlock() {}

    public static boolean is(final BlockState state) {
        return ID.equals(state.name());
    }

    /**
     * State to place depending on the player's orientation.
     *
     * <p>The {@code facing} property points to the face where the latch is: it is opposite to the
     * direction the player is looking when placing, so the chest faces them.
     */
    public static BlockState placedBy(final Location placer, final boolean waterlogged) {
        return new BlockState(
                ID,
                Map.of(
                        "facing", oppositeOfFacing(placer.yaw()),
                        "waterlogged", Boolean.toString(waterlogged)));
    }

    private static String oppositeOfFacing(final float yaw) {
        // Yaw grows clockwise: 0 = south, 90 = west, 180 = north, 270 = east.
        final int quadrant = Math.floorMod(Math.round(yaw / 90f), 4);
        return switch (quadrant) {
            case 0 -> "north"; // player facing south -> latch to the north
            case 1 -> "east";
            case 2 -> "south";
            default -> "west";
        };
    }

    /**
     * A chest cannot be opened if a solid block obstructs it from above.
     *
     * <p>Fidorial has no notion of block occlusion yet: we simply check that the spot above is
     * neither air nor a fluid, which covers the common case.
     */
    public static boolean isBlockedAbove(final ServerWorld world, final BlockPos pos) {
        try {
            final BlockState above = world.getBlock(pos.x(), pos.y() + 1, pos.z());
            return !above.isAir() && !isFluid(above);
        } catch (final IOException exception) {
            return false; // when in doubt, allow opening rather than blocking the player
        }
    }

    private static boolean isFluid(final BlockState state) {
        return "minecraft:water".equals(state.name()) || "minecraft:lava".equals(state.name());
    }
}