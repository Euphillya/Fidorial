package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Waterlogged extends BlockData {

    default boolean isWaterlogged() {
        return Boolean.parseBoolean(get("waterlogged"));
    }

    default Waterlogged setWaterlogged(boolean waterlogged) {
        return (Waterlogged) with("waterlogged", String.valueOf(waterlogged));
    }
}
