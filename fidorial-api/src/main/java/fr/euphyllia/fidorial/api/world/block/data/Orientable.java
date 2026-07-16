package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;

public interface Orientable extends BlockData {

    default Axis getAxis() {
        return Axis.valueOf(get("axis").toUpperCase(java.util.Locale.ROOT));
    }

    default Orientable setAxis(Axis axis) {
        return (Orientable) with("axis", axis.name().toLowerCase(java.util.Locale.ROOT));
    }

    enum Axis {X, Y, Z}
}
