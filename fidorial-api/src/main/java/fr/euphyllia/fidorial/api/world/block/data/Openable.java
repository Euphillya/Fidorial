package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Openable extends BlockData {

    default boolean isOpen() {
        return Boolean.parseBoolean(get("open"));
    }

    default Openable setOpen(boolean open) {
        return (Openable) with("open", String.valueOf(open));
    }
}

