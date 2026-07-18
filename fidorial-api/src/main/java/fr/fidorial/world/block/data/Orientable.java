package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Orientable extends BlockData {

    default Axis getAxis() {
        return Axis.valueOf(get("axis").toUpperCase(java.util.Locale.ROOT));
    }

    default Orientable setAxis(Axis axis) {
        return (Orientable) with("axis", axis.name().toLowerCase(java.util.Locale.ROOT));
    }

    enum Axis {X, Y, Z}
}
