package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Powerable extends BlockData {

    default boolean isPowered() {
        return Boolean.parseBoolean(get("powered"));
    }

    default Powerable setPowered(boolean powered) {
        return (Powerable) with("powered", String.valueOf(powered));
    }
}
