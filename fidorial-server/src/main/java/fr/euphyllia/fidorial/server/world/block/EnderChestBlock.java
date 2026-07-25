package fr.euphyllia.fidorial.server.world.block;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;

import java.io.IOException;
import java.util.Map;

public final class EnderChestBlock {

    public static final String ID = "minecraft:ender_chest";

    public static final int LIGHT_LEVEL = 7;

    public static final int OBSIDIAN_DROPS = 8;

    private EnderChestBlock() {}

    public static boolean is(final BlockState state) {
        return ID.equals(state.name());
    }

    public static BlockState placedBy(final Location placer, final boolean waterlogged) {
        return new BlockState(
                ID,
                Map.of(
                        "facing", oppositeOfFacing(placer.yaw()),
                        "waterlogged", Boolean.toString(waterlogged)));
    }

    private static String oppositeOfFacing(final float yaw) {
        final int quadrant = Math.floorMod(Math.round(yaw / 90f), 4);
        return switch (quadrant) {
            case 0 -> "north";
            case 1 -> "east";
            case 2 -> "south";
            default -> "west";
        };
    }

    public static boolean isBlockedAbove(final ServerWorld world, final BlockPos pos) {
        try {
            final BlockState above = world.getBlock(pos.x(), pos.y() + 1, pos.z());
            return !above.isAir() && !isFluid(above);
        } catch (final IOException exception) {
            return false;
        }
    }

    private static boolean isFluid(final BlockState state) {
        return "minecraft:water".equals(state.name()) || "minecraft:lava".equals(state.name());
    }
}