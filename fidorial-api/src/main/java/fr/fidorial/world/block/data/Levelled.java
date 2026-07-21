package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockProperty;

public interface Levelled extends BlockData {

    default int getLevel() {
        return Integer.parseInt(get("level"));
    }

    default Levelled setLevel(int level) {
        return (Levelled) with("level", String.valueOf(level));
    }

    default int getMaximumLevel() {
        BlockProperty property = type().property("level");
        if (property == null) {
            throw new IllegalStateException("Cannot determine maximum level");
        }
        return Integer.parseInt(property.values().getLast());
    }
}
