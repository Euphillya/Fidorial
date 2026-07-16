package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Lightable extends BlockData {

    default boolean isLit() {
        return Boolean.parseBoolean(get("lit"));
    }

    default Lightable setLit(boolean lit) {
        return (Lightable) with("lit", String.valueOf(lit));
    }
}
