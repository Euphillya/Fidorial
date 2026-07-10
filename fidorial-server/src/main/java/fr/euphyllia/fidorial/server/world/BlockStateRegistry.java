package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;

import java.util.HashMap;
import java.util.Map;

public final class BlockStateRegistry {

    private final Map<BlockState, Integer> ids = new HashMap<>();
    private int defaultId = 0; // air

    public BlockStateRegistry() {
        register(BlockState.AIR, 0);
        register(BlockState.of("minecraft:cobblestone"), 14);
    }

    public void register(BlockState state, int networkId) {
        ids.put(state, networkId);
    }

    public int networkId(BlockState state) {
        return ids.getOrDefault(state, defaultId);
    }

    public boolean contains(BlockState state) {
        return ids.containsKey(state);
    }
}
