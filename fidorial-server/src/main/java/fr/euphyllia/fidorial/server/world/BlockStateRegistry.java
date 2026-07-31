package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockGetter;
import fr.fidorial.world.block.BlockPlaceContext;
import fr.fidorial.world.block.BlockRegistry;
import fr.fidorial.world.block.BlockType;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.Map;


public record BlockStateRegistry(BlockRegistry registry) {

    private static final int AIR_BLOCK = 0;

    public int networkId(final BlockState state) {
        final BlockData data = resolve(state);
        return data == null ? AIR_BLOCK : data.networkId();
    }

    public BlockState byId(final int networkId) {
        final BlockData data = registry.fromNetworkId(networkId);
        if (data == null) {
            return BlockState.AIR;
        }
        return new BlockState(data.key(), data.propertyMap());
    }

    public boolean contains(final BlockState state) {
        return resolve(state) != null;
    }

    public BlockState toBlockState(final BlockData data) {
        return new BlockState(data.key(), data.propertyMap());
    }

    @SuppressWarnings("PatternValidation")
    public @Nullable BlockState placementState(final BlockState state, final BlockPlaceContext context) {
        final BlockBehaviour behaviour = registry.behaviour(state.name()).orElse(null);
        if (behaviour == null) {
            return state;
        }
        final BlockData placed = behaviour.placementState(context);
        return placed == null ? null : toBlockState(placed);
    }

    public BlockGetter view(final ServerWorld world) {
        return pos -> {
            try {
                return resolve(world.getBlock(pos.x(), pos.y(), pos.z()));
            } catch (final IOException exception) {
                return null;
            }
        };
    }

    @SuppressWarnings("PatternValidation")
    public @Nullable BlockData resolve(final BlockState state) {
        final BlockType type = registry.type(state.name()).orElse(null);
        if (type == null) {
            return null;
        }
        return type.dataOrNull(state.properties());
    }

    public @Nullable BlockState blockForItem(final @Nullable Key itemId) {
        // Todo : Match the item to the block it is actually supposed to relate to. This method is just for testing purposes and will need to be removed!
        if (itemId == null) {
            return null;
        }

        if (itemId.equals(Key.key("minecraft", "water_bucket"))) {
            return new BlockState(Key.key("minecraft", "water"), Map.of("level", "0"));
        }

        if (itemId.equals(Key.key("minecraft", "lava_bucket"))) {
            return new BlockState(Key.key("minecraft", "lava"), Map.of("level", "0"));
        }

        final BlockState candidate = BlockState.of(itemId);
        if (candidate.isAir()) {
            return null;
        }
        if (contains(candidate)) {
            return candidate;
        }
        return BlockState.of(Key.key("minecraft", "cobblestone"));
    }
}