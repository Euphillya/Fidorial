package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Rotatable extends BlockData {

    default int getRotation() {
        return Integer.parseInt(get("rotation"));
    }

    default Rotatable setRotation(int rotation) {
        return (Rotatable) with("rotation", String.valueOf(rotation));
    }
}

