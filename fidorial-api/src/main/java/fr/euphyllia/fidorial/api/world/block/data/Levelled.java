package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;
import fr.euphyllia.fidorial.api.world.block.BlockProperty;

public interface Levelled extends BlockData {

    default int getLevel() {
        return Integer.parseInt(get("level"));
    }

    default Levelled setLevel(int level) {
        return (Levelled) with("level", String.valueOf(level));
    }

    default int getMaximumLevel() {
        BlockProperty property = type().property("level");
        return Integer.parseInt(property.values().getLast());
    }
}
