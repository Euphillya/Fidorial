package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Waterlogged extends BlockData {

    default boolean isWaterlogged() {
        return Boolean.parseBoolean(get("waterlogged"));
    }

    default Waterlogged setWaterlogged(boolean waterlogged) {
        return (Waterlogged) with("waterlogged", String.valueOf(waterlogged));
    }
}
