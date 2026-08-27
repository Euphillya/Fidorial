package fr.euphyllia.fidorial.server.world.block;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.registry.keys.BlockTypeKeys;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockPlaceContext;
import fr.fidorial.world.block.BlockType;
import fr.fidorial.world.block.Blocks;
import fr.fidorial.world.block.data.Directional;
import fr.fidorial.world.block.data.Waterlogged;
import net.kyori.adventure.key.Key;

import java.util.Objects;

public final class EnderChestBlock implements BlockBehaviour {

    public static final EnderChestBlock INSTANCE = new EnderChestBlock();

    public static final Key KEY = BlockTypeKeys.ENDER_CHEST.key();

    public static final int LIGHT_EMISSION = 7;

    public static final int OBSIDIAN_DROPS = 8;

    private EnderChestBlock() {
    }

    public static boolean is(final BlockState state) {
        return KEY.equals(state.name());
    }

    public static boolean isBlockedAbove(final ServerWorld world, final BlockPos pos) {
        return ChestBlocks.isBlockedAbove(world, pos);
    }

    @Override
    public BlockType type() {
        return Objects.requireNonNull(Blocks.type(KEY));
    }

    @Override
    public BlockData placementState(final BlockPlaceContext context) {
        final BlockFace facing = context.horizontalFacing().opposite();
        BlockData state = Objects.requireNonNull(type().defaultData());
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
