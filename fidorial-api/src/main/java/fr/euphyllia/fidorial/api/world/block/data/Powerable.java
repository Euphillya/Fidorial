package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Powerable extends BlockData {

    default boolean isPowered() {
        return Boolean.parseBoolean(get("powered"));
    }

    default Powerable setPowered(boolean powered) {
        return (Powerable) with("powered", String.valueOf(powered));
    }
}
