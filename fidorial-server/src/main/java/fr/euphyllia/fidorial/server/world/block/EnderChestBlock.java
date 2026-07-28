package fr.euphyllia.fidorial.server.world.block;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockPlaceContext;
import fr.fidorial.world.block.BlockProperties;
import fr.fidorial.world.block.BlockType;
import fr.fidorial.world.block.data.Directional;
import fr.fidorial.world.block.data.Waterlogged;
import net.kyori.adventure.key.Key;

import java.io.IOException;
import java.util.Objects;


public final class EnderChestBlock implements BlockBehaviour {

    public static final EnderChestBlock INSTANCE = new EnderChestBlock();

    public static final Key KEY = Key.key("minecraft", "ender_chest");

    public static final int FIRST_STATE_ID = 9575;

    public static final int LIGHT_EMISSION = 7;

    public static final int OBSIDIAN_DROPS = 8;

    public static final BlockType TYPE = BlockType.builder(KEY)
            .property(BlockProperties.HORIZONTAL_FACING)
            .property(BlockProperties.WATERLOGGED)
            .firstStateId(FIRST_STATE_ID)
            .defaultValue("facing", "north")
            .defaultValue("waterlogged", "false")
            .build();

    private EnderChestBlock() {}

    public static boolean is(final BlockState state) {
        return KEY.asString().equals(state.name());
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

    @Override
    public BlockType type() {
        return TYPE;
    }

    @Override
    public BlockData placementState(final BlockPlaceContext context) {
        final BlockFace facing = context.horizontalFacing().opposite();
        BlockData state = Objects.requireNonNull(TYPE.defaultData());
        state = ((Directional) state).setFacing(facing);
        state = ((Waterlogged) state).setWaterlogged(context.intoWater());
        return state;
    }

    @Override
    public int lightEmission(final BlockData data) {
        return LIGHT_EMISSION;
    }

    @Override
    public int lightOpacity(final BlockData data) {
        return 0;
    }
}