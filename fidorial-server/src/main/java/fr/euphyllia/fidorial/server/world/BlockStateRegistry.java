package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.world.block.Blocks;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;

import java.util.HashMap;
import java.util.Map;

public final class BlockStateRegistry {

    private static final int AIR_BLOCK = 0;

    private final Map<BlockState, Integer> ids = new HashMap<>();
    private final Map<Integer, BlockState> states = new HashMap<>();

    public BlockStateRegistry() {
        for (Blocks block : Blocks.values()) {
            register(BlockState.of(block.key().asString()), block.networkId());
        }
    }

    public void register(BlockState state, int networkId) {
        ids.put(state, networkId);
        states.putIfAbsent(networkId, state);
    }

    public int networkId(BlockState state) {
        return ids.getOrDefault(state, AIR_BLOCK);
    }

    public BlockState byId(int networkId) {
        return states.getOrDefault(networkId, BlockState.AIR);
    }

    public boolean contains(BlockState state) {
        return ids.containsKey(state);
    }

    public BlockState blockForItem(Key itemId) {
        if (itemId == null) {
            return null;
        }
        BlockState candidate = BlockState.of(itemId.asString());
        if (candidate.isAir()) {
            return null;
        }
        if (contains(candidate)) {
            return candidate;
        }
        return BlockState.of("minecraft:cobblestone");
    }
}