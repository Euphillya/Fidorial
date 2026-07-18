package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Snowable extends BlockData {

    default boolean isSnowy() {
        return Boolean.parseBoolean(get("snowy"));
    }

    default Snowable setSnowy(boolean snowy) {
        return (Snowable) with("snowy", String.valueOf(snowy));
    }
}
