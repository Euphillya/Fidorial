package fr.euphyllia.fidorial.server.world;

import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockRegistry;
import fr.fidorial.world.block.BlockType;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import net.kyori.adventure.key.Key;

import java.util.Map;


public record BlockStateRegistry(BlockRegistry registry) {

    private static final int AIR_BLOCK = 0;

    public int networkId(BlockState state) {
        BlockData data = resolve(state);
        return data == null ? AIR_BLOCK : data.networkId();
    }

    public BlockState byId(int networkId) {
        BlockData data = registry.fromNetworkId(networkId);
        if (data == null) {
            return BlockState.AIR;
        }
        return new BlockState(data.key().asString(), data.propertyMap());
    }

    public boolean contains(BlockState state) {
        return resolve(state) != null;
    }

    @SuppressWarnings("PatternValidation")
    private BlockData resolve(BlockState state) {
        BlockType type = registry.type(Key.key(state.name())).orElse(null);
        if (type == null) {
            return null;
        }
        return type.dataOrNull(state.properties());
    }

    public BlockState blockForItem(Key itemId) {
        if (itemId == null) {
            return null;
        }

        if (itemId.asString().equals("minecraft:water_bucket")) {
            return new BlockState("minecraft:water", Map.of("level", "0"));
        }

        if (itemId.asString().equals("minecraft:lava_bucket")) {
            return new BlockState("minecraft:lava", Map.of("level", "0"));
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