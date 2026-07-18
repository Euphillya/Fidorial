package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Lightable extends BlockData {

    default boolean isLit() {
        return Boolean.parseBoolean(get("lit"));
    }

    default Lightable setLit(boolean lit) {
        return (Lightable) with("lit", String.valueOf(lit));
    }
}
