package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Snowable extends BlockData {

    default boolean isSnowy() {
        return Boolean.parseBoolean(get("snowy"));
    }

    default Snowable setSnowy(boolean snowy) {
        return (Snowable) with("snowy", String.valueOf(snowy));
    }
}
